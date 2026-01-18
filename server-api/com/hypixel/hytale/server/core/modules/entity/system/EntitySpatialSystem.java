/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.component.spatial.SpatialSystem;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySpatialSystem
/*    */   extends SpatialSystem<EntityStore>
/*    */ {
/* 23 */   public static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)TransformComponent.getComponentType(), (Query)Query.not((Query)Intangible.getComponentType()), (Query)Query.not((Query)Player.getComponentType()) });
/*    */   
/*    */   public EntitySpatialSystem(ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spatialResource) {
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\EntitySpatialSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */