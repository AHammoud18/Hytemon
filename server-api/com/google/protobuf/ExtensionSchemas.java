/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CheckReturnValue
/*    */ final class ExtensionSchemas
/*    */ {
/* 12 */   private static final ExtensionSchema<?> LITE_SCHEMA = new ExtensionSchemaLite();
/* 13 */   private static final ExtensionSchema<?> FULL_SCHEMA = loadSchemaForFullRuntime();
/*    */   
/*    */   private static ExtensionSchema<?> loadSchemaForFullRuntime() {
/* 16 */     if (Android.assumeLiteRuntime) {
/* 17 */       return null;
/*    */     }
/*    */     try {
/* 20 */       Class<?> clazz = Class.forName("com.google.protobuf.ExtensionSchemaFull");
/* 21 */       return clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 22 */     } catch (Exception e) {
/* 23 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   static ExtensionSchema<?> lite() {
/* 28 */     return LITE_SCHEMA;
/*    */   }
/*    */   
/*    */   static ExtensionSchema<?> full() {
/* 32 */     if (FULL_SCHEMA == null) {
/* 33 */       throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
/*    */     }
/* 35 */     return FULL_SCHEMA;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ExtensionSchemas.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */