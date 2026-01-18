/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.channel.unix.IovArray;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
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
/*     */ public final class KQueueSocketChannel
/*     */   extends AbstractKQueueStreamChannel
/*     */   implements SocketChannel
/*     */ {
/*     */   private final KQueueSocketChannelConfig config;
/*     */   
/*     */   public KQueueSocketChannel() {
/*  36 */     super((Channel)null, BsdSocket.newSocketStream(), false);
/*  37 */     this.config = new KQueueSocketChannelConfig(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueSocketChannel(InternetProtocolFamily protocol) {
/*  45 */     super((Channel)null, BsdSocket.newSocketStream(protocol), false);
/*  46 */     this.config = new KQueueSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   public KQueueSocketChannel(SocketProtocolFamily protocol) {
/*  50 */     super((Channel)null, BsdSocket.newSocketStream(protocol), false);
/*  51 */     this.config = new KQueueSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   public KQueueSocketChannel(int fd) {
/*  55 */     super(new BsdSocket(fd));
/*  56 */     this.config = new KQueueSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   KQueueSocketChannel(Channel parent, BsdSocket fd, InetSocketAddress remoteAddress) {
/*  60 */     super(parent, fd, remoteAddress);
/*  61 */     this.config = new KQueueSocketChannelConfig(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/*  66 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/*  71 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueSocketChannelConfig config() {
/*  76 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/*  81 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect0(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  87 */     ChannelOutboundBuffer outbound = unsafe().outboundBuffer();
/*  88 */     outbound.addFlush();
/*     */     Object curr;
/*  90 */     if (this.config.isTcpFastOpenConnect() && curr = outbound.current() instanceof ByteBuf) {
/*  91 */       ByteBuf initialData = (ByteBuf)curr;
/*     */       
/*  93 */       if (initialData.isReadable()) {
/*  94 */         IovArray iov = new IovArray(this.config.getAllocator().directBuffer());
/*     */         try {
/*  96 */           iov.add(initialData, initialData.readerIndex(), initialData.readableBytes());
/*  97 */           int bytesSent = this.socket.connectx((InetSocketAddress)localAddress, (InetSocketAddress)remoteAddress, iov, true);
/*     */           
/*  99 */           writeFilter(true);
/* 100 */           outbound.removeBytes(Math.abs(bytesSent));
/*     */ 
/*     */           
/* 103 */           return (bytesSent > 0);
/*     */         } finally {
/* 105 */           iov.release();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return super.doConnect0(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
/* 115 */     return new KQueueSocketChannelUnsafe();
/*     */   }
/*     */   
/*     */   private final class KQueueSocketChannelUnsafe
/*     */     extends AbstractKQueueStreamChannel.KQueueStreamUnsafe {
/*     */     private KQueueSocketChannelUnsafe() {}
/*     */     
/*     */     protected Executor prepareToClose() {
/*     */       try {
/* 124 */         if (KQueueSocketChannel.this.isOpen() && KQueueSocketChannel.this.config().getSoLinger() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 129 */           KQueueSocketChannel.this.doDeregister();
/* 130 */           return (Executor)GlobalEventExecutor.INSTANCE;
/*     */         } 
/* 132 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */