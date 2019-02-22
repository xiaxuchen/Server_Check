package com.cxyz.check.util.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    private static Logger logger = Logger.getLogger(MailUtil.class);
    /**
     * 邮件单发（自由编辑短信，并发送，适用于私信）
     *
     * @param toEmailAddress 收件箱地址
     * @param emailTitle 邮件主题
     * @param createMSG 构造msg对象
     * @throws Exception
     */
    public static void sendEmail(String toEmailAddress, String emailTitle, CreateMSG createMSG) throws Exception{
        new Thread(){
            @Override
            public void run() {
                super.run();
                Transport transport = null;
                try {
                    Properties mail = new Properties();
                    mail.load(MailUtil.class.getClassLoader().getResourceAsStream("mail/mail.properties"));

                    Properties props = new Properties();

                    // 发送服务器需要身份验证
                    props.setProperty("mail.smtp.auth", "true");

                    // 端口号
                    props.put("mail.smtp.port", 465);

                    // 设置邮件服务器主机名
                    props.setProperty("mail.smtp.host", mail.getProperty("mail.host"));

                    // 发送邮件协议名称
                    props.setProperty("mail.transport.protocol", "smtp");

                    /**SSL认证，注意腾讯邮箱是基于SSL加密的，所以需要开启才可以使用**/
                    MailSSLSocketFactory sf = new MailSSLSocketFactory();
                    sf.setTrustAllHosts(true);

                    //设置是否使用ssl安全连接（一般都使用）
                    props.put("mail.smtp.ssl.enable", "true");
                    props.put("mail.smtp.ssl.socketFactory", sf);

                    //创建会话
                    Session session = Session.getInstance(props);

                    //获取邮件对象
                    //发送的消息，基于观察者模式进行设计的
                    Message msg = new MimeMessage(session);

                    //设置邮件标题
                    msg.setSubject(emailTitle);

                    //构造一个msg
                    if(createMSG != null)
                        createMSG.createMsg(msg);

                    //设置发件人邮箱
                    // InternetAddress 的三个参数分别为: 发件人邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
                    msg.setFrom(new InternetAddress(mail.getProperty("mail.account"),"点到为止", "UTF-8"));

                    //得到邮差对象
                    transport = session.getTransport();

                    //连接自己的邮箱账户
                    //密码不是自己QQ邮箱的密码，而是在开启SMTP服务时所获取到的授权码
                    //connect(host, user, password)
                    transport.connect( mail.getProperty("mail.host"),mail.getProperty("mail.account"),mail.getProperty("mail.is"));

                    //发送邮件
                    transport.sendMessage(msg, new Address[] { new InternetAddress(toEmailAddress) });

                    //        //将该邮件保存到本地
                    //        OutputStream out = new FileOutputStream("MyEmail.eml");
                    //        msg.writeTo(out);
                    //        out.flush();
                    //        out.close();
                }catch (Exception e)
                {
                    e.printStackTrace();
                    logger.error(e.getStackTrace());
                }finally {
                    if(transport != null) {
                        try {
                            transport.close();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                }



            }
        }.start();
    }

    public interface CreateMSG{

        void createMsg(Message msg) throws UnknownHostException;
    }
}
