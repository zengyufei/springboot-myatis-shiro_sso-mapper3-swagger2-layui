package com.zyf.admin.support.shiro.entity;

import com.zyf.commons.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class Token extends BaseEntity{

	//id
	//createTime
	//updateTime

	private Long roleId;
	private List<String> resourceIds;
	private Long orgId;
	private String data;
	private String cookies;
	private String ip;
	private Integer time; //有效期

}
