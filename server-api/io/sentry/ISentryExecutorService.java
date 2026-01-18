package io.sentry;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public interface ISentryExecutorService {
  @NotNull
  Future<?> submit(@NotNull Runnable paramRunnable) throws RejectedExecutionException;
  
  @NotNull
  <T> Future<T> submit(@NotNull Callable<T> paramCallable) throws RejectedExecutionException;
  
  @NotNull
  Future<?> schedule(@NotNull Runnable paramRunnable, long paramLong) throws RejectedExecutionException;
  
  void close(long paramLong);
  
  boolean isClosed();
  
  void prewarm();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ISentryExecutorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */