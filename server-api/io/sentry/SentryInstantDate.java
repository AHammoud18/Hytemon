/*    */ package io.sentry;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SentryInstantDate
/*    */   extends SentryDate
/*    */ {
/*    */   @NotNull
/*    */   private final Instant date;
/*    */   
/*    */   public SentryInstantDate() {
/* 16 */     this(Instant.now());
/*    */   }
/*    */   
/*    */   public SentryInstantDate(@NotNull Instant date) {
/* 20 */     this.date = date;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long nanoTimestamp() {
/* 26 */     return DateUtils.secondsToNanos(this.date.getEpochSecond()) + this.date.getNano();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryInstantDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */