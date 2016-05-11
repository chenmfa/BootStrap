package learn.threadutil;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author chenmf13021
 * @date 2015年11月26日
 * @description CountDownLatch用来管理一组线程，当他们都完成的时候执行某个动作
 * 原理： 只需要在主线程里面调用他的await()方法，然后让其他线程调用countDown方法，等到其他线程都执行完countDown方法了,
 * await()再返回
 * 
 * @description 线程池描述(支持额外添加ThreadFactory参数)
 * 
 * 固定线程池Executors.newFixedThreadPool(seg);
 * 创建固定数量的线程, 可重用，适用于服务器上的固定的，正规并发线程
 * 带缓存的线程池Executors.newCachedThreadPool(); 
 * 原理： 缓存已经初始化的线程池，如果有60秒没被使用，则移除该线程,可重用，用于生存期短的异步型任务
 * 单个线程池,与固定线程池创建的不同Executors.newSingleThreadExecutor(); 不同于fixed(1)
 * 定时执行的线程池Executors.newScheduledThreadPool(seg)和Executors.newSingleThreadScheduledExecutor();
 */


@SuppressWarnings({"unused"})
public class CountDownLatchDemo{
  
  private static final int seg=10;
  
  public static void main(String[] args) throws InterruptedException {
    Future<Boolean> future =new FutureTask<Boolean>(new Callable<Boolean>() {
      public Boolean call(){
        return true;
      }
    });
    CountDownLatch startSignal = new CountDownLatch(1);
    CountDownLatch endSignal=  new CountDownLatch(seg);
    //latchCount(startSignal, endSignal);
    threadPoolLatch(startSignal, endSignal);
    startSignal.countDown();
    System.out.println("开始");
    endSignal.await();
    System.out.println("结束");
  }
  
  public static void latchCount(CountDownLatch startSignal,CountDownLatch endSignal) 
      throws InterruptedException{
    for(int i=0;i<10;i++){
      new Thread(new Piolot(startSignal, endSignal)).start();
    }
  }
  
  public static void threadPoolLatch(CountDownLatch startSignal,CountDownLatch endSignal){
    //线程池， 支持额外添加ThreadFactory参数
    //固定线程池
    ExecutorService threadPool = Executors.newFixedThreadPool(seg); 
    //带缓存的线程池
    ExecutorService threadPool2 = Executors.newCachedThreadPool(); 
    //单个线程池,与固定线程池创建的不同
    ExecutorService threadPool3 = Executors.newSingleThreadExecutor(); 
    //定时执行的线程池
    ExecutorService threadPool4 = Executors.newScheduledThreadPool(seg); 
    ExecutorService threadPool5 = Executors.newSingleThreadScheduledExecutor();
    for(int i=0;i< 10; i++){
      threadPool.execute(new Piolot(startSignal, endSignal));
      //threadPool2.execute(new Piolot(startSignal, endSignal));
      //threadPool3.execute(new Piolot(startSignal, endSignal));
      //threadPool4.execute(new Piolot(startSignal, endSignal));
      //threadPool5.execute(new Piolot(startSignal, endSignal));
    }
  }
  
  public void print(Object obj){
    System.out.println();
  }
  
}

/**
 * 当类处于CountDownLatchDemo类里面时，需要将本类设置成静态类，因为静态方法中不能访问非静态内部类
 */
class Piolot implements Runnable{
  private CountDownLatch startSignal;  
  private CountDownLatch endSignal;
  
  public Piolot(CountDownLatch startSignal,CountDownLatch endSignal){
    this.startSignal = startSignal;
    this.endSignal = endSignal;      
  }

  public void run() {
    try {
      startSignal.await();
      Thread.sleep((long)(Math.random()*1000));
      System.out.println(Thread.currentThread().getName()+" has finished.");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }finally{
      endSignal.countDown();
      System.out.println(endSignal.getCount());
    }
  }   
}
