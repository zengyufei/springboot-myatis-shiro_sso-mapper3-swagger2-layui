package com.zyf.admin.web.sys;

import com.zyf.commons.entity.sys.Role;
import com.zyf.commons.result.Msg;
import com.zyf.commons.option.sys.RoleOption;
import com.zyf.commons.service.sys.RoleService;
import com.zyf.framework.utils.ValidUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "角色管理", description = "角色增删改查")
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation(value = "查看角色列表", notes = "")
	@ApiResponse(code = 401, message = "查询失败")
	@RequestMapping(value = "queryList", method = RequestMethod.GET)
	public String queryList() {
		log.info("查询角色列表信息");
		Msg msg = new Msg();
		List<Role> roleList = roleService.queryList(new RoleOption());
		if (CollectionUtils.isNotEmpty(roleList)) {
			msg.setData(roleList);
		}
		return msg.toString();
	}

	@ApiOperation(value = "查看角色信息", notes = "")
	@ApiResponse(code = 401, message = "查询角色信息失败")
	@RequestMapping(value = "queryById/{id}", method = RequestMethod.GET)
	public String queryById(@PathVariable("id") long id) {
		log.info("查询单个角色信息");
		Msg msg = ValidUtils.validMsg(Role.class, "id", id);
		if (msg.hasError()) {
			return msg.toString();
		}

		Role role = roleService.query(id);
		if (role != null) {
			msg.setData(role);
		}
		return msg.toString();
	}

	@ApiOperation(value = "删除角色", notes = "")
	@ApiResponse(code = 401, message = "删除角色失败")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") long id) {
		log.info("查询单个角色信息");
		Msg msg = ValidUtils.validMsg(Role.class, "id", id);
		if (msg.hasError()) {
			return msg.toString();
		}
		int effect = roleService.deleteById(id);
		if (effect > 0) {
			return msg.withSuccess("删除角色成功").toString();
		}else {
			return msg.withSuccess("删除角色失败").toString();
		}
	}

	@ApiOperation(value = "新增角色", notes = "")
	@ApiResponse(code = 401, message = "新增角色失败")
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public String insert(Role role) {
		log.info("新增单个角色信息");
		Msg msg = ValidUtils.validMsg(role, "name");
		if (msg.hasError()) {
			return msg.toString();
		}
		// 在什么单位下创建，就应该拥有什么单位的权限
		Role pidRole = roleService.query(role.getPid());
		role.setResourceId(pidRole.getResourceId());
		// 新增业务
		int effect = roleService.insert(role);
		if (effect > 0) {
			msg.setData(role);
			return msg.withSuccess("新增角色成功！").toString();
		}
		return msg.withError("新增角色失败").toString();
	}

	@ApiOperation(value = "修改角色", notes = "")
	@ApiResponse(code = 401, message = "修改角色失败")
	@RequestMapping(value = "update", method = {RequestMethod.GET, RequestMethod.POST})
	public String update(Role role, String ids) {
		log.info("修改单个角色信息");
		Msg msg = ValidUtils.validMsg(role, "name");
		if (msg.hasError()) {
			return msg.toString();
		}
		//移动了单位，就要赋值相应单位下的权限
		if(role.getPid() != null && role.getPid() > 0){
			Role pidRole = roleService.query(role.getPid());
			role.setResourceId(pidRole.getResourceId());
		}
		//事务修改角色
		boolean executeResult = transactionTemplate.execute(s -> {
			Object savepoint = s.createSavepoint();
			int effect = 0;
			effect = roleService.update(role);
			if (effect < 0) {
				s.rollbackToSavepoint(savepoint);
				return false;
			}
			if (StringUtils.isNotBlank(ids)) {
				role.setId(null);
				effect = roleService.batchUpdate(ids, role);
				if (effect <= 0) {
					s.rollbackToSavepoint(savepoint);
					return false;
				}
			}
			s.flush();
			return true;
		});
		if (executeResult) {
			return msg.withSuccess("修改角色成功").toString();
		} else {
			return msg.withError("修改角色失败").toString();
		}
	}

}
