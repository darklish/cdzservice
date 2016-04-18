package com.ylk.datamineservice.util;

public class FrameTypeUtil {
	public static final int REQ_IC_STATE = 0x01;// 请求ic卡状态
	public static final int RES_IC_STATE = 0x41;// 向设备应答IC卡验证结果
	public static final int REQ_START_CHARGE = 0x02;// 向服务请求开始充电
	public static final int RES_START_CHARGE = 0x42;// 向设备应答是否开始充电
	public static final int REQ_END_CHARGE = 0x03;//向服务器请求结束充电
	public static final int RES_END_CHARGE = 0x43;// 向设备应答充电结果
	public static final int REQ_PAUSE_CHARGE = 0x04;//向服务器请求暂停充电
	public static final int RES_PAUSE_CHARGE = 0x44;//向设备应答暂停充电结果
	public static final int REQ_FROZEN_IC = 0x05;// 向服务器请求冻结ic卡
	public static final int RES_FROZEN_IC = 0x45;// 向设备应答IC冻结结果
	public static final int REQ_OFFLINE_DATA = 0x06;// 向服务器上传上次离线后数据
	public static final int RES_OFFLINE_DATA_ = 0x46;// 向设备应答离线数据上传
	public static final int REQ_BEGIN_CHARGE = 0x07;// 开始充电
	public static final int RES_BEGIN_CHARGE = 0x47;// 向设备应答离线数据上传
	public static final int REQ_DEVICE_STATE = 0x00;// 向设备请求是否在线，心跳包
	public static final int RES_DEVICE_STATE = 0x40;// 向服务器返回状态
	public static final int REQ_DEVICE_IC = 0x10;// 向设备请求当前卡号
	public static final int RES_DEVICE_IC = 0x50;// 向服务返回当前设备卡号
	public static final int REQ_CONSUM_E = 0x11;// 查询设备当前消耗电度
	public static final int RES_CONSUM_E = 0x51;// 设备应答服务器电度查询
	public static final int REQ_DEVICE_QUERY_PWD = 0x12;// 查询设备操作员和管理员密码
	public static final int RES_DEVICE_QUERY_PWD = 0x52;// 设备回复操作员和管理员密码查询
	public static final int REQ_DEVICE_SETUP_PWD = 0x13;// 设置设备操作员和管理员密码
	public static final int RES_DEVICE_SETUP_PWD = 0x53;// 设备回复操作员和管理员密码设置
	public static final int REQ_DEVICE_LOCK = 0x14;// 锁停当前设备
	public static final int RES_DEVICE_LOCK = 0x54;// 设备回复锁停操作
	public static final int REQ_DEVICE_UNLOCK_START = 0x15;// 解锁并启动当前设备
	public static final int RES_DEVICE_UNLOCK_START = 0x55;// 回复解锁设备
	public static final int REQ_IC_ADD = 0x31;// 向服务器请求添加卡号
	public static final int RES_IC_ADD = 0x71;// 服务器回复添加卡号
	public static final int REQ_TIME_CHECK = 0x32;// 向服务器请求时间校验
	public static final int RES_TIME_CHECK = 0x72;// 回复设备时间校验请求
	
	
	
	

}
