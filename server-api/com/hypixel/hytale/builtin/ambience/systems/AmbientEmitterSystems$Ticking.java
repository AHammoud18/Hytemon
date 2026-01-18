/*     */ package com.hypixel.hytale.builtin.ambience.systems;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.ambience.AmbiencePlugin;
/*     */ import com.hypixel.hytale.builtin.ambience.components.AmbientEmitterComponent;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
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
/*     */ public class Ticking
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/* 106 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)AmbientEmitterComponent.getComponentType(), (Query)TransformComponent.getComponentType() });
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 110 */     AmbientEmitterComponent emitter = (AmbientEmitterComponent)archetypeChunk.getComponent(index, AmbientEmitterComponent.getComponentType());
/* 111 */     assert emitter != null;
/*     */     
/* 113 */     TransformComponent transform = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 114 */     assert transform != null;
/*     */     
/* 116 */     if (emitter.getSpawnedEmitter() == null || !emitter.getSpawnedEmitter().isValid()) {
/* 117 */       AmbiencePlugin.get().getLogger().at(Level.WARNING).log("Ambient emitter lost at %s: %d %s", transform
/* 118 */           .getPosition(), Integer.valueOf(archetypeChunk.getReferenceTo(index).getIndex()), emitter.getSoundEventId());
/* 119 */       commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 123 */     TransformComponent ownedEmitterTransform = (TransformComponent)store.getComponent(emitter.getSpawnedEmitter(), TransformComponent.getComponentType());
/* 124 */     if (transform.getPosition().distanceSquaredTo(ownedEmitterTransform.getPosition()) > 1.0D) {
/* 125 */       ownedEmitterTransform.setPosition(transform.getPosition());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Query<EntityStore> getQuery() {
/* 132 */     return this.query;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\systems\AmbientEmitterSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */