package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCounted;

public interface SpdyUnknownFrame extends SpdyFrame, ByteBufHolder {
  int frameType();
  
  byte flags();
  
  SpdyUnknownFrame copy();
  
  SpdyUnknownFrame duplicate();
  
  SpdyUnknownFrame retainedDuplicate();
  
  SpdyUnknownFrame replace(ByteBuf paramByteBuf);
  
  SpdyUnknownFrame retain();
  
  SpdyUnknownFrame retain(int paramInt);
  
  SpdyUnknownFrame touch();
  
  SpdyUnknownFrame touch(Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyUnknownFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */