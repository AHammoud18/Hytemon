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
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
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
/*     */ public final class EpollDatagramChannelConfig
/*     */   extends EpollChannelConfig
/*     */   implements DatagramChannelConfig
/*     */ {
/*     */   private boolean activeOnOpen;
/*     */   private volatile int maxDatagramSize;
/*     */   private volatile boolean gro;
/*     */   
/*     */   EpollDatagramChannelConfig(EpollDatagramChannel channel) {
/*  39 */     super((Channel)channel, (RecvByteBufAllocator)new FixedRecvByteBufAllocator(2048));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  45 */     return getOptions(super
/*  46 */         .getOptions(), new ChannelOption[] { ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, EpollChannelOption.SO_REUSEPORT, EpollChannelOption.IP_FREEBIND, EpollChannelOption.IP_TRANSPARENT, EpollChannelOption.IP_RECVORIGDSTADDR, EpollChannelOption.MAX_DATAGRAM_PAYLOAD_SIZE, EpollChannelOption.UDP_GRO, EpollChannelOption.IP_MULTICAST_ALL });
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
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  59 */     if (option == ChannelOption.SO_BROADCAST) {
/*  60 */       return (T)Boolean.valueOf(isBroadcast());
/*     */     }
/*  62 */     if (option == ChannelOption.SO_RCVBUF) {
/*  63 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  65 */     if (option == ChannelOption.SO_SNDBUF) {
/*  66 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  68 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  69 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  71 */     if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/*  72 */       return (T)Boolean.valueOf(isLoopbackModeDisabled());
/*     */     }
/*  74 */     if (option == ChannelOption.IP_MULTICAST_ADDR) {
/*  75 */       return (T)getInterface();
/*     */     }
/*  77 */     if (option == ChannelOption.IP_MULTICAST_IF) {
/*  78 */       return (T)getNetworkInterface();
/*     */     }
/*  80 */     if (option == ChannelOption.IP_MULTICAST_TTL) {
/*  81 */       return (T)Integer.valueOf(getTimeToLive());
/*     */     }
/*  83 */     if (option == ChannelOption.IP_TOS) {
/*  84 */       return (T)Integer.valueOf(getTrafficClass());
/*     */     }
/*  86 */     if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/*  87 */       return (T)Boolean.valueOf(this.activeOnOpen);
/*     */     }
/*  89 */     if (option == EpollChannelOption.SO_REUSEPORT) {
/*  90 */       return (T)Boolean.valueOf(isReusePort());
/*     */     }
/*  92 */     if (option == EpollChannelOption.IP_TRANSPARENT) {
/*  93 */       return (T)Boolean.valueOf(isIpTransparent());
/*     */     }
/*  95 */     if (option == EpollChannelOption.IP_FREEBIND) {
/*  96 */       return (T)Boolean.valueOf(isFreeBind());
/*     */     }
/*  98 */     if (option == EpollChannelOption.IP_RECVORIGDSTADDR) {
/*  99 */       return (T)Boolean.valueOf(isIpRecvOrigDestAddr());
/*     */     }
/* 101 */     if (option == EpollChannelOption.IP_MULTICAST_ALL) {
/* 102 */       return (T)Boolean.valueOf(isIpMulticastAll());
/*     */     }
/* 104 */     if (option == EpollChannelOption.MAX_DATAGRAM_PAYLOAD_SIZE) {
/* 105 */       return (T)Integer.valueOf(getMaxDatagramPayloadSize());
/*     */     }
/* 107 */     if (option == EpollChannelOption.UDP_GRO) {
/* 108 */       return (T)Boolean.valueOf(isUdpGro());
/*     */     }
/* 110 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 116 */     validate(option, value);
/*     */     
/* 118 */     if (option == ChannelOption.SO_BROADCAST) {
/* 119 */       setBroadcast(((Boolean)value).booleanValue());
/* 120 */     } else if (option == ChannelOption.SO_RCVBUF) {
/* 121 */       setReceiveBufferSize(((Integer)value).intValue());
/* 122 */     } else if (option == ChannelOption.SO_SNDBUF) {
/* 123 */       setSendBufferSize(((Integer)value).intValue());
/* 124 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/* 125 */       setReuseAddress(((Boolean)value).booleanValue());
/* 126 */     } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/* 127 */       setLoopbackModeDisabled(((Boolean)value).booleanValue());
/* 128 */     } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
/* 129 */       setInterface((InetAddress)value);
/* 130 */     } else if (option == ChannelOption.IP_MULTICAST_IF) {
/* 131 */       setNetworkInterface((NetworkInterface)value);
/* 132 */     } else if (option == ChannelOption.IP_MULTICAST_TTL) {
/* 133 */       setTimeToLive(((Integer)value).intValue());
/* 134 */     } else if (option == EpollChannelOption.IP_MULTICAST_ALL) {
/* 135 */       setIpMulticastAll(((Boolean)value).booleanValue());
/* 136 */     } else if (option == ChannelOption.IP_TOS) {
/* 137 */       setTrafficClass(((Integer)value).intValue());
/* 138 */     } else if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 139 */       setActiveOnOpen(((Boolean)value).booleanValue());
/* 140 */     } else if (option == EpollChannelOption.SO_REUSEPORT) {
/* 141 */       setReusePort(((Boolean)value).booleanValue());
/* 142 */     } else if (option == EpollChannelOption.IP_FREEBIND) {
/* 143 */       setFreeBind(((Boolean)value).booleanValue());
/* 144 */     } else if (option == EpollChannelOption.IP_TRANSPARENT) {
/* 145 */       setIpTransparent(((Boolean)value).booleanValue());
/* 146 */     } else if (option == EpollChannelOption.IP_RECVORIGDSTADDR) {
/* 147 */       setIpRecvOrigDestAddr(((Boolean)value).booleanValue());
/* 148 */     } else if (option == EpollChannelOption.MAX_DATAGRAM_PAYLOAD_SIZE) {
/* 149 */       setMaxDatagramPayloadSize(((Integer)value).intValue());
/* 150 */     } else if (option == EpollChannelOption.UDP_GRO) {
/* 151 */       setUdpGro(((Boolean)value).booleanValue());
/*     */     } else {
/* 153 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 156 */     return true;
/*     */   }
/*     */   
/*     */   private void setActiveOnOpen(boolean activeOnOpen) {
/* 160 */     if (this.channel.isRegistered()) {
/* 161 */       throw new IllegalStateException("Can only changed before channel was registered");
/*     */     }
/* 163 */     this.activeOnOpen = activeOnOpen;
/*     */   }
/*     */   
/*     */   boolean getActiveOnOpen() {
/* 167 */     return this.activeOnOpen;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 172 */     super.setMessageSizeEstimator(estimator);
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollDatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 179 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollDatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 186 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 192 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setAutoClose(boolean autoClose) {
/* 198 */     super.setAutoClose(autoClose);
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setAutoRead(boolean autoRead) {
/* 204 */     super.setAutoRead(autoRead);
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 210 */     super.setRecvByteBufAllocator(allocator);
/* 211 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 216 */     super.setWriteSpinCount(writeSpinCount);
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 222 */     super.setAllocator(allocator);
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 228 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 235 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 242 */       return ((EpollDatagramChannel)this.channel).socket.getSendBufferSize();
/* 243 */     } catch (IOException e) {
/* 244 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 251 */       ((EpollDatagramChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 252 */       return this;
/* 253 */     } catch (IOException e) {
/* 254 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 261 */       return ((EpollDatagramChannel)this.channel).socket.getReceiveBufferSize();
/* 262 */     } catch (IOException e) {
/* 263 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 270 */       ((EpollDatagramChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
/* 271 */       return this;
/* 272 */     } catch (IOException e) {
/* 273 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrafficClass() {
/*     */     try {
/* 280 */       return ((EpollDatagramChannel)this.channel).socket.getTrafficClass();
/* 281 */     } catch (IOException e) {
/* 282 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setTrafficClass(int trafficClass) {
/*     */     try {
/* 289 */       ((EpollDatagramChannel)this.channel).socket.setTrafficClass(trafficClass);
/* 290 */       return this;
/* 291 */     } catch (IOException e) {
/* 292 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*     */     try {
/* 299 */       return ((EpollDatagramChannel)this.channel).socket.isReuseAddress();
/* 300 */     } catch (IOException e) {
/* 301 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/* 308 */       ((EpollDatagramChannel)this.channel).socket.setReuseAddress(reuseAddress);
/* 309 */       return this;
/* 310 */     } catch (IOException e) {
/* 311 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBroadcast() {
/*     */     try {
/* 318 */       return ((EpollDatagramChannel)this.channel).socket.isBroadcast();
/* 319 */     } catch (IOException e) {
/* 320 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setBroadcast(boolean broadcast) {
/*     */     try {
/* 327 */       ((EpollDatagramChannel)this.channel).socket.setBroadcast(broadcast);
/* 328 */       return this;
/* 329 */     } catch (IOException e) {
/* 330 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoopbackModeDisabled() {
/*     */     try {
/* 337 */       return ((EpollDatagramChannel)this.channel).socket.isLoopbackModeDisabled();
/* 338 */     } catch (IOException e) {
/* 339 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
/*     */     try {
/* 346 */       ((EpollDatagramChannel)this.channel).socket.setLoopbackModeDisabled(loopbackModeDisabled);
/* 347 */       return this;
/* 348 */     } catch (IOException e) {
/* 349 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTimeToLive() {
/*     */     try {
/* 356 */       return ((EpollDatagramChannel)this.channel).socket.getTimeToLive();
/* 357 */     } catch (IOException e) {
/* 358 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setTimeToLive(int ttl) {
/*     */     try {
/* 365 */       ((EpollDatagramChannel)this.channel).socket.setTimeToLive(ttl);
/* 366 */       return this;
/* 367 */     } catch (IOException e) {
/* 368 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress getInterface() {
/*     */     try {
/* 375 */       return ((EpollDatagramChannel)this.channel).socket.getInterface();
/* 376 */     } catch (IOException e) {
/* 377 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setInterface(InetAddress interfaceAddress) {
/*     */     try {
/* 384 */       ((EpollDatagramChannel)this.channel).socket.setInterface(interfaceAddress);
/* 385 */       return this;
/* 386 */     } catch (IOException e) {
/* 387 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkInterface getNetworkInterface() {
/*     */     try {
/* 394 */       return ((EpollDatagramChannel)this.channel).socket.getNetworkInterface();
/* 395 */     } catch (IOException e) {
/* 396 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
/*     */     try {
/* 403 */       EpollDatagramChannel datagramChannel = (EpollDatagramChannel)this.channel;
/* 404 */       datagramChannel.socket.setNetworkInterface(networkInterface);
/* 405 */       return this;
/* 406 */     } catch (IOException e) {
/* 407 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setEpollMode(EpollMode mode) {
/* 413 */     super.setEpollMode(mode);
/* 414 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReusePort() {
/*     */     try {
/* 422 */       return ((EpollDatagramChannel)this.channel).socket.isReusePort();
/* 423 */     } catch (IOException e) {
/* 424 */       throw new ChannelException(e);
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
/*     */   public EpollDatagramChannelConfig setReusePort(boolean reusePort) {
/*     */     try {
/* 437 */       ((EpollDatagramChannel)this.channel).socket.setReusePort(reusePort);
/* 438 */       return this;
/* 439 */     } catch (IOException e) {
/* 440 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIpTransparent() {
/*     */     try {
/* 450 */       return ((EpollDatagramChannel)this.channel).socket.isIpTransparent();
/* 451 */     } catch (IOException e) {
/* 452 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setIpTransparent(boolean ipTransparent) {
/*     */     try {
/* 462 */       ((EpollDatagramChannel)this.channel).socket.setIpTransparent(ipTransparent);
/* 463 */       return this;
/* 464 */     } catch (IOException e) {
/* 465 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFreeBind() {
/*     */     try {
/* 475 */       return ((EpollDatagramChannel)this.channel).socket.isIpFreeBind();
/* 476 */     } catch (IOException e) {
/* 477 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setFreeBind(boolean freeBind) {
/*     */     try {
/* 487 */       ((EpollDatagramChannel)this.channel).socket.setIpFreeBind(freeBind);
/* 488 */       return this;
/* 489 */     } catch (IOException e) {
/* 490 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIpRecvOrigDestAddr() {
/*     */     try {
/* 500 */       return ((EpollDatagramChannel)this.channel).socket.isIpRecvOrigDestAddr();
/* 501 */     } catch (IOException e) {
/* 502 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setIpRecvOrigDestAddr(boolean ipTransparent) {
/*     */     try {
/* 512 */       ((EpollDatagramChannel)this.channel).socket.setIpRecvOrigDestAddr(ipTransparent);
/* 513 */       return this;
/* 514 */     } catch (IOException e) {
/* 515 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIpMulticastAll() {
/*     */     try {
/* 525 */       return ((EpollDatagramChannel)this.channel).socket.isIpMulticastAll();
/* 526 */     } catch (IOException e) {
/* 527 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setIpMulticastAll(boolean multicastAll) {
/*     */     try {
/* 537 */       ((EpollDatagramChannel)this.channel).socket.setIpMulticastAll(multicastAll);
/* 538 */       return this;
/* 539 */     } catch (IOException e) {
/* 540 */       throw new ChannelException(e);
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
/*     */   
/*     */   public EpollDatagramChannelConfig setMaxDatagramPayloadSize(int maxDatagramSize) {
/* 553 */     this.maxDatagramSize = ObjectUtil.checkPositiveOrZero(maxDatagramSize, "maxDatagramSize");
/* 554 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDatagramPayloadSize() {
/* 561 */     return this.maxDatagramSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setUdpGro(boolean gro) {
/*     */     try {
/* 573 */       ((EpollDatagramChannel)this.channel).socket.setUdpGro(gro);
/* 574 */     } catch (IOException e) {
/* 575 */       throw new ChannelException(e);
/*     */     } 
/* 577 */     this.gro = gro;
/* 578 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUdpGro() {
/* 588 */     return this.gro;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDatagramChannelConfig setMaxMessagesPerWrite(int maxMessagesPerWrite) {
/* 593 */     super.setMaxMessagesPerWrite(maxMessagesPerWrite);
/* 594 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollDatagramChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */