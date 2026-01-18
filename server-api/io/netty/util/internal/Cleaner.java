package io.netty.util.internal;

import java.nio.ByteBuffer;

interface Cleaner {
  CleanableDirectBuffer allocate(int paramInt);
  
  @Deprecated
  void freeDirectBuffer(ByteBuffer paramByteBuffer);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\Cleaner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */