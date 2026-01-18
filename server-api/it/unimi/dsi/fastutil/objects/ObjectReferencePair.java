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
/*    */ public interface ObjectReferencePair<K, V>
/*    */   extends Pair<K, V>
/*    */ {
/*    */   static <K, V> ObjectReferencePair<K, V> of(K left, V right) {
/* 31 */     return new ObjectReferenceImmutablePair<>(left, right);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectReferencePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */