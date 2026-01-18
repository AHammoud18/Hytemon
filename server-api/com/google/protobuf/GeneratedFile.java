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
/*    */ public abstract class GeneratedFile
/*    */ {
/*    */   protected static void addOptionalExtension(ExtensionRegistry registry, String className, String fieldName) {
/*    */     try {
/* 24 */       GeneratedMessage.GeneratedExtension<?, ?> ext = (GeneratedMessage.GeneratedExtension<?, ?>)Class.forName(className).getField(fieldName).get(null);
/* 25 */       registry.add(ext);
/* 26 */     } catch (ClassNotFoundException classNotFoundException) {
/*    */     
/* 28 */     } catch (NoSuchFieldException noSuchFieldException) {
/*    */     
/* 30 */     } catch (IllegalAccessException illegalAccessException) {}
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\GeneratedFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */