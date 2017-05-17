package com.zyf.commons.support.mybatis;

import com.zyf.framework.support.mybatis.provider.BatchUpdateProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @className:
 * @classDesc:
 * @author：zengyufei
 * @createTime: 2017年04月17日 18:15
 * www.zzsim.com
 */

public interface BatchUpdateMapper<T> {
	/**
	 * 根据主键字符串进行删除，类中只有存在一个带有@Id注解的字段
	 *
	 * @param ids 如 "1,2,3,4"
	 * @return
	 */
	@UpdateProvider(type = BatchUpdateProvider.class, method = "dynamicSQL")
	@Options(useCache = false, useGeneratedKeys = false)
	int updateByIds(@Param("ids") String ids,@Param("record")  T record);
}
