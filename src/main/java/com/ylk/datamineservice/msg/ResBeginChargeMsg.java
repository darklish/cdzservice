package com.ylk.datamineservice.msg;

import com.ylk.datamineservice.util.FrameTypeUtil;

public class ResBeginChargeMsg extends ResBaseMsg{
	
	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)1);
		dataBuf.flip();
		this.fType = FrameTypeUtil.RES_BEGIN_CHARGE;
	}

}
