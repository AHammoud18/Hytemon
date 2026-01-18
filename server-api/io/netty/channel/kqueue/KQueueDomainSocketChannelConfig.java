/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.socket.DuplexChannelConfig;
/*     */ import io.netty.channel.unix.DomainSocketChannelConfig;
/*     */ import io.netty.channel.unix.DomainSocketReadMode;
/*     */ import io.netty.channel.unix.UnixChannelOption;
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ public final class KQueueDomainSocketChannelConfig
/*     */   extends KQueueChannelConfig
/*     */   implements DomainSocketChannelConfig, DuplexChannelConfig
/*     */ {
/*  39 */   private volatile DomainSocketReadMode mode = DomainSocketReadMode.BYTES;
/*     */   private volatile boolean allowHalfClosure;
/*     */   
/*     */   KQueueDomainSocketChannelConfig(AbstractKQueueChannel channel) {
/*  43 */     super(channel);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  48 */     return getOptions(super.getOptions(), new ChannelOption[] { UnixChannelOption.DOMAIN_SOCKET_READ_MODE, ChannelOption.ALLOW_HALF_CLOSURE, ChannelOption.SO_SNDBUF, ChannelOption.SO_RCVBUF });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  54 */     if (option == UnixChannelOption.DOMAIN_SOCKET_READ_MODE) {
/*  55 */       return (T)getReadMode();
/*     */     }
/*  57 */     if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  58 */       return (T)Boolean.valueOf(isAllowHalfClosure());
/*     */     }
/*  60 */     if (option == ChannelOption.SO_SNDBUF) {
/*  61 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  63 */     if (option == ChannelOption.SO_RCVBUF) {
/*  64 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  66 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  71 */     validate(option, value);
/*     */     
/*  73 */     if (option == UnixChannelOption.DOMAIN_SOCKET_READ_MODE) {
/*  74 */       setReadMode((DomainSocketReadMode)value);
/*  75 */     } else if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  76 */       setAllowHalfClosure(((Boolean)value).booleanValue());
/*  77 */     } else if (option == ChannelOption.SO_SNDBUF) {
/*  78 */       setSendBufferSize(((Integer)value).intValue());
/*  79 */     } else if (option == ChannelOption.SO_RCVBUF) {
/*  80 */       setReceiveBufferSize(((Integer)value).intValue());
/*     */     } else {
/*  82 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
/*  90 */     super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDomainSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/*  97 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 103 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 109 */     super.setWriteSpinCount(writeSpinCount);
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 115 */     super.setRecvByteBufAllocator(allocator);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 121 */     super.setAllocator(allocator);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setAutoClose(boolean autoClose) {
/* 127 */     super.setAutoClose(autoClose);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 133 */     super.setMessageSizeEstimator(estimator);
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDomainSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 140 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDomainSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 147 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 153 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setAutoRead(boolean autoRead) {
/* 159 */     super.setAutoRead(autoRead);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setReadMode(DomainSocketReadMode mode) {
/* 165 */     this.mode = (DomainSocketReadMode)ObjectUtil.checkNotNull(mode, "mode");
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketReadMode getReadMode() {
/* 171 */     return this.mode;
/*     */   }
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 176 */       return ((KQueueDomainSocketChannel)this.channel).socket.getSendBufferSize();
/* 177 */     } catch (IOException e) {
/* 178 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 184 */       ((KQueueDomainSocketChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 185 */       return this;
/* 186 */     } catch (IOException e) {
/* 187 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 193 */       return ((KQueueDomainSocketChannel)this.channel).socket.getReceiveBufferSize();
/* 194 */     } catch (IOException e) {
/* 195 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 201 */       ((KQueueDomainSocketChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
/* 202 */       return this;
/* 203 */     } catch (IOException e) {
/* 204 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowHalfClosure() {
/* 210 */     return this.allowHalfClosure;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 215 */     this.allowHalfClosure = allowHalfClosure;
/* 216 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueDomainSocketChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */