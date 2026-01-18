/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class DoubleComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements DoubleComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(double a, double b) {
/*  33 */       return Double.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator reversed() {
/*  38 */       return DoubleComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return DoubleComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final DoubleComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements DoubleComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(double a, double b) {
/*  54 */       return -Double.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator reversed() {
/*  59 */       return DoubleComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return DoubleComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final DoubleComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements DoubleComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final DoubleComparator comparator;
/*     */     
/*     */     protected OppositeComparator(DoubleComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(double a, double b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final DoubleComparator reversed() {
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
/*     */   public static DoubleComparator oppositeComparator(DoubleComparator c) {
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
/*     */   public static DoubleComparator asDoubleComparator(final Comparator<? super Double> c) {
/* 106 */     if (c == null || c instanceof DoubleComparator) return (DoubleComparator)c; 
/* 107 */     return new DoubleComparator()
/*     */       {
/*     */         public int compare(double x, double y) {
/* 110 */           return c.compare(Double.valueOf(x), Double.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Double x, Double y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */