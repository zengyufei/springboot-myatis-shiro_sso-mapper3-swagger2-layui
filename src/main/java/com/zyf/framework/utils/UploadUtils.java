package com.zyf.framework.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class UploadUtils {

	public static String upload(MultipartFile file){
		String dir = "tmp/";
		File tmp = new File(dir);
		if(!tmp.exists()){
			tmp.mkdir();
		}
		String finalFilePath = dir + file.getOriginalFilename();
		if(upload(file, finalFilePath)){
			return finalFilePath;
		}
		return null;
	}

	public static boolean upload(MultipartFile file, String filePath){
		File finalFile = new File(filePath);
		try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(finalFile))){
			out.write(file.getBytes());
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<String> uploadReadLine(MultipartFile file){
		List<String> list = Lists.newArrayList();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), Charset.defaultCharset()))) {
			String line;
			while (StringUtils.isNotBlank((line=br.readLine()))) {
				list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
