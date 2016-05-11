package learn.xmltools;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import learn.BaseLearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bean.Company;
import bean.Employee;


/**
 * @author chenmf13021
 * @date 2015年12月9日
 * @description xml与对象之间的互相转换JAXBContext
 * @description 
 */
public class Xml_ObjectTransfer extends BaseLearn{
  private Logger log =LoggerFactory.getLogger(Xml_ObjectTransfer.class);
  
  public static void main(String[] args){
    Xml_ObjectTransfer xo = new Xml_ObjectTransfer();
    Company company = new Company(1,1,"陈苗发");
    Employee emp1 = new Employee(1,"员工1");
    Employee emp2 = new Employee(2,"员工2");
    List<Employee> empss = new ArrayList<Employee>();
    empss.add(emp1);
    empss.add(emp2);
    company.setEmps(empss);
    String xml = xo.convertObjectToXml(company, "UTF-8");
    System.out.println(xml);  
    Company resultCompany = xo.convertXmlToObject(xml, Company.class);
    System.out.println(resultCompany);
  }
  
  /**
   * @description JAXBContext转换xml为对象
   */
  @SuppressWarnings("unchecked")
  public <T>T convertXmlToObject(String xml, Class<T> clz){
    T t= null;
    try {
      JAXBContext jaxb = JAXBContext.newInstance(clz);
      Unmarshaller marshaller = jaxb.createUnmarshaller();      
      t = (T)marshaller.unmarshal(new StringReader(xml));
    } catch (Exception e) {
      log.info("xml转化对象失败",e);
    } 
    return t;
  }
  
  /**
   * @description JAXBContext转换对象为xml
   */
  public <T> String convertObjectToXml(T t,String encoding){
    String xml="";
    try {
      JAXBContext jaxb = JAXBContext.newInstance(t.getClass());
      Marshaller marshaller = jaxb.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding); //设置编码格式
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);//设置是否格式化输出
      
      StringWriter sw = new StringWriter();
      marshaller.marshal(t, sw);//注：这里是一个待转换的对象
      xml= sw.toString();
    } catch (Exception e) {
      log.info("对象转化xml失败",e);
    }
    return xml;
  }

}
