/*    */ package com.hypixel.hytale.server.core.modules.entity.stamina;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StaminaGameplayConfig
/*    */ {
/*    */   public static final String ID = "Stamina";
/*    */   public static final BuilderCodec<StaminaGameplayConfig> CODEC;
/*    */   protected SprintRegenDelayConfig sprintRegenDelay;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(StaminaGameplayConfig.class, StaminaGameplayConfig::new).appendInherited(new KeyedCodec("SprintRegenDelay", (Codec)SprintRegenDelayConfig.CODEC), (staminaGameplayConfig, s) -> staminaGameplayConfig.sprintRegenDelay = s, staminaGameplayConfig -> staminaGameplayConfig.sprintRegenDelay, (staminaGameplayConfig, parent) -> staminaGameplayConfig.sprintRegenDelay = parent.sprintRegenDelay).addValidator(Validators.nonNull()).documentation("The stamina regeneration delay applied after sprinting").add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SprintRegenDelayConfig getSprintRegenDelay() {
/* 30 */     return this.sprintRegenDelay;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 36 */     return "StaminaGameplayConfig{sprintRegenDelay=" + String.valueOf(this.sprintRegenDelay) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class SprintRegenDelayConfig
/*    */   {
/*    */     public static final BuilderCodec<SprintRegenDelayConfig> CODEC;
/*    */ 
/*    */ 
/*    */     
/*    */     protected String statId;
/*    */ 
/*    */ 
/*    */     
/*    */     protected int statIndex;
/*    */ 
/*    */ 
/*    */     
/*    */     protected float statValue;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 63 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SprintRegenDelayConfig.class, SprintRegenDelayConfig::new).appendInherited(new KeyedCodec("EntityStatId", (Codec)Codec.STRING), (entityStatConfig, s) -> entityStatConfig.statId = s, entityStatConfig -> entityStatConfig.statId, (entityStatConfig, parent) -> entityStatConfig.statId = parent.statId).addValidator(Validators.nonNull()).addValidator(EntityStatType.VALIDATOR_CACHE.getValidator()).documentation("The ID of the stamina regen delay EntityStat").add()).appendInherited(new KeyedCodec("Value", (Codec)Codec.FLOAT), (entityStatConfig, s) -> entityStatConfig.statValue = s.floatValue(), entityStatConfig -> Float.valueOf(entityStatConfig.statValue), (entityStatConfig, parent) -> entityStatConfig.statValue = parent.statValue).addValidator(Validators.max(Float.valueOf(0.0F))).documentation("The amount of stamina regen delay to apply").add()).afterDecode(entityStatConfig -> entityStatConfig.statIndex = EntityStatType.getAssetMap().getIndex(entityStatConfig.statId))).build();
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public int getIndex() {
/* 70 */       return this.statIndex;
/*    */     }
/*    */     
/*    */     public float getValue() {
/* 74 */       return this.statValue;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 80 */       return "SprintRegenDelayConfig{statId='" + this.statId + "', statIndex=" + this.statIndex + ", statValue=" + this.statValue + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\stamina\StaminaGameplayConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */