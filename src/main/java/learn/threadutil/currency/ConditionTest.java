package learn.threadutil.currency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConditionTest {

	public static void main(String[] args) {
 
        // 创建并发访问的账户
        Account myAccount = new Account("95599200901215522", 10000);
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(6);
        Thread t1 = new DrawThread("张三", myAccount, 11000);
        Thread t2 = new SaveThread("李四", myAccount, 3600);
        Thread t3 = new DrawThread("王五", myAccount, 2700);
        Thread t4 = new SaveThread("老张", myAccount, 600);
        Thread t5 = new DrawThread("老牛", myAccount, 1300);
        Thread t6 = new SaveThread("胖子", myAccount, 2000);
        
        runTask(t1, pool);
        runTask(t2, pool);
        runTask(t3, pool);
        runTask(t4, pool);
        runTask(t5, pool);
        runTask(t6, pool);
        
/*        pool.execute(t2);
        pool.execute(t1);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        pool.execute(t6);*/
        // 关闭线程池
        ///pool.shutdown();

    }
	
	public static String runTask(Thread task, ExecutorService threadPool){
	  Future<?> future = threadPool.submit(task);
	  try {
      future.get(2, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      return "主线程在等待执行结果时被中断";      
    } catch (ExecutionException e) {
      return "主线程在等待异常结果, 但执行过程出现异常";
    } catch (TimeoutException e) {
      return "主线程执行任务超时,因此终端任务"+task.getName();
    }
	  return "执行成功";
	}
}
