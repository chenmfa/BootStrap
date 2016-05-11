package learn.xmltools;

import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import learn.BaseLearn;
import learn.exception.TestException;
import learn.util.ClassUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.DataBinder;

import com.alibaba.fastjson.JSON;

import bean.Company;

/**
 * @author chenmf13021
 * @date 2015年12月18日
 * @description 使用dom4j将object转换成s
 */
public class Dom4jXml_Obj extends BaseLearn{
  
  private static Logger log =LoggerFactory.getLogger(Dom4jXml_Obj.class);
  
  public static void main(String[] args) {
    Dom4jXml_Obj xml_obj= new Dom4jXml_Obj();
    Document doc = xml_obj.getXMlFileDoc("toolbox.xml");
    Object obj = xml_obj.parse(doc.getRootElement());
    System.out.println(JSON.toJSONString(obj));
    Map<Object, Object> map = Dom4jXml_Obj.parseXmlStringToMap("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
          +"<Companny>"
          +"<serialNo>1</serialNo>"
          +"<companyId>1</companyId>"
          +"<companyName>陈苗发</companyName>"
          +"</Companny>");
    log.info(map.toString());
    Company company = (Company)Dom4jXml_Obj.convertMapToObject(map, Company.class);
    log.info(company.toString());
  }
  
  public Document getXMlFileDoc(String fileName){
    SAXReader sax= new SAXReader();
    Document doc;
    try {
      //this.getClass().getResource("").getPath()  获取到的是当前类的目录，classLoader获取的是class目录
      String rootPath2= this.getClass().getClassLoader().getResource("").getPath();
      String rootPath =Dom4jXml_Obj.class.getClassLoader().getResource("").getPath();
      InputStream fileStream = 
          new BufferedInputStream(new FileInputStream(new File(rootPath2+"xml"+File.separator+fileName)));   
      doc = sax.read(fileStream);
      return doc;
    } catch (FileNotFoundException e) {
      log.error("未找到文件", e);
    } catch (DocumentException e) {
      log.error("sax读取生成文档对象异常", e);
    }
    return null;
  }
  
  /**
   * @description 使用dom4j转化xml为map映射
   */
  @SuppressWarnings("unchecked")
  public static Map<Object,Object> parseXmlStringToMap(String xml){
    Map<Object,Object> map = new HashMap<Object,Object>();
    Document doc;
    try {
      doc = DocumentHelper.parseText(xml);
      Element root = doc.getRootElement();
      List<Element> elements = root.elements();    
      Iterator<Element> iterator = elements.iterator();
      while(iterator.hasNext()){
        Element node = iterator.next();
        String nodeVal = node.getTextTrim();
        String nodeName = node.getName();
        map.put(nodeName, nodeVal);
      }
    } catch (DocumentException e) {
      log.info("转换成xml格式失败", e);
      e.printStackTrace();
    }   
    return map;
  }
  
  /**
   * @description 转化map映射为简单类
   */
  public static Object convertMapToObject(Map<Object,Object> map,Class<?> clz){
    Object obj;
    try {
      obj = clz.newInstance();
      if(null != map && map.size()>0){
        for(Map.Entry<Object,Object> entry:map.entrySet()){
          Object key = entry.getKey();
          Object value = entry.getValue();
          if(null !=value){           
            PropertyDescriptor pds =  new PropertyDescriptor(key.toString(), clz);
            Class<?> propertyType = pds.getPropertyType();
            System.out.println(propertyType.equals(Integer.class));
            //spring将request传入的值放入bean是在BeanWrapperImpl里可以参考
            //BeanWrapper ss =new BeanWrapperImpl();
            propertyType = ClassUtil.resolvePossiblePrimitiveType(propertyType);
            
            String name = propertyType.getSimpleName().toLowerCase();
            System.out.println("name is "+name);
            //switch----case
            String valueStr = value.toString();
            switch(name){          
            case "short":
              value =Short.valueOf(valueStr);
              break;
            case "integer":           
              value=Integer.valueOf(valueStr);
              break;
            case "double":
              value=Double.valueOf(valueStr);
              break;
            case "float":
              value=Float.valueOf(valueStr);
              break;
            case "long":
              value = Long.valueOf(valueStr);
              break;
            case "boolean":
              value=Boolean.valueOf(valueStr);
              break;
            case "string":
              break;
            default:
              throw new TestException("类型不匹配");
            }
            Method writeMethod = pds.getWriteMethod();
            writeMethod.invoke(obj,value);
          }
        }
      }
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  /**
   * @description 使用dom4j转化对象为xml字符串
   */
  public <T> String convertObjetcToXml(T t){
    
    return "";
  }
  
  /**
   * @param xml 字符串
   * @return String
   * @throws DocumentException 
   * @throws IOException 
   * @description 格式化传入的xml字符串
   */
  public String getFormattedXml(String xml) throws IOException, DocumentException{
    //SAX 读入初始化
    SAXReader reader = new SAXReader();
    
    //创建字符输入流
    StringReader sr = new StringReader(xml);
    
    Document doc= reader.read(sr);
    //创建输出格式
    OutputFormat format = OutputFormat.createPrettyPrint();
    format.setEncoding("UTF-8");
    //如果包含xml的版本信息，则去除
    format.setSuppressDeclaration(true);
    
    //创建输出目标
    StringWriter sw = new StringWriter();
    XMLWriter writer= new XMLWriter(sw, format);
    writer.write(doc);
    writer.flush();
    writer.close();
    return writer.toString();
  }
  
  public Object parse(Element root) {
    List<?> elements = root.elements();
    if (elements.size() == 0) {
      // 没有子元素
      return root.getTextTrim();
    } else {
      // 有子元素
      String prev = null;
      boolean guess = true; // 默认按照数组处理
       
      Iterator<?> iterator = elements.iterator();
      while (iterator.hasNext()) {
        Element elem = (Element) iterator.next();
        String name = elem.getName();
        if (prev == null) {
          prev = name;
        } else {
          guess = name.equals(prev);
          break;
        }
      }
      iterator = elements.iterator();
      if (guess) {
        List<Object> data = new ArrayList<Object>();
        while (iterator.hasNext()) {
          Element elem = (Element) iterator.next();
          ((List<Object>) data).add(parse(elem));
        }
        return data;
      } else {
        Map<String, Object> data = new HashMap<String, Object>();
        while (iterator.hasNext()) {
            Element elem = (Element) iterator.next();
            ((Map<String, Object>) data).put(elem.getName(), parse(elem));
        }
        return data;
      }
    }
  }

}
