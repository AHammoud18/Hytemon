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
/*    */ class NoOpFieldNameValidator
/*    */   implements FieldNameValidator
/*    */ {
/*    */   public boolean validate(String fieldName) {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public FieldNameValidator getValidatorForField(String fieldName) {
/* 27 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\NoOpFieldNameValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */