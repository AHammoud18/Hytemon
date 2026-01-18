/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.rrweb.RRWebEvent;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpReplayBreadcrumbConverter
/*    */   implements ReplayBreadcrumbConverter {
/*  9 */   private static final NoOpReplayBreadcrumbConverter instance = new NoOpReplayBreadcrumbConverter();
/*    */   
/*    */   public static NoOpReplayBreadcrumbConverter getInstance() {
/* 12 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public RRWebEvent convert(@NotNull Breadcrumb breadcrumb) {
/* 19 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpReplayBreadcrumbConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */