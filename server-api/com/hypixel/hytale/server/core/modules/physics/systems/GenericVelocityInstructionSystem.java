/*    */ package com.hypixel.hytale.server.core.modules.physics.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.Order;
/*    */ import com.hypixel.hytale.component.dependency.SystemTypeDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenericVelocityInstructionSystem
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 27 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemTypeDependency(Order.AFTER, 
/*    */         
/* 29 */         EntityModule.get().getVelocityModifyingSystemType()));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GenericVelocityInstructionSystem() {
/* 42 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)Velocity.getComponentType() });
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 47 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/* 48 */     assert velocityComponent != null;
/*    */ 
/*    */     
/* 51 */     for (Velocity.Instruction instruction : velocityComponent.getInstructions()) {
/* 52 */       switch (instruction.getType()) { case Set:
/* 53 */           velocityComponent.set(instruction.getVelocity());
/* 54 */         case Add: velocityComponent.addForce(instruction.getVelocity()); }
/*    */ 
/*    */ 
/*    */     
/*    */     } 
/* 59 */     velocityComponent.getInstructions().clear();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 65 */     return this.dependencies;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 71 */     return this.query;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physics\systems\GenericVelocityInstructionSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */