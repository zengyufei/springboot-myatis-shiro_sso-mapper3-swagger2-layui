package com.zyf.admin.support.filter;

import com.zyf.admin.support.shiro.entity.Token;
import com.zyf.admin.support.utils.CookieHelper;
import com.zyf.admin.support.utils.TokenHelper;
import com.zyf.commons.entity.sys.User;
import com.zyf.commons.service.sys.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.springframework.beans.BeanUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 构建 shiro 用户对象以及判断认证
 */
public class StatelessAuthcFilter extends AccessControlFilter {

	private int cookiesOutTime;

	public void setCookiesOutTime(int cookiesOutTime) {
		this.cookiesOutTime = cookiesOutTime;
	}

	private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
	                                  ServletResponse response,
	                                  Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
	                                 ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//这里填写你允许进行跨域的主机ip
		res.setHeader("Access-Control-Allow-Origin", "*");
		//允许的访问方法
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
		//Access-Control-Max-Age 用于 CORS 相关配置的缓存
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		/*
		   验证加密的 cookies ，解密成功则说明是我们出厂的，则进行下一步
		 */
		Token token = TokenHelper.getToken(req);
		if (token != null) {
			// 刷新 cookies 登录时长，30分钟内无操作，自动登出
			token.setTime(cookiesOutTime);
			CookieHelper.setSSOCookie(req, res, token, false);

			// 查询身份信息
			User user = userService.queryById(token.getId());
			User.DTO dto = new User.DTO();
			BeanUtils.copyProperties(user, dto);
			dto.setResourceIds(token.getResourceIds());

			//构造一个 web subject 给当前线程使用，当前线程 subject.getPrincipal() 就能得到 user
			WebDelegatingSubject subject = getWebSubject(request, response, dto);
			ThreadContext.bind(subject);
		} else {
			this.redirectToLogin(request, response); // 重定向到登录页面
			return false;
		}
		return true;
	}

	private WebDelegatingSubject getWebSubject(ServletRequest request, ServletResponse response, User.DTO user) {
		SimplePrincipalCollection principal = new SimplePrincipalCollection(user, user.getName());
		return new WebDelegatingSubject(principal, true,
		                                getHost(request), null, false,
		                                request, response, SecurityUtils.getSecurityManager());
	}

	private String getHost(ServletRequest request) {
		return request.getRemoteHost();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private String getUrl(ServletRequest request) {
		return ((HttpServletRequest) request).getRequestURI();
	}

}
