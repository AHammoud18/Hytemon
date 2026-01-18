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
/*    */ public class Code
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 475535263314046697L;
/*    */   private final String code;
/*    */   
/*    */   public Code(String code) {
/* 38 */     this.code = code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCode() {
/* 47 */     return this.code;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 52 */     if (this == o) {
/* 53 */       return true;
/*    */     }
/* 55 */     if (o == null || getClass() != o.getClass()) {
/* 56 */       return false;
/*    */     }
/*    */     
/* 59 */     Code code1 = (Code)o;
/*    */     
/* 61 */     if (!this.code.equals(code1.code)) {
/* 62 */       return false;
/*    */     }
/*    */     
/* 65 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 70 */     return this.code.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return "Code{code='" + this.code + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\Code.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */