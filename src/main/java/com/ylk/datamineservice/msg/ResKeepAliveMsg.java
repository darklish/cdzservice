package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.BCDUtil;

public class ResKeepAliveMsg extends ReqSinglePackMsg {
	
	private int runState;
	
	private int workState;

	public ResKeepAliveMsg(BaseFrame frame) {
		super(frame);
	}

	@Override
	protected void initSelfMsg() {
		byte[] cardBytes = new byte[1];
		data.get(cardBytes, 0, cardBytes.length);
		runState =BCDUtil.bytesToHex(cardBytes);
		
		byte[] fBytes = new byte[1];
		data.get(fBytes, 0, fBytes.length);
		workState =BCDUtil.bytesToHex(fBytes);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("模块号:");
		sb.append(moduleNo);
		sb.append(",设备号:");
		sb.append(deviceNo);
		sb.append(",运行状态:");
		sb.append(runState);
		sb.append(",工作状态:");
		sb.append(workState);
		return sb.toString();
	}

	public int getRunState() {
		return runState;
	}

	public void setRunState(int runState) {
		this.runState = runState;
	}

	public int getWorkState() {
		return workState;
	}

	public void setWorkState(int workState) {
		this.workState = workState;
	}
	
	


}
