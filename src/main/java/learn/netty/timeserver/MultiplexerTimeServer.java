package learn.netty.timeserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{
	
	private int port;
	
	private Selector selector;
	
	private ServerSocketChannel serverChannel;
	
	private volatile boolean stop;

	public MultiplexerTimeServer(int port){
		this.port = port;
		
		try {
	    selector = Selector.open();
	    System.out.println(selector.isOpen());
	    serverChannel = ServerSocketChannel.open();
	    serverChannel.configureBlocking(false);//设置为非阻塞模式
	    serverChannel.socket().bind(new InetSocketAddress(port),1024);
	    serverChannel.register(selector, SelectionKey.OP_ACCEPT);
	    System.out.println("The server is start in port: "+port);
	    
    } catch (IOException e) {
    	e.printStackTrace();
    	System.exit(1);
    }
	}
	
	public void stop(){
		this.stop = stop;
	}

	
	@Override
  public void run() {
		while(!stop){
			try {
				//设置超时时间为1000秒
	      selector.select(1000);
	      Set<SelectionKey> selectedKeys = selector.selectedKeys();
	      Iterator<SelectionKey> it = selectedKeys.iterator();
	      SelectionKey key = null;
	      while(it.hasNext()){
	      	key = it.next();
	      	it.remove();
	      	
	      	try {
	          handleInput(key);
          } catch (Exception e) {
          	if(null != key){
          		key.cancel();
          		SelectableChannel channel = key.channel();
          		if(null != channel){
          			channel.close();
          		}
          	}          	
	          //e.printStackTrace();
          }
	      	
	      }
      } catch (Throwable e) { //IOException原文是catch(Throwable t)
	      e.printStackTrace();
      }
		}
		
		if(null != selector){
			try {
	        selector.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
		}
  }
	
	private void handleInput(SelectionKey key) throws IOException{
		if(null != key && key.isValid()){
			if(key.isAcceptable()){
				//处理接入请求
				ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);//设置非阻塞模式, 这里指的是SocketChannel，而不是ServerSocketChannel
				//将新连接添加到selector上面
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			if(key.isReadable()){
				//Read the Data
				SocketChannel sc = (SocketChannel)key.channel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(buffer);
				if(readBytes > 0){
					/**
					 * 缓冲区的capacity是它所包含的元素的数量。缓冲区的capacity不能为负并且不能更改。
					 * 缓冲区的limit 是第一个不应该读取或写入的元素的索引。缓冲区的limit不能为负，并且不能大于其capacity。
					 * 缓冲区的position是下一个要读取或写入的元素的索引。缓冲区的位置不能为负，并且不能大于其limit。对于每个非
					 * boolean 基本类型，此类都有一个子类与之对应。
					 * 在使用缓冲区进行输入输出数据之前，必须确定缓冲区的position，limit都已经设置了正确的值。
					 * 如果现在想用这个缓冲区进行信道的写操作，由于write()方法将从position指示的位置开始读取数据，
					 * 在limit指示的位置停止，因此在进行写操作前，先要将limit的值设为position的当前值，
					 * 再将position的值设为0。这个操作可以通过这个flip()方法实现。
					 * flip()使缓冲区为一系列新的通道写入或相对获取 操作做好准备：
					 * 它将限制设置为当前位置，然后将位置设置为0
					 */
					buffer.flip();
					byte[] bytes = new byte[buffer.remaining()];
					buffer.get(bytes);
					String body  = new String(bytes,"UTF-8");
					System.out.println("The time server receive order: "+ body);
					String currentTime = "QUERY TIME ORDER".equals(body)?
							new Date(System.currentTimeMillis()).toString():"BAD ORDER";
					doWrite(sc, currentTime);
				}else if(readBytes < 0){
					key.cancel();
					sc.close();
				}else{
					//读到0字节主动忽略，可以不写？
				}
				
			}
		}
	}
	
	private void doWrite(SocketChannel sc, String response) throws IOException{
		if(null != response && response.trim().length()>0){
			byte[] bytes = response.getBytes();
			ByteBuffer bf = ByteBuffer.allocate(bytes.length);
			bf.put(bytes);
			bf.flip();
			sc.write(bf);
		}
	}
	
}
