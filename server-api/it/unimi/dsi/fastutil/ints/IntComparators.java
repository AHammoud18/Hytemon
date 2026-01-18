/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class IntComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements IntComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(int a, int b) {
/*  33 */       return Integer.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator reversed() {
/*  38 */       return IntComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return IntComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final IntComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements IntComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(int a, int b) {
/*  54 */       return -Integer.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator reversed() {
/*  59 */       return IntComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return IntComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final IntComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements IntComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final IntComparator comparator;
/*     */     
/*     */     protected OppositeComparator(IntComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(int a, int b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final IntComparator reversed() {
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
/*     */   public static IntComparator oppositeComparator(IntComparator c) {
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
/*     */   public static IntComparator asIntComparator(final Comparator<? super Integer> c) {
/* 106 */     if (c == null || c instanceof IntComparator) return (IntComparator)c; 
/* 107 */     return new IntComparator()
/*     */       {
/*     */         public int compare(int x, int y) {
/* 110 */           return c.compare(Integer.valueOf(x), Integer.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Integer x, Integer y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */