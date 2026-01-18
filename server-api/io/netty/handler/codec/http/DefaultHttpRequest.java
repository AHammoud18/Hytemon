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
/*     */ public class DefaultHttpRequest
/*     */   extends DefaultHttpMessage
/*     */   implements HttpRequest
/*     */ {
/*     */   private static final int HASH_CODE_PRIME = 31;
/*     */   private HttpMethod method;
/*     */   private String uri;
/*     */   
/*     */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri) {
/*  37 */     this(httpVersion, method, uri, DefaultHttpHeadersFactory.headersFactory().newHeaders());
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
/*     */   @Deprecated
/*     */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, boolean validateHeaders) {
/*  52 */     this(httpVersion, method, uri, DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders));
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
/*     */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, HttpHeadersFactory headersFactory) {
/*  66 */     this(httpVersion, method, uri, headersFactory.newHeaders());
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
/*     */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, HttpHeaders headers) {
/*  78 */     this(httpVersion, method, uri, headers, true);
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
/*     */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, HttpHeaders headers, boolean validateRequestLine) {
/*  91 */     super(httpVersion, headers);
/*  92 */     this.method = (HttpMethod)ObjectUtil.checkNotNull(method, "method");
/*  93 */     this.uri = (String)ObjectUtil.checkNotNull(uri, "uri");
/*  94 */     if (validateRequestLine) {
/*  95 */       HttpUtil.validateRequestLineTokens(method, uri);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HttpMethod getMethod() {
/* 102 */     return method();
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpMethod method() {
/* 107 */     return this.method;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getUri() {
/* 113 */     return uri();
/*     */   }
/*     */ 
/*     */   
/*     */   public String uri() {
/* 118 */     return this.uri;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpRequest setMethod(HttpMethod method) {
/* 123 */     this.method = (HttpMethod)ObjectUtil.checkNotNull(method, "method");
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpRequest setUri(String uri) {
/* 129 */     this.uri = (String)ObjectUtil.checkNotNull(uri, "uri");
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpRequest setProtocolVersion(HttpVersion version) {
/* 135 */     super.setProtocolVersion(version);
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     int result = 1;
/* 142 */     result = 31 * result + this.method.hashCode();
/* 143 */     result = 31 * result + this.uri.hashCode();
/* 144 */     result = 31 * result + super.hashCode();
/* 145 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 150 */     if (!(o instanceof DefaultHttpRequest)) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     DefaultHttpRequest other = (DefaultHttpRequest)o;
/*     */     
/* 156 */     return (method().equals(other.method()) && 
/* 157 */       uri().equalsIgnoreCase(other.uri()) && super
/* 158 */       .equals(o));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 163 */     return HttpMessageUtil.appendRequest(new StringBuilder(256), this).toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultHttpRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */