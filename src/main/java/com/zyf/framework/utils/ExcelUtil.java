package com.zyf.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtil {

	/**
	 * 读取excel
	 */
	public static List<Map<String, Object>> uploadFile(String fileName) throws FileNotFoundException, IOException {
		return uploadFile(fileName, 0, 0);
	}

	/***
	 * @param sheetat
	 *            第几个页签
	 * @param top
	 *            第几行为开始标题行，从0开始算
	 */
	public static List<Map<String, Object>> uploadFile(String fileName, int sheetat, int top) throws FileNotFoundException, IOException {
		boolean isE2007 = false; // 判断是否是excel2007格式
		if (fileName.endsWith("xlsx"))
			isE2007 = true;

		InputStream input = new FileInputStream(fileName); // 建立输入流
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Workbook workbook = null;
		if (isE2007)
			workbook = new XSSFWorkbook(input);
		else
			workbook = new HSSFWorkbook(input);
		// 取出第一个工作表，索引是0
		Sheet sheet = workbook.getSheetAt(sheetat);
		Row topRow = sheet.getRow(top);
		int count = sheet.getLastRowNum();
		for (int i = top + 1; i <= count; i++) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			// 获取行对象
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			for (Iterator<Cell> it = row.cellIterator(); it.hasNext();) {
				Cell cell = it.next();
				if (topRow.getCell(cell.getColumnIndex()) == null) {
					continue;
				}
				String cellValue = StringUtils.trimToEmpty(topRow.getCell(cell.getColumnIndex()).getStringCellValue());
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							map.put(cellValue,  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString());
						} else {
							map.put(cellValue, cell.getNumericCellValue());
						}
					} else {
						NumberFormat nf = NumberFormat.getInstance();
						nf.setGroupingUsed(false);// true时的格式：1,234,567,890
						String cv = nf.format(cell.getNumericCellValue());
						map.put(cellValue, cv);
					}
				} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					map.put(cellValue, cell.getBooleanCellValue());
				} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					String cvalue = StringUtils.isBlank(cell.getStringCellValue()) ? " " : cell.getStringCellValue();
					map.put(cellValue, cvalue);
				}

			}
			if (map.isEmpty()) {
				continue;
			}
			list.add(map);
		}
		return list;
	}
	
	public static void writeExcel2007(String excelPath, String sheetName, Map<Integer, String> cellMap, List<Map<String, String>> rowList){
		XSSFWorkbook wb = new XSSFWorkbook(); 
		XSSFSheet sheet = wb.createSheet();
		XSSFCellStyle style = wb.createCellStyle();
		XSSFRow row = sheet.createRow(0);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
		for (Map.Entry<Integer, String> entry : cellMap.entrySet()) {
			XSSFCell cell = row.createCell(entry.getKey());
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(style);
			sheet.autoSizeColumn(entry.getKey());
		}

		XSSFCell hcell = null;
		XSSFCellStyle cs = wb.createCellStyle();
		for (int i = 0; i < rowList.size(); i++) {
			Map<String, String> map = rowList.get(i);
			row = sheet.createRow(i + 1);
			for (Map.Entry<Integer, String> entry : cellMap.entrySet()) {
				sheet.autoSizeColumn(entry.getKey());
				String v = StringUtils.trimToEmpty(map.get(entry.getValue()));
				String regex = "[0-9]+(.[0-9]+)?";
				hcell = row.createCell(entry.getKey());
				if (match(regex, v)) {
					if (v.length() >= 11) {
						hcell.setCellValue(v);
					} else {
						if (v.contains(".")) {
							cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
							hcell.setCellValue(Double.parseDouble(v));
						} else {
							cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
							hcell.setCellValue(Long.parseLong(v));
						}
					}
					hcell.setCellStyle(cs);
				} else {
					hcell.setCellValue(StringUtils.trimToEmpty(v));
				}
			}
		}

		try {
			FileOutputStream fout = new FileOutputStream(excelPath);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeExcel2003(String excelPath, String sheetName, Map<Integer, String> cellMap, List<Map<String, String>> rowList) {
		HSSFWorkbook wb = writeHSSFWork(sheetName, cellMap, rowList);
		try {
			FileOutputStream fout = new FileOutputStream(excelPath);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HSSFWorkbook writeHSSFWork(String sheetName, Map<Integer, String> cellMap, List<Map<String, String>> rowList){
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
		for (Map.Entry<Integer, String> entry : cellMap.entrySet()) {
			HSSFCell cell = row.createCell(entry.getKey());
			cell.setCellValue(entry.getValue());
			cell.setCellStyle(style);
			sheet.autoSizeColumn(entry.getKey());
		}

		HSSFCell hcell = null;
		HSSFCellStyle cs = wb.createCellStyle();
		for (int i = 0; i < rowList.size(); i++) {
			Map<String, String> map = rowList.get(i);
			row = sheet.createRow(i + 1);
			for (Map.Entry<Integer, String> entry : cellMap.entrySet()) {
				sheet.autoSizeColumn(entry.getKey());
				String v = StringUtils.trimToEmpty(map.get(entry.getValue()));
				String regex = "[0-9]+(.[0-9]+)?";
				hcell = row.createCell(entry.getKey());
				if (match(regex, v)) {
					if (v.length() >= 11) {
						hcell.setCellValue(v);
					} else {
						if (v.contains(".")) {
							cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
							hcell.setCellValue(Double.parseDouble(v));
						} else {
							cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
							hcell.setCellValue(Long.parseLong(v));
						}
					}
					hcell.setCellStyle(cs);
				} else {
					hcell.setCellValue(StringUtils.trimToEmpty(v));
				}
			}
		}
		return wb;
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}


}
