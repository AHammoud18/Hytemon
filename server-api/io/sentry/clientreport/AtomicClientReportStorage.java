/*    */ package io.sentry.clientreport;
/*    */ 
/*    */ import io.sentry.DataCategory;
/*    */ import io.sentry.util.LazyEvaluator;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ final class AtomicClientReportStorage
/*    */   implements IClientReportStorage {
/*    */   public AtomicClientReportStorage() {
/* 19 */     this.lostEventCounts = new LazyEvaluator(() -> {
/*    */           Map<ClientReportKey, AtomicLong> modifyableEventCountsForInit = new ConcurrentHashMap<>();
/*    */           for (DiscardReason discardReason : DiscardReason.values()) {
/*    */             for (DataCategory category : DataCategory.values()) {
/*    */               modifyableEventCountsForInit.put(new ClientReportKey(discardReason.getReason(), category.getCategory()), new AtomicLong(0L));
/*    */             }
/*    */           } 
/*    */           return (LazyEvaluator.Evaluator)Collections.unmodifiableMap(modifyableEventCountsForInit);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   private final LazyEvaluator<Map<ClientReportKey, AtomicLong>> lostEventCounts;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCount(ClientReportKey key, Long count) {
/* 40 */     AtomicLong quantity = (AtomicLong)((Map)this.lostEventCounts.getValue()).get(key);
/*    */     
/* 42 */     if (quantity != null) {
/* 43 */       quantity.addAndGet(count.longValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<DiscardedEvent> resetCountsAndGet() {
/* 49 */     List<DiscardedEvent> discardedEvents = new ArrayList<>();
/*    */     
/* 51 */     Set<Map.Entry<ClientReportKey, AtomicLong>> entrySet = ((Map<ClientReportKey, AtomicLong>)this.lostEventCounts.getValue()).entrySet();
/* 52 */     for (Map.Entry<ClientReportKey, AtomicLong> entry : entrySet) {
/* 53 */       Long quantity = Long.valueOf(((AtomicLong)entry.getValue()).getAndSet(0L));
/* 54 */       if (quantity.longValue() > 0L) {
/* 55 */         discardedEvents.add(new DiscardedEvent(((ClientReportKey)entry
/* 56 */               .getKey()).getReason(), ((ClientReportKey)entry.getKey()).getCategory(), quantity));
/*    */       }
/*    */     } 
/* 59 */     return discardedEvents;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\AtomicClientReportStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */