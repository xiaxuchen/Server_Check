package com.cxyz.check.util.push;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

public class PushUtil {

    public static final String APPKEY = "2791f4944cc55de38f90d072";

    public static final String MASTER_SCRET = "8db4ddc691fecb7c468aa8e3";

    private static JPushClient instance;

    private static long timeToLive =  3*60 * 60 * 24;

    /**
     * 获取JPushClient实例
     *
     * @return
     */
    public static JPushClient getClient() {
        if (instance == null)
            instance = new JPushClient(MASTER_SCRET, APPKEY,false,timeToLive);
        return instance;
    }

    // 极光推送>>Android
    // Map<String, String> parm是我自己传过来的参数,同学们可以自定义参数
    public static void jpushAndroid(String title,Map<String, String> params,String ... alias) { // 设置好账号的app_key和masterSecret
        Message.Builder msg = Message.newBuilder();
        msg.setTitle(title);
        msg.setMsgContent("");
        msg.addExtras(params);
        // 推送的关键,构造一个payload
        final PushPayload.Builder builder = PushPayload.newBuilder().setPlatform(Platform.android())
                // 你项目中的所有用户
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                // 这里是指定开发环境,不用设置也没关系
                .setMessage(msg.build());
        // 自定义信息
        for (String alia : alias)
        {
            builder.setAudience(Audience.alias(alia));
            try {
                PushResult pu = getClient().sendPush(builder.build());
            } catch (APIConnectionException e) {
                e.printStackTrace();
            } catch (APIRequestException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 推送到Android设备
     * @param title 标题
     * @param key 键
     * @param value 值
     * @param alias 用户别名
     */
    public static void jpushAndroid(String title,String key,String value,String ... alias)
    {
        Map<String,String> map = new HashMap<>();
        map.put(key,value);
        jpushAndroid(title,map,alias);
    }


    /**
     * 推送到Android设备
     * @param title 标题
     * @param key 键
     * @param value 值
     * @param alias 用户别名
     */
    public static void jpushAndroid(String title, String key, String value, Collection<String> alias)
    {
        String[] as = new String[alias.size()];
        int i[] = {0};
        alias.forEach(s -> {
            as[i[0]] = s;
            i[0]++;
        });
        jpushAndroid(title,key,value,as);
    }

}
