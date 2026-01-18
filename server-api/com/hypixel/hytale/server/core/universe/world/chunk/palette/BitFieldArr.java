/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.palette;
/*     */ 
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class BitFieldArr
/*     */ {
/*     */   public static final int BITS_PER_INDEX = 8;
/*     */   public static final int LAST_BIT_INDEX = 7;
/*     */   public static final int INDEX_MASK = 255;
/*     */   private final int bits;
/*     */   private final int length;
/*     */   @Nonnull
/*     */   private final byte[] array;
/*     */   
/*     */   public BitFieldArr(int bits, int length) {
/*  25 */     if (bits <= 0) throw new IllegalArgumentException("The number of bits must be greater than zero."); 
/*  26 */     if (length <= 0) throw new IllegalArgumentException("The length must be greater than zero."); 
/*  27 */     this.bits = bits;
/*  28 */     this.array = new byte[length * bits / 8];
/*  29 */     this.length = length;
/*     */   }
/*     */   
/*     */   public int getLength() {
/*  33 */     return this.length;
/*     */   }
/*     */   
/*     */   public int get(int index) {
/*  37 */     int bitIndex = index * this.bits;
/*  38 */     int endBitIndex = (index + 1) * this.bits - 1;
/*  39 */     int endArrIndex = endBitIndex / 8;
/*     */     
/*  41 */     int value = 0;
/*  42 */     for (int i = 0; i < this.bits; i++, bitIndex++) {
/*  43 */       int arrIndex = bitIndex / 8;
/*  44 */       int startBit = bitIndex % 8;
/*     */       
/*  46 */       if (arrIndex > endArrIndex || startBit == 7) {
/*  47 */         value |= (this.array[arrIndex] >> startBit & 0x1) << i;
/*     */       } else {
/*     */         int endBit;
/*  50 */         if (arrIndex == endArrIndex) {
/*  51 */           endBit = endBitIndex % 8;
/*     */           
/*  53 */           if (startBit == endBit) {
/*  54 */             value |= (this.array[arrIndex] >> startBit & 0x1) << i;
/*  55 */           } else if (startBit == 0 && endBit == 7) {
/*  56 */             value |= (this.array[arrIndex] & 0xFF) << i;
/*     */           } else {
/*  58 */             int mask = -1 >>> 32 - endBit + 1 - startBit;
/*  59 */             value |= (this.array[arrIndex] >>> startBit & mask) << i;
/*     */           } 
/*     */         } else {
/*  62 */           endBit = 7;
/*     */           
/*  64 */           if (startBit == 0) {
/*  65 */             value |= (this.array[arrIndex] & 0xFF) << i;
/*     */           } else {
/*  67 */             int mask = -1 >>> 32 - endBit + 1 - startBit;
/*  68 */             value |= (this.array[arrIndex] >>> startBit & mask) << i;
/*     */           } 
/*     */         } 
/*     */         
/*  72 */         int inc = endBit - startBit;
/*  73 */         i += inc;
/*  74 */         bitIndex += inc;
/*     */       } 
/*     */     } 
/*  77 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int index, int value) {
/*  82 */     int bitIndex = index * this.bits;
/*  83 */     for (int i = 0; i < this.bits; i++, bitIndex++) {
/*  84 */       setBit(bitIndex, value >> i & 0x1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setBit(int bitIndex, int bit) {
/*  89 */     if (bit == 0) {
/*  90 */       this.array[bitIndex / 8] = (byte)(this.array[bitIndex / 8] & (1 << bitIndex % 8 ^ 0xFFFFFFFF));
/*     */     } else {
/*  92 */       this.array[bitIndex / 8] = (byte)(this.array[bitIndex / 8] | 1 << bitIndex % 8);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] get() {
/*  97 */     byte[] bytes = new byte[this.array.length];
/*  98 */     System.arraycopy(this.array, 0, bytes, 0, this.array.length);
/*  99 */     return bytes;
/*     */   }
/*     */   
/*     */   public void set(@Nonnull byte[] bytes) {
/* 103 */     System.arraycopy(bytes, 0, this.array, 0, Math.min(bytes.length, this.array.length));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toBitString() {
/* 108 */     StringBuilder sb = new StringBuilder();
/* 109 */     for (byte b : this.array) {
/* 110 */       sb.append(String.format("%8s", new Object[] { Integer.toBinaryString(b & 0xFF) }).replace(' ', '0'));
/*     */     } 
/* 112 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void copyFrom(@Nonnull BitFieldArr other) {
/* 116 */     if (this.bits == other.bits) throw new IllegalArgumentException("bits must be the same"); 
/* 117 */     if (this.length == other.length) throw new IllegalArgumentException("length must be the same"); 
/* 118 */     System.arraycopy(other.array, 0, this.array, 0, this.array.length);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\palette\BitFieldArr.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */