package com.zyf.framework.support.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * 通过 ids 字符串的各种操作
 * <p/>
 * ids 如 "1,2,3"
 * @author zengyufei
 */
public class BatchUpdateProvider extends MapperTemplate {

	public BatchUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	/**
	 * 根据主键字符串进行批量更新，类中只有存在一个带有@Id注解的字段
	 * @param ms
	 * @return
	 */
	public String updateByIds(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(SqlHelper.updateSetColumns(entityClass, "record", true, isNotEmpty()));
		Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
		if (columnList.size() == 1) {
			EntityColumn column = columnList.iterator().next();
			sql.append(" where ");
			sql.append(column.getColumn());
			sql.append(" in (${_parameter.ids})");
		} else {
			throw new MapperException("实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
		}
		return sql.toString();
	}

}
