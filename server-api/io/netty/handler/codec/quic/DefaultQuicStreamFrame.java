/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.DefaultByteBufHolder;
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ public final class DefaultQuicStreamFrame
/*     */   extends DefaultByteBufHolder
/*     */   implements QuicStreamFrame
/*     */ {
/*     */   private final boolean fin;
/*     */   
/*     */   public DefaultQuicStreamFrame(ByteBuf data, boolean fin) {
/*  26 */     super(data);
/*  27 */     this.fin = fin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFin() {
/*  32 */     return this.fin;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame copy() {
/*  37 */     return new DefaultQuicStreamFrame(content().copy(), this.fin);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame duplicate() {
/*  42 */     return new DefaultQuicStreamFrame(content().duplicate(), this.fin);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame retainedDuplicate() {
/*  47 */     return new DefaultQuicStreamFrame(content().retainedDuplicate(), this.fin);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame replace(ByteBuf content) {
/*  52 */     return new DefaultQuicStreamFrame(content, this.fin);
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame retain() {
/*  57 */     super.retain();
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame retain(int increment) {
/*  63 */     super.retain(increment);
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame touch() {
/*  69 */     super.touch();
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuicStreamFrame touch(Object hint) {
/*  75 */     super.touch(hint);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  81 */     return "DefaultQuicStreamFrame{fin=" + this.fin + ", content=" + 
/*     */       
/*  83 */       contentToString() + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  89 */     if (this == o) {
/*  90 */       return true;
/*     */     }
/*  92 */     if (o == null || getClass() != o.getClass()) {
/*  93 */       return false;
/*     */     }
/*  95 */     DefaultQuicStreamFrame that = (DefaultQuicStreamFrame)o;
/*     */     
/*  97 */     if (this.fin != that.fin) {
/*  98 */       return false;
/*     */     }
/*     */     
/* 101 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     int result = super.hashCode();
/* 107 */     result = 31 * result + (this.fin ? 1 : 0);
/* 108 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\DefaultQuicStreamFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */