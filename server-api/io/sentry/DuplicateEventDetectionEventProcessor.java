/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class DuplicateEventDetectionEventProcessor
/*    */   implements EventProcessor {
/*    */   @NotNull
/* 15 */   private final Map<Throwable, Object> capturedObjects = Collections.synchronizedMap(new WeakHashMap<>());
/*    */ 
/*    */   
/*    */   public DuplicateEventDetectionEventProcessor(@NotNull SentryOptions options) {
/* 19 */     this.options = (SentryOptions)Objects.requireNonNull(options, "options are required");
/*    */   } @NotNull
/*    */   private final SentryOptions options;
/*    */   @Nullable
/*    */   public SentryEvent process(@NotNull SentryEvent event, @NotNull Hint hint) {
/* 24 */     if (this.options.isEnableDeduplication()) {
/* 25 */       Throwable throwable = event.getThrowable();
/* 26 */       if (throwable != null) {
/* 27 */         if (this.capturedObjects.containsKey(throwable) || 
/* 28 */           containsAnyKey(this.capturedObjects, allCauses(throwable))) {
/* 29 */           this.options
/* 30 */             .getLogger()
/* 31 */             .log(SentryLevel.DEBUG, "Duplicate Exception detected. Event %s will be discarded.", new Object[] {
/*    */ 
/*    */                 
/* 34 */                 event.getEventId() });
/* 35 */           return null;
/*    */         } 
/* 37 */         this.capturedObjects.put(throwable, null);
/*    */       } 
/*    */     } else {
/*    */       
/* 41 */       this.options.getLogger().log(SentryLevel.DEBUG, "Event deduplication is disabled.", new Object[0]);
/*    */     } 
/* 43 */     return event;
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> boolean containsAnyKey(@NotNull Map<T, Object> map, @NotNull List<T> list) {
/* 48 */     for (T entry : list) {
/* 49 */       if (map.containsKey(entry)) {
/* 50 */         return true;
/*    */       }
/*    */     } 
/* 53 */     return false;
/*    */   }
/*    */   @NotNull
/*    */   private static List<Throwable> allCauses(@NotNull Throwable throwable) {
/* 57 */     List<Throwable> causes = new ArrayList<>();
/* 58 */     Throwable ex = throwable;
/* 59 */     while (ex.getCause() != null) {
/* 60 */       causes.add(ex.getCause());
/* 61 */       ex = ex.getCause();
/*    */     } 
/* 63 */     return causes;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Long getOrder() {
/* 68 */     return Long.valueOf(1000L);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DuplicateEventDetectionEventProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */