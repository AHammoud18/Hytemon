package io.netty.handler.codec.quic;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DuplexChannelConfig;

public interface QuicStreamChannelConfig extends DuplexChannelConfig {
  QuicStreamChannelConfig setReadFrames(boolean paramBoolean);
  
  boolean isReadFrames();
  
  QuicStreamChannelConfig setAllowHalfClosure(boolean paramBoolean);
  
  QuicStreamChannelConfig setMaxMessagesPerRead(int paramInt);
  
  QuicStreamChannelConfig setWriteSpinCount(int paramInt);
  
  QuicStreamChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  QuicStreamChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  QuicStreamChannelConfig setAutoRead(boolean paramBoolean);
  
  QuicStreamChannelConfig setAutoClose(boolean paramBoolean);
  
  QuicStreamChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
  
  QuicStreamChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark paramWriteBufferWaterMark);
  
  QuicStreamChannelConfig setConnectTimeoutMillis(int paramInt);
  
  QuicStreamChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  QuicStreamChannelConfig setWriteBufferLowWaterMark(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */