/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.ServerChannelRecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import io.netty.util.NetUtil;
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
/*     */ public class KQueueServerChannelConfig
/*     */   extends KQueueChannelConfig
/*     */   implements ServerSocketChannelConfig
/*     */ {
/*  38 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */   private volatile boolean enableTcpFastOpen;
/*     */   
/*     */   KQueueServerChannelConfig(AbstractKQueueChannel channel) {
/*  42 */     super(channel, (RecvByteBufAllocator)new ServerChannelRecvByteBufAllocator());
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  47 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG, ChannelOption.TCP_FASTOPEN });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  53 */     if (option == ChannelOption.SO_RCVBUF) {
/*  54 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  56 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  57 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  59 */     if (option == ChannelOption.SO_BACKLOG) {
/*  60 */       return (T)Integer.valueOf(getBacklog());
/*     */     }
/*  62 */     if (option == ChannelOption.TCP_FASTOPEN) {
/*  63 */       return isTcpFastOpen() ? (T)Integer.valueOf(1) : (T)Integer.valueOf(0);
/*     */     }
/*  65 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  70 */     validate(option, value);
/*     */     
/*  72 */     if (option == ChannelOption.SO_RCVBUF) {
/*  73 */       setReceiveBufferSize(((Integer)value).intValue());
/*  74 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/*  75 */       setReuseAddress(((Boolean)value).booleanValue());
/*  76 */     } else if (option == ChannelOption.SO_BACKLOG) {
/*  77 */       setBacklog(((Integer)value).intValue());
/*  78 */     } else if (option == ChannelOption.TCP_FASTOPEN) {
/*  79 */       setTcpFastOpen((((Integer)value).intValue() > 0));
/*     */     } else {
/*  81 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*     */     try {
/*  90 */       return ((AbstractKQueueChannel)this.channel).socket.isReuseAddress();
/*  91 */     } catch (IOException e) {
/*  92 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/*  99 */       ((AbstractKQueueChannel)this.channel).socket.setReuseAddress(reuseAddress);
/* 100 */       return this;
/* 101 */     } catch (IOException e) {
/* 102 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 109 */       return ((AbstractKQueueChannel)this.channel).socket.getReceiveBufferSize();
/* 110 */     } catch (IOException e) {
/* 111 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 118 */       ((AbstractKQueueChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
/* 119 */       return this;
/* 120 */     } catch (IOException e) {
/* 121 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBacklog() {
/* 127 */     return this.backlog;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setBacklog(int backlog) {
/* 132 */     ObjectUtil.checkPositiveOrZero(backlog, "backlog");
/* 133 */     this.backlog = backlog;
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTcpFastOpen() {
/* 143 */     return this.enableTcpFastOpen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setTcpFastOpen(boolean enableTcpFastOpen) {
/* 155 */     this.enableTcpFastOpen = enableTcpFastOpen;
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
/* 161 */     super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 172 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 179 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 185 */     super.setWriteSpinCount(writeSpinCount);
/* 186 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 191 */     super.setAllocator(allocator);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 197 */     super.setRecvByteBufAllocator(allocator);
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setAutoRead(boolean autoRead) {
/* 203 */     super.setAutoRead(autoRead);
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 210 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 217 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 223 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 229 */     super.setMessageSizeEstimator(estimator);
/* 230 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueServerChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */