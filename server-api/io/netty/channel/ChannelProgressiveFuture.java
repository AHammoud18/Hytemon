package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;

public interface ChannelProgressiveFuture extends ChannelFuture, ProgressiveFuture<Void> {
  ChannelProgressiveFuture addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelProgressiveFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelProgressiveFuture removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelProgressiveFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelProgressiveFuture sync() throws InterruptedException;
  
  ChannelProgressiveFuture syncUninterruptibly();
  
  ChannelProgressiveFuture await() throws InterruptedException;
  
  ChannelProgressiveFuture awaitUninterruptibly();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelProgressiveFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */