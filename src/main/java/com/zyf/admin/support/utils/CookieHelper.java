package com.zyf.admin.support.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zyf.admin.support.shiro.entity.SSOConfig;
import com.zyf.admin.support.shiro.entity.Token;
import com.zyf.framework.utils.RandomUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class CookieHelper {
	private static final Logger logger = Logger.getLogger("CookieHelper");

	private CookieHelper() {
	}

	private static void authJSESSIONID(HttpServletRequest request, String value) {
		request.getSession().invalidate();
		request.getSession().setAttribute("KISSO-" + value, Boolean.valueOf(true));
	}

	public static Cookie findCookieByName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		} else {
			for (int i = 0; i < cookies.length; ++i) {
				if (cookies[i].getName().equals(cookieName)) {
					return cookies[i];
				}
			}
			return null;
		}
	}

	public static void clearCookieByName(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	public static void clearAllCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; ++i) {
			clearCookie(response, cookies[i].getName(), cookies[i].getPath());
		}
	}

	public static void clearAllCookie(HttpServletRequest request, HttpServletResponse response, String domain, String path) {
		Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; ++i) {
			clearCookie(response, cookies[i].getName(), domain, path);
		}

		logger.info("clearAllCookie in  domain " + domain);
	}

	public static boolean clearCookieByName(HttpServletRequest request, HttpServletResponse response, String cookieName, String domain, String path) {
		boolean result = false;
		Cookie ck = findCookieByName(request, cookieName);
		if (ck != null) {
			result = clearCookie(response, cookieName, domain, path);
		}

		return result;
	}

	private static boolean clearCookie(HttpServletResponse response, String cookieName, String path) {
		boolean result = false;

		try {
			Cookie e = new Cookie(cookieName, "");
			e.setMaxAge(0);
			e.setPath(path);
			response.addCookie(e);
			logger.fine("clear cookie " + cookieName);
			result = true;
		} catch (Exception var6) {
			logger.severe("clear cookie " + cookieName + " is exception!\n" + var6.toString());
		}

		return result;
	}

	private static boolean clearCookie(HttpServletResponse response, String cookieName, String domain, String path) {
		boolean result = false;

		try {
			Cookie e = new Cookie(cookieName, "");
			e.setMaxAge(0);
			e.setDomain(domain);
			e.setPath(path);
			response.addCookie(e);
			logger.fine("clear cookie " + cookieName);
			result = true;
		} catch (Exception var6) {
			logger.severe("clear cookie " + cookieName + " is exception!\n" + var6.toString());
		}

		return result;
	}

	private static void addCookie(HttpServletResponse response, String domain, String path, String name, String value, int maxAge, boolean httpOnly, boolean secured) {
		Cookie cookie = new Cookie(name, value);
		if (domain != null && !"".equals(domain)) {
			cookie.setDomain(domain);
		}
		if (path != null && !"".equals(path)) {
			cookie.setPath(path);
		}
		cookie.setMaxAge(maxAge);
		if (secured) {
			cookie.setSecure(secured);
		}

		if (httpOnly) {
			addHttpOnlyCookie(response, cookie);
		} else {
			response.addCookie(cookie);
		}

	}

	private static void addHttpOnlyCookie(HttpServletResponse response, Cookie cookie) {
		if (cookie != null) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			int maxAge = cookie.getMaxAge();
			String path = cookie.getPath();
			String domain = cookie.getDomain();
			boolean isSecure = cookie.getSecure();
			StringBuffer sf = new StringBuffer();
			sf.append(cookieName + "=" + cookieValue + ";");
			if (maxAge >= 0) {
				sf.append("Max-Age=" + cookie.getMaxAge() + ";");
			}

			if (domain != null) {
				sf.append("domain=" + domain + ";");
			}

			if (path != null) {
				sf.append("path=" + path + ";");
			}

			if (isSecure) {
				sf.append("secure;HTTPOnly;");
			} else {
				sf.append("HTTPOnly;");
			}

			response.addHeader("Set-Cookie", sf.toString());
		}
	}

	public static void setSSOCookie(HttpServletRequest request, HttpServletResponse response, Token token) {
		setSSOCookie(request, response, token, true);
	}

	public static void setSSOCookie(HttpServletRequest request, HttpServletResponse response, Token token, boolean httpOnly) {
		try {
			Cookie e = generateCookie(request, token);
			if (httpOnly) {
				CookieHelper.addHttpOnlyCookie(response, e);
			} else {
				response.addCookie(e);
			}
		} catch (Exception var12) {
			logger.severe("set HTTPOnly cookie createAUID is exception! " + var12.toString());
		}
	}


	private static Cookie generateCookie(HttpServletRequest request, Token token) {
		try {
			Cookie e = new Cookie(SSOConfig.get().getCookiesName(), encryptCookie(request, token));
			if(SSOConfig.get().getDomain() != null && !"".equals(SSOConfig.get().getDomain())){
				e.setDomain(SSOConfig.get().getDomain());
			}
			if (SSOConfig.get().getMaxAge() >= 0) {
				e.setMaxAge(SSOConfig.get().getMaxAge());
			}
			if (SSOConfig.get().getPath() == null || "".equals(SSOConfig.get().getPath())) {
				e.setPath("/");
			}else{
				e.setPath(SSOConfig.get().getPath());
			}
			return e;
		} catch (Exception var8) {
			logger.severe("generateCookie is exception!" + var8.toString());
			return null;
		}
	}

	protected static String encryptCookie(HttpServletRequest request, Token token) throws Exception {
		if(token == null) {
			throw new NullPointerException(" Token not for null.");
		} else {
			String jt = JSONObject.toJSONString(token, SerializerFeature.WriteDateUseDateFormat);
			StringBuffer sf = new StringBuffer();
			sf.append(jt);
			sf.append("#");
			if(checkCookieBrowser()) {
				sf.append(BrowserUtils.getUserAgent(request, jt));
			} else {
				sf.append(RandomUtil.getCharacterAndNumber(8));
			}
			return EncryptHelper.encrypt(sf.toString());
		}
	}

	private static boolean checkCookieBrowser() {
		return false;
	}
}
