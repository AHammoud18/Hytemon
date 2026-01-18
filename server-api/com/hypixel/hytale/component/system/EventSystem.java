/*    */ package com.hypixel.hytale.component.system;
/*    */ 
/*    */ import javax.annotation.Nonnull;
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
/*    */ public abstract class EventSystem<EventType extends EcsEvent>
/*    */ {
/*    */   @Nonnull
/*    */   private final Class<EventType> eventType;
/*    */   
/*    */   protected EventSystem(@Nonnull Class<EventType> eventType) {
/* 25 */     this.eventType = eventType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean shouldProcessEvent(@Nonnull EventType event) {
/* 35 */     if (event instanceof ICancellableEcsEvent) { ICancellableEcsEvent cancellable = (ICancellableEcsEvent)event; if (!cancellable.isCancelled()); return false; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Class<EventType> getEventType() {
/* 43 */     return this.eventType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\EventSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */