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
/*    */ final class Android
/*    */ {
/*    */   private static boolean ASSUME_ANDROID;
/*    */   static boolean assumeLiteRuntime = false;
/* 25 */   private static final Class<?> MEMORY_CLASS = getClassForName("libcore.io.Memory");
/*    */   
/* 27 */   private static final boolean IS_ROBOLECTRIC = (!ASSUME_ANDROID && 
/* 28 */     getClassForName("org.robolectric.Robolectric") != null);
/*    */ 
/*    */   
/*    */   static boolean isOnAndroidDevice() {
/* 32 */     return (ASSUME_ANDROID || (MEMORY_CLASS != null && !IS_ROBOLECTRIC));
/*    */   }
/*    */ 
/*    */   
/*    */   static Class<?> getMemoryClass() {
/* 37 */     return MEMORY_CLASS;
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> Class<T> getClassForName(String name) {
/*    */     try {
/* 43 */       return (Class)Class.forName(name);
/* 44 */     } catch (Throwable e) {
/* 45 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Android.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */