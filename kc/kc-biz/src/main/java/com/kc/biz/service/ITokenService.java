package com.kc.biz.service;

import com.kc.biz.bean.UserBean;

public interface ITokenService {

	String getToken(UserBean user);
	
	boolean refresh(String phone,String token);
	
	void logout(String token);



}
