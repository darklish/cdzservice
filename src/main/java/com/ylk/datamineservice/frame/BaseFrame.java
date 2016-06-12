package com.ylk.datamineservice.frame;

import java.net.InetSocketAddress;
import java.util.Arrays;

import com.ylk.datamineservice.util.BCDUtil;

public abstract class BaseFrame {
	
	protected int gunType;
	
	protected int frameType;
	
	protected boolean isSubPac;
	
	protected int  serverNo;
	
	protected int moduleNo;
	
	protected int deviceNo;
	
	protected int frameNo;
	
	protected int valideByte;
	
	protected byte[] srcBytes;
	
	protected byte[] data;
	protected byte src;
	
	protected int packNum;
	protected InetSocketAddress remoteAddress;
	
	

	public InetSocketAddress getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(InetSocketAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public int getFrameType() {
		return frameType;
	}

	public void setFrameType(int frameType) {
		this.frameType = frameType;
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

	public void setDeviceNo(int deviceNo) {
		this.deviceNo = deviceNo;
	}

	public int getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(int frameNo) {
		this.frameNo = frameNo;
	}

	public int getValideByte() {
		return valideByte;
	}

	public void setValideByte(int valideByte) {
		this.valideByte = valideByte;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
	public byte[] getSrcBytes() {
		return srcBytes;
	}

	public void setSrcBytes(byte[] srcBytes) {
		this.srcBytes = srcBytes;
	}
	
	

	public boolean isSubPac() {
		return isSubPac;
	}

	public void setSubPac(boolean isSubPac) {
		this.isSubPac = isSubPac;
	}
	
	public int getPackNum() {
		return packNum;
	}
	
	

	public int getGunType() {
		return gunType;
	}

	public void superDecoder() {
		if (this.srcBytes != null) {
			byte pidb = srcBytes[0];
			gunType = BCDUtil.byteToHex(pidb);
			byte ftb = srcBytes[1];
			frameType = BCDUtil.byteToHex(ftb);
			byte sn = srcBytes[2];
			serverNo = BCDUtil.byteToHex(sn);
			src = srcBytes[3];
			moduleNo = src >>> 5;
			deviceNo = src & 31;
			byte fnb = srcBytes[4];
			frameNo = BCDUtil.byteToHex(fnb);
			byte vb = srcBytes[5];
			valideByte = BCDUtil.byteToHex(vb);
			data = Arrays.copyOfRange(srcBytes, 6, 6+valideByte);
		}
	}

	public abstract BaseFrame decoder();
	
	/**
	 * 包标识增加枪号判断
	 * @return
	 */
	public String getPackKey() {
		return "" + gunType + frameType + moduleNo + deviceNo;
	}
	
	public String toString() {
		return "mk:"+moduleNo+",dev:"+deviceNo+",gunType:"+gunType+",ft:"+frameType+",fn:"+frameNo;
	}
	

}
