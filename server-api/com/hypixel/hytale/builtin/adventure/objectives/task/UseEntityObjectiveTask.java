/*    */ package com.hypixel.hytale.builtin.adventure.objectives.task;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.DialogPage;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CountObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.UseEntityObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionRecord;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.UseEntityTransactionRecord;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UseEntityObjectiveTask
/*    */   extends CountObjectiveTask
/*    */ {
/*    */   public static final BuilderCodec<UseEntityObjectiveTask> CODEC;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(UseEntityObjectiveTask.class, UseEntityObjectiveTask::new, CountObjectiveTask.CODEC).append(new KeyedCodec("NpcUUIDs", (Codec)new ArrayCodec((Codec)Codec.UUID_BINARY, x$0 -> new UUID[x$0])), (useEntityObjectiveTask, uuids) -> { useEntityObjectiveTask.npcUUIDs.clear(); Collections.addAll(useEntityObjectiveTask.npcUUIDs, uuids); }useEntityObjectiveTask -> (UUID[])useEntityObjectiveTask.npcUUIDs.toArray(())).add()).build();
/*    */   } @Nonnull
/* 39 */   protected Set<UUID> npcUUIDs = new HashSet<>();
/*    */ 
/*    */   
/*    */   public UseEntityObjectiveTask(@Nonnull UseEntityObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/* 43 */     super((CountObjectiveTaskAsset)asset, taskSetIndex, taskIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UseEntityObjectiveTaskAsset getAsset() {
/* 52 */     return (UseEntityObjectiveTaskAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected TransactionRecord[] setup0(@Nonnull Objective objective, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 58 */     UUID objectiveUUID = objective.getObjectiveUUID();
/* 59 */     ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/* 60 */     String taskId = getAsset().getTaskId();
/*    */     
/* 62 */     for (UUID playerUUID : objective.getActivePlayerUUIDs()) objectiveDataStore.addEntityTaskForPlayer(playerUUID, taskId, objectiveUUID);
/*    */     
/* 64 */     return TransactionRecord.appendTransaction(null, (TransactionRecord)new UseEntityTransactionRecord(objectiveUUID, taskId));
/*    */   }
/*    */   
/*    */   public boolean increaseTaskCompletion(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, int qty, @Nonnull Objective objective, @Nonnull PlayerRef playerRef, UUID npcUUID) {
/* 68 */     if (!this.npcUUIDs.add(npcUUID)) {
/* 69 */       playerRef.sendMessage(Message.translation("server.modules.objective.task.alreadyInteractedWithNPC"));
/* 70 */       return false;
/*    */     } 
/*    */     
/* 73 */     increaseTaskCompletion(store, ref, qty, objective);
/*    */     
/* 75 */     if (isComplete()) {
/* 76 */       UseEntityObjectiveTaskAsset.DialogOptions dialogOptions = getAsset().getDialogOptions();
/* 77 */       if (dialogOptions != null) {
/* 78 */         Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 79 */         assert playerComponent != null;
/*    */         
/* 81 */         playerRef.sendMessage(Message.join(new Message[] { Message.translation(dialogOptions.getEntityNameKey()), Message.raw(": "), Message.translation(dialogOptions.getDialogKey()) }));
/* 82 */         playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new DialogPage(playerRef, dialogOptions));
/*    */       } 
/*    */     } 
/*    */     
/* 86 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 92 */     return "UseEntityObjectiveTask{npcUUIDs=" + String.valueOf(this.npcUUIDs) + "} " + super
/*    */       
/* 94 */       .toString();
/*    */   }
/*    */   
/*    */   protected UseEntityObjectiveTask() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\task\UseEntityObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */