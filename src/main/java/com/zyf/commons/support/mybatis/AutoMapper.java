package com.zyf.commons.support.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 被继承的Mapper，一般业务Mapper继承它
 */
public interface AutoMapper<T> extends Mapper<T>, MySqlMapper<T>, BatchUpdateMapper<T> {
	//FIXME 特别注意，该接口不能被扫描到，否则会出错
}