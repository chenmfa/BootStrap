package learn.nio.netty5.timeserver;

public class TimeServer {
	
	public static void main(String[] args) {
	 int port = 8080;
	 
	 if(args != null && args.length != 0){
		 try {
	    port = Integer.parseInt(args[0]);
		 } catch (NumberFormatException e) {
		 }
	 }
	 
	 MultiplexerTimeServer timeserver =  new MultiplexerTimeServer(port);
	 new Thread(timeserver, "NIO-MultiplexerTimeServer-001").start();
  }
}
