/*    */ package com.hypixel.hytale.server.worldgen.util.cache;
/*    */ 
/*    */ import java.lang.ref.Cleaner;
/*    */ import java.util.concurrent.ScheduledFuture;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CleanupFutureAction
/*    */   implements Runnable
/*    */ {
/* 11 */   public static final Cleaner CLEANER = Cleaner.create();
/*    */   
/*    */   private final ScheduledFuture<?> future;
/*    */   
/*    */   public CleanupFutureAction(ScheduledFuture<?> future) {
/* 16 */     this.future = future;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 21 */     this.future.cancel(false);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\CleanupFutureAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */