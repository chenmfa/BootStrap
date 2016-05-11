package learn.scheduledtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("springTask")
public class SpringTaskSchedule {
	
	private static final Logger log = 
			LoggerFactory.getLogger(SpringTaskSchedule.class);
	
	public void springTask(){
		log.info("SpringScheduledTask: "+ System.currentTimeMillis());
	}
}
