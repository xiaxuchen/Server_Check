package com.cxyz.check.util.automapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//需要映射的类
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Classes
{
    //有映射关系的class
    Class[] value();
}