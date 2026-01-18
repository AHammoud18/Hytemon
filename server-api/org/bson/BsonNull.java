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
/*    */ public final class BsonNull
/*    */   extends BsonValue
/*    */ {
/* 26 */   public static final BsonNull VALUE = new BsonNull();
/*    */ 
/*    */   
/*    */   public BsonType getBsonType() {
/* 30 */     return BsonType.NULL;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 35 */     if (this == o) {
/* 36 */       return true;
/*    */     }
/* 38 */     if (o == null || getClass() != o.getClass()) {
/* 39 */       return false;
/*    */     }
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 46 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return "BsonNull";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonNull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */