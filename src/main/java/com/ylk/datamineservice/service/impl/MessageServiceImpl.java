package com.ylk.datamineservice.service.impl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.HashedWheelTimer;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import com.google.common.collect.MapMaker;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.ylk.datamineservice.event.CommonFrameEvent;
import com.ylk.datamineservice.event.CompletePacEvent;
import com.ylk.datamineservice.event.DeviceOfflineEvent;
import com.ylk.datamineservice.event.SubPacEvent;
import com.ylk.datamineservice.event.TestEvent;
import com.ylk.datamineservice.frame.ResBaseFrame;
import com.ylk.datamineservice.model.CdzInfo;
import com.ylk.datamineservice.msg.ReqBaseMsg;
import com.ylk.datamineservice.msg.ReqBeginChargeMsg;
import com.ylk.datamineservice.msg.ReqEndChargeMsg;
import com.ylk.datamineservice.msg.ReqFrozenIcMsg;
import com.ylk.datamineservice.msg.ReqICStateMsg;
import com.ylk.datamineservice.msg.ReqMultiPackMsg;
import com.ylk.datamineservice.msg.ReqOfflineDataMsg;
import com.ylk.datamineservice.msg.ReqPauseChargeMsg;
import com.ylk.datamineservice.msg.ReqStartChargeMsg;
import com.ylk.datamineservice.msg.ReqTimeCheckMsg;
import com.ylk.datamineservice.msg.ResBeginChargeMsg;
import com.ylk.datamineservice.msg.ResEndChargeMsg;
import com.ylk.datamineservice.msg.ResFrozenIcMsg;
import com.ylk.datamineservice.msg.ResIcStateMsg;
import com.ylk.datamineservice.msg.ResKeepAliveMsg;
import com.ylk.datamineservice.msg.ResOfflineDataMsg;
import com.ylk.datamineservice.msg.ResPauseChargeMsg;
import com.ylk.datamineservice.msg.ResStartChargeMsg;
import com.ylk.datamineservice.msg.ResTimeCheckMsg;
import com.ylk.datamineservice.service.BusinessComService;
import com.ylk.datamineservice.service.MessageService;

@Service
@ManagedResource (objectName= "bean:name=MessageServiceImpl" , description= "MessageServiceImpl Bean" )
public class MessageServiceImpl implements MessageService {
	Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	private static final int CLEANUP_PERIOD = 60 * 1000;
	private EventBus eventbus;

	private ExecutorService executors;
	
	ConcurrentMap<Object, ChannelHandlerContext> userChannleCtxPool;
	
	//存放can消息，key为充电桩+消息号,设置后判断消息完整性
	ConcurrentMap<String,ReqMultiPackMsg> messagePool = new ConcurrentHashMap<String,ReqMultiPackMsg>();
	
	//存放can消息，key为充电桩+消息号,存放消息是否超时
	ConcurrentMap<String,ReqMultiPackMsg> receiveMsgTimerPool;
	
	@Resource
	private BusinessComService businessComService;
	
	
	private int receiveMsgTimeOut = 60;
	

	// 并发程度，和CPU数量一样就可以
	public static final int DEFAULT_CONCURRENT_LEVEL = Runtime.getRuntime()
			.availableProcessors() * 2;
	private int concurrentLevel = DEFAULT_CONCURRENT_LEVEL;
	HashedWheelTimer timer;
	private ConcurrentHashMap<Object, Boolean> undoneEventList = new ConcurrentHashMap<Object, Boolean>();

	public MessageServiceImpl() {
		executors = Executors.newFixedThreadPool(concurrentLevel,
				new EventThreadFactory());
		userChannleCtxPool = new MapMaker().concurrencyLevel(concurrentLevel).makeMap();
		eventbus = new AsyncEventBus("eventbus", executors);
		getEventbus().register(this);
		
        //Timer cleanupTimer = new Timer("cleanup-timer", true);

//        cleanupTimer.schedule(new java.util.TimerTask() {
//            @Override
//            public void run() {
//                for (Object devt : undoneEventList.keySet()) {
//                	System.out.println("触发");
//                    eventbus.post(devt);
//                }
//            }
//        }, 0, CLEANUP_PERIOD);
	}
	
	@ManagedOperation(description="undone event size")
	public int getUndoneEventList() {
		return undoneEventList.size();
	}
	@ManagedOperation(description="event size")
	public void postEvent(int i) {
		
	}

	static class EventThreadFactory implements ThreadFactory {
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;
		private final ThreadGroup group;

		EventThreadFactory() {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
					.getThreadGroup();
			namePrefix = "event111-";
		}
		
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix
					+ threadNumber.getAndIncrement(), 0);
			t.setDaemon(true);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
	@AllowConcurrentEvents
	@Subscribe 
	public void onTestEvent(TestEvent event) {
		userChannleCtxPool.put(1, event.handlerContext);
		undoneEventList.remove(event);
		//cdzInfoService.listAll();
		System.out.println();
	}
	
	@AllowConcurrentEvents
	@Subscribe 
	public void onSubPacEvent(SubPacEvent event) {
		final String key = event.getMessageId();
		logger.info("key:{}",key);
		ReqBaseMsg bm = event.getBaseMsg();
		// 统一参数校验
		boolean commonCheck = commonCheckMsg(bm);
		if (!commonCheck) {
			logger.info("未查询到充电桩信息,cdzNo:{}",bm.getCdzNo());
			return;
		}
		if (bm instanceof ReqICStateMsg) {
			ReqICStateMsg msg = (ReqICStateMsg)bm;
			msg.extraData();
			logger.info("接收卡号验证请求消息:{}",msg.toString());
			dealReqICStateMsg(msg,event.handlerContext);
		}
		else if(bm instanceof ReqStartChargeMsg){
			ReqStartChargeMsg msg = (ReqStartChargeMsg)bm;
			msg.extraData();
			logger.info("接收请求充电开始消息:{}",msg.toString());
			dealReqStartChargeMsg(msg,event.handlerContext);
		}
		else if(bm instanceof ReqEndChargeMsg){
			ReqEndChargeMsg msg = (ReqEndChargeMsg)bm;
			msg.extraData();
			logger.info("接收请求充电结束消息:{}",msg.toString());
			dealReqEndChargeMsg(msg,event.handlerContext);
		}
		
		else if(bm instanceof ReqPauseChargeMsg){
			ReqPauseChargeMsg msg = (ReqPauseChargeMsg)bm;
			msg.extraData();
			logger.info("接收请求充电暂停消息:{}",msg.toString());
			dealReqPauseChargeMsg(msg,event.handlerContext);
		}
		else if(bm instanceof ReqOfflineDataMsg){
			ReqOfflineDataMsg msg = (ReqOfflineDataMsg)bm;
			msg.extraData();
			logger.info("接收上传上次离线数据消息:{}",msg.toString());
			dealReqOfflineDataMsg(msg,event.handlerContext);
		}
		
	}
	

	private boolean commonCheckMsg(ReqBaseMsg bm) {
		return businessComService.commonCheckMsg(bm);
	}

	private void dealReqOfflineDataMsg(ReqOfflineDataMsg msg,
			ChannelHandlerContext handlerContext) {
		logger.info("处理离线数据上传信息");
		// 校验密码,返回卡状态
		ResOfflineDataMsg resMsg = businessComService.offLineDataUpload(msg);
		List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
		for (int i = 0; i < baseFrames.size(); i++) {
			handlerContext.writeAndFlush(baseFrames.get(i));
		}
		
	}

	private void dealReqPauseChargeMsg(ReqPauseChargeMsg msg,
			ChannelHandlerContext handlerContext) {
		logger.info("处理暂停充电信息");
		// 校验密码,返回卡状态
		ResPauseChargeMsg resMsg = businessComService.pauseCharge(msg);
		List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
		for (int i = 0; i < baseFrames.size(); i++) {
			handlerContext.writeAndFlush(baseFrames.get(i));
		}
		
	}

	private void dealReqEndChargeMsg(ReqEndChargeMsg msg,
			ChannelHandlerContext handlerContext) {
		logger.info("处理结束充电信息");
		// 校验密码,返回卡状态
		ResEndChargeMsg resMsg = businessComService.endCharge(msg);
		List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
		for (int i = 0; i < baseFrames.size(); i++) {
			handlerContext.writeAndFlush(baseFrames.get(i));
		}
		
	}

	private void dealReqStartChargeMsg(ReqStartChargeMsg msg,
			ChannelHandlerContext handlerContext) {
		logger.info("处理开始充电信息");
		// 校验密码,返回卡状态
		ResStartChargeMsg resMsg = businessComService.startCharge(msg);
		List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
		for (int i = 0; i < baseFrames.size(); i++) {
			handlerContext.writeAndFlush(baseFrames.get(i));
		}
		
	}

	private void dealReqICStateMsg(ReqICStateMsg msg,ChannelHandlerContext ctx) {
		logger.info("处理校验IC卡信息");
		// 校验密码,返回卡状态
		ResIcStateMsg resMsg = businessComService.checkICCard(msg);
		List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
		for (int i = 0; i < baseFrames.size(); i++) {
			ctx.writeAndFlush(baseFrames.get(i));
		}
	}

	@AllowConcurrentEvents
	@Subscribe 
	public void onCompletePacEvent(CompletePacEvent event) {
		ReqBaseMsg baseMsg = event.getBaseMsg();
		if (baseMsg instanceof ReqTimeCheckMsg) {
			ReqTimeCheckMsg msg = (ReqTimeCheckMsg)baseMsg;
			msg.extraData();
			logger.info("接收时间校验消息");
			ResTimeCheckMsg resMsg = businessComService.reqTimeCheck(msg);
			logger.info("返回时间校验消息:{}",resMsg.toString());
			List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
			for (int i = 0; i < baseFrames.size(); i++) {
				event.handlerContext.writeAndFlush(baseFrames.get(i));
			}
		}
		else if (baseMsg instanceof ReqFrozenIcMsg) {
			ReqFrozenIcMsg msg = (ReqFrozenIcMsg)baseMsg;
			msg.extraData();
			logger.info("接收冻结IC卡消息:{}",msg.toString());
			ResFrozenIcMsg resMsg = businessComService.frozenIc(msg);
			List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
			for (int i = 0; i < baseFrames.size(); i++) {
				event.handlerContext.writeAndFlush(baseFrames.get(i));
			}
		}
		else if (baseMsg instanceof ReqBeginChargeMsg) {
			ReqBeginChargeMsg msg = (ReqBeginChargeMsg)baseMsg;
			msg.extraData();
			logger.info("接收正式充电消息");
			ResBeginChargeMsg resMsg = businessComService.beginCharge(msg);
			logger.info("返回正式充电消息:{}",resMsg.toString());
			List<ResBaseFrame> baseFrames = resMsg.getResBaseFrames(msg.getFirstFrame());
			for (int i = 0; i < baseFrames.size(); i++) {
				event.handlerContext.writeAndFlush(baseFrames.get(i));
			}
		}
		else if (baseMsg instanceof ResKeepAliveMsg) {
			ResKeepAliveMsg msg = (ResKeepAliveMsg)baseMsg;
			msg.extraData();
			logger.info("接收设备心跳消息");
			businessComService.keepAlive(msg);
		}
	}
	
	@AllowConcurrentEvents
	@Subscribe 
	public void onDeviceOfflineEvent(DeviceOfflineEvent event) {
		businessComService.deviceOffLine(event.getCdzNo());
	}
	
	@AllowConcurrentEvents
	@Subscribe 
	public void onCommonFrameEvent(CommonFrameEvent event) {
		businessComService.onLineDevicee(event.getCdzNo());
	}
	
	
	
	public EventBus getEventbus() {
		return eventbus;
	}
	public void setEventbus(EventBus eventbus) {
		this.eventbus = eventbus;
	}

	public List<CdzInfo> selectAllDevice() {
		return businessComService.listAllDevices();
	}
	

}
