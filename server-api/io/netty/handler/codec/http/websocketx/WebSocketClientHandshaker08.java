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
/*     */ public class WebSocketClientHandshaker08
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker08.class);
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
/*     */   public WebSocketClientHandshaker08(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  72 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true, false, 10000L);
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
/*     */   public WebSocketClientHandshaker08(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch) {
/* 103 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, 10000L);
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
/*     */   public WebSocketClientHandshaker08(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis) {
/* 136 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, forceCloseTimeoutMillis, false, true);
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
/*     */   WebSocketClientHandshaker08(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl) {
/* 173 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, forceCloseTimeoutMillis, absoluteUpgradeUrl, true);
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
/*     */   WebSocketClientHandshaker08(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl, boolean generateOriginHeader) {
/* 213 */     super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, absoluteUpgradeUrl, generateOriginHeader);
/*     */     
/* 215 */     this.allowExtensions = allowExtensions;
/* 216 */     this.performMasking = performMasking;
/* 217 */     this.allowMaskMismatch = allowMaskMismatch;
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
/* 240 */     URI wsURL = uri();
/*     */ 
/*     */     
/* 243 */     byte[] nonce = WebSocketUtil.randomBytes(16);
/* 244 */     String key = WebSocketUtil.base64(nonce);
/*     */     
/* 246 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 247 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 248 */     this.expectedChallengeResponseString = WebSocketUtil.base64(sha1);
/*     */     
/* 250 */     if (logger.isDebugEnabled()) {
/* 251 */       logger.debug("WebSocket version 08 client handshake key: {}, expected response: {}", key, this.expectedChallengeResponseString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, upgradeUrl(wsURL), Unpooled.EMPTY_BUFFER);
/*     */     
/* 259 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/*     */     
/* 261 */     if (this.customHeaders != null) {
/* 262 */       headers.add(this.customHeaders);
/* 263 */       if (!headers.contains((CharSequence)HttpHeaderNames.HOST))
/*     */       {
/*     */ 
/*     */         
/* 267 */         headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */       }
/*     */     } else {
/* 270 */       headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */     } 
/*     */     
/* 273 */     headers.set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 274 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE)
/* 275 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY, key);
/*     */     
/* 277 */     if (this.generateOriginHeader && !headers.contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN)) {
/* 278 */       headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, websocketOriginValue(wsURL));
/*     */     }
/*     */     
/* 281 */     String expectedSubprotocol = expectedSubprotocol();
/* 282 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 283 */       headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, expectedSubprotocol);
/*     */     }
/*     */     
/* 286 */     headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_VERSION, version().toAsciiString());
/* 287 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/* 309 */     HttpResponseStatus status = response.status();
/* 310 */     if (!HttpResponseStatus.SWITCHING_PROTOCOLS.equals(status)) {
/* 311 */       throw new WebSocketClientHandshakeException("Invalid handshake response getStatus: " + status, response);
/*     */     }
/*     */     
/* 314 */     HttpHeaders headers = response.headers();
/* 315 */     CharSequence upgrade = headers.get((CharSequence)HttpHeaderNames.UPGRADE);
/* 316 */     if (!HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(upgrade)) {
/* 317 */       throw new WebSocketClientHandshakeException("Invalid handshake response upgrade: " + upgrade, response);
/*     */     }
/*     */     
/* 320 */     if (!headers.containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.UPGRADE, true)) {
/* 321 */       throw new WebSocketClientHandshakeException("Invalid handshake response connection: " + headers
/* 322 */           .get(HttpHeaderNames.CONNECTION), response);
/*     */     }
/*     */     
/* 325 */     CharSequence accept = headers.get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ACCEPT);
/* 326 */     if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
/* 327 */       throw new WebSocketClientHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { accept, this.expectedChallengeResponseString }), response);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 334 */     return new WebSocket08FrameDecoder(false, this.allowExtensions, maxFramePayloadLength(), this.allowMaskMismatch);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 339 */     return new WebSocket08FrameEncoder(this.performMasking);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker08 setForceCloseTimeoutMillis(long forceCloseTimeoutMillis) {
/* 344 */     super.setForceCloseTimeoutMillis(forceCloseTimeoutMillis);
/* 345 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker08.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */