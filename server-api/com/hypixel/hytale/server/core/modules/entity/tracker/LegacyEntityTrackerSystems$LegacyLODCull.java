/*     */ package com.hypixel.hytale.server.core.modules.entity.tracker;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class LegacyLODCull
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   public static final double ENTITY_LOD_RATIO_DEFAULT = 3.5E-5D;
/* 200 */   public static double ENTITY_LOD_RATIO = 3.5E-5D;
/*     */   
/*     */   private final ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType;
/*     */   private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   
/*     */   public LegacyLODCull(ComponentType<EntityStore, EntityTrackerSystems.EntityViewer> componentType) {
/* 210 */     this.componentType = componentType;
/* 211 */     this.boundingBoxComponentType = BoundingBox.getComponentType();
/* 212 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)TransformComponent.getComponentType() });
/* 213 */     this.dependencies = Collections.singleton(new SystemDependency(Order.AFTER, EntityTrackerSystems.CollectVisible.class));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 219 */     return EntityTrackerSystems.FIND_VISIBLE_ENTITIES_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 225 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 231 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 236 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 241 */     EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)archetypeChunk.getComponent(index, this.componentType);
/* 242 */     assert entityViewerComponent != null;
/*     */     
/* 244 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 245 */     assert transformComponent != null;
/* 246 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 248 */     for (Iterator<Ref<EntityStore>> iterator = entityViewerComponent.visible.iterator(); iterator.hasNext(); ) {
/* 249 */       Ref<EntityStore> ref = iterator.next();
/*     */ 
/*     */       
/* 252 */       BoundingBox boundingBoxComponent = (BoundingBox)commandBuffer.getComponent(ref, this.boundingBoxComponentType);
/* 253 */       if (boundingBoxComponent == null)
/*     */         continue; 
/* 255 */       TransformComponent otherTransformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 256 */       assert otherTransformComponent != null;
/*     */ 
/*     */       
/* 259 */       double distanceSq = otherTransformComponent.getPosition().distanceSquaredTo(position);
/* 260 */       double maximumThickness = boundingBoxComponent.getBoundingBox().getMaximumThickness();
/* 261 */       if (maximumThickness < ENTITY_LOD_RATIO * distanceSq) {
/* 262 */         entityViewerComponent.lodExcludedCount++;
/* 263 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\tracker\LegacyEntityTrackerSystems$LegacyLODCull.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */