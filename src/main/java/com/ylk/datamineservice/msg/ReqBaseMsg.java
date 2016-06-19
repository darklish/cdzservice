package com.ylk.datamineservice.msg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ConcurrentHashMap;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.ConstUtil;

public abstract class ReqBaseMsg extends BaseMsg {
	protected ConcurrentHashMap<Integer,BaseFrame> packList = new ConcurrentHashMap<Integer, BaseFrame>();
	
	protected String key;//
	
	protected boolean completeFlag = false;
	
	protected BaseFrame firstFrame;
	
	protected ByteBuffer data;
	
	public ReqBaseMsg(){
		
	}
	
	public ReqBaseMsg(BaseFrame firstRecFrame) {
		this.firstFrame = firstRecFrame;
		addPackFrame(firstFrame);
		this.gunType = firstRecFrame.getGunType();
		this.packNum = firstRecFrame.getPackNum();
		this.msgType = firstRecFrame.getFrameType();
		this.moduleNo = firstRecFrame.getModuleNo();
		this.deviceNo = firstRecFrame.getDeviceNo();
		this.key = ""+ gunType + msgType + moduleNo + deviceNo;
	}
	
	public String getStrGunType() {
		if (gunType == 0x05) {
			return "A";
		} else {
			return "B";
		}
	}
	public boolean isCompleteFlag() {
		return completeFlag;
	}

	public void setCompleteFlag(boolean completeFlag) {
		this.completeFlag = completeFlag;
	}
	public String getMsgKey() {
		return key;
	}

	public boolean addPackFrame(BaseFrame baseFrame) {
		packList.put(baseFrame.getFrameNo(), baseFrame);
		//校验消息完整性
		return checkMsgComplete();
	}
	private boolean checkMsgComplete() {
		System.out.println("已收到帧个数:"+packList.size());
		if (packList.size() == packNum) {
			completeFlag = true;
			
		}
		return completeFlag;
	}
	
	protected abstract void initSelfMsg();
	public void extraData() {
		data = ByteBuffer.allocate(packNum*ConstUtil.FRAME_DATA_LEN).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i< packList.size(); i++) {
			BaseFrame baseFrame = packList.get(i);
			data.put(baseFrame.getData());
		}
		data.flip();
		initSelfMsg();
	}

	public BaseFrame getFirstFrame() {
		return firstFrame;
	}

	public void setFirstFrame(BaseFrame firstFrame) {
		this.firstFrame = firstFrame;
	}
	
	
}
