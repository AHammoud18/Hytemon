/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.ModelSystems;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
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
/*     */ public class RoleActivateSystem
/*     */   extends HolderSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ModelComponent> modelComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, BoundingBox> boundingBoxComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   
/*     */   public RoleActivateSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/*  86 */     this.npcComponentType = npcComponentType;
/*  87 */     this.modelComponentType = ModelComponent.getComponentType();
/*  88 */     this.boundingBoxComponentType = BoundingBox.getComponentType();
/*  89 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcComponentType, (Query)this.modelComponentType, (Query)this.boundingBoxComponentType });
/*  90 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, BalancingInitialisationSystem.class), new SystemDependency(Order.AFTER, ModelSystems.ModelSpawned.class));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  97 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 103 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 108 */     NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/* 109 */     assert npcComponent != null;
/*     */     
/* 111 */     Role role = npcComponent.getRole();
/* 112 */     role.getStateSupport().activate();
/* 113 */     role.getDebugSupport().activate();
/*     */     
/* 115 */     ModelComponent modelComponent = (ModelComponent)holder.getComponent(this.modelComponentType);
/* 116 */     assert modelComponent != null;
/*     */     
/* 118 */     BoundingBox boundingBoxComponent = (BoundingBox)holder.getComponent(this.boundingBoxComponentType);
/* 119 */     assert boundingBoxComponent != null;
/*     */     
/* 121 */     role.updateMotionControllers(null, modelComponent.getModel(), boundingBoxComponent.getBoundingBox(), null);
/*     */     
/* 123 */     role.clearOnce();
/* 124 */     role.getActiveMotionController().activate();
/*     */ 
/*     */     
/* 127 */     holder.ensureComponent(InteractionModule.get().getChainingDataComponent());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 132 */     NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/* 133 */     assert npcComponent != null;
/*     */     
/* 135 */     Role role = npcComponent.getRole();
/* 136 */     role.getActiveMotionController().deactivate();
/* 137 */     role.getWorldSupport().resetAllBlockSensors();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\RoleSystems$RoleActivateSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */