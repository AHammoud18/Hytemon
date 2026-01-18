/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.Object2FloatMapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.ChangeStatBehaviour;
/*    */ import com.hypixel.hytale.protocol.ValueType;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
/*    */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2FloatMap;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ public abstract class ChangeStatBaseInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   public static final BuilderCodec<ChangeStatBaseInteraction> CODEC;
/*    */   protected Object2FloatMap<String> entityStatAssets;
/*    */   @Nullable
/*    */   protected Int2FloatMap entityStats;
/*    */   
/*    */   static {
/* 60 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ChangeStatBaseInteraction.class, SimpleInstantInteraction.CODEC).append(new KeyedCodec("StatModifiers", (Codec)new Object2FloatMapCodec((Codec)Codec.STRING, it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap::new), true), (changeStatInteraction, stringObject2DoubleMap) -> changeStatInteraction.entityStatAssets = stringObject2DoubleMap, changeStatInteraction -> changeStatInteraction.entityStatAssets).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyMap()).addValidator((Validator)EntityStatType.VALIDATOR_CACHE.getMapKeyValidator()).documentation("Modifiers to apply to EntityStats.").add()).append(new KeyedCodec("ValueType", (Codec)new EnumCodec(ValueType.class)), (changeStatInteraction, valueType) -> changeStatInteraction.valueType = valueType, changeStatInteraction -> changeStatInteraction.valueType).documentation("Enum to specify if the StatModifiers must be considered as absolute values or percent. Default value is Absolute. When using ValueType.Absolute, '100' matches the max value.").add()).append(new KeyedCodec("Behaviour", (Codec)ProtocolCodecs.CHANGE_STAT_BEHAVIOUR_CODEC), (changeStatInteraction, changeStatBehaviour) -> changeStatInteraction.changeStatBehaviour = changeStatBehaviour, changeStatInteraction -> changeStatInteraction.changeStatBehaviour).documentation("Specifies how StatModifiers should be applied to the stats.").add()).appendInherited(new KeyedCodec("Entity", (Codec)InteractionTarget.CODEC), (o, i) -> o.entityTarget = i, o -> o.entityTarget, (o, p) -> o.entityTarget = p.entityTarget).documentation("The entity to target for this interaction.").addValidator(Validators.nonNull()).add()).afterDecode(changeStatInteraction -> changeStatInteraction.entityStats = EntityStatsModule.resolveEntityStats(changeStatInteraction.entityStatAssets))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 65 */   protected ValueType valueType = ValueType.Absolute;
/* 66 */   protected ChangeStatBehaviour changeStatBehaviour = ChangeStatBehaviour.Add;
/* 67 */   protected InteractionTarget entityTarget = InteractionTarget.USER;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "ChangeStatBaseInteraction{unknownEntityStats=" + String.valueOf(this.entityStatAssets) + ", entityStats=" + String.valueOf(this.entityStats) + ", valueType=" + String.valueOf(this.valueType) + ", changeStatBehaviour=" + String.valueOf(this.changeStatBehaviour) + ", entityTarget=" + String.valueOf(this.entityTarget) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\ChangeStatBaseInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */