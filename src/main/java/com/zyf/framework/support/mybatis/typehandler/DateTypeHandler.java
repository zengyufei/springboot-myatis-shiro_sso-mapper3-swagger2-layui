package com.zyf.framework.support.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.Date;

public class DateTypeHandler extends BaseTypeHandler<Date> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {

		if (parameter != null) {
			ps.setTimestamp(i, new Timestamp(parameter.getTime()));
		} else {
			ps.setTimestamp(i, null);
		}
	}

	@Override
	public Date getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp time = rs.getTimestamp(columnName);
		if (rs.wasNull()) {
			return null;
		}
		if (time != null) {
			return new Date(time.getTime());
		}
		return null;
	}

	@Override
	public Date getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp time = cs.getTimestamp(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		if (time != null) {
			return new Date(time.getTime());
		}
		return null;
	}

	@Override
	public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp time = rs.getTimestamp(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		if (time != null) {
			return new Date(time.getTime());
		}
		return null;
	}
}
