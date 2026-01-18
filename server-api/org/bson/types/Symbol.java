/*    */ package org.bson.types;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class Symbol
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1326269319883146072L;
/*    */   private final String symbol;
/*    */   
/*    */   public Symbol(String symbol) {
/* 38 */     this.symbol = symbol;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSymbol() {
/* 47 */     return this.symbol;
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
/* 58 */     if (this == o) {
/* 59 */       return true;
/*    */     }
/* 61 */     if (o == null || getClass() != o.getClass()) {
/* 62 */       return false;
/*    */     }
/*    */     
/* 65 */     Symbol symbol1 = (Symbol)o;
/*    */     
/* 67 */     if (!this.symbol.equals(symbol1.symbol)) {
/* 68 */       return false;
/*    */     }
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     return this.symbol.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return this.symbol;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\Symbol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */