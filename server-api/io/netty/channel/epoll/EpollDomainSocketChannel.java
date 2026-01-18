/*     */ package io.netty.channel.epoll;
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
/*     */ public final class EpollDomainSocketChannel
/*     */   extends AbstractEpollStreamChannel
/*     */   implements DomainSocketChannel
/*     */ {
/*  33 */   private final EpollDomainSocketChannelConfig config = new EpollDomainSocketChannelConfig(this);
/*     */   
/*     */   private volatile DomainSocketAddress local;
/*     */   private volatile DomainSocketAddress remote;
/*     */   
/*     */   public EpollDomainSocketChannel() {
/*  39 */     super(LinuxSocket.newSocketDomain(), false);
/*     */   }
/*     */   
/*     */   EpollDomainSocketChannel(Channel parent, FileDescriptor fd) {
/*  43 */     this(parent, new LinuxSocket(fd.intValue()));
/*     */   }
/*     */   
/*     */   public EpollDomainSocketChannel(int fd) {
/*  47 */     super(fd);
/*     */   }
/*     */   
/*     */   public EpollDomainSocketChannel(Channel parent, LinuxSocket fd) {
/*  51 */     super(parent, fd);
/*  52 */     this.local = fd.localDomainSocketAddress();
/*  53 */     this.remote = fd.remoteDomainSocketAddress();
/*     */   }
/*     */   
/*     */   public EpollDomainSocketChannel(int fd, boolean active) {
/*  57 */     super(new LinuxSocket(fd), active);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
/*  62 */     return new EpollDomainUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress localAddress0() {
/*  67 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress remoteAddress0() {
/*  72 */     return this.remote;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  77 */     this.socket.bind(localAddress);
/*  78 */     this.local = (DomainSocketAddress)localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollDomainSocketChannelConfig config() {
/*  83 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  88 */     if (super.doConnect(remoteAddress, localAddress)) {
/*  89 */       this.local = (localAddress != null) ? (DomainSocketAddress)localAddress : this.socket.localDomainSocketAddress();
/*  90 */       this.remote = (DomainSocketAddress)remoteAddress;
/*  91 */       return true;
/*     */     } 
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress remoteAddress() {
/*  98 */     return (DomainSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress localAddress() {
/* 103 */     return (DomainSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doWriteSingle(ChannelOutboundBuffer in) throws Exception {
/* 108 */     Object msg = in.current();
/* 109 */     if (msg instanceof FileDescriptor && this.socket.sendFd(((FileDescriptor)msg).intValue()) > 0) {
/*     */       
/* 111 */       in.remove();
/* 112 */       return 1;
/*     */     } 
/* 114 */     return super.doWriteSingle(in);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object filterOutboundMessage(Object msg) {
/* 119 */     if (msg instanceof FileDescriptor) {
/* 120 */       return msg;
/*     */     }
/* 122 */     return super.filterOutboundMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeerCredentials peerCredentials() throws IOException {
/* 130 */     return this.socket.getPeerCredentials();
/*     */   }
/*     */   
/*     */   private final class EpollDomainUnsafe
/*     */     extends AbstractEpollStreamChannel.EpollStreamUnsafe {
/*     */     void epollInReady() {
/* 136 */       switch (EpollDomainSocketChannel.this.config().getReadMode()) {
/*     */         case BYTES:
/* 138 */           super.epollInReady();
/*     */           return;
/*     */         case FILE_DESCRIPTORS:
/* 141 */           epollInReadFd();
/*     */           return;
/*     */       } 
/* 144 */       throw new Error("Unexpected read mode: " + EpollDomainSocketChannel.this.config().getReadMode());
/*     */     }
/*     */     private EpollDomainUnsafe() {}
/*     */     
/*     */     private void epollInReadFd() {
/* 149 */       if (EpollDomainSocketChannel.this.socket.isInputShutdown()) {
/* 150 */         clearEpollIn0();
/*     */         return;
/*     */       } 
/* 153 */       EpollDomainSocketChannelConfig epollDomainSocketChannelConfig = EpollDomainSocketChannel.this.config();
/* 154 */       EpollRecvByteAllocatorHandle allocHandle = recvBufAllocHandle();
/*     */       
/* 156 */       ChannelPipeline pipeline = EpollDomainSocketChannel.this.pipeline();
/* 157 */       allocHandle.reset((ChannelConfig)epollDomainSocketChannelConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*     */         do {
/* 164 */           allocHandle.lastBytesRead(EpollDomainSocketChannel.this.socket.recvFd());
/* 165 */           switch (allocHandle.lastBytesRead()) {
/*     */             case 0:
/*     */               break;
/*     */             case -1:
/* 169 */               close(voidPromise());
/*     */               return;
/*     */           } 
/* 172 */           allocHandle.incMessagesRead(1);
/* 173 */           this.readPending = false;
/* 174 */           pipeline.fireChannelRead(new FileDescriptor(allocHandle.lastBytesRead()));
/*     */         
/*     */         }
/* 177 */         while (allocHandle.continueReading());
/*     */         
/* 179 */         allocHandle.readComplete();
/* 180 */         pipeline.fireChannelReadComplete();
/* 181 */       } catch (Throwable t) {
/* 182 */         allocHandle.readComplete();
/* 183 */         pipeline.fireChannelReadComplete();
/* 184 */         pipeline.fireExceptionCaught(t);
/*     */       } finally {
/* 186 */         if (shouldStopReading((ChannelConfig)epollDomainSocketChannelConfig))
/* 187 */           EpollDomainSocketChannel.this.clearEpollIn(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollDomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */