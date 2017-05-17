package com.zyf.framework.utils;

import org.springframework.util.StreamUtils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 流处理工具类
 *
 * @author L.cm
 * @email: 596392912@qq.com
 * @site: http://www.dreamlu.net
 * @date 2015年4月20日下午8:56:43
 */
public class IOUtils extends StreamUtils {

	/**
	 * closeQuietly
	 *
	 * @param closeable
	 */
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
}
