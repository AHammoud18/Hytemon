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
/*     */ 
/*     */ public class DefaultFullHttpResponse
/*     */   extends DefaultHttpResponse
/*     */   implements FullHttpResponse
/*     */ {
/*     */   private final ByteBuf content;
/*     */   private final HttpHeaders trailingHeaders;
/*     */   private int hash;
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status) {
/*  44 */     this(version, status, Unpooled.buffer(0), DefaultHttpHeadersFactory.headersFactory(), DefaultHttpHeadersFactory.trailersFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content) {
/*  51 */     this(version, status, content, DefaultHttpHeadersFactory.headersFactory(), DefaultHttpHeadersFactory.trailersFactory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, boolean validateHeaders) {
/*  62 */     this(version, status, Unpooled.buffer(0), 
/*  63 */         DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders), 
/*  64 */         DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders));
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
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, boolean validateHeaders, boolean singleFieldHeaders) {
/*  77 */     this(version, status, Unpooled.buffer(0), 
/*  78 */         DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders).withCombiningHeaders(singleFieldHeaders), 
/*  79 */         DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders).withCombiningHeaders(singleFieldHeaders));
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
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content, boolean validateHeaders) {
/*  91 */     this(version, status, content, 
/*  92 */         DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders), 
/*  93 */         DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders));
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
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content, boolean validateHeaders, boolean singleFieldHeaders) {
/* 106 */     this(version, status, content, 
/* 107 */         DefaultHttpHeadersFactory.headersFactory().withValidation(validateHeaders).withCombiningHeaders(singleFieldHeaders), 
/* 108 */         DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders).withCombiningHeaders(singleFieldHeaders));
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
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content, HttpHeadersFactory headersFactory, HttpHeadersFactory trailersFactory) {
/* 120 */     this(version, status, content, headersFactory.newHeaders(), trailersFactory.newHeaders());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content, HttpHeaders headers, HttpHeaders trailingHeaders) {
/* 128 */     super(version, status, headers);
/* 129 */     this.content = (ByteBuf)ObjectUtil.checkNotNull(content, "content");
/* 130 */     this.trailingHeaders = (HttpHeaders)ObjectUtil.checkNotNull(trailingHeaders, "trailingHeaders");
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/* 135 */     return this.trailingHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/* 140 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 145 */     return this.content.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse retain() {
/* 150 */     this.content.retain();
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse retain(int increment) {
/* 156 */     this.content.retain(increment);
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse touch() {
/* 162 */     this.content.touch();
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse touch(Object hint) {
/* 168 */     this.content.touch(hint);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 174 */     return this.content.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 179 */     return this.content.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse setProtocolVersion(HttpVersion version) {
/* 184 */     super.setProtocolVersion(version);
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse setStatus(HttpResponseStatus status) {
/* 190 */     super.setStatus(status);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse copy() {
/* 196 */     return replace(content().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse duplicate() {
/* 201 */     return replace(content().duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse retainedDuplicate() {
/* 206 */     return replace(content().retainedDuplicate());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FullHttpResponse replace(ByteBuf content) {
/* 212 */     FullHttpResponse response = new DefaultFullHttpResponse(protocolVersion(), status(), content, headers().copy(), trailingHeaders().copy());
/* 213 */     response.setDecoderResult(decoderResult());
/* 214 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 219 */     int hash = this.hash;
/* 220 */     if (hash == 0) {
/* 221 */       if (ByteBufUtil.isAccessible(content())) {
/*     */         try {
/* 223 */           hash = 31 + content().hashCode();
/* 224 */         } catch (IllegalReferenceCountException ignored) {
/*     */           
/* 226 */           hash = 31;
/*     */         } 
/*     */       } else {
/* 229 */         hash = 31;
/*     */       } 
/* 231 */       hash = 31 * hash + trailingHeaders().hashCode();
/* 232 */       hash = 31 * hash + super.hashCode();
/* 233 */       this.hash = hash;
/*     */     } 
/* 235 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 240 */     if (!(o instanceof DefaultFullHttpResponse)) {
/* 241 */       return false;
/*     */     }
/*     */     
/* 244 */     DefaultFullHttpResponse other = (DefaultFullHttpResponse)o;
/*     */     
/* 246 */     return (super.equals(other) && 
/* 247 */       content().equals(other.content()) && 
/* 248 */       trailingHeaders().equals(other.trailingHeaders()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 253 */     return HttpMessageUtil.appendFullResponse(new StringBuilder(256), this).toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultFullHttpResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */