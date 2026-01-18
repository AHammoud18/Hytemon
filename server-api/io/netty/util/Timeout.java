package io.netty.util;

public interface Timeout {
  Timer timer();
  
  TimerTask task();
  
  boolean isExpired();
  
  boolean isCancelled();
  
  boolean cancel();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\Timeout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */