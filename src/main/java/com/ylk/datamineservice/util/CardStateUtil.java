package com.ylk.datamineservice.util;

public class CardStateUtil {
	
	public static final int CARD_NORMAL = 0x01;
	
	public static final int CARD_UNKNOW = 0xf0;
	
	public static final int ERROR_PWD = 0xf1;
	
	public static final int CARD_FROZEN = 0xf2;
	
	public static final int CARD_LOSS = 0xf3;
	
	public static final int CARD_USING = 0xf4;
	
	public static final int RESERVE = 0;
	
	public static final int DB_CARD_STATE_UNINIT = 0;//未出售
	public static final int DB_CARD_STATE_NORMAL = 1;//正常
	public static final int DB_CARD_STATE_LOSS = 2;// 挂失
	public static final int DB_CARD_STATE_FROZEN = 3;//冻结
	public static final int DB_CARD_STATE_CHARGE = 6;//正在充电

}
