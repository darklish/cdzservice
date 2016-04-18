package com.ylk.datamineservice.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 时间、日期处理公共类
 *
 * @date 2012-4-5
 *
 */
public class DateUtil {
	/**
	 * 默认的日期格式
	 */
	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static String FOLDER_FORMATE = "yyyyMMdd";

	public static String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String HOUR_DATE_FORMAT = "HH:mm:ss";

	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat sFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String DATE_SEPARATOR = "-/";

	/** 作日期分析之用 */
	static StringTokenizer sToken;

	/**
	 * 取得当前日期
	 * 
	 * @return Date 当前日期
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 返回当前日期对应的默认格式的字符串
	 * 
	 * @return String 当前日期对应的字符串
	 */
	public static String getCurrentStringDate() {
		return convertDate2String(getCurrentDate(), DEFAULT_DATE_FORMAT);
	}

	/**
	 * 返回当前日期对应的指定格式的字符串
	 * 
	 * @param dateFormat
	 *            - 日期格式
	 * @return String 当前日期对应的字符串
	 */
	public static String getCurrentStringDate(String dateFormat) {
		return convertDate2String(getCurrentDate(), dateFormat);
	}

	/**
	 * 将日期转换成指定格式的字符串
	 * 
	 * @param date
	 *            - 要转换的日期
	 * @param dateFormat
	 *            - 日期格式
	 * @return String 日期对应的字符串
	 */
	public static String convertDate2String(Date date, String dateFormat) {
		SimpleDateFormat sdf = null;
		if (dateFormat != null && !dateFormat.equals("")) {
			try {
				sdf = new SimpleDateFormat(dateFormat);
			} catch (Exception e) {
				e.printStackTrace();
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			}
		} else {
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}
		return sdf.format(date);
	}

	/**
	 * 将日期转换成指定格式的字符串
	 * 
	 * @param date
	 *            - 要转换的日期
	 * @param dateFormat
	 *            - 日期格式
	 * @return String 日期对应的字符串
	 */
	public static String convertDate2String(Date date) {
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return sdf.format(date);
	}

	/**
	 * 将字符串转换成日期
	 * 
	 * @param stringDate
	 *            - 要转换的字符串格式的日期
	 * @return Date 字符串对应的日期
	 */
	public static Date convertString2Date(String stringDate) {
		return convertString2Date(stringDate, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 将字符串转换成日期
	 * 
	 * @param stringDate
	 *            - 要转换的字符串格式的日期
	 * @param dateFormat
	 *            - 要转换的字符串对应的日期格式
	 * @return Date 字符串对应的日期
	 */
	public static Date convertString2Date(String stringDate, String dateFormat) {
		SimpleDateFormat sdf = null;
		if (dateFormat != null && !dateFormat.equals("")) {
			try {
				sdf = new SimpleDateFormat(dateFormat);
			} catch (Exception e) {
				e.printStackTrace();
				sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			}
		} else {
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		}
		try {
			return sdf.parse(stringDate);
		} catch (ParseException pe) {
			pe.printStackTrace();
			return new Date(System.currentTimeMillis());
		}
	}

	/**
	 * 将一种格式的日期字符串转换成默认格式的日期字符串
	 * 
	 * @param oldDate
	 *            - 要格式化的日期字符串
	 * @param oldFormat
	 *            - 要格式化的日期的格式
	 * @return String 格式化后的日期字符串
	 */
	public static String formatStringDate(String oldStringDate, String oldFormat) {
		return convertDate2String(convertString2Date(oldStringDate, oldFormat),
				DEFAULT_DATE_FORMAT);
	}

	/**
	 * 将一种格式的日期字符串转换成另一种格式的日期字符串
	 * 
	 * @param oldDate
	 *            - 要格式化的日期字符串
	 * @param oldFormat
	 *            - 要格式化的日期的格式
	 * @param newFormat
	 *            - 格式化后的日期的格式
	 * @return String 格式化后的日期字符串
	 */
	public static String formatStringDate(String oldStringDate,
			String oldFormat, String newFormat) {
		return convertDate2String(convertString2Date(oldStringDate, oldFormat),
				newFormat);
	}

	/**
	 * 根据年份和月份判断该月有几天
	 * 
	 * @param year
	 *            - 年份
	 * @param month
	 *            - 月份
	 * @return int
	 */
	public static int days(int year, int month) {
		int total = 30;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			total = 31;
			break;
		case 2:
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				total = 29;
			else
				total = 28;
			break;
		default:
			break;
		}
		return total;
	}

	private static final String datePattern1 = "\\d{4}\\d{2}\\d{2}";

	private static final String datePattern3 = "^((((1[6-9]|[2-9]\\d)\\d{2})(0?[13578]|1[02])(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})(0?[13456789]|1[012])(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})0?2(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))0?2-29))$";

	/**
	 * 判断是否是有效的日期 1.位数8位 2.年份大于1900 3.格式yyyyMMdd
	 * */
	public static boolean isValidDate(String sDate) {
		if ((sDate != null)) {
			if (sDate.trim().length() != 8)
				return false;
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern3);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	public static Timestamp getTimestamp(String strDate) {
		return Timestamp.valueOf(strDate);
	}

	/**
	 * 得到当前日期的前后日期 +为后 -为前
	 * 
	 * @param day_i
	 * @return
	 */
	public static final String getBefDateString(String currentDate, int day_i,
			String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(currentDate);
			Calendar day = Calendar.getInstance();
			day.setTime(date);
			day.add(Calendar.DATE, day_i);
			return sdf.format(day.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static long getRuntime(long begintime) {
		return ((System.currentTimeMillis() - begintime)) / 1000;
	}
	

	/**
	 * 取当前日期 前后 offset天的日期
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Date getDate(int offset) {
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		// cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
		cal.add(Calendar.DAY_OF_MONTH, offset);// 取当前日期的后一天.
		String sdate = convertDate2String(cal.getTime(),DEFAULT_DATE_FORMAT);
		return convertString2Date(sdate);
	}

	/**
	 * 取当前小时数 24制
	 * 
	 * @return
	 */
	public static int getCurrentHour24() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取当前系统分钟数
	 * 
	 * @return
	 */
	public static int getCurrentMin() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}


	public static int getDateBetweenByFormat(Date start, Date end) {
		if (start == null || end == null)
			return 0;
		return cutTwoDateToDay(start, end);// DateUtils.truncatedCompareTo(start,
											// end, field);

	}
	
	public static int cutTwoDateToDay(Date a, Date b) {
		SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Calendar calendar = Calendar.getInstance();
		int theday = 0;
		try {
			Date beginDate = format.parse(format.format(a));
			Date endDate = format.parse(format.format(b));

			calendar.setTime(beginDate);
			long begin = calendar.getTimeInMillis();
			calendar.setTime(endDate);
			long end = calendar.getTimeInMillis();

			theday = (int) ((end - begin) / (86400000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return theday;
	}

	/**
	 * if now 2012-05-23 17:50 ~ 2012-05-23 17:59 understand ?
	 * 
	 * @return 2012-05-23 17:50:00|2012-05-23 17:59:59
	 */
	public static Date[] getCurrTenMin() {
		String min = String.valueOf(getCurrentMin());
		String date = convertDate2String(new Date(), "yyyy-MM-dd HH");
		String start = ":";
		String end = ":";
		if (min.length() == 1) {
			start = date + start + "00:00";
			end = date + end + "09:59";
		} else {
			start = date + start + min.charAt(0) + "0:00";
			end = date + end + min.charAt(0) + "9:59";
		}
		Date[] result = { convertString2Date(start, FULL_DATE_FORMAT),
				convertString2Date(end, FULL_DATE_FORMAT) };
		return result;
	}

	public static int random(int low, int heigh) {
		return low + (int) ((heigh - low) * new Random().nextDouble());
	}

	public static void main(String[] args) {
		// String min = String.valueOf(getCurrentMin());
		//
		// String date = convertDate2String(new Date(), "yyyy-MM-dd HH");
		// String start = ":";
		// String end = ":";
		// if (min.length() == 1) {
		// start = date + start + "00:00";
		// end = date + end + "09:59";
		// } else {
		// start = date + start + min.charAt(0) + "0:00";
		// end = date + end + min.charAt(0) + "9:59";
		// }
		// Date endDate = convertString2Date(end, FULL_DATE_FORMAT);
		// long init = (endDate.getTime() - System.currentTimeMillis()) / 1000 /
		// 60;
		// System.err.println(start + "|" + end);
		// System.err.println(init);

		// Date startDate = convertString2Date("2012-8-17 17:16:39",
		// FULL_DATE_FORMAT);
		// Date endDate = new Date();

		// System.err.println(compareDateByFormat(startDate, endDate,
		// Calendar.DAY_OF_MONTH));
		// System.err.println(cutTwoDateToDay(startDate, endDate));
		// System.err.println(checkTimeByHour("17:35:55", "17:35:59"));
		// for (int i = 0; i < 100; i++) {
		// System.err.println(random(1320, 3120));
		// }
		System.err.println(getCurrentHour24());
	}

}