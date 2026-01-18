/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CheckReturnValue
/*    */ final class ListFieldSchemas
/*    */ {
/* 12 */   private static final ListFieldSchema FULL_SCHEMA = loadSchemaForFullRuntime();
/* 13 */   private static final ListFieldSchema LITE_SCHEMA = new ListFieldSchemaLite();
/*    */   
/*    */   static ListFieldSchema full() {
/* 16 */     return FULL_SCHEMA;
/*    */   }
/*    */   
/*    */   static ListFieldSchema lite() {
/* 20 */     return LITE_SCHEMA;
/*    */   }
/*    */   
/*    */   private static ListFieldSchema loadSchemaForFullRuntime() {
/* 24 */     if (Android.assumeLiteRuntime) {
/* 25 */       return null;
/*    */     }
/*    */     try {
/* 28 */       Class<?> clazz = Class.forName("com.google.protobuf.ListFieldSchemaFull");
/* 29 */       return clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 30 */     } catch (Exception e) {
/* 31 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ListFieldSchemas.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */