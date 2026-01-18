/*    */ package com.hypixel.hytale.component.system.tick;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentRegistry;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.QuerySystem;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class ArchetypeTickingSystem<ECS_TYPE> extends TickingSystem<ECS_TYPE> implements QuerySystem<ECS_TYPE> {
/*    */   public boolean test(@Nonnull ComponentRegistry<ECS_TYPE> componentRegistry, @Nonnull Archetype<ECS_TYPE> archetype) {
/* 13 */     if (!isExplicitQuery())
/*    */     {
/*    */       
/* 16 */       if (componentRegistry.getNonTickingComponentType().test(archetype)) return false;
/*    */     
/*    */     }
/* 19 */     return getQuery().test(archetype);
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
/*    */   public boolean isExplicitQuery() {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<ECS_TYPE> store) {
/* 36 */     store.tick(this, dt, systemIndex);
/*    */   }
/*    */   
/*    */   public abstract void tick(float paramFloat, @Nonnull ArchetypeChunk<ECS_TYPE> paramArchetypeChunk, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\tick\ArchetypeTickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */