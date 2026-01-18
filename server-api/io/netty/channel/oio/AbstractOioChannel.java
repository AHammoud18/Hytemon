/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelPromise;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class AbstractOioChannel
/*     */   extends AbstractChannel
/*     */ {
/*     */   protected static final int SO_TIMEOUT = 1000;
/*     */   boolean readPending;
/*     */   boolean readWhenInactive;
/*     */   
/*  38 */   final Runnable readTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  41 */         AbstractOioChannel.this.doRead();
/*     */       }
/*     */     };
/*  44 */   private final Runnable clearReadPendingRunnable = new Runnable()
/*     */     {
/*     */       public void run() {
/*  47 */         AbstractOioChannel.this.readPending = false;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractOioChannel(Channel parent) {
/*  55 */     super(parent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/*  60 */     return new DefaultOioUnsafe();
/*     */   }
/*     */   private final class DefaultOioUnsafe extends AbstractChannel.AbstractUnsafe { private DefaultOioUnsafe() {
/*  63 */       super(AbstractOioChannel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  68 */       if (!promise.setUncancellable() || !ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/*  73 */         boolean wasActive = AbstractOioChannel.this.isActive();
/*  74 */         AbstractOioChannel.this.doConnect(remoteAddress, localAddress);
/*     */ 
/*     */ 
/*     */         
/*  78 */         boolean active = AbstractOioChannel.this.isActive();
/*     */         
/*  80 */         safeSetSuccess(promise);
/*  81 */         if (!wasActive && active) {
/*  82 */           AbstractOioChannel.this.pipeline().fireChannelActive();
/*     */         }
/*  84 */       } catch (Throwable t) {
/*  85 */         safeSetFailure(promise, annotateConnectException(t, remoteAddress));
/*  86 */         closeIfClosed();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  93 */     return loop instanceof io.netty.channel.ThreadPerChannelEventLoop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 104 */     if (this.readPending) {
/*     */       return;
/*     */     }
/* 107 */     if (!isActive()) {
/* 108 */       this.readWhenInactive = true;
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     this.readPending = true;
/* 113 */     eventLoop().execute(this.readTask);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doRead();
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected boolean isReadPending() {
/* 124 */     return this.readPending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setReadPending(final boolean readPending) {
/* 133 */     if (isRegistered()) {
/* 134 */       EventLoop eventLoop = eventLoop();
/* 135 */       if (eventLoop.inEventLoop()) {
/* 136 */         this.readPending = readPending;
/*     */       } else {
/* 138 */         eventLoop.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 141 */                 AbstractOioChannel.this.readPending = readPending;
/*     */               }
/*     */             });
/*     */       } 
/*     */     } else {
/* 146 */       this.readPending = readPending;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void clearReadPending() {
/* 154 */     if (isRegistered()) {
/* 155 */       EventLoop eventLoop = eventLoop();
/* 156 */       if (eventLoop.inEventLoop()) {
/* 157 */         this.readPending = false;
/*     */       } else {
/* 159 */         eventLoop.execute(this.clearReadPendingRunnable);
/*     */       } 
/*     */     } else {
/*     */       
/* 163 */       this.readPending = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\oio\AbstractOioChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */