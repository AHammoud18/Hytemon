package io.netty.handler.codec.spdy;

public interface SpdyPingFrame extends SpdyFrame {
  int id();
  
  SpdyPingFrame setId(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyPingFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */