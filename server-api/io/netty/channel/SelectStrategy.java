package io.netty.channel;

import io.netty.util.IntSupplier;

public interface SelectStrategy {
  public static final int SELECT = -1;
  
  public static final int CONTINUE = -2;
  
  public static final int BUSY_WAIT = -3;
  
  int calculateStrategy(IntSupplier paramIntSupplier, boolean paramBoolean) throws Exception;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\SelectStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */