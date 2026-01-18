/*    */ package io.netty.handler.codec.http.multipart;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ final class DeleteFileOnExitHook
/*    */ {
/* 26 */   private static final Set<String> FILES = ConcurrentHashMap.newKeySet();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 36 */     Runtime.getRuntime().addShutdownHook(new Thread()
/*    */         {
/*    */           public void run()
/*    */           {
/* 40 */             DeleteFileOnExitHook.runHook();
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void remove(String file) {
/* 51 */     FILES.remove(file);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void add(String file) {
/* 60 */     FILES.add(file);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean checkFileExist(String file) {
/* 70 */     return FILES.contains(file);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void runHook() {
/* 77 */     for (String filename : FILES)
/* 78 */       (new File(filename)).delete(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\DeleteFileOnExitHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */