package com.zyf.framework.support.mybatis.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@MappedJdbcTypes({JdbcType.VARCHAR})
public class ListStringTypeHandler extends BaseTypeHandler<List<String>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
		if(parameter == null || parameter.size() == 0){
			ps.setString(i, null);
		}else{
			ps.setString(i, StringUtils.join(parameter,","));
		}
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if(rs.wasNull()){
			return null;
		}
		if(rs.getObject(columnName) == null){
			return null;
		}
		return Arrays.asList(StringUtils.split(rs.getString(columnName),","));
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if(rs.wasNull()){
			return null;
		}
		if(rs.getObject(columnIndex) == null){
			return null;
		}
		return Arrays.asList(StringUtils.split(rs.getString(columnIndex),","));
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if(cs.wasNull()){
			return null;
		}
		if(cs.getObject(columnIndex) == null){
			return null;
		}
		return Arrays.asList(StringUtils.split(cs.getString(columnIndex),","));
	}

}
