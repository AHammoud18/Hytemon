package io.netty.buffer;

import io.netty.util.ReferenceCounted;

public interface ByteBufHolder extends ReferenceCounted {
  ByteBuf content();
  
  ByteBufHolder copy();
  
  ByteBufHolder duplicate();
  
  ByteBufHolder retainedDuplicate();
  
  ByteBufHolder replace(ByteBuf paramByteBuf);
  
  ByteBufHolder retain();
  
  ByteBufHolder retain(int paramInt);
  
  ByteBufHolder touch();
  
  ByteBufHolder touch(Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\ByteBufHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */