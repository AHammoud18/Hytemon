/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.unix.DomainSocketAddress;
/*     */ import io.netty.channel.unix.FileDescriptor;
/*     */ import io.netty.channel.unix.ServerDomainSocketChannel;
/*     */ import io.netty.channel.unix.Socket;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
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
/*     */ 
/*     */ public final class EpollServerDomainSocketChannel
/*     */   extends AbstractEpollServerChannel
/*     */   implements ServerDomainSocketChannel
/*     */ {
/*  32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollServerDomainSocketChannel.class);
/*     */ 
/*     */   
/*  35 */   private final EpollServerChannelConfig config = new EpollServerChannelConfig(this);
/*     */   private volatile DomainSocketAddress local;
/*     */   
/*     */   public EpollServerDomainSocketChannel() {
/*  39 */     super(LinuxSocket.newSocketDomain(), false);
/*     */   }
/*     */   
/*     */   public EpollServerDomainSocketChannel(int fd) {
/*  43 */     super(fd);
/*     */   }
/*     */   
/*     */   public EpollServerDomainSocketChannel(int fd, boolean active) {
/*  47 */     super(new LinuxSocket(fd), active);
/*     */   }
/*     */   
/*     */   EpollServerDomainSocketChannel(LinuxSocket fd) {
/*  51 */     super(fd);
/*     */   }
/*     */   
/*     */   EpollServerDomainSocketChannel(LinuxSocket fd, boolean active) {
/*  55 */     super(fd, active);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Channel newChildChannel(int fd, byte[] addr, int offset, int len) throws Exception {
/*  60 */     return (Channel)new EpollDomainSocketChannel((Channel)this, (FileDescriptor)new Socket(fd));
/*     */   }
/*     */ 
/*     */   
/*     */   protected DomainSocketAddress localAddress0() {
/*  65 */     return this.local;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  70 */     this.socket.bind(localAddress);
/*  71 */     this.socket.listen(this.config.getBacklog());
/*  72 */     this.local = (DomainSocketAddress)localAddress;
/*  73 */     this.active = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*     */     try {
/*  79 */       super.doClose();
/*     */     } finally {
/*  81 */       DomainSocketAddress local = this.local;
/*  82 */       if (local != null) {
/*     */         
/*  84 */         File socketFile = new File(local.path());
/*  85 */         boolean success = socketFile.delete();
/*  86 */         if (!success && logger.isDebugEnabled()) {
/*  87 */           logger.debug("Failed to delete a domain socket file: {}", local.path());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EpollServerChannelConfig config() {
/*  95 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress remoteAddress() {
/* 100 */     return (DomainSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public DomainSocketAddress localAddress() {
/* 105 */     return (DomainSocketAddress)super.localAddress();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollServerDomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */