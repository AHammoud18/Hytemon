/*    */ package org.bson.codecs.pojo;
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
/*    */ class PropertyModelSerializationImpl<T>
/*    */   implements PropertySerialization<T>
/*    */ {
/*    */   public boolean shouldSerialize(T value) {
/* 26 */     return (value != null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyModelSerializationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */