package com.ylk.datamineservice.msg;

import io.netty.util.Timeout;

import java.nio.ByteBuffer;

import com.ylk.datamineservice.frame.BaseFrame;
import com.ylk.datamineservice.util.ConstUtil;

public abstract class ReqMultiPackMsg extends ReqBaseMsg {
	
	
	public ReqMultiPackMsg(BaseFrame firstRecFrame) {
		super(firstRecFrame);
	}
	private Timeout timeout;

	public Timeout getTimeout() {
		return timeout;
	}

	public void setTimeout(Timeout timeout) {
		this.timeout = timeout;
	}

	

}
