package bean;

/**
 * @author Sub
 * @date 创建时间: 2016年6月8日 上午10:11:04
 * @description
 */
public class LogDTO {
//日志操作类型 1.获取日志对象，不包括日志级别为空的。 2.获取所有对象。 3.修改日志级别	
	private int actionType; 
//日志操作	
	private String action;     
//日志节点名称	
	private String loggerName;
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLoggerName() {
		return loggerName;
	}
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}
	
}
