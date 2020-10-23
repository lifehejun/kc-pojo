package com.kc.common.web.resolver;

import org.springframework.web.servlet.view.InternalResourceView;

import java.io.File;
import java.util.Locale;

/**
 * @ClassName: HtmlResourceView  
 * @Description: TODO 静态资源视图
 * @author jason  
 * @date 2017-8-21
 */
public class StaticResourceView extends InternalResourceView {

	@Override
	public boolean checkResource(Locale locale) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(this.getServletContext().getRealPath("/") + getUrl());  
		return file.exists();
	}
	
	
	

}
