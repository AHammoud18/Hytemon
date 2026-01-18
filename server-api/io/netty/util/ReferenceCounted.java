package io.netty.util;

public interface ReferenceCounted {
  int refCnt();
  
  ReferenceCounted retain();
  
  ReferenceCounted retain(int paramInt);
  
  ReferenceCounted touch();
  
  ReferenceCounted touch(Object paramObject);
  
  boolean release();
  
  boolean release(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ReferenceCounted.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */