package io.netty.handler.codec.quic;

public interface QuicTransportParameters {
  long maxIdleTimeout();
  
  long maxUdpPayloadSize();
  
  long initialMaxData();
  
  long initialMaxStreamDataBidiLocal();
  
  long initialMaxStreamDataBidiRemote();
  
  long initialMaxStreamDataUni();
  
  long initialMaxStreamsBidi();
  
  long initialMaxStreamsUni();
  
  long ackDelayExponent();
  
  long maxAckDelay();
  
  boolean disableActiveMigration();
  
  long activeConnIdLimit();
  
  long maxDatagramFrameSize();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicTransportParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */