/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
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
/*     */ public final class EpollSocketChannel
/*     */   extends AbstractEpollStreamChannel
/*     */   implements SocketChannel
/*     */ {
/*     */   private final EpollSocketChannelConfig config;
/*  48 */   private volatile Collection<InetAddress> tcpMd5SigAddresses = Collections.emptyList();
/*     */   
/*     */   public EpollSocketChannel() {
/*  51 */     super(LinuxSocket.newSocketStream(), false);
/*  52 */     this.config = new EpollSocketChannelConfig(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollSocketChannel(InternetProtocolFamily protocol) {
/*  61 */     super(LinuxSocket.newSocketStream(protocol), false);
/*  62 */     this.config = new EpollSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   public EpollSocketChannel(SocketProtocolFamily protocol) {
/*  66 */     super(LinuxSocket.newSocketStream(protocol), false);
/*  67 */     this.config = new EpollSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   public EpollSocketChannel(int fd) {
/*  71 */     super(fd);
/*  72 */     this.config = new EpollSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   EpollSocketChannel(LinuxSocket fd, boolean active) {
/*  76 */     super(fd, active);
/*  77 */     this.config = new EpollSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   EpollSocketChannel(Channel parent, LinuxSocket fd, InetSocketAddress remoteAddress) {
/*  81 */     super(parent, fd, remoteAddress);
/*  82 */     this.config = new EpollSocketChannelConfig(this);
/*     */     
/*  84 */     if (parent instanceof EpollServerSocketChannel) {
/*  85 */       this.tcpMd5SigAddresses = ((EpollServerSocketChannel)parent).tcpMd5SigAddresses();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollTcpInfo tcpInfo() {
/*  94 */     return tcpInfo(new EpollTcpInfo());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollTcpInfo tcpInfo(EpollTcpInfo info) {
/*     */     try {
/* 103 */       this.socket.getTcpInfo(info);
/* 104 */       return info;
/* 105 */     } catch (IOException e) {
/* 106 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 112 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 117 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollSocketChannelConfig config() {
/* 122 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/* 127 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/* 132 */     return new EpollSocketChannelUnsafe();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean doConnect0(SocketAddress remote) throws Exception {
/* 138 */     ChannelOutboundBuffer outbound = unsafe().outboundBuffer();
/* 139 */     outbound.addFlush();
/*     */     Object curr;
/* 141 */     if (Native.IS_SUPPORTING_TCP_FASTOPEN_CLIENT && this.config.isTcpFastOpenConnect() && curr = outbound.current() instanceof ByteBuf) {
/* 142 */       ByteBuf initialData = (ByteBuf)curr;
/*     */ 
/*     */       
/* 145 */       long localFlushedAmount = doWriteOrSendBytes(initialData, (InetSocketAddress)remote, true);
/*     */       
/* 147 */       if (localFlushedAmount > 0L) {
/*     */ 
/*     */         
/* 150 */         outbound.removeBytes(localFlushedAmount);
/* 151 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     return super.doConnect0(remote);
/*     */   }
/*     */   
/*     */   private final class EpollSocketChannelUnsafe
/*     */     extends AbstractEpollStreamChannel.EpollStreamUnsafe {
/*     */     private EpollSocketChannelUnsafe() {}
/*     */     
/*     */     protected Executor prepareToClose() {
/*     */       try {
/* 164 */         if (EpollSocketChannel.this.isOpen() && EpollSocketChannel.this.config().getSoLinger() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 169 */           EpollSocketChannel.this.registration().cancel();
/* 170 */           return (Executor)GlobalEventExecutor.INSTANCE;
/*     */         } 
/* 172 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void setTcpMd5Sig(Map<InetAddress, byte[]> keys) throws IOException {
/* 183 */     synchronized (this) {
/* 184 */       this.tcpMd5SigAddresses = TcpMd5Util.newTcpMd5Sigs(this, this.tcpMd5SigAddresses, keys);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */