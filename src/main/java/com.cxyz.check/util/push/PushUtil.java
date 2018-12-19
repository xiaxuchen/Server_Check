package com.cxyz.check.util.push;

import java.util.HashMap;
import java.util.Map;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class PushUtil {

    public static final String APPKEY = "9a1fc9d813243fb8f9f707da";

    public static final String MASTER_SCRET = "af6045559f70d708d04ca077";

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
        msg.setMsgContent("4444");
        msg.addExtras(params);
        // 推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android())
                // 指定android平台的用户
                .setAudience(Audience.alias(alias))
                // 你项目中的所有用户
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                // 这里是指定开发环境,不用设置也没关系
                .setMessage(msg.build())
                // 自定义信息
                .build();
        try {
            PushResult pu = getClient().sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
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

}
