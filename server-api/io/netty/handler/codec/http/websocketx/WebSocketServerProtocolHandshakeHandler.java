/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpObject;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.ssl.SslHandler;
/*     */ import io.netty.util.ReferenceCountUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WebSocketServerProtocolHandshakeHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final WebSocketServerProtocolConfig serverConfig;
/*     */   private ChannelHandlerContext ctx;
/*     */   private ChannelPromise handshakePromise;
/*     */   private boolean isWebSocketPath;
/*     */   
/*     */   WebSocketServerProtocolHandshakeHandler(WebSocketServerProtocolConfig serverConfig) {
/*  50 */     this.serverConfig = (WebSocketServerProtocolConfig)ObjectUtil.checkNotNull(serverConfig, "serverConfig");
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/*  55 */     this.ctx = ctx;
/*  56 */     this.handshakePromise = ctx.newPromise();
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
/*  61 */     HttpObject httpObject = (HttpObject)msg;
/*     */     
/*  63 */     if (httpObject instanceof HttpRequest) {
/*  64 */       final HttpRequest req = (HttpRequest)httpObject;
/*  65 */       this.isWebSocketPath = isWebSocketPath(req);
/*  66 */       if (!this.isWebSocketPath) {
/*  67 */         ctx.fireChannelRead(msg);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/*  72 */         final WebSocketServerHandshaker handshaker = WebSocketServerHandshakerFactory.resolveHandshaker(req, 
/*     */             
/*  74 */             getWebSocketLocation(ctx.pipeline(), req, this.serverConfig.websocketPath()), this.serverConfig
/*  75 */             .subprotocols(), this.serverConfig.decoderConfig());
/*  76 */         final ChannelPromise localHandshakePromise = this.handshakePromise;
/*  77 */         if (handshaker == null) {
/*  78 */           WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/*  85 */           WebSocketServerProtocolHandler.setHandshaker(ctx.channel(), handshaker);
/*  86 */           ctx.pipeline().remove((ChannelHandler)this);
/*     */           
/*  88 */           ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
/*  89 */           handshakeFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) {
/*  92 */                   if (!future.isSuccess()) {
/*  93 */                     localHandshakePromise.tryFailure(future.cause());
/*  94 */                     ctx.fireExceptionCaught(future.cause());
/*     */                   } else {
/*  96 */                     localHandshakePromise.trySuccess();
/*     */                     
/*  98 */                     ctx.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
/*     */                     
/* 100 */                     ctx.fireUserEventTriggered(new WebSocketServerProtocolHandler.HandshakeComplete(req
/*     */                           
/* 102 */                           .uri(), req.headers(), handshaker.selectedSubprotocol()));
/*     */                   } 
/*     */                 }
/*     */               });
/* 106 */           applyHandshakeTimeout();
/*     */         } 
/*     */       } finally {
/* 109 */         ReferenceCountUtil.release(req);
/*     */       } 
/* 111 */     } else if (!this.isWebSocketPath) {
/* 112 */       ctx.fireChannelRead(msg);
/*     */     } else {
/* 114 */       ReferenceCountUtil.release(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isWebSocketPath(HttpRequest req) {
/* 119 */     String websocketPath = this.serverConfig.websocketPath();
/* 120 */     String uri = req.uri();
/* 121 */     return this.serverConfig.checkStartsWith() ? (
/* 122 */       (uri.startsWith(websocketPath) && ("/".equals(websocketPath) || checkNextUri(uri, websocketPath)))) : 
/* 123 */       uri.equals(websocketPath);
/*     */   }
/*     */   
/*     */   private boolean checkNextUri(String uri, String websocketPath) {
/* 127 */     int len = websocketPath.length();
/* 128 */     if (uri.length() > len) {
/* 129 */       char nextUri = uri.charAt(len);
/* 130 */       return (nextUri == '/' || nextUri == '?');
/*     */     } 
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
/* 136 */     String protocol = "ws";
/* 137 */     if (cp.get(SslHandler.class) != null)
/*     */     {
/* 139 */       protocol = "wss";
/*     */     }
/* 141 */     String host = req.headers().get((CharSequence)HttpHeaderNames.HOST);
/* 142 */     return protocol + "://" + host + path;
/*     */   }
/*     */   
/*     */   private void applyHandshakeTimeout() {
/* 146 */     final ChannelPromise localHandshakePromise = this.handshakePromise;
/* 147 */     long handshakeTimeoutMillis = this.serverConfig.handshakeTimeoutMillis();
/* 148 */     if (handshakeTimeoutMillis <= 0L || localHandshakePromise.isDone()) {
/*     */       return;
/*     */     }
/*     */     
/* 152 */     final ScheduledFuture timeoutFuture = this.ctx.executor().schedule(new Runnable()
/*     */         {
/*     */           public void run() {
/* 155 */             if (!localHandshakePromise.isDone() && localHandshakePromise
/* 156 */               .tryFailure(new WebSocketServerHandshakeException("handshake timed out"))) {
/* 157 */               WebSocketServerProtocolHandshakeHandler.this.ctx.flush()
/* 158 */                 .fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_TIMEOUT)
/* 159 */                 .close();
/*     */             }
/*     */           }
/*     */         },  handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */ 
/*     */     
/* 165 */     localHandshakePromise.addListener((GenericFutureListener)new FutureListener<Void>()
/*     */         {
/*     */           public void operationComplete(Future<Void> f) {
/* 168 */             timeoutFuture.cancel(false);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandshakeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */