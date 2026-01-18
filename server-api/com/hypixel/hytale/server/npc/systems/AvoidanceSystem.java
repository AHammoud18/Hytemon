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
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import com.hypixel.hytale.server.npc.role.support.DebugSupport;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AvoidanceSystem
/*     */   extends SteppableTickingSystem
/*     */ {
/*  29 */   public static final Vector3f DEBUG_COLOR_STEERING_POST = new Vector3f(0.0F, 1.0F, 0.0F);
/*  30 */   public static final Vector3f DEBUG_COLOR_STEERING_PRE = new Vector3f(1.0F, 0.0F, 0.0F);
/*  31 */   public static final Vector3f DEBUG_COLOR_AVOIDANCE = new Vector3f(1.0F, 1.0F, 1.0F);
/*  32 */   public static final Vector3f DEBUG_COLOR_SEPARATION = new Vector3f(0.0F, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double DEBUG_MIN_VECTOR_DRAW_LENGTH_SQUARED = 0.01D;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final double DEBUG_VECTORS_SCALE = 4.0D;
/*     */ 
/*     */   
/*     */   public static final float DEBUG_VECTORS_TIME = 0.05F;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> componentType;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, TransformComponent> transformComponentType;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */   
/*     */   @Nonnull
/*  59 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, RoleSystems.BehaviourTickSystem.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AvoidanceSystem(@Nonnull ComponentType<EntityStore, NPCEntity> componentType) {
/*  67 */     this.componentType = componentType;
/*  68 */     this.transformComponentType = TransformComponent.getComponentType();
/*  69 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)this.transformComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  75 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  80 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  86 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public void steppedTick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  91 */     Ref<EntityStore> npcRef = archetypeChunk.getReferenceTo(index);
/*  92 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.componentType);
/*  93 */     assert npcComponent != null;
/*     */     
/*  95 */     Role role = npcComponent.getRole();
/*  96 */     if (role.isAvoidingEntities() || role.isApplySeparation()) {
/*  97 */       Ref<EntityStore> target = role.getMarkedEntitySupport().getTargetReferenceToIgnoreForAvoidance();
/*  98 */       if (target != null && target.isValid()) {
/*  99 */         role.getIgnoredEntitiesForAvoidance().add(target);
/*     */       }
/*     */     } 
/*     */     
/* 103 */     if (!role.getActiveMotionController().isObstructed()) {
/* 104 */       DebugSupport debugSupport = role.getDebugSupport();
/* 105 */       boolean debugVisAvoidance = debugSupport.isDebugFlagSet(RoleDebugFlags.VisAvoidance);
/* 106 */       boolean debugVisSeparation = debugSupport.isDebugFlagSet(RoleDebugFlags.VisSeparation);
/* 107 */       Vector3d preBlendSteering = (debugVisSeparation || debugVisAvoidance) ? role.getBodySteering().getTranslation().clone() : null;
/* 108 */       boolean renderSteering = false;
/*     */       
/* 110 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, this.transformComponentType);
/* 111 */       assert transformComponent != null;
/*     */       
/* 113 */       Vector3d position = transformComponent.getPosition();
/* 114 */       World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */       
/* 116 */       if (role.isAvoidingEntities()) {
/* 117 */         role.blendAvoidance(npcRef, position, role.getBodySteering(), commandBuffer);
/* 118 */         if (debugVisAvoidance) {
/* 119 */           renderSteering = true;
/* 120 */           renderDebugSteeringVector(position, role.getLastAvoidanceSteering(), DEBUG_COLOR_AVOIDANCE, world);
/*     */         } 
/*     */       } 
/* 123 */       if (role.isApplySeparation()) {
/* 124 */         role.blendSeparation(archetypeChunk.getReferenceTo(index), position, role.getBodySteering(), this.transformComponentType, commandBuffer);
/* 125 */         if (debugVisSeparation) {
/* 126 */           renderSteering = true;
/* 127 */           renderDebugSteeringVector(position, role.getLastSeparationSteering(), DEBUG_COLOR_SEPARATION, world);
/*     */         } 
/*     */       } 
/* 130 */       if (renderSteering) {
/* 131 */         renderDebugSteeringVectorInverse(position, preBlendSteering, DEBUG_COLOR_STEERING_PRE, world);
/* 132 */         renderDebugSteeringVector(position, role.getBodySteering().getTranslation(), DEBUG_COLOR_STEERING_POST, world);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void renderDebugSteeringVector(@Nonnull Vector3d position, @Nonnull Vector3d direction, @Nonnull Vector3f color, @Nonnull World world) {
/* 138 */     if (direction.squaredLength() < 0.01D) {
/*     */       return;
/*     */     }
/* 141 */     Vector3d scaledDir = direction.clone().scale(4.0D);
/* 142 */     DebugUtils.addArrow(world, position, scaledDir, color, 0.05F, false);
/*     */   }
/*     */   
/*     */   private static void renderDebugSteeringVectorInverse(@Nonnull Vector3d position, @Nonnull Vector3d direction, @Nonnull Vector3f color, @Nonnull World world) {
/* 146 */     if (direction.squaredLength() < 0.01D) {
/*     */       return;
/*     */     }
/* 149 */     Vector3d scaledDir = direction.clone().scale(4.0D);
/* 150 */     Vector3d start = position.clone().subtract(scaledDir);
/* 151 */     DebugUtils.addArrow(world, start, scaledDir, color, 0.05F, false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\AvoidanceSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */