package io.netty.channel;

public interface MessageSizeEstimator {
  Handle newHandle();
  
  public static interface Handle {
    int size(Object param1Object);
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\MessageSizeEstimator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */