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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Extension<ContainingType extends MessageLite, Type>
/*    */   extends ExtensionLite<ContainingType, Type>
/*    */ {
/*    */   final boolean isLite() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected enum ExtensionType
/*    */   {
/* 37 */     IMMUTABLE,
/* 38 */     MUTABLE,
/* 39 */     PROTO1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public enum MessageType
/*    */   {
/* 46 */     PROTO1,
/* 47 */     PROTO2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MessageType getMessageType() {
/* 55 */     return MessageType.PROTO2;
/*    */   }
/*    */   
/*    */   public abstract Message getMessageDefaultInstance();
/*    */   
/*    */   public abstract Descriptors.FieldDescriptor getDescriptor();
/*    */   
/*    */   protected abstract ExtensionType getExtensionType();
/*    */   
/*    */   protected abstract Object fromReflectionType(Object paramObject);
/*    */   
/*    */   protected abstract Object singularFromReflectionType(Object paramObject);
/*    */   
/*    */   protected abstract Object toReflectionType(Object paramObject);
/*    */   
/*    */   protected abstract Object singularToReflectionType(Object paramObject);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Extension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */