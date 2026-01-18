package io.netty.channel;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;

public interface ChannelProgressivePromise extends ProgressivePromise<Void>, ChannelProgressiveFuture, ChannelPromise {
  ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener);
  
  ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs);
  
  ChannelProgressivePromise sync() throws InterruptedException;
  
  ChannelProgressivePromise syncUninterruptibly();
  
  ChannelProgressivePromise await() throws InterruptedException;
  
  ChannelProgressivePromise awaitUninterruptibly();
  
  ChannelProgressivePromise setSuccess(Void paramVoid);
  
  ChannelProgressivePromise setSuccess();
  
  ChannelProgressivePromise setFailure(Throwable paramThrowable);
  
  ChannelProgressivePromise setProgress(long paramLong1, long paramLong2);
  
  ChannelProgressivePromise unvoid();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelProgressivePromise.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */