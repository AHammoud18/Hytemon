/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class CharComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements CharComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(char a, char b) {
/*  33 */       return Character.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator reversed() {
/*  38 */       return CharComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  42 */       return CharComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  46 */   public static final CharComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator
/*     */     implements CharComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(char a, char b) {
/*  54 */       return -Character.compare(a, b);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator reversed() {
/*  59 */       return CharComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  63 */       return CharComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  67 */   public static final CharComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements CharComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final CharComparator comparator;
/*     */     
/*     */     protected OppositeComparator(CharComparator c) {
/*  74 */       this.comparator = c;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int compare(char a, char b) {
/*  79 */       return this.comparator.compare(b, a);
/*     */     }
/*     */ 
/*     */     
/*     */     public final CharComparator reversed() {
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
/*     */   public static CharComparator oppositeComparator(CharComparator c) {
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
/*     */   public static CharComparator asCharComparator(final Comparator<? super Character> c) {
/* 106 */     if (c == null || c instanceof CharComparator) return (CharComparator)c; 
/* 107 */     return new CharComparator()
/*     */       {
/*     */         public int compare(char x, char y) {
/* 110 */           return c.compare(Character.valueOf(x), Character.valueOf(y));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public int compare(Character x, Character y) {
/* 116 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */