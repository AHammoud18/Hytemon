/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SprintingCondition
/*    */   extends Condition
/*    */ {
/*    */   @Nonnull
/* 21 */   public static final BuilderCodec<SprintingCondition> CODEC = BuilderCodec.builder(SprintingCondition.class, SprintingCondition::new, Condition.BASE_CODEC)
/* 22 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SprintingCondition() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SprintingCondition(boolean inverse) {
/* 36 */     super(inverse);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/* 41 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)componentAccessor.getComponent(ref, MovementStatesComponent.getComponentType());
/* 42 */     assert movementStatesComponent != null;
/*    */     
/* 44 */     return (movementStatesComponent.getMovementStates()).sprinting;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "SprintingCondition{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\SprintingCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */