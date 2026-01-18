package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.ReferenceCounted;

public interface HttpContent extends HttpObject, ByteBufHolder {
  HttpContent copy();
  
  HttpContent duplicate();
  
  HttpContent retainedDuplicate();
  
  HttpContent replace(ByteBuf paramByteBuf);
  
  HttpContent retain();
  
  HttpContent retain(int paramInt);
  
  HttpContent touch();
  
  HttpContent touch(Object paramObject);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */