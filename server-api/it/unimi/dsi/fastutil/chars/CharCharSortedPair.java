/*    */ package it.unimi.dsi.fastutil.chars;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.SortedPair;
/*    */ import java.io.Serializable;
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
/*    */ public interface CharCharSortedPair
/*    */   extends CharCharPair, SortedPair<Character>, Serializable
/*    */ {
/*    */   static CharCharSortedPair of(char left, char right) {
/* 34 */     return CharCharImmutableSortedPair.of(left, right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean contains(char e) {
/* 45 */     return (e == leftChar() || e == rightChar());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default boolean contains(Object o) {
/* 56 */     if (o == null) return false; 
/* 57 */     return contains(((Character)o).charValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharCharSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */