/*     */ package com.hypixel.hytale.builtin.adventure.objectives.task;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionRecord;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionUtil;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.event.IEventRegistry;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.ObjectiveTask;
/*     */ import com.hypixel.hytale.protocol.packets.assets.UpdateObjectiveTask;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializer;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class ObjectiveTask implements NetworkSerializer<Objective, ObjectiveTask> {
/*  41 */   public static final CodecMapCodec<ObjectiveTask> CODEC = new CodecMapCodec("Type");
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
/*     */   public static final BuilderCodec<ObjectiveTask> BASE_CODEC;
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
/*     */   protected ObjectiveTaskAsset asset;
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
/*     */   static {
/*  76 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ObjectiveTask.class).append(new KeyedCodec("Task", (Codec)ObjectiveTaskAsset.CODEC), (aObjectiveTask, objectiveTaskAsset) -> aObjectiveTask.asset = objectiveTaskAsset, aObjectiveTask -> aObjectiveTask.asset).add()).append(new KeyedCodec("Complete", (Codec)Codec.BOOLEAN), (aObjectiveTask, aBoolean) -> aObjectiveTask.complete = aBoolean.booleanValue(), aObjectiveTask -> Boolean.valueOf(aObjectiveTask.complete)).add()).append(new KeyedCodec("TransactionRecords", (Codec)new ArrayCodec((Codec)TransactionRecord.CODEC, x$0 -> new TransactionRecord[x$0])), (objectiveTask, transactionRecords) -> objectiveTask.serializedTransactionRecords = transactionRecords, objectiveTask -> objectiveTask.serializedTransactionRecords).add()).append(new KeyedCodec("TaskIndex", (Codec)Codec.INTEGER), (objectiveTask, integer) -> objectiveTask.taskIndex = integer.intValue(), objectiveTask -> Integer.valueOf(objectiveTask.taskIndex)).add()).append(new KeyedCodec("Markers", (Codec)ProtocolCodecs.MARKER_ARRAY), (objectiveTask, markers) -> { objectiveTask.markers.clear(); Collections.addAll(objectiveTask.markers, markers); }objectiveTask -> (MapMarker[])objectiveTask.markers.toArray(())).add()).append(new KeyedCodec("TaskSetIndex", (Codec)Codec.INTEGER), (objectiveTask, integer) -> objectiveTask.taskSetIndex = integer.intValue(), objectiveTask -> Integer.valueOf(objectiveTask.taskSetIndex)).add()).build();
/*     */   }
/*     */   protected boolean complete = false;
/*     */   @Nullable
/*     */   protected EventRegistry eventRegistry;
/*     */   @Nullable
/*     */   protected TransactionRecord[] serializedTransactionRecords;
/*     */   @Nullable
/*     */   protected TransactionRecord[] nonSerializedTransactionRecords;
/*     */   protected int taskIndex;
/*     */   @Nonnull
/*  87 */   protected List<MapMarker> markers = (List<MapMarker>)new ObjectArrayList();
/*     */   
/*     */   protected int taskSetIndex;
/*     */   protected ObjectiveTaskRef<? extends ObjectiveTask> taskRef;
/*     */   
/*     */   public ObjectiveTask(@Nonnull ObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/*  93 */     this.asset = asset;
/*  94 */     this.taskIndex = taskIndex;
/*  95 */     this.taskSetIndex = taskSetIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ObjectiveTaskAsset getAsset() {
/* 103 */     return this.asset;
/*     */   }
/*     */   
/*     */   public void setAsset(@Nonnull ObjectiveTaskAsset asset) {
/* 107 */     this.asset = asset;
/*     */   }
/*     */   
/*     */   public boolean isComplete() {
/* 111 */     return this.complete;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TransactionRecord[] getSerializedTransactionRecords() {
/* 116 */     return this.serializedTransactionRecords;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TransactionRecord[] getNonSerializedTransactionRecords() {
/* 121 */     return this.nonSerializedTransactionRecords;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Message getInfoMessage(@Nonnull Objective objective) {
/* 126 */     return Message.translation(this.asset.getDescriptionKey(objective.getObjectiveId(), this.taskSetIndex, this.taskIndex));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<MapMarker> getMarkers() {
/* 131 */     return this.markers;
/*     */   }
/*     */   
/*     */   public void addMarker(@Nonnull MapMarker marker) {
/* 135 */     this.markers.add(marker);
/*     */   }
/*     */   
/*     */   public void removeMarker(String id) {
/* 139 */     for (MapMarker marker : this.markers) {
/* 140 */       if (!marker.id.equals(id))
/* 141 */         continue;  this.markers.remove(marker);
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public final TransactionRecord[] setup(@Nonnull Objective objective, @Nonnull Store<EntityStore> store) {
/* 153 */     World world = Universe.get().getWorld(objective.getWorldUUID());
/* 154 */     if (world == null) {
/* 155 */       String transactionMessage = "This World doesn't exist in this Universe: " + String.valueOf(objective.getWorldUUID());
/* 156 */       return TransactionRecord.appendFailedTransaction(this.nonSerializedTransactionRecords, (TransactionRecord)new WorldTransactionRecord(), transactionMessage);
/*     */     } 
/*     */     
/* 159 */     if (this.eventRegistry != null) throw new IllegalStateException("ObjectiveTask.eventRegistry is not null, setup() shouldn't be run more than once!");
/*     */     
/* 161 */     this.eventRegistry = new EventRegistry(new CopyOnWriteArrayList(), () -> true, null, (IEventRegistry)world.getEventRegistry());
/*     */     
/* 163 */     Vector3i[] mapMarkerPositions = this.asset.getMapMarkers();
/* 164 */     if (mapMarkerPositions != null) {
/* 165 */       String objectiveUUIDString = objective.getObjectiveUUID().toString();
/*     */       
/* 167 */       for (int i = 0; i < mapMarkerPositions.length; i++) {
/* 168 */         Vector3i mapMarkerPosition = mapMarkerPositions[i];
/* 169 */         addMarker(new MapMarker("ObjectiveMarker_" + objectiveUUIDString + "_" + i, "Objective", "Home.png", PositionUtil.toTransformPacket(new Transform(mapMarkerPosition)), null));
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     this.taskRef = new ObjectiveTaskRef<>(objective.getObjectiveUUID(), this);
/* 174 */     registerTaskRef();
/*     */     
/* 176 */     TransactionRecord[] transactionRecords = setup0(objective, world, store);
/*     */     
/* 178 */     if (transactionRecords == null) return null;
/*     */     
/* 180 */     int serializedCount = 0;
/* 181 */     for (TransactionRecord transactionRecord : transactionRecords) {
/* 182 */       if (transactionRecord.shouldBeSerialized()) {
/* 183 */         serializedCount++;
/*     */       }
/*     */     } 
/*     */     
/* 187 */     this.serializedTransactionRecords = new TransactionRecord[serializedCount];
/* 188 */     this.nonSerializedTransactionRecords = new TransactionRecord[transactionRecords.length - serializedCount];
/*     */     
/* 190 */     int serializedIndex = 0;
/* 191 */     int nonSerializedIndex = 0;
/* 192 */     for (TransactionRecord transactionRecord : transactionRecords) {
/* 193 */       if (transactionRecord.shouldBeSerialized()) {
/* 194 */         this.serializedTransactionRecords[serializedIndex++] = transactionRecord;
/*     */       } else {
/* 196 */         this.nonSerializedTransactionRecords[nonSerializedIndex++] = transactionRecord;
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     return transactionRecords;
/*     */   }
/*     */   
/*     */   public void complete(@Nonnull Objective objective, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 204 */     if (this.complete)
/*     */       return; 
/* 206 */     if (componentAccessor != null) {
/* 207 */       objective.forEachParticipant((participantReference, message) -> {
/*     */             Player playerComponent = (Player)componentAccessor.getComponent(participantReference, Player.getComponentType()); if (playerComponent != null)
/*     */               playerComponent.sendMessage(message); 
/* 210 */           }Message.translation("server.modules.objective.task.completed").insert(getInfoMessage(objective)));
/*     */     }
/*     */     
/* 213 */     this.markers.clear();
/*     */     
/* 215 */     this.complete = true;
/* 216 */     completeTransactionRecords();
/*     */   }
/*     */   
/*     */   private void registerTaskRef() {
/* 220 */     ObjectivePlugin.get().getObjectiveDataStore().addTaskRef(this.taskRef);
/*     */   }
/*     */   
/*     */   private void unregisterTaskRef() {
/* 224 */     ObjectivePlugin.get().getObjectiveDataStore().removeTaskRef(this.taskRef);
/*     */   }
/*     */   
/*     */   public void completeTransactionRecords() {
/* 228 */     TransactionUtil.completeAll(this.serializedTransactionRecords);
/* 229 */     this.serializedTransactionRecords = null;
/*     */     
/* 231 */     TransactionUtil.completeAll(this.nonSerializedTransactionRecords);
/* 232 */     this.nonSerializedTransactionRecords = null;
/*     */     
/* 234 */     shutdownEventRegistry();
/* 235 */     unregisterTaskRef();
/*     */   }
/*     */   
/*     */   public void revertTransactionRecords() {
/* 239 */     TransactionUtil.revertAll(this.serializedTransactionRecords);
/* 240 */     this.serializedTransactionRecords = null;
/*     */     
/* 242 */     TransactionUtil.revertAll(this.nonSerializedTransactionRecords);
/* 243 */     this.nonSerializedTransactionRecords = null;
/*     */     
/* 245 */     shutdownEventRegistry();
/* 246 */     unregisterTaskRef();
/*     */   }
/*     */   
/*     */   public void unloadTransactionRecords() {
/* 250 */     TransactionUtil.unloadAll(this.serializedTransactionRecords);
/* 251 */     this.serializedTransactionRecords = null;
/*     */     
/* 253 */     TransactionUtil.unloadAll(this.nonSerializedTransactionRecords);
/* 254 */     this.nonSerializedTransactionRecords = null;
/*     */     
/* 256 */     shutdownEventRegistry();
/* 257 */     unregisterTaskRef();
/*     */   }
/*     */   
/*     */   private void shutdownEventRegistry() {
/* 261 */     if (this.eventRegistry != null) {
/* 262 */       this.eventRegistry.shutdown();
/* 263 */       this.eventRegistry = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void assetChanged(@Nonnull Objective objective) {
/* 268 */     if (this.complete)
/*     */       return; 
/* 270 */     if (checkCompletion()) {
/* 271 */       consumeTaskConditions(null, null, objective.getActivePlayerUUIDs());
/* 272 */       complete(objective, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendUpdateObjectiveTaskPacket(@Nonnull Objective objective) {
/* 277 */     UpdateObjectiveTask updateObjectiveTaskPacket = new UpdateObjectiveTask(objective.getObjectiveUUID(), this.taskIndex, (ObjectiveTask)toPacket(objective));
/*     */     
/* 279 */     Universe universe = Universe.get();
/* 280 */     for (UUID playerUUID : objective.getActivePlayerUUIDs()) {
/* 281 */       PlayerRef player = universe.getPlayer(playerUUID);
/* 282 */       if (player == null)
/* 283 */         continue;  player.getPacketHandler().writeNoCache((Packet)updateObjectiveTaskPacket);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean areTaskConditionsFulfilled(@Nullable ComponentAccessor<EntityStore> componentAccessor, @Nullable Ref<EntityStore> ref, @Nullable Set<UUID> objectivePlayers) {
/* 288 */     TaskConditionAsset[] taskConditions = this.asset.getTaskConditions();
/* 289 */     if (taskConditions == null) return true;
/*     */     
/* 291 */     for (TaskConditionAsset taskCondition : taskConditions) {
/* 292 */       if (!taskCondition.isConditionFulfilled(componentAccessor, ref, objectivePlayers)) return false;
/*     */     
/*     */     } 
/* 295 */     return true;
/*     */   }
/*     */   
/*     */   public void consumeTaskConditions(@Nullable ComponentAccessor<EntityStore> componentAccessor, @Nullable Ref<EntityStore> ref, @Nonnull Set<UUID> objectivePlayers) {
/* 299 */     TaskConditionAsset[] taskConditions = this.asset.getTaskConditions();
/* 300 */     if (taskConditions == null)
/*     */       return; 
/* 302 */     for (TaskConditionAsset taskCondition : taskConditions) {
/* 303 */       taskCondition.consumeCondition(componentAccessor, ref, objectivePlayers);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 310 */     return "ObjectiveTask{asset=" + String.valueOf(this.asset) + ", complete=" + this.complete + ", eventRegistry=" + String.valueOf(this.eventRegistry) + ", serializedTransactionRecords=" + 
/*     */ 
/*     */ 
/*     */       
/* 314 */       Arrays.toString((Object[])this.serializedTransactionRecords) + ", nonSerializedTransactionRecords=" + 
/* 315 */       Arrays.toString((Object[])this.nonSerializedTransactionRecords) + ", taskIndex=" + this.taskIndex + ", markers=" + String.valueOf(this.markers) + ", taskSetIndex=" + this.taskSetIndex + "}";
/*     */   }
/*     */   
/*     */   protected ObjectiveTask() {}
/*     */   
/*     */   public abstract boolean checkCompletion();
/*     */   
/*     */   @Nullable
/*     */   protected abstract TransactionRecord[] setup0(@Nonnull Objective paramObjective, @Nonnull World paramWorld, @Nonnull Store<EntityStore> paramStore);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\task\ObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */