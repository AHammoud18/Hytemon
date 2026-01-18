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
/*     */ public abstract class DefaultHttpMessage
/*     */   extends DefaultHttpObject
/*     */   implements HttpMessage
/*     */ {
/*     */   private static final int HASH_CODE_PRIME = 31;
/*     */   private HttpVersion version;
/*     */   private final HttpHeaders headers;
/*     */   
/*     */   protected DefaultHttpMessage(HttpVersion version) {
/*  32 */     this(version, DefaultHttpHeadersFactory.headersFactory());
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
/*     */   protected DefaultHttpMessage(HttpVersion version, boolean validateHeaders, boolean singleFieldHeaders) {
/*  44 */     this(version, DefaultHttpHeadersFactory.headersFactory()
/*  45 */         .withValidation(validateHeaders)
/*  46 */         .withCombiningHeaders(singleFieldHeaders));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DefaultHttpMessage(HttpVersion version, HttpHeadersFactory headersFactory) {
/*  53 */     this(version, headersFactory.newHeaders());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DefaultHttpMessage(HttpVersion version, HttpHeaders headers) {
/*  60 */     this.version = (HttpVersion)ObjectUtil.checkNotNull(version, "version");
/*  61 */     this.headers = (HttpHeaders)ObjectUtil.checkNotNull(headers, "headers");
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders headers() {
/*  66 */     return this.headers;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpVersion getProtocolVersion() {
/*  72 */     return protocolVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpVersion protocolVersion() {
/*  77 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  82 */     int result = 1;
/*  83 */     result = 31 * result + this.headers.hashCode();
/*  84 */     result = 31 * result + this.version.hashCode();
/*  85 */     result = 31 * result + super.hashCode();
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  91 */     if (!(o instanceof DefaultHttpMessage)) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     DefaultHttpMessage other = (DefaultHttpMessage)o;
/*     */     
/*  97 */     return (headers().equals(other.headers()) && 
/*  98 */       protocolVersion().equals(other.protocolVersion()) && super
/*  99 */       .equals(o));
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpMessage setProtocolVersion(HttpVersion version) {
/* 104 */     this.version = (HttpVersion)ObjectUtil.checkNotNull(version, "version");
/* 105 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultHttpMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */