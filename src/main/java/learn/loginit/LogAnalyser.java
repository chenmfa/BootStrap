package learn.loginit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bean.LogContentDTO;

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
	
	private static Map<String,List<LogContentDTO>> logMap = new HashMap<String,List<LogContentDTO>>();
	
	private static List<LogContentDTO> logcontentAllList = new ArrayList<LogContentDTO>();
	
	private static ThreadLocal<Long> currentFileIndex = new ThreadLocal<Long>();
		
	public static void main(String[] args) {
		LogAnalyser.analyse(new File("D:\\base\\netty.log"));
		List list = LogAnalyser.getFilterLog("C2:B3:0F:7E:B0:F2");
		System.out.println(list);
  }
	
	public static void analyse(File file,String mode,String filterPattern){
		if(null == mode){
			mode= "r";
		}
		if(null == file){
			logger.error("传入文件信息为空");
		}
		RandomAccessFile random = null;
		try {
	    random = new RandomAccessFile(file, mode);
      String line;
      while((line = random.readLine()) != null ){
      	line = new String(line.getBytes("ISO8859-1"),"UTF-8");
      	//logger.info(line);
      	//\\[([^\\]]*)\\] 这个是[日志类型] [时间][处理类][唯一性标签][设备类型]
      	Pattern pattern = Pattern.compile("\\[([^\\]]*)\\] \\[([^\\]]*)\\]\\[([^\\]]*)\\](\\[([^\\]]*)\\])?(\\[([^\\]]*)\\])?(.[^\\[\\]]*)");
      	Matcher match = pattern.matcher(line);
      	if(match.find()){
      		int groupCount = match.groupCount();
      		String fullMsg = getNoSpaceData(match.group(0));
      		String logLevel = getNoSpaceData(match.group(1));
      		String logTime = getNoSpaceData(match.group(2));
      		String logClass = getNoSpaceData(match.group(3));
      		String logIdentity = getNoSpaceData(match.group(5));
      		String logDesc = getNoSpaceData(match.group(7));
      		String logContent= getNoSpaceData(match.group(groupCount));

      		if(logClass.indexOf("com.dsmzg")>=0 && null != logIdentity && logIdentity.length()>12){
      			LogContentDTO log = new LogContentDTO();
      			log.setLogDesc(logDesc);
      			log.setLogContent(logContent);
      			log.setLogClass(logClass);
      			log.setLogIdentity(logIdentity);
      			log.setLogLevel(logLevel);
      			log.setLogTime(logTime);
      			//System.out.println(log);
      			logcontentAllList.add(log);
      			//根据自定义的filterpattern来分组
      			String key = logIdentity.substring(0,17);
      			if(logMap.containsKey(key)){
      				logMap.get(key).add(log);
      			}else{
      				List<LogContentDTO> logcontentList = new ArrayList<LogContentDTO>();
      				logcontentList.add(log);
      				logMap.put(key, logcontentList);
      			}
      		}
      	}
      }
      long pointer = random.getFilePointer();
      currentFileIndex.set(pointer+3l);
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
		analyse(file,null,null);
	}
	
	public static void analyse(File file,String pattern){
		analyse(file,null,pattern);
	}
	
	public static void analyse(String fileName){
		analyse(new File(fileName));
	}
	
	public static void analyse(String fileName,String pattern){
		analyse(new File(fileName),pattern);
	}
	
	public static List<LogContentDTO> getFilterLog(String key){
		return logMap.get(key);
	}
	
	public static String getNoSpaceData(String data){
		return (null != data)?data.trim():"";
	}

	private static class AnalyserHolder{
		private static  final LogAnalyser instance =new LogAnalyser();		
	}
	
	public static LogAnalyser getInstance(){
		return AnalyserHolder.instance;
	}
}
