package com.kc.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName: PropertiesUtil  
 * @Description: TODO properties文件工具类
 * @author jason  
 * @date 2018-1-12
 */
public class PropertiesUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties properties = null;

	static {
		String COMMON_PROPERTIES_FILE = "config.properties";
		properties = new Properties();
		Resource resource = new ClassPathResource(COMMON_PROPERTIES_FILE);
		try {
			properties.load(resource.getInputStream());
		} catch (IOException e) {
			logger.error("load properties file error:", e);
		}
	}

	/**
	 * @Title: getProperty  
	 * @Description: TODO 获取配置文件数据
	 * @param key
	 * @return String
	 * @throws
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @Title: getPropertyInteger  
	 * @Description: TODO 获取配置文件数据  
	 * @param key
	 * @return Integer
	 * @throws
	 */
	public static Integer getPropertyInteger(String key) {
		return Integer.parseInt( properties.getProperty(key));
	}
	
	private PropertiesUtil() {

	}

}
