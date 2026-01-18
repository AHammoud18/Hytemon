/*     */ package io.netty.channel.socket;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.ServerChannelRecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.SocketException;
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
/*     */ public class DefaultServerSocketChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements ServerSocketChannelConfig
/*     */ {
/*     */   protected final ServerSocket javaSocket;
/*  45 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultServerSocketChannelConfig(ServerSocketChannel channel, ServerSocket javaSocket) {
/*  51 */     super((Channel)channel, (RecvByteBufAllocator)new ServerChannelRecvByteBufAllocator());
/*  52 */     this.javaSocket = (ServerSocket)ObjectUtil.checkNotNull(javaSocket, "javaSocket");
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  57 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  63 */     if (option == ChannelOption.SO_RCVBUF) {
/*  64 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  66 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  67 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  69 */     if (option == ChannelOption.SO_BACKLOG) {
/*  70 */       return (T)Integer.valueOf(getBacklog());
/*     */     }
/*     */     
/*  73 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  78 */     validate(option, value);
/*     */     
/*  80 */     if (option == ChannelOption.SO_RCVBUF) {
/*  81 */       setReceiveBufferSize(((Integer)value).intValue());
/*  82 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/*  83 */       setReuseAddress(((Boolean)value).booleanValue());
/*  84 */     } else if (option == ChannelOption.SO_BACKLOG) {
/*  85 */       setBacklog(((Integer)value).intValue());
/*     */     } else {
/*  87 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*     */     try {
/*  96 */       return this.javaSocket.getReuseAddress();
/*  97 */     } catch (SocketException e) {
/*  98 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/* 105 */       this.javaSocket.setReuseAddress(reuseAddress);
/* 106 */     } catch (SocketException e) {
/* 107 */       throw new ChannelException(e);
/*     */     } 
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 115 */       return this.javaSocket.getReceiveBufferSize();
/* 116 */     } catch (SocketException e) {
/* 117 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 124 */       this.javaSocket.setReceiveBufferSize(receiveBufferSize);
/* 125 */     } catch (SocketException e) {
/* 126 */       throw new ChannelException(e);
/*     */     } 
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 133 */     this.javaSocket.setPerformancePreferences(connectionTime, latency, bandwidth);
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBacklog() {
/* 139 */     return this.backlog;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setBacklog(int backlog) {
/* 144 */     ObjectUtil.checkPositiveOrZero(backlog, "backlog");
/* 145 */     this.backlog = backlog;
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 151 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 158 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 164 */     super.setWriteSpinCount(writeSpinCount);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 170 */     super.setAllocator(allocator);
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 176 */     super.setRecvByteBufAllocator(allocator);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setAutoRead(boolean autoRead) {
/* 182 */     super.setAutoRead(autoRead);
/* 183 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 188 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 194 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 195 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 200 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 206 */     super.setMessageSizeEstimator(estimator);
/* 207 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\DefaultServerSocketChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */