/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.common.util.TimeUtil;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*    */ import com.hypixel.hytale.server.core.entity.damage.DamageDataComponent;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
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
/*    */ 
/*    */ public class ChargingCondition
/*    */   extends Condition
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<ChargingCondition> CODEC;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ChargingCondition.class, ChargingCondition::new, Condition.BASE_CODEC).append(new KeyedCodec("Delay", (Codec)Codec.DURATION_SECONDS), (condition, value) -> condition.delay = value, condition -> condition.delay).documentation("The delay duration within which a recent charge is considered valid.").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 41 */   protected Duration delay = Duration.ZERO;
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
/* 53 */     InteractionManager interactionManager = (InteractionManager)componentAccessor.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
/*    */ 
/*    */     
/* 56 */     Boolean result = (Boolean)interactionManager.forEachInteraction((chain, interaction, val) -> val.booleanValue() ? Boolean.TRUE : Boolean.valueOf(interaction instanceof com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ChargingInteraction), Boolean.FALSE);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     if (result.booleanValue()) return true;
/*    */     
/* 63 */     DamageDataComponent damageDataComponent = (DamageDataComponent)componentAccessor.getComponent(ref, DamageDataComponent.getComponentType());
/* 64 */     Instant timeInstant = damageDataComponent.getLastChargeTime();
/* 65 */     return (timeInstant != null && TimeUtil.compareDifference(timeInstant, currentTime, this.delay) <= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 71 */     return "ChargingCondition{delay=" + String.valueOf(this.delay) + "} " + super
/*    */       
/* 73 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\ChargingCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */