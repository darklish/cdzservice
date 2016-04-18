package com.ylk.datamineservice.util;

public class CdzStateUtil {
	
	public static final int CDZ_NORMAL = 1;//正常在线
	
	public static final int CDZ_FALT = 3;//故障
	
	public static final int CDZ_LOCK = 2;//锁停
	
	public static final int CDZ_OFFLINE = 4;//离线
	
	public static final int DEVICE_NORMAL = 0x00;//离线
	
	public static final int DEVICE_LOCK = 0x10;//锁停
	
	public static final int DEVICE_FALT = 0xF0;//故障
	
	
	
	public static final int DEVICE_WORK_WAIT = 0x00;//待机状态
	
	public static final int DEVICE_WORK_CONFIG = 0x01;//已刷卡等待充电配置状态
	
	public static final int DEVICE_WORK_CHARGE = 0x02;//正在充电状态
	
	public static final int DEVICE_WORK_OVER = 0x03;//充电完成等待结算
	
	public static final int DEVICE_WORK_PAUSE = 0x04;//工作异常，充电已暂停状态
	
	public static final int DEVICE_WORK_UPLOAD = 0x05;//上次离线有数据待上次状态
	
	public static final int DEVICE_WORK_BOOK = 0x06;//预约充电等待状态
	
	
	public static final int CDZ_WORK_WAIT = 1;//待机状态
	
	public static final int CDZ_WORK_CONFIG = 2;//已刷卡等待充电配置状态
	
	public static final int CDZ_WORK_CHARGE = 3;//正在充电状态
	
	public static final int CDZ_WORK_OVER = 4;//充电完成等待结算
	
	public static final int CDZ_WORK_PAUSE = 5;//工作异常，充电已暂停状态
	
	public static final int CDZ_WORK_UPLOAD = 6;//上次离线有数据待上次状态
	
	public static final int CDZ_WORK_BOOK = 7;//预约充电等待状态
	
	


}
