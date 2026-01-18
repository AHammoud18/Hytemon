package io.netty.handler.codec.http;

public interface HttpResponse extends HttpMessage {
  @Deprecated
  HttpResponseStatus getStatus();
  
  HttpResponseStatus status();
  
  HttpResponse setStatus(HttpResponseStatus paramHttpResponseStatus);
  
  HttpResponse setProtocolVersion(HttpVersion paramHttpVersion);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */