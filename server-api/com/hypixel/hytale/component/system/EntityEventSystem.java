/*    */ package com.hypixel.hytale.component.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
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
/*    */ public abstract class EntityEventSystem<ECS_TYPE, EventType extends EcsEvent>
/*    */   extends EventSystem<EventType>
/*    */   implements QuerySystem<ECS_TYPE>
/*    */ {
/*    */   protected EntityEventSystem(@Nonnull Class<EventType> eventType) {
/* 24 */     super(eventType);
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
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void handle(int paramInt, @Nonnull ArchetypeChunk<ECS_TYPE> paramArchetypeChunk, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer, @Nonnull EventType paramEventType);
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
/*    */   public void handleInternal(int index, @Nonnull ArchetypeChunk<ECS_TYPE> archetypeChunk, @Nonnull Store<ECS_TYPE> store, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer, @Nonnull EventType event) {
/* 52 */     if (!shouldProcessEvent(event))
/* 53 */       return;  handle(index, archetypeChunk, store, commandBuffer, event);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\EntityEventSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */