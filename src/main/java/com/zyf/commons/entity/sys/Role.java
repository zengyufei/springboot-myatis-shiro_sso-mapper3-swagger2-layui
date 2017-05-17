package com.zyf.commons.entity.sys;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zyf.commons.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;

@ApiModel(value = "用户角色实体", description = "该实体用于管理用户角色属性")
@Data
@NoArgsConstructor
@Table(name = "t_role")
public class Role extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -4015226349078291825L;

	private String name;
	private String description;
	private String resourceId;
	private Long pid;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}

}
