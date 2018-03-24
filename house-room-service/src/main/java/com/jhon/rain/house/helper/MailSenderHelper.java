package com.jhon.rain.house.helper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>功能描述</br>邮件发送辅助类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/21 22:57
 */
@Component
public class MailSenderHelper {

  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String from;

  @Async
  public void sendSimpleCustomEmail(String subject, String content, String email) {
    /** 发送简单的邮件 **/
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setSubject(subject);
    message.setTo(email);
    message.setText(content);
    mailSender.send(message);
  }

  /**
   * <pre>邮件发送的异步配置</pre>
   *
   * @param title 主题
   * @param url   嵌入的链接
   * @param email 接收邮件的邮箱
   */
  @Async
  public void sendHtmlRegistionNotifyMail(String title, String url, String prefix, String subfix, String email) {
    MimeMessage message = null;
    String text = url;
    try {
      Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
      configuration.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(), "static/tpl");
      Template emailTemplate = configuration.getTemplate("email.ftl");
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("email", email);
      model.put("linkUrl", url);
      model.put("prefixContent", prefix);
      model.put("subContent", subfix);
      text = FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, model);
      /** 邮件发送 **/
      message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setSubject(title);
      helper.setTo(email);
      /** 这里的第二个参数为true ，代表的是以HTML格式来显示邮件 **/
      helper.setText(text, true);
      mailSender.send(message);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
