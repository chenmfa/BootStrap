package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.ReplicationDriver;

import bean.Company;

public class Test {
	
	private static final Logger logger = LoggerFactory.getLogger(Test.class);
	
  public static void main(String[] args) throws IOException, SQLException{
  	System.out.println("13585425616".matches("((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[6-8])|(17[0|1|3])|(147))\\d{8}$"));
  	//BouncyCastleProvider bcs = new BouncyCastleProvider();
  	System.out.println(System.getProperty("java.class.path"));
  	System.out.println(System.currentTimeMillis()/1000);
  	StringBuffer unicode = new StringBuffer();
  	 String sdasdad ="yao jie(15958125171)";
    for (int i = 0; i < sdasdad.length(); i++) {
 
        // 取出每一个字符
        char c = sdasdad.charAt(i);
 
        // 转换为unicode
        unicode.append("\\u" + Integer.toHexString(c));
    }
  	
    System.out.println(unicode);
		System.out.println("2超级管理员386130459708447.png".startsWith("2超级管理员"));
  	System.out.println("D:\\Software\\LearningSoftware\\apache-tomcat-7.0.42\\webapps\\base\\upload/workeruserphoto/2超级管理员385523540708073.png".length());
  	String qrText ="http://dsmjd.com/?x=lock520|11111111|E5:3F:33:43:88:EC|lock";
  	String macStrWithOutSplit="E53:F334388EC";
  	String lockType = "普通锁";
  	if("普通锁".equals(lockType)){
			int equalIndex = qrText.indexOf("=");
			if(equalIndex>0){
				String variable = qrText.split("=")[1];
				String qrTextMacAddress = variable.split("\\|")[2].replace(":", "");
				if(!qrTextMacAddress.equals(macStrWithOutSplit)){
					System.out.println("生成的二维码的MAC地址与二维码下方的MAC地址出现不一致，请重新操作或者联系波波");
					return ;
				}
			}else{
				System.out.println("需要打印的二维码不正确");
				return ;
			}
		}
  	for(int i=1;i<12;i++){
  		System.out.print((40+i*27)+" ");
  	}
  	System.out.println("DF-FE-2C-8B-23-87".length());
  	JOptionPane.showMessageDialog(null, "MAC地址读取出错，请重试", "MAC地址读取出错，请重试", JOptionPane.WARNING_MESSAGE); 
  	Object[] options = { "确定"}; 
  	JOptionPane.showOptionDialog(null, "MAC地址读取出错，请重试", "Warning", 
  			JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
  			null, options, options[0]); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		System.out.println(formatter.format(null));
  	String regesxxx ="{\"username\":\"sss\",\"password\":\"dddd\"}";
  	//对于正则表达式， \\数字表示对应的捕获组 ?:表示不捕获，?<quoteb>表示别名
  	//\k<name> 表示引用命名为 name 的捕获组捕获到的内容，在Java7 中；
    //1: //这里同上面的正则表达式一样，只不过给 \1 取了个名字叫 repeat
   // 2: //然后用 \k<repeat> 代替 \1 而已
    //3: Pattern pattern = Pattern.compile("\\b(?<repeat>\\w+)\\b((\\s+)\\k<repeat>\\b)+");
  	String regex = "((?<quotea>\"?)(?<key>[^:\"]*)\\2:(?<quoteb>\"?)(?<value>[^:\"]*)\\4,?)";
  	Matcher matche = Pattern.compile(regex).matcher(regesxxx);
  	//System.out.println("asadadassd"+matche.matches());
  	while(matche.find()){  
  		System.out.println(matche.group("key")+"-------"+matche.group("value"));
  		System.out.println(matche.group(1)+"******* "+matche.group(2)+"   -------"+matche.group(3)+"\\\\  "+matche.group(4)+"|||  "+matche.group(5));
  	}
  	Map<String, String> ma = new HashMap<String, String>();
  	Long maic = 5173774730493910600L;
  	ma.put("4532984075973813095", "1");
  	ma.put("5173774730493910600", "1");
  	System.out.println(ma.containsKey(maic));
	 ReplicationDriver driver = new ReplicationDriver();

   Properties props = new Properties();

   // We want this for failover on the slaves
   props.put("autoReconnect", "true");

   // We want to load balance between the slaves
   props.put("roundRobinLoadBalance", "true");

   props.put("user", "foo");
   props.put("password", "bar");
  	Connection connss =
        driver.connect("jdbc:mysql:replication://master,slave1,slave2,slave3/test",
            props);
  	
  	//测试字符串分隔
  	String splitStr = "I am: a]dof[her";
  	String[] splitArr = splitStr.split("[;:\\s]");
  	System.out.println(splitArr);
  	System.out.println("\\u"+Integer.toHexString(')'));
  	System.out.println("\u0028\u0029");
  	System.out.println("-----------");
  	int assasa= 0xE4;
  	System.out.println(assasa);
  	macToByte("E4:38:4E:3A:52:90");
  	int hanzi= 0x2E80;
  	while(hanzi <= 0xFE4F){
  		hanzi++;
  		String hexString = Integer.toHexString(hanzi);
  		String sssss= "\\u"+hexString;
  		String word = decodeUnicode(sssss);
  		if(StringUtils.isBlank(word)){
  			System.out.println(sssss+"-----"+word);
  		}
  	}
  	
  	
  	System.out.println("\u3300");
  	String fakeLockString="fe 02 07 00 16 0b d4 e6 bb 27 49 c9 00 00 00 00 00 00 00 00 00 00 00 00 00 00 02 03 da";
  	String[] fakeLockArr  = fakeLockString.split(" ");
  	byte[] by = new byte[fakeLockArr.length];
  	int p = 0;
  	for(String fakeString: fakeLockArr){
  		int intVal = Integer.parseInt(fakeString,16);
  		byte b =(byte)intVal; 
  		by[p] = b;
  		p++;
  	}
  	//System.out.println(by);
  	int parseInt = Integer.parseInt("1", 16);
  	//对象的hashCode与地址有关，需要改正
  	Company company = new Company();
  	company.setCompanyId(1);
  	Company company2 = new Company();
  	company2.setCompanyId(1);
  	System.out.println(company.hashCode());
  	System.out.println(company2.hashCode());
  	
  	String testRegex = "C53";
  	System.out.println("ISMatch: "+testRegex.matches("^[DC]\\d{1,2}"));
  	
  	String aint =Long.toString((new Integer(44).byteValue()) & 0xff,16);
    File flow = new File("D:/workspace/HS_Job/BootStrap/target/classes/flow.txt");
    System.out.println(flow.getAbsolutePath());
    if(!flow.exists()){
      throw new FileNotFoundException("flow.txt不存在");      
    }
    BufferedReader bbb = new BufferedReader(new InputStreamReader(new FileInputStream(flow)));
    StringBuffer sb = new StringBuffer();
    String xml= "";
    while(null !=(xml=bbb.readLine())){
      sb.append(xml.trim());
    }
    System.out.println(sb.toString());
    bbb.close();
    
    System.out.println("623906".matches("6239\\d{2}"));
    try {
      Class.forName("com.mysql.jdbc.driver");
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hspub?useUnicode=true&amp;characterEncoding=utf8&amp;useServerPrepStmts=false&amp;rewriteBatchedStatements=true");
      CallableStatement prepareCall = conn.prepareCall("{call testProc(?,?)}");
      prepareCall.setInt(1, 1001);
      prepareCall.registerOutParameter(2, Types.INTEGER);
           
    } catch (ClassNotFoundException e1) {
      e1.printStackTrace();
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    System.out.println(Test.class.getResource("/").getPath());
    //基本数据类型没有引用，无论Integer还是int，而对象有引用
    Company[] coms= new Company[1];
    coms[0] =new Company();
    coms[0].setCompanyId(1001);
 /*   for(Company com:coms){
      //com= new Company();
      com.setCompanyId(1);
    }*/
    //与上述一致
    for(int i=0;i<coms.length;i++){
      Company com= coms[i];
      com.setCompanyId(2222);
    }
    System.out.println(coms);
    //简单密码正则
    System.out.println("hasdj!dsdsf".matches("[1-9A-Za-z@_#]{6,}")?"匹配":"不匹配");
    //简单匹配目录
    Matcher matcher = Pattern.compile("(\\/{1}[^\\/]+)+\\/?").matcher("//home/aaa");
    System.out.println(matcher.find());
    System.out.println(matcher.group());
    generateXML();
    String allList="1,3,9,18,";
    String a = "18";
    if(allList.matches(".*,?"+a+",.*")){
      System.out.println("匹配");
    }
    Company cm= new Company(3,5,"小陈");
    Integer serialNo = cm.getSerialNo();
    cm.setSerialNo(10);;
    System.out.println("修改后的公司流水： "+cm.getSerialNo());
    Integer i= null;
    if(null == i){
      i=0;
    }
    switch(i){
      case 1:
        System.out.println("值等于1");
        break;
      default:
        System.out.println("值不相等");
    }
    System.out.println(System.getProperty("java.class.path") );
    System.out.println(10>>>2);
    //regexTest();
    //mathcount();
    File f= new File("src/source/uv.txt");
    if(!f.exists()){
      return;
    }
    BufferedReader bfr= new BufferedReader(new FileReader(f));
    String str="";
    Map<Integer,String> map =new HashMap<Integer,String>();  
    while((str=bfr.readLine())!=null){
      String[] users= str.split(" ");
      int key=0;
      try{
        key =Integer.parseInt(users[1]);
      }catch(NumberFormatException e){
        System.out.println(e.getMessage());
      }
      String value= users[2];      
      map.put(key, value);
    }
    System.out.println(map.size());
  }

  public static void mathcount() {
    /**
     *2,3整除 
     */
    int i=2;
    int countMatch=0;
    while(true){
      if(i%2 ==0 || i%3 == 0){
        countMatch++;
        if(countMatch == 2333){
          System.out.println(i);
          break;
        }
      }
      i++;
    }
  }

  public static void regexTest() {
    //String regex= "(^//.|^/|^[a-zA-Z])?:?/.+(/$)?";
    String regex= "(\\/{1}\\w+)+\\/?";
    String path="/home/pub/workspace/log";
    Matcher matcher = Pattern.compile(regex,Pattern.CASE_INSENSITIVE).matcher(path);
    //取巧得最后的文件目录或名字
    System.out.println(matcher.group()+"\t");
    //System.out.println(matcher.matches());
    while(matcher.find()){
      System.out.print(matcher.groupCount()+"\t");
      System.out.println(matcher.group(1)+"\t");
     // System.out.println(matcher.replaceAll("1"));
    }
  }
  
  public void testDTO(){
    int i=0;
    InsRiskStepsDTO[] dtos=new InsRiskStepsDTO[0];
    System.out.println(i);
    for(InsRiskStepsDTO dto:dtos){
      i++;
      System.out.println(i);
    }
  }
  
  public static String generateXML(){
    Document doc= DocumentHelper.createDocument();
    
    Element root = doc.addElement("process");
    Element noEle = root.addElement("no");
    //这里的setAttribute已经不推荐使用了，因为他会覆盖已经存在的属性，所以用add
    root.addAttribute("xmlns", "http://jbpm.org/4.4/jpdl");
    Element msgEle = root.addElement("msg");
    noEle.setText("1");
    msgEle.setText("获取成功有关的信息");
    Element mapRoot = root.addElement("user");
    Element finalEle = mapRoot.addElement("text");
    finalEle.setText("获取文本");
    
    StringWriter sw = new StringWriter();
    OutputFormat of= OutputFormat.createPrettyPrint();
    of.setEncoding("UTF-8");
    
    XMLWriter xw =new XMLWriter(sw, of);
    try {
      xw.write(doc);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(sw.toString());
    return sw.toString();
  }
  
  public static void testSyncronised(){
  	Collections.unmodifiableMap(new HashMap<String, String>());
  }
  
  
  public static String decodeUnicode(String theString){
    char aChar;
    int len = theString.length();
    StringBuffer outBuffer = new StringBuffer(len);
    for (int x = 0; x < len;) {
        aChar = theString.charAt(x++);
        if (aChar == '\\') {
            aChar = theString.charAt(x++);
            if (aChar == 'u') {
                // Read the xxxx
                int value = 0;
                for (int i = 0; i < 4; i++) {
                    aChar = theString.charAt(x++);
                    switch (aChar) {
	                    case '0':
	                    case '1':
	                    case '2':
	                    case '3':
	                    case '4':
	                    case '5':
	                    case '6':
	                    case '7':
	                    case '8':
	                    case '9':
	                        value = (value << 4) + aChar - '0';
	                        break;
	                    case 'a':
	                    case 'b':
	                    case 'c':
	                    case 'd':
	                    case 'e':
	                    case 'f':
	                        value = (value << 4) + 10 + aChar - 'a';
	                        break;
	                    case 'A':
	                    case 'B':
	                    case 'C':
	                    case 'D':
	                    case 'E':
	                    case 'F':
	                        value = (value << 4) + 10 + aChar - 'A';
	                        break;
	                    default:
	                        throw new IllegalArgumentException(
	                                "Malformed   \\uxxxx   encoding.");
                    }

                }
                outBuffer.append((char) value);
            } else {
                if (aChar == 't')
                    aChar = '\t';
                else if (aChar == 'r')
                    aChar = '\r';
                else if (aChar == 'n')
                    aChar = '\n';
                else if (aChar == 'f')
                    aChar = '\f';
                outBuffer.append(aChar);
            }

        } else
            outBuffer.append(aChar);
    }
    return outBuffer.toString();
  }
  
  public static void macToByte(String str){
  	String[] arr =str.split(":");
  	for(String s: arr){  		
  		System.out.print(Integer.parseInt(s,16)+" ");
  	}
  }
}
