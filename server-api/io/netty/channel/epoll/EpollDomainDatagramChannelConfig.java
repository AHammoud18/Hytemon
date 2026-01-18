/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
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
/*     */ public final class EpollDomainDatagramChannelConfig
/*     */   extends EpollChannelConfig
/*     */   implements DomainDatagramChannelConfig
/*     */ {
/*     */   private boolean activeOnOpen;
/*     */   
/*     */   EpollDomainDatagramChannelConfig(EpollDomainDatagramChannel channel) {
/*  38 */     super((Channel)channel, (RecvByteBufAllocator)new FixedRecvByteBufAllocator(2048));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  44 */     return getOptions(super
/*  45 */         .getOptions(), new ChannelOption[] { ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, ChannelOption.SO_SNDBUF });
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
/*     */   public EpollDomainDatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
/*  90 */     super.setAllocator(allocator);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setAutoClose(boolean autoClose) {
/*  96 */     super.setAutoClose(autoClose);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setAutoRead(boolean autoRead) {
/* 102 */     super.setAutoRead(autoRead);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 108 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setEpollMode(EpollMode mode) {
/* 114 */     super.setEpollMode(mode);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollDomainDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 121 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setMaxMessagesPerWrite(int maxMessagesPerWrite) {
/* 127 */     super.setMaxMessagesPerWrite(maxMessagesPerWrite);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 133 */     super.setMessageSizeEstimator(estimator);
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 139 */     super.setRecvByteBufAllocator(allocator);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 146 */       ((EpollDomainDatagramChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 147 */       return this;
/* 148 */     } catch (IOException e) {
/* 149 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 156 */       return ((EpollDomainDatagramChannel)this.channel).socket.getSendBufferSize();
/* 157 */     } catch (IOException e) {
/* 158 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 164 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainDatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 170 */     super.setWriteSpinCount(writeSpinCount);
/* 171 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollDomainDatagramChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */