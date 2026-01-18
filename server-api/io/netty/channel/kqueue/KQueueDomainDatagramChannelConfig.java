/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.FixedRecvByteBufAllocator;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.unix.DomainDatagramChannelConfig;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KQueueDomainDatagramChannelConfig
/*     */   extends KQueueChannelConfig
/*     */   implements DomainDatagramChannelConfig
/*     */ {
/*     */   private boolean activeOnOpen;
/*     */   
/*     */   KQueueDomainDatagramChannelConfig(KQueueDomainDatagramChannel channel) {
/*  39 */     super(channel, (RecvByteBufAllocator)new FixedRecvByteBufAllocator(2048));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  45 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, ChannelOption.SO_SNDBUF });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  52 */     if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/*  53 */       return (T)Boolean.valueOf(this.activeOnOpen);
/*     */     }
/*  55 */     if (option == ChannelOption.SO_SNDBUF) {
/*  56 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  58 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  64 */     validate(option, value);
/*     */     
/*  66 */     if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/*  67 */       setActiveOnOpen(((Boolean)value).booleanValue());
/*  68 */     } else if (option == ChannelOption.SO_SNDBUF) {
/*  69 */       setSendBufferSize(((Integer)value).intValue());
/*     */     } else {
/*  71 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   private void setActiveOnOpen(boolean activeOnOpen) {
/*  78 */     if (this.channel.isRegistered()) {
/*  79 */       throw new IllegalStateException("Can only changed before channel was registered");
/*     */     }
/*  81 */     this.activeOnOpen = activeOnOpen;
/*     */   }
/*     */   
/*     */   boolean getActiveOnOpen() {
/*  85 */     return this.activeOnOpen;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
/*  90 */     super.setAllocator(allocator);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setAutoClose(boolean autoClose) {
/*  96 */     super.setAutoClose(autoClose);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setAutoRead(boolean autoRead) {
/* 102 */     super.setAutoRead(autoRead);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 108 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDomainDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 115 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setMaxMessagesPerWrite(int maxMessagesPerWrite) {
/* 121 */     super.setMaxMessagesPerWrite(maxMessagesPerWrite);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 127 */     super.setMessageSizeEstimator(estimator);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
/* 133 */     super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 139 */     super.setRecvByteBufAllocator(allocator);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 146 */       ((KQueueDomainDatagramChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 147 */       return this;
/* 148 */     } catch (IOException e) {
/* 149 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 156 */       return ((KQueueDomainDatagramChannel)this.channel).socket.getSendBufferSize();
/* 157 */     } catch (IOException e) {
/* 158 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 164 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainDatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 170 */     super.setWriteSpinCount(writeSpinCount);
/* 171 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueDomainDatagramChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */