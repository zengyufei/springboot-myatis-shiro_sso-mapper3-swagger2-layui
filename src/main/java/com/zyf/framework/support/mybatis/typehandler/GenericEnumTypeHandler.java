package com.zyf.framework.support.mybatis.typehandler;

import com.zyf.framework.support.mybatis.DescriptionID;
import com.zyf.framework.support.mybatis.annotation.ClazzTypeScan;
import com.zyf.framework.support.mybatis.enums.TypeScan;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ClazzTypeScan(TypeScan.enumToInt)
public class GenericEnumTypeHandler<E extends DescriptionID> extends BaseTypeHandler<E> {

	private Class<E> type;

	private E[] enums;
	
	public GenericEnumTypeHandler(){}

	public GenericEnumTypeHandler(Class<E> type) {
		if (type == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null) {
			throw new IllegalArgumentException(type.getName() + " does not represent an enum type.");
		}
	}
	
	private E loadEnum(int index) {
		for (E e : enums) {
			if (e.getIndex() == index) {
				return e;
			}
		}
		throw new IllegalArgumentException(type.getName() + "  unknown enumerated type  index:" + index);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getIndex());
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if(rs.getObject(columnName) == null){
			return null;
		}
		int index = rs.getInt(columnName);
		return loadEnum(index);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if(rs.getObject(columnIndex) == null){
			return null;
		}
		int index = rs.getInt(columnIndex);
		return loadEnum(index);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		if(cs.getObject(columnIndex) == null){
			return null;
		}
		int index = cs.getInt(columnIndex);
		return loadEnum(index);
	}

}
