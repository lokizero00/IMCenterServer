package com.loki.server.test;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailTest {
	public static void main(String[] args) throws MessagingException {
		Properties prop = new Properties();
		prop.put("mail.host", "smtp.bestimade.com");
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		// 使用java发送邮件5步骤
		// 1.创建sesssion
		Session session = Session.getInstance(prop);
		// 开启session的调试模式，可以查看当前邮件发送状态
		session.setDebug(true);

		// 2.通过session获取Transport对象（发送邮件的核心API）
		Transport ts = session.getTransport();
		// 3.通过邮件用户名密码链接，阿里云默认是开启个人邮箱pop3、smtp协议的，所以无需在阿里云邮箱里设置
		ts.connect("techlwb@bestimade.com", "iMade123456");

		// 4.创建邮件

		Message msg = createSimpleMail(session);

		// 5.发送电子邮件

		ts.sendMessage(msg, msg.getAllRecipients());
	}

	public static MimeMessage createSimpleMail(Session session) throws AddressException, MessagingException {
		// 创建邮件对象
		MimeMessage mm = new MimeMessage(session);
		// 设置发件人
		mm.setFrom(new InternetAddress("techlwb@bestimade.com"));
		// 设置收件人
		mm.setRecipient(Message.RecipientType.TO, new InternetAddress("lokizero0@live.com"));
		// 设置抄送人
//		mm.setRecipient(Message.RecipientType.CC, new InternetAddress("XXXX@qq.com"));

		mm.setSubject("XXX网站注册邮件！");
		mm.setContent("验证码为690", "text/html;charset=gbk");

		return mm;

	}
}
