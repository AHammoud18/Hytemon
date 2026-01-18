/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
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
/*     */ public class ComputeVelocitySystem
/*     */   extends SteppableTickingSystem
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcEntityComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Velocity> velocityComponentType;
/*     */   @Nonnull
/*  40 */   private final ComponentType<EntityStore, TransformComponent> transformComponentType = TransformComponent.getComponentType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComputeVelocitySystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcEntityComponentType, @Nonnull ComponentType<EntityStore, Velocity> velocityComponentType, @Nonnull Set<Dependency<EntityStore>> dependencies) {
/*  64 */     this.npcEntityComponentType = npcEntityComponentType;
/*  65 */     this.velocityComponentType = velocityComponentType;
/*  66 */     this.dependencies = dependencies;
/*  67 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcEntityComponentType, (Query)this.transformComponentType, (Query)velocityComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  73 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  78 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  83 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcEntityComponentType);
/*  84 */     assert npcComponent != null;
/*     */     
/*  86 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/*  87 */     assert transformComponent != null;
/*     */     
/*  89 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, this.velocityComponentType);
/*  90 */     assert velocityComponent != null;
/*     */     
/*  92 */     Vector3d position = transformComponent.getPosition();
/*  93 */     Vector3d oldPosition = npcComponent.getOldPosition();
/*  94 */     double x = (position.getX() - oldPosition.getX()) / dt;
/*  95 */     double y = (position.getY() - oldPosition.getY()) / dt;
/*  96 */     double z = (position.getZ() - oldPosition.getZ()) / dt;
/*  97 */     velocityComponent.set(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 103 */     return this.query;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\ComputeVelocitySystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */