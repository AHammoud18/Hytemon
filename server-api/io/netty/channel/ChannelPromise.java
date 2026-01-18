package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

public interface ChannelPromise extends ChannelFuture, Promise<Void> {
  Channel channel();
  
  ChannelPromise setSuccess(Void paramVoid);
  
  ChannelPromise setSuccess();
  
  boolean trySuccess();
  
  ChannelPromise setFailure(Throwable paramThrowable);
  
  ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelPromise sync() throws InterruptedException;
  
  ChannelPromise syncUninterruptibly();
  
  ChannelPromise await() throws InterruptedException;
  
  ChannelPromise awaitUninterruptibly();
  
  ChannelPromise unvoid();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelPromise.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */