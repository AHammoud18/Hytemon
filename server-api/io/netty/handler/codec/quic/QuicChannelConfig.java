package io.netty.handler.codec.quic;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

public interface QuicChannelConfig extends ChannelConfig {
  @Deprecated
  QuicChannelConfig setMaxMessagesPerRead(int paramInt);
  
  QuicChannelConfig setConnectTimeoutMillis(int paramInt);
  
  QuicChannelConfig setWriteSpinCount(int paramInt);
  
  QuicChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  QuicChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  QuicChannelConfig setAutoRead(boolean paramBoolean);
  
  QuicChannelConfig setAutoClose(boolean paramBoolean);
  
  QuicChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  QuicChannelConfig setWriteBufferLowWaterMark(int paramInt);
  
  QuicChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark paramWriteBufferWaterMark);
  
  QuicChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */