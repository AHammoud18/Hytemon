package io.netty.util;

@Deprecated
public interface ResourceLeak {
  void record();
  
  void record(Object paramObject);
  
  boolean close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ResourceLeak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */