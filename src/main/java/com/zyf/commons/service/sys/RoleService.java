package com.zyf.commons.service.sys;

import com.zyf.commons.entity.sys.Role;
import com.zyf.commons.option.sys.RoleOption;

import java.util.List;

public interface RoleService {

	int insert(Role role);

	int update(Role role);

	int batchUpdate(String ids, Role role);

	Role query(long id);

	List<Role> queryList(RoleOption roleOption);

	int updateRoleResource(Role role);

	int deleteById(long id);
}
