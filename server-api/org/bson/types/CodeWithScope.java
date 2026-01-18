/*    */ package org.bson.types;
/*    */ 
/*    */ import org.bson.Document;
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
/*    */ public class CodeWithScope
/*    */   extends Code
/*    */ {
/*    */   private final Document scope;
/*    */   private static final long serialVersionUID = -6284832275113680002L;
/*    */   
/*    */   public CodeWithScope(String code, Document scope) {
/* 39 */     super(code);
/* 40 */     this.scope = scope;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Document getScope() {
/* 49 */     return this.scope;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 54 */     if (this == o) {
/* 55 */       return true;
/*    */     }
/* 57 */     if (o == null || getClass() != o.getClass()) {
/* 58 */       return false;
/*    */     }
/* 60 */     if (!super.equals(o)) {
/* 61 */       return false;
/*    */     }
/*    */     
/* 64 */     CodeWithScope that = (CodeWithScope)o;
/*    */     
/* 66 */     if ((this.scope != null) ? !this.scope.equals(that.scope) : (that.scope != null)) {
/* 67 */       return false;
/*    */     }
/*    */     
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 75 */     return getCode().hashCode() ^ this.scope.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\CodeWithScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */