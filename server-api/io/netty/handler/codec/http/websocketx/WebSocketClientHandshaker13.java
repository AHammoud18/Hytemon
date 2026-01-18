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
/*     */ public class WebSocketClientHandshaker13
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker13.class);
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
/*     */   public WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  72 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, true, false);
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
/*     */   public WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch) {
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
/*     */   
/*     */   public WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis) {
/* 137 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, forceCloseTimeoutMillis, false);
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
/*     */   WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl) {
/* 174 */     this(webSocketURL, version, subprotocol, allowExtensions, customHeaders, maxFramePayloadLength, performMasking, allowMaskMismatch, forceCloseTimeoutMillis, absoluteUpgradeUrl, true);
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
/*     */   
/*     */   WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength, boolean performMasking, boolean allowMaskMismatch, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl, boolean generateOriginHeader) {
/* 215 */     super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, absoluteUpgradeUrl, generateOriginHeader);
/*     */     
/* 217 */     this.allowExtensions = allowExtensions;
/* 218 */     this.performMasking = performMasking;
/* 219 */     this.allowMaskMismatch = allowMaskMismatch;
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
/*     */   protected FullHttpRequest newHandshakeRequest() {
/* 241 */     URI wsURL = uri();
/*     */ 
/*     */     
/* 244 */     byte[] nonce = WebSocketUtil.randomBytes(16);
/* 245 */     String key = WebSocketUtil.base64(nonce);
/*     */     
/* 247 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 248 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 249 */     this.expectedChallengeResponseString = WebSocketUtil.base64(sha1);
/*     */     
/* 251 */     if (logger.isDebugEnabled()) {
/* 252 */       logger.debug("WebSocket version 13 client handshake key: {}, expected response: {}", key, this.expectedChallengeResponseString);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, upgradeUrl(wsURL), Unpooled.EMPTY_BUFFER);
/*     */     
/* 260 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/*     */     
/* 262 */     if (this.customHeaders != null) {
/* 263 */       headers.add(this.customHeaders);
/* 264 */       if (!headers.contains((CharSequence)HttpHeaderNames.HOST))
/*     */       {
/*     */ 
/*     */         
/* 268 */         headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */       }
/*     */     } else {
/* 271 */       headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */     } 
/*     */     
/* 274 */     headers.set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 275 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE)
/* 276 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY, key);
/*     */     
/* 278 */     if (this.generateOriginHeader && !headers.contains((CharSequence)HttpHeaderNames.ORIGIN)) {
/* 279 */       headers.set((CharSequence)HttpHeaderNames.ORIGIN, websocketOriginValue(wsURL));
/*     */     }
/*     */     
/* 282 */     String expectedSubprotocol = expectedSubprotocol();
/* 283 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 284 */       headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, expectedSubprotocol);
/*     */     }
/*     */     
/* 287 */     headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_VERSION, version().toAsciiString());
/* 288 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/* 310 */     HttpResponseStatus status = response.status();
/* 311 */     if (!HttpResponseStatus.SWITCHING_PROTOCOLS.equals(status)) {
/* 312 */       throw new WebSocketClientHandshakeException("Invalid handshake response getStatus: " + status, response);
/*     */     }
/*     */     
/* 315 */     HttpHeaders headers = response.headers();
/* 316 */     CharSequence upgrade = headers.get((CharSequence)HttpHeaderNames.UPGRADE);
/* 317 */     if (!HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(upgrade)) {
/* 318 */       throw new WebSocketClientHandshakeException("Invalid handshake response upgrade: " + upgrade, response);
/*     */     }
/*     */     
/* 321 */     if (!headers.containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.UPGRADE, true)) {
/* 322 */       throw new WebSocketClientHandshakeException("Invalid handshake response connection: " + headers
/* 323 */           .get(HttpHeaderNames.CONNECTION), response);
/*     */     }
/*     */     
/* 326 */     CharSequence accept = headers.get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ACCEPT);
/* 327 */     if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
/* 328 */       throw new WebSocketClientHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { accept, this.expectedChallengeResponseString }), response);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 335 */     return new WebSocket13FrameDecoder(false, this.allowExtensions, maxFramePayloadLength(), this.allowMaskMismatch);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 340 */     return new WebSocket13FrameEncoder(this.performMasking);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker13 setForceCloseTimeoutMillis(long forceCloseTimeoutMillis) {
/* 345 */     super.setForceCloseTimeoutMillis(forceCloseTimeoutMillis);
/* 346 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isAllowExtensions() {
/* 350 */     return this.allowExtensions;
/*     */   }
/*     */   
/*     */   public boolean isPerformMasking() {
/* 354 */     return this.performMasking;
/*     */   }
/*     */   
/*     */   public boolean isAllowMaskMismatch() {
/* 358 */     return this.allowMaskMismatch;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */