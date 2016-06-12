package com.ylk.datamineservice.frame;

import java.net.InetSocketAddress;

public class ResBaseFrame {
	
	protected int pHead = 0x88;
	protected int gunType;
	protected int  serverNo;
	protected int moduleNo;
	protected int deviceNo;
	protected int frameType;
	protected int frameNo;
	protected int dataLenth;
	protected byte dst;
	protected byte[] data = new byte[6];
	protected InetSocketAddress remoteAddress;
	public ResBaseFrame(BaseFrame baseFrame) {
		this.gunType = baseFrame.gunType;
		this.serverNo = baseFrame.serverNo;
		this.moduleNo = baseFrame.moduleNo;
		this.deviceNo = baseFrame.deviceNo;
		this.frameType = baseFrame.frameType;
		this.frameNo = baseFrame.frameNo;
		this.dst = baseFrame.src;
		this.remoteAddress = baseFrame.getRemoteAddress();
	}
	
	public int getpHead() {
		return pHead;
	}
	public void setpHead(int pHead) {
		this.pHead = pHead;
	}
	
	
	
	public int getGunType() {
		return gunType;
	}

	public void setGunType(int gunType) {
		this.gunType = gunType;
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

	public InetSocketAddress getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(InetSocketAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public int getDataLenth() {
		return dataLenth;
	}

	public void setDataLenth(int dataLenth) {
		this.dataLenth = dataLenth;
	}

	public byte getDst() {
		return dst;
	}

	public void setDst(byte dst) {
		this.dst = dst;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
	
	
}
