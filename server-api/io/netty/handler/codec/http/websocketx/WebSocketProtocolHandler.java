/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandler;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.PromiseNotifier;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ abstract class WebSocketProtocolHandler
/*     */   extends MessageToMessageDecoder<WebSocketFrame>
/*     */   implements ChannelOutboundHandler
/*     */ {
/*     */   private final boolean dropPongFrames;
/*     */   private final WebSocketCloseStatus closeStatus;
/*     */   private final long forceCloseTimeoutMillis;
/*     */   private ChannelPromise closeSent;
/*     */   
/*     */   WebSocketProtocolHandler() {
/*  46 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WebSocketProtocolHandler(boolean dropPongFrames) {
/*  57 */     this(dropPongFrames, null, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   WebSocketProtocolHandler(boolean dropPongFrames, WebSocketCloseStatus closeStatus, long forceCloseTimeoutMillis) {
/*  63 */     super(WebSocketFrame.class);
/*  64 */     this.dropPongFrames = dropPongFrames;
/*  65 */     this.closeStatus = closeStatus;
/*  66 */     this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
/*  71 */     if (frame instanceof PingWebSocketFrame) {
/*  72 */       frame.content().retain();
/*  73 */       ctx.writeAndFlush(new PongWebSocketFrame(frame.content()));
/*  74 */       readIfNeeded(ctx);
/*     */       return;
/*     */     } 
/*  77 */     if (frame instanceof PongWebSocketFrame && this.dropPongFrames) {
/*  78 */       readIfNeeded(ctx);
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     out.add(frame.retain());
/*     */   }
/*     */   
/*     */   private static void readIfNeeded(ChannelHandlerContext ctx) {
/*  86 */     if (!ctx.channel().config().isAutoRead()) {
/*  87 */       ctx.read();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
/*  93 */     if (this.closeStatus == null || !ctx.channel().isActive()) {
/*  94 */       ctx.close(promise);
/*     */     } else {
/*  96 */       if (this.closeSent == null) {
/*  97 */         write(ctx, new CloseWebSocketFrame(this.closeStatus), ctx.newPromise());
/*     */       }
/*  99 */       flush(ctx);
/* 100 */       applyCloseSentTimeout(ctx);
/* 101 */       this.closeSent.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) {
/* 104 */               ctx.close(promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 112 */     if (this.closeSent != null) {
/* 113 */       ReferenceCountUtil.release(msg);
/* 114 */       promise.setFailure(new ClosedChannelException());
/* 115 */     } else if (msg instanceof CloseWebSocketFrame) {
/* 116 */       closeSent(promise.unvoid());
/* 117 */       ctx.write(msg).addListener((GenericFutureListener)new PromiseNotifier(false, new Promise[] { (Promise)this.closeSent }));
/*     */     } else {
/* 119 */       ctx.write(msg, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   void closeSent(ChannelPromise promise) {
/* 124 */     this.closeSent = promise;
/*     */   }
/*     */   
/*     */   private void applyCloseSentTimeout(ChannelHandlerContext ctx) {
/* 128 */     if (this.closeSent.isDone() || this.forceCloseTimeoutMillis < 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 132 */     final ScheduledFuture timeoutTask = ctx.executor().schedule(new Runnable()
/*     */         {
/*     */           public void run() {
/* 135 */             if (!WebSocketProtocolHandler.this.closeSent.isDone()) {
/* 136 */               WebSocketProtocolHandler.this.closeSent.tryFailure(WebSocketProtocolHandler.this.buildHandshakeException("send close frame timed out"));
/*     */             }
/*     */           }
/*     */         },  this.forceCloseTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */     
/* 141 */     this.closeSent.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) {
/* 144 */             timeoutTask.cancel(false);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketHandshakeException buildHandshakeException(String message) {
/* 154 */     return new WebSocketHandshakeException(message);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 160 */     ctx.bind(localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 166 */     ctx.connect(remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 172 */     ctx.disconnect(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 177 */     ctx.deregister(promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 182 */     ctx.read();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 187 */     ctx.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 192 */     ctx.fireExceptionCaught(cause);
/* 193 */     ctx.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketProtocolHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */