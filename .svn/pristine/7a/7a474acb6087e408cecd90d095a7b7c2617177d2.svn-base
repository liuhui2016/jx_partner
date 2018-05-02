package com.game.smvc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.smvc.core.util.DateFormat;

public class DateUtil {
	static Logger logger = LogManager.getLogger(DateUtil.class);
	
	private static final SimpleDateFormat ym = new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat timesatmp = new SimpleDateFormat("yyyyMMddHH:mm:ss");
	
	/*
	 * 第三方
	 * */
	public static String getDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	public static String getDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	public static String getDate(DateFormat format) {
		return new SimpleDateFormat(format.toString()).format(new Date());
	}

	public static String getAmountDate(int field, int amount, DateFormat format) {
		return getAmountDate(field, amount, format.toString());
	}
	
	public static String getAmountDate(int field, int amount, String format) {
		String strDate = new SimpleDateFormat(format).format(getAmountDate(field, amount));
		return strDate;
	}
	
	public static String getAmountDate(Date date, int field, int amount, DateFormat format) {
		return getAmountDate(date, field, amount, format.toString());
	}
	
	public static String getAmountDate(Date date, int field, int amount, String format) {
		String strDate = new SimpleDateFormat(format).format(getAmountDate(date, field, amount));
		return strDate;
	}
	
	public static Date getAmountDate(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(field, amount);
		return cal.getTime();
	}
	
	public static Date getAmountDate(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static String dateToString(String format, String strDate) {
		String date = null;
		try {
			date = new SimpleDateFormat(format).format(
				new SimpleDateFormat(DateFormat.Y_M_D$H_M_S.toString()).parse(strDate)
			);
		} catch (ParseException ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return date;
	}
	
	public static String formatDate(DateFormat format, Date date) {
		return formatDate(format.toString(), date);
	}
	
	public static String formatDate(String format, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		try {
			return formatter.format(date);
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return null;
	}
	
	public static String formatDate(String format, String strDate) {
		String formatDate = strDate;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		try {
			formatDate = formatter.format(formatter.parse(strDate));
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return formatDate;
	}

	public static Date stringToDate(String strDate, String format) {
		java.util.Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(strDate);
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return date;
	}

	public static Date stringToDateTime(String strDate) {
		java.util.Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.Y_M_D$H_M_S.toString());
			date = simpleDateFormat.parse(strDate);
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
		}
		return date;
	}

	public static String getCurrMonthStartDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String strDate = format.format(new Date()) + "-01";
		return strDate;
	}

	public static String getDiffDate(Calendar cal, int diffDays) {
		cal.add(Calendar.DAY_OF_MONTH, diffDays);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strDate = format.format(cal.getTime());
		return strDate;
	}

	public static String getDiffDateYMD(Calendar cal, int diffDays) {
		cal.add(Calendar.DAY_OF_MONTH, diffDays);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String strDate = format.format(cal.getTime());
		return strDate;
	}

	public static String getDiffMonth(Calendar cal, int diffMonths) {
		cal.add(Calendar.MONTH, diffMonths);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strDate = format.format(cal.getTime());
		return strDate;
	}

	public static String getDiffDateBySeconds(Calendar cal, int diffSends) {
		cal.add(Calendar.SECOND, diffSends);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strDate = format.format(cal.getTime());
		return strDate;
	}

	public static long getIntervalSeconds(String strDate1, String strDate2) throws ParseException {
		long interval = getIntervalSeconds(stringToDateTime(strDate1), stringToDateTime(strDate2));

		return interval;
	}

	public static long getIntervalSeconds(Date date1, Date date2) {
		// 默认为毫秒，除以1000是为了转换成秒
		long interval = (date2.getTime() - date1.getTime()) / 1000; // 秒

		return interval;
	}

	public static String getIntervalHMS(Date date1, Date date2) {
		// 默认为毫秒，除以1000是为了转换成秒
		long interval = (date2.getTime() - date1.getTime()) / 1000; // 秒

		return getIntervalHMS(interval);
	}

	public static String getIntervalHMS(long secondInterval) {
		long interval = secondInterval; // 秒

		long day = interval / (24 * 3600);// 天
		long hour = interval % (24 * 3600) / 3600;// 小时
		long minute = interval % 3600 / 60;// 分钟
		long second = interval % 60;// 秒
		hour = (day > 0) ? day * 24 + hour : hour;

		String sHour = (hour < 10) ? ("0" + hour) : String.valueOf(hour);
		String sMinute = (minute < 10) ? ("0" + minute) : String.valueOf(minute);
		String sSecond = (second < 10) ? ("0" + second) : String.valueOf(second);
		return sHour + ":" + sMinute + ":" + sSecond;
	}

	public static long getIntervalMillSeconds(Date date1, Date date2) {
		long interval = date2.getTime() - date1.getTime(); // 毫秒
		return interval;
	}

	public static int getIntervalMinutes(Date date1, Date date2) {
		// 默认为毫秒，除以1000是为了转换成秒
		long interval = (date2.getTime() - date1.getTime()) / 1000; // 秒
		return getIntervalMinutes(interval);
	}

	public static int getIntervalMinutes(long intervalSecond) {
		long interval = intervalSecond; // 秒
		int minutes = (int) Math.ceil(interval / 60.0);// 分钟
		// long seconds=interval%60;//秒
		// 不够一分钟，算一分钟
		// if(minutes==0 && seconds>0) minutes = 1;

		return minutes;
	}

	public static int getIntervalHour(Date date1, Date date2) {
		// 默认为毫秒，除以1000是为了转换成秒
		long interval = (date2.getTime() - date1.getTime()) / 1000; // 秒
		int hours = (int) (interval % 3600);// 小时
		return hours;
	}

	public static Calendar stringToCalendar(String strDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.stringToDateTime(strDate));

		return cal;
	}
	/*
	 * 自己写的
	 * */
	public static Date getCurrentTime() throws ParseException {
		return time.parse(date2String(new Date()));
	}

	public static String date2String(Date dt) {
		return time.format(dt);
	}
	public static String date2Str(Date dt) {
		return date.format(dt);
	}

	public static Date string2Date(String str) throws ParseException {
		return date.parse(str);
	}
	/**
	 * 获取当前月份
	 * @return
	 */
	public static String getYearMonth(){
		return ym.format(new Date());
	}
	/**
	 * 获取当前的时间戳
	 * @return
	 */
	public static String getTimeStamp(){
		return timesatmp.format(new Date()).replaceAll(":", "");
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(string2Date("1991-01-01"));
//		System.out.println(date2String(new Date()));
		System.out.println(getYearMonth());
//		System.out.println(getTimeStamp());
	}
}
