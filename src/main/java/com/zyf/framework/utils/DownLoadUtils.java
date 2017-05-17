package com.zyf.framework.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownLoadUtils {

	public static void download(HttpServletResponse response, String fileAliaName, String path) {
		String fileName = null;
		try {
			fileName = new String(fileAliaName.getBytes("gbk"), "iso-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			inputStream = new FileInputStream(new File(path));
			os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
