/*    */ package com.hypixel.hytale.builtin.adventure.objectives.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.interactions.StartObjectiveInteraction;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ObjectiveItemEntityRemovalSystem
/*    */   extends HolderSystem<EntityStore> {
/* 20 */   private static final ComponentType<EntityStore, ItemComponent> COMPONENT_TYPE = ItemComponent.getComponentType();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 25 */     return (Query)COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 34 */     if (reason != RemoveReason.REMOVE)
/*    */       return; 
/* 36 */     ItemComponent itemComponent = (ItemComponent)holder.getComponent(COMPONENT_TYPE);
/* 37 */     assert itemComponent != null;
/*    */     
/* 39 */     ItemStack itemStack = itemComponent.getItemStack();
/* 40 */     if (itemStack == null)
/*    */       return; 
/* 42 */     UUID objectiveUUID = (UUID)itemStack.getFromMetadataOrNull(StartObjectiveInteraction.OBJECTIVE_UUID);
/* 43 */     if (objectiveUUID == null)
/*    */       return; 
/* 45 */     if (itemComponent.isRemovedByPlayerPickup())
/*    */       return; 
/* 47 */     ObjectivePlugin.get().cancelObjective(objectiveUUID, store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\systems\ObjectiveItemEntityRemovalSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */