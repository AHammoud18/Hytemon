/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
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
/*     */ public final class LongComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements LongComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(long a, long b) {
/*  33 */       return Long.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator reversed() {
/*  38 */       return LongComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return LongComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final LongComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements LongComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(long a, long b) {
/*  54 */       return -Long.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator reversed() {
/*  59 */       return LongComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return LongComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final LongComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements LongComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final LongComparator comparator;
/*     */     
/*     */     protected OppositeComparator(LongComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(long a, long b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final LongComparator reversed() {
/*  84 */       return this.comparator;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongComparator oppositeComparator(LongComparator c) {
/*  95 */     if (c instanceof OppositeComparator) return ((OppositeComparator)c).comparator; 
/*  96 */     return new OppositeComparator(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongComparator asLongComparator(final Comparator<? super Long> c) {
/* 106 */     if (c == null || c instanceof LongComparator) return (LongComparator)c; 
/* 107 */     return new LongComparator()
/*     */       {
/*     */         public int compare(long x, long y) {
/* 110 */           return c.compare(Long.valueOf(x), Long.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Long x, Long y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */