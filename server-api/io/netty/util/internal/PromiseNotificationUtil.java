/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.concurrent.Promise;
/*    */ import io.netty.util.internal.logging.InternalLogger;
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
/*    */ public final class PromiseNotificationUtil
/*    */ {
/*    */   public static void tryCancel(Promise<?> p, InternalLogger logger) {
/* 32 */     if (!p.cancel(false) && logger != null) {
/* 33 */       Throwable err = p.cause();
/* 34 */       if (err == null) {
/* 35 */         logger.warn("Failed to cancel promise because it has succeeded already: {}", p);
/*    */       } else {
/* 37 */         logger.warn("Failed to cancel promise because it has failed already: {}, unnotified cause:", p, err);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <V> void trySuccess(Promise<? super V> p, V result, InternalLogger logger) {
/* 48 */     if (!p.trySuccess(result) && logger != null) {
/* 49 */       Throwable err = p.cause();
/* 50 */       if (err == null) {
/* 51 */         logger.warn("Failed to mark a promise as success because it has succeeded already: {}", p);
/*    */       } else {
/* 53 */         logger.warn("Failed to mark a promise as success because it has failed already: {}, unnotified cause:", p, err);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void tryFailure(Promise<?> p, Throwable cause, InternalLogger logger) {
/* 64 */     if (!p.tryFailure(cause) && logger != null) {
/* 65 */       Throwable err = p.cause();
/* 66 */       if (err == null) {
/* 67 */         logger.warn("Failed to mark a promise as failure because it has succeeded already: {}", p, cause);
/* 68 */       } else if (logger.isWarnEnabled()) {
/* 69 */         logger.warn("Failed to mark a promise as failure because it has failed already: {}, unnotified cause:", p, cause);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\PromiseNotificationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */