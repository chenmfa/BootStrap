package learn.loginit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.RandomAccess;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenmfa
 * @date 创建时间: 2016年9月19日 下午12:55:49
 * @description
 */
public class LogAnalyser {
	
	static{
		try {
	    LogUtil.logSettingInit();
    } catch (URISyntaxException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}
	
	private static final Logger logger = LoggerFactory.getLogger(LogAnalyser.class);
	
	private static AtomicLong filePointer = new AtomicLong(); 
	
	public static void main(String[] args) {
		LogAnalyser.analyse(new File("D:\\log\\locknetty\\filelog.log"));
  }
	
	//priivate 
	
	public static void analyse(File file,String mode){
		if(null == mode){
			mode= "r";
		}
		if(null == file){
			logger.error("传入文件信息为空");
		}
		RandomAccessFile random = null;
		try {
	    random = new RandomAccessFile(file, mode);
	    System.out.println(random.getFilePointer());
      String line;
      while((line = random.readLine()) != null ){
      	logger.info(line);
      	Pattern pattern = Pattern.compile("\\[(.*)\\] \\[(.*)\\]\\[([^\\]]*)\\](.*)");
      	Matcher match = pattern.matcher(line);
      	if(match.find()){
      		String fullMsg = match.group(0);
      		String msgType = match.group(1);
      		String msgDate = match.group(2);
      		String msgClass = match.group(3);
      		String msg = match.group(4);
      		if(msgClass.indexOf("com.dsmzg.tcp")>=0){      			
      			System.out.println("消息类型:	"+msgType);
      			System.out.println("消息时间:	"+msgDate);
      			System.out.println("消息类: "+msgClass);
      			System.out.println("消息体: "+msg);
      		}
      	}
      	//logger.info(line+"--");
      }
      long pointer = random.getFilePointer();
      filePointer.set(pointer+3l);
    } catch (FileNotFoundException e) {
    	logger.error("文件不存在"+file.getName());
    }	catch (IOException e) {
    	logger.error("文件操作出错"+file.getName());
    }finally{
    	if(null != random){
    		try {
	        random.close();
        } catch (IOException e) {
        	logger.error("关闭文件流出错"+file.getName());
        }
    	}
    }
	}
	
	public static void analyse(File file){
		analyse(file,null);
	}

	private static class AnalyserHolder{
		//private 
	}
}
