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
/*     */ public class WebSocketServerHandshaker13
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_13_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   
/*     */   public WebSocketServerHandshaker13(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
/*  57 */     this(webSocketURL, subprotocols, allowExtensions, maxFramePayloadLength, false);
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
/*     */   public WebSocketServerHandshaker13(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength, boolean allowMaskMismatch) {
/*  80 */     this(webSocketURL, subprotocols, WebSocketDecoderConfig.newBuilder()
/*  81 */         .allowExtensions(allowExtensions)
/*  82 */         .maxFramePayloadLength(maxFramePayloadLength)
/*  83 */         .allowMaskMismatch(allowMaskMismatch)
/*  84 */         .build());
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
/*     */   public WebSocketServerHandshaker13(String webSocketURL, String subprotocols, WebSocketDecoderConfig decoderConfig) {
/* 100 */     super(WebSocketVersion.V13, webSocketURL, subprotocols, decoderConfig);
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
/* 139 */     HttpMethod method = req.method();
/* 140 */     if (!HttpMethod.GET.equals(method)) {
/* 141 */       throw new WebSocketServerHandshakeException("Invalid WebSocket handshake method: " + method, req);
/*     */     }
/*     */     
/* 144 */     HttpHeaders reqHeaders = req.headers();
/* 145 */     if (!reqHeaders.contains((CharSequence)HttpHeaderNames.CONNECTION) || 
/* 146 */       !reqHeaders.containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.UPGRADE, true)) {
/* 147 */       throw new WebSocketServerHandshakeException("not a WebSocket request: a |Connection| header must includes a token 'Upgrade'", req);
/*     */     }
/*     */ 
/*     */     
/* 151 */     if (!reqHeaders.contains((CharSequence)HttpHeaderNames.UPGRADE, (CharSequence)HttpHeaderValues.WEBSOCKET, true)) {
/* 152 */       throw new WebSocketServerHandshakeException("not a WebSocket request: a |Upgrade| header must containing the value 'websocket'", req);
/*     */     }
/*     */ 
/*     */     
/* 156 */     CharSequence key = reqHeaders.get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY);
/* 157 */     if (key == null) {
/* 158 */       throw new WebSocketServerHandshakeException("not a WebSocket request: missing key", req);
/*     */     }
/*     */ 
/*     */     
/* 162 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS, req.content().alloc().buffer(0));
/* 163 */     if (headers != null) {
/* 164 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 167 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 168 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 169 */     String accept = WebSocketUtil.base64(sha1);
/*     */     
/* 171 */     if (logger.isDebugEnabled()) {
/* 172 */       logger.debug("WebSocket version 13 server handshake key: {}, response: {}", key, accept);
/*     */     }
/*     */     
/* 175 */     defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 176 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE)
/* 177 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ACCEPT, accept);
/*     */     
/* 179 */     String subprotocols = reqHeaders.get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
/* 180 */     if (subprotocols != null) {
/* 181 */       String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 182 */       if (selectedSubprotocol == null) {
/* 183 */         if (logger.isDebugEnabled()) {
/* 184 */           logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
/*     */         }
/*     */       } else {
/* 187 */         defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, selectedSubprotocol);
/*     */       } 
/*     */     } 
/* 190 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 195 */     return new WebSocket13FrameDecoder(decoderConfig());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 200 */     return new WebSocket13FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */