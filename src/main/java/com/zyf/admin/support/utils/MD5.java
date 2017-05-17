//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zyf.admin.support.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class MD5 {
	private static final Logger logger = Logger.getLogger("MD5");
	public static final String ALGORITHM = "MD5";

	public MD5() {
	}

	public static String toMD5(String plainText) {
		StringBuffer rlt = new StringBuffer();

		try {
			rlt.append(md5String(plainText.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException var3) {
			logger.severe(" CipherHelper toMD5 exception.");
			var3.printStackTrace();
		}

		return rlt.toString();
	}

	public static String getSignature(HashMap<String, String> params, String secret) {
		TreeMap sortedParams = new TreeMap(params);
		Set entrys = sortedParams.entrySet();
		StringBuilder basestring = new StringBuilder();
		Iterator var5 = entrys.iterator();

		while (var5.hasNext()) {
			Entry param = (Entry) var5.next();
			basestring.append((String) param.getKey()).append("=").append((String) param.getValue());
		}

		return getSignature(basestring.toString(), secret);
	}

	public static String getSignature(String sigstr, String secret) {
		StringBuilder basestring = new StringBuilder(sigstr);
		basestring.append("#");
		basestring.append(toMD5(secret));
		return toMD5(basestring.toString());
	}

	public static byte[] md5Raw(byte[] data) {
		Object md5buf = null;

		byte[] md5buf1;
		try {
			MessageDigest e = MessageDigest.getInstance("MD5");
			md5buf1 = e.digest(data);
		} catch (Exception var3) {
			md5buf1 = null;
			logger.severe("md5Raw error.");
			var3.printStackTrace();
		}

		return md5buf1;
	}

	public static String md5String(byte[] data) {
		String md5Str = "";

		try {
			MessageDigest e = MessageDigest.getInstance("MD5");
			byte[] buf = e.digest(data);

			for (int i = 0; i < buf.length; ++i) {
				md5Str = md5Str + Byte2Hex.byte2Hex(buf[i]);
			}
		} catch (Exception var5) {
			md5Str = null;
			logger.severe("md5String error.");
			var5.printStackTrace();
		}

		return md5Str;
	}

}

class Byte2Hex {
	public Byte2Hex() {
	}

	public static String byte2Hex(byte b) {
		String hex = Integer.toHexString(b);
		if (hex.length() > 2) {
			hex = hex.substring(hex.length() - 2);
		}

		while (hex.length() < 2) {
			hex = "0" + hex;
		}

		return hex;
	}

	public static String byte2Hex(byte[] bytes) {
		Formatter formatter = new Formatter();
		byte[] hash = bytes;
		int var3 = bytes.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			byte b = hash[var4];
			formatter.format("%02x", new Object[]{Byte.valueOf(b)});
		}

		String var6 = formatter.toString();
		formatter.close();
		return var6;
	}
}
