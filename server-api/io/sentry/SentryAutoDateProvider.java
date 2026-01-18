/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Platform;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class SentryAutoDateProvider implements SentryDateProvider {
/*    */   @NotNull
/*    */   private final SentryDateProvider dateProvider;
/*    */   
/*    */   public SentryAutoDateProvider() {
/* 13 */     if (checkInstantAvailabilityAndPrecision()) {
/* 14 */       this.dateProvider = new SentryInstantDateProvider();
/*    */     } else {
/* 16 */       this.dateProvider = new SentryNanotimeDateProvider();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryDate now() {
/* 23 */     return this.dateProvider.now();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean checkInstantAvailabilityAndPrecision() {
/* 33 */     return (Platform.isJvm() && Platform.isJavaNinePlus());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryAutoDateProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */