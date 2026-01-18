/*    */ package com.hypixel.hytale.builtin.portals.systems.curse;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.metadata.AdventureMetadata;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CurseItemDropsSystem extends RefSystem<EntityStore> {
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 19 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 20 */     if (!portalWorld.exists())
/* 21 */       return;  Set<String> cursedItemsInWorld = portalWorld.getPortalType().getCursedItems();
/* 22 */     if (cursedItemsInWorld.isEmpty())
/*    */       return; 
/* 24 */     ItemComponent itemComponent = (ItemComponent)store.getComponent(ref, ItemComponent.getComponentType());
/*    */     
/* 26 */     ItemStack itemStack = itemComponent.getItemStack();
/* 27 */     if (itemStack == null)
/*    */       return; 
/* 29 */     String itemId = itemStack.getItemId().toString();
/* 30 */     if (!cursedItemsInWorld.contains(itemId))
/*    */       return; 
/* 32 */     AdventureMetadata adventureMeta = (AdventureMetadata)itemStack.getFromMetadataOrDefault("Adventure", AdventureMetadata.CODEC);
/* 33 */     adventureMeta.setCursed(true);
/* 34 */     ItemStack cursed = itemStack.withMetadata(AdventureMetadata.KEYED_CODEC, adventureMeta);
/*    */     
/* 36 */     itemComponent.setItemStack(cursed);
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
/* 47 */     return (Query<EntityStore>)ItemComponent.getComponentType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\curse\CurseItemDropsSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */