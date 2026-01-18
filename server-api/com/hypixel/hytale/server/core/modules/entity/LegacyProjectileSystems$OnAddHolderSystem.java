/*     */ package com.hypixel.hytale.server.core.modules.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.projectile.config.Projectile;
/*     */ import com.hypixel.hytale.server.core.entity.entities.ProjectileComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class OnAddHolderSystem
/*     */   extends HolderSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*  81 */   private static final ComponentType<EntityStore, ProjectileComponent> PROJECTILE_COMPONENT_TYPE = ProjectileComponent.getComponentType();
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*     */     BoundingBox boundingBox;
/*  85 */     if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/*  86 */       holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*     */     }
/*     */     
/*  89 */     ProjectileComponent projectileComponent = (ProjectileComponent)holder.getComponent(PROJECTILE_COMPONENT_TYPE);
/*  90 */     assert projectileComponent != null;
/*     */     
/*  92 */     projectileComponent.initialize();
/*     */ 
/*     */     
/*  95 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(projectileComponent.getAppearance());
/*  96 */     if (modelAsset != null) {
/*  97 */       Model model = Model.createUnitScaleModel(modelAsset);
/*  98 */       holder.putComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/*  99 */       holder.putComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/* 100 */       boundingBox = new BoundingBox(model.getBoundingBox());
/*     */     } else {
/* 102 */       Projectile projectileAsset = projectileComponent.getProjectile();
/* 103 */       if (projectileAsset != null) {
/* 104 */         boundingBox = new BoundingBox(Box.horizontallyCentered(projectileAsset.getRadius(), projectileAsset.getHeight(), projectileAsset.getRadius()));
/*     */       } else {
/* 106 */         boundingBox = new BoundingBox(Box.horizontallyCentered(0.25D, 0.25D, 0.25D));
/*     */       } 
/*     */     } 
/* 109 */     holder.putComponent(BoundingBox.getComponentType(), (Component)boundingBox);
/*     */     
/* 111 */     projectileComponent.initializePhysics(boundingBox);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 120 */     return (Query)PROJECTILE_COMPONENT_TYPE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\LegacyProjectileSystems$OnAddHolderSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */