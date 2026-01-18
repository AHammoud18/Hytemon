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
/*    */ public class BsonSymbol
/*    */   extends BsonValue
/*    */ {
/*    */   private final String symbol;
/*    */   
/*    */   public BsonSymbol(String value) {
/* 36 */     if (value == null) {
/* 37 */       throw new IllegalArgumentException("Value can not be null");
/*    */     }
/* 39 */     this.symbol = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonType getBsonType() {
/* 44 */     return BsonType.SYMBOL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSymbol() {
/* 53 */     return this.symbol;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 64 */     if (this == o) {
/* 65 */       return true;
/*    */     }
/* 67 */     if (o == null || getClass() != o.getClass()) {
/* 68 */       return false;
/*    */     }
/*    */     
/* 71 */     BsonSymbol symbol1 = (BsonSymbol)o;
/*    */     
/* 73 */     if (!this.symbol.equals(symbol1.symbol)) {
/* 74 */       return false;
/*    */     }
/*    */     
/* 77 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 82 */     return this.symbol.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return this.symbol;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonSymbol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */