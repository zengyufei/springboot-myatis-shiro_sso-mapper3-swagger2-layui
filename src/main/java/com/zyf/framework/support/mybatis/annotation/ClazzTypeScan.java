package com.zyf.framework.support.mybatis.annotation;


import com.zyf.framework.support.mybatis.enums.TypeScan;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ClazzTypeScan {

	TypeScan value();
}
