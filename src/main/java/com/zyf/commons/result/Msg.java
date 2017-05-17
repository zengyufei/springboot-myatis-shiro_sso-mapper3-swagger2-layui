package com.zyf.commons.result;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回消息封装类
 *
 * @author zengyufei
 *         2017年03月23日 14:21
 */
public class Msg implements Serializable {

	public final static String DEFAULT_CODE = "401";

	@Getter
	@Setter(AccessLevel.PRIVATE)
	private String code;
	@Getter
	private String error;
	@Getter
	@Setter(AccessLevel.PRIVATE)
	private String msg;
	@Getter
	@Setter
	private Object data;

	@JSONField(serialize = false, deserialize = false)
	private Message message = new Message();

	public Msg() {
	}

	public static Msg getNewMsg() {
		return new Msg();
	}

	public static Msg getNewMsg(String code, String message) {
		Msg msg = new Msg();
		msg.setCode(code);
		msg.setMsg(message);
		return msg;
	}

	private void setThisError(String errorMsg){
		this.error = errorMsg;
	}

	public void setError(String errorMeg) {
		withError(DEFAULT_CODE, errorMeg);
	}

	public void setError(String code, String errorMeg) {
		withError(code, errorMeg);
	}

	private void setDefaultError(String errorMeg) {
		this.message.errorList.put(DEFAULT_CODE, errorMeg);
	}

	public Msg withError(String errorMeg) {
		return withError(DEFAULT_CODE, errorMeg);
	}

	public Msg withError(String code, String errorMeg) {
		setDefaultError(errorMeg);
		this.setCode(code);
		this.setThisError(errorMeg);
		return this;
	}

	@JSONField(name = "success")
	public boolean isSuccess() {
		return !hasError();
	}

	public void setSuccess(String msg) {
		this.msg = msg;
	}

	public Msg withSuccess(String msg) {
		this.msg = msg;
		return this;
	}

	@JSONField(serialize = false, deserialize = false)
	public Map<String, String> getErrorList() {
		return this.message.getErrorList();
	}

	@JSONField(serialize = false, deserialize = false)
	public boolean hasError() {
		return this.message.hasError();
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}

	static class Message {

		@Getter
		private Map<String, String> errorList = new HashMap<>();

		public Map<String, String> put(String code, String errorMeg) {
			errorList.put(code, errorMeg);
			return errorList;
		}

		public boolean hasError() {
			return !errorList.isEmpty();
		}
	}

}
