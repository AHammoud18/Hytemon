/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public final class BooleanComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements BooleanComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(boolean a, boolean b) {
/*  33 */       return Boolean.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanComparator reversed() {
/*  38 */       return BooleanComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return BooleanComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final BooleanComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements BooleanComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(boolean a, boolean b) {
/*  54 */       return -Boolean.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanComparator reversed() {
/*  59 */       return BooleanComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return BooleanComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final BooleanComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements BooleanComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final BooleanComparator comparator;
/*     */     
/*     */     protected OppositeComparator(BooleanComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(boolean a, boolean b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final BooleanComparator reversed() {
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
/*     */   public static BooleanComparator oppositeComparator(BooleanComparator c) {
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
/*     */   public static BooleanComparator asBooleanComparator(final Comparator<? super Boolean> c) {
/* 106 */     if (c == null || c instanceof BooleanComparator) return (BooleanComparator)c; 
/* 107 */     return new BooleanComparator()
/*     */       {
/*     */         public int compare(boolean x, boolean y) {
/* 110 */           return c.compare(Boolean.valueOf(x), Boolean.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Boolean x, Boolean y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */