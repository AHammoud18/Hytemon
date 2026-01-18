/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public final class ShortComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements ShortComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(short a, short b) {
/*  33 */       return Short.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator reversed() {
/*  38 */       return ShortComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return ShortComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final ShortComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements ShortComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(short a, short b) {
/*  54 */       return -Short.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator reversed() {
/*  59 */       return ShortComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return ShortComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final ShortComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements ShortComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final ShortComparator comparator;
/*     */     
/*     */     protected OppositeComparator(ShortComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(short a, short b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final ShortComparator reversed() {
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
/*     */   public static ShortComparator oppositeComparator(ShortComparator c) {
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
/*     */   public static ShortComparator asShortComparator(final Comparator<? super Short> c) {
/* 106 */     if (c == null || c instanceof ShortComparator) return (ShortComparator)c; 
/* 107 */     return new ShortComparator()
/*     */       {
/*     */         public int compare(short x, short y) {
/* 110 */           return c.compare(Short.valueOf(x), Short.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Short x, Short y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */