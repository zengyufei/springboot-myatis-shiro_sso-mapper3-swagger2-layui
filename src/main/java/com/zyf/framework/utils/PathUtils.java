package com.zyf.framework.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class PathUtils {

	public static String getPath(String fileName) {
		try {
			return new ClassPathResource("/" + fileName).getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getPath("runman.sql"));
	}

	public static void getFiles(String fileName) {
		File file = new File(fileName);
		if (file.isDirectory()) {
			String[] list = file.list();
			for (String string : list) {
				getFiles(string);
			}
		}
		System.out.println(file.getName());
	}
}
