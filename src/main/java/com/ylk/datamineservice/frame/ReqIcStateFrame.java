package com.ylk.datamineservice.frame;


public class ReqIcStateFrame extends BaseFrame {

	@Override
	public BaseFrame decoder() {
		super.superDecoder();
		packNum = 2;
		isSubPac = true;
		return this;
	}

}
