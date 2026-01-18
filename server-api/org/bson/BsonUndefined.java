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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BsonUndefined
/*    */   extends BsonValue
/*    */ {
/*    */   public BsonType getBsonType() {
/* 31 */     return BsonType.UNDEFINED;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 36 */     if (this == o) {
/* 37 */       return true;
/*    */     }
/*    */     
/* 40 */     if (o == null || getClass() != o.getClass()) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonUndefined.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */