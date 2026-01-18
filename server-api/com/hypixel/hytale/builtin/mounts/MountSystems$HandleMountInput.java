/*     */ package com.hypixel.hytale.builtin.mounts;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.MountController;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerInput;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSystems;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandleMountInput
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/* 350 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)MountedComponent.getComponentType(), (Query)PlayerInput.getComponentType() });
/* 351 */   private final Set<Dependency<EntityStore>> deps = (Set)Set.of(new SystemDependency(Order.BEFORE, PlayerSystems.ProcessPlayerInput.class));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 357 */     MountedComponent mounted = (MountedComponent)archetypeChunk.getComponent(index, MountedComponent.getComponentType());
/* 358 */     assert mounted != null;
/* 359 */     PlayerInput input = (PlayerInput)archetypeChunk.getComponent(index, PlayerInput.getComponentType());
/* 360 */     assert input != null;
/*     */     
/* 362 */     MountController controller = mounted.getControllerType();
/*     */ 
/*     */     
/* 365 */     Ref<EntityStore> targetRef = (controller == MountController.BlockMount) ? archetypeChunk.getReferenceTo(index) : mounted.getMountedToEntity();
/*     */     
/* 367 */     List<PlayerInput.InputUpdate> queue = input.getMovementUpdateQueue();
/*     */     
/* 369 */     for (int i = 0; i < queue.size(); i++) {
/* 370 */       PlayerInput.InputUpdate q = queue.get(i);
/*     */       
/* 372 */       if (controller == MountController.BlockMount && (q instanceof PlayerInput.RelativeMovement || q instanceof PlayerInput.AbsoluteMovement)) {
/* 373 */         if (mounted.getMountedDurationMs() < 600L) {
/*     */           continue;
/*     */         }
/* 376 */         Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 377 */         commandBuffer.removeComponent(ref, MountedComponent.getComponentType());
/*     */       } 
/*     */       
/* 380 */       if (q instanceof PlayerInput.SetRiderMovementStates) { PlayerInput.SetRiderMovementStates s = (PlayerInput.SetRiderMovementStates)q;
/* 381 */         MovementStates states = s.movementStates();
/* 382 */         MovementStatesComponent movementStatesComponent = (MovementStatesComponent)archetypeChunk.getComponent(index, MovementStatesComponent.getComponentType());
/* 383 */         if (movementStatesComponent != null)
/* 384 */           movementStatesComponent.setMovementStates(states);  }
/* 385 */       else if (!(q instanceof PlayerInput.WishMovement))
/*     */       
/* 387 */       { if (q instanceof PlayerInput.RelativeMovement) { PlayerInput.RelativeMovement relative = (PlayerInput.RelativeMovement)q;
/*     */           
/* 389 */           relative.apply(commandBuffer, archetypeChunk, index);
/* 390 */           TransformComponent transform = (TransformComponent)commandBuffer.getComponent(targetRef, TransformComponent.getComponentType());
/* 391 */           transform.getPosition().add(relative.getX(), relative.getY(), relative.getZ()); }
/* 392 */         else if (q instanceof PlayerInput.AbsoluteMovement) { PlayerInput.AbsoluteMovement absolute = (PlayerInput.AbsoluteMovement)q;
/*     */           
/* 394 */           absolute.apply(commandBuffer, archetypeChunk, index);
/* 395 */           TransformComponent transform = (TransformComponent)commandBuffer.getComponent(targetRef, TransformComponent.getComponentType());
/* 396 */           transform.getPosition().assign(absolute.getX(), absolute.getY(), absolute.getZ()); }
/* 397 */         else if (q instanceof PlayerInput.SetMovementStates) { PlayerInput.SetMovementStates s = (PlayerInput.SetMovementStates)q;
/* 398 */           MovementStates states = s.movementStates();
/* 399 */           MovementStatesComponent movementStatesComponent = (MovementStatesComponent)commandBuffer.getComponent(targetRef, MovementStatesComponent.getComponentType());
/* 400 */           if (movementStatesComponent != null)
/* 401 */             movementStatesComponent.setMovementStates(states);  }
/* 402 */         else if (q instanceof PlayerInput.SetBody) { PlayerInput.SetBody body = (PlayerInput.SetBody)q;
/*     */           
/* 404 */           body.apply(commandBuffer, archetypeChunk, index);
/* 405 */           TransformComponent transform = (TransformComponent)commandBuffer.getComponent(targetRef, TransformComponent.getComponentType());
/* 406 */           transform.getRotation().assign((body.direction()).pitch, (body.direction()).yaw, (body.direction()).roll); }
/* 407 */         else if (q instanceof PlayerInput.SetHead) { PlayerInput.SetHead head = (PlayerInput.SetHead)q;
/* 408 */           head.apply(commandBuffer, archetypeChunk, index); }
/*     */          }
/*     */        continue;
/*     */     } 
/* 412 */     queue.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 418 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 424 */     return this.deps;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\MountSystems$HandleMountInput.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */