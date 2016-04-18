package com.ylk.datamineservice.frame;

public class BroadCastFrame {
	protected int pHead = 0x88;
	protected int pid = 0x10;
	protected int  serverNo = 0xF1;
	protected int moduleNo;
	protected int deviceNo = 0x53;
	protected int frameType = 0x00;
	protected int frameNo;
	protected int dataLenth;
	protected byte[] data = new byte[6];
	public int getpHead() {
		return pHead;
	}
	public void setpHead(int pHead) {
		this.pHead = pHead;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getServerNo() {
		return serverNo;
	}
	public void setServerNo(int serverNo) {
		this.serverNo = serverNo;
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
	public int getFrameType() {
		return frameType;
	}
	public void setFrameType(int frameType) {
		this.frameType = frameType;
	}
	public int getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(int frameNo) {
		this.frameNo = frameNo;
	}
	public int getDataLenth() {
		return dataLenth;
	}
	public void setDataLenth(int dataLenth) {
		this.dataLenth = dataLenth;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
