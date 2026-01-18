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
/*     */ import io.netty.channel.socket.DuplexChannelConfig;
/*     */ import java.util.Map;
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
/*     */ final class QuicheQuicStreamChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements QuicStreamChannelConfig
/*     */ {
/*     */   private volatile boolean allowHalfClosure = true;
/*     */   private volatile boolean readFrames;
/*     */   volatile DirectIoByteBufAllocator allocator;
/*     */   
/*     */   QuicheQuicStreamChannelConfig(QuicStreamChannel channel) {
/*  35 */     super((Channel)channel);
/*  36 */     this.allocator = new DirectIoByteBufAllocator(super.getAllocator());
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  41 */     if (isHalfClosureSupported()) {
/*  42 */       return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.ALLOW_HALF_CLOSURE, QuicChannelOption.READ_FRAMES });
/*     */     }
/*  44 */     return super.getOptions();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  50 */     if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  51 */       return (T)Boolean.valueOf(isAllowHalfClosure());
/*     */     }
/*  53 */     if (option == QuicChannelOption.READ_FRAMES) {
/*  54 */       return (T)Boolean.valueOf(isReadFrames());
/*     */     }
/*  56 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  61 */     validate(option, value);
/*     */     
/*  63 */     if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  64 */       if (isHalfClosureSupported()) {
/*  65 */         setAllowHalfClosure(((Boolean)value).booleanValue());
/*  66 */         return true;
/*     */       } 
/*  68 */       return false;
/*     */     } 
/*  70 */     if (option == QuicChannelOption.READ_FRAMES) {
/*  71 */       setReadFrames(((Boolean)value).booleanValue());
/*     */     }
/*  73 */     return super.setOption(option, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setReadFrames(boolean readFrames) {
/*  78 */     this.readFrames = readFrames;
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadFrames() {
/*  84 */     return this.readFrames;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/*  89 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/*  95 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 101 */     super.setWriteSpinCount(writeSpinCount);
/* 102 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 107 */     this.allocator = new DirectIoByteBufAllocator(allocator);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 113 */     super.setRecvByteBufAllocator(allocator);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setAutoRead(boolean autoRead) {
/* 119 */     super.setAutoRead(autoRead);
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setAutoClose(boolean autoClose) {
/* 125 */     super.setAutoClose(autoClose);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 131 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 137 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 143 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 149 */     super.setMessageSizeEstimator(estimator);
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 155 */     if (!isHalfClosureSupported()) {
/* 156 */       throw new UnsupportedOperationException("Undirectional streams don't support half-closure");
/*     */     }
/* 158 */     this.allowHalfClosure = allowHalfClosure;
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocator getAllocator() {
/* 164 */     return this.allocator.wrapped();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowHalfClosure() {
/* 169 */     return this.allowHalfClosure;
/*     */   }
/*     */   
/*     */   private boolean isHalfClosureSupported() {
/* 173 */     return (((QuicStreamChannel)this.channel).type() == QuicStreamType.BIDIRECTIONAL);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicheQuicStreamChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */