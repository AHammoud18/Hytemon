/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.Object2FloatMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.ValueType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StatsConditionBaseInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<StatsConditionBaseInteraction> CODEC;
/*     */   protected Object2FloatMap<String> rawCosts;
/*     */   @Nullable
/*     */   protected Int2FloatMap costs;
/*     */   protected boolean lessThan;
/*     */   protected boolean lenient;
/*     */   
/*     */   static {
/*  67 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(StatsConditionBaseInteraction.class, SimpleInstantInteraction.CODEC).appendInherited(new KeyedCodec("Costs", (Codec)new Object2FloatMapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap::new)), (i, s) -> i.rawCosts = s, i -> i.rawCosts, (i, parent) -> i.rawCosts = parent.rawCosts).addValidator(Validators.nonNull()).addValidator((Validator)EntityStatType.VALIDATOR_CACHE.getMapKeyValidator()).add()).appendInherited(new KeyedCodec("LessThan", (Codec)Codec.BOOLEAN), (statsConditionInteraction, aBoolean) -> statsConditionInteraction.lessThan = aBoolean.booleanValue(), statsConditionInteraction -> Boolean.valueOf(statsConditionInteraction.lessThan), (statsConditionInteraction, parent) -> statsConditionInteraction.lessThan = parent.lessThan).add()).appendInherited(new KeyedCodec("Lenient", (Codec)Codec.BOOLEAN), (statsConditionInteraction, aBoolean) -> statsConditionInteraction.lenient = aBoolean.booleanValue(), statsConditionInteraction -> Boolean.valueOf(statsConditionInteraction.lenient), (statsConditionInteraction, parent) -> statsConditionInteraction.lenient = parent.lenient).documentation("Specifies that the interaction can run even if the stat cost is not met, providing that the value is greater than zero.").add()).appendInherited(new KeyedCodec("ValueType", (Codec)new EnumCodec(ValueType.class)), (statsConditionInteraction, valueType) -> statsConditionInteraction.valueType = valueType, statsConditionInteraction -> statsConditionInteraction.valueType, (statsConditionInteraction, parent) -> statsConditionInteraction.valueType = parent.valueType).documentation("Enum to specify if the Costs must be considered as absolute values or percent. Default value is Absolute. When using ValueType.Absolute, '100' matches the max value.").add()).afterDecode(c -> c.costs = EntityStatsModule.resolveEntityStats(c.rawCosts))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   protected ValueType valueType = ValueType.Absolute;
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  78 */     Ref<EntityStore> ref = context.getEntity();
/*  79 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*     */     
/*  81 */     if (!canAfford(ref, (ComponentAccessor<EntityStore>)commandBuffer)) {
/*  82 */       (context.getState()).state = InteractionState.Failed;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canOverdraw(float value, float min) {
/*  90 */     return (this.lenient && value > 0.0F && min < 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  96 */     return "StatsConditionBaseInteraction{rawCosts=" + String.valueOf(this.rawCosts) + ", costs=" + String.valueOf(this.costs) + ", lessThan=" + this.lessThan + ", lenient=" + this.lenient + ", valueType=" + String.valueOf(this.valueType) + "}" + super
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       .toString();
/*     */   }
/*     */   
/*     */   protected abstract boolean canAfford(@Nonnull Ref<EntityStore> paramRef, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\StatsConditionBaseInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */