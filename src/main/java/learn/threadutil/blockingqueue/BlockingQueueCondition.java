package learn.threadutil.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingQueueCondition {
	private final BlockingQueue<String> inQueue = new ArrayBlockingQueue<String>(10);
	
	private final BlockingQueue<String> outQueue = new ArrayBlockingQueue<String>(10);
	
	
	{
    //这里是匿名构造方法，只要new一个对象都会调用这个匿名构造方法，它与静态块不同，静态块只会执行一次，
    //在类第一次加载到JVM的时候执行
    //这里主要是让main线程首先put一个，就有东西可以取，如果不加这个匿名构造方法put一个的话程序就死锁了
		try {
	    outQueue.put("第一个");
    } catch (InterruptedException e) {
	    e.printStackTrace();
    }
		
	}
	
	public static void main(String[] args) {
		ExecutorService single =Executors.newSingleThreadExecutor();
		final BlockingQueueCondition bqc = new BlockingQueueCondition();
		single.execute(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<50;i++){
					bqc.inQueuePut();
				}
			}
		});
		
		for(int i=0;i<50;i++){
			bqc.outQueuePut();
		}
		
  }
	
	public void inQueuePut(){
		
		try {
	    outQueue.take();
	    for(int i=0;i<10;i++){
	    	System.out.println(Thread.currentThread().getName()+"---"+i);
	    }
	    inQueue.put("InQueue"+System.currentTimeMillis());
    } catch (InterruptedException e) {
	    e.printStackTrace();
    }
	}
	
	
	public void outQueuePut(){	
		try {
	    inQueue.take();
	    for(int i=0; i<5; i++){
	    	System.out.println(Thread.currentThread().getName()+"~~~"+System.currentTimeMillis());
	    }
	    outQueue.put("OutQueue: "+System.currentTimeMillis());
    } catch (InterruptedException e) {
	    e.printStackTrace();
    }
	}
}
