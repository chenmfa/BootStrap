package learn.loginit;

import java.io.File;
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

@SuppressWarnings("unchecked")
public class LogUtil {
	
	private static final ConcurrentMap<String, Level> logMap = new ConcurrentHashMap<String, Level>();
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogUtil.class);
	
	/**
	 * 
	 */
	public static void logSettingInit(){
		final String log4jdir = System.getProperty("user.dir")
	      +File.separator+"src"+File.separator+"main"+File.separator
	      +"webapp"+File.separator+"WEB-INF"
	      +File.separator+"conf"+File.separator+"log4j.properties";
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
	
	public static Logger changeLogger(String loggerName, String levelVal){
		if(StringUtils.isBlank(loggerName)){
			logger.info("需要修改的日志级别名称为空");
			return null;
		}
		Logger target =getLogger(loggerName);
		Level level;
		try {
	    int logLevel = Integer.parseInt(levelVal);
	    level = Level.toLevel(logLevel);
    } catch (NumberFormatException e) {
    	level = Level.toLevel(levelVal);
    }
  	target.setLevel(level);
  	logMap.put(logger.getName(), level);
		return target;
	}
	
	public static Map<String, Level> getLogMap(){
		return logMap;
	}
	
	 /**
	  * @return List<Logger>
	  * @description 获取当前的日志列表
	  */
  public static Map<String,String> getLogList(boolean... allLog){  
    //日志列表  
//    List<Logger> loggers = new ArrayList<Logger>();
  	Map<String,String> map = new HashMap<String, String>();
    //获取根日志  
    Logger rootLogger = Logger.getRootLogger();       
    //loggers.add(rootLogger);  
    map.put(rootLogger.getName(),rootLogger.getLevel().toString());        
    //获取当前所有的日志对象  
    Enumeration<Logger> enumer = (Enumeration<Logger>)rootLogger.getLoggerRepository().getCurrentLoggers();               
    while(enumer.hasMoreElements())  
    {  
			Logger childlogger = enumer.nextElement();
			String name = childlogger.getName();
			Level level = childlogger.getLevel();
			if(null ==level && (allLog.length == 0 || !allLog[0])){
				continue;
			}
			map.put((null!=name)?name:"",(null != level)?level.toString():"");  
//		loggers.add(logger);  
    }        
    return map; 
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
