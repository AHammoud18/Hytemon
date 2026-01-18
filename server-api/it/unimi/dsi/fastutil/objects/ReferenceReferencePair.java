/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Pair;
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
/*    */ public interface ReferenceReferencePair<K, V>
/*    */   extends Pair<K, V>
/*    */ {
/*    */   static <K, V> ReferenceReferencePair<K, V> of(K left, V right) {
/* 31 */     return new ReferenceReferenceImmutablePair<>(left, right);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceReferencePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */