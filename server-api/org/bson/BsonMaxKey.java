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
/*    */ public final class BsonMaxKey
/*    */   extends BsonValue
/*    */ {
/*    */   public BsonType getBsonType() {
/* 26 */     return BsonType.MAX_KEY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 31 */     return o instanceof BsonMaxKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 36 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     return "BsonMaxKey";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonMaxKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */