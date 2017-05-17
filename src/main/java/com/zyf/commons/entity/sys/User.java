package com.zyf.commons.entity.sys;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zyf.commons.entity.BaseEntity;
import com.zyf.commons.enums.sys.UserType;
import com.zyf.commons.valid.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "用户登录实体", description = "该实体用于管理用户属性")
@Data
@NoArgsConstructor
@Table(name = "t_user")
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 730863452165463427L;

	@ApiModelProperty(value = "真实姓名", required = true, dataType = "String", example = "管理员")
	@NotBlank(code = "name.notBlank", message = "真实姓名不能为空")
	@NotNull(code = "name.notNull", message = "真实姓名不能为 null")
	private String name;

	@ApiModelProperty(value = "账号", required = true, dataType = "String", example = "admin", access = "123", name = "321")
	@NotBlank(code = "account.notBlank", message = "账号不能为空")
	@NotNull(code = "account.notNull", message = "账号不能为 null")
	private String account;

	@ApiModelProperty(value = "密码", required = true, dataType = "String", example = "admin")
	//@Size(code = "密码不能小于 5 位或大于 18 位", min = 5, max = 18, message = "密码长度不能小于6位")
	@NotBlank(code = "password.notBlank", message = "密码不能为空")
	@NotNull(code = "password.notNull", message = "密码不能为 null")
	private String password;

	@ApiModelProperty(value = "用户角色", required = true, dataType = "Long", example = "管理员角色")
	@Min(code = "roleId.min", value = 1, message = "ID不能小于0")
	private Long roleId;

	@ApiModelProperty(value = "用户类型", required = true, example = "用户的种类", dataType = "UserType.class")
	@NotNull(code = "userType.notNull", message = "用户类型不能为 null")
	private UserType userType;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}

	@Data
	public static class DTO extends BaseEntity implements Serializable{
		private static final long serialVersionUID = -3035739720980054180L;
		private String name;
		private String account;
		private Long roleId;
		private UserType userType;
		private List<String> resourceIds;
	}

	@Data
	public static class VO extends BaseEntity implements Serializable {
		private static final long serialVersionUID = 7547460487417787018L;
		private String name;
		private String account;
		private Long roleId;
		private String roleName;
		private UserType userType;
	}

}