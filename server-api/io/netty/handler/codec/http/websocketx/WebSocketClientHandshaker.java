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
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.EmptyHttpHeaders;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpClientCodec;
/*     */ import io.netty.handler.codec.http.HttpContentDecompressor;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpObject;
/*     */ import io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import io.netty.handler.codec.http.HttpRequestEncoder;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseDecoder;
/*     */ import io.netty.handler.codec.http.HttpScheme;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.URI;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ public abstract class WebSocketClientHandshaker
/*     */ {
/*  59 */   private static final String HTTP_SCHEME_PREFIX = HttpScheme.HTTP + "://";
/*  60 */   private static final String HTTPS_SCHEME_PREFIX = HttpScheme.HTTPS + "://";
/*     */   
/*     */   protected static final int DEFAULT_FORCE_CLOSE_TIMEOUT_MILLIS = 10000;
/*     */   
/*     */   private final URI uri;
/*     */   
/*     */   private final WebSocketVersion version;
/*     */   
/*     */   private volatile boolean handshakeComplete;
/*  69 */   private volatile long forceCloseTimeoutMillis = 10000L;
/*     */ 
/*     */   
/*     */   private volatile int forceCloseInit;
/*     */   
/*  74 */   private static final AtomicIntegerFieldUpdater<WebSocketClientHandshaker> FORCE_CLOSE_INIT_UPDATER = AtomicIntegerFieldUpdater.newUpdater(WebSocketClientHandshaker.class, "forceCloseInit");
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean forceCloseComplete;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String expectedSubprotocol;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile String actualSubprotocol;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final HttpHeaders customHeaders;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxFramePayloadLength;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean absoluteUpgradeUrl;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean generateOriginHeader;
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength) {
/* 107 */     this(uri, version, subprotocol, customHeaders, maxFramePayloadLength, 10000L);
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
/*     */ 
/*     */   
/*     */   protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength, long forceCloseTimeoutMillis) {
/* 130 */     this(uri, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl) {
/* 156 */     this(uri, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, absoluteUpgradeUrl, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl, boolean generateOriginHeader) {
/* 186 */     this.uri = uri;
/* 187 */     this.version = version;
/* 188 */     this.expectedSubprotocol = subprotocol;
/* 189 */     this.customHeaders = customHeaders;
/* 190 */     this.maxFramePayloadLength = maxFramePayloadLength;
/* 191 */     this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/* 192 */     this.absoluteUpgradeUrl = absoluteUpgradeUrl;
/* 193 */     this.generateOriginHeader = generateOriginHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI uri() {
/* 200 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketVersion version() {
/* 207 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxFramePayloadLength() {
/* 214 */     return this.maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHandshakeComplete() {
/* 221 */     return this.handshakeComplete;
/*     */   }
/*     */   
/*     */   private void setHandshakeComplete() {
/* 225 */     this.handshakeComplete = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String expectedSubprotocol() {
/* 232 */     return this.expectedSubprotocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String actualSubprotocol() {
/* 240 */     return this.actualSubprotocol;
/*     */   }
/*     */   
/*     */   private void setActualSubprotocol(String actualSubprotocol) {
/* 244 */     this.actualSubprotocol = actualSubprotocol;
/*     */   }
/*     */   
/*     */   public long forceCloseTimeoutMillis() {
/* 248 */     return this.forceCloseTimeoutMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isForceCloseComplete() {
/* 256 */     return this.forceCloseComplete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker setForceCloseTimeoutMillis(long forceCloseTimeoutMillis) {
/* 266 */     this.forceCloseTimeoutMillis = forceCloseTimeoutMillis;
/* 267 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture handshake(Channel channel) {
/* 277 */     ObjectUtil.checkNotNull(channel, "channel");
/* 278 */     return handshake(channel, channel.newPromise());
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
/*     */   public final ChannelFuture handshake(Channel channel, final ChannelPromise promise) {
/* 290 */     ChannelPipeline pipeline = channel.pipeline();
/* 291 */     HttpResponseDecoder decoder = (HttpResponseDecoder)pipeline.get(HttpResponseDecoder.class);
/* 292 */     if (decoder == null) {
/* 293 */       HttpClientCodec codec = (HttpClientCodec)pipeline.get(HttpClientCodec.class);
/* 294 */       if (codec == null) {
/* 295 */         promise.setFailure(new IllegalStateException("ChannelPipeline does not contain an HttpResponseDecoder or HttpClientCodec"));
/*     */         
/* 297 */         return (ChannelFuture)promise;
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     if (this.uri.getHost() == null) {
/* 302 */       if (this.customHeaders == null || !this.customHeaders.contains((CharSequence)HttpHeaderNames.HOST)) {
/* 303 */         promise.setFailure(new IllegalArgumentException("Cannot generate the 'host' header value, webSocketURI should contain host or passed through customHeaders"));
/*     */         
/* 305 */         return (ChannelFuture)promise;
/*     */       } 
/*     */       
/* 308 */       if (this.generateOriginHeader && !this.customHeaders.contains((CharSequence)HttpHeaderNames.ORIGIN)) {
/*     */         String originName;
/* 310 */         if (this.version == WebSocketVersion.V07 || this.version == WebSocketVersion.V08) {
/* 311 */           originName = HttpHeaderNames.SEC_WEBSOCKET_ORIGIN.toString();
/*     */         } else {
/* 313 */           originName = HttpHeaderNames.ORIGIN.toString();
/*     */         } 
/*     */         
/* 316 */         promise.setFailure(new IllegalArgumentException("Cannot generate the '" + originName + "' header value, webSocketURI should contain host or disable generateOriginHeader or pass value through customHeaders"));
/*     */ 
/*     */         
/* 319 */         return (ChannelFuture)promise;
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     FullHttpRequest request = newHandshakeRequest();
/*     */     
/* 325 */     channel.writeAndFlush(request).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) {
/* 328 */             if (future.isSuccess()) {
/* 329 */               ChannelPipeline p = future.channel().pipeline();
/* 330 */               ChannelHandlerContext ctx = p.context(HttpRequestEncoder.class);
/* 331 */               if (ctx == null) {
/* 332 */                 ctx = p.context(HttpClientCodec.class);
/*     */               }
/* 334 */               if (ctx == null) {
/* 335 */                 promise.setFailure(new IllegalStateException("ChannelPipeline does not contain an HttpRequestEncoder or HttpClientCodec"));
/*     */                 
/*     */                 return;
/*     */               } 
/* 339 */               p.addAfter(ctx.name(), "ws-encoder", (ChannelHandler)WebSocketClientHandshaker.this.newWebSocketEncoder());
/*     */               
/* 341 */               promise.setSuccess();
/*     */             } else {
/* 343 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/* 347 */     return (ChannelFuture)promise;
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
/*     */   public final void finishHandshake(Channel channel, FullHttpResponse response) {
/* 364 */     verify(response);
/*     */ 
/*     */ 
/*     */     
/* 368 */     String receivedProtocol = response.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
/* 369 */     receivedProtocol = (receivedProtocol != null) ? receivedProtocol.trim() : null;
/* 370 */     String expectedProtocol = (this.expectedSubprotocol != null) ? this.expectedSubprotocol : "";
/* 371 */     boolean protocolValid = false;
/*     */     
/* 373 */     if (expectedProtocol.isEmpty() && receivedProtocol == null) {
/*     */       
/* 375 */       protocolValid = true;
/* 376 */       setActualSubprotocol(this.expectedSubprotocol);
/* 377 */     } else if (!expectedProtocol.isEmpty() && receivedProtocol != null && !receivedProtocol.isEmpty()) {
/*     */       
/* 379 */       for (String protocol : expectedProtocol.split(",")) {
/* 380 */         if (protocol.trim().equals(receivedProtocol)) {
/* 381 */           protocolValid = true;
/* 382 */           setActualSubprotocol(receivedProtocol);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 388 */     if (!protocolValid) {
/* 389 */       throw new WebSocketClientHandshakeException(String.format("Invalid subprotocol. Actual: %s. Expected one of: %s", new Object[] { receivedProtocol, this.expectedSubprotocol }), response);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 394 */     setHandshakeComplete();
/*     */     
/* 396 */     final ChannelPipeline p = channel.pipeline();
/*     */     
/* 398 */     HttpContentDecompressor decompressor = (HttpContentDecompressor)p.get(HttpContentDecompressor.class);
/* 399 */     if (decompressor != null) {
/* 400 */       p.remove((ChannelHandler)decompressor);
/*     */     }
/*     */ 
/*     */     
/* 404 */     HttpObjectAggregator aggregator = (HttpObjectAggregator)p.get(HttpObjectAggregator.class);
/* 405 */     if (aggregator != null) {
/* 406 */       p.remove((ChannelHandler)aggregator);
/*     */     }
/*     */     
/* 409 */     ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
/* 410 */     if (ctx == null) {
/* 411 */       ctx = p.context(HttpClientCodec.class);
/* 412 */       if (ctx == null) {
/* 413 */         throw new IllegalStateException("ChannelPipeline does not contain an HttpRequestEncoder or HttpClientCodec");
/*     */       }
/*     */       
/* 416 */       final HttpClientCodec codec = (HttpClientCodec)ctx.handler();
/*     */       
/* 418 */       codec.removeOutboundHandler();
/*     */       
/* 420 */       p.addAfter(ctx.name(), "ws-decoder", (ChannelHandler)newWebsocketDecoder());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 425 */       channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 428 */               p.remove((ChannelHandler)codec);
/*     */             }
/*     */           });
/*     */     } else {
/* 432 */       if (p.get(HttpRequestEncoder.class) != null)
/*     */       {
/* 434 */         p.remove(HttpRequestEncoder.class);
/*     */       }
/* 436 */       final ChannelHandlerContext context = ctx;
/* 437 */       p.addAfter(context.name(), "ws-decoder", (ChannelHandler)newWebsocketDecoder());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 442 */       channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 445 */               p.remove(context.handler());
/*     */             }
/*     */           });
/*     */     } 
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
/*     */   public final ChannelFuture processHandshake(Channel channel, HttpResponse response) {
/* 462 */     return processHandshake(channel, response, channel.newPromise());
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
/*     */   public final ChannelFuture processHandshake(final Channel channel, HttpResponse response, final ChannelPromise promise) {
/* 479 */     if (response instanceof FullHttpResponse) {
/*     */       try {
/* 481 */         finishHandshake(channel, (FullHttpResponse)response);
/* 482 */         promise.setSuccess();
/* 483 */       } catch (Throwable cause) {
/* 484 */         promise.setFailure(cause);
/*     */       } 
/*     */     } else {
/* 487 */       ChannelPipeline p = channel.pipeline();
/* 488 */       ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
/* 489 */       if (ctx == null) {
/* 490 */         ctx = p.context(HttpClientCodec.class);
/* 491 */         if (ctx == null) {
/* 492 */           return (ChannelFuture)promise.setFailure(new IllegalStateException("ChannelPipeline does not contain an HttpResponseDecoder or HttpClientCodec"));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 497 */       String aggregatorCtx = ctx.name();
/*     */       
/* 499 */       if (this.version == WebSocketVersion.V00) {
/*     */ 
/*     */         
/* 502 */         aggregatorCtx = "httpAggregator";
/* 503 */         p.addAfter(ctx.name(), aggregatorCtx, (ChannelHandler)new HttpObjectAggregator(8192));
/*     */       } 
/*     */       
/* 506 */       p.addAfter(aggregatorCtx, "handshaker", (ChannelHandler)new ChannelInboundHandlerAdapter()
/*     */           {
/*     */             private FullHttpResponse fullHttpResponse;
/*     */ 
/*     */             
/*     */             public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 512 */               if (msg instanceof HttpObject) {
/*     */                 try {
/* 514 */                   handleHandshakeResponse(ctx, (HttpObject)msg);
/*     */                 } finally {
/* 516 */                   ReferenceCountUtil.release(msg);
/*     */                 } 
/*     */               } else {
/* 519 */                 super.channelRead(ctx, msg);
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 526 */               ctx.pipeline().remove((ChannelHandler)this);
/* 527 */               promise.setFailure(cause);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/*     */               try {
/* 534 */                 if (!promise.isDone()) {
/* 535 */                   promise.tryFailure(new ClosedChannelException());
/*     */                 }
/* 537 */                 ctx.fireChannelInactive();
/*     */               } finally {
/* 539 */                 releaseFullHttpResponse();
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/*     */             public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 545 */               releaseFullHttpResponse();
/*     */             }
/*     */             
/*     */             private void handleHandshakeResponse(ChannelHandlerContext ctx, HttpObject response) {
/* 549 */               if (response instanceof FullHttpResponse) {
/* 550 */                 ctx.pipeline().remove((ChannelHandler)this);
/* 551 */                 tryFinishHandshake((FullHttpResponse)response);
/*     */                 
/*     */                 return;
/*     */               } 
/* 555 */               if (response instanceof io.netty.handler.codec.http.LastHttpContent) {
/* 556 */                 assert this.fullHttpResponse != null;
/* 557 */                 FullHttpResponse handshakeResponse = this.fullHttpResponse;
/* 558 */                 this.fullHttpResponse = null;
/*     */                 try {
/* 560 */                   ctx.pipeline().remove((ChannelHandler)this);
/* 561 */                   tryFinishHandshake(handshakeResponse);
/*     */                 } finally {
/* 563 */                   handshakeResponse.release();
/*     */                 } 
/*     */                 
/*     */                 return;
/*     */               } 
/* 568 */               if (response instanceof HttpResponse) {
/* 569 */                 HttpResponse httpResponse = (HttpResponse)response;
/* 570 */                 this
/* 571 */                   .fullHttpResponse = (FullHttpResponse)new DefaultFullHttpResponse(httpResponse.protocolVersion(), httpResponse.status(), Unpooled.EMPTY_BUFFER, httpResponse.headers(), (HttpHeaders)EmptyHttpHeaders.INSTANCE);
/*     */                 
/* 573 */                 if (httpResponse.decoderResult().isFailure()) {
/* 574 */                   this.fullHttpResponse.setDecoderResult(httpResponse.decoderResult());
/*     */                 }
/*     */               } 
/*     */             }
/*     */             
/*     */             private void tryFinishHandshake(FullHttpResponse fullHttpResponse) {
/*     */               try {
/* 581 */                 WebSocketClientHandshaker.this.finishHandshake(channel, fullHttpResponse);
/* 582 */                 promise.setSuccess();
/* 583 */               } catch (Throwable cause) {
/* 584 */                 promise.setFailure(cause);
/*     */               } 
/*     */             }
/*     */             
/*     */             private void releaseFullHttpResponse() {
/* 589 */               if (this.fullHttpResponse != null) {
/* 590 */                 this.fullHttpResponse.release();
/* 591 */                 this.fullHttpResponse = null;
/*     */               } 
/*     */             }
/*     */           });
/*     */       try {
/* 596 */         ctx.fireChannelRead(ReferenceCountUtil.retain(response));
/* 597 */       } catch (Throwable cause) {
/* 598 */         promise.setFailure(cause);
/*     */       } 
/*     */     } 
/* 601 */     return (ChannelFuture)promise;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
/* 631 */     ObjectUtil.checkNotNull(channel, "channel");
/* 632 */     return close(channel, frame, channel.newPromise());
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
/* 649 */     ObjectUtil.checkNotNull(channel, "channel");
/* 650 */     return close0((ChannelOutboundInvoker)channel, channel, frame, promise);
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
/* 662 */     ObjectUtil.checkNotNull(ctx, "ctx");
/* 663 */     return close(ctx, frame, ctx.newPromise());
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
/* 677 */     ObjectUtil.checkNotNull(ctx, "ctx");
/* 678 */     return close0((ChannelOutboundInvoker)ctx, ctx.channel(), frame, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   private ChannelFuture close0(final ChannelOutboundInvoker invoker, final Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 683 */     invoker.writeAndFlush(frame, promise);
/* 684 */     final long forceCloseTimeoutMillis = this.forceCloseTimeoutMillis;
/* 685 */     final WebSocketClientHandshaker handshaker = this;
/* 686 */     if (forceCloseTimeoutMillis <= 0L || !channel.isActive() || this.forceCloseInit != 0) {
/* 687 */       return (ChannelFuture)promise;
/*     */     }
/*     */     
/* 690 */     promise.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public void operationComplete(ChannelFuture future)
/*     */           {
/* 697 */             if (future.isSuccess() && channel.isActive() && WebSocketClientHandshaker
/* 698 */               .FORCE_CLOSE_INIT_UPDATER.compareAndSet(handshaker, 0, 1)) {
/* 699 */               final ScheduledFuture forceCloseFuture = channel.eventLoop().schedule(new Runnable()
/*     */                   {
/*     */                     public void run() {
/* 702 */                       if (channel.isActive()) {
/* 703 */                         invoker.close();
/* 704 */                         WebSocketClientHandshaker.this.forceCloseComplete = true;
/*     */                       } 
/*     */                     }
/*     */                   },  forceCloseTimeoutMillis, TimeUnit.MILLISECONDS);
/*     */               
/* 709 */               channel.closeFuture().addListener((GenericFutureListener)new ChannelFutureListener()
/*     */                   {
/*     */                     public void operationComplete(ChannelFuture future) throws Exception {
/* 712 */                       forceCloseFuture.cancel(false);
/*     */                     }
/*     */                   });
/*     */             } 
/*     */           }
/*     */         });
/* 718 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String upgradeUrl(URI wsURL) {
/* 725 */     if (this.absoluteUpgradeUrl) {
/* 726 */       return wsURL.toString();
/*     */     }
/*     */     
/* 729 */     String path = wsURL.getRawPath();
/* 730 */     path = (path == null || path.isEmpty()) ? "/" : path;
/* 731 */     String query = wsURL.getRawQuery();
/* 732 */     return (query != null && !query.isEmpty()) ? (path + '?' + query) : path;
/*     */   }
/*     */   
/*     */   static CharSequence websocketHostValue(URI wsURL) {
/* 736 */     int port = wsURL.getPort();
/* 737 */     if (port == -1) {
/* 738 */       return wsURL.getHost();
/*     */     }
/* 740 */     String host = wsURL.getHost();
/* 741 */     String scheme = wsURL.getScheme();
/* 742 */     if (port == HttpScheme.HTTP.port()) {
/* 743 */       return 
/* 744 */         (HttpScheme.HTTP.name().contentEquals(scheme) || WebSocketScheme.WS.name().contentEquals(scheme)) ? 
/* 745 */         host : NetUtil.toSocketAddressString(host, port);
/*     */     }
/* 747 */     if (port == HttpScheme.HTTPS.port()) {
/* 748 */       return 
/* 749 */         (HttpScheme.HTTPS.name().contentEquals(scheme) || WebSocketScheme.WSS.name().contentEquals(scheme)) ? 
/* 750 */         host : NetUtil.toSocketAddressString(host, port);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 755 */     return NetUtil.toSocketAddressString(host, port);
/*     */   } static CharSequence websocketOriginValue(URI wsURL) {
/*     */     String schemePrefix;
/*     */     int defaultPort;
/* 759 */     String scheme = wsURL.getScheme();
/*     */     
/* 761 */     int port = wsURL.getPort();
/*     */     
/* 763 */     if (WebSocketScheme.WSS.name().contentEquals(scheme) || HttpScheme.HTTPS
/* 764 */       .name().contentEquals(scheme) || (scheme == null && port == WebSocketScheme.WSS
/* 765 */       .port())) {
/*     */       
/* 767 */       schemePrefix = HTTPS_SCHEME_PREFIX;
/* 768 */       defaultPort = WebSocketScheme.WSS.port();
/*     */     } else {
/* 770 */       schemePrefix = HTTP_SCHEME_PREFIX;
/* 771 */       defaultPort = WebSocketScheme.WS.port();
/*     */     } 
/*     */ 
/*     */     
/* 775 */     String host = wsURL.getHost().toLowerCase(Locale.US);
/*     */     
/* 777 */     if (port != defaultPort && port != -1)
/*     */     {
/*     */       
/* 780 */       return schemePrefix + NetUtil.toSocketAddressString(host, port);
/*     */     }
/* 782 */     return schemePrefix + host;
/*     */   }
/*     */   
/*     */   protected abstract FullHttpRequest newHandshakeRequest();
/*     */   
/*     */   protected abstract void verify(FullHttpResponse paramFullHttpResponse);
/*     */   
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */   
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */