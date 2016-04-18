package com.ylk.datamineservice.frame;


public class ReqStartChargeFrame extends BaseFrame {

	@Override
	public BaseFrame decoder() {
		super.superDecoder();
		packNum = 3;
		isSubPac = true;
		return this;
	}

}
