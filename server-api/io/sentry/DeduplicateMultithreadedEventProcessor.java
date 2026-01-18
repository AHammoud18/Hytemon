/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.hints.EventDropReason;
/*    */ import io.sentry.protocol.SentryException;
/*    */ import io.sentry.util.HintUtils;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DeduplicateMultithreadedEventProcessor
/*    */   implements EventProcessor
/*    */ {
/*    */   @NotNull
/* 20 */   private final Map<String, Long> processedEvents = Collections.synchronizedMap(new HashMap<>());
/*    */   @NotNull
/*    */   private final SentryOptions options;
/*    */   
/*    */   public DeduplicateMultithreadedEventProcessor(@NotNull SentryOptions options) {
/* 25 */     this.options = options;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SentryEvent process(@NotNull SentryEvent event, @NotNull Hint hint) {
/* 30 */     if (!HintUtils.hasType(hint, UncaughtExceptionHandlerIntegration.UncaughtExceptionHint.class))
/*    */     {
/*    */       
/* 33 */       return event;
/*    */     }
/*    */     
/* 36 */     SentryException exception = event.getUnhandledException();
/* 37 */     if (exception == null) {
/* 38 */       return event;
/*    */     }
/*    */     
/* 41 */     String type = exception.getType();
/* 42 */     if (type == null) {
/* 43 */       return event;
/*    */     }
/*    */     
/* 46 */     Long currentEventTid = exception.getThreadId();
/* 47 */     if (currentEventTid == null) {
/* 48 */       return event;
/*    */     }
/*    */     
/* 51 */     Long tid = this.processedEvents.get(type);
/* 52 */     if (tid != null && !tid.equals(currentEventTid)) {
/* 53 */       this.options
/* 54 */         .getLogger()
/* 55 */         .log(SentryLevel.INFO, "Event %s has been dropped due to multi-threaded deduplication", new Object[] {
/*    */ 
/*    */             
/* 58 */             event.getEventId() });
/* 59 */       HintUtils.setEventDropReason(hint, EventDropReason.MULTITHREADED_DEDUPLICATION);
/* 60 */       return null;
/*    */     } 
/* 62 */     this.processedEvents.put(type, currentEventTid);
/* 63 */     return event;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Long getOrder() {
/* 68 */     return Long.valueOf(7000L);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DeduplicateMultithreadedEventProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */