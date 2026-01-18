/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
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
/*     */ public class EpollServerChannelConfig
/*     */   extends EpollChannelConfig
/*     */   implements ServerSocketChannelConfig
/*     */ {
/*  38 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */   private volatile int pendingFastOpenRequestsThreshold;
/*     */   
/*     */   EpollServerChannelConfig(AbstractEpollChannel channel) {
/*  42 */     super((Channel)channel, (RecvByteBufAllocator)new ServerChannelRecvByteBufAllocator());
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
/*  63 */       return (T)Integer.valueOf(getTcpFastopen());
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
/*  79 */       setTcpFastopen(((Integer)value).intValue());
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
/*  90 */       return ((AbstractEpollChannel)this.channel).socket.isReuseAddress();
/*  91 */     } catch (IOException e) {
/*  92 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/*  99 */       ((AbstractEpollChannel)this.channel).socket.setReuseAddress(reuseAddress);
/* 100 */       return this;
/* 101 */     } catch (IOException e) {
/* 102 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 109 */       return ((AbstractEpollChannel)this.channel).socket.getReceiveBufferSize();
/* 110 */     } catch (IOException e) {
/* 111 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 118 */       ((AbstractEpollChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
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
/*     */   public EpollServerChannelConfig setBacklog(int backlog) {
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
/*     */   public int getTcpFastopen() {
/* 143 */     return this.pendingFastOpenRequestsThreshold;
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
/*     */   
/*     */   public EpollServerChannelConfig setTcpFastopen(int pendingFastOpenRequestsThreshold) {
/* 156 */     this.pendingFastOpenRequestsThreshold = ObjectUtil.checkPositiveOrZero(pendingFastOpenRequestsThreshold, "pendingFastOpenRequestsThreshold");
/*     */     
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 168 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 175 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 181 */     super.setWriteSpinCount(writeSpinCount);
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 187 */     super.setAllocator(allocator);
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 193 */     super.setRecvByteBufAllocator(allocator);
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setAutoRead(boolean autoRead) {
/* 199 */     super.setAutoRead(autoRead);
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 206 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 213 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 219 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 225 */     super.setMessageSizeEstimator(estimator);
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig setEpollMode(EpollMode mode) {
/* 231 */     super.setEpollMode(mode);
/* 232 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollServerChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */