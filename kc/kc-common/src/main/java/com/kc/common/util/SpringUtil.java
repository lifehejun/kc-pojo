package com.kc.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringUtil  
 * @Description: TODO spring 工具类
 * @author jason  
 * @date 2017-8-18
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @Title: getBean  
	 * @Description: TODO 获取容器bean
	 * @param name
	 * @throws BeansException
	 * @return T
	 * @throws
	 */
	@SuppressWarnings({ "unchecked"})
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}
}