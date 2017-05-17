package com.zyf.commons.entity.sys;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zyf.commons.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "资源实体", description = "该实体用于管理资源属性")
@Data
@NoArgsConstructor
@Table(name = "t_resource")
public class Resource extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3761143844780087351L;

	private Long pid;
	private String name;
	private String url;
	private String resIcon;
	private String permission;

	@Transient
	@Setter(value = AccessLevel.NONE)
	private List<Resource> children = new ArrayList<>();

	@Override
	public String toString() {
		return JSONObject.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}

}