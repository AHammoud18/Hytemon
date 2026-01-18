/*    */ package com.hypixel.hytale.builtin.adventure.objectives.task;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CountObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.UseBlockObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.RegistrationTransactionRecord;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.event.events.entity.LivingEntityUseBlockEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UseBlockObjectiveTask extends CountObjectiveTask {
/* 19 */   public static final BuilderCodec<UseBlockObjectiveTask> CODEC = BuilderCodec.builder(UseBlockObjectiveTask.class, UseBlockObjectiveTask::new, CountObjectiveTask.CODEC)
/* 20 */     .build();
/*    */   
/*    */   public UseBlockObjectiveTask(@Nonnull UseBlockObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/* 23 */     super((CountObjectiveTaskAsset)asset, taskSetIndex, taskIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UseBlockObjectiveTask() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UseBlockObjectiveTaskAsset getAsset() {
/* 32 */     return (UseBlockObjectiveTaskAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected TransactionRecord[] setup0(@Nonnull Objective objective, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 38 */     this.eventRegistry.register(LivingEntityUseBlockEvent.class, world.getName(), event -> {
/*    */           BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(event.getBlockType());
/*    */           if (blockType == null) {
/*    */             return;
/*    */           }
/*    */           String baseItem = blockType.getItem().getId();
/*    */           if (!getAsset().getBlockTagOrItemIdField().isBlockTypeIncluded(baseItem)) {
/*    */             return;
/*    */           }
/*    */           Ref<EntityStore> entityRef = event.getRef();
/*    */           Store<EntityStore> entityStore = entityRef.getStore();
/*    */           Player playerComponent = (Player)entityStore.getComponent(entityRef, Player.getComponentType());
/*    */           if (playerComponent == null) {
/*    */             return;
/*    */           }
/*    */           UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(entityRef, UUIDComponent.getComponentType());
/*    */           assert uuidComponent != null;
/*    */           if (!objective.getActivePlayerUUIDs().contains(uuidComponent.getUuid()))
/*    */             return; 
/*    */           increaseTaskCompletion(store, entityRef, 1, objective);
/*    */         });
/* 59 */     return RegistrationTransactionRecord.wrap(this.eventRegistry);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 65 */     return "UseBlockObjectiveTask{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\task\UseBlockObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */