/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.IllegalReferenceCountException;
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ public class DefaultFullHttpRequest
/*     */   extends DefaultHttpRequest
/*     */   implements FullHttpRequest
/*     */ {
/*     */   private final ByteBuf content;
/*     */   private final HttpHeaders trailingHeader;
/*     */   private int hash;
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri) {
/*  43 */     this(httpVersion, method, uri, Unpooled.buffer(0), DefaultHttpHeadersFactory.headersFactory(), DefaultHttpHeadersFactory.trailersFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content) {
/*  50 */     this(httpVersion, method, uri, content, DefaultHttpHeadersFactory.headersFactory(), DefaultHttpHeadersFactory.trailersFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, boolean validateHeaders) {
/*  60 */     this(httpVersion, method, uri, Unpooled.buffer(0), 
/*  61 */         DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders), 
/*  62 */         DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content, boolean validateHeaders) {
/*  73 */     this(httpVersion, method, uri, content, 
/*  74 */         DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders), 
/*  75 */         DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders));
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
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content, HttpHeadersFactory headersFactory, HttpHeadersFactory trailersFactory) {
/*  87 */     this(httpVersion, method, uri, content, headersFactory.newHeaders(), trailersFactory.newHeaders());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content, HttpHeaders headers, HttpHeaders trailingHeader) {
/*  95 */     this(httpVersion, method, uri, content, headers, trailingHeader, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content, HttpHeaders headers, HttpHeaders trailingHeader, boolean validateRequestLine) {
/* 103 */     super(httpVersion, method, uri, headers, validateRequestLine);
/* 104 */     this.content = (ByteBuf)ObjectUtil.checkNotNull(content, "content");
/* 105 */     this.trailingHeader = (HttpHeaders)ObjectUtil.checkNotNull(trailingHeader, "trailingHeader");
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/* 110 */     return this.trailingHeader;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/* 115 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 120 */     return this.content.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest retain() {
/* 125 */     this.content.retain();
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest retain(int increment) {
/* 131 */     this.content.retain(increment);
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest touch() {
/* 137 */     this.content.touch();
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest touch(Object hint) {
/* 143 */     this.content.touch(hint);
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 149 */     return this.content.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 154 */     return this.content.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setProtocolVersion(HttpVersion version) {
/* 159 */     super.setProtocolVersion(version);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setMethod(HttpMethod method) {
/* 165 */     super.setMethod(method);
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setUri(String uri) {
/* 171 */     super.setUri(uri);
/* 172 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest copy() {
/* 177 */     return replace(content().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest duplicate() {
/* 182 */     return replace(content().duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest retainedDuplicate() {
/* 187 */     return replace(content().retainedDuplicate());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FullHttpRequest replace(ByteBuf content) {
/* 193 */     FullHttpRequest request = new DefaultFullHttpRequest(protocolVersion(), method(), uri(), content, headers().copy(), trailingHeaders().copy());
/* 194 */     request.setDecoderResult(decoderResult());
/* 195 */     return request;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     int hash = this.hash;
/* 201 */     if (hash == 0) {
/* 202 */       if (ByteBufUtil.isAccessible(content())) {
/*     */         try {
/* 204 */           hash = 31 + content().hashCode();
/* 205 */         } catch (IllegalReferenceCountException ignored) {
/*     */           
/* 207 */           hash = 31;
/*     */         } 
/*     */       } else {
/* 210 */         hash = 31;
/*     */       } 
/* 212 */       hash = 31 * hash + trailingHeaders().hashCode();
/* 213 */       hash = 31 * hash + super.hashCode();
/* 214 */       this.hash = hash;
/*     */     } 
/* 216 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 221 */     if (!(o instanceof DefaultFullHttpRequest)) {
/* 222 */       return false;
/*     */     }
/*     */     
/* 225 */     DefaultFullHttpRequest other = (DefaultFullHttpRequest)o;
/*     */     
/* 227 */     return (super.equals(other) && 
/* 228 */       content().equals(other.content()) && 
/* 229 */       trailingHeaders().equals(other.trailingHeaders()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 234 */     return HttpMessageUtil.appendFullRequest(new StringBuilder(256), this).toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultFullHttpRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */