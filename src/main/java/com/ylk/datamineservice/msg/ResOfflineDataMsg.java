package com.ylk.datamineservice.msg;

import java.nio.ByteBuffer;
import java.util.List;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.frame.ResBaseFrame;
import com.ylk.datamineservice.util.FrameTypeUtil;

public class ResOfflineDataMsg extends ResBaseMsg {
	
	private int uploadCode;
	
	private float balance;
	
	public ResOfflineDataMsg() {
		
	}

	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)uploadCode);
		dataBuf.putFloat(balance);
		dataBuf.flip();
		this.fType = 0x45;
		
	}

	public int getUploadCode() {
		return uploadCode;
	}

	public void setUploadCode(int uploadCode) {
		this.uploadCode = uploadCode;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	
	
	

}
