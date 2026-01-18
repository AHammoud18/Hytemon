/*    */ package com.google.protobuf;
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
/*    */ public abstract class ExtensionLite<ContainingType extends MessageLite, Type>
/*    */ {
/*    */   public abstract int getNumber();
/*    */   
/*    */   public abstract WireFormat.FieldType getLiteType();
/*    */   
/*    */   public abstract boolean isRepeated();
/*    */   
/*    */   public abstract Type getDefaultValue();
/*    */   
/*    */   public abstract MessageLite getMessageDefaultInstance();
/*    */   
/*    */   boolean isLite() {
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ExtensionLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */