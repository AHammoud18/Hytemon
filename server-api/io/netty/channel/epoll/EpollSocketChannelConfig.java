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
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EpollSocketChannelConfig
/*     */   extends EpollChannelConfig
/*     */   implements SocketChannelConfig
/*     */ {
/*     */   private volatile boolean allowHalfClosure;
/*     */   private volatile boolean tcpFastopen;
/*     */   
/*     */   EpollSocketChannelConfig(EpollSocketChannel channel) {
/*  48 */     super((Channel)channel);
/*     */     
/*  50 */     if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
/*  51 */       setTcpNoDelay(true);
/*     */     }
/*  53 */     calculateMaxBytesPerGatheringWrite();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  58 */     return getOptions(super
/*  59 */         .getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE, EpollChannelOption.TCP_CORK, EpollChannelOption.TCP_NOTSENT_LOWAT, EpollChannelOption.TCP_KEEPCNT, EpollChannelOption.TCP_KEEPIDLE, EpollChannelOption.TCP_KEEPINTVL, EpollChannelOption.TCP_MD5SIG, EpollChannelOption.TCP_QUICKACK, EpollChannelOption.IP_BIND_ADDRESS_NO_PORT, EpollChannelOption.IP_TRANSPARENT, ChannelOption.TCP_FASTOPEN_CONNECT, EpollChannelOption.SO_BUSY_POLL });
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
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  71 */     if (option == ChannelOption.SO_RCVBUF) {
/*  72 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  74 */     if (option == ChannelOption.SO_SNDBUF) {
/*  75 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  77 */     if (option == ChannelOption.TCP_NODELAY) {
/*  78 */       return (T)Boolean.valueOf(isTcpNoDelay());
/*     */     }
/*  80 */     if (option == ChannelOption.SO_KEEPALIVE) {
/*  81 */       return (T)Boolean.valueOf(isKeepAlive());
/*     */     }
/*  83 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  84 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  86 */     if (option == ChannelOption.SO_LINGER) {
/*  87 */       return (T)Integer.valueOf(getSoLinger());
/*     */     }
/*  89 */     if (option == ChannelOption.IP_TOS) {
/*  90 */       return (T)Integer.valueOf(getTrafficClass());
/*     */     }
/*  92 */     if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  93 */       return (T)Boolean.valueOf(isAllowHalfClosure());
/*     */     }
/*  95 */     if (option == EpollChannelOption.TCP_CORK) {
/*  96 */       return (T)Boolean.valueOf(isTcpCork());
/*     */     }
/*  98 */     if (option == EpollChannelOption.TCP_NOTSENT_LOWAT) {
/*  99 */       return (T)Long.valueOf(getTcpNotSentLowAt());
/*     */     }
/* 101 */     if (option == EpollChannelOption.TCP_KEEPIDLE) {
/* 102 */       return (T)Integer.valueOf(getTcpKeepIdle());
/*     */     }
/* 104 */     if (option == EpollChannelOption.TCP_KEEPINTVL) {
/* 105 */       return (T)Integer.valueOf(getTcpKeepIntvl());
/*     */     }
/* 107 */     if (option == EpollChannelOption.TCP_KEEPCNT) {
/* 108 */       return (T)Integer.valueOf(getTcpKeepCnt());
/*     */     }
/* 110 */     if (option == EpollChannelOption.TCP_USER_TIMEOUT) {
/* 111 */       return (T)Integer.valueOf(getTcpUserTimeout());
/*     */     }
/* 113 */     if (option == EpollChannelOption.TCP_QUICKACK) {
/* 114 */       return (T)Boolean.valueOf(isTcpQuickAck());
/*     */     }
/* 116 */     if (option == EpollChannelOption.IP_BIND_ADDRESS_NO_PORT) {
/* 117 */       return (T)Boolean.valueOf(isIpBindAddressNoPort());
/*     */     }
/* 119 */     if (option == EpollChannelOption.IP_TRANSPARENT) {
/* 120 */       return (T)Boolean.valueOf(isIpTransparent());
/*     */     }
/* 122 */     if (option == ChannelOption.TCP_FASTOPEN_CONNECT) {
/* 123 */       return (T)Boolean.valueOf(isTcpFastOpenConnect());
/*     */     }
/* 125 */     if (option == EpollChannelOption.SO_BUSY_POLL) {
/* 126 */       return (T)Integer.valueOf(getSoBusyPoll());
/*     */     }
/* 128 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 133 */     validate(option, value);
/*     */     
/* 135 */     if (option == ChannelOption.SO_RCVBUF) {
/* 136 */       setReceiveBufferSize(((Integer)value).intValue());
/* 137 */     } else if (option == ChannelOption.SO_SNDBUF) {
/* 138 */       setSendBufferSize(((Integer)value).intValue());
/* 139 */     } else if (option == ChannelOption.TCP_NODELAY) {
/* 140 */       setTcpNoDelay(((Boolean)value).booleanValue());
/* 141 */     } else if (option == ChannelOption.SO_KEEPALIVE) {
/* 142 */       setKeepAlive(((Boolean)value).booleanValue());
/* 143 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/* 144 */       setReuseAddress(((Boolean)value).booleanValue());
/* 145 */     } else if (option == ChannelOption.SO_LINGER) {
/* 146 */       setSoLinger(((Integer)value).intValue());
/* 147 */     } else if (option == ChannelOption.IP_TOS) {
/* 148 */       setTrafficClass(((Integer)value).intValue());
/* 149 */     } else if (option == ChannelOption.ALLOW_HALF_CLOSURE) {
/* 150 */       setAllowHalfClosure(((Boolean)value).booleanValue());
/* 151 */     } else if (option == EpollChannelOption.TCP_CORK) {
/* 152 */       setTcpCork(((Boolean)value).booleanValue());
/* 153 */     } else if (option == EpollChannelOption.TCP_NOTSENT_LOWAT) {
/* 154 */       setTcpNotSentLowAt(((Long)value).longValue());
/* 155 */     } else if (option == EpollChannelOption.TCP_KEEPIDLE) {
/* 156 */       setTcpKeepIdle(((Integer)value).intValue());
/* 157 */     } else if (option == EpollChannelOption.TCP_KEEPCNT) {
/* 158 */       setTcpKeepCnt(((Integer)value).intValue());
/* 159 */     } else if (option == EpollChannelOption.TCP_KEEPINTVL) {
/* 160 */       setTcpKeepIntvl(((Integer)value).intValue());
/* 161 */     } else if (option == EpollChannelOption.TCP_USER_TIMEOUT) {
/* 162 */       setTcpUserTimeout(((Integer)value).intValue());
/* 163 */     } else if (option == EpollChannelOption.IP_BIND_ADDRESS_NO_PORT) {
/* 164 */       setIpBindAddressNoPort(((Boolean)value).booleanValue());
/* 165 */     } else if (option == EpollChannelOption.IP_TRANSPARENT) {
/* 166 */       setIpTransparent(((Boolean)value).booleanValue());
/* 167 */     } else if (option == EpollChannelOption.TCP_MD5SIG) {
/*     */       
/* 169 */       Map<InetAddress, byte[]> m = (Map<InetAddress, byte[]>)value;
/* 170 */       setTcpMd5Sig(m);
/* 171 */     } else if (option == EpollChannelOption.TCP_QUICKACK) {
/* 172 */       setTcpQuickAck(((Boolean)value).booleanValue());
/* 173 */     } else if (option == ChannelOption.TCP_FASTOPEN_CONNECT) {
/* 174 */       setTcpFastOpenConnect(((Boolean)value).booleanValue());
/* 175 */     } else if (option == EpollChannelOption.SO_BUSY_POLL) {
/* 176 */       setSoBusyPoll(((Integer)value).intValue());
/*     */     } else {
/* 178 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 187 */       return ((EpollSocketChannel)this.channel).socket.getReceiveBufferSize();
/* 188 */     } catch (IOException e) {
/* 189 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 196 */       return ((EpollSocketChannel)this.channel).socket.getSendBufferSize();
/* 197 */     } catch (IOException e) {
/* 198 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSoLinger() {
/*     */     try {
/* 205 */       return ((EpollSocketChannel)this.channel).socket.getSoLinger();
/* 206 */     } catch (IOException e) {
/* 207 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrafficClass() {
/*     */     try {
/* 214 */       return ((EpollSocketChannel)this.channel).socket.getTrafficClass();
/* 215 */     } catch (IOException e) {
/* 216 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isKeepAlive() {
/*     */     try {
/* 223 */       return ((EpollSocketChannel)this.channel).socket.isKeepAlive();
/* 224 */     } catch (IOException e) {
/* 225 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*     */     try {
/* 232 */       return ((EpollSocketChannel)this.channel).socket.isReuseAddress();
/* 233 */     } catch (IOException e) {
/* 234 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTcpNoDelay() {
/*     */     try {
/* 241 */       return ((EpollSocketChannel)this.channel).socket.isTcpNoDelay();
/* 242 */     } catch (IOException e) {
/* 243 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTcpCork() {
/*     */     try {
/* 252 */       return ((EpollSocketChannel)this.channel).socket.isTcpCork();
/* 253 */     } catch (IOException e) {
/* 254 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSoBusyPoll() {
/*     */     try {
/* 263 */       return ((EpollSocketChannel)this.channel).socket.getSoBusyPoll();
/* 264 */     } catch (IOException e) {
/* 265 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTcpNotSentLowAt() {
/*     */     try {
/* 275 */       return ((EpollSocketChannel)this.channel).socket.getTcpNotSentLowAt();
/* 276 */     } catch (IOException e) {
/* 277 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpKeepIdle() {
/*     */     try {
/* 286 */       return ((EpollSocketChannel)this.channel).socket.getTcpKeepIdle();
/* 287 */     } catch (IOException e) {
/* 288 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpKeepIntvl() {
/*     */     try {
/* 297 */       return ((EpollSocketChannel)this.channel).socket.getTcpKeepIntvl();
/* 298 */     } catch (IOException e) {
/* 299 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpKeepCnt() {
/*     */     try {
/* 308 */       return ((EpollSocketChannel)this.channel).socket.getTcpKeepCnt();
/* 309 */     } catch (IOException e) {
/* 310 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTcpUserTimeout() {
/*     */     try {
/* 319 */       return ((EpollSocketChannel)this.channel).socket.getTcpUserTimeout();
/* 320 */     } catch (IOException e) {
/* 321 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setKeepAlive(boolean keepAlive) {
/*     */     try {
/* 328 */       ((EpollSocketChannel)this.channel).socket.setKeepAlive(keepAlive);
/* 329 */       return this;
/* 330 */     } catch (IOException e) {
/* 331 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
/* 338 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 344 */       ((EpollSocketChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
/* 345 */       return this;
/* 346 */     } catch (IOException e) {
/* 347 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/* 354 */       ((EpollSocketChannel)this.channel).socket.setReuseAddress(reuseAddress);
/* 355 */       return this;
/* 356 */     } catch (IOException e) {
/* 357 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 364 */       ((EpollSocketChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 365 */       calculateMaxBytesPerGatheringWrite();
/* 366 */       return this;
/* 367 */     } catch (IOException e) {
/* 368 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setSoLinger(int soLinger) {
/*     */     try {
/* 375 */       ((EpollSocketChannel)this.channel).socket.setSoLinger(soLinger);
/* 376 */       return this;
/* 377 */     } catch (IOException e) {
/* 378 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpNoDelay(boolean tcpNoDelay) {
/*     */     try {
/* 385 */       ((EpollSocketChannel)this.channel).socket.setTcpNoDelay(tcpNoDelay);
/* 386 */       return this;
/* 387 */     } catch (IOException e) {
/* 388 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpCork(boolean tcpCork) {
/*     */     try {
/* 397 */       ((EpollSocketChannel)this.channel).socket.setTcpCork(tcpCork);
/* 398 */       return this;
/* 399 */     } catch (IOException e) {
/* 400 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setSoBusyPoll(int loopMicros) {
/*     */     try {
/* 409 */       ((EpollSocketChannel)this.channel).socket.setSoBusyPoll(loopMicros);
/* 410 */       return this;
/* 411 */     } catch (IOException e) {
/* 412 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpNotSentLowAt(long tcpNotSentLowAt) {
/*     */     try {
/* 422 */       ((EpollSocketChannel)this.channel).socket.setTcpNotSentLowAt(tcpNotSentLowAt);
/* 423 */       return this;
/* 424 */     } catch (IOException e) {
/* 425 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTrafficClass(int trafficClass) {
/*     */     try {
/* 432 */       ((EpollSocketChannel)this.channel).socket.setTrafficClass(trafficClass);
/* 433 */       return this;
/* 434 */     } catch (IOException e) {
/* 435 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpKeepIdle(int seconds) {
/*     */     try {
/* 444 */       ((EpollSocketChannel)this.channel).socket.setTcpKeepIdle(seconds);
/* 445 */       return this;
/* 446 */     } catch (IOException e) {
/* 447 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpKeepIntvl(int seconds) {
/*     */     try {
/* 456 */       ((EpollSocketChannel)this.channel).socket.setTcpKeepIntvl(seconds);
/* 457 */       return this;
/* 458 */     } catch (IOException e) {
/* 459 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollSocketChannelConfig setTcpKeepCntl(int probes) {
/* 468 */     return setTcpKeepCnt(probes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpKeepCnt(int probes) {
/*     */     try {
/* 476 */       ((EpollSocketChannel)this.channel).socket.setTcpKeepCnt(probes);
/* 477 */       return this;
/* 478 */     } catch (IOException e) {
/* 479 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpUserTimeout(int milliseconds) {
/*     */     try {
/* 488 */       ((EpollSocketChannel)this.channel).socket.setTcpUserTimeout(milliseconds);
/* 489 */       return this;
/* 490 */     } catch (IOException e) {
/* 491 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIpBindAddressNoPort() {
/*     */     try {
/* 500 */       return ((EpollSocketChannel)this.channel).socket.isIpBindAddressNoPort();
/* 501 */     } catch (IOException e) {
/* 502 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setIpBindAddressNoPort(boolean ipBindAddressNoPort) {
/*     */     try {
/* 514 */       ((EpollSocketChannel)this.channel).socket.setIpBindAddressNoPort(ipBindAddressNoPort);
/* 515 */       return this;
/* 516 */     } catch (IOException e) {
/* 517 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIpTransparent() {
/*     */     try {
/* 527 */       return ((EpollSocketChannel)this.channel).socket.isIpTransparent();
/* 528 */     } catch (IOException e) {
/* 529 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setIpTransparent(boolean transparent) {
/*     */     try {
/* 539 */       ((EpollSocketChannel)this.channel).socket.setIpTransparent(transparent);
/* 540 */       return this;
/* 541 */     } catch (IOException e) {
/* 542 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpMd5Sig(Map<InetAddress, byte[]> keys) {
/*     */     try {
/* 553 */       ((EpollSocketChannel)this.channel).setTcpMd5Sig(keys);
/* 554 */       return this;
/* 555 */     } catch (IOException e) {
/* 556 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpQuickAck(boolean quickAck) {
/*     */     try {
/* 567 */       ((EpollSocketChannel)this.channel).socket.setTcpQuickAck(quickAck);
/* 568 */       return this;
/* 569 */     } catch (IOException e) {
/* 570 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTcpQuickAck() {
/*     */     try {
/* 580 */       return ((EpollSocketChannel)this.channel).socket.isTcpQuickAck();
/* 581 */     } catch (IOException e) {
/* 582 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setTcpFastOpenConnect(boolean fastOpenConnect) {
/* 593 */     this.tcpFastopen = fastOpenConnect;
/* 594 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTcpFastOpenConnect() {
/* 601 */     return this.tcpFastopen;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowHalfClosure() {
/* 606 */     return this.allowHalfClosure;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
/* 611 */     this.allowHalfClosure = allowHalfClosure;
/* 612 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 617 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 618 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 624 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 625 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 630 */     super.setWriteSpinCount(writeSpinCount);
/* 631 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 636 */     super.setAllocator(allocator);
/* 637 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 642 */     super.setRecvByteBufAllocator(allocator);
/* 643 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAutoRead(boolean autoRead) {
/* 648 */     super.setAutoRead(autoRead);
/* 649 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setAutoClose(boolean autoClose) {
/* 654 */     super.setAutoClose(autoClose);
/* 655 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 661 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 662 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 668 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 669 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 674 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 675 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 680 */     super.setMessageSizeEstimator(estimator);
/* 681 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig setEpollMode(EpollMode mode) {
/* 686 */     super.setEpollMode(mode);
/* 687 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void calculateMaxBytesPerGatheringWrite() {
/* 692 */     int newSendBufferSize = getSendBufferSize() << 1;
/* 693 */     if (newSendBufferSize > 0)
/* 694 */       setMaxBytesPerGatheringWrite(newSendBufferSize); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollSocketChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */