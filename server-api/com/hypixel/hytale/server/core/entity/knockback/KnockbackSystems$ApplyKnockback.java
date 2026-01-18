/*    */ package com.hypixel.hytale.server.core.entity.knockback;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyLivingEntityTypesQuery;
/*    */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*    */ import com.hypixel.hytale.server.core.modules.physics.systems.IVelocityModifyingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplyKnockback
/*    */   extends EntityTickingSystem<EntityStore>
/*    */   implements IVelocityModifyingSystem
/*    */ {
/*    */   @Nonnull
/* 35 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)AllLegacyLivingEntityTypesQuery.INSTANCE, 
/*    */         
/* 37 */         (Query)KnockbackComponent.getComponentType(), 
/* 38 */         (Query)Velocity.getComponentType(), 
/* 39 */         (Query)Query.not((Query)Player.getComponentType()) });
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 45 */     return QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 50 */     KnockbackComponent knockbackComponent = (KnockbackComponent)archetypeChunk.getComponent(index, KnockbackComponent.getComponentType());
/* 51 */     assert knockbackComponent != null;
/*    */     
/* 53 */     knockbackComponent.applyModifiers();
/*    */     
/* 55 */     Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/* 56 */     assert velocityComponent != null;
/* 57 */     velocityComponent.addInstruction(knockbackComponent.getVelocity(), knockbackComponent.getVelocityConfig(), knockbackComponent.getVelocityType());
/*    */     
/* 59 */     if (knockbackComponent.getDuration() > 0.0F) {
/* 60 */       knockbackComponent.incrementTimer(dt);
/*    */     }
/*    */     
/* 63 */     if (knockbackComponent.getDuration() == 0.0F || knockbackComponent.getTimer() > knockbackComponent.getDuration()) {
/* 64 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 65 */       commandBuffer.tryRemoveComponent(ref, KnockbackComponent.getComponentType());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\knockback\KnockbackSystems$ApplyKnockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */