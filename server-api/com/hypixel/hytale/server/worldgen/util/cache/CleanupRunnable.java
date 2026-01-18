/*    */ package com.hypixel.hytale.server.worldgen.util.cache;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CleanupRunnable<K, V>
/*    */   implements Runnable
/*    */ {
/* 13 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */   
/*    */   private final WeakReference<Cache<K, V>> reference;
/*    */   
/*    */   public CleanupRunnable(WeakReference<Cache<K, V>> reference) {
/* 18 */     this.reference = reference;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 24 */       Cache<K, V> cache = this.reference.get();
/* 25 */       if (cache != null) cache.cleanup(); 
/* 26 */     } catch (Exception e) {
/* 27 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to run cache cleanup!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\cache\CleanupRunnable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */