package learn.threadutil.blockingqueue;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SingleBlockingQueue {	
	
	public static void main(String[] args) {
		//其中的fair参数如果为true时将以先进先出的模式访问队列
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
		//存入线程
		for(int i=0;i<2;i++){		
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						Double random = Math.random()*5000;
						long sleepTime = random.longValue();
						System.out.println("存入线程---Random is:"+random+"; sleepTime is:"+sleepTime+"， 当前时间："+System.currentTimeMillis()/1000);
						try {
							Thread.sleep(sleepTime);
							System.out.println(Thread.currentThread().getName()+": 准备放数据.，当前队列有数据："+queue.size()+"个。 当前时间："+System.currentTimeMillis()/1000);
							queue.put(Thread.currentThread().getName()+"--- ， 当前时间："+System.currentTimeMillis()/1000);
							System.out.println(Thread.currentThread().getName()+": 放数据完成.，当前队列有数据："+queue.size()+"个。 当前时间："+System.currentTimeMillis()/1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				}
			}).start();
		}
		
		//取数据线程
		new Thread(new Runnable() {			
			public void run() {
				while(true){
					Double random = Math.random()*5000;
					long sleepTime = random.longValue();
					System.out.println("取数据线程---Random is:"+random+"; sleepTime is:"+sleepTime+"， 当前时间："+System.currentTimeMillis()/1000);
					try {
						Thread.sleep(sleepTime);
						System.out.println(Thread.currentThread().getName()+": 准备取数据, 当前队列有数据："+queue.size()+"个。 当前时间："+System.currentTimeMillis()/1000);
						queue.take();
						System.out.println(Thread.currentThread().getName()+": 取数据完成, 当前队列有数据："+queue.size()+"个。 当前时间："+System.currentTimeMillis()/1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		}).start();
	  
  }
}
