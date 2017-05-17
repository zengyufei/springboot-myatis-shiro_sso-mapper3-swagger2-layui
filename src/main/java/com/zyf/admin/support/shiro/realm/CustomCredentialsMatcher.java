package com.zyf.admin.support.shiro.realm;

import com.zyf.framework.utils.MD5;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
		String tokenCredentials = MD5.encode(usertoken.getUsername()+String.valueOf(usertoken.getPassword()));
		String accountCredentials = (String)info.getCredentials();
		return equals(tokenCredentials, accountCredentials);
	}

	/*public static void main(String[] args) {
		//admin admin f6fdffe48c908deb0f4c3bd36c032e72
		//admin 123456 a66abb5684c45962d887564f08346e8d
		System.out.println(MD5.encode("admin" + "admin"));
	}*/
}