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
/*    */ 
/*    */ 
/*    */ public class BsonElement
/*    */ {
/*    */   private final String name;
/*    */   private final BsonValue value;
/*    */   
/*    */   public BsonElement(String name, BsonValue value) {
/* 36 */     this.name = name;
/* 37 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 46 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonValue getValue() {
/* 55 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 60 */     if (this == o) {
/* 61 */       return true;
/*    */     }
/* 63 */     if (o == null || getClass() != o.getClass()) {
/* 64 */       return false;
/*    */     }
/*    */     
/* 67 */     BsonElement that = (BsonElement)o;
/*    */     
/* 69 */     if ((getName() != null) ? !getName().equals(that.getName()) : (that.getName() != null)) {
/* 70 */       return false;
/*    */     }
/* 72 */     if ((getValue() != null) ? !getValue().equals(that.getValue()) : (that.getValue() != null)) {
/* 73 */       return false;
/*    */     }
/*    */     
/* 76 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 81 */     int result = (getName() != null) ? getName().hashCode() : 0;
/* 82 */     result = 31 * result + ((getValue() != null) ? getValue().hashCode() : 0);
/* 83 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */