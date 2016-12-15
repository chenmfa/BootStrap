package learn.netty.nio.nettytimeserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	
	public void connect(int port, String host) throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		
		Bootstrap boot = new Bootstrap();
		boot.group(group).channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		.handler(new ChannelInitializer<SocketChannel>() {

			@Override
      protected void initChannel(SocketChannel channel) throws Exception {
	      channel.pipeline().addLast(new TimeClientHandler());
      }
			
		});
	}
}
