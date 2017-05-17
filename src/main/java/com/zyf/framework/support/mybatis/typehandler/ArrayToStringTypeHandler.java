package com.zyf.framework.support.mybatis.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes({JdbcType.VARCHAR})
public class ArrayToStringTypeHandler extends BaseTypeHandler<String[]> {
	
	private final String array_sign = ",";

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, StringUtils.join(parameter, array_sign));
	}

	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if(rs.wasNull()){
			return null;
		}
		if(rs.getObject(columnName) == null){
			return null;
		}
		return StringUtils.split(rs.getString(columnName), array_sign);
	}

	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if(rs.wasNull()){
			return null;
		}
		if(rs.getObject(columnIndex) == null){
			return null;
		}
		return StringUtils.split(rs.getString(columnIndex), array_sign);
	}

	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if(cs.wasNull()){
			return null;
		}
		if(cs.getObject(columnIndex) == null){
			return null;
		}
		return StringUtils.split(cs.getString(columnIndex), array_sign);
	}

}
