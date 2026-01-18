/*    */ package com.hypixel.hytale.server.spawning.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.component.spatial.SpatialSystem;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.beacons.SpawnBeacon;
/*    */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeaconSpatialSystem
/*    */   extends SpatialSystem<EntityStore>
/*    */ {
/* 23 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)SpawnBeacon.getComponentType(), (Query)FloodFillPositionSelector.getComponentType(), (Query)TransformComponent.getComponentType() });
/*    */   
/*    */   public BeaconSpatialSystem(ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spatialResource) {
/* 26 */     super(spatialResource);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 32 */     return QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 37 */     super.tick(dt, systemIndex, store);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPosition(@Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 43 */     return ((TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType())).getPosition();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\systems\BeaconSpatialSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */