/*     */ package com.hypixel.hytale.builtin.parkour;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.OrderPriority;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.PlayerSpatialSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ public class Ticking
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, ParkourCheckpoint> parkourCheckpointComponentType;
/*     */   private final ComponentType<EntityStore, Player> playerComponentType;
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent;
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   
/*     */   public Ticking(ComponentType<EntityStore, ParkourCheckpoint> parkourCheckpointComponentType, ComponentType<EntityStore, Player> playerComponentType, ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent) {
/*  78 */     this.parkourCheckpointComponentType = parkourCheckpointComponentType;
/*  79 */     this.playerComponentType = playerComponentType;
/*  80 */     this.playerSpatialComponent = playerSpatialComponent;
/*     */     
/*  82 */     this.transformComponentType = TransformComponent.getComponentType();
/*  83 */     this.uuidComponentType = UUIDComponent.getComponentType();
/*     */     
/*  85 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)parkourCheckpointComponentType, (Query)this.transformComponentType });
/*  86 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, PlayerSpatialSystem.class, OrderPriority.CLOSEST));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  92 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  98 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 103 */     int lastIndex = ParkourPlugin.get().getLastIndex();
/* 104 */     if (lastIndex == 0)
/*     */       return; 
/* 106 */     int parkourCheckpointIndex = ((ParkourCheckpoint)archetypeChunk.getComponent(index, this.parkourCheckpointComponentType)).getIndex();
/*     */     
/* 108 */     SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.playerSpatialComponent);
/* 109 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 110 */     Vector3d position = ((TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType)).getPosition();
/* 111 */     spatialResource.getSpatialStructure().ordered(position, 1.0D, (List)results);
/*     */     
/* 113 */     ParkourPlugin parkourPlugin = ParkourPlugin.get();
/* 114 */     Object2IntMap<UUID> currentCheckpointByPlayerMap = parkourPlugin.getCurrentCheckpointByPlayerMap();
/* 115 */     Object2LongMap<UUID> startTimeByPlayerMap = parkourPlugin.getStartTimeByPlayerMap();
/*     */     
/* 117 */     for (int i = 0; i < results.size(); i++) {
/* 118 */       Ref<EntityStore> otherReference = (Ref<EntityStore>)results.get(i);
/* 119 */       UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(otherReference, this.uuidComponentType);
/* 120 */       UUID playerUuid = uuidComponent.getUuid();
/* 121 */       Player player = (Player)commandBuffer.getComponent(otherReference, this.playerComponentType);
/*     */       
/* 123 */       handleCheckpointUpdate(currentCheckpointByPlayerMap, startTimeByPlayerMap, player, playerUuid, parkourCheckpointIndex, lastIndex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void handleCheckpointUpdate(@Nonnull Object2IntMap<UUID> currentCheckpointByPlayerMap, @Nonnull Object2LongMap<UUID> startTimeByPlayerMap, @Nonnull Player player, UUID playerUuid, int checkpointIndex, int lastIndex) {
/* 128 */     int currentCheckpoint = currentCheckpointByPlayerMap.getOrDefault(playerUuid, -1);
/*     */     
/* 130 */     if (currentCheckpoint == -1) {
/*     */       
/* 132 */       if (checkpointIndex != 0) {
/*     */         return;
/*     */       }
/* 135 */       currentCheckpointByPlayerMap.put(playerUuid, 0);
/* 136 */       startTimeByPlayerMap.put(playerUuid, System.nanoTime());
/* 137 */       player.sendMessage(Message.translation("server.general.parkourRun.started"));
/*     */     }
/*     */     else {
/*     */       
/* 141 */       if (currentCheckpoint + 1 != checkpointIndex) {
/*     */         return;
/*     */       }
/* 144 */       if (lastIndex == checkpointIndex) {
/* 145 */         long completionTimeNano = System.nanoTime() - startTimeByPlayerMap.getLong(playerUuid);
/* 146 */         long completionTimeMillis = TimeUnit.NANOSECONDS.toMillis(completionTimeNano);
/*     */         
/* 148 */         player.sendMessage(Message.translation("server.general.parkourRun.completed").param("seconds", completionTimeMillis / 1000.0D));
/* 149 */         currentCheckpointByPlayerMap.remove(playerUuid, currentCheckpoint);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 154 */       currentCheckpointByPlayerMap.put(playerUuid, checkpointIndex);
/* 155 */       player.sendMessage(Message.translation("server.general.parkourRun.checkpointReached").param("checkpoint", checkpointIndex).param("checkpoints", lastIndex));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\ParkourCheckpointSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */