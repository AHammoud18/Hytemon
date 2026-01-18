package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCounted;

public interface SpdyDataFrame extends ByteBufHolder, SpdyStreamFrame {
  SpdyDataFrame setStreamId(int paramInt);
  
  SpdyDataFrame setLast(boolean paramBoolean);
  
  ByteBuf content();
  
  SpdyDataFrame copy();
  
  SpdyDataFrame duplicate();
  
  SpdyDataFrame retainedDuplicate();
  
  SpdyDataFrame replace(ByteBuf paramByteBuf);
  
  SpdyDataFrame retain();
  
  SpdyDataFrame retain(int paramInt);
  
  SpdyDataFrame touch();
  
  SpdyDataFrame touch(Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyDataFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */