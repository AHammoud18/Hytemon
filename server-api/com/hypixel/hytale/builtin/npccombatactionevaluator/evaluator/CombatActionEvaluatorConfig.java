/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.combatactions.CombatActionOption;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.MapUtil;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.Condition;
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
/*     */ public class CombatActionEvaluatorConfig
/*     */ {
/*     */   public static final BuilderCodec<CombatActionEvaluatorConfig> CODEC;
/*     */   
/*     */   static {
/*  86 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CombatActionEvaluatorConfig.class, CombatActionEvaluatorConfig::new).appendInherited(new KeyedCodec("AvailableActions", (Codec)new MapCodec(CombatActionOption.CHILD_ASSET_CODEC, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new)), (option, o) -> option.availableActions = MapUtil.combineUnmodifiable(option.availableActions, o), option -> option.availableActions, (option, parent) -> option.availableActions = parent.availableActions).addValidator(Validators.nonNull()).addValidator((Validator)CombatActionOption.VALIDATOR_CACHE.getMapValueValidator()).documentation("A map of all available combat actions this NPC can take.").add()).append(new KeyedCodec("ActionSets", (Codec)new MapCodec((Codec)ActionSet.CODEC, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new)), (option, o) -> option.actionSets = o, option -> option.actionSets).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyMap()).documentation("A mapping of all combat substate names to the basic attacks and abilities that should be used in them.").add()).appendInherited(new KeyedCodec("RunConditions", Condition.CHILD_ASSET_CODEC_ARRAY), (option, s) -> option.runConditions = s, option -> option.runConditions, (option, parent) -> option.runConditions = parent.runConditions).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)Condition.VALIDATOR_CACHE.getArrayValidator()).documentation("The list of conditions that determine whether or not the combat action evaluator should run.").add()).appendInherited(new KeyedCodec("MinRunUtility", (Codec)Codec.DOUBLE), (option, d) -> option.minRunUtility = d.doubleValue(), option -> Double.valueOf(option.minRunUtility), (option, parent) -> option.minRunUtility = parent.minRunUtility).addValidator(Validators.range(Double.valueOf(0.5D), Double.valueOf(1.0D))).documentation("The minimum utility score required to be returned from the RunConditions to trigger a new run of the combat action evaluator.").add()).appendInherited(new KeyedCodec("MinActionUtility", (Codec)Codec.DOUBLE), (option, d) -> option.minActionUtility = d.doubleValue(), option -> Double.valueOf(option.minActionUtility), (option, parent) -> option.minActionUtility = parent.minActionUtility).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).documentation("The minimum utility score required for any individual combat action to be run.").add()).appendInherited(new KeyedCodec("PredictabilityRange", (Codec)Codec.DOUBLE_ARRAY), (option, o) -> option.predictabilityRange = o, option -> option.predictabilityRange, (option, parent) -> option.predictabilityRange = parent.predictabilityRange).addValidator(Validators.doubleArraySize(2)).addValidator(Validators.weaklyMonotonicSequentialDoubleArrayValidator()).documentation("A random range from which to pick the NPC's predictability factor.").add()).build();
/*     */   }
/*  88 */   private static final double[] DEFAULT_PREDICTABILITY_RANGE = new double[] { 1.0D, 1.0D };
/*     */   
/*  90 */   protected Map<String, String> availableActions = Collections.emptyMap();
/*     */   protected Map<String, ActionSet> actionSets;
/*     */   protected String[] runConditions;
/*  93 */   protected double minRunUtility = 0.8D;
/*  94 */   protected double minActionUtility = 0.1D;
/*  95 */   protected double[] predictabilityRange = DEFAULT_PREDICTABILITY_RANGE;
/*     */   
/*     */   public Map<String, String> getAvailableActions() {
/*  98 */     return this.availableActions;
/*     */   }
/*     */   
/*     */   public Map<String, ActionSet> getActionSets() {
/* 102 */     return this.actionSets;
/*     */   }
/*     */   
/*     */   public String[] getRunConditions() {
/* 106 */     return this.runConditions;
/*     */   }
/*     */   
/*     */   public double getMinRunUtility() {
/* 110 */     return this.minRunUtility;
/*     */   }
/*     */   
/*     */   public double getMinActionUtility() {
/* 114 */     return this.minActionUtility;
/*     */   }
/*     */   
/*     */   public double[] getPredictabilityRange() {
/* 118 */     return this.predictabilityRange;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 124 */     return "CombatActionEvaluatorConfig{availableActions=" + String.valueOf(this.availableActions) + ", actionSets=" + String.valueOf(this.actionSets) + ", runConditions=" + 
/*     */ 
/*     */       
/* 127 */       Arrays.toString((Object[])this.runConditions) + ", minRunUtility=" + this.minRunUtility + ", minActionUtility=" + this.minActionUtility + ", predictabilityRange=" + 
/*     */ 
/*     */       
/* 130 */       Arrays.toString(this.predictabilityRange) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ActionSet
/*     */   {
/*     */     public static final BuilderCodec<ActionSet> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     protected CombatActionEvaluatorConfig.BasicAttacks basicAttacks;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String[] combatActions;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 152 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ActionSet.class, ActionSet::new).append(new KeyedCodec("BasicAttacks", (Codec)CombatActionEvaluatorConfig.BasicAttacks.CODEC), (actionSet, o) -> actionSet.basicAttacks = o, actionSet -> actionSet.basicAttacks).documentation("The basic attacks to be used in this combat substate.").add()).append(new KeyedCodec("Actions", (Codec)Codec.STRING_ARRAY), (actionSet, o) -> actionSet.combatActions = o, actionsSet -> actionsSet.combatActions).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).documentation("A list of available actions that should be used in this combat substate, mapped from AvailableActions.").add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CombatActionEvaluatorConfig.BasicAttacks getBasicAttacks() {
/* 161 */       return this.basicAttacks;
/*     */     }
/*     */     
/*     */     public String[] getCombatActions() {
/* 165 */       return this.combatActions;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 171 */       return "ActionSet{basicAttacks=" + String.valueOf(this.basicAttacks) + ", combatActions=" + 
/*     */         
/* 173 */         Arrays.toString((Object[])this.combatActions) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicAttacks
/*     */   {
/*     */     public static final BuilderCodec<BasicAttacks> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String[] attacks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean randomise;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected double maxRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected double maxRangeSquared;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 253 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BasicAttacks.class, BasicAttacks::new).append(new KeyedCodec("Attacks", RootInteraction.CHILD_ASSET_CODEC_ARRAY), (basicAttacks, o) -> basicAttacks.attacks = o, basicAttacks -> basicAttacks.attacks).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getArrayValidator()).documentation("The sequence of basic attacks to be used.").add()).append(new KeyedCodec("Randomise", (Codec)Codec.BOOLEAN), (basicAttacks, b) -> basicAttacks.randomise = b.booleanValue(), basicAttacks -> Boolean.valueOf(basicAttacks.randomise)).documentation("Whether or not the basic attacks should be executed randomly, or run in the order they were defined in.").add()).append(new KeyedCodec("MaxRange", (Codec)Codec.DOUBLE), (basicAttacks, d) -> { basicAttacks.maxRange = d.doubleValue(); basicAttacks.maxRangeSquared = d.doubleValue() * d.doubleValue(); }basicAttacks -> Double.valueOf(basicAttacks.maxRange)).addValidator(Validators.nonNull()).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).documentation("How close a target needs to be to use a basic attack against them.").add()).append(new KeyedCodec("Timeout", (Codec)Codec.FLOAT), (basicAttacks, f) -> basicAttacks.timeout = f.floatValue(), basicAttacks -> Float.valueOf(basicAttacks.timeout)).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).documentation("How long before giving up if a target moves out of range while preparing to execute a basic attack.").add()).append(new KeyedCodec("CooldownRange", (Codec)Codec.DOUBLE_ARRAY), (basicAttacks, o) -> basicAttacks.cooldownRange = o, basicAttacks -> basicAttacks.cooldownRange).addValidator(Validators.nonNull()).addValidator(Validators.doubleArraySize(2)).addValidator(Validators.weaklyMonotonicSequentialDoubleArrayValidator()).documentation("A random range to pick a cooldown between basic attacks from.").add()).appendInherited(new KeyedCodec("InteractionVars", (Codec)new MapCodec((Codec)RootInteraction.CHILD_ASSET_CODEC, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new)), (basicAttacks, v) -> basicAttacks.interactionVars = v, basicAttacks -> basicAttacks.interactionVars, (basicAttacks, parent) -> basicAttacks.interactionVars = parent.interactionVars).addValidator((Validator)RootInteraction.VALIDATOR_CACHE.getMapValueValidator()).documentation("Interaction vars to modify the values in the interaction itself.").add()).appendInherited(new KeyedCodec("DamageFriendlies", (Codec)Codec.BOOLEAN), (basicAttacks, b) -> basicAttacks.damageFriendlies = b.booleanValue(), basicAttacks -> Boolean.valueOf(basicAttacks.damageFriendlies), (basicAttacks, parent) -> basicAttacks.damageFriendlies = parent.damageFriendlies).documentation("Whether or not basic attacks should be able to damage friendly targets.").add()).appendInherited(new KeyedCodec("UseProjectedDistance", (Codec)Codec.BOOLEAN), (basicAttacks, b) -> basicAttacks.useProjectedDistance = b.booleanValue(), basicAttacks -> Boolean.valueOf(basicAttacks.useProjectedDistance), (basicAttacks, parent) -> basicAttacks.useProjectedDistance = parent.useProjectedDistance).documentation("Whether to use projected distance instead of 3D distance for checking if in range of basic attacks.").add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     protected float timeout = 2.0F;
/*     */     protected double[] cooldownRange;
/* 261 */     protected Map<String, String> interactionVars = Collections.emptyMap();
/*     */     
/*     */     protected boolean damageFriendlies;
/*     */     
/*     */     protected boolean useProjectedDistance;
/*     */ 
/*     */     
/*     */     public String[] getAttacks() {
/* 269 */       return this.attacks;
/*     */     }
/*     */     
/*     */     public boolean isRandom() {
/* 273 */       return this.randomise;
/*     */     }
/*     */     
/*     */     public double getMaxRange() {
/* 277 */       return this.maxRange;
/*     */     }
/*     */     
/*     */     public double getMaxRangeSquared() {
/* 281 */       return this.maxRangeSquared;
/*     */     }
/*     */     
/*     */     public float getTimeout() {
/* 285 */       return this.timeout;
/*     */     }
/*     */     
/*     */     public double[] getCooldownRange() {
/* 289 */       return this.cooldownRange;
/*     */     }
/*     */     
/*     */     public Map<String, String> getInteractionVars(InteractionContext c) {
/* 293 */       return this.interactionVars;
/*     */     }
/*     */     
/*     */     public boolean isDamageFriendlies() {
/* 297 */       return this.damageFriendlies;
/*     */     }
/*     */     
/*     */     public boolean shouldUseProjectedDistance() {
/* 301 */       return this.useProjectedDistance;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 308 */       return "BasicAttacks{attacks=" + Arrays.toString((Object[])this.attacks) + ", randomise=" + this.randomise + ", maxRange=" + this.maxRange + ", maxRangeSquared=" + this.maxRangeSquared + ", timeout=" + this.timeout + ", cooldownRange=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 313 */         Arrays.toString(this.cooldownRange) + ", interactionVars=" + String.valueOf(this.interactionVars) + ", damageFriendlies=" + this.damageFriendlies + ", useProjectedDistance=" + this.useProjectedDistance + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\evaluator\CombatActionEvaluatorConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */