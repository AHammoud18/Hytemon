/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketServerProtocolHandler
/*     */   extends WebSocketProtocolHandler
/*     */ {
/*     */   public enum ServerHandshakeStateEvent
/*     */   {
/*  66 */     HANDSHAKE_COMPLETE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     HANDSHAKE_TIMEOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class HandshakeComplete
/*     */   {
/*     */     private final String requestUri;
/*     */     
/*     */     private final HttpHeaders requestHeaders;
/*     */     private final String selectedSubprotocol;
/*     */     
/*     */     public HandshakeComplete(String requestUri, HttpHeaders requestHeaders, String selectedSubprotocol) {
/*  84 */       this.requestUri = requestUri;
/*  85 */       this.requestHeaders = requestHeaders;
/*  86 */       this.selectedSubprotocol = selectedSubprotocol;
/*     */     }
/*     */     
/*     */     public String requestUri() {
/*  90 */       return this.requestUri;
/*     */     }
/*     */     
/*     */     public HttpHeaders requestHeaders() {
/*  94 */       return this.requestHeaders;
/*     */     }
/*     */     
/*     */     public String selectedSubprotocol() {
/*  98 */       return this.selectedSubprotocol;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 103 */   private static final AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY = AttributeKey.valueOf(WebSocketServerHandshaker.class, "HANDSHAKER");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final WebSocketServerProtocolConfig serverConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(WebSocketServerProtocolConfig serverConfig) {
/* 114 */     super(((WebSocketServerProtocolConfig)ObjectUtil.checkNotNull(serverConfig, "serverConfig")).dropPongFrames(), serverConfig
/* 115 */         .sendCloseFrame(), serverConfig
/* 116 */         .forceCloseTimeoutMillis());
/*     */     
/* 118 */     this.serverConfig = serverConfig;
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath) {
/* 122 */     this(websocketPath, 10000L);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, long handshakeTimeoutMillis) {
/* 126 */     this(websocketPath, false, handshakeTimeoutMillis);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, boolean checkStartsWith) {
/* 130 */     this(websocketPath, checkStartsWith, 10000L);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, boolean checkStartsWith, long handshakeTimeoutMillis) {
/* 134 */     this(websocketPath, (String)null, false, 65536, false, checkStartsWith, handshakeTimeoutMillis);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols) {
/* 138 */     this(websocketPath, subprotocols, 10000L);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, long handshakeTimeoutMillis) {
/* 142 */     this(websocketPath, subprotocols, false, handshakeTimeoutMillis);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
/* 146 */     this(websocketPath, subprotocols, allowExtensions, 10000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, long handshakeTimeoutMillis) {
/* 151 */     this(websocketPath, subprotocols, allowExtensions, 65536, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize) {
/* 156 */     this(websocketPath, subprotocols, allowExtensions, maxFrameSize, 10000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, long handshakeTimeoutMillis) {
/* 161 */     this(websocketPath, subprotocols, allowExtensions, maxFrameSize, false, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch) {
/* 166 */     this(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, 10000L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, long handshakeTimeoutMillis) {
/* 172 */     this(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, false, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith) {
/* 178 */     this(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, checkStartsWith, 10000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith, long handshakeTimeoutMillis) {
/* 185 */     this(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, checkStartsWith, true, handshakeTimeoutMillis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith, boolean dropPongFrames) {
/* 192 */     this(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch, checkStartsWith, dropPongFrames, 10000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch, boolean checkStartsWith, boolean dropPongFrames, long handshakeTimeoutMillis) {
/* 199 */     this(websocketPath, subprotocols, checkStartsWith, dropPongFrames, handshakeTimeoutMillis, 
/* 200 */         WebSocketDecoderConfig.newBuilder()
/* 201 */         .maxFramePayloadLength(maxFrameSize)
/* 202 */         .allowMaskMismatch(allowMaskMismatch)
/* 203 */         .allowExtensions(allowExtensions)
/* 204 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean checkStartsWith, boolean dropPongFrames, long handshakeTimeoutMillis, WebSocketDecoderConfig decoderConfig) {
/* 210 */     this(WebSocketServerProtocolConfig.newBuilder()
/* 211 */         .websocketPath(websocketPath)
/* 212 */         .subprotocols(subprotocols)
/* 213 */         .checkStartsWith(checkStartsWith)
/* 214 */         .handshakeTimeoutMillis(handshakeTimeoutMillis)
/* 215 */         .dropPongFrames(dropPongFrames)
/* 216 */         .decoderConfig(decoderConfig)
/* 217 */         .build());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/* 222 */     ChannelPipeline cp = ctx.pipeline();
/* 223 */     if (cp.get(WebSocketServerProtocolHandshakeHandler.class) == null)
/*     */     {
/* 225 */       cp.addBefore(ctx.name(), WebSocketServerProtocolHandshakeHandler.class.getName(), (ChannelHandler)new WebSocketServerProtocolHandshakeHandler(this.serverConfig));
/*     */     }
/*     */     
/* 228 */     if (this.serverConfig.decoderConfig().withUTF8Validator() && cp.get(Utf8FrameValidator.class) == null)
/*     */     {
/* 230 */       cp.addBefore(ctx.name(), Utf8FrameValidator.class.getName(), (ChannelHandler)new Utf8FrameValidator(this.serverConfig
/* 231 */             .decoderConfig().closeOnProtocolViolation()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
/* 237 */     if (this.serverConfig.handleCloseFrames() && frame instanceof CloseWebSocketFrame) {
/* 238 */       WebSocketServerHandshaker handshaker = getHandshaker(ctx.channel());
/* 239 */       if (handshaker != null) {
/* 240 */         frame.retain();
/* 241 */         ChannelPromise promise = ctx.newPromise();
/* 242 */         closeSent(promise);
/* 243 */         handshaker.close(ctx, (CloseWebSocketFrame)frame, promise);
/*     */       } else {
/* 245 */         ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */       } 
/*     */       return;
/*     */     } 
/* 249 */     super.decode(ctx, frame, out);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketServerHandshakeException buildHandshakeException(String message) {
/* 254 */     return new WebSocketServerHandshakeException(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 259 */     if (cause instanceof WebSocketHandshakeException) {
/*     */       
/* 261 */       DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(cause.getMessage().getBytes()));
/* 262 */       ctx.channel().writeAndFlush(defaultFullHttpResponse).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     } else {
/* 264 */       ctx.fireExceptionCaught(cause);
/* 265 */       ctx.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   static WebSocketServerHandshaker getHandshaker(Channel channel) {
/* 270 */     return (WebSocketServerHandshaker)channel.attr(HANDSHAKER_ATTR_KEY).get();
/*     */   }
/*     */   
/*     */   static void setHandshaker(Channel channel, WebSocketServerHandshaker handshaker) {
/* 274 */     channel.attr(HANDSHAKER_ATTR_KEY).set(handshaker);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */