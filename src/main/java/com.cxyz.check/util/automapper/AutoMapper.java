package com.cxyz.check.util.automapper;

import com.cxyz.check.util.automapper.annotation.Classes;
import com.cxyz.check.util.automapper.annotation.Ignore;
import com.cxyz.check.util.automapper.annotation.Path;
import com.cxyz.check.util.automapper.exception.ClassNotMatchException;
import com.cxyz.check.util.automapper.exception.PathNotFoundException;

import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AutoMapper
{
    private static Logger logger = Logger.getLogger(AutoMapper.class);

    /**
     * 对对应的entity和dto进行自动映射
     * @param source entity
     * @param destination dto
     * @param <TSource> entity的类型
     * @param <TDestination> dto的类型
     * @return
     */
    public static <TSource,TDestination> TDestination mapping(TSource source,TDestination destination)
    {
        //获取class
        Class<?> clazz = destination.getClass();
        if(clazz.isAnnotationPresent(Classes.class))
        {
            //获取映射的class
            Class[] value = clazz.getAnnotation(Classes.class).value();
            //当class只有一个时，自动映射的成员可以不需要path
            if(value.length == 1)
            {
                //当实体类的类型与注解中声明的类型不符时抛出异常
                if(source.getClass() != value[0])
                    throw new ClassNotMatchException();
                //获取所有的属性
                Field[] fields = clazz.getDeclaredFields();
                //遍历
                for(Field f:fields)
                {
                    if(f.isAnnotationPresent(Ignore.class))
                    {
                        logger.info("ignore:"+f.getName());
                        //当有Ignore注解时跳过此属性
                        continue;
                    }
                    else if(f.isAnnotationPresent(Path.class))
                    {
                        //当有path时做相应处理
                        String path = f.getAnnotation(Path.class).value();
                        logger.info("path:"+path);
                        //调用souce的get方法获取数据并将数据set给destination
                        invokeSet(f.getName(), destination, invokeGet(path, source));
                    }else{
                        //当只有一个映射类时，不加注解的字段自动映射
                        logger.info("none:"+f.getName());
                        invokeSet(f.getName(), destination,  invokeGet(f.getName(), source));
                    }
                }
                //返回映射后的结果
                return destination;
            }else{
                //当实体类的类型与注解中声明的类型不符时抛出异常
                boolean isTrue = false;
                for(Class c:value)
                {
                    if(c == source.getClass())
                        isTrue = true;
                }
                if(!isTrue)
                    throw new ClassNotMatchException();
                //获取所有的属性
                Field[] fields = clazz.getDeclaredFields();
                //遍历
                for(Field f:fields)
                {
                    if(f.isAnnotationPresent(Path.class))
                    {
                        //当有path时做相应处理
                        Path p = f.getAnnotation(Path.class);
                        String path = p.value();
                        if(value[p.index()] == source.getClass())
                        {
                            logger.info("path:"+path);
                            //调用souce的get方法获取数据并将数据set给destination
                            invokeSet(f.getName(), destination, invokeGet(path, source));
                        }
                    }else{
                        //当有多个映射类时，不加注解和加Ignore注解的字段都视为ignore
                        logger.info("none:"+f.getName());
                        continue;
                    }
                }
                logger.info("目标对象："+destination);
                //返回映射后的结果
                return destination;
            }
        }
        throw new ClassNotMatchException();
    }


    public static <S, T> List<T> mappingList(List<S> src, Class<T> targetClass) {
        List<T> target = new ArrayList<T>();
        for (int i = 0; i < src.size(); i++) {
            try {
                Object object = targetClass.newInstance();
                target.add((T) object);
                mapping(src.get(i), object);
            } catch (Exception e) {
                continue;// 某个方法反射异常
            }
        }
        logger.info("目标list："+target);
        return target;
    }

    /**
     * 调用path中一系列的get方法获取最终的参数
     * @param paths 字段名称链
     * @param obj 第一次调用的对象
     * @return
     */
    public static Object invokeGet(String paths,Object obj)
    {
        String pathes[] = paths.split("\\.");
        //将第一个调用方法的对象置为obj
        Object current = obj;
        //遍历调用方法，获取最终的对象
        for(String path:pathes) {
            logger.info("path"+path);
            //给字段拼凑方法名
            try {
                current = new PropertyDescriptor(path,current.getClass()).getReadMethod().invoke(current);
                //如果get方法返回的是null，则直接返回null
                if(current == null)
                    return null;
            } catch (Exception e)
            {
                e.printStackTrace();
                //当调用方法异常时抛出路径无法找到异常
                throw new PathNotFoundException();
            }
        }
        return current;
    }


    /**
     * 调用set方法
     * @param name
     * @param obj
     * @param params
     */
    public static void invokeSet(String name,Object obj,Object ... params)
    {
        logger.info("name:"+name);
        try {
            Method writeMethod = new PropertyDescriptor(name, obj.getClass()).getWriteMethod();
            writeMethod.invoke(obj,params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PathNotFoundException();
        }
    }

}