/*
package com.zyf.admin.web.error;

import com.alibaba.fastjson.JSONObject;
import com.zyf.framework.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * 统一异常处理
 * Created by zengyufei on 2016/11/25.
 *//*

@ControllerAdvice
public class ErrorController {

	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e.getMsg());
		mav.addObject("url", req.getRequestURL().toString());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

	@ExceptionHandler(value = MyException.class)
	@ResponseBody
	public String jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
		Map<String, String> error = new HashMap<>();
		error.put("msg", e.getMsg());
		error.put("code", "400");
		error.put("data", "Some Data");
		error.put("url", req.getRequestURL().toString());
		return JSONObject.toJSONString(error);
	}
}
*/
