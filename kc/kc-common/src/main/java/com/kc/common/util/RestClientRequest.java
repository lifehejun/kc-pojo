package com.kc.common.util;

import net.sf.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: RestClientRequest  
 * @Description: TODO Rest客户端请求
 * @author jason  
 * @date 2018-4-23
 */
public class RestClientRequest {
	
	

	/**
	 * @Title: restSubmit  
	 * @Description: rest post提交 
	 * @param url
	 * @param sPara
	 * @return LinkedHashMap
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedHashMap restSubmit(String url, Map<String, String> sPara) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType
				.parseMediaType("application/json; charset=utf-8"));
		headers.set("Accept-Charset", "UTF-8");
		HttpEntity entity = new HttpEntity(sPara, headers);
		ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST,
				entity, Map.class);
		LinkedHashMap responseMap = (LinkedHashMap) response.getBody();
		return responseMap;
	}
	  

	/**
	 * @Title: restSubmitJson  
	 * @Description: rest json 请求
	 * @param url
	 * @param sPara
	 * @return LinkedHashMap
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedHashMap restSubmitJson(String url, Map<String, String> sPara) {
		JSONObject jsonMap = JSONObject.fromObject(sPara);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType
				.parseMediaType("application/json; charset=utf-8"));
		headers.set("Accept-Charset", "UTF-8");
		HttpEntity entity = new HttpEntity(jsonMap, headers);
		ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST,
				entity, Map.class);
		LinkedHashMap responseMap = (LinkedHashMap) response.getBody();
		return responseMap;
	}
}
