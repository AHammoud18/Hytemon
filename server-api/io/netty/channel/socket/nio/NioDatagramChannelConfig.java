/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DefaultDatagramChannelConfig;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketOption;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.util.Enumeration;
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
/*     */ class NioDatagramChannelConfig
/*     */   extends DefaultDatagramChannelConfig
/*     */ {
/*     */   private final DatagramChannel javaChannel;
/*     */   
/*     */   NioDatagramChannelConfig(NioDatagramChannel channel, DatagramChannel javaChannel) {
/*  41 */     super(channel, javaChannel.socket());
/*  42 */     this.javaChannel = javaChannel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTimeToLive() {
/*  47 */     return ((Integer)getOption0(StandardSocketOptions.IP_MULTICAST_TTL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setTimeToLive(int ttl) {
/*  52 */     setOption0(StandardSocketOptions.IP_MULTICAST_TTL, Integer.valueOf(ttl));
/*  53 */     return (DatagramChannelConfig)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetAddress getInterface() {
/*  58 */     NetworkInterface inf = getNetworkInterface();
/*  59 */     if (inf != null) {
/*  60 */       Enumeration<InetAddress> addresses = SocketUtils.addressesFromNetworkInterface(inf);
/*  61 */       if (addresses.hasMoreElements()) {
/*  62 */         return addresses.nextElement();
/*     */       }
/*     */     } 
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setInterface(InetAddress interfaceAddress) {
/*     */     try {
/*  71 */       setNetworkInterface(NetworkInterface.getByInetAddress(interfaceAddress));
/*  72 */     } catch (SocketException e) {
/*  73 */       throw new ChannelException(e);
/*     */     } 
/*  75 */     return (DatagramChannelConfig)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkInterface getNetworkInterface() {
/*  80 */     return getOption0(StandardSocketOptions.IP_MULTICAST_IF);
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
/*  85 */     setOption0(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);
/*  86 */     return (DatagramChannelConfig)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoopbackModeDisabled() {
/*  91 */     return ((Boolean)getOption0(StandardSocketOptions.IP_MULTICAST_LOOP)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean loopbackModeDisabled) {
/*  96 */     setOption0(StandardSocketOptions.IP_MULTICAST_LOOP, Boolean.valueOf(loopbackModeDisabled));
/*  97 */     return (DatagramChannelConfig)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig setAutoRead(boolean autoRead) {
/* 102 */     super.setAutoRead(autoRead);
/* 103 */     return (DatagramChannelConfig)this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void autoReadCleared() {
/* 108 */     ((NioDatagramChannel)this.channel).clearReadPending0();
/*     */   }
/*     */   
/*     */   private <T> T getOption0(SocketOption<T> option) {
/*     */     try {
/* 113 */       return this.javaChannel.getOption(option);
/* 114 */     } catch (IOException e) {
/* 115 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> void setOption0(SocketOption<T> option, T value) {
/*     */     try {
/* 121 */       this.javaChannel.setOption(option, value);
/* 122 */     } catch (IOException e) {
/* 123 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 129 */     if (option instanceof NioChannelOption) {
/* 130 */       return NioChannelOption.setOption(this.javaChannel, (NioChannelOption<T>)option, value);
/*     */     }
/* 132 */     return super.setOption(option, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getOption(ChannelOption<T> option) {
/* 137 */     if (option instanceof NioChannelOption) {
/* 138 */       return NioChannelOption.getOption(this.javaChannel, (NioChannelOption<T>)option);
/*     */     }
/* 140 */     return (T)super.getOption(option);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions() {
/* 145 */     return getOptions(super.getOptions(), (ChannelOption[])NioChannelOption.getOptions(this.javaChannel));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioDatagramChannelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */