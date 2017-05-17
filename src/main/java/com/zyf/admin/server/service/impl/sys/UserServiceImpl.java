package com.zyf.admin.server.service.impl.sys;

import com.zyf.commons.entity.sys.User;
import com.zyf.commons.mapper.sys.UserMapper;
import com.zyf.commons.option.sys.UserOption;
import com.zyf.commons.service.sys.UserService;
import com.zyf.framework.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User login(String account, String password) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("account", account)
				.andEqualTo("password", password);
		List<User> users = userMapper.selectByExample(example);
		if(users !=null && !users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	@Override
	public List<User> queryList() {
		List<User> users = userMapper.selectAll();
		if(users !=null && !users.isEmpty())
			return users;
		else
			return null;
	}

	@Override
	public List<User> queryList(UserOption userOption) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(userOption.getName())){
			criteria.andLike("name", "%"+userOption.getName()+"%");
		}
		if(StringUtils.isNotBlank(userOption.getAccount())){
			criteria.andLike("account", "%"+userOption.getAccount()+"%");
		}
		if(StringUtils.isNotBlank(userOption.getMobile())){
			criteria.andLike("mobile", "%"+userOption.getMobile()+"%");
		}
		List<User> users = userMapper.selectByExample(example);
		if(users !=null && !users.isEmpty())
			return users;
		else
			return null;
	}

	@Override
	public List<User.VO> queryPages() {
		List<User.VO> queryPages = userMapper.queryPages("");
		return queryPages;
	}

	@Override
	public List<User.VO> queryPages2() {
		List<User.VO> queryPages = userMapper.queryPages2("");
		return queryPages;
	}

	@Override
	public User queryById(long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User queryByAccont(String account) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andCondition("account=", account);
		List<User> users = userMapper.selectByExample(example);
		if(users !=null && !users.isEmpty())
			return users.get(0);
		else
			return null;
	}

	@Override
	public int insert(User user) {
		user.setCreateTime(LocalDateTime.now());
		user.setUpdateTime(LocalDateTime.now());
		String md5Password = MD5.encode(user.getAccount()+String.valueOf(user.getPassword()));
		user.setPassword(md5Password);
		return userMapper.insertSelective(user);
	}

	@Override
	public int batchInsert(List<User> users) {
		return userMapper.insertList(users);
	}

	@Override
	public int update(User user) {
		if(StringUtils.isNotBlank(user.getPassword())){
			String md5Password = MD5.encode(user.getAccount()+String.valueOf(user.getPassword()));
			user.setPassword(md5Password);
		}
		return userMapper.updateByPrimaryKeySelective(user);
	}

}