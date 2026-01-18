/*     */ package com.hypixel.hytale.server.core.modules.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.DisableProcessingAssert;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TickingSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */   implements DisableProcessingAssert
/*     */ {
/*     */   @Nonnull
/* 133 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Velocity> velocityComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ProjectileComponent> projectileComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Archetype<EntityStore> archetype;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TickingSystem(@Nonnull ComponentType<EntityStore, ProjectileComponent> projectileComponentType, @Nonnull ComponentType<EntityStore, TransformComponent> transformComponentType, @Nonnull ComponentType<EntityStore, Velocity> velocityComponentType, @Nonnull ComponentType<EntityStore, BoundingBox> boundingBoxComponentType) {
/* 177 */     this.projectileComponentType = projectileComponentType;
/* 178 */     this.velocityComponentType = velocityComponentType;
/* 179 */     this.boundingBoxComponentType = boundingBoxComponentType;
/* 180 */     this.transformComponentType = transformComponentType;
/* 181 */     this.archetype = Archetype.of(new ComponentType[] { projectileComponentType, transformComponentType, velocityComponentType, boundingBoxComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 186 */     return (Query<EntityStore>)this.archetype;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 191 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 192 */     assert transformComponent != null;
/*     */     
/* 194 */     ProjectileComponent projectileComponent = (ProjectileComponent)archetypeChunk.getComponent(index, this.projectileComponentType);
/* 195 */     assert projectileComponent != null;
/*     */     
/* 197 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, this.velocityComponentType);
/* 198 */     assert velocityComponent != null;
/*     */ 
/*     */ 
/*     */     
/* 202 */     BoundingBox boundingBox = (BoundingBox)archetypeChunk.getComponent(index, this.boundingBoxComponentType);
/*     */     
/* 204 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */     
/*     */     try {
/* 207 */       if (projectileComponent.consumeDeadTimer(dt)) {
/* 208 */         projectileComponent.onProjectileDeath(ref, transformComponent.getPosition(), commandBuffer);
/* 209 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */         
/*     */         return;
/*     */       } 
/* 213 */       World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 214 */       projectileComponent.getSimplePhysicsProvider().tick(dt, velocityComponent, world, transformComponent, ref, (ComponentAccessor)commandBuffer);
/* 215 */     } catch (Throwable throwable) {
/* 216 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Exception while ticking entity! Removing!! %s", projectileComponent);
/* 217 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\LegacyProjectileSystems$TickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */