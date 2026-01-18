package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCounted;

public interface FullHttpRequest extends HttpRequest, FullHttpMessage {
  FullHttpRequest copy();
  
  FullHttpRequest duplicate();
  
  FullHttpRequest retainedDuplicate();
  
  FullHttpRequest replace(ByteBuf paramByteBuf);
  
  FullHttpRequest retain(int paramInt);
  
  FullHttpRequest retain();
  
  FullHttpRequest touch();
  
  FullHttpRequest touch(Object paramObject);
  
  FullHttpRequest setProtocolVersion(HttpVersion paramHttpVersion);
  
  FullHttpRequest setMethod(HttpMethod paramHttpMethod);
  
  FullHttpRequest setUri(String paramString);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\FullHttpRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */