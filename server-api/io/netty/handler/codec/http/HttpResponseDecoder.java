/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpResponseDecoder
/*     */   extends HttpObjectDecoder
/*     */ {
/* 121 */   private static final HttpResponseStatus UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResponseDecoder() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResponseDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/* 146 */     super((new HttpDecoderConfig())
/* 147 */         .setMaxInitialLineLength(maxInitialLineLength)
/* 148 */         .setMaxHeaderSize(maxHeaderSize)
/* 149 */         .setMaxChunkSize(maxChunkSize));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpResponseDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
/* 160 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders);
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
/*     */   public HttpResponseDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize) {
/* 172 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders, initialBufferSize);
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
/*     */   public HttpResponseDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths) {
/* 185 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders, initialBufferSize, allowDuplicateContentLengths);
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
/*     */   public HttpResponseDecoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders, int initialBufferSize, boolean allowDuplicateContentLengths, boolean allowPartialChunks) {
/* 198 */     super(maxInitialLineLength, maxHeaderSize, maxChunkSize, true, validateHeaders, initialBufferSize, allowDuplicateContentLengths, allowPartialChunks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResponseDecoder(HttpDecoderConfig config) {
/* 208 */     super(config);
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpMessage createMessage(String[] initialLine) {
/* 213 */     return new DefaultHttpResponse(
/*     */         
/* 215 */         HttpVersion.valueOf(initialLine[0], true), 
/* 216 */         HttpResponseStatus.valueOf(Integer.parseInt(initialLine[1]), initialLine[2]), this.headersFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpMessage createInvalidMessage() {
/* 221 */     return new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, UNKNOWN_STATUS, Unpooled.buffer(0), this.headersFactory, this.trailersFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDecodingRequest() {
/* 227 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpResponseDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */