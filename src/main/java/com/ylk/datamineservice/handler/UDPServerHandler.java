package com.ylk.datamineservice.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import com.ylk.datamineservice.event.CommonFrameEvent;
import com.ylk.datamineservice.event.CompletePacEvent;
import com.ylk.datamineservice.event.DeviceOfflineEvent;
import com.ylk.datamineservice.event.SubPacEvent;
import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.frame.ResBaseFrame;
import com.ylk.datamineservice.model.CdzInfo;
import com.ylk.datamineservice.msg.ReqBaseMsg;
import com.ylk.datamineservice.msg.ReqBeginChargeMsg;
import com.ylk.datamineservice.msg.ReqEndChargeMsg;
import com.ylk.datamineservice.msg.ReqFrozenIcMsg;
import com.ylk.datamineservice.msg.ReqICStateMsg;
import com.ylk.datamineservice.msg.ReqIcAddMsg;
import com.ylk.datamineservice.msg.ReqMultiPackMsg;
import com.ylk.datamineservice.msg.ReqOfflineDataMsg;
import com.ylk.datamineservice.msg.ReqPauseChargeMsg;
import com.ylk.datamineservice.msg.ReqSinglePackMsg;
import com.ylk.datamineservice.msg.ReqStartChargeMsg;
import com.ylk.datamineservice.msg.ReqTimeCheckMsg;
import com.ylk.datamineservice.msg.ResKeepAliveMsg;
import com.ylk.datamineservice.service.impl.MessageServiceImpl;
import com.ylk.datamineservice.util.FrameTypeUtil;
@Service
@Scope("singleton")
@ManagedResource (objectName= "bean:name=UDPServerHandler" , description= "UDPServerHandler Bean" )
public class UDPServerHandler extends SimpleChannelInboundHandler<BaseFrame> {
	Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	public static final ReadWriteLock lock = new ReentrantReadWriteLock(false);
	public static final ReadWriteLock offLineLock = new ReentrantReadWriteLock(false);
	@Resource
	private MessageServiceImpl messageService;
	
	ConcurrentMap<String,ReqMultiPackMsg> multiPackMap = new ConcurrentHashMap<String,ReqMultiPackMsg>();// 多包中间对象
	
	ConcurrentMap<String,DeviceKeepAliveTask> deviceMap = new ConcurrentHashMap<String,DeviceKeepAliveTask>();
	
	Timer timer;
	HashedWheelTimer hashTimer;
	private int receiveMsgTimeOut = 10;//秒 接收完整消息超时限制
	private int deviceOfflineTimeOut = 60000;//毫秒,设备离线周期设置
	public UDPServerHandler() {
		timer = new Timer();
		hashTimer = new HashedWheelTimer();
		// 获取所有的充电桩设备,建立定时任务
	}
	
	public void dealAllCdz() {
		logger.info("启动时设备状态监控初始化开始!");
		List<CdzInfo> cInfoLst = messageService.selectAllDevice();
		if (cInfoLst != null) {
			for (int i = 0; i < cInfoLst.size();i++) {
				String cdzNo = cInfoLst.get(i).getCdzno();
				logger.info("充电桩{} : {}", i+1, cdzNo);
				DeviceKeepAliveTask task = new DeviceKeepAliveTask(cdzNo);
				timer.schedule(task, deviceOfflineTimeOut);
				deviceMap.put(cdzNo, task);
			}
		}
		logger.info("启动时设备状态监控初始化完成!");
		
		logger.info("初始化检查系统基础配置开始");
		
		if (messageService.sysCommonConfigCheck()) {
			logger.info("初始化检查系统基础配置成功");
		} else {
			logger.info("初始化检查系统基础配置失败");
		}
		
		logger.info("初始化检查系统基础配置结束");
		
	}

	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseFrame out)
			throws Exception {
		//消息类型,心跳;故障;获取卡号;卡号、密码校验;充电量上传;故障锁卡后激活
		String cdzNo = out.getCdzNo();
		if (logger.isDebugEnabled()) {
			logger.debug("接受到设备 心跳消息,cdzNo:{}", cdzNo);
		}
		
		messageService.getEventbus().post(new CommonFrameEvent(cdzNo));
		offLineLock.readLock().lock();
		DeviceKeepAliveTask oldTask = deviceMap.get(cdzNo);
		if (oldTask != null) {
			oldTask.cancel();
			DeviceKeepAliveTask task = new DeviceKeepAliveTask(cdzNo);
			timer.schedule(task, deviceOfflineTimeOut);
			deviceMap.put(cdzNo, task);
		} else {
			DeviceKeepAliveTask task = new DeviceKeepAliveTask(cdzNo);
			timer.schedule(task, deviceOfflineTimeOut);
			deviceMap.put(cdzNo, task);
		}
		offLineLock.readLock().unlock();
		//messageService.getEventbus().post(new SubPacEvent(msg,ctx));
		if (out.isSubPac()) {
			final String key = out.getPackKey();
			ResBaseFrame res1Msg = new ResBaseFrame(out);
			ctx.writeAndFlush(res1Msg);
			lock.readLock().lock();
			ReqMultiPackMsg msg1 = multiPackMap.get(key);
			lock.readLock().unlock();
			
			if (msg1 != null) {
				//lock.readLock().lock();
				boolean flag = msg1.addPackFrame(out);
				//lock.readLock().unlock();
				//是否完整消息
				if (flag) {
					//调用后台处理
					multiPackMap.get(key).getTimeout().cancel();
					ReqBaseMsg msg = multiPackMap.get(key);
					messageService.getEventbus().post(new SubPacEvent(msg,ctx));
					multiPackMap.remove(key);
				}
			}
			else {
				int frameType = out.getFrameType();
				Timeout timeout = hashTimer.newTimeout(new TimerTask(){
					public void run(Timeout timeout) throws Exception {
						logger.error("接收消息10秒超时;key:{}",key);
						multiPackMap.remove(key);
					}
				}, receiveMsgTimeOut, TimeUnit.SECONDS);
				ReqMultiPackMsg con = null;
				switch(frameType) {
				case FrameTypeUtil.REQ_IC_STATE:
					con = new ReqICStateMsg(out);
					con.setTimeout(timeout);
					multiPackMap.put(key, con);
					break;
				case FrameTypeUtil.REQ_START_CHARGE:
					con = new ReqStartChargeMsg(out);
					con.setTimeout(timeout);
					multiPackMap.put(key, con);
					break;
				case FrameTypeUtil.REQ_END_CHARGE:
					con = new ReqEndChargeMsg(out);
					con.setTimeout(timeout);
					multiPackMap.put(key, con);
					break;
					
				case FrameTypeUtil.REQ_PAUSE_CHARGE:
					con = new ReqPauseChargeMsg(out);
					con.setTimeout(timeout);
					multiPackMap.put(key, con);
					break;
				case FrameTypeUtil.REQ_OFFLINE_DATA:
					con = new ReqOfflineDataMsg(out);
					con.setTimeout(timeout);
					multiPackMap.put(key, con);
					break;
				case FrameTypeUtil.REQ_IC_ADD:
					con = new ReqIcAddMsg(out);
					con.setTimeout(timeout);
					multiPackMap.put(key, con);
					break;
				case FrameTypeUtil.RES_DEVICE_STATE:
					/*con = new ReqD(out);
					con.setTimeout(timeout);
					multiPackMap.put(key, con);
					break;*/
				}
			}
		}
		else {
			int frameType = out.getFrameType();
			ReqSinglePackMsg sinMsg;
			switch(frameType)
			{
			case FrameTypeUtil.REQ_TIME_CHECK:
				sinMsg = new ReqTimeCheckMsg(out);
				messageService.getEventbus().post(new CompletePacEvent(sinMsg,ctx));
				break;
			case FrameTypeUtil.REQ_FROZEN_IC:
				sinMsg = new ReqFrozenIcMsg(out);
				messageService.getEventbus().post(new CompletePacEvent(sinMsg,ctx));
				break;
			case FrameTypeUtil.RES_DEVICE_STATE:
				sinMsg = new ResKeepAliveMsg(out);
				messageService.getEventbus().post(new CompletePacEvent(sinMsg,ctx));
				break;
			case FrameTypeUtil.REQ_BEGIN_CHARGE:
				sinMsg = new ReqBeginChargeMsg(out);
				messageService.getEventbus().post(new CompletePacEvent(sinMsg,ctx));
				break;
			}
		}
	}
	
	class DeviceKeepAliveTask extends java.util.TimerTask{
		String cdzNo;
    	public DeviceKeepAliveTask(String cdzNo){
    		this.cdzNo = cdzNo; 
    	}
		@Override
		public void run() {
			//设备离线
			deviceMap.remove(cdzNo);
			logger.info("设备离线:cdzNo:{}",cdzNo);
			messageService.getEventbus().post(new DeviceOfflineEvent(cdzNo));
			System.gc();
		}
		@Override
		public boolean cancel() {
			return super.cancel();
		}
	}
	
	@ManagedOperation(description="devicetask size")
	public int getTaskNum() {
		return deviceMap.size();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	
	
	

}
