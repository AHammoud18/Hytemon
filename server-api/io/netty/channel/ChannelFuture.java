package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public interface ChannelFuture extends Future<Void> {
  Channel channel();
  
  ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelFuture sync() throws InterruptedException;
  
  ChannelFuture syncUninterruptibly();
  
  ChannelFuture await() throws InterruptedException;
  
  ChannelFuture awaitUninterruptibly();
  
  boolean isVoid();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */