/*    */ package com.hypixel.hytale.builtin.crafting.system;
/*    */ import com.hypixel.hytale.builtin.crafting.component.CraftingManager;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PlayerCraftingSystems {
/*    */   public static class CraftingManagerAddSystem extends HolderSystem<EntityStore> {
/* 15 */     private final ComponentType<EntityStore, Player> playerComponentType = Player.getComponentType();
/*    */     private final ComponentType<EntityStore, CraftingManager> craftingManagerComponentType;
/*    */     
/*    */     public CraftingManagerAddSystem(ComponentType<EntityStore, CraftingManager> craftingManagerComponentType) {
/* 19 */       this.craftingManagerComponentType = craftingManagerComponentType;
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 24 */       Player player = (Player)holder.getComponent(Player.getComponentType());
/* 25 */       if (player == null) {
/* 26 */         throw new UnsupportedOperationException("Cannot have null player component during crafting system creation");
/*    */       }
/*    */       
/* 29 */       holder.ensureComponent(this.craftingManagerComponentType);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 34 */       CraftingManager craftingManager = (CraftingManager)holder.getComponent(this.craftingManagerComponentType);
/* 35 */       if (craftingManager == null)
/*    */         return; 
/* 37 */       Player player = (Player)holder.getComponent(this.playerComponentType);
/*    */       
/*    */       try {
/* 40 */         Ref<EntityStore> ref = player.getReference();
/* 41 */         craftingManager.cancelAllCrafting(ref, (ComponentAccessor)store);
/*    */       } finally {
/* 43 */         World world = ((EntityStore)store.getExternalData()).getWorld();
/* 44 */         if (world.getWorldConfig().isSavingPlayers() && 
/* 45 */           player != null) player.saveConfig(world, holder);
/*    */       
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Query<EntityStore> getQuery() {
/* 53 */       return (Query)this.playerComponentType;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class PlayerCraftingSystem extends EntityTickingSystem<EntityStore> {
/*    */     private final ComponentType<EntityStore, CraftingManager> craftingManagerComponentType;
/*    */     
/*    */     public PlayerCraftingSystem(ComponentType<EntityStore, CraftingManager> craftingManagerComponentType) {
/* 61 */       this.craftingManagerComponentType = craftingManagerComponentType;
/*    */     }
/*    */ 
/*    */     
/*    */     public Query<EntityStore> getQuery() {
/* 66 */       return (Query)this.craftingManagerComponentType;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 71 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */     }
/*    */ 
/*    */     
/*    */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 76 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 77 */       CraftingManager craftingManagerComponent = (CraftingManager)archetypeChunk.getComponent(index, this.craftingManagerComponentType);
/* 78 */       craftingManagerComponent.tick(ref, (ComponentAccessor)commandBuffer, dt);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\system\PlayerCraftingSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */