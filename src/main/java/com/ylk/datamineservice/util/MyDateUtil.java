package com.ylk.datamineservice.util;

import java.util.Date;

import com.ylk.datamineservice.msg.ResTimeCheckMsg;

public class MyDateUtil {
	
	public static ResTimeCheckMsg getResTimeCheckMsg() {
		Date date = new Date();
		ResTimeCheckMsg msg = new ResTimeCheckMsg();
		String ymd = DateUtil.convertDate2String(date, DateUtil.DEFAULT_DATE_FORMAT);
		msg.setHour(date.getHours());
		msg.setMinute(date.getMinutes());
		msg.setSecond(date.getSeconds());
		String[] ymdArr = ymd.split("-");
		msg.setDay(Integer.valueOf(ymdArr[2]));
		msg.setMonth(Integer.valueOf(ymdArr[1]));
		msg.setYear(Integer.valueOf(ymdArr[0].substring(2)));
		return msg;
	}
	
	public static void main(String[] args) {
		System.out.println(getResTimeCheckMsg().toString());

	}

}
