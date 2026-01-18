/*    */ package io.sentry.util;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class ClassLoaderUtils
/*    */ {
/*    */   @NotNull
/*    */   public static ClassLoader classLoaderOrDefault(@Nullable ClassLoader classLoader) {
/* 10 */     if (classLoader == null) {
/*    */ 
/*    */       
/* 13 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/* 14 */       if (contextClassLoader != null) {
/* 15 */         return contextClassLoader;
/*    */       }
/*    */       
/* 18 */       return ClassLoader.getSystemClassLoader();
/*    */     } 
/* 20 */     return classLoader;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\ClassLoaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */