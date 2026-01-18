/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.common.util.TimeUtil;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.CombatConfig;
/*    */ import com.hypixel.hytale.server.core.entity.damage.DamageDataComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Duration;
/*    */ import java.time.Instant;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OutOfCombatCondition
/*    */   extends Condition
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<OutOfCombatCondition> CODEC;
/*    */   protected Duration delay;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(OutOfCombatCondition.class, OutOfCombatCondition::new, Condition.BASE_CODEC).append(new KeyedCodec("DelaySeconds", (Codec)Codec.DURATION_SECONDS), (condition, value) -> condition.delay = value, condition -> condition.delay).documentation("Delay before an entity is considered out of combat. Expressed in seconds.").add()).build();
/*    */   }
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
/*    */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/* 50 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*    */ 
/*    */ 
/*    */     
/* 54 */     CombatConfig combatConfig = world.getGameplayConfig().getCombatConfig();
/* 55 */     Duration delayToUse = (this.delay != null) ? this.delay : combatConfig.getOutOfCombatDelay();
/*    */     
/* 57 */     DamageDataComponent damageDataComponent = (DamageDataComponent)componentAccessor.getComponent(ref, DamageDataComponent.getComponentType());
/* 58 */     assert damageDataComponent != null;
/* 59 */     Instant lastCombatAction = damageDataComponent.getLastCombatAction();
/*    */     
/* 61 */     return (TimeUtil.compareDifference(lastCombatAction, currentTime, delayToUse) >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 67 */     return "OutOfCombatCondition{delay=" + String.valueOf(this.delay) + "} " + super
/*    */       
/* 69 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\OutOfCombatCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */