package com.zyf.admin.support.shiro.realm;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends AuthenticationFilter {

	private String unauthorizedUrl;


	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}


	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String head = httpRequest.getHeader("X-Requested-With");
		boolean isAjax = false;
		if (!org.apache.commons.lang3.StringUtils.isBlank(head)) {
			if (head.equalsIgnoreCase("XMLHttpRequest")) {
				isAjax = true;
			}
		}

		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {
			if (!isAjax) {
				saveRequestAndRedirectToLogin(request, response);
			} else {
				String loginfailure = "/pages/rbac/loginFailure.jsp";
				WebUtils.issueRedirect(request, response, loginfailure);
				//redirectToLogin(request, response);
			}
		} else {
			String unauthorizedUrl = getUnauthorizedUrl();
			if (StringUtils.hasText(unauthorizedUrl)) {
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			} else {
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		return false;
	}

}
