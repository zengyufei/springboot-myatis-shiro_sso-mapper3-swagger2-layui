package com.zyf.framework.utils;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Function : 文件压缩成zip
 *
 * @author : lqf
 * @Date : 2015-12-15
 */
public class ZipUtils {

	private ZipUtils(){}

	/**
	 * @param oldFile     待打包的文件夹或文件
	 * @param newFile 打包下载的文件名
	 * @throws IOException
	 */
	public static void zip(File oldFile, File newFile) throws IOException {
		OutputStream outputStream = new FileOutputStream(newFile);
		ZipCompressor zipCompressor = new ZipUtils.ZipCompressor(outputStream);
		zipCompressor.compress(oldFile);
		outputStream.flush();
		try {
			outputStream.close();
		} catch (Throwable ignored) {
		}
	}

	public static void main(String[] args) throws IOException {
		String path = PathUtils.getPath("static/ehcache/testCache.xml");
		assert path != null;
		ZipUtils.zip(new File(path), new File("d:/ss.zip"));
	}


	static class ZipCompressor {
		static final int BUFFER = 8192;
		private OutputStream outputStream;
		public ZipCompressor(OutputStream outputStream) {
			this.outputStream=outputStream;
		}
		public void compress(File file) {
			if (!file.exists())
				throw new RuntimeException(file.getAbsolutePath() + "不存在！");
			try {
				CheckedOutputStream cos = new CheckedOutputStream(outputStream,
						new CRC32());
				ZipOutputStream out = new ZipOutputStream(cos);
				String basedir = "";
				compress(file, out, basedir);
				out.close();//必须关闭,这样才会写入zip的结束信息,否则zip文件不完整.若想继续写入,可重写outputStream.close()方法
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		private void compress(File file, ZipOutputStream out, String basedir) {
			//判断是目录还是文件
			if (file.isDirectory()) {
				this.compressDirectory(file, out, basedir);
			} else {
				this.compressFile(file, out, basedir);
			}
		}
		// 压缩一个目录
		private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
			if (!dir.exists())
				return;
			File[] files = dir.listFiles();
			if(files != null)
				for (File file : files) {
					/** 递归 */
					compress(file, out, basedir + dir.getName() + "/");
				}
		}
		//压缩一个文件
		private void compressFile(File file, ZipOutputStream out, String basedir) {
			if (!file.exists()) {
				return;
			}
			try {
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(file));
				ZipEntry entry = new ZipEntry(basedir + file.getName());
				out.putNextEntry(entry);
				int count;
				byte data[] = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				bis.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}