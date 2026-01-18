/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
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
/*     */ public final class EpollDomainSocketChannelConfig
/*     */   extends EpollChannelConfig
/*     */   implements DomainSocketChannelConfig, DuplexChannelConfig
/*     */ {
/*  39 */   private volatile DomainSocketReadMode mode = DomainSocketReadMode.BYTES;
/*     */   private volatile boolean allowHalfClosure;
/*     */   
/*     */   EpollDomainSocketChannelConfig(AbstractEpollChannel channel) {
/*  43 */     super((Channel)channel);
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
/*     */   @Deprecated
/*     */   public EpollDomainSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/*  91 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/*  97 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 103 */     super.setWriteSpinCount(writeSpinCount);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 109 */     super.setRecvByteBufAllocator(allocator);
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 115 */     super.setAllocator(allocator);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setAutoClose(boolean autoClose) {
/* 121 */     super.setAutoClose(autoClose);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 127 */     super.setMessageSizeEstimator(estimator);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollDomainSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 134 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollDomainSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 141 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 147 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setAutoRead(boolean autoRead) {
/* 153 */     super.setAutoRead(autoRead);
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setEpollMode(EpollMode mode) {
/* 159 */     super.setEpollMode(mode);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setReadMode(DomainSocketReadMode mode) {
/* 165 */     this.mode = (DomainSocketReadMode)ObjectUtil.checkNotNull(mode, "mode");
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketReadMode getReadMode() {
/* 171 */     return this.mode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowHalfClosure() {
/* 176 */     return this.allowHalfClosure;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 181 */     this.allowHalfClosure = allowHalfClosure;
/* 182 */     return this;
/*     */   }
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 187 */       return ((EpollDomainSocketChannel)this.channel).socket.getSendBufferSize();
/* 188 */     } catch (IOException e) {
/* 189 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EpollDomainSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 195 */       ((EpollDomainSocketChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 196 */       return this;
/* 197 */     } catch (IOException e) {
/* 198 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 204 */       return ((EpollDomainSocketChannel)this.channel).socket.getReceiveBufferSize();
/* 205 */     } catch (IOException e) {
/* 206 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EpollDomainSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 212 */       ((EpollDomainSocketChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
/* 213 */       return this;
/* 214 */     } catch (IOException e) {
/* 215 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollDomainSocketChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */