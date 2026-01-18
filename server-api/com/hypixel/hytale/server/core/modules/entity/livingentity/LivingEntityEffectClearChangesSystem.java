/*    */ package com.hypixel.hytale.server.core.modules.entity.livingentity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.Order;
/*    */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LivingEntityEffectClearChangesSystem
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 27 */   private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, EntityTrackerSystems.EffectControllerSystem.class));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 34 */     return (Query<EntityStore>)EffectControllerComponent.getComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 40 */     return DEPENDENCIES;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 45 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)archetypeChunk.getComponent(index, EffectControllerComponent.getComponentType());
/* 46 */     assert effectControllerComponent != null;
/*    */     
/* 48 */     effectControllerComponent.clearChanges();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\livingentity\LivingEntityEffectClearChangesSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */