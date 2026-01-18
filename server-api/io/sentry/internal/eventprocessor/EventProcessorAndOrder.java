/*    */ package io.sentry.internal.eventprocessor;
/*    */ 
/*    */ import io.sentry.EventProcessor;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class EventProcessorAndOrder implements Comparable<EventProcessorAndOrder> {
/*    */   @NotNull
/*    */   private final EventProcessor eventProcessor;
/*    */   @NotNull
/*    */   private final Long order;
/*    */   
/*    */   public EventProcessorAndOrder(@NotNull EventProcessor eventProcessor, @Nullable Long order) {
/* 14 */     this.eventProcessor = eventProcessor;
/* 15 */     if (order == null) {
/* 16 */       this.order = Long.valueOf(System.nanoTime());
/*    */     } else {
/* 18 */       this.order = order;
/*    */     } 
/*    */   }
/*    */   @NotNull
/*    */   public EventProcessor getEventProcessor() {
/* 23 */     return this.eventProcessor;
/*    */   }
/*    */   @NotNull
/*    */   public Long getOrder() {
/* 27 */     return this.order;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(@NotNull EventProcessorAndOrder o) {
/* 32 */     return this.order.compareTo(o.order);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\internal\eventprocessor\EventProcessorAndOrder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */