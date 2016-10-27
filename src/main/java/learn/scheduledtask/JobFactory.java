package learn.scheduledtask;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * @author chenmfa
 * @date 创建时间: 2016年10月24日 下午2:56:36
 * @description
 */
public class JobFactory extends AdaptableJobFactory{
	
  //这个对象Spring会帮我们自动注入进来,
  @Autowired
  private AutowireCapableBeanFactory capableBeanFactory;
  
	@Override
	public Object createJobInstance(TriggerFiredBundle bundle) throws Exception{
		//通过父类获取job的实例
		Object jobInstance = super.createJobInstance(bundle);
		//调用AutowiredCapableBeanFactory来设置spring的自动注入
		capableBeanFactory.autowireBean(jobInstance);
		return jobInstance;
		
	}
}
