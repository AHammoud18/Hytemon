/*    */ package org.bson.types;
/*    */ 
/*    */ import org.bson.BSONObject;
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
/*    */ public class CodeWScope
/*    */   extends Code
/*    */ {
/*    */   private final BSONObject scope;
/*    */   private static final long serialVersionUID = -6284832275113680002L;
/*    */   
/*    */   public CodeWScope(String code, BSONObject scope) {
/* 38 */     super(code);
/* 39 */     this.scope = scope;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BSONObject getScope() {
/* 47 */     return this.scope;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 52 */     if (o == null) {
/* 53 */       return false;
/*    */     }
/* 55 */     if (getClass() != o.getClass()) {
/* 56 */       return false;
/*    */     }
/* 58 */     CodeWScope c = (CodeWScope)o;
/* 59 */     return (getCode().equals(c.getCode()) && this.scope.equals(c.scope));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     return getCode().hashCode() ^ this.scope.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\CodeWScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */