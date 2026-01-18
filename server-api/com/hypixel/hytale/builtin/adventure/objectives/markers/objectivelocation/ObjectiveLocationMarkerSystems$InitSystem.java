/*     */ package com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLocationMarkerAsset;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.packets.assets.UntrackObjective;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabCopyableComponent;
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
/*     */ public class InitSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ObjectiveLocationMarker> objectiveLocationMarkerComponent;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ModelComponent> modelComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public InitSystem(@Nonnull ComponentType<EntityStore, ObjectiveLocationMarker> objectiveLocationMarkerComponent) {
/*  79 */     this.objectiveLocationMarkerComponent = objectiveLocationMarkerComponent;
/*  80 */     this.modelComponentType = ModelComponent.getComponentType();
/*  81 */     this.transformComponentType = TransformComponent.getComponentType();
/*  82 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)objectiveLocationMarkerComponent, (Query)this.modelComponentType, (Query)this.transformComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  88 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  93 */     ObjectiveLocationMarker objectiveLocationMarkerComponent = (ObjectiveLocationMarker)store.getComponent(ref, this.objectiveLocationMarkerComponent);
/*  94 */     assert objectiveLocationMarkerComponent != null;
/*     */     
/*  96 */     ObjectiveLocationMarkerAsset markerAsset = (ObjectiveLocationMarkerAsset)ObjectiveLocationMarkerAsset.getAssetMap().getAsset(objectiveLocationMarkerComponent.objectiveLocationMarkerId);
/*  97 */     if (markerAsset == null) {
/*  98 */       ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Failed to find ObjectiveLocationMarker '%s'. Entity removed!", objectiveLocationMarkerComponent.objectiveLocationMarkerId);
/*  99 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     if (objectiveLocationMarkerComponent.activeObjectiveUUID != null) {
/* 104 */       Objective activeObjective = ObjectivePlugin.get().getObjectiveDataStore().loadObjective(objectiveLocationMarkerComponent.activeObjectiveUUID, store);
/* 105 */       if (activeObjective == null) {
/* 106 */         ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Failed to load Objective with UUID '%s'. Entity removed!", objectiveLocationMarkerComponent.activeObjectiveUUID);
/* 107 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */         
/*     */         return;
/*     */       } 
/* 111 */       objectiveLocationMarkerComponent.setActiveObjective(activeObjective);
/* 112 */       objectiveLocationMarkerComponent.setUntrackPacket(new UntrackObjective(objectiveLocationMarkerComponent.activeObjectiveUUID));
/*     */     } 
/*     */     
/* 115 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, this.transformComponentType);
/* 116 */     assert transformComponent != null;
/*     */     
/* 118 */     Vector3f rotation = transformComponent.getRotation();
/* 119 */     objectiveLocationMarkerComponent.updateLocationMarkerValues(markerAsset, rotation.getYaw(), store);
/*     */ 
/*     */     
/* 122 */     ModelComponent modelComponent = (ModelComponent)store.getComponent(ref, this.modelComponentType);
/* 123 */     assert modelComponent != null;
/*     */     
/* 125 */     Model model = modelComponent.getModel();
/* 126 */     commandBuffer.putComponent(ref, this.modelComponentType, (Component)new ModelComponent(new Model(model.getModelAssetId(), model.getScale(), model.getRandomAttachmentIds(), model
/* 127 */             .getAttachments(), objectiveLocationMarkerComponent.getArea().getBoxForEntryArea(), model.getModel(), model.getTexture(), model.getGradientSet(), model.getGradientId(), model
/* 128 */             .getEyeHeight(), model.getCrouchOffset(), model.getAnimationSetMap(), model.getCamera(), model.getLight(), model.getParticles(), model.getTrails(), model
/* 129 */             .getPhysicsValues(), model.getDetailBoxes(), model.getPhobia(), model.getPhobiaModelAssetId())));
/*     */     
/* 131 */     commandBuffer.ensureComponent(ref, PrefabCopyableComponent.getComponentType());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 136 */     ObjectiveLocationMarker objectLocationMarkerComponent = (ObjectiveLocationMarker)store.getComponent(ref, this.objectiveLocationMarkerComponent);
/* 137 */     assert objectLocationMarkerComponent != null;
/*     */     
/* 139 */     Objective activeObjective = objectLocationMarkerComponent.getActiveObjective();
/* 140 */     if (activeObjective == null)
/*     */       return; 
/* 142 */     ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/*     */ 
/*     */     
/* 145 */     objectiveDataStore.saveToDisk(objectLocationMarkerComponent.activeObjectiveUUID.toString(), activeObjective);
/* 146 */     objectiveDataStore.unloadObjective(objectLocationMarkerComponent.activeObjectiveUUID);
/*     */     
/* 148 */     if (reason == RemoveReason.REMOVE)
/*     */     {
/*     */       
/* 151 */       commandBuffer.run(theStore -> ObjectivePlugin.get().cancelObjective(objectLocationMarkerComponent.activeObjectiveUUID, theStore));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\objectivelocation\ObjectiveLocationMarkerSystems$InitSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */