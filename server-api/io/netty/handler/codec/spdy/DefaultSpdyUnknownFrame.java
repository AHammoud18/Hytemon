/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.DefaultByteBufHolder;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultSpdyUnknownFrame
/*     */   extends DefaultByteBufHolder
/*     */   implements SpdyUnknownFrame
/*     */ {
/*     */   private final int frameType;
/*     */   private final byte flags;
/*     */   
/*     */   public DefaultSpdyUnknownFrame(int frameType, byte flags, ByteBuf data) {
/*  27 */     super(data);
/*  28 */     this.frameType = frameType;
/*  29 */     this.flags = flags;
/*     */   }
/*     */ 
/*     */   
/*     */   public int frameType() {
/*  34 */     return this.frameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte flags() {
/*  39 */     return this.flags;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame copy() {
/*  44 */     return replace(content().copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame duplicate() {
/*  49 */     return replace(content().duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame retainedDuplicate() {
/*  54 */     return replace(content().retainedDuplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame replace(ByteBuf content) {
/*  59 */     return new DefaultSpdyUnknownFrame(this.frameType, this.flags, content);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame retain() {
/*  64 */     super.retain();
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame retain(int increment) {
/*  70 */     super.retain(increment);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame touch() {
/*  76 */     super.touch();
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultSpdyUnknownFrame touch(Object hint) {
/*  82 */     super.touch(hint);
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  88 */     if (!(o instanceof DefaultSpdyUnknownFrame)) {
/*  89 */       return false;
/*     */     }
/*  91 */     DefaultSpdyUnknownFrame that = (DefaultSpdyUnknownFrame)o;
/*  92 */     return (this.frameType == that.frameType && this.flags == that.flags && super
/*     */       
/*  94 */       .equals(that));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  99 */     int result = super.hashCode();
/* 100 */     result = 31 * result + this.frameType;
/* 101 */     result = 31 * result + this.flags;
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return StringUtil.simpleClassName(this) + "(frameType=" + this.frameType + ", flags=" + this.flags + ", content=" + 
/* 108 */       contentToString() + ')';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\DefaultSpdyUnknownFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */