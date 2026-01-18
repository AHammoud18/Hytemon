/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class FloatComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements FloatComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(float a, float b) {
/*  33 */       return Float.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator reversed() {
/*  38 */       return FloatComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return FloatComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final FloatComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements FloatComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(float a, float b) {
/*  54 */       return -Float.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator reversed() {
/*  59 */       return FloatComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return FloatComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final FloatComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements FloatComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final FloatComparator comparator;
/*     */     
/*     */     protected OppositeComparator(FloatComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(float a, float b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final FloatComparator reversed() {
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
/*     */   public static FloatComparator oppositeComparator(FloatComparator c) {
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
/*     */   public static FloatComparator asFloatComparator(final Comparator<? super Float> c) {
/* 106 */     if (c == null || c instanceof FloatComparator) return (FloatComparator)c; 
/* 107 */     return new FloatComparator()
/*     */       {
/*     */         public int compare(float x, float y) {
/* 110 */           return c.compare(Float.valueOf(x), Float.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Float x, Float y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */