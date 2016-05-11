package learn.threadutil;

import java.util.LinkedList;
import java.util.List;

public class MyThreadPool {

	private int workNum = 5;
	private WorkThread[] workThrads;
	private List<Runnable> taskQueue = new LinkedList<Runnable>();
	private Thread currentThread;
	
	//
	public MyThreadPool() {	  
		this(5);		
	}

	// 指定大小线程池
	public MyThreadPool(int workNum) {
	  currentThread = Thread.currentThread();
		if (workNum <= 0) {
			workNum = this.workNum;
		} else {
			this.workNum = workNum;
		}
		workThrads = new WorkThread[workNum];
		for(int i=0;i< workThrads.length;i++){
		  workThrads[i] = new WorkThread();
		  workThrads[i].start();
		}
	}

	// 执行单个任务
	public void execute(Runnable run) {
		synchronized (taskQueue) {
			taskQueue.add(run);
			//taskQueue.notify();
		}
	}

	// 执行多个任务
	public void execute(List<Runnable> list) {
		synchronized (taskQueue) {
			taskQueue.addAll(list);
			taskQueue.notify();
		}
	}

	public void destroy() {	  
	  System.out.println("Task Queue is "+ taskQueue);
		if (taskQueue.isEmpty()) {
		  System.out.println("Task Queue is Empty.");
			for (WorkThread workThrad : workThrads) {
				workThrad.stopWork();
				workThrad = null;
			}
			taskQueue.clear();
		}else{
		  try {
		    //Thread.currentThread().wait(1000);
		    //Thread.currentThread().join(3000);// 超时停止
		    Thread.sleep(1000);
		    destroy();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
		}
	}

	class WorkThread extends Thread {
		boolean isRunning = true;

		@Override
		public void run() {
			if (isRunning) {
				synchronized (taskQueue) {
					if (taskQueue.isEmpty()) {
						try {
							taskQueue.wait(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (!taskQueue.isEmpty()) {
						Runnable run = taskQueue.remove(0);
						if (run != null) {
							run.run();
						}
						run = null;
					}
				}
			}
		}

		public void stopWork() {
			isRunning = false;
		}
	}

	public static void main(String[] args) {
		MyThreadPool pool = new MyThreadPool(3);
		pool.execute(new Task());
		pool.execute(new Task());
		pool.destroy();
	}

	static class Task implements Runnable {
		private static int i = 1;

		@Override
		public void run() {
			System.out.println("任务 " + (i++) + " 完成");
		}
	}
}
