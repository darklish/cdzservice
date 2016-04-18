package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

public class ReqStartChargeMsg extends ReqMultiPackMsg{
	
	private int cardNo;
	
	private int chargeConfig;
	
	private int chargeData;
	
	private int meterQua;
	
	

	public ReqStartChargeMsg(BaseFrame firstRecFrame) {
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
				chargeConfig = BCDUtil.bytesToHex(ccBytes);
				
				byte[] cdBytes = new byte[4];
				data.get(cdBytes, 0, 4);
				chargeData = ByteUtil.getInt(cdBytes,0);
				
				byte[] mqBytes = new byte[4];
				data.get(mqBytes, 0, 4);
				meterQua = ByteUtil.getInt(mqBytes,0);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public int getChargeConfig() {
		return chargeConfig;
	}

	public void setChargeConfig(int chargeConfig) {
		this.chargeConfig = chargeConfig;
	}



	public int getMeterQua() {
		return meterQua;
	}

	public void setMeterQua(int meterQua) {
		this.meterQua = meterQua;
	}

	public int getChargeData() {
		return chargeData;
	}

	public void setChargeData(int chargeData) {
		this.chargeData = chargeData;
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
		sb.append(",充电配置:");
		sb.append(chargeConfig);
		sb.append(",配置数据");
		sb.append(chargeData);
		sb.append(",电表电度");
		sb.append(meterQua);
		return sb.toString();
	}

	
	
}
