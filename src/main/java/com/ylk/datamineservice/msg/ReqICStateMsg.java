package com.ylk.datamineservice.msg;

import java.nio.ByteBuffer;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.BCDUtil;
import com.ylk.datamineservice.util.ByteUtil;
import com.ylk.datamineservice.util.ConstUtil;

public class ReqICStateMsg extends ReqMultiPackMsg{
	
	private int cardNo; 
	
	private long pwd;
	

	public ReqICStateMsg(BaseFrame firstRecFrame) {
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
				pwd = ByteUtil.getInt(pwdBytes,0);
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

	public long getPwd() {
		return pwd;
	}

	public void setPwd(long pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "模块号:"+moduleNo+",设备号:"+deviceNo+",卡号:"+cardNo+",密码:"+pwd;
	}

	
	
	
}
