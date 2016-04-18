package com.ylk.datamineservice.msg;

import java.util.Arrays;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

public class ReqPauseChargeMsg extends ReqMultiPackMsg {
	private int cardNo;
	
	private int endCode;
	
	private int meterQua;
	public ReqPauseChargeMsg(BaseFrame firstRecFrame) {
		super(firstRecFrame);
	}
	@Override
	public void initSelfMsg() {
		if (completeFlag) {
			try{
				byte[] cardBytes = new byte[4];
				data.get(cardBytes, 0, cardBytes.length);
				cardNo =ByteUtil.getInt(cardBytes, 0);
				
				byte[] ccBytes = new byte[1];
				data.get(ccBytes, 0, 1);
				endCode = BCDUtil.bytesToHex(ccBytes);
				
				byte[] cdBytes = new byte[4];
				data.get(cdBytes, 0, 4);
				meterQua = ByteUtil.getInt(cdBytes,0);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("模块号:");
		sb.append(moduleNo);
		sb.append(",设备号:");
		sb.append(deviceNo);
		sb.append(",卡号:");
		sb.append(cardNo);
		sb.append(",暂停请求:");
		sb.append(endCode);
		sb.append(",电表电度");
		sb.append(meterQua);
		return sb.toString();
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public int getEndCode() {
		return endCode;
	}
	public void setEndCode(int endCode) {
		this.endCode = endCode;
	}
	public int getMeterQua() {
		return meterQua;
	}
	public void setMeterQua(int meterQua) {
		this.meterQua = meterQua;
	}
	
	
}
