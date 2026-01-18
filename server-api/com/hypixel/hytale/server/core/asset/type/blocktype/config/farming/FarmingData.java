/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.farming;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FarmingData
/*     */ {
/*     */   @Nonnull
/*     */   public static Codec<FarmingData> CODEC;
/*     */   protected Map<String, FarmingStageData[]> stages;
/*     */   
/*     */   static {
/*  61 */     CODEC = (Codec<FarmingData>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FarmingData.class, FarmingData::new).append(new KeyedCodec("Stages", (Codec)new MapCodec((Codec)new ArrayCodec((Codec)FarmingStageData.CODEC, x$0 -> new FarmingStageData[x$0]), java.util.HashMap::new)), (farming, stages) -> farming.stages = stages, farming -> farming.stages).add()).append(new KeyedCodec("StartingStageSet", (Codec)Codec.STRING), (farming, starting) -> farming.startingStageSet = starting, farming -> farming.startingStageSet).add()).append(new KeyedCodec("StageSetAfterHarvest", (Codec)Codec.STRING), (farming, set) -> farming.stageSetAfterHarvest = set, farming -> farming.stageSetAfterHarvest).add()).append(new KeyedCodec("ActiveGrowthModifiers", GrowthModifierAsset.CHILD_ASSET_CODEC_ARRAY), (farming, modifiers) -> farming.growthModifiers = modifiers, farming -> farming.growthModifiers).add()).appendInherited(new KeyedCodec("SoilConfig", (Codec)SoilConfig.CODEC), (o, v) -> o.soilConfig = v, o -> o.soilConfig, (o, p) -> o.soilConfig = p.soilConfig).add()).afterDecode(farmingData -> { if (farmingData != null && farmingData.getStages() != null) { if (!farmingData.getStages().containsKey(farmingData.startingStageSet)) throw new IllegalArgumentException("Invalid StartingStageSet " + farmingData.startingStageSet);  if (farmingData.stageSetAfterHarvest != null && !farmingData.getStages().containsKey(farmingData.stageSetAfterHarvest)) throw new IllegalArgumentException("Invalid StageSetAfterHarvest " + farmingData.startingStageSet);  }  })).build();
/*     */   }
/*     */   
/*  64 */   protected String startingStageSet = "Default";
/*     */   protected String stageSetAfterHarvest;
/*     */   protected String[] growthModifiers;
/*     */   @Nullable
/*     */   protected SoilConfig soilConfig;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, FarmingStageData[]> getStages() {
/*  72 */     return this.stages;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getStartingStageSet() {
/*  77 */     return this.startingStageSet;
/*     */   }
/*     */   
/*     */   public String getStageSetAfterHarvest() {
/*  81 */     return this.stageSetAfterHarvest;
/*     */   }
/*     */   
/*     */   public String[] getGrowthModifiers() {
/*  85 */     return this.growthModifiers;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SoilConfig getSoilConfig() {
/*  90 */     return this.soilConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  96 */     return "FarmingData{stages=" + String.valueOf(this.stages) + ", startingStageSet='" + this.startingStageSet + "', stageSetAfterHarvest='" + this.stageSetAfterHarvest + "', growthModifiers=" + 
/*     */ 
/*     */ 
/*     */       
/* 100 */       Arrays.toString((Object[])this.growthModifiers) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SoilConfig
/*     */   {
/*     */     public static final BuilderCodec<SoilConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String targetBlock;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Rangef lifetime;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 124 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SoilConfig.class, SoilConfig::new).appendInherited(new KeyedCodec("TargetBlock", (Codec)Codec.STRING), (o, v) -> o.targetBlock = v, o -> o.targetBlock, (o, p) -> o.targetBlock = p.targetBlock).addValidatorLate(() -> BlockType.VALIDATOR_CACHE.getValidator().late()).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Lifetime", (Codec)ProtocolCodecs.RANGEF), (o, v) -> o.lifetime = v, o -> o.lifetime, (o, p) -> o.lifetime = p.lifetime).addValidator(Validators.nonNull()).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTargetBlock() {
/* 130 */       return this.targetBlock;
/*     */     }
/*     */     
/*     */     public Rangef getLifetime() {
/* 134 */       return this.lifetime;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\farming\FarmingData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */