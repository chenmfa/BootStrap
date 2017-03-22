package learn.nio.netty5.timeserver;

public class TimeClient{
	
	public static void main(String[] args) {
	  int port = 8080;
		if(null != args && args.length>0){
	  	try {
	  		port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
      }
	  }
		
		new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();
  }
}
