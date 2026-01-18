/*     */ package com.hypixel.hytale.builtin.adventure.objectives.task;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.BlockTagOrItemIdField;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CountObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.GatherObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.event.events.entity.LivingEntityInventoryChangeEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class GatherObjectiveTask extends CountObjectiveTask {
/*  24 */   public static final BuilderCodec<GatherObjectiveTask> CODEC = BuilderCodec.builder(GatherObjectiveTask.class, GatherObjectiveTask::new, CountObjectiveTask.CODEC)
/*  25 */     .build();
/*     */   
/*     */   public GatherObjectiveTask(@Nonnull GatherObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/*  28 */     super((CountObjectiveTaskAsset)asset, taskSetIndex, taskIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected GatherObjectiveTask() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public GatherObjectiveTaskAsset getAsset() {
/*  37 */     return (GatherObjectiveTaskAsset)super.getAsset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected TransactionRecord[] setup0(@Nonnull Objective objective, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  44 */     Set<UUID> participatingPlayers = objective.getPlayerUUIDs();
/*  45 */     int countItem = countObjectiveItemInInventories(participatingPlayers, (ComponentAccessor<EntityStore>)store);
/*  46 */     if (areTaskConditionsFulfilled(null, null, participatingPlayers)) {
/*  47 */       this.count = MathUtil.clamp(countItem, 0, getAsset().getCount());
/*  48 */       if (checkCompletion()) {
/*  49 */         consumeTaskConditions(null, null, participatingPlayers);
/*  50 */         this.complete = true;
/*  51 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.eventRegistry.register(LivingEntityInventoryChangeEvent.class, world.getName(), event -> {
/*     */           LivingEntity livingEntity = (LivingEntity)event.getEntity();
/*     */ 
/*     */           
/*     */           if (!(livingEntity instanceof Player)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*     */           Ref<EntityStore> ref = livingEntity.getReference();
/*     */ 
/*     */           
/*     */           World refWorld = ((EntityStore)store.getExternalData()).getWorld();
/*     */ 
/*     */           
/*     */           refWorld.execute(());
/*     */         });
/*     */ 
/*     */     
/*  76 */     return RegistrationTransactionRecord.wrap(this.eventRegistry);
/*     */   }
/*     */   
/*     */   private int countObjectiveItemInInventories(@Nonnull Set<UUID> participatingPlayers, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  80 */     int count = 0;
/*  81 */     BlockTagOrItemIdField blockTypeOrSet = getAsset().getBlockTagOrItemIdField();
/*  82 */     for (UUID playerUUID : participatingPlayers) {
/*     */       
/*  84 */       PlayerRef playerRefComponent = Universe.get().getPlayer(playerUUID);
/*  85 */       if (playerRefComponent == null)
/*     */         continue; 
/*  87 */       Ref<EntityStore> playerRef = playerRefComponent.getReference();
/*  88 */       if (playerRef == null || !playerRef.isValid())
/*     */         continue; 
/*  90 */       Player playerComponent = (Player)componentAccessor.getComponent(playerRef, Player.getComponentType());
/*  91 */       assert playerComponent != null;
/*     */       
/*  93 */       CombinedItemContainer inventory = playerComponent.getInventory().getCombinedHotbarFirst();
/*  94 */       count += inventory.countItemStacks(itemStack -> blockTypeOrSet.isBlockTypeIncluded(itemStack.getItemId()));
/*     */     } 
/*     */     
/*  97 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 103 */     return "GatherObjectiveTask{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\task\GatherObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */