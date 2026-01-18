/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.CharsetUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketServerHandshaker07
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_07_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   
/*     */   public WebSocketServerHandshaker07(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
/*  58 */     this(webSocketURL, subprotocols, allowExtensions, maxFramePayloadLength, false);
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
/*     */   public WebSocketServerHandshaker07(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
/*  81 */     this(webSocketURL, subprotocols, WebSocketDecoderConfig.newBuilder()
/*  82 */         .allowExtensions(allowExtensions)
/*  83 */         .maxFramePayloadLength(maxFramePayloadLength)
/*  84 */         .allowMaskMismatch(allowMaskMismatch)
/*  85 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerHandshaker07(String webSocketURL, String subprotocols, WebSocketDecoderConfig decoderConfig) {
/*  95 */     super(WebSocketVersion.V07, webSocketURL, subprotocols, decoderConfig);
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
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
/* 133 */     HttpMethod method = req.method();
/* 134 */     if (!HttpMethod.GET.equals(method)) {
/* 135 */       throw new WebSocketServerHandshakeException("Invalid WebSocket handshake method: " + method, req);
/*     */     }
/*     */     
/* 138 */     CharSequence key = req.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY);
/* 139 */     if (key == null) {
/* 140 */       throw new WebSocketServerHandshakeException("not a WebSocket request: missing key", req);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 145 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, req.content().alloc().buffer(0));
/*     */     
/* 147 */     if (headers != null) {
/* 148 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 151 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 152 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 153 */     String accept = WebSocketUtil.base64(sha1);
/*     */     
/* 155 */     if (logger.isDebugEnabled()) {
/* 156 */       logger.debug("WebSocket version 07 server handshake key: {}, response: {}.", key, accept);
/*     */     }
/*     */     
/* 159 */     defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 160 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE)
/* 161 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ACCEPT, accept);
/*     */     
/* 163 */     String subprotocols = req.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
/* 164 */     if (subprotocols != null) {
/* 165 */       String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 166 */       if (selectedSubprotocol == null) {
/* 167 */         if (logger.isDebugEnabled()) {
/* 168 */           logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
/*     */         }
/*     */       } else {
/* 171 */         defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, selectedSubprotocol);
/*     */       } 
/*     */     } 
/* 174 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 179 */     return new WebSocket07FrameDecoder(decoderConfig());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 184 */     return new WebSocket07FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker07.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */