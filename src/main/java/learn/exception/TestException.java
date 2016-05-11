package learn.exception;

public class TestException extends RuntimeException{
  private static final long serialVersionUID = 1L;
  
  private Integer errId = 1001; //异常id,默认为1001
  
  private String errInfo; //异常消息
  
  private String clsName; //异常类
  
  private String clsMethod; //异常方法
  
  private Exception previous;//上一个异常
  
  private String lineSeperator = System.getProperty("line.seperator");
  
  public TestException(String errInfo){
    this(1001,errInfo,"","");
  }

  public TestException(Integer errId, String errInfo, String clsName,
      String clsMethod) {
    this.errId = errId;
    this.errInfo = errInfo;
    this.clsName = clsName;
    this.clsMethod = clsMethod;
  }
  
  public String printTrace(){
    return printTrace(lineSeperator);
  }
  
  public String printTrace(String lineSeperator){
    StringBuilder sb = new StringBuilder();
    this.lineSeperator = lineSeperator;
    this.previous = this;
    packInfo(sb, "Calling sequence(Top to Bottom)");
    packInfo(sb,errId.toString());
    packInfo(sb, errInfo);
    sb.append("Affect class/method:");
    packInfo(sb, this.clsName,this.clsMethod);
    return sb.toString();
  }
  
  //String...messages 不固定参数个数，支持(String str1,String str2)和数组{str1, str2};
  private void packInfo(StringBuilder sb,String...messages){
    if(null != messages && messages.length>0){      
      for(String msg: messages){      
        sb.append(msg);
      }    
      sb.append(lineSeperator);
    }
  }

}
