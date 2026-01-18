/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.types.ObjectId;
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
/*    */ public class ObjectIdGenerator
/*    */   implements IdGenerator
/*    */ {
/*    */   public Object generate() {
/* 29 */     return new ObjectId();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\ObjectIdGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */