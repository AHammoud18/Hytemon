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
/*     */ public class WebSocketServerHandshaker08
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_08_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   
/*     */   public WebSocketServerHandshaker08(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
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
/*     */   public WebSocketServerHandshaker08(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerHandshaker08(String webSocketURL, String subprotocols, WebSocketDecoderConfig decoderConfig) {
/* 101 */     super(WebSocketVersion.V08, webSocketURL, subprotocols, decoderConfig);
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
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
/* 140 */     HttpMethod method = req.method();
/* 141 */     if (!HttpMethod.GET.equals(method)) {
/* 142 */       throw new WebSocketServerHandshakeException("Invalid WebSocket handshake method: " + method, req);
/*     */     }
/*     */     
/* 145 */     CharSequence key = req.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY);
/* 146 */     if (key == null) {
/* 147 */       throw new WebSocketServerHandshakeException("not a WebSocket request: missing key", req);
/*     */     }
/*     */ 
/*     */     
/* 151 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, req.content().alloc().buffer(0));
/*     */     
/* 153 */     if (headers != null) {
/* 154 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 157 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 158 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 159 */     String accept = WebSocketUtil.base64(sha1);
/*     */     
/* 161 */     if (logger.isDebugEnabled()) {
/* 162 */       logger.debug("WebSocket version 08 server handshake key: {}, response: {}", key, accept);
/*     */     }
/*     */     
/* 165 */     defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 166 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE)
/* 167 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ACCEPT, accept);
/*     */     
/* 169 */     String subprotocols = req.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
/* 170 */     if (subprotocols != null) {
/* 171 */       String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 172 */       if (selectedSubprotocol == null) {
/* 173 */         if (logger.isDebugEnabled()) {
/* 174 */           logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
/*     */         }
/*     */       } else {
/* 177 */         defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, selectedSubprotocol);
/*     */       } 
/*     */     } 
/* 180 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 185 */     return new WebSocket08FrameDecoder(decoderConfig());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 190 */     return new WebSocket08FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker08.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */