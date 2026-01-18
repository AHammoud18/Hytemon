/*    */ package com.hypixel.hytale.component.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
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
/*    */ public abstract class WorldEventSystem<ECS_TYPE, EventType extends EcsEvent>
/*    */   extends EventSystem<EventType>
/*    */   implements ISystem<ECS_TYPE>
/*    */ {
/*    */   protected WorldEventSystem(@Nonnull Class<EventType> eventType) {
/* 23 */     super(eventType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void handle(@Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer, @Nonnull EventType paramEventType);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleInternal(@Nonnull Store<ECS_TYPE> store, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer, @Nonnull EventType event) {
/* 45 */     if (!shouldProcessEvent(event))
/* 46 */       return;  handle(store, commandBuffer, event);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\WorldEventSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */