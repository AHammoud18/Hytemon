/*    */ package com.hypixel.hytale.server.spawning.suppression.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawnsuppression.SpawnSuppression;
/*    */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*    */ import com.hypixel.hytale.server.spawning.suppression.SpawnSuppressorEntry;
/*    */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionController;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SpawnMarkerSuppressionSystem extends RefSystem<EntityStore> {
/*    */   private final ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType;
/* 27 */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType(); private final ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType;
/* 28 */   private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*    */   
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */ 
/*    */   
/*    */   public SpawnMarkerSuppressionSystem(ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerEntityComponentType, ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType) {
/* 35 */     this.spawnMarkerEntityComponentType = spawnMarkerEntityComponentType;
/* 36 */     this.spawnSuppressionControllerResourceType = spawnSuppressionControllerResourceType;
/*    */     
/* 38 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)spawnMarkerEntityComponentType, (Query)this.uuidComponentType, (Query)this.transformComponentType });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> reference, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 43 */     SpawnSuppressionController suppressionController = (SpawnSuppressionController)store.getResource(this.spawnSuppressionControllerResourceType);
/* 44 */     SpawnMarkerEntity marker = (SpawnMarkerEntity)store.getComponent(reference, this.spawnMarkerEntityComponentType);
/* 45 */     TransformComponent transform = (TransformComponent)commandBuffer.getComponent(reference, this.transformComponentType);
/* 46 */     UUIDComponent uuid = (UUIDComponent)commandBuffer.getComponent(reference, this.uuidComponentType);
/* 47 */     Map<UUID, SpawnSuppressorEntry> spawnSuppressorMap = suppressionController.getSpawnSuppressorMap();
/*    */     
/* 49 */     spawnSuppressorMap.forEach((id, entry) -> {
/*    */           SpawnSuppression suppression = (SpawnSuppression)SpawnSuppression.getAssetMap().getAsset(entry.getSuppressionId());
/*    */           if (suppression == null) {
/*    */             throw new NullPointerException(String.format("No such suppression with ID %s", new Object[] { entry.getSuppressionId() }));
/*    */           }
/*    */           if (!suppression.isSuppressSpawnMarkers()) {
/*    */             return;
/*    */           }
/*    */           double radius = suppression.getRadius();
/*    */           double radiusSquared = radius * radius;
/*    */           if (transform.getPosition().distanceSquaredTo(entry.getPosition()) <= radiusSquared) {
/*    */             marker.suppress(id);
/*    */             SpawningPlugin.get().getLogger().at(Level.FINEST).log("Suppressing spawn marker %s on add/load", uuid.getUuid());
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> reference, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 70 */     return this.query;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\system\SpawnMarkerSuppressionSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */