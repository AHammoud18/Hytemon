/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ class WebSocketClientProtocolHandshakeHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private static final long DEFAULT_HANDSHAKE_TIMEOUT_MS = 10000L;
/*     */   private final WebSocketClientHandshaker handshaker;
/*     */   private final long handshakeTimeoutMillis;
/*     */   private ChannelHandlerContext ctx;
/*     */   private ChannelPromise handshakePromise;
/*     */   
/*     */   WebSocketClientProtocolHandshakeHandler(WebSocketClientHandshaker handshaker) {
/*  41 */     this(handshaker, 10000L);
/*     */   }
/*     */   
/*     */   WebSocketClientProtocolHandshakeHandler(WebSocketClientHandshaker handshaker, long handshakeTimeoutMillis) {
/*  45 */     this.handshaker = handshaker;
/*  46 */     this.handshakeTimeoutMillis = ObjectUtil.checkPositive(handshakeTimeoutMillis, "handshakeTimeoutMillis");
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  51 */     this.ctx = ctx;
/*  52 */     this.handshakePromise = ctx.newPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(final ChannelHandlerContext ctx) throws Exception {
/*  57 */     super.channelActive(ctx);
/*  58 */     this.handshaker.handshake(ctx.channel()).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/*  61 */             if (!future.isSuccess()) {
/*  62 */               WebSocketClientProtocolHandshakeHandler.this.handshakePromise.tryFailure(future.cause());
/*  63 */               ctx.fireExceptionCaught(future.cause());
/*     */             } else {
/*  65 */               ctx.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_ISSUED);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  70 */     applyHandshakeTimeout();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/*  75 */     if (!this.handshakePromise.isDone()) {
/*  76 */       this.handshakePromise.tryFailure(new WebSocketClientHandshakeException("channel closed with handshake in progress"));
/*     */     }
/*     */ 
/*     */     
/*  80 */     super.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  85 */     if (!(msg instanceof FullHttpResponse)) {
/*  86 */       ctx.fireChannelRead(msg);
/*     */       
/*     */       return;
/*     */     } 
/*  90 */     FullHttpResponse response = (FullHttpResponse)msg;
/*     */     try {
/*  92 */       if (!this.handshaker.isHandshakeComplete()) {
/*  93 */         this.handshaker.finishHandshake(ctx.channel(), response);
/*  94 */         this.handshakePromise.trySuccess();
/*  95 */         ctx.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE);
/*     */         
/*  97 */         ctx.pipeline().remove((ChannelHandler)this);
/*     */         return;
/*     */       } 
/* 100 */       throw new IllegalStateException("WebSocketClientHandshaker should have been non finished yet");
/*     */     } finally {
/* 102 */       response.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyHandshakeTimeout() {
/* 107 */     final ChannelPromise localHandshakePromise = this.handshakePromise;
/* 108 */     if (this.handshakeTimeoutMillis <= 0L || localHandshakePromise.isDone()) {
/*     */       return;
/*     */     }
/*     */     
/* 112 */     final ScheduledFuture timeoutFuture = this.ctx.executor().schedule(new Runnable()
/*     */         {
/*     */           public void run() {
/* 115 */             if (localHandshakePromise.isDone()) {
/*     */               return;
/*     */             }
/*     */             
/* 119 */             if (localHandshakePromise.tryFailure(new WebSocketClientHandshakeException("handshake timed out"))) {
/* 120 */               WebSocketClientProtocolHandshakeHandler.this.ctx.flush()
/* 121 */                 .fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_TIMEOUT)
/* 122 */                 .close();
/*     */             }
/*     */           }
/*     */         },  this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */ 
/*     */     
/* 128 */     localHandshakePromise.addListener((GenericFutureListener)new FutureListener<Void>()
/*     */         {
/*     */           public void operationComplete(Future<Void> f) throws Exception {
/* 131 */             timeoutFuture.cancel(false);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChannelFuture getHandshakeFuture() {
/* 142 */     return (ChannelFuture)this.handshakePromise;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientProtocolHandshakeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */