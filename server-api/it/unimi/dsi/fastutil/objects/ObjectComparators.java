/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ 
/*     */ public final class ObjectComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements Comparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(Object a, Object b) {
/*  34 */       return ((Comparable<Object>)a).compareTo(b);
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator reversed() {
/*  39 */       return ObjectComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  43 */       return ObjectComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  48 */   public static final Comparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */ 
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements Comparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(Object a, Object b) {
/*  57 */       return ((Comparable<Object>)b).compareTo(a);
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator reversed() {
/*  62 */       return ObjectComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  66 */       return ObjectComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  71 */   public static final Comparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator<K> implements Comparator<K>, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final Comparator<K> comparator;
/*     */     
/*     */     protected OppositeComparator(Comparator<K> c) {
/*  78 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(K a, K b) {
/*  83 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final Comparator<K> reversed() {
/*  88 */       return this.comparator;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Comparator<K> oppositeComparator(Comparator<K> c) {
/*  99 */     if (c instanceof OppositeComparator) return ((OppositeComparator)c).comparator; 
/* 100 */     return new OppositeComparator<>(c);
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
/*     */   public static <K> Comparator<K> asObjectComparator(Comparator<K> c) {
/* 113 */     return c;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */