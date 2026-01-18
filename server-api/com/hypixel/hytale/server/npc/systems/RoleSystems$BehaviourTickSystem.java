/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.Frozen;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.NewSpawnComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.components.StepComponent;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BehaviourTickSystem
/*     */   extends TickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, StepComponent> stepComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Frozen> frozenComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NewSpawnComponent> newSpawnComponentType;
/*     */   
/*     */   public BehaviourTickSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType, @Nonnull ComponentType<EntityStore, StepComponent> stepComponentType) {
/* 287 */     this.npcComponentType = npcComponentType;
/* 288 */     this.stepComponentType = stepComponentType;
/* 289 */     this.frozenComponentType = Frozen.getComponentType();
/* 290 */     this.newSpawnComponentType = NewSpawnComponent.getComponentType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 295 */     List<Ref<EntityStore>> entities = RoleSystems.ENTITY_LIST.get();
/*     */ 
/*     */     
/* 298 */     store.forEachChunk((Query)this.npcComponentType, (archetypeChunk, commandBuffer) -> {
/*     */           for (int index = 0; index < archetypeChunk.size(); index++) {
/*     */             entities.add(archetypeChunk.getReferenceTo(index));
/*     */           }
/*     */         });
/*     */     
/* 304 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 305 */     boolean isAllNpcFrozen = world.getWorldConfig().isAllNPCFrozen();
/* 306 */     for (Ref<EntityStore> entityReference : entities) {
/* 307 */       float tickLength; if (!entityReference.isValid() || 
/* 308 */         store.getComponent(entityReference, this.newSpawnComponentType) != null) {
/*     */         continue;
/*     */       }
/* 311 */       if (store.getComponent(entityReference, this.frozenComponentType) != null || isAllNpcFrozen) {
/* 312 */         StepComponent stepComponent = (StepComponent)store.getComponent(entityReference, this.stepComponentType);
/* 313 */         if (stepComponent == null)
/* 314 */           continue;  tickLength = stepComponent.getTickLength();
/*     */       } else {
/* 316 */         tickLength = dt;
/*     */       } 
/*     */       
/* 319 */       NPCEntity npcComponent = (NPCEntity)store.getComponent(entityReference, this.npcComponentType);
/* 320 */       assert npcComponent != null;
/*     */       
/*     */       try {
/* 323 */         Role role = npcComponent.getRole();
/* 324 */         boolean benchmarking = NPCPlugin.get().isBenchmarkingRole();
/* 325 */         if (benchmarking) {
/* 326 */           long start = System.nanoTime();
/* 327 */           role.tick(entityReference, tickLength, store);
/* 328 */           NPCPlugin.get().collectRoleTick(role.getRoleIndex(), System.nanoTime() - start); continue;
/*     */         } 
/* 330 */         role.tick(entityReference, tickLength, store);
/*     */       }
/* 332 */       catch (NullPointerException|IllegalArgumentException|IllegalStateException e) {
/* 333 */         ((HytaleLogger.Api)NPCPlugin.get().getLogger().at(Level.SEVERE).withCause(e)).log("Failed to tick NPC: %s", npcComponent.getRoleName());
/* 334 */         store.removeEntity(entityReference, RemoveReason.REMOVE);
/*     */       } 
/*     */     } 
/*     */     
/* 338 */     entities.clear();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\RoleSystems$BehaviourTickSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */