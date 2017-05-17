package com.zyf.admin.web;

import com.zyf.admin.support.shiro.entity.Token;
import com.zyf.admin.support.utils.CookieHelper;
import com.zyf.commons.entity.sys.Role;
import com.zyf.commons.entity.sys.User;
import com.zyf.commons.result.Msg;
import com.zyf.commons.service.sys.RoleService;
import com.zyf.framework.utils.ValidUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Controller
@Api(value = "登录", description = "登录一些跳转")
public class LoginController {

	private final static String LOGIN_URL = "/pages/html/login.html";
	private final static String INDEX_URL = "/pages/html/index.html";

	@Autowired
	private RoleService roleService;

	@ApiOperation(value = "登录", notes = "根据传入的账户和密码进行shiro登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "账户", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
	})
	@ApiResponse(code = 401, message = "登录失败")
	@RequestMapping(value = "/loginIn", method = RequestMethod.POST)
	@ResponseBody
	public String loginIn(String account, String password,
	                      HttpServletRequest request,
	                      HttpServletResponse response) {
		log.info("用户：[{}] 进行登录操作", account);
		Msg msg = ValidUtils.validMsg(User.class, "account,password", account, password);
		if(msg.hasError()){
			return msg.toString();
		}
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(new UsernamePasswordToken(account, password));
		} catch (IncorrectCredentialsException e) {
			msg.setError("账号密码错误，请重新登录");
		} catch (AuthenticationException e) {
			msg.setError("账号密码错误，请重新登录");
		} catch (Exception e) {
			msg.setError("login fail, try again");
		}finally {
			if(!msg.getErrorList().isEmpty())
				return msg.toString();
		}
		//登录成功逻辑
		User user = (User) subject.getPrincipal();
		setCookies(request, response, user, true);
		msg.setData(getRedirectUrl(request));
		return msg.toString();
	}

	@ApiOperation(value = "退出登录", notes = "跳转到登录页面，如果已经登录则退出登录")
	@ApiResponse(code = 401, message = "登出失败")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.getPrincipal() != null) {
			User.DTO user = (User.DTO) subject.getPrincipal();
			log.info("用户：[{}] 登出。", user.getAccount());
			// 清除 cookies，不清除将永久免登陆
			CookieHelper.clearAllCookie(request, response);
		}
		return "redirect:" + LOGIN_URL;
	}

	@ApiOperation(value = "查询用户名", notes = "")
	@ApiResponse(code = 401, message = "查询失败")
	@RequestMapping(value = "/queryName", method = RequestMethod.GET)
	@ResponseBody
	public String queryName() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.getPrincipal() != null) {
			User.DTO user = (User.DTO) subject.getPrincipal();
			return user.getName();
		}
		return "用户名未知";
	}

	@ApiOperation(value = "一键登录", notes = "开发阶段使用")
	@ApiResponse(code = 401, message = "登录失败")
	@RequestMapping(value = "/onceLoginIn", method = {RequestMethod.GET, RequestMethod.POST})
	public String onceLoginIn(HttpServletRequest request, HttpServletResponse response) {
		return loginIn("admin", "admin", request, response);
	}

	@RequestMapping(value = "/loginInSSO")
	@ResponseBody
	public String loginInSSO(String account, String password,
	                         String redirectUrl,
	                      HttpServletRequest request,
	                      HttpServletResponse response) {
		log.info("用户：[{}] 进行登录操作", account);
		Msg msg = ValidUtils.validMsg(User.class, "account,password", account, password);
		if(msg.hasError()){
			return msg.toString();
		}

		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(new UsernamePasswordToken(account, password));
		} catch (IncorrectCredentialsException e) {
			msg.setError("账号密码错误，请重新登录");
		} catch (AuthenticationException e) {
			msg.setError("账号密码错误，请重新登录");
		} catch (Exception e) {
			msg.setError("login fail, try again");
		}finally {
			if(!msg.getErrorList().isEmpty())
				return msg.toString();
		}
		//登录成功逻辑
		User user = (User) subject.getPrincipal();
		setCookies(request, response, user, false);
		msg.setData(redirectUrl);
		return msg.toString();
	}


	private String getRedirectUrl(HttpServletRequest request) {
		SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
		String url = INDEX_URL;
		if (savedRequest != null && StringUtils.isNotBlank(savedRequest.getRequestUrl())
				&& !"/".equals(savedRequest.getRequestUrl()) && savedRequest.getRequestUrl().indexOf("login.html") < 0) {
			url = savedRequest.getRequestUrl().replaceFirst(request.getContextPath(), "");
		}
		return url;
	}

	private void setCookies(HttpServletRequest request, HttpServletResponse response, User user, boolean httpOnly) {
		Token tk = new Token();
		tk.setId(user.getId());
		tk.setRoleId(user.getRoleId());
		Role role = roleService.query(user.getRoleId());
		tk.setResourceIds(Arrays.asList(role.getResourceId().split(",")));
		LocalDateTime now = LocalDateTime.now();
		tk.setCreateTime(now);
		tk.setUpdateTime(now);
		CookieHelper.setSSOCookie(request, response, tk, httpOnly);
	}
}
