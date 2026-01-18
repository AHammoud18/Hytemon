package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCounted;

public interface FullHttpResponse extends HttpResponse, FullHttpMessage {
  FullHttpResponse copy();
  
  FullHttpResponse duplicate();
  
  FullHttpResponse retainedDuplicate();
  
  FullHttpResponse replace(ByteBuf paramByteBuf);
  
  FullHttpResponse retain(int paramInt);
  
  FullHttpResponse retain();
  
  FullHttpResponse touch();
  
  FullHttpResponse touch(Object paramObject);
  
  FullHttpResponse setProtocolVersion(HttpVersion paramHttpVersion);
  
  FullHttpResponse setStatus(HttpResponseStatus paramHttpResponseStatus);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\FullHttpResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */