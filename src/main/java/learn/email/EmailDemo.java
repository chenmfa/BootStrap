package learn.email;
import java.io.File;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import learn.BaseLearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 
 * 
 * <pre>
 * _________________________CLASS INFO________________________
 *  @Description : [Email例子] 
 *  @Class       : [EmailSample]
 *  @Package     : [email]
 *  @Project     : [Learn]
 *  @Author      : [Sevenlee]
 *  @CreateDate  : [2015年4月6日 上午9:18:58]
 *  @UpdateUser  : [chenmfa]
 *  @UpdateDate  : [20160106]
 *  @UpdateRemark: [摘自http://www.zhljc.com/tutorial/detail/20150406095438592390]
 *  @Company     : [org&seven— 2. hundsun]
 *  @Version     : [v 1.0] [1.01]
 * __________________________________________________________
 * </pre>
 */
public class EmailDemo extends BaseLearn{
  /**
   * 记录日志
   */
  private static Logger log =LoggerFactory.getLogger(EmailDemo.class);
  /**
   * 邮件测试工具类
   * @param subject 邮件主题
   * @param content HTML格式的邮件内容
   */
  public void sendFileMail(String subject, String content) {
    JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
    // 设置自己登陆email的服务商提供的host
    senderImpl.setHost("mail.hundsun.com");
    // 设置自己登陆邮箱账号
    //senderImpl.setUsername("chenmf13021");
    // 邮箱密码
    senderImpl.setPassword("yckngd*EQFGT23142");
    try {
      // 建立HTML邮件消息
      MimeMessage mailMessage = senderImpl.createMimeMessage();
      // true表示开始附件模式.如果邮件不需要附件设置成false即可(Spring封装类)
      MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
      // 设置收信人的email地址
      messageHelper.setTo("790109677@qq.com");
      // 设置寄信人的email地址{与上面登陆的邮件一致},也可以不设置登录账号，
      messageHelper.setFrom("chenmf13021@hundsun.com");
      // 设置邮件发送内容的主题
      messageHelper.setSubject(subject);
      // true 表示启动HTML格式的邮件
      messageHelper.setText("<html><title>陈苗发的测试spring</title><body>"
          + content + "<img src='cid:img1'></img></body></html>", true);
      // 如不需要附件,这里可以省略---------------------------------------START
      // 读取附件一
      FileSystemResource file1 = new FileSystemResource(new File("D:\\window7_sha1.png"));
      // 读取附件二
      FileSystemResource file2 = new FileSystemResource(new File("D:\\陈愿.doc"));
      // 添加附件一
      //messageHelper.addAttachment("window7_sha1.jpg", file1);
      //将附件直接插入到邮件正文里面
      messageHelper.addInline("img1", file1);
      // 添加附件二
      // 附件名有中文可能出现乱码(处理乱码)
      messageHelper.addAttachment(MimeUtility.encodeWord("陈愿.doc"), file2);
      // 如不需要附件,这里可以省略------------------------------------------END
      // 发送邮件
      
      Properties prop = new Properties();
      //prop.put("mail.transport.protocol", "smtp");
      prop.put("mail.smtp.auth", "true"); //这里的true 一定要加引号，否则出错
      prop.put("mail.smtp.timeout", 2500);
      senderImpl.setJavaMailProperties(prop);
      senderImpl.send(mailMessage);
      log.info("Email Send Success!");
    } catch (Exception e) {
      log.error("Email Send Error!" + e.getMessage());
    }

  }

  public static void main(String[] agrs) {
    // 注意测试需要修改您自己的邮件服务商host,登陆邮件用户,邮件密码,附件,收信人地址
    new EmailDemo().sendFileMail("测试邮件", "<H1>陈苗发的测试邮件</H1>");
  }
}