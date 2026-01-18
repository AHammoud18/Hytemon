/*    */ package io.sentry.util.runtime;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class NeutralRuntimeManager
/*    */   implements IRuntimeManager {
/*    */   public <T> T runWithRelaxedPolicy(@NotNull IRuntimeManager.IRuntimeManagerCallback<T> toRun) {
/* 10 */     return toRun.run();
/*    */   }
/*    */ 
/*    */   
/*    */   public void runWithRelaxedPolicy(@NotNull Runnable toRun) {
/* 15 */     toRun.run();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\runtime\NeutralRuntimeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */