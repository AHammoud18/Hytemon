package io.netty.handler.codec.spdy;

public interface SpdyStreamFrame extends SpdyFrame {
  int streamId();
  
  SpdyStreamFrame setStreamId(int paramInt);
  
  boolean isLast();
  
  SpdyStreamFrame setLast(boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyStreamFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */