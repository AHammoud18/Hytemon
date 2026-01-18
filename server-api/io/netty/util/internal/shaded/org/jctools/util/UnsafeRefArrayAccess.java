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
/*     */ public final class UnsafeRefArrayAccess
/*     */ {
/*     */   static {
/*  26 */     int scale = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
/*  27 */     if (4 == scale) {
/*     */       
/*  29 */       REF_ELEMENT_SHIFT = 2;
/*     */     }
/*  31 */     else if (8 == scale) {
/*     */       
/*  33 */       REF_ELEMENT_SHIFT = 3;
/*     */     }
/*     */     else {
/*     */       
/*  37 */       throw new IllegalStateException("Unknown pointer size: " + scale);
/*     */     } 
/*  39 */   } public static final long REF_ARRAY_BASE = UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int REF_ELEMENT_SHIFT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> void spRefElement(E[] buffer, long offset, E e) {
/*  51 */     UnsafeAccess.UNSAFE.putObject(buffer, offset, e);
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
/*     */   public static <E> void soRefElement(E[] buffer, long offset, E e) {
/*  63 */     UnsafeAccess.UNSAFE.putOrderedObject(buffer, offset, e);
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
/*     */   public static <E> E lpRefElement(E[] buffer, long offset) {
/*  76 */     return (E)UnsafeAccess.UNSAFE.getObject(buffer, offset);
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
/*     */   public static <E> E lvRefElement(E[] buffer, long offset) {
/*  89 */     return (E)UnsafeAccess.UNSAFE.getObjectVolatile(buffer, offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long calcRefElementOffset(long index) {
/*  98 */     return REF_ARRAY_BASE + (index << REF_ELEMENT_SHIFT);
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
/*     */   public static long calcCircularRefElementOffset(long index, long mask) {
/* 110 */     return REF_ARRAY_BASE + ((index & mask) << REF_ELEMENT_SHIFT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> E[] allocateRefArray(int capacity) {
/* 119 */     return (E[])new Object[capacity];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctool\\util\UnsafeRefArrayAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */