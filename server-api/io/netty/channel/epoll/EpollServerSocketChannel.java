/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.channel.unix.NativeInetAddress;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ public final class EpollServerSocketChannel
/*     */   extends AbstractEpollServerChannel
/*     */   implements ServerSocketChannel
/*     */ {
/*     */   private final EpollServerSocketChannelConfig config;
/*  42 */   private volatile Collection<InetAddress> tcpMd5SigAddresses = Collections.emptyList();
/*     */   
/*     */   public EpollServerSocketChannel() {
/*  45 */     this((SocketProtocolFamily)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollServerSocketChannel(InternetProtocolFamily protocol) {
/*  53 */     super(LinuxSocket.newSocketStream(protocol), false);
/*  54 */     this.config = new EpollServerSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannel(SocketProtocolFamily protocol) {
/*  58 */     super(LinuxSocket.newSocketStream(protocol), false);
/*  59 */     this.config = new EpollServerSocketChannelConfig(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannel(int fd) {
/*  65 */     this(new LinuxSocket(fd));
/*     */   }
/*     */   
/*     */   EpollServerSocketChannel(LinuxSocket fd) {
/*  69 */     super(fd);
/*  70 */     this.config = new EpollServerSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   EpollServerSocketChannel(LinuxSocket fd, boolean active) {
/*  74 */     super(fd, active);
/*  75 */     this.config = new EpollServerSocketChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  80 */     super.doBind(localAddress);
/*     */     int tcpFastopen;
/*  82 */     if (Native.IS_SUPPORTING_TCP_FASTOPEN_SERVER && (tcpFastopen = this.config.getTcpFastopen()) > 0) {
/*  83 */       this.socket.setTcpFastOpen(tcpFastopen);
/*     */     }
/*  85 */     this.socket.listen(this.config.getBacklog());
/*  86 */     this.active = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/*  91 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/*  96 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerSocketChannelConfig config() {
/* 101 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Channel newChildChannel(int fd, byte[] address, int offset, int len) throws Exception {
/* 106 */     return (Channel)new EpollSocketChannel((Channel)this, new LinuxSocket(fd), NativeInetAddress.address(address, offset, len));
/*     */   }
/*     */   
/*     */   Collection<InetAddress> tcpMd5SigAddresses() {
/* 110 */     return this.tcpMd5SigAddresses;
/*     */   }
/*     */ 
/*     */   
/*     */   void setTcpMd5Sig(Map<InetAddress, byte[]> keys) throws IOException {
/* 115 */     synchronized (this) {
/* 116 */       this.tcpMd5SigAddresses = TcpMd5Util.newTcpMd5Sigs(this, this.tcpMd5SigAddresses, keys);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollServerSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */