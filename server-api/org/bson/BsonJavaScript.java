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
/*    */ 
/*    */ public class BsonJavaScript
/*    */   extends BsonValue
/*    */ {
/*    */   private final String code;
/*    */   
/*    */   public BsonJavaScript(String code) {
/* 34 */     this.code = code;
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonType getBsonType() {
/* 39 */     return BsonType.JAVASCRIPT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 48 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 53 */     if (this == o) {
/* 54 */       return true;
/*    */     }
/* 56 */     if (o == null || getClass() != o.getClass()) {
/* 57 */       return false;
/*    */     }
/*    */     
/* 60 */     BsonJavaScript code1 = (BsonJavaScript)o;
/*    */     
/* 62 */     if (!this.code.equals(code1.code)) {
/* 63 */       return false;
/*    */     }
/*    */     
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 71 */     return this.code.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 76 */     return "BsonJavaScript{code='" + this.code + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonJavaScript.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */