/*     */ package com.hypixel.hytale.builtin.adventure.objectives;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTaskRef;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.universe.datastore.DataStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ObjectiveDataStore {
/*  22 */   private final Map<UUID, Objective> objectives = new ConcurrentHashMap<>();
/*  23 */   private final Map<UUID, Map<String, Set<UUID>>> entityObjectiveUUIDsPerPlayer = new ConcurrentHashMap<>();
/*     */   
/*     */   @Nonnull
/*     */   private final DataStore<Objective> dataStore;
/*  27 */   private final Map<Class<? extends ObjectiveTask>, Set<ObjectiveTaskRef<? extends ObjectiveTask>>> taskRefByType = new ConcurrentHashMap<>();
/*     */   
/*     */   @Nonnull
/*     */   private final HytaleLogger logger;
/*     */   
/*     */   public ObjectiveDataStore(@Nonnull DataStore<Objective> dataStore) {
/*  33 */     this.dataStore = dataStore;
/*  34 */     this.logger = ObjectivePlugin.get().getLogger();
/*     */   }
/*     */   
/*     */   public Objective getObjective(UUID objectiveUUID) {
/*  38 */     return this.objectives.get(objectiveUUID);
/*     */   }
/*     */   
/*     */   public Map<String, Set<UUID>> getEntityTasksForPlayer(UUID playerUUID) {
/*  42 */     return this.entityObjectiveUUIDsPerPlayer.get(playerUUID);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Collection<Objective> getObjectiveCollection() {
/*  47 */     return this.objectives.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends ObjectiveTask> Set<ObjectiveTaskRef<T>> getTaskRefsForType(Class<T> taskClass) {
/*  52 */     return (Set<ObjectiveTaskRef<T>>)this.taskRefByType.get(taskClass);
/*     */   }
/*     */   
/*     */   public <T extends ObjectiveTask> void addTaskRef(@Nonnull ObjectiveTaskRef<T> taskRef) {
/*  56 */     ((Set<ObjectiveTaskRef<T>>)this.taskRefByType.get(taskRef.getObjectiveTask().getClass())).add(taskRef);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends ObjectiveTask> void removeTaskRef(@Nullable ObjectiveTaskRef<T> taskRef) {
/*  61 */     if (taskRef == null)
/*     */       return; 
/*  63 */     ((Set)this.taskRefByType.get(taskRef.getObjectiveTask().getClass())).remove(taskRef);
/*     */   }
/*     */   
/*     */   public <T extends ObjectiveTask> void registerTaskRef(Class<T> taskClass) {
/*  67 */     this.taskRefByType.put(taskClass, ConcurrentHashMap.newKeySet());
/*     */   }
/*     */   
/*     */   public void saveToDisk(String objectiveId, @Nonnull Objective objective) {
/*  71 */     if (!objective.consumeDirty())
/*     */       return; 
/*  73 */     this.dataStore.save(objectiveId, objective);
/*     */   }
/*     */   
/*     */   public void saveToDiskAllObjectives() {
/*  77 */     for (Map.Entry<UUID, Objective> entry : this.objectives.entrySet()) {
/*  78 */       saveToDisk(((UUID)entry.getKey()).toString(), entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean removeFromDisk(String objectiveId) {
/*     */     try {
/*  84 */       this.dataStore.remove(objectiveId);
/*  85 */     } catch (IOException e) {
/*  86 */       ((HytaleLogger.Api)this.logger.at(Level.WARNING).withCause(e)).log("Failed removal of objective with UUID: %s", objectiveId);
/*  87 */       return false;
/*     */     } 
/*     */     
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addObjective(UUID objectiveUUID, Objective objective) {
/*  94 */     return (this.objectives.putIfAbsent(objectiveUUID, objective) == null);
/*     */   }
/*     */   
/*     */   public void removeObjective(UUID objectiveUUID) {
/*  98 */     this.objectives.remove(objectiveUUID);
/*     */   }
/*     */   
/*     */   public void addEntityTaskForPlayer(UUID playerUUID, String taskId, UUID objectiveUUID) {
/* 102 */     ((Set<UUID>)((Map<String, Set<UUID>>)this.entityObjectiveUUIDsPerPlayer.computeIfAbsent(playerUUID, s -> new ConcurrentHashMap<>())).computeIfAbsent(taskId, s -> ConcurrentHashMap.newKeySet())).add(objectiveUUID);
/*     */   }
/*     */   
/*     */   public void removeEntityTask(UUID objectiveUUID, String taskId) {
/* 106 */     Iterator<Map.Entry<UUID, Map<String, Set<UUID>>>> entityObjectiveUUIDsPerPlayerIterator = this.entityObjectiveUUIDsPerPlayer.entrySet().iterator();
/* 107 */     while (entityObjectiveUUIDsPerPlayerIterator.hasNext()) {
/* 108 */       Map.Entry<UUID, Map<String, Set<UUID>>> entityObjectiveUUIDsEntry = entityObjectiveUUIDsPerPlayerIterator.next();
/* 109 */       Map<String, Set<UUID>> entityObjectiveUUIDs = entityObjectiveUUIDsEntry.getValue();
/*     */       
/* 111 */       Set<UUID> objectiveUUIDs = entityObjectiveUUIDs.get(taskId);
/* 112 */       if (objectiveUUIDs == null)
/*     */         continue; 
/* 114 */       if (!objectiveUUIDs.remove(objectiveUUID))
/*     */         continue; 
/* 116 */       if (objectiveUUIDs.isEmpty()) {
/* 117 */         entityObjectiveUUIDs.remove(taskId);
/*     */       }
/*     */       
/* 120 */       if (entityObjectiveUUIDs.isEmpty()) {
/* 121 */         entityObjectiveUUIDsPerPlayerIterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeEntityTaskForPlayer(UUID objectiveUUID, String taskId, UUID playerUUID) {
/* 127 */     Map<String, Set<UUID>> entityObjectiveUUIDs = this.entityObjectiveUUIDsPerPlayer.get(playerUUID);
/* 128 */     if (entityObjectiveUUIDs == null)
/*     */       return; 
/* 130 */     Set<UUID> objectiveUUIDs = entityObjectiveUUIDs.get(taskId);
/* 131 */     if (objectiveUUIDs == null)
/*     */       return; 
/* 133 */     if (!objectiveUUIDs.remove(objectiveUUID))
/*     */       return; 
/* 135 */     if (objectiveUUIDs.isEmpty()) {
/* 136 */       entityObjectiveUUIDs.remove(taskId);
/*     */     }
/*     */     
/* 139 */     if (entityObjectiveUUIDs.isEmpty()) {
/* 140 */       this.entityObjectiveUUIDsPerPlayer.remove(playerUUID);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Objective loadObjective(@Nonnull UUID objectiveUUID, @Nonnull Store<EntityStore> store) {
/* 146 */     Objective objective = this.objectives.get(objectiveUUID);
/* 147 */     if (objective != null) return objective;
/*     */     
/*     */     try {
/* 150 */       objective = (Objective)this.dataStore.load(objectiveUUID.toString());
/* 151 */     } catch (IOException e) {
/* 152 */       ((HytaleLogger.Api)this.logger.at(Level.WARNING).withCause(e)).log("Unable to load objective with UUID '%s'", objectiveUUID);
/* 153 */       return null;
/*     */     } 
/*     */     
/* 156 */     if (objective == null) {
/* 157 */       this.logger.at(Level.WARNING).log("No objective saved with UUID '%s'", objectiveUUID);
/* 158 */       return null;
/*     */     } 
/*     */     
/* 161 */     String objectiveId = objective.getObjectiveId();
/* 162 */     if (ObjectiveAsset.getAssetMap().getAsset(objectiveId) == null) {
/* 163 */       this.logger.at(Level.WARNING).log("Couldn't find objective '%s'. Skipping objective.", objectiveId);
/* 164 */       return null;
/*     */     } 
/*     */     
/* 167 */     if (!objective.setupCurrentTasks(store)) {
/* 168 */       this.logger.at(Level.WARNING).log("A problem occurred while setting up the objective '%s'. Skipping objective.", objectiveId);
/* 169 */       return null;
/*     */     } 
/*     */     
/* 172 */     addObjective(objectiveUUID, objective);
/*     */     
/* 174 */     return objective;
/*     */   }
/*     */   
/*     */   public void unloadObjective(UUID objectiveUUID) {
/* 178 */     Objective objective = this.objectives.get(objectiveUUID);
/* 179 */     if (objective == null)
/*     */       return; 
/* 181 */     objective.unload();
/*     */     
/* 183 */     removeObjective(objective.getObjectiveUUID());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\ObjectiveDataStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */