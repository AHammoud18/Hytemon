/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
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
/*     */ final class FailedChannel
/*     */   extends AbstractChannel
/*     */ {
/*  29 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  30 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this);
/*     */   
/*     */   FailedChannel() {
/*  33 */     super(null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/*  38 */     return new FailedChannelUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/*  53 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) {
/*  58 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() {
/*  63 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() {
/*  68 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBeginRead() {
/*  73 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) {
/*  78 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/*  83 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  98 */     return METADATA;
/*     */   }
/*     */   private final class FailedChannelUnsafe extends AbstractChannel.AbstractUnsafe { private FailedChannelUnsafe() {
/* 101 */       super(FailedChannel.this);
/*     */     }
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 104 */       promise.setFailure(new UnsupportedOperationException());
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\bootstrap\FailedChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */