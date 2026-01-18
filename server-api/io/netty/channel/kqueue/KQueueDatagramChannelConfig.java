/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.FixedRecvByteBufAllocator;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.WriteBufferWaterMark;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.unix.UnixChannelOption;
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
/*     */ public final class KQueueDatagramChannelConfig
/*     */   extends KQueueChannelConfig
/*     */   implements DatagramChannelConfig
/*     */ {
/*     */   private boolean activeOnOpen;
/*     */   
/*     */   KQueueDatagramChannelConfig(KQueueDatagramChannel channel) {
/*  48 */     super(channel, (RecvByteBufAllocator)new FixedRecvByteBufAllocator(2048));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/*  54 */     return getOptions(super
/*  55 */         .getOptions(), new ChannelOption[] { ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, UnixChannelOption.SO_REUSEPORT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/*  64 */     if (option == ChannelOption.SO_BROADCAST) {
/*  65 */       return (T)Boolean.valueOf(isBroadcast());
/*     */     }
/*  67 */     if (option == ChannelOption.SO_RCVBUF) {
/*  68 */       return (T)Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  70 */     if (option == ChannelOption.SO_SNDBUF) {
/*  71 */       return (T)Integer.valueOf(getSendBufferSize());
/*     */     }
/*  73 */     if (option == ChannelOption.SO_REUSEADDR) {
/*  74 */       return (T)Boolean.valueOf(isReuseAddress());
/*     */     }
/*  76 */     if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/*  77 */       return (T)Boolean.valueOf(isLoopbackModeDisabled());
/*     */     }
/*  79 */     if (option == ChannelOption.IP_MULTICAST_ADDR) {
/*  80 */       return (T)getInterface();
/*     */     }
/*  82 */     if (option == ChannelOption.IP_MULTICAST_IF) {
/*  83 */       return (T)getNetworkInterface();
/*     */     }
/*  85 */     if (option == ChannelOption.IP_MULTICAST_TTL) {
/*  86 */       return (T)Integer.valueOf(getTimeToLive());
/*     */     }
/*  88 */     if (option == ChannelOption.IP_TOS) {
/*  89 */       return (T)Integer.valueOf(getTrafficClass());
/*     */     }
/*  91 */     if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/*  92 */       return (T)Boolean.valueOf(this.activeOnOpen);
/*     */     }
/*  94 */     if (option == UnixChannelOption.SO_REUSEPORT) {
/*  95 */       return (T)Boolean.valueOf(isReusePort());
/*     */     }
/*  97 */     return super.getOption(option);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 103 */     validate(option, value);
/*     */     
/* 105 */     if (option == ChannelOption.SO_BROADCAST) {
/* 106 */       setBroadcast(((Boolean)value).booleanValue());
/* 107 */     } else if (option == ChannelOption.SO_RCVBUF) {
/* 108 */       setReceiveBufferSize(((Integer)value).intValue());
/* 109 */     } else if (option == ChannelOption.SO_SNDBUF) {
/* 110 */       setSendBufferSize(((Integer)value).intValue());
/* 111 */     } else if (option == ChannelOption.SO_REUSEADDR) {
/* 112 */       setReuseAddress(((Boolean)value).booleanValue());
/* 113 */     } else if (option == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/* 114 */       setLoopbackModeDisabled(((Boolean)value).booleanValue());
/* 115 */     } else if (option == ChannelOption.IP_MULTICAST_ADDR) {
/* 116 */       setInterface((InetAddress)value);
/* 117 */     } else if (option == ChannelOption.IP_MULTICAST_IF) {
/* 118 */       setNetworkInterface((NetworkInterface)value);
/* 119 */     } else if (option == ChannelOption.IP_MULTICAST_TTL) {
/* 120 */       setTimeToLive(((Integer)value).intValue());
/* 121 */     } else if (option == ChannelOption.IP_TOS) {
/* 122 */       setTrafficClass(((Integer)value).intValue());
/* 123 */     } else if (option == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 124 */       setActiveOnOpen(((Boolean)value).booleanValue());
/* 125 */     } else if (option == UnixChannelOption.SO_REUSEPORT) {
/* 126 */       setReusePort(((Boolean)value).booleanValue());
/*     */     } else {
/* 128 */       return super.setOption(option, value);
/*     */     } 
/*     */     
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   private void setActiveOnOpen(boolean activeOnOpen) {
/* 135 */     if (this.channel.isRegistered()) {
/* 136 */       throw new IllegalStateException("Can only changed before channel was registered");
/*     */     }
/* 138 */     this.activeOnOpen = activeOnOpen;
/*     */   }
/*     */   
/*     */   boolean getActiveOnOpen() {
/* 142 */     return this.activeOnOpen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReusePort() {
/*     */     try {
/* 150 */       return ((KQueueDatagramChannel)this.channel).socket.isReusePort();
/* 151 */     } catch (IOException e) {
/* 152 */       throw new ChannelException(e);
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
/*     */   public KQueueDatagramChannelConfig setReusePort(boolean reusePort) {
/*     */     try {
/* 165 */       ((KQueueDatagramChannel)this.channel).socket.setReusePort(reusePort);
/* 166 */       return this;
/* 167 */     } catch (IOException e) {
/* 168 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setRcvAllocTransportProvidesGuess(boolean transportProvidesGuess) {
/* 174 */     super.setRcvAllocTransportProvidesGuess(transportProvidesGuess);
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
/* 180 */     super.setMessageSizeEstimator(estimator);
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDatagramChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
/* 187 */     super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDatagramChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
/* 194 */     super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
/* 195 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
/* 200 */     super.setWriteBufferWaterMark(writeBufferWaterMark);
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setAutoClose(boolean autoClose) {
/* 206 */     super.setAutoClose(autoClose);
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setAutoRead(boolean autoRead) {
/* 212 */     super.setAutoRead(autoRead);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
/* 218 */     super.setRecvByteBufAllocator(allocator);
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setWriteSpinCount(int writeSpinCount) {
/* 224 */     super.setWriteSpinCount(writeSpinCount);
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setAllocator(ByteBufAllocator allocator) {
/* 230 */     super.setAllocator(allocator);
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
/* 236 */     super.setConnectTimeoutMillis(connectTimeoutMillis);
/* 237 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueDatagramChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
/* 243 */     super.setMaxMessagesPerRead(maxMessagesPerRead);
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSendBufferSize() {
/*     */     try {
/* 250 */       return ((KQueueDatagramChannel)this.channel).socket.getSendBufferSize();
/* 251 */     } catch (IOException e) {
/* 252 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setSendBufferSize(int sendBufferSize) {
/*     */     try {
/* 259 */       ((KQueueDatagramChannel)this.channel).socket.setSendBufferSize(sendBufferSize);
/* 260 */       return this;
/* 261 */     } catch (IOException e) {
/* 262 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReceiveBufferSize() {
/*     */     try {
/* 269 */       return ((KQueueDatagramChannel)this.channel).socket.getReceiveBufferSize();
/* 270 */     } catch (IOException e) {
/* 271 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setReceiveBufferSize(int receiveBufferSize) {
/*     */     try {
/* 278 */       ((KQueueDatagramChannel)this.channel).socket.setReceiveBufferSize(receiveBufferSize);
/* 279 */       return this;
/* 280 */     } catch (IOException e) {
/* 281 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTrafficClass() {
/*     */     try {
/* 288 */       return ((KQueueDatagramChannel)this.channel).socket.getTrafficClass();
/* 289 */     } catch (IOException e) {
/* 290 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setTrafficClass(int trafficClass) {
/*     */     try {
/* 297 */       ((KQueueDatagramChannel)this.channel).socket.setTrafficClass(trafficClass);
/* 298 */       return this;
/* 299 */     } catch (IOException e) {
/* 300 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReuseAddress() {
/*     */     try {
/* 307 */       return ((KQueueDatagramChannel)this.channel).socket.isReuseAddress();
/* 308 */     } catch (IOException e) {
/* 309 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setReuseAddress(boolean reuseAddress) {
/*     */     try {
/* 316 */       ((KQueueDatagramChannel)this.channel).socket.setReuseAddress(reuseAddress);
/* 317 */       return this;
/* 318 */     } catch (IOException e) {
/* 319 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBroadcast() {
/*     */     try {
/* 326 */       return ((KQueueDatagramChannel)this.channel).socket.isBroadcast();
/* 327 */     } catch (IOException e) {
/* 328 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setBroadcast(boolean broadcast) {
/*     */     try {
/* 335 */       ((KQueueDatagramChannel)this.channel).socket.setBroadcast(broadcast);
/* 336 */       return this;
/* 337 */     } catch (IOException e) {
/* 338 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoopbackModeDisabled() {
/* 344 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
/* 349 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTimeToLive() {
/* 354 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setTimeToLive(int ttl) {
/* 359 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress getInterface() {
/* 364 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setInterface(InetAddress interfaceAddress) {
/* 369 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkInterface getNetworkInterface() {
/* 374 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
/* 379 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDatagramChannelConfig setMaxMessagesPerWrite(int maxMessagesPerWrite) {
/* 384 */     super.setMaxMessagesPerWrite(maxMessagesPerWrite);
/* 385 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueDatagramChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */