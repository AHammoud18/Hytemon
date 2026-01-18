package io.netty.handler.codec.http.websocketx.extensions;

public interface WebSocketExtension {
  public static final int RSV1 = 4;
  
  public static final int RSV2 = 2;
  
  public static final int RSV3 = 1;
  
  int rsv();
  
  WebSocketExtensionEncoder newExtensionEncoder();
  
  WebSocketExtensionDecoder newExtensionDecoder();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\extensions\WebSocketExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */