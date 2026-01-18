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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHttpResponse
/*     */   extends DefaultHttpMessage
/*     */   implements HttpResponse
/*     */ {
/*     */   private HttpResponseStatus status;
/*     */   
/*     */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status) {
/*  37 */     this(version, status, DefaultHttpHeadersFactory.headersFactory());
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
/*     */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, boolean validateHeaders) {
/*  51 */     this(version, status, DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders));
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
/*     */   @Deprecated
/*     */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, boolean validateHeaders, boolean singleFieldHeaders) {
/*  71 */     this(version, status, DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders)
/*  72 */         .withCombiningHeaders(singleFieldHeaders));
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
/*     */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, HttpHeadersFactory headersFactory) {
/*  84 */     this(version, status, headersFactory.newHeaders());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, HttpHeaders headers) {
/*  95 */     super(version, headers);
/*  96 */     this.status = (HttpResponseStatus)ObjectUtil.checkNotNull(status, "status");
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpResponseStatus getStatus() {
/* 102 */     return status();
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpResponseStatus status() {
/* 107 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpResponse setStatus(HttpResponseStatus status) {
/* 112 */     this.status = (HttpResponseStatus)ObjectUtil.checkNotNull(status, "status");
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpResponse setProtocolVersion(HttpVersion version) {
/* 118 */     super.setProtocolVersion(version);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return HttpMessageUtil.appendResponse(new StringBuilder(256), this).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     int result = 1;
/* 130 */     result = 31 * result + this.status.hashCode();
/* 131 */     result = 31 * result + super.hashCode();
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 137 */     if (!(o instanceof DefaultHttpResponse)) {
/* 138 */       return false;
/*     */     }
/*     */     
/* 141 */     DefaultHttpResponse other = (DefaultHttpResponse)o;
/*     */     
/* 143 */     return (this.status.equals(other.status()) && super.equals(o));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultHttpResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */