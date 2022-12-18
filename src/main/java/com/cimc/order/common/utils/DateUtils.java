package com.cimc.order.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	/**
	 * 时间格式(yyyy-MM-dd)
	 */
	public final static String DATE_PATTERN_DAY = "yyyy-MM-dd";
	/**
	 * 时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public final static String DATE_PATTERN_MS= "yyyy-MM-dd HH:mm:ss";

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	public static String formatDay(Date date ) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN_DAY);
			return df.format(date);
		}
		return null;
	}
	public static String formatMs(Date date) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN_MS);
			return df.format(date);
		}
		return null;
	}
	public static Date parseDateDay(String date) throws ParseException {
		return parseDate(date,DATE_PATTERN_DAY);
	}
	public static Date parseDateMs(String date) throws ParseException {
		return parseDate(date,DATE_PATTERN_MS);
	}
}
