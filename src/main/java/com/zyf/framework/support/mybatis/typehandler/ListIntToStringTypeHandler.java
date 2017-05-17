package com.zyf.framework.support.mybatis.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MappedJdbcTypes({JdbcType.VARCHAR})
public class ListIntToStringTypeHandler extends BaseTypeHandler<List<Integer>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
		if(parameter == null || parameter.size() == 0){
			ps.setString(i, null);
		}else{
			ps.setString(i, StringUtils.join(parameter,","));
		}
	}

	@Override
	public List<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if(rs.wasNull()){
			return null;
		}
		if(rs.getObject(columnName) == null){
			return null;
		}
		String[] value = StringUtils.split(rs.getString(columnName),",");
        List<Integer> list = new ArrayList<Integer>(value.length);
        for (String str : value) {
            list.add(Integer.parseInt(str));
        }
        return list;
	}

	@Override
	public List<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if(rs.wasNull()){
			return null;
		}
		if(rs.getObject(columnIndex) == null){
			return null;
		}
		String[] value = StringUtils.split(rs.getString(columnIndex),",");
        List<Integer> list = new ArrayList<Integer>(value.length);
        for (String str : value) {
            list.add(Integer.parseInt(str));
        }
        return list;
	}

	@Override
	public List<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if(cs.wasNull()){
			return null;
		}
		if(cs.getObject(columnIndex) == null){
			return null;
		}
		String[] value = StringUtils.split(cs.getString(columnIndex),",");
        List<Integer> list = new ArrayList<Integer>(value.length);
        for (String str : value) {
            list.add(Integer.parseInt(str));
        }
        return list;
	}

}
