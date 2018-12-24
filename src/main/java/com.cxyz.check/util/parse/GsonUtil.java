package com.cxyz.check.util.parse;


import com.cxyz.check.exception.util.GsonException;
import com.cxyz.check.util.parse.typeadapter.TimestampTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public class GsonUtil {
    //不用创建对象,直接使用Gson.就可以调用方法
    private static Gson gson = null;

    private static Logger logger = Logger.getLogger(GsonUtil.class);
    //判断gson对象是否存在了,不存在则创建对象
    static {
        if (gson == null) {
            //gson = new Gson();
            //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
            gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
    }

    //无参的私有构造方法
    private GsonUtil() {
    }

    /**
     * 将对象转成json格式
     *
     * @param object
     * @return String
     */
    public static String toJson(Object object) throws GsonException {
        String gsonString = null;
        try {
            if (gson != null) {
                gsonString = gson.toJson(object);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GsonException(e.getMessage());
        }
        return gsonString;
    }


    /**
     * 将json转成特定的的对象
     *
     * @param gsonString <T> 希望获得的类型
     * @return
     * @throws GsonException
     */
    public static Object fromJson(String gsonString, Type type) throws GsonException {
        Object obj = null;
        try {
            if (gson != null) {
                //传入json对象和对象类型,将json转成对象
                obj = gson.fromJson(gsonString, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new GsonException(e.getMessage());
        }
        return obj;
    }


} 