/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.URI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketClientHandshaker07
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker07.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String expectedChallengeResponseString;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean allowExtensions;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean performMasking;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean allowMaskMismatch;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker07(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  71 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true, false);
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
/*     */   public WebSocketClientHandshaker07(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch) {
/* 101 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, 10000L);
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
/*     */   public WebSocketClientHandshaker07(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis) {
/* 134 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, forceCloseTimeoutMillis, false);
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
/*     */ 
/*     */   
/*     */   WebSocketClientHandshaker07(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl) {
/* 171 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, forceCloseTimeoutMillis, absoluteUpgradeUrl, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WebSocketClientHandshaker07(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl, boolean generateOriginHeader) {
/* 211 */     super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, absoluteUpgradeUrl, generateOriginHeader);
/*     */     
/* 213 */     this.allowExtensions = allowExtensions;
/* 214 */     this.performMasking = performMasking;
/* 215 */     this.allowMaskMismatch = allowMaskMismatch;
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
/*     */   protected FullHttpRequest newHandshakeRequest() {
/* 238 */     URI wsURL = uri();
/*     */ 
/*     */     
/* 241 */     byte[] nonce = WebSocketUtil.randomBytes(16);
/* 242 */     String key = WebSocketUtil.base64(nonce);
/*     */     
/* 244 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 245 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 246 */     this.expectedChallengeResponseString = WebSocketUtil.base64(sha1);
/*     */     
/* 248 */     if (logger.isDebugEnabled()) {
/* 249 */       logger.debug("WebSocket version 07 client handshake key: {}, expected response: {}", key, this.expectedChallengeResponseString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, upgradeUrl(wsURL), Unpooled.EMPTY_BUFFER);
/*     */     
/* 257 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/*     */     
/* 259 */     if (this.customHeaders != null) {
/* 260 */       headers.add(this.customHeaders);
/* 261 */       if (!headers.contains((CharSequence)HttpHeaderNames.HOST))
/*     */       {
/*     */ 
/*     */         
/* 265 */         headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */       }
/*     */     } else {
/* 268 */       headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */     } 
/*     */     
/* 271 */     headers.set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 272 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE)
/* 273 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY, key);
/*     */     
/* 275 */     if (this.generateOriginHeader && !headers.contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN)) {
/* 276 */       headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, websocketOriginValue(wsURL));
/*     */     }
/*     */     
/* 279 */     String expectedSubprotocol = expectedSubprotocol();
/* 280 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 281 */       headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, expectedSubprotocol);
/*     */     }
/*     */     
/* 284 */     headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_VERSION, version().toAsciiString());
/* 285 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/*     */   protected void verify(FullHttpResponse response) {
/* 307 */     HttpResponseStatus status = response.status();
/* 308 */     if (!HttpResponseStatus.SWITCHING_PROTOCOLS.equals(status)) {
/* 309 */       throw new WebSocketClientHandshakeException("Invalid handshake response getStatus: " + status, response);
/*     */     }
/*     */     
/* 312 */     HttpHeaders headers = response.headers();
/* 313 */     CharSequence upgrade = headers.get((CharSequence)HttpHeaderNames.UPGRADE);
/* 314 */     if (!HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(upgrade)) {
/* 315 */       throw new WebSocketClientHandshakeException("Invalid handshake response upgrade: " + upgrade, response);
/*     */     }
/*     */     
/* 318 */     if (!headers.containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.UPGRADE, true)) {
/* 319 */       throw new WebSocketClientHandshakeException("Invalid handshake response connection: " + headers
/* 320 */           .get(HttpHeaderNames.CONNECTION), response);
/*     */     }
/*     */     
/* 323 */     CharSequence accept = headers.get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ACCEPT);
/* 324 */     if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
/* 325 */       throw new WebSocketClientHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { accept, this.expectedChallengeResponseString }), response);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 332 */     return new WebSocket07FrameDecoder(false, this.allowExtensions, maxFramePayloadLength(), this.allowMaskMismatch);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 337 */     return new WebSocket07FrameEncoder(this.performMasking);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker07 setForceCloseTimeoutMillis(long forceCloseTimeoutMillis) {
/* 342 */     super.setForceCloseTimeoutMillis(forceCloseTimeoutMillis);
/* 343 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker07.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */