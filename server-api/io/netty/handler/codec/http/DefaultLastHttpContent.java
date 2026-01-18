/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultLastHttpContent
/*     */   extends DefaultHttpContent
/*     */   implements LastHttpContent
/*     */ {
/*     */   private final HttpHeaders trailingHeaders;
/*     */   
/*     */   public DefaultLastHttpContent() {
/*  37 */     this(Unpooled.buffer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultLastHttpContent(ByteBuf content) {
/*  44 */     this(content, DefaultHttpHeadersFactory.trailersFactory());
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
/*     */   public DefaultLastHttpContent(ByteBuf content, boolean validateHeaders) {
/*  64 */     this(content, DefaultHttpHeadersFactory.trailersFactory().withValidation(validateHeaders));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultLastHttpContent(ByteBuf content, HttpHeadersFactory trailersFactory) {
/*  71 */     super(content);
/*  72 */     this.trailingHeaders = trailersFactory.newHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultLastHttpContent(ByteBuf content, HttpHeaders trailingHeaders) {
/*  79 */     super(content);
/*  80 */     this.trailingHeaders = (HttpHeaders)ObjectUtil.checkNotNull(trailingHeaders, "trailingHeaders");
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent copy() {
/*  85 */     return replace(content().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent duplicate() {
/*  90 */     return replace(content().duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent retainedDuplicate() {
/*  95 */     return replace(content().retainedDuplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent replace(ByteBuf content) {
/* 100 */     return new DefaultLastHttpContent(content, trailingHeaders().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent retain(int increment) {
/* 105 */     super.retain(increment);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent retain() {
/* 111 */     super.retain();
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent touch() {
/* 117 */     super.touch();
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent touch(Object hint) {
/* 123 */     super.touch(hint);
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/* 129 */     return this.trailingHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 134 */     StringBuilder buf = new StringBuilder(super.toString());
/* 135 */     buf.append(StringUtil.NEWLINE);
/* 136 */     appendHeaders(buf);
/*     */ 
/*     */     
/* 139 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 140 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void appendHeaders(StringBuilder buf) {
/* 144 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)trailingHeaders()) {
/* 145 */       buf.append(e.getKey());
/* 146 */       buf.append(": ");
/* 147 */       buf.append(e.getValue());
/* 148 */       buf.append(StringUtil.NEWLINE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\DefaultLastHttpContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */