package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCounted;

public interface FullHttpMessage extends HttpMessage, LastHttpContent {
  FullHttpMessage copy();
  
  FullHttpMessage duplicate();
  
  FullHttpMessage retainedDuplicate();
  
  FullHttpMessage replace(ByteBuf paramByteBuf);
  
  FullHttpMessage retain(int paramInt);
  
  FullHttpMessage retain();
  
  FullHttpMessage touch();
  
  FullHttpMessage touch(Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\FullHttpMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */