/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.AndQuery;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*    */ import com.hypixel.hytale.component.spatial.SpatialSystem;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.item.PreventItemMerging;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSpatialSystem
/*    */   extends SpatialSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 28 */   private static final AndQuery<EntityStore> QUERY = Query.and(new Query[] {
/* 29 */         (Query)ItemComponent.getComponentType(), 
/* 30 */         (Query)TransformComponent.getComponentType(), 
/* 31 */         (Query)Query.not((Query)PreventItemMerging.getComponentType())
/*    */       });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemSpatialSystem(ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spatialResource) {
/* 40 */     super(spatialResource);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 46 */     return (Query<EntityStore>)QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 51 */     super.tick(dt, systemIndex, store);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d getPosition(@Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 57 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 58 */     assert transformComponent != null;
/* 59 */     return transformComponent.getPosition();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\ItemSpatialSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */