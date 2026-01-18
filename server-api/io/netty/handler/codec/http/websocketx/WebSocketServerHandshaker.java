/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelOutboundInvoker;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.EmptyHttpHeaders;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpContentCompressor;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpObject;
/*     */ import io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpRequestDecoder;
/*     */ import io.netty.handler.codec.http.HttpResponseEncoder;
/*     */ import io.netty.handler.codec.http.HttpServerCodec;
/*     */ import io.netty.handler.codec.http.HttpUtil;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
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
/*     */ public abstract class WebSocketServerHandshaker
/*     */ {
/*  57 */   protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String uri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] subprotocols;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final WebSocketVersion version;
/*     */ 
/*     */ 
/*     */   
/*     */   private final WebSocketDecoderConfig decoderConfig;
/*     */ 
/*     */ 
/*     */   
/*     */   private String selectedSubprotocol;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SUB_PROTOCOL_WILDCARD = "*";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketServerHandshaker(WebSocketVersion version, String uri, String subprotocols, int maxFramePayloadLength) {
/*  90 */     this(version, uri, subprotocols, WebSocketDecoderConfig.newBuilder()
/*  91 */         .maxFramePayloadLength(maxFramePayloadLength)
/*  92 */         .build());
/*     */   }
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
/*     */   protected WebSocketServerHandshaker(WebSocketVersion version, String uri, String subprotocols, WebSocketDecoderConfig decoderConfig) {
/* 110 */     this.version = version;
/* 111 */     this.uri = uri;
/* 112 */     if (subprotocols != null) {
/* 113 */       String[] subprotocolArray = subprotocols.split(",");
/* 114 */       for (int i = 0; i < subprotocolArray.length; i++) {
/* 115 */         subprotocolArray[i] = subprotocolArray[i].trim();
/*     */       }
/* 117 */       this.subprotocols = subprotocolArray;
/*     */     } else {
/* 119 */       this.subprotocols = EmptyArrays.EMPTY_STRINGS;
/*     */     } 
/* 121 */     this.decoderConfig = (WebSocketDecoderConfig)ObjectUtil.checkNotNull(decoderConfig, "decoderConfig");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String uri() {
/* 129 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> subprotocols() {
/* 136 */     Set<String> ret = new LinkedHashSet<>();
/* 137 */     Collections.addAll(ret, this.subprotocols);
/* 138 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketVersion version() {
/* 145 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxFramePayloadLength() {
/* 154 */     return this.decoderConfig.maxFramePayloadLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketDecoderConfig decoderConfig() {
/* 163 */     return this.decoderConfig;
/*     */   }
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
/*     */   public ChannelFuture handshake(Channel channel, FullHttpRequest req) {
/* 178 */     return handshake(channel, req, (HttpHeaders)null, channel.newPromise());
/*     */   }
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
/*     */   public final ChannelFuture handshake(Channel channel, FullHttpRequest req, HttpHeaders responseHeaders, final ChannelPromise promise) {
/*     */     final String encoderName;
/* 200 */     if (logger.isDebugEnabled()) {
/* 201 */       logger.debug("{} WebSocket version {} server handshake", channel, version());
/*     */     }
/* 203 */     FullHttpResponse response = newHandshakeResponse(req, responseHeaders);
/* 204 */     ChannelPipeline p = channel.pipeline();
/* 205 */     if (p.get(HttpObjectAggregator.class) != null) {
/* 206 */       p.remove(HttpObjectAggregator.class);
/*     */     }
/* 208 */     if (p.get(HttpContentCompressor.class) != null) {
/* 209 */       p.remove(HttpContentCompressor.class);
/*     */     }
/* 211 */     ChannelHandlerContext ctx = p.context(HttpRequestDecoder.class);
/*     */     
/* 213 */     if (ctx == null) {
/*     */       
/* 215 */       ctx = p.context(HttpServerCodec.class);
/* 216 */       if (ctx == null) {
/* 217 */         promise.setFailure(new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
/*     */         
/* 219 */         response.release();
/* 220 */         return (ChannelFuture)promise;
/*     */       } 
/* 222 */       p.addBefore(ctx.name(), "wsencoder", (ChannelHandler)newWebSocketEncoder());
/* 223 */       p.addBefore(ctx.name(), "wsdecoder", (ChannelHandler)newWebsocketDecoder());
/* 224 */       encoderName = ctx.name();
/*     */     } else {
/* 226 */       p.replace(ctx.name(), "wsdecoder", (ChannelHandler)newWebsocketDecoder());
/*     */       
/* 228 */       encoderName = p.context(HttpResponseEncoder.class).name();
/* 229 */       p.addBefore(encoderName, "wsencoder", (ChannelHandler)newWebSocketEncoder());
/*     */     } 
/* 231 */     channel.writeAndFlush(response).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 234 */             if (future.isSuccess()) {
/* 235 */               ChannelPipeline p = future.channel().pipeline();
/* 236 */               p.remove(encoderName);
/* 237 */               promise.setSuccess();
/*     */             } else {
/* 239 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/* 243 */     return (ChannelFuture)promise;
/*     */   }
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
/*     */   public ChannelFuture handshake(Channel channel, HttpRequest req) {
/* 258 */     return handshake(channel, req, (HttpHeaders)null, channel.newPromise());
/*     */   }
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
/*     */   public final ChannelFuture handshake(final Channel channel, HttpRequest req, final HttpHeaders responseHeaders, final ChannelPromise promise) {
/* 279 */     if (req instanceof FullHttpRequest) {
/* 280 */       return handshake(channel, (FullHttpRequest)req, responseHeaders, promise);
/*     */     }
/*     */     
/* 283 */     if (logger.isDebugEnabled()) {
/* 284 */       logger.debug("{} WebSocket version {} server handshake", channel, version());
/*     */     }
/*     */     
/* 287 */     ChannelPipeline p = channel.pipeline();
/* 288 */     ChannelHandlerContext ctx = p.context(HttpRequestDecoder.class);
/* 289 */     if (ctx == null) {
/*     */       
/* 291 */       ctx = p.context(HttpServerCodec.class);
/* 292 */       if (ctx == null) {
/* 293 */         promise.setFailure(new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
/*     */         
/* 295 */         return (ChannelFuture)promise;
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     String aggregatorCtx = ctx.name();
/* 300 */     if (HttpUtil.isContentLengthSet((HttpMessage)req) || HttpUtil.isTransferEncodingChunked((HttpMessage)req) || this.version == WebSocketVersion.V00) {
/*     */ 
/*     */ 
/*     */       
/* 304 */       aggregatorCtx = "httpAggregator";
/* 305 */       p.addAfter(ctx.name(), aggregatorCtx, (ChannelHandler)new HttpObjectAggregator(8192));
/*     */     } 
/*     */     
/* 308 */     p.addAfter(aggregatorCtx, "handshaker", (ChannelHandler)new ChannelInboundHandlerAdapter()
/*     */         {
/*     */           private FullHttpRequest fullHttpRequest;
/*     */ 
/*     */           
/*     */           public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 314 */             if (msg instanceof HttpObject) {
/*     */               try {
/* 316 */                 handleHandshakeRequest(ctx, (HttpObject)msg);
/*     */               } finally {
/* 318 */                 ReferenceCountUtil.release(msg);
/*     */               } 
/*     */             } else {
/* 321 */               super.channelRead(ctx, msg);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 328 */             ctx.pipeline().remove((ChannelHandler)this);
/* 329 */             promise.tryFailure(cause);
/* 330 */             ctx.fireExceptionCaught(cause);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/*     */             try {
/* 337 */               if (!promise.isDone()) {
/* 338 */                 promise.tryFailure(new ClosedChannelException());
/*     */               }
/* 340 */               ctx.fireChannelInactive();
/*     */             } finally {
/* 342 */               releaseFullHttpRequest();
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 348 */             releaseFullHttpRequest();
/*     */           }
/*     */           
/*     */           private void handleHandshakeRequest(ChannelHandlerContext ctx, HttpObject httpObject) {
/* 352 */             if (httpObject instanceof FullHttpRequest) {
/* 353 */               ctx.pipeline().remove((ChannelHandler)this);
/* 354 */               WebSocketServerHandshaker.this.handshake(channel, (FullHttpRequest)httpObject, responseHeaders, promise);
/*     */               
/*     */               return;
/*     */             } 
/* 358 */             if (httpObject instanceof io.netty.handler.codec.http.LastHttpContent) {
/* 359 */               assert this.fullHttpRequest != null;
/* 360 */               FullHttpRequest handshakeRequest = this.fullHttpRequest;
/* 361 */               this.fullHttpRequest = null;
/*     */               try {
/* 363 */                 ctx.pipeline().remove((ChannelHandler)this);
/* 364 */                 WebSocketServerHandshaker.this.handshake(channel, handshakeRequest, responseHeaders, promise);
/*     */               } finally {
/* 366 */                 handshakeRequest.release();
/*     */               } 
/*     */               
/*     */               return;
/*     */             } 
/* 371 */             if (httpObject instanceof HttpRequest) {
/* 372 */               HttpRequest httpRequest = (HttpRequest)httpObject;
/* 373 */               this
/* 374 */                 .fullHttpRequest = (FullHttpRequest)new DefaultFullHttpRequest(httpRequest.protocolVersion(), httpRequest.method(), httpRequest.uri(), Unpooled.EMPTY_BUFFER, httpRequest.headers(), (HttpHeaders)EmptyHttpHeaders.INSTANCE);
/* 375 */               if (httpRequest.decoderResult().isFailure()) {
/* 376 */                 this.fullHttpRequest.setDecoderResult(httpRequest.decoderResult());
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/*     */           private void releaseFullHttpRequest() {
/* 382 */             if (this.fullHttpRequest != null) {
/* 383 */               this.fullHttpRequest.release();
/* 384 */               this.fullHttpRequest = null;
/*     */             } 
/*     */           }
/*     */         });
/*     */     try {
/* 389 */       ctx.fireChannelRead(ReferenceCountUtil.retain(req));
/* 390 */     } catch (Throwable cause) {
/* 391 */       promise.setFailure(cause);
/*     */     } 
/* 393 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract FullHttpResponse newHandshakeResponse(FullHttpRequest paramFullHttpRequest, HttpHeaders paramHttpHeaders);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
/* 413 */     ObjectUtil.checkNotNull(channel, "channel");
/* 414 */     return close(channel, frame, channel.newPromise());
/*     */   }
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
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 431 */     return close0((ChannelOutboundInvoker)channel, frame, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
/* 443 */     ObjectUtil.checkNotNull(ctx, "ctx");
/* 444 */     return close(ctx, frame, ctx.newPromise());
/*     */   }
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
/*     */   public ChannelFuture close(ChannelHandlerContext ctx, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 458 */     ObjectUtil.checkNotNull(ctx, "ctx");
/* 459 */     return close0((ChannelOutboundInvoker)ctx, frame, promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */   
/*     */   private ChannelFuture close0(ChannelOutboundInvoker invoker, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 463 */     return invoker.writeAndFlush(frame, promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String selectSubprotocol(String requestedSubprotocols) {
/* 474 */     if (requestedSubprotocols == null || this.subprotocols.length == 0) {
/* 475 */       return null;
/*     */     }
/*     */     
/* 478 */     String[] requestedSubprotocolArray = requestedSubprotocols.split(",");
/* 479 */     for (String p : requestedSubprotocolArray) {
/* 480 */       String requestedSubprotocol = p.trim();
/*     */       
/* 482 */       for (String supportedSubprotocol : this.subprotocols) {
/* 483 */         if ("*".equals(supportedSubprotocol) || requestedSubprotocol
/* 484 */           .equals(supportedSubprotocol)) {
/* 485 */           this.selectedSubprotocol = requestedSubprotocol;
/* 486 */           return requestedSubprotocol;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 492 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String selectedSubprotocol() {
/* 502 */     return this.selectedSubprotocol;
/*     */   }
/*     */   
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */   
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */