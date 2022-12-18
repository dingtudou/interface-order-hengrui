package com.cimc.order.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IPUtils {
	public static final long INT_TO_LONG_MASK = 0x00000000FFFFFFFFL;

	public static String longToIp(long i) {
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
	}

	public static Long ipToLong(String ipAddress) {
		long result = 0;
		try {
			String[] ipAddressInArray = ipAddress.split("\\.");
			for (int i = 3; i >= 0; i--) {
				long ip = Long.parseLong(ipAddressInArray[3 - i]);
				result |= ip << (i * 8);

			}
		} catch (Exception e) {
		}
		return result;
	}

	public static long exchangeBigLittle(long i) {
		if (i < 0) {
			i = i & INT_TO_LONG_MASK;
		}
		return ((i & 0xFF) << 24) | ((i & 0xFF00) << 8) | ((i >> 8) & 0xFF00) | (i >> 24);
	}

	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

	@SuppressWarnings("unused")
	public static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<String, String>();
		Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String name = entry.getKey();
			String[] values = entry.getValue();
			int valLen = values.length;

			if (valLen == 1) {
				retMap.put(name, values[0]);
			} else if (valLen > 1) {
				StringBuilder sb = new StringBuilder();
				for (String val : values) {
					sb.append(",").append(val);
				}
				retMap.put(name, sb.toString().substring(1));
			} else {
				retMap.put(name, "");
			}
		}
		return retMap;
	}

	public static void main(String[] args) {
		System.out.println(ipToLong("114.113.2a22.66"));
	}
}
