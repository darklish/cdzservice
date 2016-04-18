package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.util.FrameTypeUtil;


public class ResStartChargeMsg extends ResBaseMsg {
	
	private int chargeCode;
	
	private int code = 0x00;
	
	public ResStartChargeMsg() {
		
	}


	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)chargeCode);
		dataBuf.put((byte)code);
		dataBuf.flip();
		this.fType = FrameTypeUtil.RES_START_CHARGE;
	}


	public int getChargeCode() {
		return chargeCode;
	}


	public void setChargeCode(int chargeCode) {
		this.chargeCode = chargeCode;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}
	
	

}
