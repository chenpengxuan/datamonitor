package com.ymatou.datamonitor.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ymatou.datamonitor.config.MailConfig;

/**
 * 发送邮件工具类
 * 
 * @author qianmin
 *
 */
@Component
public class MailUtil implements InitializingBean {

    protected static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private MailConfig mailConfig;

    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    /**
     * 发送html邮件
     * 
     * @param from
     * @param to
     * @param title
     * @param text
     * @throws AddressException
     * @throws MessagingException
     */
    public void sendHtmlMail(String from, String[] to, String title, String text)
            throws AddressException, MessagingException {

        long start = System.currentTimeMillis();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "GBK");

        InternetAddress[] toArray = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            toArray[i] = new InternetAddress(to[i]);
        }
        messageHelper.setFrom(new InternetAddress(from));
        messageHelper.setTo(toArray);
        messageHelper.setSubject(title);
        messageHelper.setText(text, true);
        mimeMessage = messageHelper.getMimeMessage();
        
        mailSender.send(mimeMessage);
        long end = System.currentTimeMillis();
        logger.info("send mail start:" + start + " end :" + end);
    }

    /**
     * 设置邮箱host，用户名和密码
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender.setHost(mailConfig.getEmailHost());

        if (!StringUtils.isEmpty(mailConfig.getEmailUser())) {
            mailSender.setUsername(mailConfig.getEmailUser());
        }

        if (!StringUtils.isEmpty(mailConfig.getEmailPassword())) {
            mailSender.setPassword(mailConfig.getEmailPassword());
        }

        if (!StringUtils.isEmpty(mailConfig.getEmailPort())) {
            try {

                Integer port = Integer.parseInt(mailConfig.getEmailPort());
                mailSender.setPort(port);
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }
}
