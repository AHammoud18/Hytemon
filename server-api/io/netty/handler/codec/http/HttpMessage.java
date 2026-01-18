package io.netty.handler.codec.http;

public interface HttpMessage extends HttpObject {
  @Deprecated
  HttpVersion getProtocolVersion();
  
  HttpVersion protocolVersion();
  
  HttpMessage setProtocolVersion(HttpVersion paramHttpVersion);
  
  HttpHeaders headers();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */