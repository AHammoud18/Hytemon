/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class QuicheQuicChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements QuicChannelConfig
/*     */ {
/*     */   private volatile QLogConfiguration qLogConfiguration;
/*  35 */   private volatile SegmentedDatagramPacketAllocator segmentedDatagramPacketAllocator = SegmentedDatagramPacketAllocator.NONE;
/*     */ 
/*     */   
/*     */   QuicheQuicChannelConfig(Channel channel) {
/*  39 */     super(channel);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  44 */     return getOptions(super.getOptions(), new ChannelOption[] { QuicChannelOption.QLOG, QuicChannelOption.SEGMENTED_DATAGRAM_PACKET_ALLOCATOR });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  51 */     if (option == QuicChannelOption.QLOG) {
/*  52 */       return (T)getQLogConfiguration();
/*     */     }
/*  54 */     if (option == QuicChannelOption.SEGMENTED_DATAGRAM_PACKET_ALLOCATOR) {
/*  55 */       return (T)getSegmentedDatagramPacketAllocator();
/*     */     }
/*  57 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  62 */     if (option == QuicChannelOption.QLOG) {
/*  63 */       setQLogConfiguration((QLogConfiguration)value);
/*  64 */       return true;
/*     */     } 
/*  66 */     if (option == QuicChannelOption.SEGMENTED_DATAGRAM_PACKET_ALLOCATOR) {
/*  67 */       setSegmentedDatagramPacketAllocator((SegmentedDatagramPacketAllocator)value);
/*  68 */       return true;
/*     */     } 
/*  70 */     return super.setOption(option, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/*  75 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public QuicChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/*  82 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setWriteSpinCount(int writeSpinCount) {
/*  88 */     super.setWriteSpinCount(writeSpinCount);
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setAllocator(ByteBufAllocator allocator) {
/*  94 */     super.setAllocator(allocator);
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 100 */     super.setRecvByteBufAllocator(allocator);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setAutoRead(boolean autoRead) {
/* 106 */     super.setAutoRead(autoRead);
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setAutoClose(boolean autoClose) {
/* 112 */     super.setAutoClose(autoClose);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 118 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 124 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 130 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 136 */     super.setMessageSizeEstimator(estimator);
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   QLogConfiguration getQLogConfiguration() {
/* 142 */     return this.qLogConfiguration;
/*     */   }
/*     */   
/*     */   private void setQLogConfiguration(QLogConfiguration qLogConfiguration) {
/* 146 */     if (this.channel.isRegistered()) {
/* 147 */       throw new IllegalStateException("QLOG can only be enabled before the Channel was registered");
/*     */     }
/* 149 */     this.qLogConfiguration = qLogConfiguration;
/*     */   }
/*     */   
/*     */   SegmentedDatagramPacketAllocator getSegmentedDatagramPacketAllocator() {
/* 153 */     return this.segmentedDatagramPacketAllocator;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setSegmentedDatagramPacketAllocator(SegmentedDatagramPacketAllocator segmentedDatagramPacketAllocator) {
/* 158 */     this.segmentedDatagramPacketAllocator = segmentedDatagramPacketAllocator;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */