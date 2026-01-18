package io.netty.handler.codec.quic;

public interface QuicConnectionStats {
  long recv();
  
  long sent();
  
  long lost();
  
  long retrans();
  
  long sentBytes();
  
  long recvBytes();
  
  long lostBytes();
  
  long streamRetransBytes();
  
  long pathsCount();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicConnectionStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */