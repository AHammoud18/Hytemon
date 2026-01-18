/*    */ package io.netty.channel.kqueue;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelConfig;
/*    */ import io.netty.channel.unix.DomainSocketAddress;
/*    */ import io.netty.channel.unix.ServerDomainSocketChannel;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.io.File;
/*    */ import java.net.SocketAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class KQueueServerDomainSocketChannel
/*    */   extends AbstractKQueueServerChannel
/*    */   implements ServerDomainSocketChannel
/*    */ {
/* 31 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(KQueueServerDomainSocketChannel.class);
/*    */ 
/*    */   
/* 34 */   private final KQueueServerChannelConfig config = new KQueueServerChannelConfig(this);
/*    */   private volatile DomainSocketAddress local;
/*    */   
/*    */   public KQueueServerDomainSocketChannel() {
/* 38 */     super(BsdSocket.newSocketDomain(), false);
/*    */   }
/*    */   
/*    */   public KQueueServerDomainSocketChannel(int fd) {
/* 42 */     this(new BsdSocket(fd), false);
/*    */   }
/*    */   
/*    */   KQueueServerDomainSocketChannel(BsdSocket socket, boolean active) {
/* 46 */     super(socket, active);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Channel newChildChannel(int fd, byte[] addr, int offset, int len) throws Exception {
/* 51 */     return (Channel)new KQueueDomainSocketChannel((Channel)this, new BsdSocket(fd));
/*    */   }
/*    */ 
/*    */   
/*    */   protected DomainSocketAddress localAddress0() {
/* 56 */     return this.local;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 61 */     this.socket.bind(localAddress);
/* 62 */     this.socket.listen(this.config.getBacklog());
/* 63 */     this.local = (DomainSocketAddress)localAddress;
/* 64 */     this.active = true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doClose() throws Exception {
/*    */     try {
/* 70 */       super.doClose();
/*    */     } finally {
/* 72 */       DomainSocketAddress local = this.local;
/* 73 */       if (local != null) {
/*    */         
/* 75 */         File socketFile = new File(local.path());
/* 76 */         boolean success = socketFile.delete();
/* 77 */         if (!success && logger.isDebugEnabled()) {
/* 78 */           logger.debug("Failed to delete a domain socket file: {}", local.path());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public KQueueServerChannelConfig config() {
/* 86 */     return this.config;
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainSocketAddress remoteAddress() {
/* 91 */     return (DomainSocketAddress)super.remoteAddress();
/*    */   }
/*    */ 
/*    */   
/*    */   public DomainSocketAddress localAddress() {
/* 96 */     return (DomainSocketAddress)super.localAddress();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueServerDomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */