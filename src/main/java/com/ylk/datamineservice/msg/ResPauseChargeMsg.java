package com.ylk.datamineservice.msg;

import java.nio.ByteBuffer;
import java.util.List;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.frame.ResBaseFrame;
import com.ylk.datamineservice.util.FrameTypeUtil;

public class ResPauseChargeMsg extends ResBaseMsg {
	
	private int pauseCode;
	
	private float consum;
	
	public ResPauseChargeMsg() {
		
	}

	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)pauseCode);
		dataBuf.putFloat(consum);
		dataBuf.flip();
		this.fType = 0x44;
	}

	public int getPauseCode() {
		return pauseCode;
	}

	public void setPauseCode(int pauseCode) {
		this.pauseCode = pauseCode;
	}

	public float getConsum() {
		return consum;
	}

	public void setConsum(float consum) {
		this.consum = consum;
	}

	

}
