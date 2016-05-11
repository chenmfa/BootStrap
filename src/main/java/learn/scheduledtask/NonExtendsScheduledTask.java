package learn.scheduledtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonExtendsScheduledTask {
	
	private static final Logger log = 
			LoggerFactory.getLogger(NonExtendsScheduledTask.class);
	
	public void schedule(){
		log.info("NonExtendsSchedule: "+System.currentTimeMillis());
	}
}
