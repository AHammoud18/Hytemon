/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public final class LongLongHashMap
/*     */ {
/*     */   private static final int MASK_TEMPLATE = -2;
/*     */   private int mask;
/*     */   private long[] array;
/*     */   private int maxProbe;
/*     */   private long zeroVal;
/*     */   private final long emptyVal;
/*     */   
/*     */   public LongLongHashMap(long emptyVal) {
/*  29 */     this.emptyVal = emptyVal;
/*  30 */     this.zeroVal = emptyVal;
/*  31 */     int initialSize = 32;
/*  32 */     this.array = new long[initialSize];
/*  33 */     this.mask = initialSize - 1;
/*  34 */     computeMaskAndProbe();
/*     */   }
/*     */   
/*     */   public LongLongHashMap(LongLongHashMap other) {
/*  38 */     this.mask = other.mask;
/*  39 */     this.array = Arrays.copyOf(other.array, other.array.length);
/*  40 */     this.maxProbe = other.maxProbe;
/*  41 */     this.zeroVal = other.zeroVal;
/*  42 */     this.emptyVal = other.emptyVal;
/*     */   }
/*     */   
/*     */   public long put(long key, long value) {
/*  46 */     if (key == 0L) {
/*  47 */       long prev = this.zeroVal;
/*  48 */       this.zeroVal = value;
/*  49 */       return prev;
/*     */     } 
/*     */     
/*     */     while (true) {
/*  53 */       int index = index(key);
/*  54 */       for (int i = 0; i < this.maxProbe; i++) {
/*  55 */         long existing = this.array[index];
/*  56 */         if (existing == key || existing == 0L) {
/*  57 */           long prev = (existing == 0L) ? this.emptyVal : this.array[index + 1];
/*  58 */           this.array[index] = key;
/*  59 */           this.array[index + 1] = value;
/*  60 */           for (; i < this.maxProbe; i++) {
/*  61 */             index = index + 2 & this.mask;
/*  62 */             if (this.array[index] == key) {
/*  63 */               this.array[index] = 0L;
/*  64 */               prev = this.array[index + 1];
/*     */               break;
/*     */             } 
/*     */           } 
/*  68 */           return prev;
/*     */         } 
/*  70 */         index = index + 2 & this.mask;
/*     */       } 
/*  72 */       expand();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(long key) {
/*  77 */     if (key == 0L) {
/*  78 */       this.zeroVal = this.emptyVal;
/*     */       return;
/*     */     } 
/*  81 */     int index = index(key);
/*  82 */     for (int i = 0; i < this.maxProbe; i++) {
/*  83 */       long existing = this.array[index];
/*  84 */       if (existing == key) {
/*  85 */         this.array[index] = 0L;
/*     */         break;
/*     */       } 
/*  88 */       index = index + 2 & this.mask;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long get(long key) {
/*  93 */     if (key == 0L) {
/*  94 */       return this.zeroVal;
/*     */     }
/*  96 */     int index = index(key);
/*  97 */     for (int i = 0; i < this.maxProbe; i++) {
/*  98 */       long existing = this.array[index];
/*  99 */       if (existing == key) {
/* 100 */         return this.array[index + 1];
/*     */       }
/* 102 */       index = index + 2 & this.mask;
/*     */     } 
/* 104 */     return this.emptyVal;
/*     */   }
/*     */ 
/*     */   
/*     */   private int index(long key) {
/* 109 */     key ^= key >>> 33L;
/* 110 */     key *= -49064778989728563L;
/* 111 */     key ^= key >>> 33L;
/* 112 */     key *= -4265267296055464877L;
/* 113 */     key ^= key >>> 33L;
/* 114 */     return (int)key & this.mask;
/*     */   }
/*     */   
/*     */   private void expand() {
/* 118 */     long[] prev = this.array;
/* 119 */     this.array = new long[prev.length * 2];
/* 120 */     computeMaskAndProbe();
/* 121 */     for (int i = 0; i < prev.length; i += 2) {
/* 122 */       long key = prev[i];
/* 123 */       if (key != 0L) {
/* 124 */         long val = prev[i + 1];
/* 125 */         put(key, val);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void computeMaskAndProbe() {
/* 131 */     int length = this.array.length;
/* 132 */     this.mask = length - 1 & 0xFFFFFFFE;
/* 133 */     this.maxProbe = (int)Math.log(length);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\LongLongHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */