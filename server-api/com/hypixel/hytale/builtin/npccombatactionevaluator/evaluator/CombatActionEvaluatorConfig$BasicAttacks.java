/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
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
/*     */ public class BasicAttacks
/*     */ {
/*     */   public static final BuilderCodec<BasicAttacks> CODEC;
/*     */   protected String[] attacks;
/*     */   protected boolean randomise;
/*     */   protected double maxRange;
/*     */   protected double maxRangeSquared;
/*     */   
/*     */   static {
/* 253 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BasicAttacks.class, BasicAttacks::new).append(new KeyedCodec("Attacks", RootInteraction.CHILD_ASSET_CODEC_ARRAY), (basicAttacks, o) -> basicAttacks.attacks = o, basicAttacks -> basicAttacks.attacks).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getArrayValidator()).documentation("The sequence of basic attacks to be used.").add()).append(new KeyedCodec("Randomise", (Codec)Codec.BOOLEAN), (basicAttacks, b) -> basicAttacks.randomise = b.booleanValue(), basicAttacks -> Boolean.valueOf(basicAttacks.randomise)).documentation("Whether or not the basic attacks should be executed randomly, or run in the order they were defined in.").add()).append(new KeyedCodec("MaxRange", (Codec)Codec.DOUBLE), (basicAttacks, d) -> { basicAttacks.maxRange = d.doubleValue(); basicAttacks.maxRangeSquared = d.doubleValue() * d.doubleValue(); }basicAttacks -> Double.valueOf(basicAttacks.maxRange)).addValidator(Validators.nonNull()).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).documentation("How close a target needs to be to use a basic attack against them.").add()).append(new KeyedCodec("Timeout", (Codec)Codec.FLOAT), (basicAttacks, f) -> basicAttacks.timeout = f.floatValue(), basicAttacks -> Float.valueOf(basicAttacks.timeout)).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).documentation("How long before giving up if a target moves out of range while preparing to execute a basic attack.").add()).append(new KeyedCodec("CooldownRange", (Codec)Codec.DOUBLE_ARRAY), (basicAttacks, o) -> basicAttacks.cooldownRange = o, basicAttacks -> basicAttacks.cooldownRange).addValidator(Validators.nonNull()).addValidator(Validators.doubleArraySize(2)).addValidator(Validators.weaklyMonotonicSequentialDoubleArrayValidator()).documentation("A random range to pick a cooldown between basic attacks from.").add()).appendInherited(new KeyedCodec("InteractionVars", (Codec)new MapCodec((Codec)RootInteraction.CHILD_ASSET_CODEC, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new)), (basicAttacks, v) -> basicAttacks.interactionVars = v, basicAttacks -> basicAttacks.interactionVars, (basicAttacks, parent) -> basicAttacks.interactionVars = parent.interactionVars).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getMapValueValidator()).documentation("Interaction vars to modify the values in the interaction itself.").add()).appendInherited(new KeyedCodec("DamageFriendlies", (Codec)Codec.BOOLEAN), (basicAttacks, b) -> basicAttacks.damageFriendlies = b.booleanValue(), basicAttacks -> Boolean.valueOf(basicAttacks.damageFriendlies), (basicAttacks, parent) -> basicAttacks.damageFriendlies = parent.damageFriendlies).documentation("Whether or not basic attacks should be able to damage friendly targets.").add()).appendInherited(new KeyedCodec("UseProjectedDistance", (Codec)Codec.BOOLEAN), (basicAttacks, b) -> basicAttacks.useProjectedDistance = b.booleanValue(), basicAttacks -> Boolean.valueOf(basicAttacks.useProjectedDistance), (basicAttacks, parent) -> basicAttacks.useProjectedDistance = parent.useProjectedDistance).documentation("Whether to use projected distance instead of 3D distance for checking if in range of basic attacks.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 259 */   protected float timeout = 2.0F;
/*     */   protected double[] cooldownRange;
/* 261 */   protected Map<String, String> interactionVars = Collections.emptyMap();
/*     */   
/*     */   protected boolean damageFriendlies;
/*     */   
/*     */   protected boolean useProjectedDistance;
/*     */ 
/*     */   
/*     */   public String[] getAttacks() {
/* 269 */     return this.attacks;
/*     */   }
/*     */   
/*     */   public boolean isRandom() {
/* 273 */     return this.randomise;
/*     */   }
/*     */   
/*     */   public double getMaxRange() {
/* 277 */     return this.maxRange;
/*     */   }
/*     */   
/*     */   public double getMaxRangeSquared() {
/* 281 */     return this.maxRangeSquared;
/*     */   }
/*     */   
/*     */   public float getTimeout() {
/* 285 */     return this.timeout;
/*     */   }
/*     */   
/*     */   public double[] getCooldownRange() {
/* 289 */     return this.cooldownRange;
/*     */   }
/*     */   
/*     */   public Map<String, String> getInteractionVars(InteractionContext c) {
/* 293 */     return this.interactionVars;
/*     */   }
/*     */   
/*     */   public boolean isDamageFriendlies() {
/* 297 */     return this.damageFriendlies;
/*     */   }
/*     */   
/*     */   public boolean shouldUseProjectedDistance() {
/* 301 */     return this.useProjectedDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 308 */     return "BasicAttacks{attacks=" + Arrays.toString((Object[])this.attacks) + ", randomise=" + this.randomise + ", maxRange=" + this.maxRange + ", maxRangeSquared=" + this.maxRangeSquared + ", timeout=" + this.timeout + ", cooldownRange=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 313 */       Arrays.toString(this.cooldownRange) + ", interactionVars=" + String.valueOf(this.interactionVars) + ", damageFriendlies=" + this.damageFriendlies + ", useProjectedDistance=" + this.useProjectedDistance + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\evaluator\CombatActionEvaluatorConfig$BasicAttacks.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */