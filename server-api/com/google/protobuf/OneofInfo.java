/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.lang.reflect.Field;
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
/*    */ @CheckReturnValue
/*    */ final class OneofInfo
/*    */ {
/*    */   private final int id;
/*    */   private final Field caseField;
/*    */   private final Field valueField;
/*    */   
/*    */   public OneofInfo(int id, Field caseField, Field valueField) {
/* 22 */     this.id = id;
/* 23 */     this.caseField = caseField;
/* 24 */     this.valueField = valueField;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 32 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getCaseField() {
/* 37 */     return this.caseField;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getValueField() {
/* 42 */     return this.valueField;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\OneofInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */