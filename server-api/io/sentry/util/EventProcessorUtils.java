/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.EventProcessor;
/*    */ import io.sentry.internal.eventprocessor.EventProcessorAndOrder;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ public final class EventProcessorUtils
/*    */ {
/*    */   public static List<EventProcessor> unwrap(@Nullable List<EventProcessorAndOrder> orderedEventProcessor) {
/* 14 */     List<EventProcessor> eventProcessors = new ArrayList<>();
/*    */     
/* 16 */     if (orderedEventProcessor != null) {
/* 17 */       for (EventProcessorAndOrder eventProcessorAndOrder : orderedEventProcessor) {
/* 18 */         eventProcessors.add(eventProcessorAndOrder.getEventProcessor());
/*    */       }
/*    */     }
/*    */     
/* 22 */     return new CopyOnWriteArrayList<>(eventProcessors);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\EventProcessorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */