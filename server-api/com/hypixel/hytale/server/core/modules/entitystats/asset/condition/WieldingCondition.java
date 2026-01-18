/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.damage.DamageDataComponent;
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
/*    */ public class WieldingCondition
/*    */   extends Condition
/*    */ {
/*    */   @Nonnull
/* 21 */   public static final BuilderCodec<WieldingCondition> CODEC = BuilderCodec.builder(WieldingCondition.class, WieldingCondition::new, Condition.BASE_CODEC)
/* 22 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected WieldingCondition() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WieldingCondition(boolean inverse) {
/* 36 */     super(inverse);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/* 41 */     DamageDataComponent damageComponent = (DamageDataComponent)componentAccessor.getComponent(ref, DamageDataComponent.getComponentType());
/* 42 */     assert damageComponent != null;
/*    */     
/* 44 */     return (damageComponent.getCurrentWielding() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "WieldingCondition{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\WieldingCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */