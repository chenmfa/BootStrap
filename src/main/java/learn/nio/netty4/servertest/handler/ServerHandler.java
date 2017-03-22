package learn.nio.netty4.servertest.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter{
  private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);  
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    logger.info(msg.toString());
    super.channelRead(ctx, msg);
  }
  @Override  
  public void exceptionCaught(ChannelHandlerContext ctx,  
          Throwable cause) throws Exception {  
      logger.error("Unexpected exception from downstream.", cause);  
      ctx.close();  
  }
}
