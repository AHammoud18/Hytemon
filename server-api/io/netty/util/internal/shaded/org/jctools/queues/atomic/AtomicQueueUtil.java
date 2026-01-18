/*     */ package io.netty.util.internal.shaded.org.jctools.queues.atomic;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicLongArray;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AtomicQueueUtil
/*     */ {
/*     */   public static <E> E lvRefElement(AtomicReferenceArray<E> buffer, int offset) {
/*  13 */     return buffer.get(offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> E lpRefElement(AtomicReferenceArray<E> buffer, int offset) {
/*  18 */     return buffer.get(offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> void spRefElement(AtomicReferenceArray<E> buffer, int offset, E value) {
/*  23 */     buffer.lazySet(offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void soRefElement(AtomicReferenceArray<Object> buffer, int offset, Object value) {
/*  28 */     buffer.lazySet(offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> void svRefElement(AtomicReferenceArray<E> buffer, int offset, E value) {
/*  33 */     buffer.set(offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calcRefElementOffset(long index) {
/*  38 */     return (int)index;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calcCircularRefElementOffset(long index, long mask) {
/*  43 */     return (int)(index & mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> AtomicReferenceArray<E> allocateRefArray(int capacity) {
/*  48 */     return new AtomicReferenceArray<E>(capacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spLongElement(AtomicLongArray buffer, int offset, long e) {
/*  53 */     buffer.lazySet(offset, e);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void soLongElement(AtomicLongArray buffer, int offset, long e) {
/*  58 */     buffer.lazySet(offset, e);
/*     */   }
/*     */ 
/*     */   
/*     */   public static long lpLongElement(AtomicLongArray buffer, int offset) {
/*  63 */     return buffer.get(offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public static long lvLongElement(AtomicLongArray buffer, int offset) {
/*  68 */     return buffer.get(offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calcLongElementOffset(long index) {
/*  73 */     return (int)index;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calcCircularLongElementOffset(long index, int mask) {
/*  78 */     return (int)(index & mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AtomicLongArray allocateLongArray(int capacity) {
/*  83 */     return new AtomicLongArray(capacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int length(AtomicReferenceArray<?> buf) {
/*  88 */     return buf.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int modifiedCalcCircularRefElementOffset(long index, long mask) {
/*  96 */     return (int)(index & mask) >> 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int nextArrayOffset(AtomicReferenceArray<?> curr) {
/* 101 */     return length(curr) - 1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\atomic\AtomicQueueUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */