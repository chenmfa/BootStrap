package bean;

/**
 * @author chenmfa
 * @date 创建时间: 2016年9月28日 下午3:46:26
 * @description
 */
public class LogContentDTO {
	private String logIdentity; 		//日志标志
	private String logDesc;					//来源描述，可不填
	private String logLevel;				//日志级别
	private String logTime;					//日志时间
	private String logClass;				//日志发生类
	private String logContent;			//日志内容
	public String getLogIdentity() {
		return logIdentity;
	}
	public void setLogIdentity(String logIdentity) {
		this.logIdentity = logIdentity;
	}
	public String getLogDesc() {
		return logDesc;
	}
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getLogClass() {
		return logClass;
	}
	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	@Override
	public String toString(){
		String seperator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("消息类型:"+logLevel+seperator);
		sb.append("标志:"+logIdentity+seperator);
		sb.append("消息时间:"+logTime+seperator);
		sb.append("消息类:"+logClass+seperator);
		sb.append("消息体:"+logContent+seperator);
		sb.append("来源设备:"+ logDesc+seperator);
		return sb.toString();
	}
}
