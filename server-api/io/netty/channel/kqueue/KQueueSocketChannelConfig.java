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
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KQueueSocketChannelConfig
/*     */   extends KQueueChannelConfig
/*     */   implements SocketChannelConfig
/*     */ {
/*     */   private volatile boolean allowHalfClosure;
/*     */   private volatile boolean tcpFastopen;
/*     */   
/*     */   KQueueSocketChannelConfig(KQueueSocketChannel channel) {
/*  46 */     super(channel);
/*  47 */     if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
/*  48 */       setTcpNoDelay(true);
/*     */     }
/*  50 */     calculateMaxBytesPerGatheringWrite();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  55 */     return getOptions(super
/*  56 */         .getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE, KQueueChannelOption.SO_SNDLOWAT, KQueueChannelOption.TCP_NOPUSH });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  64 */     if (option == ChannelOption.SO_RCVBUF) {
/*  65 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  67 */     if (option == ChannelOption.SO_SNDBUF) {
/*  68 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  70 */     if (option == ChannelOption.TCP_NODELAY) {
/*  71 */       return (T)Boolean.valueOf(isTcpNoDelay());
/*     */     }
/*  73 */     if (option == ChannelOption.SO_KEEPALIVE) {
/*  74 */       return (T)Boolean.valueOf(isKeepAlive());
/*     */     }
/*  76 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  77 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  79 */     if (option == ChannelOption.SO_LINGER) {
/*  80 */       return (T)Integer.valueOf(getSoLinger());
/*     */     }
/*  82 */     if (option == ChannelOption.IP_TOS) {
/*  83 */       return (T)Integer.valueOf(getTrafficClass());
/*     */     }
/*  85 */     if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  86 */       return (T)Boolean.valueOf(isAllowHalfClosure());
/*     */     }
/*  88 */     if (option == KQueueChannelOption.SO_SNDLOWAT) {
/*  89 */       return (T)Integer.valueOf(getSndLowAt());
/*     */     }
/*  91 */     if (option == KQueueChannelOption.TCP_NOPUSH) {
/*  92 */       return (T)Boolean.valueOf(isTcpNoPush());
/*     */     }
/*  94 */     if (option == ChannelOption.TCP_FASTOPEN_CONNECT) {
/*  95 */       return (T)Boolean.valueOf(isTcpFastOpenConnect());
/*     */     }
/*  97 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 102 */     validate(option, value);
/*     */     
/* 104 */     if (option == ChannelOption.SO_RCVBUF) {
/* 105 */       setReceiveBufferSize(((Integer)value).intValue());
/* 106 */     } else if (option == ChannelOption.SO_SNDBUF) {
/* 107 */       setSendBufferSize(((Integer)value).intValue());
/* 108 */     } else if (option == ChannelOption.TCP_NODELAY) {
/* 109 */       setTcpNoDelay(((Boolean)value).booleanValue());
/* 110 */     } else if (option == ChannelOption.SO_KEEPALIVE) {
/* 111 */       setKeepAlive(((Boolean)value).booleanValue());
/* 112 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/* 113 */       setReuseAddress(((Boolean)value).booleanValue());
/* 114 */     } else if (option == ChannelOption.SO_LINGER) {
/* 115 */       setSoLinger(((Integer)value).intValue());
/* 116 */     } else if (option == ChannelOption.IP_TOS) {
/* 117 */       setTrafficClass(((Integer)value).intValue());
/* 118 */     } else if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/* 119 */       setAllowHalfClosure(((Boolean)value).booleanValue());
/* 120 */     } else if (option == KQueueChannelOption.SO_SNDLOWAT) {
/* 121 */       setSndLowAt(((Integer)value).intValue());
/* 122 */     } else if (option == KQueueChannelOption.TCP_NOPUSH) {
/* 123 */       setTcpNoPush(((Boolean)value).booleanValue());
/* 124 */     } else if (option == ChannelOption.TCP_FASTOPEN_CONNECT) {
/* 125 */       setTcpFastOpenConnect(((Boolean)value).booleanValue());
/*     */     } else {
/* 127 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 136 */       return ((KQueueSocketChannel)this.channel).socket.getReceiveBufferSize();
/* 137 */     } catch (IOException e) {
/* 138 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 145 */       return ((KQueueSocketChannel)this.channel).socket.getSendBufferSize();
/* 146 */     } catch (IOException e) {
/* 147 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSoLinger() {
/*     */     try {
/* 154 */       return ((KQueueSocketChannel)this.channel).socket.getSoLinger();
/* 155 */     } catch (IOException e) {
/* 156 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrafficClass() {
/*     */     try {
/* 163 */       return ((KQueueSocketChannel)this.channel).socket.getTrafficClass();
/* 164 */     } catch (IOException e) {
/* 165 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isKeepAlive() {
/*     */     try {
/* 172 */       return ((KQueueSocketChannel)this.channel).socket.isKeepAlive();
/* 173 */     } catch (IOException e) {
/* 174 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*     */     try {
/* 181 */       return ((KQueueSocketChannel)this.channel).socket.isReuseAddress();
/* 182 */     } catch (IOException e) {
/* 183 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTcpNoDelay() {
/*     */     try {
/* 190 */       return ((KQueueSocketChannel)this.channel).socket.isTcpNoDelay();
/* 191 */     } catch (IOException e) {
/* 192 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSndLowAt() {
/*     */     try {
/* 198 */       return ((KQueueSocketChannel)this.channel).socket.getSndLowAt();
/* 199 */     } catch (IOException e) {
/* 200 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSndLowAt(int sndLowAt) {
/*     */     try {
/* 206 */       ((KQueueSocketChannel)this.channel).socket.setSndLowAt(sndLowAt);
/* 207 */     } catch (IOException e) {
/* 208 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isTcpNoPush() {
/*     */     try {
/* 214 */       return ((KQueueSocketChannel)this.channel).socket.isTcpNoPush();
/* 215 */     } catch (IOException e) {
/* 216 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setTcpNoPush(boolean tcpNoPush) {
/*     */     try {
/* 222 */       ((KQueueSocketChannel)this.channel).socket.setTcpNoPush(tcpNoPush);
/* 223 */     } catch (IOException e) {
/* 224 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setKeepAlive(boolean keepAlive) {
/*     */     try {
/* 231 */       ((KQueueSocketChannel)this.channel).socket.setKeepAlive(keepAlive);
/* 232 */       return this;
/* 233 */     } catch (IOException e) {
/* 234 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 241 */       ((KQueueSocketChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
/* 242 */       return this;
/* 243 */     } catch (IOException e) {
/* 244 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/* 251 */       ((KQueueSocketChannel)this.channel).socket.setReuseAddress(reuseAddress);
/* 252 */       return this;
/* 253 */     } catch (IOException e) {
/* 254 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 261 */       ((KQueueSocketChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 262 */       calculateMaxBytesPerGatheringWrite();
/* 263 */       return this;
/* 264 */     } catch (IOException e) {
/* 265 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setSoLinger(int soLinger) {
/*     */     try {
/* 272 */       ((KQueueSocketChannel)this.channel).socket.setSoLinger(soLinger);
/* 273 */       return this;
/* 274 */     } catch (IOException e) {
/* 275 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setTcpNoDelay(boolean tcpNoDelay) {
/*     */     try {
/* 282 */       ((KQueueSocketChannel)this.channel).socket.setTcpNoDelay(tcpNoDelay);
/* 283 */       return this;
/* 284 */     } catch (IOException e) {
/* 285 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setTrafficClass(int trafficClass) {
/*     */     try {
/* 292 */       ((KQueueSocketChannel)this.channel).socket.setTrafficClass(trafficClass);
/* 293 */       return this;
/* 294 */     } catch (IOException e) {
/* 295 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowHalfClosure() {
/* 301 */     return this.allowHalfClosure;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setTcpFastOpenConnect(boolean fastOpenConnect) {
/* 308 */     this.tcpFastopen = fastOpenConnect;
/* 309 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTcpFastOpenConnect() {
/* 316 */     return this.tcpFastopen;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
/* 321 */     super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
/* 322 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 333 */     this.allowHalfClosure = allowHalfClosure;
/* 334 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 339 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 340 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 346 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 347 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 352 */     super.setWriteSpinCount(writeSpinCount);
/* 353 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 358 */     super.setAllocator(allocator);
/* 359 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 364 */     super.setRecvByteBufAllocator(allocator);
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setAutoRead(boolean autoRead) {
/* 370 */     super.setAutoRead(autoRead);
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setAutoClose(boolean autoClose) {
/* 376 */     super.setAutoClose(autoClose);
/* 377 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 383 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 384 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 390 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 396 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 397 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 402 */     super.setMessageSizeEstimator(estimator);
/* 403 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void calculateMaxBytesPerGatheringWrite() {
/* 408 */     int newSendBufferSize = getSendBufferSize() << 1;
/* 409 */     if (newSendBufferSize > 0)
/* 410 */       setMaxBytesPerGatheringWrite(newSendBufferSize); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueSocketChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */