/*    */ package it.unimi.dsi.fastutil.floats;
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
/*    */ public interface FloatFloatSortedPair
/*    */   extends FloatFloatPair, SortedPair<Float>, Serializable
/*    */ {
/*    */   static FloatFloatSortedPair of(float left, float right) {
/* 34 */     return FloatFloatImmutableSortedPair.of(left, right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean contains(float e) {
/* 45 */     return (e == leftFloat() || e == rightFloat());
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
/* 57 */     return contains(((Float)o).floatValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatFloatSortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */