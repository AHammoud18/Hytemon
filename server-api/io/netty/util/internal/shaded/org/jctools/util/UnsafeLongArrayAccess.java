/*     */ package io.netty.util.internal.shaded.org.jctools.util;
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
/*     */ public final class UnsafeLongArrayAccess
/*     */ {
/*     */   static {
/*  26 */     int scale = UnsafeAccess.UNSAFE.arrayIndexScale(long[].class);
/*  27 */     if (8 == scale) {
/*     */       
/*  29 */       LONG_ELEMENT_SHIFT = 3;
/*     */     }
/*     */     else {
/*     */       
/*  33 */       throw new IllegalStateException("Unknown pointer size: " + scale);
/*     */     } 
/*  35 */   } public static final long LONG_ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(long[].class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int LONG_ELEMENT_SHIFT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spLongElement(long[] buffer, long offset, long e) {
/*  47 */     UnsafeAccess.UNSAFE.putLong(buffer, offset, e);
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
/*     */   public static void soLongElement(long[] buffer, long offset, long e) {
/*  59 */     UnsafeAccess.UNSAFE.putOrderedLong(buffer, offset, e);
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
/*     */   public static long lpLongElement(long[] buffer, long offset) {
/*  71 */     return UnsafeAccess.UNSAFE.getLong(buffer, offset);
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
/*     */   public static long lvLongElement(long[] buffer, long offset) {
/*  83 */     return UnsafeAccess.UNSAFE.getLongVolatile(buffer, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long calcLongElementOffset(long index) {
/*  92 */     return LONG_ARRAY_BASE + (index << LONG_ELEMENT_SHIFT);
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
/*     */   public static long calcCircularLongElementOffset(long index, long mask) {
/* 104 */     return LONG_ARRAY_BASE + ((index & mask) << LONG_ELEMENT_SHIFT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long[] allocateLongArray(int capacity) {
/* 112 */     return new long[capacity];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctool\\util\UnsafeLongArrayAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */