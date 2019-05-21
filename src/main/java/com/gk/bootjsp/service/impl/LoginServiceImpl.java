package com.gk.bootjsp.service.impl;

import org.springframework.stereotype.Service;

import com.gk.bootjsp.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Override
	public boolean validateUser(String userid, String password) {
		return userid.equalsIgnoreCase("administrator") && password.equalsIgnoreCase("admin");
	}

}
