package learn.socket.basecommnnication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSide {
  
  private String serverIp;     //服务端ip地址
  
  private int serverPort;   //服务器端口
  
  public static void main(String[] arsg){
    new ClientSide().init();
  }
  
  public ClientSide(){
    this.serverIp = "192.168.224.94";
    this.serverPort = 3302;
  }
  
  public ClientSide(String serverIp, int serverPort){
    this.serverIp = serverIp;
    this.serverPort = serverPort;
  }
  
  public void init(){
    System.out.println("客户端启动");
    System.out.println("当客户端接收到服务端返回的字符串为ok时,客户端将退出");
    
    Socket socket = null;
    while(true){     
      try {
        socket =  new Socket(serverIp, serverPort); //new InetAddress("localhost"), 5050
        
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        System.out.println("输入一行字符");
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        String msg = br.readLine();
        dos.writeUTF(msg);
        
        String feedback = dis.readUTF();
        System.out.println(feedback);
        if("ok".equals(feedback)){
          System.out.println("客户端将关闭连接");
          Thread.sleep(5000);
          socket.close();
        }
        
        dos.flush();
        dos.close();
        dis.close();
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
