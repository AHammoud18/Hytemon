/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
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
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketClientHandshaker00
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*     */   private ByteBuf expectedChallengeResponseBytes;
/*     */   
/*     */   public WebSocketClientHandshaker00(URI webSocketURL, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  65 */     this(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength, 10000L);
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
/*     */   public WebSocketClientHandshaker00(URI webSocketURL, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength, long forceCloseTimeoutMillis) {
/*  89 */     this(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, false);
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
/*     */   WebSocketClientHandshaker00(URI webSocketURL, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl) {
/* 115 */     this(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, absoluteUpgradeUrl, true);
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
/*     */   WebSocketClientHandshaker00(URI webSocketURL, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength, long forceCloseTimeoutMillis, boolean absoluteUpgradeUrl, boolean generateOriginHeader) {
/* 146 */     super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength, forceCloseTimeoutMillis, absoluteUpgradeUrl, generateOriginHeader);
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
/*     */   protected FullHttpRequest newHandshakeRequest() {
/* 171 */     int spaces1 = WebSocketUtil.randomNumber(1, 12);
/* 172 */     int spaces2 = WebSocketUtil.randomNumber(1, 12);
/*     */     
/* 174 */     int max1 = Integer.MAX_VALUE / spaces1;
/* 175 */     int max2 = Integer.MAX_VALUE / spaces2;
/*     */     
/* 177 */     int number1 = WebSocketUtil.randomNumber(0, max1);
/* 178 */     int number2 = WebSocketUtil.randomNumber(0, max2);
/*     */     
/* 180 */     int product1 = number1 * spaces1;
/* 181 */     int product2 = number2 * spaces2;
/*     */     
/* 183 */     String key1 = Integer.toString(product1);
/* 184 */     String key2 = Integer.toString(product2);
/*     */     
/* 186 */     key1 = insertRandomCharacters(key1);
/* 187 */     key2 = insertRandomCharacters(key2);
/*     */     
/* 189 */     key1 = insertSpaces(key1, spaces1);
/* 190 */     key2 = insertSpaces(key2, spaces2);
/*     */     
/* 192 */     byte[] key3 = WebSocketUtil.randomBytes(8);
/*     */     
/* 194 */     ByteBuffer buffer = ByteBuffer.allocate(4);
/* 195 */     buffer.putInt(number1);
/* 196 */     byte[] number1Array = buffer.array();
/* 197 */     buffer = ByteBuffer.allocate(4);
/* 198 */     buffer.putInt(number2);
/* 199 */     byte[] number2Array = buffer.array();
/*     */     
/* 201 */     byte[] challenge = new byte[16];
/* 202 */     System.arraycopy(number1Array, 0, challenge, 0, 4);
/* 203 */     System.arraycopy(number2Array, 0, challenge, 4, 4);
/* 204 */     System.arraycopy(key3, 0, challenge, 8, 8);
/* 205 */     this.expectedChallengeResponseBytes = Unpooled.wrappedBuffer(WebSocketUtil.md5(challenge));
/*     */     
/* 207 */     URI wsURL = uri();
/*     */ 
/*     */ 
/*     */     
/* 211 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, upgradeUrl(wsURL), Unpooled.wrappedBuffer(key3));
/* 212 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/*     */     
/* 214 */     if (this.customHeaders != null) {
/* 215 */       headers.add(this.customHeaders);
/* 216 */       if (!headers.contains((CharSequence)HttpHeaderNames.HOST))
/*     */       {
/*     */ 
/*     */         
/* 220 */         headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */       }
/*     */     } else {
/* 223 */       headers.set((CharSequence)HttpHeaderNames.HOST, websocketHostValue(wsURL));
/*     */     } 
/*     */     
/* 226 */     headers.set((CharSequence)HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET)
/* 227 */       .set((CharSequence)HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE)
/* 228 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY1, key1)
/* 229 */       .set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_KEY2, key2);
/*     */     
/* 231 */     if (this.generateOriginHeader && !headers.contains((CharSequence)HttpHeaderNames.ORIGIN)) {
/* 232 */       headers.set((CharSequence)HttpHeaderNames.ORIGIN, websocketOriginValue(wsURL));
/*     */     }
/*     */     
/* 235 */     String expectedSubprotocol = expectedSubprotocol();
/* 236 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 237 */       headers.set((CharSequence)HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL, expectedSubprotocol);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 242 */     headers.set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(key3.length));
/* 243 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/*     */   protected void verify(FullHttpResponse response) {
/* 268 */     HttpResponseStatus status = response.status();
/* 269 */     if (!HttpResponseStatus.SWITCHING_PROTOCOLS.equals(status)) {
/* 270 */       throw new WebSocketClientHandshakeException("Invalid handshake response getStatus: " + status, response);
/*     */     }
/*     */     
/* 273 */     HttpHeaders headers = response.headers();
/* 274 */     CharSequence upgrade = headers.get((CharSequence)HttpHeaderNames.UPGRADE);
/* 275 */     if (!HttpHeaderValues.WEBSOCKET.contentEqualsIgnoreCase(upgrade)) {
/* 276 */       throw new WebSocketClientHandshakeException("Invalid handshake response upgrade: " + upgrade, response);
/*     */     }
/*     */     
/* 279 */     if (!headers.containsValue((CharSequence)HttpHeaderNames.CONNECTION, (CharSequence)HttpHeaderValues.UPGRADE, true)) {
/* 280 */       throw new WebSocketClientHandshakeException("Invalid handshake response connection: " + headers
/* 281 */           .get(HttpHeaderNames.CONNECTION), response);
/*     */     }
/*     */     
/* 284 */     ByteBuf challenge = response.content();
/* 285 */     if (!challenge.equals(this.expectedChallengeResponseBytes)) {
/* 286 */       throw new WebSocketClientHandshakeException("Invalid challenge", response);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String insertRandomCharacters(String key) {
/* 291 */     int count = WebSocketUtil.randomNumber(1, 12);
/*     */     
/* 293 */     char[] randomChars = new char[count];
/* 294 */     int randCount = 0;
/* 295 */     while (randCount < count) {
/* 296 */       int rand = ThreadLocalRandom.current().nextInt(126) + 33;
/* 297 */       if ((33 < rand && rand < 47) || (58 < rand && rand < 126)) {
/* 298 */         randomChars[randCount] = (char)rand;
/* 299 */         randCount++;
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     for (int i = 0; i < count; i++) {
/* 304 */       int split = WebSocketUtil.randomNumber(0, key.length());
/* 305 */       String part1 = key.substring(0, split);
/* 306 */       String part2 = key.substring(split);
/* 307 */       key = part1 + randomChars[i] + part2;
/*     */     } 
/*     */     
/* 310 */     return key;
/*     */   }
/*     */   
/*     */   private static String insertSpaces(String key, int spaces) {
/* 314 */     for (int i = 0; i < spaces; i++) {
/* 315 */       int split = WebSocketUtil.randomNumber(1, key.length() - 1);
/* 316 */       String part1 = key.substring(0, split);
/* 317 */       String part2 = key.substring(split);
/* 318 */       key = part1 + ' ' + part2;
/*     */     } 
/*     */     
/* 321 */     return key;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 326 */     return new WebSocket00FrameDecoder(maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 331 */     return new WebSocket00FrameEncoder();
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker00 setForceCloseTimeoutMillis(long forceCloseTimeoutMillis) {
/* 336 */     super.setForceCloseTimeoutMillis(forceCloseTimeoutMillis);
/* 337 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker00.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */