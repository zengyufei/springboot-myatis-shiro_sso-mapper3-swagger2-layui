package com.zyf.admin.support.config.factory;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

	private String regex_ymd = "\\d{4}-(([1-9])|(0[1-9])|(1[0-2]))-(([1-9])|(0[1-9])|([12]\\d)|(3[01]))";
	private String regex_ymdhms = regex_ymd + "\\s(00|0|(0[0-9])|(1\\d)|(2[0-3])):(00|0|(0[0-9])|([1-5]\\d)):(00|0|(0[1-9])|([1-5]\\d))";
	private String format_ymdhms = "yyyy-MM-dd HH:mm:ss";

	@Override
	public LocalDateTime convert(String s) {
		if (Pattern.compile(regex_ymdhms).matcher(s).matches()) {
			return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(format_ymdhms));
		}
		return null;
	}
}
