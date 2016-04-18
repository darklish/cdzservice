package com.ylk.datamineservice.frame;

public class ReqTimeCheckFrame extends BaseFrame {

	@Override
	public BaseFrame decoder() {
		super.superDecoder();
		packNum = 1;
		isSubPac = false;
		return this;
	}

}
