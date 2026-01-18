/*    */ package com.hypixel.hytale.server.spawning.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.Archetype;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.component.spatial.SpatialSystem;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SpawnMarkerSpatialSystem extends SpatialSystem<EntityStore> {
/* 19 */   private static final Archetype<EntityStore> QUERY = Archetype.of(new ComponentType[] { SpawnMarkerEntity.getComponentType(), TransformComponent.getComponentType() });
/*    */   
/*    */   public SpawnMarkerSpatialSystem(ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spatialResource) {
/* 22 */     super(spatialResource);
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 27 */     return (Query<EntityStore>)QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 32 */     super.tick(dt, systemIndex, store);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPosition(@Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 38 */     return ((TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType())).getPosition();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\systems\SpawnMarkerSpatialSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */