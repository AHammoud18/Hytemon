/*     */ package com.hypixel.hytale.server.core.modules.entity;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.projectile.config.Projectile;
/*     */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ public class LegacyProjectileSystems {
/*     */   @Nonnull
/*  34 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OnAddRefSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*  46 */     private static final ComponentType<EntityStore, ProjectileComponent> PROJECTILE_COMPONENT_TYPE = ProjectileComponent.getComponentType();
/*     */ 
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/*  51 */       return (Query)PROJECTILE_COMPONENT_TYPE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl AddReason reason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
/*  56 */       ProjectileComponent projectileComponent = (ProjectileComponent)commandBuffer.getComponent(ref, PROJECTILE_COMPONENT_TYPE);
/*  57 */       assert projectileComponent != null;
/*     */ 
/*     */       
/*  60 */       if (projectileComponent.getProjectile() == null) {
/*  61 */         LegacyProjectileSystems.LOGGER.at(Level.WARNING).log("Removing projectile entity %s as it failed to initialize correctly!", ref);
/*  62 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl RemoveReason reason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OnAddHolderSystem
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*  81 */     private static final ComponentType<EntityStore, ProjectileComponent> PROJECTILE_COMPONENT_TYPE = ProjectileComponent.getComponentType();
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*     */       BoundingBox boundingBox;
/*  85 */       if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/*  86 */         holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*     */       }
/*     */       
/*  89 */       ProjectileComponent projectileComponent = (ProjectileComponent)holder.getComponent(PROJECTILE_COMPONENT_TYPE);
/*  90 */       assert projectileComponent != null;
/*     */       
/*  92 */       projectileComponent.initialize();
/*     */ 
/*     */       
/*  95 */       ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(projectileComponent.getAppearance());
/*  96 */       if (modelAsset != null) {
/*  97 */         Model model = Model.createUnitScaleModel(modelAsset);
/*  98 */         holder.putComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*  99 */         holder.putComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/* 100 */         boundingBox = new BoundingBox(model.getBoundingBox());
/*     */       } else {
/* 102 */         Projectile projectileAsset = projectileComponent.getProjectile();
/* 103 */         if (projectileAsset != null) {
/* 104 */           boundingBox = new BoundingBox(Box.horizontallyCentered(projectileAsset.getRadius(), projectileAsset.getHeight(), projectileAsset.getRadius()));
/*     */         } else {
/* 106 */           boundingBox = new BoundingBox(Box.horizontallyCentered(0.25D, 0.25D, 0.25D));
/*     */         } 
/*     */       } 
/* 109 */       holder.putComponent(BoundingBox.getComponentType(), (Component)boundingBox);
/*     */       
/* 111 */       projectileComponent.initializePhysics(boundingBox);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 120 */       return (Query)PROJECTILE_COMPONENT_TYPE;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TickingSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */     implements DisableProcessingAssert
/*     */   {
/*     */     @Nonnull
/* 133 */     private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, Velocity> velocityComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, ProjectileComponent> projectileComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Archetype<EntityStore> archetype;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TickingSystem(@Nonnull ComponentType<EntityStore, ProjectileComponent> projectileComponentType, @Nonnull ComponentType<EntityStore, TransformComponent> transformComponentType, @Nonnull ComponentType<EntityStore, Velocity> velocityComponentType, @Nonnull ComponentType<EntityStore, BoundingBox> boundingBoxComponentType) {
/* 177 */       this.projectileComponentType = projectileComponentType;
/* 178 */       this.velocityComponentType = velocityComponentType;
/* 179 */       this.boundingBoxComponentType = boundingBoxComponentType;
/* 180 */       this.transformComponentType = transformComponentType;
/* 181 */       this.archetype = Archetype.of(new ComponentType[] { projectileComponentType, transformComponentType, velocityComponentType, boundingBoxComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 186 */       return (Query<EntityStore>)this.archetype;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 191 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 192 */       assert transformComponent != null;
/*     */       
/* 194 */       ProjectileComponent projectileComponent = (ProjectileComponent)archetypeChunk.getComponent(index, this.projectileComponentType);
/* 195 */       assert projectileComponent != null;
/*     */       
/* 197 */       Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, this.velocityComponentType);
/* 198 */       assert velocityComponent != null;
/*     */ 
/*     */ 
/*     */       
/* 202 */       BoundingBox boundingBox = (BoundingBox)archetypeChunk.getComponent(index, this.boundingBoxComponentType);
/*     */       
/* 204 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */       
/*     */       try {
/* 207 */         if (projectileComponent.consumeDeadTimer(dt)) {
/* 208 */           projectileComponent.onProjectileDeath(ref, transformComponent.getPosition(), commandBuffer);
/* 209 */           commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */           
/*     */           return;
/*     */         } 
/* 213 */         World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 214 */         projectileComponent.getSimplePhysicsProvider().tick(dt, velocityComponent, world, transformComponent, ref, (ComponentAccessor)commandBuffer);
/* 215 */       } catch (Throwable throwable) {
/* 216 */         ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Exception while ticking entity! Removing!! %s", projectileComponent);
/* 217 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\LegacyProjectileSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */