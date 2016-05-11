package learn.socket.basecommnnication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
  
  //定义监听端口
  public static final int listenPort = 3302;
  
  public static void main(String[] args) {
    new ServerSide().init();
  }
  
  public void init(){
    
    try {
      ServerSocket serverSocket = new ServerSocket(listenPort);
      
      while(true){
        Socket client = serverSocket.accept();
        new ThreadHandler(client);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  class ThreadHandler implements Runnable{
    
    private Socket socket;
    
    public ThreadHandler(Socket client){
      
      socket =client;
      new Thread(this).start();
    }
    
    public void run(){
      try {
        DataInputStream dis = new  DataInputStream(socket.getInputStream());
        String word = dis.readUTF();
        System.out.println("接收到的数据:"+word);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        System.out.println("请输入需要的内容：");
        String response= new BufferedReader(new InputStreamReader(System.in)).readLine();
        dos.writeUTF(response);
                
        dis.close();
        dos.flush();
        dos.close();
      } catch (IOException e) {
         System.out.println("服务器应答响应失败.");
      }finally {
        if(null !=socket){
          try {
            socket.close();
          } catch (IOException e) {
            System.out.println("连接关闭异常"+e.getMessage());          
          }
        }
      }
    }
  }
}
