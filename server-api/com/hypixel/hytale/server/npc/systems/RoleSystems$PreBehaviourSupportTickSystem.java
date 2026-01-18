/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.MarkedEntitySupport;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PreBehaviourSupportTickSystem
/*     */   extends SteppableTickingSystem
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Player> playerComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, DeathComponent> deathComponentType;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   
/*     */   public PreBehaviourSupportTickSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/* 176 */     this.npcComponentType = npcComponentType;
/* 177 */     this.playerComponentType = Player.getComponentType();
/* 178 */     this.deathComponentType = DeathComponent.getComponentType();
/* 179 */     this.dependencies = Set.of(new SystemDependency(Order.BEFORE, RoleSystems.BehaviourTickSystem.class));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 185 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 190 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 196 */     return (Query)this.npcComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 201 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 202 */     assert npcComponent != null;
/*     */     
/* 204 */     Role role = npcComponent.getRole();
/*     */     
/* 206 */     MarkedEntitySupport markedEntitySupport = role.getMarkedEntitySupport();
/* 207 */     Ref[] arrayOfRef = markedEntitySupport.getEntityTargets();
/*     */ 
/*     */     
/* 210 */     for (int i = 0; i < arrayOfRef.length; i++) {
/* 211 */       Ref<EntityStore> targetReference = arrayOfRef[i];
/* 212 */       if (targetReference == null)
/*     */         continue; 
/* 214 */       if (!targetReference.isValid()) {
/* 215 */         arrayOfRef[i] = null;
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 220 */       Player playerComponent = (Player)commandBuffer.getComponent(targetReference, this.playerComponentType);
/* 221 */       if (playerComponent != null && playerComponent.getGameMode() != GameMode.Adventure)
/*     */       {
/* 223 */         if (playerComponent.getGameMode() == GameMode.Creative) {
/* 224 */           PlayerSettings playerSettingsComponent = (PlayerSettings)commandBuffer.getComponent(targetReference, PlayerSettings.getComponentType());
/* 225 */           if (playerSettingsComponent == null || !playerSettingsComponent.creativeSettings().allowNPCDetection()) {
/*     */ 
/*     */             
/* 228 */             arrayOfRef[i] = null;
/*     */             continue;
/*     */           } 
/*     */         } else {
/* 232 */           arrayOfRef[i] = null;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       }
/* 237 */       DeathComponent deathComponent = (DeathComponent)commandBuffer.getComponent(targetReference, this.deathComponentType);
/* 238 */       if (deathComponent != null) {
/* 239 */         arrayOfRef[i] = null;
/*     */       }
/*     */       continue;
/*     */     } 
/* 243 */     role.clearOnceIfNeeded();
/* 244 */     role.getBodySteering().clear();
/* 245 */     role.getHeadSteering().clear();
/* 246 */     role.getIgnoredEntitiesForAvoidance().clear();
/* 247 */     npcComponent.invalidateCachedHorizontalSpeedMultiplier();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\RoleSystems$PreBehaviourSupportTickSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */