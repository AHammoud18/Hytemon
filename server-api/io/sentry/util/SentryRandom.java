/*    */ package io.sentry.util;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SentryRandom
/*    */ {
/*    */   @NotNull
/* 14 */   private static final SentryRandomThreadLocal instance = new SentryRandomThreadLocal();
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
/*    */   @NotNull
/*    */   public static Random current() {
/* 27 */     return instance.get();
/*    */   }
/*    */   
/*    */   private static class SentryRandomThreadLocal extends ThreadLocal<Random> {
/*    */     private SentryRandomThreadLocal() {}
/*    */     
/*    */     protected Random initialValue() {
/* 34 */       return new Random();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\SentryRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */