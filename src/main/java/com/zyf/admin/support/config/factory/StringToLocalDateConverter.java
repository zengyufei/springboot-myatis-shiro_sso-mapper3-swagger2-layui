package com.zyf.admin.support.config.factory;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

	private String regex_ymd = "\\d{4}-(([1-9])|(0[1-9])|(1[0-2]))-(([1-9])|(0[1-9])|([12]\\d)|(3[01]))";
	private String format_ymd = "yyyy-MM-dd";

	@Override
	public LocalDate convert(String s) {
		if (Pattern.compile(regex_ymd).matcher(s).matches()) {
			return LocalDate.parse(s,DateTimeFormatter.ofPattern(format_ymd));
		}
		return null;
	}
}
