package com.zyf.commons.service.sys;

import com.zyf.commons.entity.sys.User;
import com.zyf.commons.option.sys.UserOption;

import java.util.List;

public interface UserService {

	/**
	 * 根据账号Account、Password 登录，空则登录失败
	 * @param account 账户
	 * @param password 密码
	 * @return User
	 */
	User login(String account, String password);

	/**
	 * 查询用户列表
	 * @return User
	 */
	List<User> queryList();

	/**
	 * 条件查询用户列表
	 * @return User
	 */
	List<User> queryList(UserOption userOption);

	/**
	 * 条件查询用户列表分页
	 * @return User
	 */
	List<User.VO> queryPages();
	/**
	 * 条件查询用户列表分页
	 * @return User
	 */
	List<User.VO> queryPages2();

	/**
	 * 根据账号Id查询当前用户
	 * @param id 账户
	 * @return User
	 */
	User queryById(long id);

	/**
	 * 根据账号Account查询当前用户
	 * @param account 账户
	 * @return User
	 */
	User queryByAccont(String account);

	/**
	 * 新增用户
	 * @param user 用户实体
	 * @return int 成功数目
	 */
	int insert(User user);

	/**
	 * 批量新增用户
	 * @param users 用户实体
	 * @return int 成功数目
	 */
	int batchInsert(List<User> users);

	/**
	 * 修改用户
	 * @param user 用户实体
	 * @return int 成功数目
	 */
	int update(User user);
}
