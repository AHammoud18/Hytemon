/*    */ package com.hypixel.hytale.builtin.adventure.objectives.task;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CountObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CraftObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.CraftingRecipe;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.event.events.player.PlayerCraftEvent;
/*    */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CraftObjectiveTask extends CountObjectiveTask {
/* 18 */   public static final BuilderCodec<CraftObjectiveTask> CODEC = BuilderCodec.builder(CraftObjectiveTask.class, CraftObjectiveTask::new, CountObjectiveTask.CODEC)
/* 19 */     .build();
/*    */   
/*    */   public CraftObjectiveTask(@Nonnull CraftObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/* 22 */     super((CountObjectiveTaskAsset)asset, taskSetIndex, taskIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   protected CraftObjectiveTask() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CraftObjectiveTaskAsset getAsset() {
/* 31 */     return (CraftObjectiveTaskAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected TransactionRecord[] setup0(@Nonnull Objective objective, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 37 */     this.eventRegistry.register(PlayerCraftEvent.class, world.getName(), event -> {
/*    */           String desiredItemId = getAsset().getItemId();
/*    */           
/*    */           CraftingRecipe recipe = event.getCraftedRecipe();
/*    */           
/*    */           boolean isOutput = false;
/*    */           for (MaterialQuantity materialQuantity : recipe.getOutputs()) {
/*    */             if (Objects.equals(materialQuantity.getItemId(), desiredItemId)) {
/*    */               isOutput = true;
/*    */               break;
/*    */             } 
/*    */           } 
/*    */           if (!isOutput) {
/*    */             return;
/*    */           }
/*    */           Ref<EntityStore> ref = event.getPlayerRef();
/*    */           UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/*    */           assert uuidComponent != null;
/*    */           if (!objective.getActivePlayerUUIDs().contains(uuidComponent.getUuid())) {
/*    */             return;
/*    */           }
/*    */           increaseTaskCompletion(store, ref, event.getQuantity(), objective);
/*    */         });
/* 60 */     return RegistrationTransactionRecord.wrap(this.eventRegistry);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 66 */     return "CraftObjectiveTask{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\task\CraftObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */