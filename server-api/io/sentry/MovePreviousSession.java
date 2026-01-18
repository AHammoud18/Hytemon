/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.cache.EnvelopeCache;
/*    */ import io.sentry.cache.IEnvelopeCache;
/*    */ import java.io.File;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ final class MovePreviousSession
/*    */   implements Runnable
/*    */ {
/*    */   @NotNull
/*    */   private final SentryOptions options;
/*    */   
/*    */   MovePreviousSession(@NotNull SentryOptions options) {
/* 16 */     this.options = options;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 21 */     String cacheDirPath = this.options.getCacheDirPath();
/* 22 */     if (cacheDirPath == null) {
/* 23 */       this.options.getLogger().log(SentryLevel.INFO, "Cache dir is not set, not moving the previous session.", new Object[0]);
/*    */       
/*    */       return;
/*    */     } 
/* 27 */     if (!this.options.isEnableAutoSessionTracking()) {
/* 28 */       this.options
/* 29 */         .getLogger()
/* 30 */         .log(SentryLevel.DEBUG, "Session tracking is disabled, bailing from previous session mover.", new Object[0]);
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     IEnvelopeCache cache = this.options.getEnvelopeDiskCache();
/* 35 */     if (cache instanceof EnvelopeCache) {
/* 36 */       File currentSessionFile = EnvelopeCache.getCurrentSessionFile(cacheDirPath);
/* 37 */       File previousSessionFile = EnvelopeCache.getPreviousSessionFile(cacheDirPath);
/*    */       
/* 39 */       ((EnvelopeCache)cache).movePreviousSession(currentSessionFile, previousSessionFile);
/*    */       
/* 41 */       ((EnvelopeCache)cache).flushPreviousSession();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MovePreviousSession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */