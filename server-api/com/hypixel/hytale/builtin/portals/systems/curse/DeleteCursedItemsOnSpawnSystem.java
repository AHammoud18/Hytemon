/*    */ package com.hypixel.hytale.builtin.portals.systems.curse;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DeleteCursedItemsOnSpawnSystem extends RefSystem<EntityStore> {
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 17 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 18 */     if (portalWorld.exists())
/*    */       return; 
/* 20 */     Player player = (Player)store.getComponent(ref, Player.getComponentType());
/* 21 */     CursedItems.deleteAll(player);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 32 */     return (Query<EntityStore>)Player.getComponentType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\curse\DeleteCursedItemsOnSpawnSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */