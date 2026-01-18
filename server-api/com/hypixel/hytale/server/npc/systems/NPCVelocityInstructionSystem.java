/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.dependency.SystemTypeDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.systems.GenericVelocityInstructionSystem;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class NPCVelocityInstructionSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*  32 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.BEFORE, GenericVelocityInstructionSystem.class), new SystemTypeDependency(Order.AFTER, 
/*     */ 
/*     */ 
/*     */         
/*  36 */         EntityModule.get().getVelocityModifyingSystemType()));
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
/*     */   public NPCVelocityInstructionSystem() {
/*  49 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)NPCEntity.getComponentType(), (Query)Velocity.getComponentType() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  54 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, NPCEntity.getComponentType());
/*  55 */     assert npcComponent != null;
/*     */     
/*  57 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/*  58 */     assert velocityComponent != null;
/*     */ 
/*     */     
/*  61 */     for (Velocity.Instruction instruction : velocityComponent.getInstructions()) {
/*  62 */       Vector3d velocity; VelocityConfig velocityConfig; Role npcRole; switch (instruction.getType()) {
/*     */         case Set:
/*  64 */           velocity = instruction.getVelocity();
/*  65 */           velocityConfig = instruction.getConfig();
/*  66 */           npcRole = npcComponent.getRole();
/*     */           
/*  68 */           npcRole.processSetVelocityInstruction(velocity, velocityConfig);
/*     */           
/*  70 */           if (DebugUtils.DISPLAY_FORCES) {
/*  71 */             TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  72 */             assert transformComponent != null;
/*  73 */             World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*  74 */             DebugUtils.addForce(world, transformComponent.getPosition(), velocity, velocityConfig);
/*     */           } 
/*     */         
/*     */         case Add:
/*  78 */           velocity = instruction.getVelocity();
/*  79 */           velocityConfig = instruction.getConfig();
/*     */           
/*  81 */           npcComponent.getRole().processAddVelocityInstruction(velocity, velocityConfig);
/*     */           
/*  83 */           if (DebugUtils.DISPLAY_FORCES) {
/*  84 */             TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  85 */             assert transformComponent != null;
/*  86 */             World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */             
/*  88 */             DebugUtils.addForce(world, transformComponent.getPosition(), velocity, velocityConfig);
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/*  95 */     velocityComponent.getInstructions().clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 101 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 107 */     return this.query;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NPCVelocityInstructionSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */