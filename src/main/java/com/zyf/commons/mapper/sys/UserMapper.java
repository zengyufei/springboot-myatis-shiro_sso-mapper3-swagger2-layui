package com.zyf.commons.mapper.sys;

import com.zyf.commons.entity.sys.User;
import com.zyf.commons.support.mybatis.AutoMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends AutoMapper<User>{

	/*
		该方法是测试方法，测试注解式 mybatis mapper，建议不要删除
	 */
	@ResultType(User.VO.class)
	@Select("select u.id as id, u.name as name, u.account as account, u.role_id as roleId," +
			" u.create_time as createTime, u.update_time as updateTime, r.name as roleName " +
			" from t_user as u " +
			" left join t_role as r " +
			" on u.role_id = r.id")
	List<User.VO> queryPages(String test);

	/*
		该方法是测试方法，测试 mapper.xml 是否正确使用，建议不要删除
	 */
	List<User.VO> queryPages2(@Param("test") String test);
}
