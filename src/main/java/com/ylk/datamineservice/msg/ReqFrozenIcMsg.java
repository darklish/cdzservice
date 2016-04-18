package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

public class ReqFrozenIcMsg extends ReqSinglePackMsg{
	private int frozenCode;
	
	private int cardNo;
	

	public ReqFrozenIcMsg(BaseFrame firstRecFrame) {
		super(firstRecFrame);
	}

	@Override
	protected void initSelfMsg() {
		byte[] cardBytes = new byte[4];
		data.get(cardBytes, 0, cardBytes.length);
		cardNo =ByteUtil.getInt(cardBytes, 0);
		
		byte[] fBytes = new byte[1];
		data.get(fBytes, 0, fBytes.length);
		frozenCode =BCDUtil.bytesToHex(fBytes);
		
	}
	
	public int getFrozenCode() {
		return frozenCode;
	}

	public void setFrozenCode(int frozenCode) {
		this.frozenCode = frozenCode;
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
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
		sb.append(",冻结代码:");
		sb.append(frozenCode);
		return sb.toString();
	}
}
