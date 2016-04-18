package com.ylk.datamineservice.util;

public class ChargeUtil {
	public static final int CHARGE_ALLOW = 0x01;//允许充电
	
	public static final int CHARGE_FORBID_NOT_FUNDS = 0xF0;//金额不足
	
	public static final int CHARGE_FORBID_DATA_ERR = 0xF1;// 数据错误
	
	public static final int CHARGE_FORBID_CON_ERR = 0xF2;// 条件不足
	public static final int CHARGE_FORBID_REVERSE = 0xF3;// 预留
	
	public static final int END_CHARGE_CODE = 0x01;//停止请求
	
	public static final int END_CHARGE_ALLOW = 0x01;// 允许
	
	public static final int END_CHARGE_FORBID = 0xF0;// 禁止
	
	public static final int PAUSE_CHARGE_CODE = 0x01;//停止请求
	
	public static final int PAUSE_CHARGE_ALLOW = 0x01;// 允许
	
	public static final int PAUSE_CHARGE_FORBID = 0xF0;// 禁止
	
	public static final int PAUSE_RESULT_ING = 0x01;// 正在充电
	
	public static final int PAUSE_RESULT_OVER = 0x00;// 充电完成
	
	public static final int CHARGE_UPLOAD_SUCCESS = 0x01;// 上传成功
	
	public static final int CHARGE_UPLOAD_FAIL = 0xF0;// 上传失败

}
