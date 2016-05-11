package bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author chenmf13021
 * @date 2015年12月10日
 * @description 
 * 添加@XmlAccessorType的原因是jaxb默认 XmlAccessType.PUBLIC_MEMBER，
 * 会默认绑定所有包含public的字段，如果不设置为Field,那么会绑定getEmps和list的emps，照成重复绑定
 * 一般只设置需要转换的class即可
 * @XmlElement 可为字段设置标签的名称，不设置即以字段名作为标签名称
 */
@XmlRootElement(name="Companny")
@XmlAccessorType(XmlAccessType.FIELD) 
public class Company {
  private Integer serialNo; //
  private int companyId;
  private String companyName;
  @XmlElementWrapper(name="employees")
  @XmlElement(name="employee")
  private List<Employee> emps;
  public Company() {
  }
  public Company(int companyId, String companyName) {
    this.serialNo=0;
    this.companyId = companyId;
    this.companyName = companyName;
  }
  public Company(int SerialNo,int companyId, String companyName) {
    this.serialNo=SerialNo;
    this.companyId = companyId;
    this.companyName = companyName;
  }
  public int getCompanyId() {
    return companyId;
  }
  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }
  public String getCompanyName() {
    return companyName;
  }
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  public String toString(){
    return "Company[SerialNo: "+serialNo+" companyId: "+companyId+" Companyname: "+companyName+"]";
  }
  public Integer getSerialNo() {
    return serialNo;
  }
  public void setSerialNo(Integer serialNo) {
    this.serialNo = serialNo;
  }
  public List<Employee> getEmps() {
    return emps;
  }
  public void setEmps(List<Employee> emps) {
    this.emps = emps;
  }
  
  @Override
  public int hashCode() {
  	StringBuilder sb = new StringBuilder();
    sb.append(((Integer)companyId).hashCode());
    sb.append(((Integer)serialNo).hashCode());
    sb.append(((Integer)serialNo).hashCode());
    sb.append(companyName.hashCode());
    return Integer.parseInt(sb.toString());
  }
}
