/*     */ package org.bson;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class ByteBufNIO
/*     */   implements ByteBuf
/*     */ {
/*     */   private ByteBuffer buf;
/*  31 */   private final AtomicInteger referenceCount = new AtomicInteger(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufNIO(ByteBuffer buf) {
/*  39 */     this.buf = buf.order(ByteOrder.LITTLE_ENDIAN);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReferenceCount() {
/*  44 */     return this.referenceCount.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufNIO retain() {
/*  49 */     if (this.referenceCount.incrementAndGet() == 1) {
/*  50 */       this.referenceCount.decrementAndGet();
/*  51 */       throw new IllegalStateException("Attempted to increment the reference count when it is already 0");
/*     */     } 
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void release() {
/*  58 */     if (this.referenceCount.decrementAndGet() < 0) {
/*  59 */       this.referenceCount.incrementAndGet();
/*  60 */       throw new IllegalStateException("Attempted to decrement the reference count below 0");
/*     */     } 
/*  62 */     if (this.referenceCount.get() == 0) {
/*  63 */       this.buf = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/*  69 */     return this.buf.capacity();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf put(int index, byte b) {
/*  74 */     this.buf.put(index, b);
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int remaining() {
/*  80 */     return this.buf.remaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf put(byte[] src, int offset, int length) {
/*  85 */     this.buf.put(src, offset, length);
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRemaining() {
/*  91 */     return this.buf.hasRemaining();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf put(byte b) {
/*  96 */     this.buf.put(b);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf flip() {
/* 102 */     this.buf.flip();
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 108 */     return this.buf.array();
/*     */   }
/*     */ 
/*     */   
/*     */   public int limit() {
/* 113 */     return this.buf.limit();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf position(int newPosition) {
/* 118 */     this.buf.position(newPosition);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf clear() {
/* 124 */     this.buf.clear();
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder byteOrder) {
/* 130 */     this.buf.order(byteOrder);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte get() {
/* 136 */     return this.buf.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte get(int index) {
/* 141 */     return this.buf.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf get(byte[] bytes) {
/* 146 */     this.buf.get(bytes);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf get(int index, byte[] bytes) {
/* 152 */     return get(index, bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf get(byte[] bytes, int offset, int length) {
/* 157 */     this.buf.get(bytes, offset, length);
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf get(int index, byte[] bytes, int offset, int length) {
/* 163 */     for (int i = 0; i < length; i++) {
/* 164 */       bytes[offset + i] = this.buf.get(index + i);
/*     */     }
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong() {
/* 171 */     return this.buf.getLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 176 */     return this.buf.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble() {
/* 181 */     return this.buf.getDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 186 */     return this.buf.getDouble(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt() {
/* 191 */     return this.buf.getInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 196 */     return this.buf.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int position() {
/* 201 */     return this.buf.position();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf limit(int newLimit) {
/* 206 */     this.buf.limit(newLimit);
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf asReadOnly() {
/* 212 */     return new ByteBufNIO(this.buf.asReadOnlyBuffer());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/* 217 */     return new ByteBufNIO(this.buf.duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer asNIO() {
/* 222 */     return this.buf;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\ByteBufNIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */