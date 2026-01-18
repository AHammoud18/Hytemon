package io.netty.util.concurrent;

public interface ThreadProperties {
  Thread.State state();
  
  int priority();
  
  boolean isInterrupted();
  
  boolean isDaemon();
  
  String name();
  
  long id();
  
  StackTraceElement[] stackTrace();
  
  boolean isAlive();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\ThreadProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */