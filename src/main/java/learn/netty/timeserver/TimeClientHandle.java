package learn.netty.timeserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class TimeClientHandle implements Runnable {
	
	private String host;
	
	private int port;
	
	private Selector selector;
	
	private SocketChannel socketChannel;
	
	private volatile boolean stop;

	
	public TimeClientHandle(String host, int port) {
	  this.host = (StringUtils.isBlank(host))?"127.0.0.1":host;
	  this.port = port;
	  
	  try {
	    selector = Selector.open();
	    socketChannel = SocketChannel.open();
	    socketChannel.configureBlocking(false);
    } catch (IOException e) {
	    e.printStackTrace();
	    System.exit(1);
    }
  }


	@Override
  public void run() {
		
		try {
	    doConnect();
    } catch (IOException e) {
	    e.printStackTrace();
	    System.exit(1);
    }
	  
		while(!stop){
			try{
				selector.select(1000);
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				System.out.println("Keys: "+selectedKeys.size());
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key = null;
				while(it.hasNext()){
					key = it.next();
					it.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						if(null != key){
							key.cancel();
							if(key.channel() != null){
								key.channel().close();
							}
						}
						e.printStackTrace();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		if(selector != null){
			try {
	      selector.close();
      } catch (IOException e) {
	      e.printStackTrace();
      }
		}
  }
	
	private void handleInput(SelectionKey key) throws IOException{
		if(key.isValid()){
			SocketChannel sc = (SocketChannel)key.channel();
			//注: 这里的connectable 和Readable 是分开的，要是一起的话，会出现错误
			if(key.isConnectable()){
				if(sc.finishConnect()){
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				}else{
					System.exit(1);//连接失败，进程退出
				}
			}	
			if(key.isReadable()){
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes >0){
					//读取到内容
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("Now the time is: "+ body);
					this.stop = true;
				}else if(readBytes <0){
					key.cancel();
					sc.close();
				}else{
					//读到0字节，不做任何处理
				}
				
			}
		}	
	}

	private void doConnect() throws IOException{
		if(socketChannel.connect(new InetSocketAddress(host, port))){
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
			
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
		
	}
	
	private void doWrite(SocketChannel sc) throws IOException{
		byte[] request = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(request.length);
		writeBuffer.put(request);
		writeBuffer.flip();
		sc.write(writeBuffer);
		if(!writeBuffer.hasRemaining()){
			System.out.println("Send order 2 server succeeed");
		}
		
  }

}
