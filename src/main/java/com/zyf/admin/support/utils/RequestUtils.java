package com.zyf.admin.support.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局 httpRequest 工具类
 * @className:
 * @classDesc:
 * @author：zengyufei
 * @createTime: 2017年01月05日 11:16
 */
public class RequestUtils {

	/**
	 * 线程安全获取 Request
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest getRequest(){
		ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
		return servletContainer.getRequest();
	}

	/**
	 * 线程安全获取 Response
	 * @return HttpServletResponse
	 */
	public static HttpServletResponse getResponse(){
		ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
		return servletContainer.getResponse();
	}
}
