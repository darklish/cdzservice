package com.ylk.datamineservice.frame;

public class ReqIcAddFrame extends BaseFrame {

	@Override
	public BaseFrame decoder() {
		super.superDecoder();
		packNum = 3;
		isSubPac = true;
		return this;
	}

}
