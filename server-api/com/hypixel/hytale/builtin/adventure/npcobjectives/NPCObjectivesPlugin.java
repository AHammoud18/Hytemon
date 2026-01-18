/*     */ package com.hypixel.hytale.builtin.adventure.npcobjectives;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.BountyObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillSpawnBeaconObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillSpawnMarkerObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.resources.KillTrackerResource;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.task.BountyObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.task.KillNPCObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.task.KillSpawnBeaconObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.task.KillSpawnMarkerObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.UseEntityObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.UseEntityObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.function.function.TriFunction;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NPCObjectivesPlugin extends JavaPlugin {
/*     */   public static NPCObjectivesPlugin get() {
/*  43 */     return instance;
/*     */   }
/*     */   protected static NPCObjectivesPlugin instance;
/*     */   private ResourceType<EntityStore, KillTrackerResource> killTrackerResourceType;
/*     */   
/*     */   public NPCObjectivesPlugin(@Nonnull JavaPluginInit init) {
/*  49 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  54 */     instance = this;
/*     */     
/*  56 */     ObjectivePlugin.get().registerTask("KillSpawnBeacon", KillSpawnBeaconObjectiveTaskAsset.class, (Codec)KillSpawnBeaconObjectiveTaskAsset.CODEC, KillSpawnBeaconObjectiveTask.class, (Codec)KillSpawnBeaconObjectiveTask.CODEC, KillSpawnBeaconObjectiveTask::new);
/*  57 */     ObjectivePlugin.get().registerTask("KillSpawnMarker", KillSpawnMarkerObjectiveTaskAsset.class, (Codec)KillSpawnMarkerObjectiveTaskAsset.CODEC, KillSpawnMarkerObjectiveTask.class, (Codec)KillSpawnMarkerObjectiveTask.CODEC, KillSpawnMarkerObjectiveTask::new);
/*  58 */     ObjectivePlugin.get().registerTask("Bounty", BountyObjectiveTaskAsset.class, (Codec)BountyObjectiveTaskAsset.CODEC, BountyObjectiveTask.class, (Codec)BountyObjectiveTask.CODEC, BountyObjectiveTask::new);
/*  59 */     ObjectivePlugin.get().registerTask("KillNPC", KillObjectiveTaskAsset.class, (Codec)KillObjectiveTaskAsset.CODEC, KillNPCObjectiveTask.class, (Codec)KillNPCObjectiveTask.CODEC, KillNPCObjectiveTask::new);
/*     */     
/*  61 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconCheckRemovalSystem());
/*  62 */     this.killTrackerResourceType = getEntityStoreRegistry().registerResource(KillTrackerResource.class, KillTrackerResource::new);
/*  63 */     getEntityStoreRegistry().registerSystem((ISystem)new KillTrackerSystem());
/*     */     
/*  65 */     NPCPlugin.get().registerCoreComponentType("CompleteTask", com.hypixel.hytale.builtin.adventure.npcobjectives.npc.builders.BuilderActionCompleteTask::new)
/*  66 */       .registerCoreComponentType("StartObjective", com.hypixel.hytale.builtin.adventure.npcobjectives.npc.builders.BuilderActionStartObjective::new)
/*  67 */       .registerCoreComponentType("HasTask", com.hypixel.hytale.builtin.adventure.npcobjectives.npc.builders.BuilderSensorHasTask::new);
/*     */     
/*  69 */     AssetRegistry.getAssetStore(ObjectiveAsset.class).injectLoadsAfter(SpawnMarker.class);
/*  70 */     AssetRegistry.getAssetStore(ObjectiveAsset.class).injectLoadsAfter(BeaconNPCSpawn.class);
/*  71 */     AssetRegistry.getAssetStore(ObjectiveAsset.class).injectLoadsAfter(NPCGroup.class);
/*     */   }
/*     */   
/*     */   public static boolean hasTask(@Nonnull UUID playerUUID, @Nonnull UUID npcId, @Nonnull String taskId) {
/*  75 */     Map<String, Set<UUID>> entityObjectives = ObjectivePlugin.get().getObjectiveDataStore().getEntityTasksForPlayer(playerUUID);
/*  76 */     if (entityObjectives == null) return false; 
/*  77 */     return (entityObjectives.get(taskId) != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static String updateTaskCompletion(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull UUID npcId, @Nonnull String taskId) {
/*  82 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/*  83 */     assert uuidComponent != null;
/*     */     
/*  85 */     ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/*  86 */     Map<String, Set<UUID>> entityObjectiveUUIDs = objectiveDataStore.getEntityTasksForPlayer(uuidComponent.getUuid());
/*  87 */     if (entityObjectiveUUIDs == null) return null;
/*     */     
/*  89 */     Set<UUID> objectiveUUIDsForTaskId = entityObjectiveUUIDs.get(taskId);
/*  90 */     if (objectiveUUIDsForTaskId == null) return null;
/*     */     
/*  92 */     for (UUID objectiveUUID : objectiveUUIDsForTaskId) {
/*  93 */       Objective objective = objectiveDataStore.getObjective(objectiveUUID);
/*  94 */       if (objective == null)
/*     */         continue; 
/*  96 */       for (ObjectiveTask task : objective.getCurrentTasks()) {
/*  97 */         if (task instanceof UseEntityObjectiveTask) { UseEntityObjectiveTask useEntityTask = (UseEntityObjectiveTask)task;
/*     */           
/*  99 */           UseEntityObjectiveTaskAsset taskAsset = useEntityTask.getAsset();
/*     */           
/* 101 */           if (taskAsset.getTaskId().equals(taskId)) {
/*     */             
/* 103 */             if (!useEntityTask.increaseTaskCompletion(store, ref, 1, objective, playerRef, npcId)) return null;
/*     */             
/* 105 */             return taskAsset.getAnimationIdToPlay();
/*     */           }  }
/*     */       
/*     */       } 
/* 109 */     }  return null;
/*     */   }
/*     */   
/*     */   public static void startObjective(@Nonnull Ref<EntityStore> playerReference, @Nonnull String taskId, @Nonnull Store<EntityStore> store) {
/* 113 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(playerReference, UUIDComponent.getComponentType());
/* 114 */     assert uuidComponent != null;
/*     */     
/* 116 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 117 */     ObjectivePlugin.get().startObjective(taskId, Set.of(uuidComponent.getUuid()), world.getWorldConfig().getUuid(), null, store);
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, KillTrackerResource> getKillTrackerResourceType() {
/* 121 */     return this.killTrackerResourceType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\NPCObjectivesPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */