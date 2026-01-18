package io.netty.channel;

public interface ChannelInboundInvoker {
  ChannelInboundInvoker fireChannelRegistered();
  
  ChannelInboundInvoker fireChannelUnregistered();
  
  ChannelInboundInvoker fireChannelActive();
  
  ChannelInboundInvoker fireChannelInactive();
  
  ChannelInboundInvoker fireExceptionCaught(Throwable paramThrowable);
  
  ChannelInboundInvoker fireUserEventTriggered(Object paramObject);
  
  ChannelInboundInvoker fireChannelRead(Object paramObject);
  
  ChannelInboundInvoker fireChannelReadComplete();
  
  ChannelInboundInvoker fireChannelWritabilityChanged();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelInboundInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */