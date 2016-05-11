package learn.threadutil;

import java.util.HashMap;
import java.util.Map;


/**
 *@description 测试线程情况下，那种状况会导致变量错乱 
 */
public class BasicTest extends Thread{
	
	private int a= 11;
	
	private boolean flag=true;
	
	//private String msg;
	private Map map;
	
/*	public BasicTest(boolean flag,String msg){
		this.flag = flag;
		this.msg = msg;
	}*/
	
	public BasicTest(boolean flag,Map msg){
		this.flag = flag;
		this.map = msg;
	}
	
	public void run(){
		
		a =21;		
		System.out.println("当前线程: "+Thread.currentThread().getName());
		if(flag){

			try {
	      Thread.sleep(3000);
      } catch (InterruptedException e) {
	      e.printStackTrace();
      }
		}else{
			a=22;
		}
		/*System.out.println(a+" and msg is"+msg);*/
		System.out.println(a+" and msg is"+map.get("msg"));
	}
	
	
	public static void main(String[] args) {
		String msg = "cc is do";
		Map<String, String> ma = new HashMap<String, String>();
		ma.put("msg", msg);
	  new BasicTest(true,ma).start();
	  msg="cc is uo";
	  ma.put("msg", msg);
	  new BasicTest(false,ma).start();
  }
}
