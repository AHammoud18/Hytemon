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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class NativeLibraryUtil
/*    */ {
/*    */   public static void loadLibrary(String libName, boolean absolute) {
/* 35 */     if (absolute) {
/* 36 */       System.load(libName);
/*    */     } else {
/* 38 */       System.loadLibrary(libName);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\NativeLibraryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */