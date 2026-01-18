/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.DefaultByteBufHolder;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultHttp3UnknownFrame
/*     */   extends DefaultByteBufHolder
/*     */   implements Http3UnknownFrame
/*     */ {
/*     */   private final long type;
/*     */   
/*     */   public DefaultHttp3UnknownFrame(long type, ByteBuf payload) {
/*  28 */     super(payload);
/*  29 */     this.type = Http3CodecUtils.checkIsReservedFrameType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public long type() {
/*  34 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame copy() {
/*  39 */     return new DefaultHttp3UnknownFrame(this.type, content().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame duplicate() {
/*  44 */     return new DefaultHttp3UnknownFrame(this.type, content().duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame retainedDuplicate() {
/*  49 */     return new DefaultHttp3UnknownFrame(this.type, content().retainedDuplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame replace(ByteBuf content) {
/*  54 */     return new DefaultHttp3UnknownFrame(this.type, content);
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame retain() {
/*  59 */     super.retain();
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame retain(int increment) {
/*  65 */     super.retain(increment);
/*  66 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame touch() {
/*  71 */     super.touch();
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Http3UnknownFrame touch(Object hint) {
/*  77 */     super.touch(hint);
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  83 */     return StringUtil.simpleClassName(this) + "(type=" + type() + ", content=" + content() + ')';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  88 */     if (this == o) {
/*  89 */       return true;
/*     */     }
/*  91 */     if (o == null || getClass() != o.getClass()) {
/*  92 */       return false;
/*     */     }
/*  94 */     DefaultHttp3UnknownFrame that = (DefaultHttp3UnknownFrame)o;
/*  95 */     if (this.type != that.type) {
/*  96 */       return false;
/*     */     }
/*  98 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), Long.valueOf(this.type) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\DefaultHttp3UnknownFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */