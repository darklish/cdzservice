package com.ylk.datamineservice.frame;


/**
 * @author liyc
 * 开始充电帧请求
 * 暂时不做处理
 *
 */
public class ReqBeginChargeFrame extends BaseFrame {

	@Override
	public BaseFrame decoder() {
		super.superDecoder();
		packNum = 1;
		isSubPac = false;
		return this;
	}

	
}
