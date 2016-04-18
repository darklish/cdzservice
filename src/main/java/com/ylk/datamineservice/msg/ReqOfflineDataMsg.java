package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

public class ReqOfflineDataMsg extends ReqMultiPackMsg {
	
	private int cardNo;
	
	private int chargeConfig;
	
	private int chargeResult;
	
	private int chargeTime;
	
	private int chargeQuantity;
	
	private int meterQua;//当前电表电量
	
	
	

	public ReqOfflineDataMsg(BaseFrame firstRecFrame) {
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
				
				byte[] crBytes = new byte[1];
				data.get(crBytes, 0, 1);
				chargeResult = BCDUtil.bytesToHex(crBytes);
				
				byte[] ctBytes = new byte[4];
				data.get(ctBytes, 0, 4);
				chargeTime = ByteUtil.getInt(ctBytes,0);
				
				byte[] cqBytes = new byte[4];
				data.get(cqBytes, 0, 4);
				meterQua = ByteUtil.getInt(cqBytes,0);
				
				byte[] consumBytes = new byte[4];
				data.get(consumBytes, 0, 4);
				chargeQuantity = ByteUtil.getInt(consumBytes,0);
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
		sb.append(",充电配置:");
		sb.append(chargeConfig);
		sb.append(",充电结果");
		sb.append(chargeResult);
		sb.append(",充电时间");
		sb.append(chargeTime+"分");
		sb.append(",充电电度");
		sb.append(chargeQuantity);
		sb.append(",电表电量");
		sb.append(meterQua);
		return sb.toString();
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

	public int getChargeResult() {
		return chargeResult;
	}

	public void setChargeResult(int chargeResult) {
		this.chargeResult = chargeResult;
	}

	public int getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(int chargeTime) {
		this.chargeTime = chargeTime;
	}

	public int getChargeQuantity() {
		return chargeQuantity;
	}

	public void setChargeQuantity(int chargeQuantity) {
		this.chargeQuantity = chargeQuantity;
	}

	public int getMeterQua() {
		return meterQua;
	}

	public void setMeterQua(int meterQua) {
		this.meterQua = meterQua;
	}




}
