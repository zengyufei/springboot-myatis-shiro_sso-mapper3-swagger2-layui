package com.zyf.commons.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/*
	@Data 是 lombok 插件的注解
	给改注解的类的所有成员变量添加 getter,setter 方法
 */
@ApiModel(value = "公共实体继承基类", description = "被其他实体继承，是一个抽象类，无法实现")
@Data
public abstract class BaseEntity {

	@ApiModelProperty(value = "id", required = true, dataType = "Long", example = "1", name = "唯一标识")
	@Id
	@Min(code = "id.min", value = 1, message = "ID不能小于0")
	protected Long id;

	/*
	LocalDateTime 使用简单
	  格式化 createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	  赋值 createTime.withYear(2017).withMonth(4).withDayOfYear(16).withHour(0).withMinute(0).withSecond(0) == 2017-04-16 00:00:00
	  加 createTime.plusDays(1)、减 createTime.minusDays(1)
	  之前之后判断 updateTime.isAfter(createTime)  判断 updateTime 是 createTime 之后
	 */
	@ApiModelProperty(value = "创建日期", dataType = "LocalDateTime", example = "2016-08-08")
	@Future(code = "createTime.future", message = "创建日期只能是已过去的时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime createTime;

	@ApiModelProperty(value = "修改日期", dataType = "LocalDateTime", example = "2016-08-08")
	@Future(code = "updateTime.future", message = "修改日期只能是已过去的时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime updateTime;
}
