/*    */ package com.hypixel.hytale.server.npc.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.entity.Frozen;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.components.StepComponent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SteppableTickingSystem
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 35 */   private final ComponentType<EntityStore, StepComponent> stepComponentType = StepComponent.getComponentType(); @Nonnull
/* 36 */   private final ComponentType<EntityStore, Frozen> frozenComponentType = Frozen.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*    */     float tickLength;
/* 43 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 44 */     Frozen frozenComponent = (Frozen)archetypeChunk.getComponent(index, this.frozenComponentType);
/* 45 */     if (frozenComponent != null || world.getWorldConfig().isAllNPCFrozen()) {
/* 46 */       StepComponent stepComponent = (StepComponent)archetypeChunk.getComponent(index, this.stepComponentType);
/* 47 */       if (stepComponent == null)
/* 48 */         return;  tickLength = stepComponent.getTickLength();
/*    */     } else {
/* 50 */       tickLength = dt;
/*    */     } 
/*    */     
/* 53 */     steppedTick(tickLength, index, archetypeChunk, store, commandBuffer);
/*    */   }
/*    */   
/*    */   public abstract void steppedTick(float paramFloat, int paramInt, @Nonnull ArchetypeChunk<EntityStore> paramArchetypeChunk, @Nonnull Store<EntityStore> paramStore, @Nonnull CommandBuffer<EntityStore> paramCommandBuffer);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\SteppableTickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */