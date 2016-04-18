package com.ylk.datamineservice.msg;


public abstract class BaseMsg {
	protected int packNum = 0;
	
	protected int msgType;
	
	protected int moduleNo;
	
	protected int deviceNo;

	public int getPackNum() {
		return packNum;
	}
	
	public void setPackNum(int packNum) {
		this.packNum = packNum;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(int moduleNo) {
		this.moduleNo = moduleNo;
	}

	public int getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(int deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	public String getCdzNo() {
		return moduleNo + "0" + getDeviceNoString();
	}
	private String getDeviceNoString() {
		if (deviceNo >= 10) {
			return deviceNo +"";
		} else {
			return "0" + deviceNo;
		}
	}
	
	

}
