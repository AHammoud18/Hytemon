/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class NewInstanceSchemaFull
/*    */   implements NewInstanceSchema
/*    */ {
/*    */   public Object newInstance(Object defaultInstance) {
/* 13 */     return ((Message)defaultInstance).toBuilder().buildPartial();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\NewInstanceSchemaFull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */