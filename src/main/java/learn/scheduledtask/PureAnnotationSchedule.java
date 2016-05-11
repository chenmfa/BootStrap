package learn.scheduledtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PureAnnotationSchedule {
	
	private static final Logger log = 
			LoggerFactory.getLogger(SpringTaskSchedule.class);
	@Scheduled(cron="* * 8 * * ?")
	public void annotationSchedule(){
		
		log.info(Thread.currentThread().getName()+"--- Annotation Schedule: "+ System.currentTimeMillis());
		try {
	    Thread.sleep(30000);
    } catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
	}
}
