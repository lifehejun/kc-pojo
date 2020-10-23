package com.kc.common.util.api;

import com.kc.common.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GsonUtils  
 * @Description: TODO Gson工具类 
 * @author jason  
 * @date 2018-1-20
 */
public class GsonUtils {
	
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	
	/**
	 * 
	 * @Title: convertList   
	 * @Description: JSON字符串转List集合 
	 * @param json JSON字符串对象
	 * @param token 数据类型令牌
	 * @return List<T> 返回类型     
	 * @throws
	 */
	public static <T> List<T> convertList(String json, TypeToken<List<T>> token) {
		if (StringUtil.isBlank(json)) {
			return new ArrayList<T>();
		}
		return gson.fromJson(json, token.getType());
	}
	
	/**
	 * @Title: convertObj  
	 * @Description: JSON字符串转对象   
	 * @param json
	 * @param token
	 * @return
	 * @return T
	 * @throws
	 */
	public static <T> T convertObj(String json, TypeToken<T> token) {
		if (StringUtil.isBlank(json)) {
			return null;
		}
		return gson.fromJson(json, token.getType());
	} 
	
	/**
	 * @Title: convertObj   
	 * @Description: JSON字符串转对象   
	 * @param json JSON字符串
	 * @param cls 对象class类型
	 * @return T 返回类型      
	 * @throws
	 */
	public static <T> T convertObj(String json, Class<T> cls) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		return gson.fromJson(json, cls);
	}

	/**
	 * @Title: toJson   
	 * @Description: Object对象转JSON
	 * @param obj Object对象
	 * @return String 返回类型      
	 * @throws
	 */
	public static String toJson(Object obj) {
		if (obj == null) {
			return "";
		}
		return gson.toJson(obj);
	}

	/**
	 * @Title: getJsonObjectAsString   
	 * @Description: 通过name获取JsonObject里面的String类型值 
	 * @param jsonObject  JsonObject对象
	 * @param name 键
	 * @return String 返回类型      
	 * @throws
	 */
	public static String getJsonObjectAsString(JsonObject jsonObject, String name) {
		if (jsonObject == null || StringUtils.isBlank(name)) {
			return null;
		}
		JsonElement jsonElement = jsonObject.get(name);
		return (jsonElement == null) ? null : jsonElement.getAsString();
	}

	/**
	 * @Title: getJsonObjectChild   
	 * @Description: 通过name获取JsonObject对象里面的 子集
	 * @param jsonObject JsonObject对象
	 * @param name 键
	 * @return JsonObject 返回类型      
	 * @throws
	 */
	public static JsonObject getJsonObjectChild(JsonObject jsonObject, String name) {
		if (jsonObject == null || StringUtils.isBlank(name)) {
			return null;
		}
		JsonElement jsonElement = jsonObject.get(name);
		return (jsonElement == null) ? null : jsonElement.getAsJsonObject();
	}

	/**
	 * @Title: getJsonObjectAsBoolean   
	 * @Description: 判断键名称是否存在
	 * @param jsonObject JsonObject对象
	 * @param name 键名称
	 * @return boolean 返回类型      
	 * @throws
	 */
	public static boolean isExistsName(JsonObject jsonObject, String name) {
		if (jsonObject == null || StringUtils.isBlank(name)) {
			return false;
		}
		JsonElement jsonElement = jsonObject.get(name);
		return (jsonElement == null) ? false : jsonElement.getAsBoolean();
	}
}
