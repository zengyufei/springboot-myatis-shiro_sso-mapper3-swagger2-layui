package com.zyf.admin.support.filter;

import com.zyf.admin.support.shiro.entity.Token;
import com.zyf.admin.support.utils.TokenHelper;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 解密成功 登录页面自动跳转 index
 */
public class AuthcedFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		/*
		   验证加密的 cookies ，解密成功则说明是我们出厂的，则进行下一步
		 */
		Token token = TokenHelper.getToken(req);
		if (token != null) {
			if (getUrl(request).indexOf("login") > -1) {
				WebUtils.issueRedirect(request, response, "/pages/html/index.html");
			}
		}
		return true;
	}

	private String getUrl(ServletRequest request) {
		return ((HttpServletRequest) request).getRequestURI();
	}

}
