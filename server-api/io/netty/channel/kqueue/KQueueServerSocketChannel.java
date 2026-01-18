/*    */ package io.netty.channel.kqueue;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelConfig;
/*    */ import io.netty.channel.socket.ServerSocketChannel;
/*    */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*    */ import io.netty.channel.unix.NativeInetAddress;
/*    */ import java.net.InetSocketAddress;
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
/*    */ public final class KQueueServerSocketChannel
/*    */   extends AbstractKQueueServerChannel
/*    */   implements ServerSocketChannel
/*    */ {
/*    */   private final KQueueServerSocketChannelConfig config;
/*    */   
/*    */   public KQueueServerSocketChannel() {
/* 31 */     super(BsdSocket.newSocketStream(), false);
/* 32 */     this.config = new KQueueServerSocketChannelConfig(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public KQueueServerSocketChannel(int fd) {
/* 38 */     this(new BsdSocket(fd));
/*    */   }
/*    */   
/*    */   KQueueServerSocketChannel(BsdSocket fd) {
/* 42 */     super(fd);
/* 43 */     this.config = new KQueueServerSocketChannelConfig(this);
/*    */   }
/*    */   
/*    */   KQueueServerSocketChannel(BsdSocket fd, boolean active) {
/* 47 */     super(fd, active);
/* 48 */     this.config = new KQueueServerSocketChannelConfig(this);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 53 */     super.doBind(localAddress);
/* 54 */     this.socket.listen(this.config.getBacklog());
/* 55 */     if (this.config.isTcpFastOpen()) {
/* 56 */       this.socket.setTcpFastOpen(true);
/*    */     }
/* 58 */     this.active = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public InetSocketAddress remoteAddress() {
/* 63 */     return (InetSocketAddress)super.remoteAddress();
/*    */   }
/*    */ 
/*    */   
/*    */   public InetSocketAddress localAddress() {
/* 68 */     return (InetSocketAddress)super.localAddress();
/*    */   }
/*    */ 
/*    */   
/*    */   public KQueueServerSocketChannelConfig config() {
/* 73 */     return this.config;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Channel newChildChannel(int fd, byte[] address, int offset, int len) throws Exception {
/* 78 */     return (Channel)new KQueueSocketChannel((Channel)this, new BsdSocket(fd), NativeInetAddress.address(address, offset, len));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueServerSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */