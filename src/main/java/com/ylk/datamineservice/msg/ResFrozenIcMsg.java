package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.util.FrameTypeUtil;

public class ResFrozenIcMsg extends ResBaseMsg{
	private int frozenCode;

	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)frozenCode);
		dataBuf.flip();
		this.fType = FrameTypeUtil.RES_FROZEN_IC;
	}

	public int getFrozenCode() {
		return frozenCode;
	}

	public void setFrozenCode(int frozenCode) {
		this.frozenCode = frozenCode;
	}
	
	

}
