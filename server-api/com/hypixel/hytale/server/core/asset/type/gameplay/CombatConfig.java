/*     */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import java.time.Duration;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombatConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<CombatConfig> CODEC;
/*     */   
/*     */   static {
/*  76 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CombatConfig.class, CombatConfig::new).appendInherited(new KeyedCodec("OutOfCombatDelaySeconds", (Codec)Codec.DURATION_SECONDS), (combatConfig, v) -> combatConfig.outOfCombatDelay = v, combatConfig -> combatConfig.outOfCombatDelay, (combatConfig, parent) -> combatConfig.outOfCombatDelay = parent.outOfCombatDelay).documentation("Delay before an entity is considered out of combat. Expressed in seconds.").addValidator(Validators.greaterThanOrEqual(Duration.ZERO)).add()).appendInherited(new KeyedCodec("StaminaBrokenEffectId", (Codec)Codec.STRING), (combatConfig, s) -> combatConfig.staminaBrokenEffectId = s, combatConfig -> combatConfig.staminaBrokenEffectId, (combatConfig, parent) -> combatConfig.staminaBrokenEffectId = parent.staminaBrokenEffectId).documentation("The id of the EntityEffect to apply upon stamina being depleted due to damage.").addValidator(Validators.nonNull()).addValidator(EntityEffect.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("DisplayHealthBars", (Codec)Codec.BOOLEAN), (combatConfig, v) -> combatConfig.displayHealthBars = v.booleanValue(), combatConfig -> Boolean.valueOf(combatConfig.displayHealthBars), (combatConfig, parent) -> combatConfig.displayHealthBars = parent.displayHealthBars).documentation("Whether to display health bars above entities. Clients can still disable this in their settings.").add()).appendInherited(new KeyedCodec("DisplayCombatText", (Codec)Codec.BOOLEAN), (combatConfig, v) -> combatConfig.displayCombatText = v.booleanValue(), combatConfig -> Boolean.valueOf(combatConfig.displayCombatText), (combatConfig, parent) -> combatConfig.displayCombatText = parent.displayCombatText).documentation("Whether to display combat text (damage numbers) on entities. Clients can still disable this in their settings.").add()).afterDecode(combatConfig -> combatConfig.staminaBrokenEffectIndex = EntityEffect.getAssetMap().getIndex(combatConfig.staminaBrokenEffectId))).appendInherited(new KeyedCodec("DisableNPCIncomingDamage", (Codec)Codec.BOOLEAN), (combatConfig, v) -> combatConfig.disableNpcIncomingDamage = v.booleanValue(), combatConfig -> Boolean.valueOf(combatConfig.disableNpcIncomingDamage), (combatConfig, parent) -> combatConfig.disableNpcIncomingDamage = parent.disableNpcIncomingDamage).documentation("Whether NPCs can take damage.").add()).appendInherited(new KeyedCodec("DisablePlayerIncomingDamage", (Codec)Codec.BOOLEAN), (combatConfig, v) -> combatConfig.disablePlayerIncomingDamage = v.booleanValue(), combatConfig -> Boolean.valueOf(combatConfig.disablePlayerIncomingDamage), (combatConfig, parent) -> combatConfig.disablePlayerIncomingDamage = parent.disablePlayerIncomingDamage).documentation("Whether players can take damage.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  82 */   protected Duration outOfCombatDelay = Duration.ofMillis(5000L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   protected String staminaBrokenEffectId = "Stamina_Broken";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int staminaBrokenEffectIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displayHealthBars = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean displayCombatText = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableNpcIncomingDamage = false;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disablePlayerIncomingDamage = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombatConfig() {
/* 115 */     this.staminaBrokenEffectIndex = EntityEffect.getAssetMap().getIndex(this.staminaBrokenEffectId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Duration getOutOfCombatDelay() {
/* 123 */     return this.outOfCombatDelay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStaminaBrokenEffectIndex() {
/* 130 */     return this.staminaBrokenEffectIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayHealthBars() {
/* 137 */     return this.displayHealthBars;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisplayCombatText() {
/* 144 */     return this.displayCombatText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNpcIncomingDamageDisabled() {
/* 151 */     return this.disableNpcIncomingDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerIncomingDamageDisabled() {
/* 158 */     return this.disablePlayerIncomingDamage;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\CombatConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */