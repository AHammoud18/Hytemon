/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.List;
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
/*     */ public class CaptureKnockbackInput
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/* 114 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] {
/* 115 */         (Query)PlayerInput.getComponentType(), 
/* 116 */         (Query)KnockbackSimulation.getComponentType()
/*     */       });
/* 118 */   private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.BEFORE, PlayerSystems.ProcessPlayerInput.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 125 */     return QUERY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 131 */     return DEPENDENCIES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 136 */     KnockbackSimulation knockbackSimulationComponent = (KnockbackSimulation)archetypeChunk.getComponent(index, KnockbackSimulation.getComponentType());
/* 137 */     assert knockbackSimulationComponent != null;
/*     */     
/* 139 */     PlayerInput playerInputComponent = (PlayerInput)archetypeChunk.getComponent(index, PlayerInput.getComponentType());
/* 140 */     assert playerInputComponent != null;
/*     */     
/* 142 */     List<PlayerInput.InputUpdate> queue = playerInputComponent.getMovementUpdateQueue();
/*     */     
/* 144 */     Vector3d client = knockbackSimulationComponent.getClientPosition();
/* 145 */     Vector3d clientLast = knockbackSimulationComponent.getClientLastPosition();
/* 146 */     Vector3d relativeMovement = knockbackSimulationComponent.getRelativeMovement();
/*     */     
/* 148 */     clientLast.assign(client);
/* 149 */     boolean hasWishMovement = false;
/*     */     
/* 151 */     if (queue.isEmpty())
/*     */       return; 
/* 153 */     for (int i = 0; i < queue.size(); i++) {
/* 154 */       PlayerInput.InputUpdate update = queue.get(i);
/* 155 */       if (update instanceof PlayerInput.AbsoluteMovement) { PlayerInput.AbsoluteMovement abs = (PlayerInput.AbsoluteMovement)update;
/* 156 */         client.assign(abs.getX(), abs.getY(), abs.getZ()); }
/* 157 */       else if (update instanceof PlayerInput.RelativeMovement) { PlayerInput.RelativeMovement rel = (PlayerInput.RelativeMovement)update;
/* 158 */         client.add(rel.getX(), rel.getY(), rel.getZ()); }
/* 159 */       else if (update instanceof PlayerInput.WishMovement) { PlayerInput.WishMovement wish = (PlayerInput.WishMovement)update;
/* 160 */         hasWishMovement = true;
/* 161 */         relativeMovement.assign(wish.getX(), wish.getY(), wish.getZ()); }
/* 162 */       else if (update instanceof PlayerInput.SetMovementStates)
/* 163 */       { MovementStates movementStates = ((PlayerInput.SetMovementStates)update).movementStates();
/* 164 */         if (movementStates.jumping) {
/* 165 */           knockbackSimulationComponent.setWasJumping(true);
/*     */         }
/* 167 */         knockbackSimulationComponent.setClientMovementStates(movementStates); }
/*     */       else
/*     */       { continue; }
/*     */ 
/*     */       
/* 172 */       queue.remove(i);
/* 173 */       i--;
/*     */       continue;
/*     */     } 
/* 176 */     if (!hasWishMovement) {
/* 177 */       relativeMovement.assign(client).subtract(clientLast);
/* 178 */       if (knockbackSimulationComponent.hadWishMovement()) {
/* 179 */         knockbackSimulationComponent.setClientFinished(true);
/*     */       }
/*     */     } else {
/* 182 */       knockbackSimulationComponent.setHadWishMovement(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\KnockbackPredictionSystems$CaptureKnockbackInput.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */