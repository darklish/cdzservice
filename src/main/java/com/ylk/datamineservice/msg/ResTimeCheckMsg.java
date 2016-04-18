package com.ylk.datamineservice.msg;

public class ResTimeCheckMsg extends ResBaseMsg{
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;

	@Override
	public void initBuffer() {
		dataBuf.clear();
		dataBuf.put((byte)year);
		dataBuf.put((byte)month);
		dataBuf.put((byte)day);
		dataBuf.put((byte)hour);
		dataBuf.put((byte)minute);
		dataBuf.put((byte)second);
		dataBuf.flip();
		this.fType = 0x72;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("年:");
		sb.append(year);
		sb.append(",月:");
		sb.append(month);
		sb.append(",日:");
		sb.append(day);
		sb.append(",时:");
		sb.append(hour);
		sb.append(",分");
		sb.append(minute);
		sb.append(",秒");
		sb.append(second);
		return sb.toString();
	}

}
