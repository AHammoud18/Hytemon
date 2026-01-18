/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.TransformSystems;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import java.util.Set;
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
/*     */ public class SteeringSystem
/*     */   extends SteppableTickingSystem
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcEntityComponent;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public SteeringSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcEntityComponent) {
/*  56 */     this.npcEntityComponent = npcEntityComponent;
/*  57 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, AvoidanceSystem.class), new SystemDependency(Order.AFTER, KnockbackSystems.ApplyKnockback.class), new SystemDependency(Order.BEFORE, TransformSystems.EntityTrackerUpdate.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcEntityComponent });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  69 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  80 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  85 */     NPCEntity npc = (NPCEntity)archetypeChunk.getComponent(index, this.npcEntityComponent);
/*  86 */     assert npc != null;
/*     */     
/*  88 */     TransformComponent npcTransformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  89 */     assert npcTransformComponent != null;
/*     */     
/*  91 */     Role role = npc.getRole();
/*  92 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  97 */       if (role.getDebugSupport().isDebugMotionSteering()) {
/*  98 */         Vector3d position = npcTransformComponent.getPosition();
/*  99 */         double x = position.getX();
/* 100 */         double z = position.getZ();
/* 101 */         float yaw = npcTransformComponent.getRotation().getYaw();
/*     */         
/* 103 */         role.getActiveMotionController().steer(ref, role, role.getBodySteering(), role.getHeadSteering(), dt, (ComponentAccessor)commandBuffer);
/*     */         
/* 105 */         x = position.getX() - x;
/* 106 */         z = position.getZ() - z;
/*     */         
/* 108 */         double l = Math.sqrt(x * x + z * z);
/* 109 */         double v = l / dt;
/* 110 */         double vx = x / dt;
/* 111 */         double vz = z / dt;
/* 112 */         double vh = (l > 0.0D) ? PhysicsMath.normalizeTurnAngle(PhysicsMath.headingFromDirection(x, z)) : 0.0D;
/*     */         
/* 114 */         NPCPlugin.get().getLogger().at(Level.FINER).log("=   Role    = t =%.4f v =%.4f vx=%.4f vz=%.4f h =%.4f nh=%.4f vh=%.4f", Float.valueOf(dt), 
/* 115 */             Double.valueOf(v), Double.valueOf(vx), Double.valueOf(vz), Float.valueOf(57.295776F * yaw), Float.valueOf(57.295776F * yaw), 
/* 116 */             Double.valueOf(57.2957763671875D * vh));
/*     */       } else {
/* 118 */         role.getActiveMotionController().steer(ref, role, role.getBodySteering(), role.getHeadSteering(), dt, (ComponentAccessor)commandBuffer);
/*     */       } 
/* 120 */     } catch (IllegalArgumentException|IllegalStateException e) {
/* 121 */       ((HytaleLogger.Api)NPCPlugin.get().getLogger().at(Level.SEVERE).withCause(e)).log();
/* 122 */       commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\SteeringSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */