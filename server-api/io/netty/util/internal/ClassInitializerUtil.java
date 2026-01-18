/*    */ package io.netty.util.internal;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ClassInitializerUtil
/*    */ {
/*    */   public static void tryLoadClasses(Class<?> loadingClass, Class<?>... classes) {
/* 32 */     ClassLoader loader = PlatformDependent.getClassLoader(loadingClass);
/* 33 */     for (Class<?> clazz : classes) {
/* 34 */       tryLoadClass(loader, clazz.getName());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private static void tryLoadClass(ClassLoader classLoader, String className) {
/*    */     try {
/* 41 */       Class.forName(className, true, classLoader);
/* 42 */     } catch (ClassNotFoundException|SecurityException classNotFoundException) {}
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ClassInitializerUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */