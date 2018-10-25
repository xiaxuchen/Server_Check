package com.cxyz.check.util.automapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//对应path
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Path{
    String value();
    //有所属关系的类
    int index() default 0;
}