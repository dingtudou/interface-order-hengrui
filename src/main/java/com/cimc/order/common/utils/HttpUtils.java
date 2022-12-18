
package com.cimc.order.common.utils;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
@Slf4j
public class HttpUtils {
	/**
	 * 通过http协议发送post
	 * 
	 * @param url
	 * @param param
	 */
	public static Map<String, Object> sendPostHttp(String url, Map<String, Object> param) {
		RestTemplate restTemplate = new RestTemplate();
		// 编码为utf-8
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		HttpHeaders headers = new HttpHeaders();
		// 定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(param, headers);
		ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
		// 获取3方接口返回的数据通过entity.getBody();它返回的是一个字符串；
		return JSONUtils.jsonToMap(entity.getBody());
	}
	/**
	 * 通过http协议发送post
	 */
	public static Map<String, Object> sendPostHttp(String url, Map<String, Object> UrlParam, Map<String, Object> BodyParam) {
		// 处理参数
		StringBuffer paramsURL = new StringBuffer(url);
		// 字符数据最好encoding一下;这样一来，某些特殊字符才能传过去(如:flag的参数值就是“&”,不encoding的话,传不过去)
		if (UrlParam != null) {
			paramsURL.append("?" + Joiner.on("&").withKeyValueSeparator("=").join(UrlParam));
		}
		url =paramsURL.toString();

		RestTemplate restTemplate = new RestTemplate();
		// 编码为utf-8
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		HttpHeaders headers = new HttpHeaders();
		// 定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object> > request = new HttpEntity<>(BodyParam, headers);
		ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
		// 获取3方接口返回的数据通过entity.getBody();它返回的是一个字符串；
		return JSONUtils.jsonToMap(entity.getBody());
	}

}
