/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
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
/*     */ public class ModelSpawned
/*     */   extends HolderSystem<EntityStore>
/*     */ {
/*  81 */   private final ComponentType<EntityStore, ModelComponent> modelComponentType = ModelComponent.getComponentType();
/*  82 */   private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType = BoundingBox.getComponentType();
/*  83 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, ModelSystems.SetRenderedModel.class));
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  87 */     Model model = ((ModelComponent)holder.getComponent(this.modelComponentType)).getModel();
/*  88 */     Box modelBoundingBox = model.getBoundingBox();
/*  89 */     if (modelBoundingBox == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  94 */     BoundingBox boundingBox = (BoundingBox)holder.getComponent(this.boundingBoxComponentType);
/*  95 */     if (boundingBox == null) {
/*  96 */       boundingBox = new BoundingBox();
/*  97 */       holder.addComponent(this.boundingBoxComponentType, (Component)boundingBox);
/*     */     } 
/*  99 */     boundingBox.setBoundingBox(modelBoundingBox);
/* 100 */     boundingBox.setDetailBoxes(model.getDetailBoxes());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 109 */     return (Query)this.modelComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 115 */     return this.dependencies;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\ModelSystems$ModelSpawned.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */