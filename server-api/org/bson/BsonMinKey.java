/*    */ package org.bson;
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
/*    */ public final class BsonMinKey
/*    */   extends BsonValue
/*    */ {
/*    */   public BsonType getBsonType() {
/* 26 */     return BsonType.MIN_KEY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 31 */     return o instanceof BsonMinKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 36 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     return "BsonMinKey";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonMinKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */