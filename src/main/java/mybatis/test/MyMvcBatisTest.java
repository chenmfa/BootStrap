package mybatis.test;

import java.util.List;

import javax.annotation.Resource;

import learn.BaseLearn;
import mybatis.dynamicproxy.impl.ProxyManagerImpl;
import mybatis.service.ICompanyService;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.naming.factory.BeanFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.Company;
@SuppressWarnings({"unused","resource"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/conf/applicationContext-mvc.xml"})
public class MyMvcBatisTest extends BaseLearn{
  private static Logger log =LoggerFactory.getLogger(MyMvcBatisTest.class);
  
  @Autowired
  private ICompanyService companyService;
  
/*  @Before
  public void before(){
    
  }*/
  
  @Test
  public void run(){
    System.out.println(11);
    List<Company> comList = companyService.queryCompanyAll();
    System.out.println(comList);
    log.info(comList.toString());
  }
  
  public static void main(String[] args){
    ApplicationContext application = new ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/conf/applicationContext-*.xml");
    ProxyManagerImpl bean = (ProxyManagerImpl)application.getBean("proxyManager");
    String result = bean.proxyTest("che");
    log.info("result is: "+result);
  }

}
