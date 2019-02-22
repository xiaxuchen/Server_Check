package com.cxyz.check.util.push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.message.MessageType;

public class JMessageUtil {

    public static final String APPKEY = "2791f4944cc55de38f90d072";

    public static final String MASTER_SCRET = "8db4ddc691fecb7c468aa8e3";

    public static void sendMessage(String receiver,String content)
    {
        JMessageClient client = new JMessageClient(APPKEY,MASTER_SCRET);
        try {
            client.sendMessage(1,"single",receiver,"admin","admin", MessageType.TEXT, MessageBody.text(content));
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }
}
