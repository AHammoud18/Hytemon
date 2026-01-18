/*     */ package com.hypixel.hytale.server.core.universe.system;
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
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.entities.ChangeVelocity;
/*     */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.physics.systems.GenericVelocityInstructionSystem;
/*     */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerVelocityInstructionSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*  35 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.BEFORE, GenericVelocityInstructionSystem.class), new SystemTypeDependency(Order.AFTER, 
/*     */ 
/*     */ 
/*     */         
/*  39 */         EntityModule.get().getVelocityModifyingSystemType()));
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
/*     */   public PlayerVelocityInstructionSystem() {
/*  52 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)PlayerRef.getComponentType(), (Query)Velocity.getComponentType() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  57 */     PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/*  58 */     assert playerRefComponent != null;
/*     */     
/*  60 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/*  61 */     assert velocityComponent != null;
/*     */ 
/*     */     
/*  64 */     for (Velocity.Instruction instruction : velocityComponent.getInstructions()) {
/*  65 */       Vector3d velocity; VelocityConfig velocityConfig; switch (instruction.getType()) {
/*     */         case Set:
/*  67 */           velocity = instruction.getVelocity();
/*  68 */           velocityConfig = instruction.getConfig();
/*  69 */           playerRefComponent.getPacketHandler().writeNoCache((Packet)new ChangeVelocity((float)velocity.x, (float)velocity.y, (float)velocity.z, ChangeVelocityType.Set, (velocityConfig != null) ? velocityConfig.toPacket() : null));
/*     */           
/*  71 */           if (DebugUtils.DISPLAY_FORCES) {
/*  72 */             TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  73 */             assert transformComponent != null;
/*  74 */             World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*  75 */             DebugUtils.addForce(world, transformComponent.getPosition(), velocity, velocityConfig);
/*     */           } 
/*     */         
/*     */         case Add:
/*  79 */           velocity = instruction.getVelocity();
/*  80 */           velocityConfig = instruction.getConfig();
/*  81 */           playerRefComponent.getPacketHandler().writeNoCache((Packet)new ChangeVelocity((float)velocity.x, (float)velocity.y, (float)velocity.z, ChangeVelocityType.Add, (velocityConfig != null) ? velocityConfig.toPacket() : null));
/*     */           
/*  83 */           if (DebugUtils.DISPLAY_FORCES) {
/*  84 */             TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  85 */             assert transformComponent != null;
/*  86 */             World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */             
/*  88 */             DebugUtils.addForce(world, transformComponent.getPosition(), new Vector3d(velocity.x, velocity.y, velocity.z), velocityConfig);
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\system\PlayerVelocityInstructionSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */