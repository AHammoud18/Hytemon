/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.AsciiString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpRequestDecoder
/*     */   extends HttpObjectDecoder
/*     */ {
/*  93 */   private static final AsciiString Accept = AsciiString.cached("Accept");
/*  94 */   private static final AsciiString Host = AsciiString.cached("Host");
/*  95 */   private static final AsciiString Connection = AsciiString.cached("Connection");
/*  96 */   private static final AsciiString ContentType = AsciiString.cached("Content-Type");
/*  97 */   private static final AsciiString ContentLength = AsciiString.cached("Content-Length");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int GET_AS_INT = 5522759;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int POST_AS_INT = 1414745936;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long HTTP_1_1_AS_LONG = 3543824036068086856L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long HTTP_1_0_AS_LONG = 3471766442030158920L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int HOST_AS_INT = 1953722184;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long CONNECTION_AS_LONG_0 = 7598807758576447299L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final short CONNECTION_AS_SHORT_1 = 28271;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long CONTENT_AS_LONG = 3275364211029339971L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TYPE_AS_INT = 1701869908;
/*     */ 
/*     */   
/*     */   private static final long LENGTH_AS_LONG = 114849160783180L;
/*     */ 
/*     */   
/*     */   private static final long ACCEPT_AS_LONG = 128026086171457L;
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequestDecoder() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/* 148 */     this((new HttpDecoderConfig())
/* 149 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 150 */         .setMaxHeaderSize(maxHeaderSize)
/* 151 */         .setMaxChunkSize(maxChunkSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
/* 163 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders);
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
/*     */   @Deprecated
/*     */   public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize) {
/* 176 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders, initialBufferSize);
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
/*     */   @Deprecated
/*     */   public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths) {
/* 190 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders, initialBufferSize, allowDuplicateContentLengths);
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
/*     */   @Deprecated
/*     */   public HttpRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths, boolean allowPartialChunks) {
/* 204 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders, initialBufferSize, allowDuplicateContentLengths, allowPartialChunks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpRequestDecoder(HttpDecoderConfig config) {
/* 214 */     super(config);
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpMessage createMessage(String[] initialLine) throws Exception {
/* 219 */     return new DefaultHttpRequest(
/*     */         
/* 221 */         HttpVersion.valueOf(initialLine[2], true), 
/* 222 */         HttpMethod.valueOf(initialLine[0]), initialLine[1], this.headersFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AsciiString splitHeaderName(byte[] sb, int start, int length) {
/* 227 */     byte firstChar = sb[start];
/* 228 */     if (firstChar == 72) {
/* 229 */       if (length == 4 && isHost(sb, start)) {
/* 230 */         return Host;
/*     */       }
/* 232 */     } else if (firstChar == 65) {
/* 233 */       if (length == 6 && isAccept(sb, start)) {
/* 234 */         return Accept;
/*     */       }
/* 236 */     } else if (firstChar == 67) {
/* 237 */       if (length == 10) {
/* 238 */         if (isConnection(sb, start)) {
/* 239 */           return Connection;
/*     */         }
/* 241 */       } else if (length == 12) {
/* 242 */         if (isContentType(sb, start)) {
/* 243 */           return ContentType;
/*     */         }
/* 245 */       } else if (length == 14 && 
/* 246 */         isContentLength(sb, start)) {
/* 247 */         return ContentLength;
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     return super.splitHeaderName(sb, start, length);
/*     */   }
/*     */   
/*     */   private static boolean isAccept(byte[] sb, int start) {
/* 255 */     long maybeAccept = (sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16 | sb[start + 3] << 24) | sb[start + 4] << 32L | sb[start + 5] << 40L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     return (maybeAccept == 128026086171457L);
/*     */   }
/*     */   
/*     */   private static boolean isHost(byte[] sb, int start) {
/* 265 */     int maybeHost = sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16 | sb[start + 3] << 24;
/*     */ 
/*     */ 
/*     */     
/* 269 */     return (maybeHost == 1953722184);
/*     */   }
/*     */   
/*     */   private static boolean isConnection(byte[] sb, int start) {
/* 273 */     long maybeConnecti = (sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16 | sb[start + 3] << 24) | sb[start + 4] << 32L | sb[start + 5] << 40L | sb[start + 6] << 48L | sb[start + 7] << 56L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     if (maybeConnecti != 7598807758576447299L) {
/* 282 */       return false;
/*     */     }
/* 284 */     short maybeOn = (short)(sb[start + 8] | sb[start + 9] << 8);
/* 285 */     return (maybeOn == 28271);
/*     */   }
/*     */   
/*     */   private static boolean isContentType(byte[] sb, int start) {
/* 289 */     long maybeContent = (sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16 | sb[start + 3] << 24) | sb[start + 4] << 32L | sb[start + 5] << 40L | sb[start + 6] << 48L | sb[start + 7] << 56L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     if (maybeContent != 3275364211029339971L) {
/* 298 */       return false;
/*     */     }
/* 300 */     int maybeType = sb[start + 8] | sb[start + 9] << 8 | sb[start + 10] << 16 | sb[start + 11] << 24;
/*     */ 
/*     */ 
/*     */     
/* 304 */     return (maybeType == 1701869908);
/*     */   }
/*     */   
/*     */   private static boolean isContentLength(byte[] sb, int start) {
/* 308 */     long maybeContent = (sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16 | sb[start + 3] << 24) | sb[start + 4] << 32L | sb[start + 5] << 40L | sb[start + 6] << 48L | sb[start + 7] << 56L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (maybeContent != 3275364211029339971L) {
/* 317 */       return false;
/*     */     }
/* 319 */     long maybeLength = (sb[start + 8] | sb[start + 9] << 8 | sb[start + 10] << 16 | sb[start + 11] << 24) | sb[start + 12] << 32L | sb[start + 13] << 40L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     return (maybeLength == 114849160783180L);
/*     */   }
/*     */   
/*     */   private static boolean isGetMethod(byte[] sb, int start) {
/* 329 */     int maybeGet = sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16;
/*     */ 
/*     */     
/* 332 */     return (maybeGet == 5522759);
/*     */   }
/*     */   
/*     */   private static boolean isPostMethod(byte[] sb, int start) {
/* 336 */     int maybePost = sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16 | sb[start + 3] << 24;
/*     */ 
/*     */ 
/*     */     
/* 340 */     return (maybePost == 1414745936);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String splitFirstWordInitialLine(byte[] sb, int start, int length) {
/* 345 */     if (length == 3) {
/* 346 */       if (isGetMethod(sb, start)) {
/* 347 */         return HttpMethod.GET.name();
/*     */       }
/* 349 */     } else if (length == 4 && 
/* 350 */       isPostMethod(sb, start)) {
/* 351 */       return HttpMethod.POST.name();
/*     */     } 
/*     */     
/* 354 */     return super.splitFirstWordInitialLine(sb, start, length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String splitThirdWordInitialLine(byte[] sb, int start, int length) {
/* 359 */     if (length == 8) {
/* 360 */       long maybeHttp1_x = (sb[start] | sb[start + 1] << 8 | sb[start + 2] << 16 | sb[start + 3] << 24) | sb[start + 4] << 32L | sb[start + 5] << 40L | sb[start + 6] << 48L | sb[start + 7] << 56L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 368 */       if (maybeHttp1_x == 3543824036068086856L)
/* 369 */         return "HTTP/1.1"; 
/* 370 */       if (maybeHttp1_x == 3471766442030158920L) {
/* 371 */         return "HTTP/1.0";
/*     */       }
/*     */     } 
/* 374 */     return super.splitThirdWordInitialLine(sb, start, length);
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpMessage createInvalidMessage() {
/* 379 */     return new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/bad-request", 
/* 380 */         Unpooled.buffer(0), this.headersFactory, this.trailersFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isDecodingRequest() {
/* 385 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 393 */     if (msg.getClass() == DefaultHttpRequest.class) {
/* 394 */       return false;
/*     */     }
/* 396 */     return super.isContentAlwaysEmpty(msg);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpRequestDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */