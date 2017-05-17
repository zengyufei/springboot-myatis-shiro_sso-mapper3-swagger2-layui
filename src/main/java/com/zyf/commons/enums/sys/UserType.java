package com.zyf.commons.enums.sys;

import com.zyf.framework.support.mybatis.DescriptionID;

public enum UserType implements DescriptionID {

	system(1, "系统用户"),
	normal(2, "普通用户"),
	;

	private int index;
	private String description;

	UserType(int index, String description) {
		this.index = index;
		this.description = description;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
