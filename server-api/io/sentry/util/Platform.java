/*    */ package io.sentry.util;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class Platform
/*    */ {
/*    */   static boolean isAndroid;
/*    */   static boolean isJavaNinePlus;
/*    */   
/*    */   static {
/*    */     try {
/* 18 */       isAndroid = "The Android Project".equals(System.getProperty("java.vendor"));
/* 19 */     } catch (Throwable e) {
/* 20 */       isAndroid = false;
/*    */     } 
/*    */     
/*    */     try {
/* 24 */       String javaStringVersion = System.getProperty("java.specification.version");
/* 25 */       if (javaStringVersion != null) {
/* 26 */         double javaVersion = Double.valueOf(javaStringVersion).doubleValue();
/* 27 */         isJavaNinePlus = (javaVersion >= 9.0D);
/*    */       } else {
/* 29 */         isJavaNinePlus = false;
/*    */       } 
/* 31 */     } catch (Throwable e) {
/* 32 */       isJavaNinePlus = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isAndroid() {
/* 37 */     return isAndroid;
/*    */   }
/*    */   
/*    */   public static boolean isJvm() {
/* 41 */     return !isAndroid;
/*    */   }
/*    */   
/*    */   public static boolean isJavaNinePlus() {
/* 45 */     return isJavaNinePlus;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\Platform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */