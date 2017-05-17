package com.zyf.admin.support.shiro.realm;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

public class CustomPermssion implements Permission {

	private String strPermission;

	//private final static String strSplid = ":";

	//private final static String strAll = "*";

	public CustomPermssion(String strPermission) {
		this.strPermission = strPermission;
	}

	@Override
	public boolean implies(Permission p) {
		WildcardPermission wp = (WildcardPermission) p;
		WildcardPermission currentWp = new WildcardPermission(strPermission);
		return wp.implies(currentWp);
	}

}
