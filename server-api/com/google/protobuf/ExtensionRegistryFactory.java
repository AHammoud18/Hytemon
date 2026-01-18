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
/*    */ final class ExtensionRegistryFactory
/*    */ {
/*    */   static final String FULL_REGISTRY_CLASS_NAME = "com.google.protobuf.ExtensionRegistry";
/* 24 */   static final Class<?> EXTENSION_REGISTRY_CLASS = reflectExtensionRegistry();
/*    */   
/*    */   static Class<?> reflectExtensionRegistry() {
/*    */     try {
/* 28 */       return Class.forName("com.google.protobuf.ExtensionRegistry");
/* 29 */     } catch (ClassNotFoundException e) {
/*    */ 
/*    */       
/* 32 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static ExtensionRegistryLite create() {
/* 38 */     ExtensionRegistryLite result = invokeSubclassFactory("newInstance");
/*    */     
/* 40 */     return (result != null) ? result : new ExtensionRegistryLite();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ExtensionRegistryLite createEmpty() {
/* 45 */     ExtensionRegistryLite result = invokeSubclassFactory("getEmptyRegistry");
/*    */     
/* 47 */     return (result != null) ? result : ExtensionRegistryLite.EMPTY_REGISTRY_LITE;
/*    */   }
/*    */   
/*    */   static boolean isFullRegistry(ExtensionRegistryLite registry) {
/* 51 */     return (!Android.assumeLiteRuntime && EXTENSION_REGISTRY_CLASS != null && EXTENSION_REGISTRY_CLASS
/*    */       
/* 53 */       .isAssignableFrom(registry.getClass()));
/*    */   }
/*    */   
/*    */   private static final ExtensionRegistryLite invokeSubclassFactory(String methodName) {
/* 57 */     if (EXTENSION_REGISTRY_CLASS == null) {
/* 58 */       return null;
/*    */     }
/*    */     
/*    */     try {
/* 62 */       return (ExtensionRegistryLite)EXTENSION_REGISTRY_CLASS
/* 63 */         .getDeclaredMethod(methodName, new Class[0]).invoke(null, new Object[0]);
/* 64 */     } catch (Exception e) {
/* 65 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ExtensionRegistryFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */