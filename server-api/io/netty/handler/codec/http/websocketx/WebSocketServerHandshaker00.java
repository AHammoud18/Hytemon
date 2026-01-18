/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpHeaderValues;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketServerHandshaker00
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*  50 */   private static final Pattern BEGINNING_DIGIT = Pattern.compile("[^0-9]");
/*  51 */   private static final Pattern BEGINNING_SPACE = Pattern.compile("[^ ]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerHandshaker00(String webSocketURL, String subprotocols, int maxFramePayloadLength) {
/*  66 */     this(webSocketURL, subprotocols, WebSocketDecoderConfig.newBuilder()
/*  67 */         .maxFramePayloadLength(maxFramePayloadLength)
/*  68 */         .build());
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
/*     */   public WebSocketServerHandshaker00(String webSocketURL, String subprotocols, WebSocketDecoderConfig decoderConfig) {
/*  83 */     super(WebSocketVersion.V00, webSocketURL, subprotocols, decoderConfig);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
/* 128 */     HttpMethod method = req.method();
/* 129 */     if (!HttpMethod.GET.equals(method)) {
/* 130 */       throw new WebSocketServerHandshakeException("Invalid WebSocket handshake method: " + method, req);
/*     */     }
/*     */ 
/*     */     
/* 134 */     if (!req.headers().containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.UPGRADE, true) || 
/* 135 */       !HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(req.headers().get((CharSequence)HttpHeaderNames.UPGRADE))) {
/* 136 */       throw new WebSocketServerHandshakeException("not a WebSocket handshake request: missing upgrade", req);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 141 */     boolean isHixie76 = (req.headers().contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY1) && req.headers().contains((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY2));
/*     */     
/* 143 */     String origin = req.headers().get((CharSequence)HttpHeaderNames.ORIGIN);
/*     */     
/* 145 */     if (origin == null && !isHixie76) {
/* 146 */       throw new WebSocketServerHandshakeException("Missing origin header, got only " + req.headers().names(), req);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(101, isHixie76 ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"), req.content().alloc().buffer(0));
/* 154 */     if (headers != null) {
/* 155 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 158 */     defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 159 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE);
/*     */ 
/*     */     
/* 162 */     if (isHixie76) {
/*     */       
/* 164 */       defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_ORIGIN, origin);
/* 165 */       defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_LOCATION, uri());
/*     */       
/* 167 */       String subprotocols = req.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
/* 168 */       if (subprotocols != null) {
/* 169 */         String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 170 */         if (selectedSubprotocol == null) {
/* 171 */           if (logger.isDebugEnabled()) {
/* 172 */             logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
/*     */           }
/*     */         } else {
/* 175 */           defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, selectedSubprotocol);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 180 */       String key1 = req.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY1);
/* 181 */       String key2 = req.headers().get((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY2);
/*     */       
/* 183 */       int a = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(key1).replaceAll("")) / BEGINNING_SPACE.matcher(key1).replaceAll("").length());
/*     */       
/* 185 */       int b = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(key2).replaceAll("")) / BEGINNING_SPACE.matcher(key2).replaceAll("").length());
/* 186 */       long c = req.content().readLong();
/* 187 */       ByteBuf input = Unpooled.wrappedBuffer(new byte[16]).setIndex(0, 0);
/* 188 */       input.writeInt(a);
/* 189 */       input.writeInt(b);
/* 190 */       input.writeLong(c);
/* 191 */       defaultFullHttpResponse.content().writeBytes(WebSocketUtil.md5(input.array()));
/*     */     } else {
/*     */       
/* 194 */       defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.WEBSOCKET_ORIGIN, origin);
/* 195 */       defaultFullHttpResponse.headers().add((CharSequence)HttpHeaderNames.WEBSOCKET_LOCATION, uri());
/*     */       
/* 197 */       String protocol = req.headers().get((CharSequence)HttpHeaderNames.WEBSOCKET_PROTOCOL);
/* 198 */       if (protocol != null) {
/* 199 */         defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.WEBSOCKET_PROTOCOL, selectSubprotocol(protocol));
/*     */       }
/*     */     } 
/* 202 */     return (FullHttpResponse)defaultFullHttpResponse;
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
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 217 */     return channel.writeAndFlush(frame, promise);
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
/*     */   public ChannelFuture close(ChannelHandlerContext ctx, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 233 */     return ctx.writeAndFlush(frame, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 238 */     return new WebSocket00FrameDecoder(decoderConfig());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 243 */     return new WebSocket00FrameEncoder();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker00.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */