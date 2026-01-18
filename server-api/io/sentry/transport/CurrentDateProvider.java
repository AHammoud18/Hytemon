/*    */ package io.sentry.transport;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ 
/*    */ @Internal
/*    */ public final class CurrentDateProvider
/*    */   implements ICurrentDateProvider {
/*  8 */   private static final ICurrentDateProvider instance = new CurrentDateProvider();
/*    */   
/*    */   public static ICurrentDateProvider getInstance() {
/* 11 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final long getCurrentTimeMillis() {
/* 18 */     return System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\CurrentDateProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */