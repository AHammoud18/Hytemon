/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
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
/*     */ public final class EpollServerSocketChannelConfig
/*     */   extends EpollServerChannelConfig
/*     */   implements ServerSocketChannelConfig
/*     */ {
/*     */   EpollServerSocketChannelConfig(EpollServerSocketChannel channel) {
/*  34 */     super(channel);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     setReuseAddress(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  44 */     return getOptions(super.getOptions(), new ChannelOption[] { EpollChannelOption.SO_REUSEPORT, EpollChannelOption.IP_FREEBIND, EpollChannelOption.IP_TRANSPARENT, EpollChannelOption.TCP_DEFER_ACCEPT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  51 */     if (option == EpollChannelOption.SO_REUSEPORT) {
/*  52 */       return (T)Boolean.valueOf(isReusePort());
/*     */     }
/*  54 */     if (option == EpollChannelOption.IP_FREEBIND) {
/*  55 */       return (T)Boolean.valueOf(isFreeBind());
/*     */     }
/*  57 */     if (option == EpollChannelOption.IP_TRANSPARENT) {
/*  58 */       return (T)Boolean.valueOf(isIpTransparent());
/*     */     }
/*  60 */     if (option == EpollChannelOption.TCP_DEFER_ACCEPT) {
/*  61 */       return (T)Integer.valueOf(getTcpDeferAccept());
/*     */     }
/*  63 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/*  68 */     validate(option, value);
/*     */     
/*  70 */     if (option == EpollChannelOption.SO_REUSEPORT) {
/*  71 */       setReusePort(((Boolean)value).booleanValue());
/*  72 */     } else if (option == EpollChannelOption.IP_FREEBIND) {
/*  73 */       setFreeBind(((Boolean)value).booleanValue());
/*  74 */     } else if (option == EpollChannelOption.IP_TRANSPARENT) {
/*  75 */       setIpTransparent(((Boolean)value).booleanValue());
/*  76 */     } else if (option == EpollChannelOption.TCP_MD5SIG) {
/*     */       
/*  78 */       Map<InetAddress, byte[]> m = (Map<InetAddress, byte[]>)value;
/*  79 */       setTcpMd5Sig(m);
/*  80 */     } else if (option == EpollChannelOption.TCP_DEFER_ACCEPT) {
/*  81 */       setTcpDeferAccept(((Integer)value).intValue());
/*     */     } else {
/*  83 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/*  91 */     super.setReuseAddress(reuseAddress);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*  97 */     super.setReceiveBufferSize(receiveBufferSize);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setBacklog(int backlog) {
/* 108 */     super.setBacklog(backlog);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 114 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 121 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 127 */     super.setWriteSpinCount(writeSpinCount);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 133 */     super.setAllocator(allocator);
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 139 */     super.setRecvByteBufAllocator(allocator);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setAutoRead(boolean autoRead) {
/* 145 */     super.setAutoRead(autoRead);
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 152 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 159 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 165 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 171 */     super.setMessageSizeEstimator(estimator);
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setTcpMd5Sig(Map<InetAddress, byte[]> keys) {
/*     */     try {
/* 182 */       ((EpollServerSocketChannel)this.channel).setTcpMd5Sig(keys);
/* 183 */       return this;
/* 184 */     } catch (IOException e) {
/* 185 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReusePort() {
/*     */     try {
/* 194 */       return ((EpollServerSocketChannel)this.channel).socket.isReusePort();
/* 195 */     } catch (IOException e) {
/* 196 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setReusePort(boolean reusePort) {
/*     */     try {
/* 209 */       ((EpollServerSocketChannel)this.channel).socket.setReusePort(reusePort);
/* 210 */       return this;
/* 211 */     } catch (IOException e) {
/* 212 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFreeBind() {
/*     */     try {
/* 222 */       return ((EpollServerSocketChannel)this.channel).socket.isIpFreeBind();
/* 223 */     } catch (IOException e) {
/* 224 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setFreeBind(boolean freeBind) {
/*     */     try {
/* 234 */       ((EpollServerSocketChannel)this.channel).socket.setIpFreeBind(freeBind);
/* 235 */       return this;
/* 236 */     } catch (IOException e) {
/* 237 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIpTransparent() {
/*     */     try {
/* 247 */       return ((EpollServerSocketChannel)this.channel).socket.isIpTransparent();
/* 248 */     } catch (IOException e) {
/* 249 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setIpTransparent(boolean transparent) {
/*     */     try {
/* 259 */       ((EpollServerSocketChannel)this.channel).socket.setIpTransparent(transparent);
/* 260 */       return this;
/* 261 */     } catch (IOException e) {
/* 262 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig setTcpDeferAccept(int deferAccept) {
/*     */     try {
/* 271 */       ((EpollServerSocketChannel)this.channel).socket.setTcpDeferAccept(deferAccept);
/* 272 */       return this;
/* 273 */     } catch (IOException e) {
/* 274 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpDeferAccept() {
/*     */     try {
/* 283 */       return ((EpollServerSocketChannel)this.channel).socket.getTcpDeferAccept();
/* 284 */     } catch (IOException e) {
/* 285 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollServerSocketChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */