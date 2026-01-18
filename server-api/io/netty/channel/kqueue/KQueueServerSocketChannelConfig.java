/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import io.netty.channel.unix.UnixChannelOption;
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
/*     */ public class KQueueServerSocketChannelConfig
/*     */   extends KQueueServerChannelConfig
/*     */ {
/*     */   KQueueServerSocketChannelConfig(KQueueServerSocketChannel channel) {
/*  33 */     super(channel);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     setReuseAddress(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  43 */     return getOptions(super.getOptions(), new ChannelOption[] { UnixChannelOption.SO_REUSEPORT, KQueueChannelOption.SO_ACCEPTFILTER });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  49 */     if (option == UnixChannelOption.SO_REUSEPORT) {
/*  50 */       return (T)Boolean.valueOf(isReusePort());
/*     */     }
/*  52 */     if (option == KQueueChannelOption.SO_ACCEPTFILTER) {
/*  53 */       return (T)getAcceptFilter();
/*     */     }
/*  55 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  60 */     validate(option, value);
/*     */     
/*  62 */     if (option == UnixChannelOption.SO_REUSEPORT) {
/*  63 */       setReusePort(((Boolean)value).booleanValue());
/*  64 */     } else if (option == KQueueChannelOption.SO_ACCEPTFILTER) {
/*  65 */       setAcceptFilter((AcceptFilter)value);
/*     */     } else {
/*  67 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  70 */     return true;
/*     */   }
/*     */   
/*     */   public KQueueServerSocketChannelConfig setReusePort(boolean reusePort) {
/*     */     try {
/*  75 */       ((KQueueServerSocketChannel)this.channel).socket.setReusePort(reusePort);
/*  76 */       return this;
/*  77 */     } catch (IOException e) {
/*  78 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isReusePort() {
/*     */     try {
/*  84 */       return ((KQueueServerSocketChannel)this.channel).socket.isReusePort();
/*  85 */     } catch (IOException e) {
/*  86 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public KQueueServerSocketChannelConfig setAcceptFilter(AcceptFilter acceptFilter) {
/*     */     try {
/*  92 */       ((KQueueServerSocketChannel)this.channel).socket.setAcceptFilter(acceptFilter);
/*  93 */       return this;
/*  94 */     } catch (IOException e) {
/*  95 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AcceptFilter getAcceptFilter() {
/*     */     try {
/* 101 */       return ((KQueueServerSocketChannel)this.channel).socket.getAcceptFilter();
/* 102 */     } catch (IOException e) {
/* 103 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
/* 109 */     super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/* 115 */     super.setReuseAddress(reuseAddress);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/* 121 */     super.setReceiveBufferSize(receiveBufferSize);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setBacklog(int backlog) {
/* 132 */     super.setBacklog(backlog);
/* 133 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setTcpFastOpen(boolean enableTcpFastOpen) {
/* 138 */     super.setTcpFastOpen(enableTcpFastOpen);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 144 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 151 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 157 */     super.setWriteSpinCount(writeSpinCount);
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 163 */     super.setAllocator(allocator);
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 169 */     super.setRecvByteBufAllocator(allocator);
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setAutoRead(boolean autoRead) {
/* 175 */     super.setAutoRead(autoRead);
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 182 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 189 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 195 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 201 */     super.setMessageSizeEstimator(estimator);
/* 202 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueServerSocketChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */