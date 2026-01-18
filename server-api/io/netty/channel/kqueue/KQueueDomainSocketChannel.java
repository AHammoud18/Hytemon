/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.unix.DomainSocketAddress;
/*     */ import io.netty.channel.unix.DomainSocketChannel;
/*     */ import io.netty.channel.unix.DomainSocketChannelConfig;
/*     */ import io.netty.channel.unix.DomainSocketReadMode;
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.channel.unix.PeerCredentials;
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
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
/*     */ public final class KQueueDomainSocketChannel
/*     */   extends AbstractKQueueStreamChannel
/*     */   implements DomainSocketChannel
/*     */ {
/*  34 */   private final KQueueDomainSocketChannelConfig config = new KQueueDomainSocketChannelConfig(this);
/*     */   
/*     */   private volatile DomainSocketAddress local;
/*     */   private volatile DomainSocketAddress remote;
/*     */   
/*     */   public KQueueDomainSocketChannel() {
/*  40 */     super((Channel)null, BsdSocket.newSocketDomain(), false);
/*     */   }
/*     */   
/*     */   public KQueueDomainSocketChannel(int fd) {
/*  44 */     this((Channel)null, new BsdSocket(fd));
/*     */   }
/*     */   
/*     */   KQueueDomainSocketChannel(Channel parent, BsdSocket fd) {
/*  48 */     super(parent, fd, true);
/*  49 */     this.local = fd.localDomainSocketAddress();
/*  50 */     this.remote = fd.remoteDomainSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
/*  55 */     return new KQueueDomainUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress localAddress0() {
/*  60 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress remoteAddress0() {
/*  65 */     return this.remote;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  70 */     this.socket.bind(localAddress);
/*  71 */     this.local = (DomainSocketAddress)localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public KQueueDomainSocketChannelConfig config() {
/*  76 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  81 */     if (super.doConnect(remoteAddress, localAddress)) {
/*  82 */       this.local = (localAddress != null) ? (DomainSocketAddress)localAddress : this.socket.localDomainSocketAddress();
/*  83 */       this.remote = (DomainSocketAddress)remoteAddress;
/*  84 */       return true;
/*     */     } 
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress remoteAddress() {
/*  91 */     return (DomainSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress localAddress() {
/*  96 */     return (DomainSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doWriteSingle(ChannelOutboundBuffer in) throws Exception {
/* 101 */     Object msg = in.current();
/* 102 */     if (msg instanceof FileDescriptor && this.socket.sendFd(((FileDescriptor)msg).intValue()) > 0) {
/*     */       
/* 104 */       in.remove();
/* 105 */       return 1;
/*     */     } 
/* 107 */     return super.doWriteSingle(in);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 112 */     if (msg instanceof FileDescriptor) {
/* 113 */       return msg;
/*     */     }
/* 115 */     return super.filterOutboundMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeerCredentials peerCredentials() throws IOException {
/* 124 */     return this.socket.getPeerCredentials();
/*     */   }
/*     */   
/*     */   private final class KQueueDomainUnsafe
/*     */     extends AbstractKQueueStreamChannel.KQueueStreamUnsafe {
/*     */     void readReady(KQueueRecvByteAllocatorHandle allocHandle) {
/* 130 */       switch (KQueueDomainSocketChannel.this.config().getReadMode()) {
/*     */         case BYTES:
/* 132 */           super.readReady(allocHandle);
/*     */           return;
/*     */         case FILE_DESCRIPTORS:
/* 135 */           readReadyFd();
/*     */           return;
/*     */       } 
/* 138 */       throw new Error("Unexpected read mode: " + KQueueDomainSocketChannel.this.config().getReadMode());
/*     */     }
/*     */     private KQueueDomainUnsafe() {}
/*     */     
/*     */     private void readReadyFd() {
/* 143 */       if (KQueueDomainSocketChannel.this.socket.isInputShutdown()) {
/* 144 */         clearReadFilter0();
/*     */         return;
/*     */       } 
/* 147 */       KQueueDomainSocketChannelConfig kQueueDomainSocketChannelConfig = KQueueDomainSocketChannel.this.config();
/* 148 */       KQueueRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/*     */       
/* 150 */       ChannelPipeline pipeline = KQueueDomainSocketChannel.this.pipeline();
/* 151 */       allocHandle.reset((ChannelConfig)kQueueDomainSocketChannelConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*     */         do {
/* 158 */           int recvFd = KQueueDomainSocketChannel.this.socket.recvFd();
/* 159 */           switch (recvFd) {
/*     */             case 0:
/* 161 */               allocHandle.lastBytesRead(0);
/*     */               break;
/*     */             case -1:
/* 164 */               allocHandle.lastBytesRead(-1);
/* 165 */               close(voidPromise());
/*     */               return;
/*     */           } 
/* 168 */           allocHandle.lastBytesRead(1);
/* 169 */           allocHandle.incMessagesRead(1);
/* 170 */           this.readPending = false;
/* 171 */           pipeline.fireChannelRead(new FileDescriptor(recvFd));
/*     */         
/*     */         }
/* 174 */         while (allocHandle.continueReading());
/*     */         
/* 176 */         allocHandle.readComplete();
/* 177 */         pipeline.fireChannelReadComplete();
/* 178 */       } catch (Throwable t) {
/* 179 */         allocHandle.readComplete();
/* 180 */         pipeline.fireChannelReadComplete();
/* 181 */         pipeline.fireExceptionCaught(t);
/*     */       } finally {
/* 183 */         if (shouldStopReading((ChannelConfig)kQueueDomainSocketChannelConfig))
/* 184 */           clearReadFilter0(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueDomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */