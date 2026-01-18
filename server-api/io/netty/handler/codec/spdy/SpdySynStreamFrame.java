package io.netty.handler.codec.spdy;

public interface SpdySynStreamFrame extends SpdyHeadersFrame {
  int associatedStreamId();
  
  SpdySynStreamFrame setAssociatedStreamId(int paramInt);
  
  byte priority();
  
  SpdySynStreamFrame setPriority(byte paramByte);
  
  boolean isUnidirectional();
  
  SpdySynStreamFrame setUnidirectional(boolean paramBoolean);
  
  SpdySynStreamFrame setStreamId(int paramInt);
  
  SpdySynStreamFrame setLast(boolean paramBoolean);
  
  SpdySynStreamFrame setInvalid();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdySynStreamFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */