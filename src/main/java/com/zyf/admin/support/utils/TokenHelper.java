
package com.zyf.admin.support.utils;

import com.alibaba.fastjson.JSONObject;
import com.zyf.admin.support.shiro.entity.Token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class TokenHelper {
	protected final static Logger logger = Logger.getLogger("TokenHelper");

	public TokenHelper() {
	}

	public static <T extends Token> T attrToken(HttpServletRequest request) {
		return (T) request.getAttribute("ssoToken");
	}

	public static Token getToken(HttpServletRequest request) {
		Token tk = checkIp(request, cacheToken(request));
		return tk;
	}

	private static Token cacheToken(HttpServletRequest request) {
		Token cookieToken = getTokenFromCookie(request);
		if (cookieToken == null) {
			return null;
		}
		/*String cacheToken = (String) EhCacheUtils.getCacheElement(cookieToken.getId());
		if (cacheToken == null) {
			logger.fine("cacheToken token is null.");
			return null;
		}*/

		return cookieToken;
	}

	protected static Token getToken(HttpServletRequest request, String cookieName) {
		String jsonToken = getJsonToken(request, cookieName);
		if (jsonToken != null && !"".equals(jsonToken)) {
			return JSONObject.parseObject(jsonToken, Token.class);
		} else {
			logger.fine("jsonToken is null.");
			return null;
		}
	}

	private static String getJsonToken(HttpServletRequest request, String cookieName) {
		Cookie uid = CookieHelper.findCookieByName(request, cookieName);
		if (uid != null) {
			String jsonToken = uid.getValue();
			String[] tokenAttr = new String[2];

			try {
				jsonToken = EncryptHelper.decrypt(jsonToken);
				tokenAttr = jsonToken.split("#");
			} catch (Exception var8) {
				logger.severe("jsonToken decrypt error, may be fake login. IP = " + IpHelper.getIpAddr(request));
			}

			if (!checkCookieBrowser()) {
				return tokenAttr[0];
			}

			if (BrowserUtils.isLegalUserAgent(request, tokenAttr[0], tokenAttr[1])) {
				return tokenAttr[0];
			}

			logger.severe("SSOHelper getToken, find Browser is illegal.");
		}

		return null;
	}

	private static boolean checkCookieBrowser() {
		return false;
	}

	private static Token checkIp(HttpServletRequest request, Token token) {
		if (checkip()) {
			String ip = IpHelper.getIpAddr(request);
			if (token != null && ip != null && !ip.equals(token.getIp())) {
				logger.info(String.format("ip inconsistent! return token null, token userIp:%s, reqIp:%s", new Object[]{token.getIp(), ip}));
				return null;
			}
		}

		return token;
	}

	private static boolean checkip() {
		return false;
	}

	private static Token getTokenFromCookie(HttpServletRequest request) {
		Token tk = attrToken(request);
		if (tk == null) {
			tk = getToken(request, "uid");
		}

		return tk;
	}

}
