/*     */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.HasTargetCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.IsInStateCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.LineOfSightCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.NearbyCountCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.RandomiserCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.SelfStatAbsoluteCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.SelfStatPercentageCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.TargetDistanceCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.TargetMovementStateCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.TargetStatAbsoluteCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.TargetStatPercentageCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.TimeOfDayCondition;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.TimeSinceLastUsedCondition;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Condition
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, Condition>>
/*     */ {
/*     */   public static final double NO_TARGET = 1.7976931348623157E308D;
/*     */   public static final int ALWAYS_TRUE_SIMPLICITY = 0;
/*     */   public static final int BOOLEAN_CHECK_SIMPLICITY = 10;
/*     */   public static final int NORMALISED_CURVE_SIMPLICITY = 20;
/*     */   public static final int SCALED_CURVE_SIMPLICITY = 30;
/*     */   public static final int HIGH_COST_SIMPLICITY = 40;
/*     */   public static final AssetCodecMapCodec<String, Condition> CODEC;
/*     */   public static final BuilderCodec<Condition> BASE_CODEC;
/*     */   
/*     */   static {
/*  59 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(Condition.class).afterDecode(condition -> condition.reference = new WeakReference<>(condition))).build();
/*     */   }
/*  71 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(Condition.class, (AssetCodec)CODEC); static {
/*  72 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/*     */   }
/*  74 */   public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(Condition::getAssetStore)); private static AssetStore<String, Condition, IndexedLookupTableAssetMap<String, Condition>> ASSET_STORE; protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   protected WeakReference<Condition> reference;
/*     */   
/*     */   public static AssetStore<String, Condition, IndexedLookupTableAssetMap<String, Condition>> getAssetStore() {
/*  79 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(Condition.class); 
/*  80 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, Condition> getAssetMap() {
/*  84 */     return (IndexedLookupTableAssetMap<String, Condition>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Condition(String id) {
/*  94 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Condition() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/* 102 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupNPC(Role role) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupNPC(Holder<EntityStore> holder) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public WeakReference<Condition> getReference() {
/* 117 */     return this.reference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 143 */     return "Condition{id='" + this.id + "'}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Condition getAlwaysTrueFor(String id) {
/* 150 */     return new AlwaysTrueCondition(id);
/*     */   }
/*     */   
/*     */   static {
/* 154 */     CODEC.register("OwnStatPercent", SelfStatPercentageCondition.class, SelfStatPercentageCondition.CODEC);
/* 155 */     CODEC.register("TargetStatPercent", TargetStatPercentageCondition.class, TargetStatPercentageCondition.CODEC);
/* 156 */     CODEC.register("OwnStatAbsolute", SelfStatAbsoluteCondition.class, SelfStatAbsoluteCondition.CODEC);
/* 157 */     CODEC.register("TargetStatAbsolute", TargetStatAbsoluteCondition.class, TargetStatAbsoluteCondition.CODEC);
/* 158 */     CODEC.register("HasTarget", HasTargetCondition.class, HasTargetCondition.CODEC);
/* 159 */     CODEC.register("TimeOfDay", TimeOfDayCondition.class, TimeOfDayCondition.CODEC);
/* 160 */     CODEC.register("IsInState", IsInStateCondition.class, IsInStateCondition.CODEC);
/* 161 */     CODEC.register("NearbyCount", NearbyCountCondition.class, NearbyCountCondition.CODEC);
/* 162 */     CODEC.register("TimeSinceLastUsed", TimeSinceLastUsedCondition.class, TimeSinceLastUsedCondition.CODEC);
/* 163 */     CODEC.register("TargetDistance", TargetDistanceCondition.class, TargetDistanceCondition.CODEC);
/* 164 */     CODEC.register("Randomiser", RandomiserCondition.class, RandomiserCondition.CODEC);
/* 165 */     CODEC.register("LineOfSight", LineOfSightCondition.class, LineOfSightCondition.CODEC);
/* 166 */     CODEC.register("TargetMovementState", TargetMovementStateCondition.class, TargetMovementStateCondition.CODEC);
/*     */   }
/*     */   
/*     */   public abstract double calculateUtility(int paramInt, ArchetypeChunk<EntityStore> paramArchetypeChunk, Ref<EntityStore> paramRef, CommandBuffer<EntityStore> paramCommandBuffer, EvaluationContext paramEvaluationContext);
/*     */   
/*     */   public abstract int getSimplicity();
/*     */   
/*     */   private static class AlwaysTrueCondition extends Condition { private AlwaysTrueCondition(String id) {
/* 174 */       super(id);
/*     */     }
/*     */ 
/*     */     
/*     */     public double calculateUtility(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 179 */       return 1.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSimplicity() {
/* 184 */       return 0;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\base\Condition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */