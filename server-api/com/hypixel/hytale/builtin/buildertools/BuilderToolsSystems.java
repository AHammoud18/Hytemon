/*    */ package com.hypixel.hytale.builtin.buildertools;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.BuilderToolItemReferenceAsset;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BuilderToolsSystems
/*    */ {
/*    */   public static class EnsureBuilderTools
/*    */     extends HolderSystem<EntityStore> {
/*    */     @Nonnull
/* 26 */     private static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Query<EntityStore> getQuery() {
/* 31 */       return (Query)PLAYER_COMPONENT_TYPE;
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 36 */       Player playerComponent = (Player)holder.getComponent(PLAYER_COMPONENT_TYPE);
/* 37 */       assert playerComponent != null;
/*    */       
/* 39 */       Map<String, BuilderToolItemReferenceAsset> builderTools = BuilderToolItemReferenceAsset.getAssetMap().getAssetMap();
/*    */ 
/*    */ 
/*    */       
/* 43 */       Inventory playerInventory = playerComponent.getInventory();
/* 44 */       ItemContainer playerTools = playerInventory.getTools();
/* 45 */       playerTools.clear();
/*    */       
/* 47 */       ObjectArrayList<ItemStack> objectArrayList = new ObjectArrayList();
/*    */ 
/*    */       
/* 50 */       for (BuilderToolItemReferenceAsset builderTool : builderTools.values()) {
/* 51 */         String[] builderToolItems = builderTool.getItems();
/* 52 */         for (String builderToolItem : builderToolItems) {
/* 53 */           objectArrayList.add(new ItemStack(builderToolItem));
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 58 */       if (!playerTools.addItemStacks((List)objectArrayList).succeeded())
/* 59 */         throw new IllegalArgumentException("Could not add items to the Tools container"); 
/*    */     }
/*    */     
/*    */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\BuilderToolsSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */