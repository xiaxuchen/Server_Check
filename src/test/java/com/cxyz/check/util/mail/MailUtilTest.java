package com.cxyz.check.util.mail;

import org.junit.Test;

import javax.mail.Message;

import static org.junit.Assert.*;

public class MailUtilTest {

    @Test
    public void sendEmail() {
        try {
            MailUtil.sendEmail("17770092259@163.com", "点到为止改密验证", new MailUtil.CreateMSG() {
                @Override
                public void createMsg(Message msg) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}