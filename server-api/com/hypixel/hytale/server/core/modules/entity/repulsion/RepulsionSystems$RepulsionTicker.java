/*     */ package com.hypixel.hytale.server.core.modules.entity.repulsion;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.OrderPriority;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector2d;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.PlayerSpatialSystem;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.systems.IVelocityModifyingSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
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
/*     */ public class RepulsionTicker
/*     */   extends EntityTickingSystem<EntityStore>
/*     */   implements IVelocityModifyingSystem
/*     */ {
/*     */   private final ComponentType<EntityStore, Repulsion> repulsionComponentType;
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spatialComponent;
/*     */   
/*     */   public RepulsionTicker(ComponentType<EntityStore, Repulsion> repulsionComponentType, ComponentType<EntityStore, TransformComponent> transformComponentComponentType, ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spatialComponent) {
/* 180 */     this.repulsionComponentType = repulsionComponentType;
/* 181 */     this.transformComponentComponentType = transformComponentComponentType;
/* 182 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)repulsionComponentType, (Query)transformComponentComponentType });
/*     */     
/* 184 */     this.spatialComponent = spatialComponent;
/* 185 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, PlayerSpatialSystem.class, OrderPriority.CLOSEST));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 191 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 197 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 203 */     return this.query;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 209 */     Repulsion repulsionComponent = (Repulsion)archetypeChunk.getComponent(index, this.repulsionComponentType);
/* 210 */     assert repulsionComponent != null;
/*     */     
/* 212 */     int repulsionConfigIndex = repulsionComponent.getRepulsionConfigIndex();
/* 213 */     if (repulsionConfigIndex == -1)
/*     */       return; 
/* 215 */     RepulsionConfig repulsion = (RepulsionConfig)RepulsionConfig.getAssetMap().getAsset(repulsionConfigIndex);
/*     */     
/* 217 */     float radius = repulsion.radius;
/* 218 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentComponentType);
/* 219 */     assert transformComponent != null;
/*     */     
/* 221 */     Vector2d position = new Vector2d((transformComponent.getPosition()).x, (transformComponent.getPosition()).z);
/*     */     
/* 223 */     SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.spatialComponent);
/* 224 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 225 */     spatialResource.getSpatialStructure().ordered(transformComponent.getPosition(), radius, (List)objectArrayList);
/*     */     
/* 227 */     for (Ref<EntityStore> entityRef : (Iterable<Ref<EntityStore>>)objectArrayList) {
/* 228 */       TransformComponent entityTransformComponent = (TransformComponent)store.getComponent(entityRef, this.transformComponentComponentType);
/* 229 */       assert entityTransformComponent != null;
/*     */       
/* 231 */       Vector2d entityPosition = new Vector2d((entityTransformComponent.getPosition()).x, (entityTransformComponent.getPosition()).z);
/*     */       
/* 233 */       if (entityPosition.equals(position))
/*     */         continue; 
/* 235 */       double distance = position.distanceTo(entityPosition);
/*     */       
/* 237 */       if (distance < 0.1D)
/*     */         continue; 
/* 239 */       double fraction = (radius - distance) / radius;
/* 240 */       float maxForce = repulsion.maxForce;
/* 241 */       int flip = 1;
/* 242 */       if (maxForce < 0.0F) {
/* 243 */         flip = -1;
/* 244 */         maxForce *= flip;
/*     */       } 
/* 246 */       double force = Math.max(repulsion.minForce, maxForce * fraction);
/* 247 */       force *= flip;
/*     */       
/* 249 */       Vector2d push = entityPosition.subtract(position);
/* 250 */       push.normalize();
/* 251 */       push.scale(force);
/*     */       
/* 253 */       Velocity entityVelocityComponent = (Velocity)commandBuffer.getComponent(entityRef, Velocity.getComponentType());
/* 254 */       assert entityVelocityComponent != null;
/* 255 */       Vector3d addedVelocity = new Vector3d((float)push.x, 0.0D, (float)push.y);
/* 256 */       entityVelocityComponent.addInstruction(addedVelocity, null, ChangeVelocityType.Add);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\repulsion\RepulsionSystems$RepulsionTicker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */