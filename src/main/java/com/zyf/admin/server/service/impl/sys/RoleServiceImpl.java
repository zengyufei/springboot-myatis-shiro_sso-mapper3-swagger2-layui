package com.zyf.admin.server.service.impl.sys;

import com.zyf.commons.entity.sys.Role;
import com.zyf.commons.mapper.sys.RoleMapper;
import com.zyf.commons.option.sys.RoleOption;
import com.zyf.commons.service.sys.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public int insert(Role role) {
		role.setCreateTime(LocalDateTime.now());
		role.setUpdateTime(LocalDateTime.now());
		return roleMapper.insertUseGeneratedKeys(role);
	}

	@Override
	public int update(Role role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public int batchUpdate(String ids, Role role) {
		return roleMapper.updateByIds(ids, role);
	}

	@Override
	public int updateRoleResource(Role role) {
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", role.getId());
		return roleMapper.updateByExampleSelective(role, example);
	}

	@Override
	public int deleteById(long id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Role query(long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> queryList(RoleOption roleOption) {
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		return roleMapper.selectByExample(example);
	}

}