package test;

/**
 * @author chenmf
 * @date 2015年11月20日
 * @description 测试接口的多继承.确定->接口是可以多继承的,属性必是static final类型
 */
public class MutiExtensions implements InterfaceZero{
  public static void main(String[] args) {
    System.out.println(name);
  }
  public void tellLies() {
    System.out.println("System 1");
    
  }

  public void goTown() {
    System.out.println("Systetm 2");
  } 
}

interface Interface1{
  public static final String name="chenmfa";
  public void tellLies();
}
interface Interface2{
  public static final String name="chenmfa13021";
  public void goTown();
}

interface InterfaceZero extends Interface1, Interface2{
  String name="chairman";
}