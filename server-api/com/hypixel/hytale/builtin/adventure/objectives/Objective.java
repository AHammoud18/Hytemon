/*     */ package com.hypixel.hytale.builtin.adventure.objectives;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLineAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.TaskSet;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveHistoryData;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveLineHistoryData;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionRecord;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionUtil;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Objective;
/*     */ import com.hypixel.hytale.protocol.ObjectiveTask;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.assets.TrackOrUpdateObjective;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Objective
/*     */   implements NetworkSerializable<Objective>
/*     */ {
/*     */   public static final BuilderCodec<Objective> CODEC;
/*     */   protected UUID objectiveUUID;
/*     */   protected String objectiveId;
/*     */   @Nullable
/*     */   protected ObjectiveLineHistoryData objectiveLineHistoryData;
/*     */   protected ObjectiveHistoryData objectiveHistoryData;
/*     */   protected Set<UUID> playerUUIDs;
/*     */   
/*     */   static {
/*  90 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Objective.class, Objective::new).append(new KeyedCodec("ObjectiveUUID", (Codec)Codec.UUID_BINARY), (objective, uuid) -> objective.objectiveUUID = uuid, objective -> objective.objectiveUUID).add()).append(new KeyedCodec("ObjectiveId", (Codec)Codec.STRING), (objective, s) -> objective.objectiveId = s, objective -> objective.objectiveId).add()).append(new KeyedCodec("ObjectiveLineData", (Codec)ObjectiveLineHistoryData.CODEC), (objective, objectiveLineData) -> objective.objectiveLineHistoryData = objectiveLineData, objective -> objective.objectiveLineHistoryData).add()).append(new KeyedCodec("ObjectiveData", (Codec)ObjectiveHistoryData.CODEC), (objective, objectiveHistoryData) -> objective.objectiveHistoryData = objectiveHistoryData, objective -> objective.objectiveHistoryData).add()).append(new KeyedCodec("Players", (Codec)new ArrayCodec((Codec)Codec.UUID_STRING, x$0 -> new UUID[x$0])), (objective, o) -> { objective.playerUUIDs = new HashSet<>(); Collections.addAll(objective.playerUUIDs, o); }objective -> (UUID[])objective.playerUUIDs.toArray(())).add()).append(new KeyedCodec("CurrentTasks", (Codec)new ArrayCodec((Codec)ObjectiveTask.CODEC, x$0 -> new ObjectiveTask[x$0])), (objective, aObjectiveTasks) -> objective.currentTasks = aObjectiveTasks, objective -> objective.currentTasks).add()).append(new KeyedCodec("CurrentTaskSetIndex", (Codec)Codec.INTEGER), (objective, integer) -> objective.currentTaskSetIndex = integer.intValue(), objective -> Integer.valueOf(objective.currentTaskSetIndex)).add()).append(new KeyedCodec("WorldUUID", (Codec)Codec.UUID_BINARY), (objective, s) -> objective.worldUUID = s, objective -> objective.worldUUID).add()).append(new KeyedCodec("ObjectiveItemStarter", (Codec)ItemStack.CODEC), (objective, itemStack) -> objective.objectiveItemStarter = itemStack, objective -> objective.objectiveItemStarter).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 104 */   protected Set<UUID> activePlayerUUIDs = ConcurrentHashMap.newKeySet();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ObjectiveTask[] currentTasks;
/*     */   
/*     */   protected int currentTaskSetIndex;
/*     */   
/*     */   protected boolean completed;
/*     */   
/*     */   protected UUID worldUUID;
/*     */   
/*     */   @Nullable
/*     */   protected UUID markerUUID;
/*     */   
/*     */   protected boolean dirty;
/*     */   
/*     */   protected ItemStack objectiveItemStarter;
/*     */ 
/*     */   
/*     */   public Objective(@Nonnull ObjectiveAsset asset, @Nullable UUID objectiveUUID, @Nonnull Set<UUID> playerUUIDs, @Nonnull UUID worldUUID, @Nullable UUID markerUUID) {
/* 125 */     this.objectiveId = asset.getId();
/* 126 */     this.currentTaskSetIndex = 0;
/* 127 */     this.playerUUIDs = playerUUIDs;
/* 128 */     this.worldUUID = worldUUID;
/* 129 */     this.objectiveUUID = (objectiveUUID == null) ? UUID.randomUUID() : objectiveUUID;
/* 130 */     this.markerUUID = markerUUID;
/* 131 */     this.objectiveHistoryData = new ObjectiveHistoryData(asset.getId(), asset.getCategory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UUID getObjectiveUUID() {
/* 139 */     return this.objectiveUUID;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getObjectiveId() {
/* 144 */     return this.objectiveId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ObjectiveAsset getObjectiveAsset() {
/* 149 */     return (ObjectiveAsset)ObjectiveAsset.getAssetMap().getAsset(this.objectiveId);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ObjectiveLineHistoryData getObjectiveLineHistoryData() {
/* 154 */     return this.objectiveLineHistoryData;
/*     */   }
/*     */   
/*     */   public void setObjectiveLineHistoryData(@Nullable ObjectiveLineHistoryData objectiveLineHistoryData) {
/* 158 */     this.objectiveLineHistoryData = objectiveLineHistoryData;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ObjectiveHistoryData getObjectiveHistoryData() {
/* 163 */     return this.objectiveHistoryData;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ObjectiveLineAsset getObjectiveLineAsset() {
/* 168 */     if (this.objectiveLineHistoryData == null) return null; 
/* 169 */     return (ObjectiveLineAsset)ObjectiveLineAsset.getAssetMap().getAsset(this.objectiveLineHistoryData.getId());
/*     */   }
/*     */   
/*     */   public Set<UUID> getPlayerUUIDs() {
/* 173 */     return this.playerUUIDs;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<UUID> getActivePlayerUUIDs() {
/* 178 */     return this.activePlayerUUIDs;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ObjectiveTask[] getCurrentTasks() {
/* 183 */     return this.currentTasks;
/*     */   }
/*     */   
/*     */   public int getCurrentTaskSetIndex() {
/* 187 */     return this.currentTaskSetIndex;
/*     */   }
/*     */   
/*     */   public String getCurrentDescription() {
/* 191 */     ObjectiveAsset objectiveAsset = Objects.<ObjectiveAsset>requireNonNull(getObjectiveAsset());
/* 192 */     TaskSet currentTaskSet = objectiveAsset.getTaskSets()[this.currentTaskSetIndex];
/* 193 */     return (currentTaskSet.getDescriptionId() != null) ? currentTaskSet.getDescriptionKey(this.objectiveId, this.currentTaskSetIndex) : objectiveAsset.getDescriptionKey();
/*     */   }
/*     */   
/*     */   public boolean isCompleted() {
/* 197 */     return this.completed;
/*     */   }
/*     */   
/*     */   public UUID getWorldUUID() {
/* 201 */     return this.worldUUID;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public UUID getMarkerUUID() {
/* 206 */     return this.markerUUID;
/*     */   }
/*     */   
/*     */   public boolean isDirty() {
/* 210 */     return this.dirty;
/*     */   }
/*     */   
/*     */   public ItemStack getObjectiveItemStarter() {
/* 214 */     return this.objectiveItemStarter;
/*     */   }
/*     */   
/*     */   public void setObjectiveItemStarter(@Nonnull ItemStack objectiveItemStarter) {
/* 218 */     this.objectiveItemStarter = objectiveItemStarter;
/*     */   }
/*     */   
/*     */   public boolean setup(@Nonnull Store<EntityStore> componentAccessor) {
/* 222 */     ObjectiveAsset objectiveAsset = Objects.<ObjectiveAsset>requireNonNull(getObjectiveAsset());
/* 223 */     ObjectivePlugin objectiveModule = ObjectivePlugin.get();
/*     */     
/* 225 */     TaskSet[] taskSets = objectiveAsset.getTaskSets();
/* 226 */     if (this.currentTaskSetIndex >= taskSets.length) {
/* 227 */       objectiveModule.getLogger().at(Level.WARNING).log("Current taskSet index is higher than total number of taskSets for objective %s", this.objectiveId);
/* 228 */       return false;
/*     */     } 
/*     */     
/* 231 */     ObjectiveTaskAsset[] tasks = taskSets[this.currentTaskSetIndex].getTasks();
/* 232 */     ObjectiveTask[] newTasks = new ObjectiveTask[tasks.length];
/* 233 */     for (int i = 0; i < tasks.length; i++) {
/* 234 */       newTasks[i] = objectiveModule.createTask(tasks[i], this.currentTaskSetIndex, i);
/*     */     }
/* 236 */     this.currentTasks = newTasks;
/*     */     
/* 238 */     return setupCurrentTasks(componentAccessor);
/*     */   }
/*     */   
/*     */   public boolean setupCurrentTasks(@Nonnull Store<EntityStore> store) {
/* 242 */     for (ObjectiveTask task : this.currentTasks) {
/* 243 */       if (!task.isComplete()) {
/*     */         
/* 245 */         TransactionRecord[] taskTransactions = task.setup(this, store);
/* 246 */         if (taskTransactions != null)
/*     */         {
/* 248 */           if (TransactionUtil.anyFailed(taskTransactions)) {
/* 249 */             ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Failed to setup objective tasks, transaction records:%s", Arrays.toString((Object[])taskTransactions));
/* 250 */             for (ObjectiveTask taskSetup : this.currentTasks) {
/* 251 */               taskSetup.revertTransactionRecords();
/*     */ 
/*     */               
/* 254 */               if (taskSetup == task)
/*     */                 break; 
/* 256 */             }  return false;
/*     */           }  } 
/*     */       } 
/* 259 */     }  return true;
/*     */   }
/*     */   
/*     */   public boolean checkTaskSetCompletion(@Nonnull Store<EntityStore> store) {
/* 263 */     for (ObjectiveTask task : this.currentTasks) {
/* 264 */       if (!task.isComplete()) return false;
/*     */     
/*     */     } 
/* 267 */     taskSetComplete(store);
/* 268 */     return true;
/*     */   }
/*     */   
/*     */   protected void taskSetComplete(@Nonnull Store<EntityStore> store) {
/* 272 */     ObjectiveAsset objectiveAsset = Objects.<ObjectiveAsset>requireNonNull(getObjectiveAsset());
/*     */     
/* 274 */     this.currentTaskSetIndex++;
/* 275 */     TaskSet[] taskSets = objectiveAsset.getTaskSets();
/* 276 */     if (this.currentTaskSetIndex < taskSets.length) {
/* 277 */       if (!setup(store)) {
/* 278 */         taskSetComplete(store);
/*     */         
/*     */         return;
/*     */       } 
/* 282 */       TrackOrUpdateObjective trackObjectivePacket = new TrackOrUpdateObjective(toPacket());
/*     */       
/* 284 */       forEachParticipant((participantReference, message, trackOrUpdateObjective) -> {
/*     */             PlayerRef playerRefComponent = (PlayerRef)store.getComponent(participantReference, PlayerRef.getComponentType());
/*     */             
/*     */             if (playerRefComponent != null) {
/*     */               playerRefComponent.sendMessage(message);
/*     */               playerRefComponent.getPacketHandler().writeNoCache((Packet)trackOrUpdateObjective);
/*     */             } 
/* 291 */           }getTaskInfoMessage(), trackObjectivePacket);
/*     */       
/* 293 */       checkTaskSetCompletion(store);
/*     */       
/*     */       return;
/*     */     } 
/* 297 */     complete(store);
/*     */   }
/*     */   
/*     */   public void complete(@Nonnull Store<EntityStore> store) {
/* 301 */     ObjectiveAsset objectiveAsset = Objects.<ObjectiveAsset>requireNonNull(getObjectiveAsset());
/*     */     
/* 303 */     forEachParticipant((participantReference, message) -> {
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(participantReference, PlayerRef.getComponentType());
/*     */           if (playerRefComponent != null) {
/*     */             playerRefComponent.sendMessage(message);
/*     */           }
/* 308 */         }Message.translation("server.modules.objective.completed")
/* 309 */         .param("title", Message.translation(objectiveAsset.getTitleKey())));
/*     */     
/* 311 */     ObjectivePlugin objectiveModule = ObjectivePlugin.get();
/*     */     
/* 313 */     ObjectiveCompletionAsset[] completionHandlerAssets = objectiveAsset.getCompletionHandlers();
/* 314 */     if (completionHandlerAssets != null) {
/* 315 */       for (ObjectiveCompletionAsset objectiveCompletionAsset : completionHandlerAssets) {
/* 316 */         objectiveModule.createCompletion(objectiveCompletionAsset).handle(this, (ComponentAccessor)store);
/*     */       }
/*     */     }
/*     */     
/* 320 */     this.completed = true;
/*     */     
/* 322 */     objectiveModule.objectiveCompleted(this, store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 330 */     for (ObjectiveTask currentTask : this.currentTasks) {
/* 331 */       currentTask.revertTransactionRecords();
/*     */     }
/*     */   }
/*     */   
/*     */   public void unload() {
/* 336 */     for (ObjectiveTask currentTask : this.currentTasks) {
/* 337 */       currentTask.unloadTransactionRecords();
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Message getTaskInfoMessage() {
/* 343 */     Message info = Message.translation(getCurrentDescription());
/* 344 */     for (ObjectiveTask task : this.currentTasks) {
/* 345 */       info.insert("\n").insert(task.getInfoMessage(this));
/*     */     }
/*     */     
/* 348 */     return info;
/*     */   }
/*     */   
/*     */   public void reloadObjectiveAsset(@Nonnull Map<String, ObjectiveAsset> reloadedAssets) {
/* 352 */     ObjectiveTaskAsset[] taskAssets = checkPossibleAssetReload(reloadedAssets);
/* 353 */     if (taskAssets == null)
/*     */       return; 
/* 355 */     World world = Universe.get().getWorld(this.worldUUID);
/* 356 */     if (world == null)
/*     */       return; 
/* 358 */     world.execute(() -> {
/*     */           Store<EntityStore> store = world.getEntityStore().getStore();
/*     */           ObjectiveTask[] newTasks = setupAndUpdateTasks(taskAssets, store);
/*     */           if (newTasks == null) {
/*     */             return;
/*     */           }
/*     */           revertRemovedTasks(newTasks);
/*     */           this.currentTasks = newTasks;
/*     */           for (ObjectiveTask currentTask : this.currentTasks) {
/*     */             currentTask.assetChanged(this);
/*     */           }
/*     */           if (checkTaskSetCompletion(store)) {
/*     */             return;
/*     */           }
/*     */           TrackOrUpdateObjective updatePacket = new TrackOrUpdateObjective(toPacket());
/*     */           forEachParticipant((), updatePacket);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ObjectiveTaskAsset[] checkPossibleAssetReload(@Nonnull Map<String, ObjectiveAsset> reloadedAssets) {
/* 385 */     ObjectiveLineAsset objectiveLineAsset = getObjectiveLineAsset();
/* 386 */     if (this.objectiveLineHistoryData != null && objectiveLineAsset == null) {
/* 387 */       cancel();
/* 388 */       return null;
/*     */     } 
/*     */     
/* 391 */     ObjectiveAsset objectiveAsset = reloadedAssets.get(this.objectiveId);
/* 392 */     if (objectiveAsset == null) {
/* 393 */       return null;
/*     */     }
/*     */     
/* 396 */     TaskSet[] taskSets = objectiveAsset.getTaskSets();
/* 397 */     if (this.currentTaskSetIndex > taskSets.length) {
/*     */       
/* 399 */       cancel();
/* 400 */       return null;
/*     */     } 
/*     */     
/* 403 */     return taskSets[this.currentTaskSetIndex].getTasks();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ObjectiveTask[] setupAndUpdateTasks(@Nonnull ObjectiveTaskAsset[] taskAssets, @Nonnull Store<EntityStore> store) {
/* 409 */     ObjectiveTask[] newTasks = new ObjectiveTask[taskAssets.length];
/*     */     
/* 411 */     for (int i = 0; i < taskAssets.length; i++) {
/* 412 */       ObjectiveTaskAsset taskAsset = taskAssets[i];
/*     */       
/* 414 */       ObjectiveTask objectiveTask = findMatchingObjectiveTask(taskAsset);
/* 415 */       if (objectiveTask != null) {
/* 416 */         objectiveTask.setAsset(taskAsset);
/* 417 */         newTasks[i] = objectiveTask;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 422 */         ObjectiveTask newTask = newTasks[i] = ObjectivePlugin.get().createTask(taskAsset, this.currentTaskSetIndex, i);
/* 423 */         TransactionRecord[] transactionRecords = newTask.setup(this, store);
/* 424 */         if (TransactionUtil.anyFailed(transactionRecords)) {
/* 425 */           cancelReload(newTasks);
/* 426 */           return null;
/*     */         } 
/*     */       } 
/*     */     } 
/* 430 */     return newTasks;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ObjectiveTask findMatchingObjectiveTask(@Nonnull ObjectiveTaskAsset taskAsset) {
/* 435 */     for (ObjectiveTask objectiveTask : this.currentTasks) {
/* 436 */       if (objectiveTask.getAsset().matchesAsset(taskAsset)) return objectiveTask;
/*     */     
/*     */     } 
/* 439 */     return null;
/*     */   }
/*     */   
/*     */   private void cancelReload(@Nonnull ObjectiveTask[] newTasks) {
/* 443 */     for (ObjectiveTask taskToRevert : newTasks) {
/* 444 */       if (taskToRevert != null) {
/* 445 */         taskToRevert.revertTransactionRecords();
/*     */       }
/*     */     } 
/* 448 */     cancel();
/* 449 */     this.currentTasks = null;
/*     */   }
/*     */   
/*     */   private void revertRemovedTasks(@Nonnull ObjectiveTask[] newTasks) {
/* 453 */     for (ObjectiveTask objectiveTask : this.currentTasks) {
/* 454 */       boolean foundMatchingTask = false;
/*     */       
/* 456 */       for (ObjectiveTask newTask : newTasks) {
/* 457 */         if (newTask.equals(objectiveTask)) {
/* 458 */           foundMatchingTask = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 463 */       if (!foundMatchingTask) objectiveTask.revertTransactionRecords(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void forEachParticipant(@Nonnull Consumer<Ref<EntityStore>> consumer) {
/* 468 */     for (UUID playerUUID : this.playerUUIDs) {
/* 469 */       PlayerRef playerRef = Universe.get().getPlayer(playerUUID);
/* 470 */       if (playerRef == null)
/* 471 */         continue;  consumer.accept(playerRef.getReference());
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> void forEachParticipant(@Nonnull BiConsumer<Ref<EntityStore>, T> consumer, T meta) {
/* 476 */     for (UUID playerUUID : this.playerUUIDs) {
/* 477 */       PlayerRef playerRef = Universe.get().getPlayer(playerUUID);
/* 478 */       if (playerRef == null)
/* 479 */         continue;  consumer.accept(playerRef.getReference(), meta);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T, U> void forEachParticipant(@Nonnull TriConsumer<Ref<EntityStore>, T, U> consumer, @Nonnull T t, @Nonnull U u) {
/* 484 */     for (UUID playerUUID : this.playerUUIDs) {
/* 485 */       PlayerRef playerRef = Universe.get().getPlayer(playerUUID);
/* 486 */       if (playerRef == null)
/* 487 */         continue;  consumer.accept(playerRef.getReference(), t, u);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vector3d getPosition(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 493 */     UUID entityUUIDToFind = null;
/* 494 */     if (this.markerUUID != null) {
/* 495 */       entityUUIDToFind = this.markerUUID;
/* 496 */     } else if (!this.playerUUIDs.isEmpty()) {
/* 497 */       entityUUIDToFind = this.playerUUIDs.iterator().next();
/*     */     } 
/* 499 */     if (entityUUIDToFind == null) return null;
/*     */     
/* 501 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 502 */     Ref<EntityStore> markerEntityReference = world.getEntityRef(entityUUIDToFind);
/* 503 */     if (markerEntityReference == null || !markerEntityReference.isValid()) return null;
/*     */     
/* 505 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(markerEntityReference, TransformComponent.getComponentType());
/* 506 */     return (transformComponent != null) ? transformComponent.getPosition() : null;
/*     */   }
/*     */   
/*     */   public void addActivePlayerUUID(UUID playerUUID) {
/* 510 */     this.activePlayerUUIDs.add(playerUUID);
/*     */   }
/*     */   
/*     */   public void removeActivePlayerUUID(UUID playerUUID) {
/* 514 */     this.activePlayerUUIDs.remove(playerUUID);
/*     */   }
/*     */   
/*     */   public void markDirty() {
/* 518 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public boolean consumeDirty() {
/* 522 */     boolean previous = this.dirty;
/* 523 */     this.dirty = false;
/* 524 */     return previous;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Objective toPacket() {
/* 530 */     ObjectiveAsset objectiveAsset = Objects.<ObjectiveAsset>requireNonNull(getObjectiveAsset());
/*     */     
/* 532 */     Objective packet = new Objective();
/* 533 */     packet.objectiveUuid = this.objectiveUUID;
/* 534 */     packet.objectiveTitleKey = objectiveAsset.getTitleKey();
/* 535 */     packet.objectiveDescriptionKey = getCurrentDescription();
/*     */ 
/*     */     
/* 538 */     if (this.objectiveLineHistoryData != null) packet.objectiveLineId = this.objectiveLineHistoryData.getId();
/*     */     
/* 540 */     packet.tasks = new ObjectiveTask[this.currentTasks.length];
/* 541 */     for (int i = 0; i < this.currentTasks.length; i++) {
/* 542 */       packet.tasks[i] = (ObjectiveTask)this.currentTasks[i].toPacket(this);
/*     */     }
/*     */     
/* 545 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 551 */     return "Objective{objectiveUUID=" + String.valueOf(this.objectiveUUID) + ", objectiveId='" + this.objectiveId + "', objectiveLineHistoryData=" + String.valueOf(this.objectiveLineHistoryData) + ", objectiveHistoryData=" + String.valueOf(this.objectiveHistoryData) + ", playerUUIDs=" + String.valueOf(this.playerUUIDs) + ", activePlayerUUIDs=" + String.valueOf(this.activePlayerUUIDs) + ", currentTasks=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 558 */       Arrays.toString((Object[])this.currentTasks) + ", currentTaskSetIndex=" + this.currentTaskSetIndex + ", completed=" + this.completed + ", worldUUID=" + String.valueOf(this.worldUUID) + ", markerUUID=" + String.valueOf(this.markerUUID) + ", dirty=" + this.dirty + "}";
/*     */   }
/*     */   
/*     */   protected Objective() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\Objective.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */