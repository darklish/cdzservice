package com.ylk.datamineservice.frame;

public class ReqOfflineDataFrame extends BaseFrame {

	@Override
	public BaseFrame decoder() {
		super.superDecoder();
		packNum = 3;
		isSubPac = true;
		return this;
	}

}
