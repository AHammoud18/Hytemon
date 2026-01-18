package io.netty.handler.codec.quic;

import java.net.InetSocketAddress;

public interface QuicConnectionPathStats {
  InetSocketAddress localAddress();
  
  InetSocketAddress peerAddress();
  
  long validationState();
  
  boolean active();
  
  long recv();
  
  long sent();
  
  long lost();
  
  long retrans();
  
  long rtt();
  
  long cwnd();
  
  long sentBytes();
  
  long recvBytes();
  
  long lostBytes();
  
  long streamRetransBytes();
  
  long pmtu();
  
  long deliveryRate();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicConnectionPathStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */