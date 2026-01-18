/*    */ package com.hypixel.hytale.component.spatial;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.QuerySystem;
/*    */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpatialSystem<ECS_TYPE>
/*    */   extends TickingSystem<ECS_TYPE>
/*    */   implements QuerySystem<ECS_TYPE>
/*    */ {
/*    */   @Nonnull
/*    */   private final ResourceType<ECS_TYPE, SpatialResource<Ref<ECS_TYPE>, ECS_TYPE>> resourceType;
/*    */   
/*    */   public SpatialSystem(@Nonnull ResourceType<ECS_TYPE, SpatialResource<Ref<ECS_TYPE>, ECS_TYPE>> resourceType) {
/* 35 */     this.resourceType = resourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<ECS_TYPE> store) {
/* 40 */     SpatialResource<Ref<ECS_TYPE>, ECS_TYPE> spatialResource = (SpatialResource<Ref<ECS_TYPE>, ECS_TYPE>)store.getResource(this.resourceType);
/* 41 */     SpatialData<Ref<ECS_TYPE>> spatialData = spatialResource.getSpatialData();
/* 42 */     spatialData.clear();
/*    */ 
/*    */     
/* 45 */     store.forEachChunk(systemIndex, (archetypeChunk, commandBuffer) -> {
/*    */           int size = archetypeChunk.size();
/*    */           
/*    */           spatialData.addCapacity(size);
/*    */           
/*    */           for (int index = 0; index < size; index++) {
/*    */             Vector3d position = getPosition(archetypeChunk, index);
/*    */             if (position != null) {
/*    */               Ref<ECS_TYPE> ref = archetypeChunk.getReferenceTo(index);
/*    */               spatialData.append(position, ref);
/*    */             } 
/*    */           } 
/*    */         });
/* 58 */     spatialResource.getSpatialStructure().rebuild(spatialData);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public abstract Vector3d getPosition(@Nonnull ArchetypeChunk<ECS_TYPE> paramArchetypeChunk, int paramInt);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\spatial\SpatialSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */