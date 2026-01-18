/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockMembership;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
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
/*     */ public class RoleActivateSystem
/*     */   extends HolderSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, StateEvaluator> stateEvaluatorComponentType;
/*     */   
/*     */   public RoleActivateSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType, @Nonnull ComponentType<EntityStore, StateEvaluator> stateEvaluatorComponentType) {
/* 152 */     this.npcComponentType = npcComponentType;
/* 153 */     this.stateEvaluatorComponentType = stateEvaluatorComponentType;
/*     */   }
/*     */   
/*     */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*     */     double influenceRadius;
/* 158 */     NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/* 159 */     assert npcComponent != null;
/*     */     
/* 161 */     Role role = npcComponent.getRole();
/*     */     
/* 163 */     if (holder.getComponent(FlockMembership.getComponentType()) != null) {
/*     */       
/* 165 */       influenceRadius = role.getFlockInfluenceRange();
/*     */     } else {
/* 167 */       influenceRadius = 0.0D;
/*     */     } 
/*     */     
/* 170 */     StateEvaluator stateEvaluator = (StateEvaluator)holder.getComponent(this.stateEvaluatorComponentType);
/* 171 */     if (stateEvaluator != null) stateEvaluator.setupNPC(holder);
/*     */     
/* 173 */     PositionCacheSystems.initialisePositionCache(role, stateEvaluator, influenceRadius);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 178 */     NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcComponentType);
/* 179 */     assert npcComponent != null;
/* 180 */     npcComponent.getRole().getPositionCache().reset(false);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 186 */     return (Query)this.npcComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\PositionCacheSystems$RoleActivateSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */