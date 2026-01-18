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
/*    */ public class BsonString
/*    */   extends BsonValue
/*    */   implements Comparable<BsonString>
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public BsonString(String value) {
/* 34 */     if (value == null) {
/* 35 */       throw new IllegalArgumentException("Value can not be null");
/*    */     }
/* 37 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(BsonString o) {
/* 42 */     return this.value.compareTo(o.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonType getBsonType() {
/* 47 */     return BsonType.STRING;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 56 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 61 */     if (this == o) {
/* 62 */       return true;
/*    */     }
/* 64 */     if (o == null || getClass() != o.getClass()) {
/* 65 */       return false;
/*    */     }
/*    */     
/* 68 */     BsonString that = (BsonString)o;
/*    */     
/* 70 */     if (!this.value.equals(that.value)) {
/* 71 */       return false;
/*    */     }
/*    */     
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 79 */     return this.value.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return "BsonString{value='" + this.value + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */