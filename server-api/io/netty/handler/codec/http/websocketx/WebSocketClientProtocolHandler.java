/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.URI;
/*     */ import java.util.List;
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
/*     */ public class WebSocketClientProtocolHandler
/*     */   extends WebSocketProtocolHandler
/*     */ {
/*     */   private final WebSocketClientHandshaker handshaker;
/*     */   private final WebSocketClientProtocolConfig clientConfig;
/*     */   
/*     */   public WebSocketClientHandshaker handshaker() {
/*  56 */     return this.handshaker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ClientHandshakeStateEvent
/*     */   {
/*  66 */     HANDSHAKE_TIMEOUT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     HANDSHAKE_ISSUED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     HANDSHAKE_COMPLETE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketClientProtocolHandler(WebSocketClientProtocolConfig clientConfig) {
/*  86 */     super(((WebSocketClientProtocolConfig)ObjectUtil.checkNotNull(clientConfig, "clientConfig")).dropPongFrames(), clientConfig
/*  87 */         .sendCloseFrame(), clientConfig.forceCloseTimeoutMillis());
/*  88 */     this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(clientConfig
/*  89 */         .webSocketUri(), clientConfig
/*  90 */         .version(), clientConfig
/*  91 */         .subprotocol(), clientConfig
/*  92 */         .allowExtensions(), clientConfig
/*  93 */         .customHeaders(), clientConfig
/*  94 */         .maxFramePayloadLength(), clientConfig
/*  95 */         .performMasking(), clientConfig
/*  96 */         .allowMaskMismatch(), clientConfig
/*  97 */         .forceCloseTimeoutMillis(), clientConfig
/*  98 */         .absoluteUpgradeUrl(), clientConfig
/*  99 */         .generateOriginHeader());
/*     */     
/* 101 */     this.clientConfig = clientConfig;
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, WebSocketClientProtocolConfig clientConfig) {
/* 115 */     super(((WebSocketClientProtocolConfig)ObjectUtil.checkNotNull(clientConfig, "clientConfig")).dropPongFrames(), clientConfig
/* 116 */         .sendCloseFrame(), clientConfig.forceCloseTimeoutMillis());
/* 117 */     this.handshaker = handshaker;
/* 118 */     this.clientConfig = clientConfig;
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
/*     */   
/*     */   public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean handleCloseFrames, boolean performMasking, boolean allowMaskMismatch) {
/* 149 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, handleCloseFrames, performMasking, allowMaskMismatch, 10000L);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean handleCloseFrames, boolean performMasking, boolean allowMaskMismatch, long handshakeTimeoutMillis) {
/* 184 */     this(WebSocketClientHandshakerFactory.newHandshaker(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch), handleCloseFrames, handshakeTimeoutMillis);
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
/*     */   public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean handleCloseFrames) {
/* 210 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, handleCloseFrames, 10000L);
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
/*     */   public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean handleCloseFrames, long handshakeTimeoutMillis) {
/* 237 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, handleCloseFrames, true, false, handshakeTimeoutMillis);
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
/*     */   public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
/* 259 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, 10000L);
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
/*     */   public WebSocketClientProtocolHandler(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, long handshakeTimeoutMillis) {
/* 284 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true, handshakeTimeoutMillis);
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, boolean handleCloseFrames) {
/* 298 */     this(handshaker, handleCloseFrames, 10000L);
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, boolean handleCloseFrames, long handshakeTimeoutMillis) {
/* 315 */     this(handshaker, handleCloseFrames, true, handshakeTimeoutMillis);
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, boolean handleCloseFrames, boolean dropPongFrames) {
/* 331 */     this(handshaker, handleCloseFrames, dropPongFrames, 10000L);
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, boolean handleCloseFrames, boolean dropPongFrames, long handshakeTimeoutMillis) {
/* 350 */     this(handshaker, handleCloseFrames, dropPongFrames, handshakeTimeoutMillis, true);
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, boolean handleCloseFrames, boolean dropPongFrames, long handshakeTimeoutMillis, boolean withUTF8Validator) {
/* 372 */     super(dropPongFrames);
/* 373 */     this.handshaker = handshaker;
/* 374 */     this
/*     */ 
/*     */ 
/*     */       
/* 378 */       .clientConfig = WebSocketClientProtocolConfig.newBuilder().handleCloseFrames(handleCloseFrames).handshakeTimeoutMillis(handshakeTimeoutMillis).withUTF8Validator(withUTF8Validator).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker) {
/* 389 */     this(handshaker, 10000L);
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
/*     */   public WebSocketClientProtocolHandler(WebSocketClientHandshaker handshaker, long handshakeTimeoutMillis) {
/* 403 */     this(handshaker, true, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
/* 408 */     if (this.clientConfig.handleCloseFrames() && frame instanceof CloseWebSocketFrame) {
/* 409 */       ctx.close();
/*     */       return;
/*     */     } 
/* 412 */     super.decode(ctx, frame, out);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketClientHandshakeException buildHandshakeException(String message) {
/* 417 */     return new WebSocketClientHandshakeException(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/* 422 */     ChannelPipeline cp = ctx.pipeline();
/* 423 */     if (cp.get(WebSocketClientProtocolHandshakeHandler.class) == null)
/*     */     {
/* 425 */       ctx.pipeline().addBefore(ctx.name(), WebSocketClientProtocolHandshakeHandler.class.getName(), (ChannelHandler)new WebSocketClientProtocolHandshakeHandler(this.handshaker, this.clientConfig
/* 426 */             .handshakeTimeoutMillis()));
/*     */     }
/* 428 */     if (this.clientConfig.withUTF8Validator() && cp.get(Utf8FrameValidator.class) == null)
/*     */     {
/* 430 */       ctx.pipeline().addBefore(ctx.name(), Utf8FrameValidator.class.getName(), (ChannelHandler)new Utf8FrameValidator());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientProtocolHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */