/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpDecoderConfig
/*     */   implements Cloneable
/*     */ {
/*  28 */   private int maxChunkSize = 8192;
/*     */   private boolean chunkedSupported = true;
/*     */   private boolean allowPartialChunks = true;
/*  31 */   private HttpHeadersFactory headersFactory = DefaultHttpHeadersFactory.headersFactory();
/*  32 */   private HttpHeadersFactory trailersFactory = DefaultHttpHeadersFactory.trailersFactory();
/*     */   private boolean allowDuplicateContentLengths = false;
/*  34 */   private int maxInitialLineLength = 4096;
/*  35 */   private int maxHeaderSize = 8192;
/*  36 */   private int initialBufferSize = 128;
/*  37 */   private boolean strictLineParsing = HttpObjectDecoder.DEFAULT_STRICT_LINE_PARSING;
/*     */   
/*     */   public int getInitialBufferSize() {
/*  40 */     return this.initialBufferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpDecoderConfig setInitialBufferSize(int initialBufferSize) {
/*  50 */     ObjectUtil.checkPositive(initialBufferSize, "initialBufferSize");
/*  51 */     this.initialBufferSize = initialBufferSize;
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   public int getMaxInitialLineLength() {
/*  56 */     return this.maxInitialLineLength;
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
/*     */   public HttpDecoderConfig setMaxInitialLineLength(int maxInitialLineLength) {
/*  68 */     ObjectUtil.checkPositive(maxInitialLineLength, "maxInitialLineLength");
/*  69 */     this.maxInitialLineLength = maxInitialLineLength;
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public int getMaxHeaderSize() {
/*  74 */     return this.maxHeaderSize;
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
/*     */   public HttpDecoderConfig setMaxHeaderSize(int maxHeaderSize) {
/*  89 */     ObjectUtil.checkPositive(maxHeaderSize, "maxHeaderSize");
/*  90 */     this.maxHeaderSize = maxHeaderSize;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public int getMaxChunkSize() {
/*  95 */     return this.maxChunkSize;
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
/*     */   public HttpDecoderConfig setMaxChunkSize(int maxChunkSize) {
/* 108 */     ObjectUtil.checkPositive(maxChunkSize, "maxChunkSize");
/* 109 */     this.maxChunkSize = maxChunkSize;
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isChunkedSupported() {
/* 114 */     return this.chunkedSupported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpDecoderConfig setChunkedSupported(boolean chunkedSupported) {
/* 125 */     this.chunkedSupported = chunkedSupported;
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isAllowPartialChunks() {
/* 130 */     return this.allowPartialChunks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpDecoderConfig setAllowPartialChunks(boolean allowPartialChunks) {
/* 140 */     this.allowPartialChunks = allowPartialChunks;
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   public HttpHeadersFactory getHeadersFactory() {
/* 145 */     return this.headersFactory;
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
/*     */   public HttpDecoderConfig setHeadersFactory(HttpHeadersFactory headersFactory) {
/* 159 */     ObjectUtil.checkNotNull(headersFactory, "headersFactory");
/* 160 */     this.headersFactory = headersFactory;
/* 161 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isAllowDuplicateContentLengths() {
/* 165 */     return this.allowDuplicateContentLengths;
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
/*     */   public HttpDecoderConfig setAllowDuplicateContentLengths(boolean allowDuplicateContentLengths) {
/* 177 */     this.allowDuplicateContentLengths = allowDuplicateContentLengths;
/* 178 */     return this;
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
/*     */   public HttpDecoderConfig setValidateHeaders(boolean validateHeaders) {
/* 193 */     DefaultHttpHeadersFactory noValidation = DefaultHttpHeadersFactory.headersFactory().withValidation(false);
/* 194 */     this.headersFactory = validateHeaders ? DefaultHttpHeadersFactory.headersFactory() : noValidation;
/* 195 */     this.trailersFactory = validateHeaders ? DefaultHttpHeadersFactory.trailersFactory() : noValidation;
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public HttpHeadersFactory getTrailersFactory() {
/* 200 */     return this.trailersFactory;
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
/*     */   public HttpDecoderConfig setTrailersFactory(HttpHeadersFactory trailersFactory) {
/* 216 */     ObjectUtil.checkNotNull(trailersFactory, "trailersFactory");
/* 217 */     this.trailersFactory = trailersFactory;
/* 218 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isStrictLineParsing() {
/* 222 */     return this.strictLineParsing;
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
/*     */   public HttpDecoderConfig setStrictLineParsing(boolean strictLineParsing) {
/* 246 */     this.strictLineParsing = strictLineParsing;
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpDecoderConfig clone() {
/*     */     try {
/* 253 */       return (HttpDecoderConfig)super.clone();
/* 254 */     } catch (CloneNotSupportedException e) {
/* 255 */       throw new AssertionError();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\HttpDecoderConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */