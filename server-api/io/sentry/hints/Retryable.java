package io.sentry.hints;

public interface Retryable {
  boolean isRetry();
  
  void setRetry(boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\hints\Retryable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */