/*    */ package com.hypixel.hytale.builtin.crafting.system;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCraftingSystem
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, CraftingManager> craftingManagerComponentType;
/*    */   
/*    */   public PlayerCraftingSystem(ComponentType<EntityStore, CraftingManager> craftingManagerComponentType) {
/* 61 */     this.craftingManagerComponentType = craftingManagerComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 66 */     return (Query)this.craftingManagerComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 71 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 76 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 77 */     CraftingManager craftingManagerComponent = (CraftingManager)archetypeChunk.getComponent(index, this.craftingManagerComponentType);
/* 78 */     craftingManagerComponent.tick(ref, (ComponentAccessor)commandBuffer, dt);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\system\PlayerCraftingSystems$PlayerCraftingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */