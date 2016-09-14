package learn.loginit;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;

@SuppressWarnings(value={"unchecked","unused"})
public class LogUtil {
	
	private static final ConcurrentMap<String, Level> logMap = new ConcurrentHashMap<String, Level>();
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogUtil.class);
	
	/**
	 * @throws URISyntaxException 
	 * @description Log4j的LogManager类里面默认加载classpath下面的log4j.xml文件，如果没有,则加载log4j.properties文件，
	 * @descritpion 用的是classloader获取资源的url路径，这样就不会因为是在jar包里面导致文件无法读取了，
	 * @reference log4j-1.2.15
	 */
	public static void logSettingInit() throws URISyntaxException{
		final String log4jdir = System.getProperty("user.dir")
	      +File.separator+"src"+File.separator+"main"+File.separator
	      +"webapp"+File.separator+"WEB-INF"
	      +File.separator+"conf"+File.separator+"log4j.properties";
	  //注：这里用URI的目的是为了当路径中有某些特殊字符的时候也能正常显示路径
    final String log4jClzdir = 
        (Class.class.getResource("/").toURI().getPath()+"log4j.properties");
    final URL log4jUrl = 
        (Class.class.getResource("/log4j.properties"));
    //URL resource = Class.class.getClassLoader().getResource("log4j.properties");
    //PropertyConfigurator.configure(resource);
		//PropertyConfigurator.configure(log4jClzdir);
    //PropertyConfigurator.configure(Class.class.getResource("/log4j.properties"));
		PropertyConfigurator.configure(log4jdir);
	}
		
	public static Logger getLogger(String loggerName){
		if(StringUtils.isBlank(loggerName) || "root".equalsIgnoreCase(loggerName)){
			return LogManager.getRootLogger();
		}
		return LogManager.exists(loggerName);
	}
		
	public static Logger addLogger(String... params){
		if(null == params || params.length == 0){
			return null;
		}else{
			String name = params[0];
			Logger target =getLogger(name);
			String levelVal = params.length>=2?params[1]:"1";
			Level level =Level.DEBUG;
			try {
	      int logLevel = Integer.parseInt(levelVal);
	      level = Level.toLevel(logLevel);
      } catch (NumberFormatException e) {
      	level = Level.toLevel(levelVal);
      }
      //List<LogLevel> log4jLevels = (List<LogLevel>)LogLevel.getLog4JLevels();
			target.setLevel(level);
			logMap.putIfAbsent(logger.getName(), level);
			return target;
		}
	}
	
	public static List<Logger> changeLogger(String loggerName, String levelVal){
		if(StringUtils.isBlank(loggerName)){
			logger.info("需要修改的日志级别名称为空");
			return null;
		}
		List<Logger> targetList =getLogList(loggerName);
		Level level;
		try {
	    int logLevel = Integer.parseInt(levelVal);
	    level = Level.toLevel(logLevel);
    } catch (NumberFormatException e) {
    	level = Level.toLevel(levelVal);
    }
		if(null != targetList){
			for(Logger target :targetList){				
				target.setLevel(level);
				logMap.put(logger.getName(), level);
			}
		}
		return targetList;
	}
	
	public static Map<String, Level> getLogMap(){
		return logMap;
	}
	
	 /**
	  * @return List<Logger>
	  * @description 获取当前的日志列表
	  */
  public static List<Logger> getLogList(String loggerName){  
    //日志列表  
//    List<Logger> loggers = new ArrayList<Logger>();
  	Map<String,String> map = new HashMap<String, String>();
  	List<Logger> loggerList= new ArrayList<Logger>();
    //获取根日志  
    Logger rootLogger = Logger.getRootLogger();       
    //loggers.add(rootLogger);
    if(StringUtils.isBlank(loggerName) || "root".equalsIgnoreCase(loggerName)){   	
    	map.put(rootLogger.getName(),rootLogger.getLevel().toString());
    }else{   	
    	//获取当前所有的日志对象  
    	Enumeration<Logger> enumer = (Enumeration<Logger>)rootLogger.getLoggerRepository().getCurrentLoggers();               
    	while(enumer.hasMoreElements())  
    	{  
    		Logger childlogger = enumer.nextElement();
    		String name = childlogger.getName();
    		Level level = childlogger.getLevel();
    		if(StringUtils.isNotBlank(name) && !name.startsWith(loggerName)){
    			continue;
    		}
    		map.put((null!=name)?name:"",(null != level)?level.toString():"");
    		loggerList.add(childlogger);
//		loggers.add(logger);  
    	}      
    }
    return loggerList; 
  }
  
  /**
   * @param loggerName
   * @description 重置日志级别为上一次的日志级别
   */
  public void resetLogLevel(String loggerName){
  	Logger target = getLogger(loggerName);
  	if(null != target){
  		Level level = logMap.get(loggerName);
  		if(null !=level){
  			target.setLevel(level);
  		}
  	}
  }
}
