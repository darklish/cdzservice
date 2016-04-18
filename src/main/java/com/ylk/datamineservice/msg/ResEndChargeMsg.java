package com.ylk.datamineservice.msg;

import java.nio.ByteBuffer;
import java.util.List;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.frame.ResBaseFrame;
import com.ylk.datamineservice.util.FrameTypeUtil;

public class ResEndChargeMsg extends ResBaseMsg {
	
	private int dealCode;
	
	private int cosum;
	
	public ResEndChargeMsg() {
		
	}


	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)dealCode);
		dataBuf.putInt(cosum);
		dataBuf.flip();
		this.fType = FrameTypeUtil.RES_END_CHARGE;
	}


	public int getDealCode() {
		return dealCode;
	}


	public void setDealCode(int dealCode) {
		this.dealCode = dealCode;
	}


	public int getCosum() {
		return cosum;
	}


	public void setCosum(int cosum) {
		this.cosum = cosum;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("dealCode:");
		sb.append(dealCode);
		sb.append(",cosum:");
		sb.append(cosum);
		return sb.toString();
	}



}
