package learn.netty.nio.nettytimeserver;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter{
	
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      ByteBuf buf = (ByteBuf)msg;
      byte[] req =new byte[buf.readableBytes()];
      buf.readBytes(req);
      String body = new String(req, "UTF-8");
      System.out.println("The timeServer receive order:"+ body);
      String currentTime= ("QUERY TIME ORDER".equalsIgnoreCase(body))?
      		(new Date(System.currentTimeMillis()).toString()):"BAD ORDER";
      ByteBuf response = Unpooled.copiedBuffer(currentTime.getBytes());
      ctx.write(response);
      
  }
  
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
  	ctx.flush();
  }
  
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
  	ctx.close();
  	cause.printStackTrace();
  }
}
