/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public final class ByteComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements ByteComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(byte a, byte b) {
/*  33 */       return Byte.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteComparator reversed() {
/*  38 */       return ByteComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return ByteComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final ByteComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements ByteComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(byte a, byte b) {
/*  54 */       return -Byte.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteComparator reversed() {
/*  59 */       return ByteComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return ByteComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final ByteComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements ByteComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final ByteComparator comparator;
/*     */     
/*     */     protected OppositeComparator(ByteComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(byte a, byte b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final ByteComparator reversed() {
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
/*     */   public static ByteComparator oppositeComparator(ByteComparator c) {
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
/*     */   public static ByteComparator asByteComparator(final Comparator<? super Byte> c) {
/* 106 */     if (c == null || c instanceof ByteComparator) return (ByteComparator)c; 
/* 107 */     return new ByteComparator()
/*     */       {
/*     */         public int compare(byte x, byte y) {
/* 110 */           return c.compare(Byte.valueOf(x), Byte.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Byte x, Byte y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */