package com.zyf.admin.support.shiro.realm;

import com.zyf.commons.entity.sys.Resource;
import com.zyf.commons.entity.sys.User;
import com.zyf.commons.service.sys.ResourceService;
import com.zyf.commons.service.sys.RoleService;
import com.zyf.commons.service.sys.UserService;
import com.zyf.framework.utils.MD5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 验证用户登录
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleService roleService;

	public UserRealm() {
		setName("userRealm");
		//加密策略
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	//权限资源角色
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User.DTO userVo = (User.DTO) SecurityUtils.getSubject().getPrincipal();
		List<Resource> resources = resourceService.queryList(userVo.getResourceIds());
		Set<String> permission = resources.parallelStream().map(Resource::getPermission).collect(Collectors.toSet());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//add Permission Resources
		info.setStringPermissions(permission);
		return info;
	}

	//登录验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		String account = upt.getUsername();
		char[] password = upt.getPassword();
		User user = userService.login(account, MD5.encode(account + String.valueOf(password)));
		if(user == null)
			throw new UnknownAccountException();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
		return info;
	}
}