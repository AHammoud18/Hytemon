package io.netty.handler.codec.spdy;

public interface SpdyWindowUpdateFrame extends SpdyFrame {
  int streamId();
  
  SpdyWindowUpdateFrame setStreamId(int paramInt);
  
  int deltaWindowSize();
  
  SpdyWindowUpdateFrame setDeltaWindowSize(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyWindowUpdateFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */