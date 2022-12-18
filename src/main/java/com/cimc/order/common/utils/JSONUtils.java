package com.cimc.order.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtils  {

	static Gson gson = new GsonBuilder().create();

	/**
	 * Bean对象转JSON
	 *
	 * @param object
	 * @param dataFormatString
	 * @return
	 */
	public static String beanToJson(Object object, String dataFormatString) {
		if (object != null) {
			if (StringUtils.isEmpty(dataFormatString)) {
				return JSONObject.toJSONString(object);
			}
			return JSON.toJSONStringWithDateFormat(object, dataFormatString);
		} else {
			return null;
		}
	}

	/**
	 * Bean对象转JSON
	 *
	 * @param object
	 * @return
	 */
	public static String beanToJson(Object object) {
		if (object != null) {
			return JSON.toJSONString(object);
		} else {
			return null;
		}
	}

	/**
	 * Bean对象转JSON
	 *
	 * @param object
	 * @return
	 */
	public static String beanToJsonignorecase(Object object) {
		if (object != null) {
			return gson.toJson(object);
		} else {
			return null;
		}
	}

	/**
	 * String转JSON字符串
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public static String stringToJsonByFastjson(String key, String value) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>(16);
		map.put(key, value);
		return beanToJson(map, null);
	}

	/**
	 * 将json字符串转换成对象
	 *
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object jsonToBean(String json, Object clazz) {
		if (StringUtils.isEmpty(json) || clazz == null) {
			return null;
		}
		return JSON.parseObject(json, clazz.getClass());
	}

	/**
	 * json字符串转map
	 *
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonToMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		return JSON.parseObject(json, Map.class);
	}

	/**
	 * json字符串转list<map>
	 *
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> jsonToListMap(String json) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		return (List<Map<String, Object>>) JSONArray.parse(json);
	}

	/**
	 * 获取输入JSON流
	 *
	 * @return
	 */
	public static JSONObject getJSONParam(HttpServletRequest request) {
		JSONObject jsonParam = null;
		try {
			// 获取输入流
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			// 写入数据到Stringbuilder
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = streamReader.readLine()) != null) {
				sb.append(line);
			}
			jsonParam = JSONObject.parseObject(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonParam;
	}

	/***
	 ** 上报字符串解析
	 **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Optional<List<String>> splitCommon(String valueOf, String split) {
		if (StringUtils.isBlank(valueOf)) {
			return Optional.absent();
		}
		return Optional.of(new ArrayList(Splitter.on(split).splitToList(valueOf)));
	}

	/***
	 ** 获取key
	 *
	 * @param valueOf     源数组
	 * @param split   起始位置
	 * @param key 0时 剩余全部
	 **/
	public static Optional<String> getKey(String valueOf, String split, int key) {
		Optional<List<String>> list = splitCommon(valueOf, split);
		if (!list.isPresent() || list.get().size() <= key) {
			return Optional.absent();
		}
		return Optional.of(list.get().get(key));
	}

	/***
	 ** 上报字符串解析
	 **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Optional<List<String>> splitCommon(String valueOf) {
		if (StringUtils.isBlank(valueOf)) {
			return Optional.absent();
		}
		return Optional.of(new ArrayList(Splitter.on(",").splitToList(valueOf)));
	}
	/***
	 ** 上报属性解析
	 **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Optional<List<String>> splitColumn(String valueOf) {
		if (StringUtils.isBlank(valueOf)) {
			return Optional.absent();
		}
		return Optional.of(new ArrayList(Splitter.on(getsplit(valueOf)).splitToList(valueOf)));
	}

	/***
	 ** 上报属性解析
	 **/
	public static String getsplit(String valueOf) {
		String split = "_";
		if (valueOf.indexOf(".") != -1) {
			split = ".";
		} else if (valueOf.indexOf(",") != -1) {
			split = ",";
		}
		return split;
	}

	/***
	 ** 截取数组
	 *
	 * @param src     源数组
	 * @param begin   起始位置
	 * @param count   个数 0时 剩余全部
	 **/
	public static Optional<List<String>> subArray(List<String> src, int begin, int count) {
		if (src == null || src.isEmpty()) {
			return Optional.absent();
		}
		if (begin > src.size()) {
			begin = src.size();
		}
		if (count == 0) {
			count = src.size();
		} else if ((begin + count) > src.size()) {
			count = src.size();
		} else {
			count += begin;
		}
		List<String> list = src.subList(begin, count);
		if (list == null || list.isEmpty()) {
			return Optional.absent();
		}
		return Optional.of(list);
	}

	/***
	 ** 获取key
	 *
	 * @param src     源数组
	 **/
	public static Optional<String> getKey(List<String> src, int key) {
		if (src.isEmpty()) {
			return Optional.absent();
		} else if (src.size() - 1 < key) {
			return Optional.absent();
		}
		return Optional.of(src.get(key));
	}

}
