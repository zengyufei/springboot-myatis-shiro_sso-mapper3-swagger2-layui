package com.zyf.admin.support.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;

public class LoginInitPwdException extends AuthenticationException {

	private static final long serialVersionUID = 7939989495540145707L;

	public LoginInitPwdException(String msg) {
		super(msg);
	}

}
