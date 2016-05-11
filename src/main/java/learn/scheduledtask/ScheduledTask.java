package learn.scheduledtask;

import learn.loginit.LogUtil;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Sub
 * @description 继承QuartzJobBean类
 */
public class ScheduledTask extends QuartzJobBean{
	
/*	static{
		LogUtil.logSettingInit();
	}*/
	
	private static final Logger log= LoggerFactory.getLogger(ScheduledTask.class);
	
	private int timeout=2000;
	
	private int count = 0;

	public void setTimeout(int timeout){
		this.timeout = timeout;
	}
	
  protected void executeInternal(JobExecutionContext context)
      throws JobExecutionException {
  	log.info("Task schedule begins: "+ System.currentTimeMillis());	  
  }
  
  

}
