package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;

public class ReqIcAddMsg extends ReqMultiPackMsg{
	
	private long cardNo;
	
	private long pwd;
	
	private float amount;

	public ReqIcAddMsg(BaseFrame firstRecFrame) {
		super(firstRecFrame);
	}

	@Override
	protected void initSelfMsg() {
		if (completeFlag) {
			try{
				byte[] cardBytes = new byte[4];
				data.get(cardBytes, 0, cardBytes.length);
				cardNo =ByteUtil.getInt(cardBytes, 0);
				
				byte[] pwdBytes = new byte[4];
				data.get(pwdBytes, 0, 4);
				pwd = ByteUtil.getLong(pwdBytes,0);
				
				byte[] amountBytes = new byte[4];
				data.get(amountBytes, 0, 4);
				amount = ByteUtil.getFloat(amountBytes,0);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
