package com.zyf.admin.support.utils;

import com.zyf.admin.support.shiro.entity.SSOConfig;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.logging.Logger;

public class EncryptHelper{

	private static final Logger logger = Logger.getLogger("EncryptHelper");
	private static final String UTF8 = "UTF-8";

	public static String encrypt(String value) throws Exception {
		byte[] b = UrlBase64.encode(encrypt(value.getBytes()));
		return new String(b, UTF8);
	}

	public static String decrypt(String value) throws Exception {
		byte[] b = decrypt(UrlBase64.decode(value.getBytes()));
		return new String(b, UTF8);
	}

	private static Key toKey(String encryptType, String strKey) throws Exception {
		byte[] keyByte = MD5.md5Raw(strKey.getBytes(UTF8));
		if("DES" == SSOConfig.get().getEncrypt()) {
			DESKeySpec dks = new DESKeySpec(keyByte);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptType);
			SecretKey secretKey = keyFactory.generateSecret(dks);
			return secretKey;
		} else {
			return new SecretKeySpec(keyByte, encryptType);
		}
	}

	public static byte[] decrypt(byte[] data) {
		try {
			Cipher e = Cipher.getInstance(SSOConfig.get().getEncrypt());
			e.init(2, toKey(SSOConfig.get().getEncrypt(), SSOConfig.get().getKey()));
			return e.doFinal(data);
		} catch (Exception var5) {
			logger.severe("Encrypt setKey is exception.");
			throw new RuntimeException();
		}
	}

	public static byte[] encrypt(byte[] data) {
		try {
			Cipher e = Cipher.getInstance(SSOConfig.get().getEncrypt());
			e.init(1, toKey(SSOConfig.get().getEncrypt(), SSOConfig.get().getKey()));
			return e.doFinal(data);
		} catch (Exception var5) {
			logger.severe("Encrypt setKey is exception.");
			throw new RuntimeException();
		}
	}

}


