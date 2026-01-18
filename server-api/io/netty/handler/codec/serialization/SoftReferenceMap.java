/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.Map;
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
/*    */ final class SoftReferenceMap<K, V>
/*    */   extends ReferenceMap<K, V>
/*    */ {
/*    */   SoftReferenceMap(Map<K, Reference<V>> delegate) {
/* 25 */     super(delegate);
/*    */   }
/*    */ 
/*    */   
/*    */   Reference<V> fold(V value) {
/* 30 */     return new SoftReference<>(value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\serialization\SoftReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */