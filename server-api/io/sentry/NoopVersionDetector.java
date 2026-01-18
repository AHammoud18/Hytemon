/*    */ package io.sentry;
/*    */ 
/*    */ public final class NoopVersionDetector
/*    */   implements IVersionDetector {
/*  5 */   private static final NoopVersionDetector instance = new NoopVersionDetector();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoopVersionDetector getInstance() {
/* 10 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkForMixedVersions() {
/* 15 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoopVersionDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */