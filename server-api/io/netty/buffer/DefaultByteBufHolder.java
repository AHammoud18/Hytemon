/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultByteBufHolder
/*     */   implements ByteBufHolder
/*     */ {
/*     */   private final ByteBuf data;
/*     */   
/*     */   public DefaultByteBufHolder(ByteBuf data) {
/*  30 */     this.data = (ByteBuf)ObjectUtil.checkNotNull(data, "data");
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  35 */     return ByteBufUtil.ensureAccessible(this.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufHolder copy() {
/*  45 */     return replace(this.data.copy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufHolder duplicate() {
/*  55 */     return replace(this.data.duplicate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufHolder retainedDuplicate() {
/*  65 */     return replace(this.data.retainedDuplicate());
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
/*     */   public ByteBufHolder replace(ByteBuf content) {
/*  77 */     return new DefaultByteBufHolder(content);
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/*  82 */     return this.data.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufHolder retain() {
/*  87 */     this.data.retain();
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufHolder retain(int increment) {
/*  93 */     this.data.retain(increment);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufHolder touch() {
/*  99 */     this.data.touch();
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufHolder touch(Object hint) {
/* 105 */     this.data.touch(hint);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 111 */     return this.data.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 116 */     return this.data.release(decrement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String contentToString() {
/* 124 */     return this.data.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     return StringUtil.simpleClassName(this) + '(' + contentToString() + ')';
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
/*     */   public boolean equals(Object o) {
/* 145 */     if (this == o) {
/* 146 */       return true;
/*     */     }
/* 148 */     if (o != null && getClass() == o.getClass()) {
/* 149 */       return this.data.equals(((DefaultByteBufHolder)o).data);
/*     */     }
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 156 */     return this.data.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\DefaultByteBufHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */