/*    */ package it.unimi.dsi.fastutil.chars;
/*    */ 
/*    */ import java.lang.invoke.SerializedLambda;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface CharComparator
/*    */   extends Comparator<Character>
/*    */ {
/*    */   default CharComparator reversed() {
/* 44 */     return CharComparators.oppositeComparator(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default int compare(Character ok1, Character ok2) {
/* 56 */     return compare(ok1.charValue(), ok2.charValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default CharComparator thenComparing(CharComparator second) {
/* 66 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   default Comparator<Character> thenComparing(Comparator<? super Character> second) {
/* 74 */     if (second instanceof CharComparator) return thenComparing((CharComparator)second); 
/* 75 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(char paramChar1, char paramChar2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */