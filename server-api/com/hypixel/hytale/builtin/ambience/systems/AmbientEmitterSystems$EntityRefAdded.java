/*    */ package com.hypixel.hytale.builtin.ambience.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.components.AmbientEmitterComponent;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.NonSerialized;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.AudioComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ public class EntityRefAdded
/*    */   extends RefSystem<EntityStore>
/*    */ {
/* 54 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)AmbientEmitterComponent.getComponentType(), (Query)TransformComponent.getComponentType() });
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 58 */     AmbientEmitterComponent emitterComponent = (AmbientEmitterComponent)store.getComponent(ref, AmbientEmitterComponent.getComponentType());
/* 59 */     assert emitterComponent != null;
/*    */     
/* 61 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 62 */     assert transformComponent != null;
/*    */ 
/*    */ 
/*    */     
/* 66 */     Holder<EntityStore> emitterHolder = EntityStore.REGISTRY.newHolder();
/* 67 */     emitterHolder.addComponent(TransformComponent.getComponentType(), (Component)transformComponent.clone());
/*    */     
/* 69 */     AudioComponent audioComponent = new AudioComponent();
/* 70 */     audioComponent.addSound(SoundEvent.getAssetMap().getIndex(emitterComponent.getSoundEventId()));
/* 71 */     emitterHolder.addComponent(AudioComponent.getComponentType(), (Component)audioComponent);
/*    */     
/* 73 */     emitterHolder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/* 74 */     emitterHolder.ensureComponent(Intangible.getComponentType());
/* 75 */     emitterHolder.addComponent(EntityStore.REGISTRY.getNonSerializedComponentType(), (Component)NonSerialized.get());
/*    */     
/* 77 */     emitterComponent.setSpawnedEmitter(commandBuffer.addEntity(emitterHolder, AddReason.SPAWN));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 84 */     if (reason == RemoveReason.REMOVE) {
/* 85 */       AmbientEmitterComponent emitterComponent = (AmbientEmitterComponent)store.getComponent(ref, AmbientEmitterComponent.getComponentType());
/* 86 */       assert emitterComponent != null;
/*    */       
/* 88 */       Ref<EntityStore> emitterRef = emitterComponent.getSpawnedEmitter();
/* 89 */       if (emitterRef != null) {
/* 90 */         commandBuffer.removeEntity(emitterRef, RemoveReason.REMOVE);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 98 */     return this.query;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\systems\AmbientEmitterSystems$EntityRefAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */