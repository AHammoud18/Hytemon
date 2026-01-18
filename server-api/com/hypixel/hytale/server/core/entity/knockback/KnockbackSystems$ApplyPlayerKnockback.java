/*     */ package com.hypixel.hytale.server.core.entity.knockback;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.KnockbackSimulation;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.systems.IVelocityModifyingSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class ApplyPlayerKnockback
/*     */   extends EntityTickingSystem<EntityStore>
/*     */   implements IVelocityModifyingSystem
/*     */ {
/*     */   public static boolean DO_SERVER_PREDICTION = false;
/*     */   @Nonnull
/*  84 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] {
/*  85 */         (Query)KnockbackComponent.getComponentType(), 
/*  86 */         (Query)Player.getComponentType(), 
/*  87 */         (Query)Velocity.getComponentType()
/*     */       });
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  93 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  98 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*     */     Velocity velocityComponent;
/* 103 */     KnockbackComponent knockbackComponent = (KnockbackComponent)archetypeChunk.getComponent(index, KnockbackComponent.getComponentType());
/* 104 */     assert knockbackComponent != null;
/*     */     
/* 106 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     KnockbackSimulation knockbackSimulationComponent = (KnockbackSimulation)archetypeChunk.getComponent(index, KnockbackSimulation.getComponentType());
/*     */     
/* 115 */     if (knockbackSimulationComponent == null && DO_SERVER_PREDICTION) {
/* 116 */       commandBuffer.addComponent(ref, KnockbackSimulation.getComponentType(), (Component)(knockbackSimulationComponent = new KnockbackSimulation()));
/*     */     }
/*     */     
/* 119 */     knockbackComponent.applyModifiers();
/*     */     
/* 121 */     switch (KnockbackSystems.null.$SwitchMap$com$hypixel$hytale$protocol$ChangeVelocityType[knockbackComponent.getVelocityType().ordinal()]) {
/*     */       case 1:
/* 123 */         if (DO_SERVER_PREDICTION) {
/* 124 */           if (knockbackSimulationComponent != null)
/* 125 */             knockbackSimulationComponent.addRequestedVelocity(knockbackComponent.getVelocity()); 
/*     */           break;
/*     */         } 
/* 128 */         velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/* 129 */         assert velocityComponent != null;
/* 130 */         velocityComponent.addInstruction(knockbackComponent.getVelocity(), knockbackComponent.getVelocityConfig(), ChangeVelocityType.Add);
/*     */         
/* 132 */         if (knockbackComponent.getDuration() > 0.0F) {
/* 133 */           knockbackComponent.incrementTimer(dt);
/*     */         }
/*     */         
/* 136 */         if (knockbackComponent.getDuration() == 0.0F || knockbackComponent.getTimer() > knockbackComponent.getDuration()) {
/* 137 */           commandBuffer.tryRemoveComponent(ref, KnockbackComponent.getComponentType());
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2:
/* 142 */         if (DO_SERVER_PREDICTION) {
/* 143 */           if (knockbackSimulationComponent != null)
/* 144 */             knockbackSimulationComponent.setRequestedVelocity(knockbackComponent.getVelocity()); 
/*     */           break;
/*     */         } 
/* 147 */         velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/* 148 */         assert velocityComponent != null;
/* 149 */         velocityComponent.addInstruction(knockbackComponent.getVelocity(), null, ChangeVelocityType.Set);
/*     */         
/* 151 */         if (knockbackComponent.getDuration() > 0.0F) {
/* 152 */           knockbackComponent.incrementTimer(dt);
/*     */         }
/*     */         
/* 155 */         if (knockbackComponent.getDuration() == 0.0F || knockbackComponent.getTimer() > knockbackComponent.getDuration()) {
/* 156 */           commandBuffer.tryRemoveComponent(ref, KnockbackComponent.getComponentType());
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 162 */     if (DO_SERVER_PREDICTION && 
/* 163 */       knockbackSimulationComponent != null) {
/* 164 */       knockbackSimulationComponent.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 172 */     return DamageModule.get().getInspectDamageGroup();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\knockback\KnockbackSystems$ApplyPlayerKnockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */