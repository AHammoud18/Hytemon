/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders.WorldLocationProvider;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TreasureMapObjectiveTaskAsset
/*     */   extends ObjectiveTaskAsset {
/*     */   public static final BuilderCodec<TreasureMapObjectiveTaskAsset> CODEC;
/*     */   protected ChestConfig[] chestConfigs;
/*     */   
/*     */   static {
/*  26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TreasureMapObjectiveTaskAsset.class, TreasureMapObjectiveTaskAsset::new, BASE_CODEC).append(new KeyedCodec("Chests", (Codec)new ArrayCodec((Codec)ChestConfig.CODEC, x$0 -> new ChestConfig[x$0])), (treasureMapObjectiveTaskAsset, chestConfigs) -> treasureMapObjectiveTaskAsset.chestConfigs = chestConfigs, treasureMapObjectiveTaskAsset -> treasureMapObjectiveTaskAsset.chestConfigs).addValidator(Validators.nonEmptyArray()).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public TreasureMapObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, ChestConfig[] chestConfigs) {
/*  31 */     super(descriptionId, taskConditions, mapMarkers);
/*  32 */     this.chestConfigs = chestConfigs;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TreasureMapObjectiveTaskAsset() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ObjectiveTaskAsset.TaskScope getTaskScope() {
/*  41 */     return ObjectiveTaskAsset.TaskScope.PLAYER;
/*     */   }
/*     */   
/*     */   public ChestConfig[] getChestConfigs() {
/*  45 */     return this.chestConfigs;
/*     */   }
/*     */   
/*     */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/*     */     TreasureMapObjectiveTaskAsset treasureMapObjectiveTaskAsset;
/*  50 */     if (task instanceof TreasureMapObjectiveTaskAsset) { treasureMapObjectiveTaskAsset = (TreasureMapObjectiveTaskAsset)task; } else { return false; }
/*     */     
/*  52 */     return Arrays.equals((Object[])treasureMapObjectiveTaskAsset.chestConfigs, (Object[])this.chestConfigs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  59 */     return "TreasureMapObjectiveTaskAsset{chestConfigs=" + Arrays.toString((Object[])this.chestConfigs) + "} " + super
/*  60 */       .toString();
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
/*     */   public static class ChestConfig
/*     */   {
/*     */     public static final BuilderCodec<ChestConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  95 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChestConfig.class, ChestConfig::new).append(new KeyedCodec("MinRadius", (Codec)Codec.FLOAT), (chestConfig, aFloat) -> chestConfig.minRadius = aFloat.floatValue(), chestConfig -> Float.valueOf(chestConfig.minRadius)).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).append(new KeyedCodec("MaxRadius", (Codec)Codec.FLOAT), (chestConfig, aFloat) -> chestConfig.maxRadius = aFloat.floatValue(), chestConfig -> Float.valueOf(chestConfig.maxRadius)).addValidator(Validators.greaterThan(Float.valueOf(1.0F))).add()).append(new KeyedCodec("DropList", (Codec)new ContainedAssetCodec(ItemDropList.class, (AssetCodec)ItemDropList.CODEC)), (chestConfig, s) -> chestConfig.droplistId = s, chestConfig -> chestConfig.droplistId).addValidator(Validators.nonNull()).addValidator(ItemDropList.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("WorldLocationCondition", (Codec)WorldLocationProvider.CODEC), (chestConfig, worldLocationCondition) -> chestConfig.worldLocationProvider = worldLocationCondition, chestConfig -> chestConfig.worldLocationProvider).add()).append(new KeyedCodec("ChestBlockTypeKey", (Codec)Codec.STRING), (chestConfig, blockTypeKey) -> chestConfig.chestBlockTypeKey = blockTypeKey, chestConfig -> chestConfig.chestBlockTypeKey).addValidator(Validators.nonNull()).addValidator(BlockType.VALIDATOR_CACHE.getValidator()).add()).afterDecode(chestConfig -> { if (chestConfig.minRadius >= chestConfig.maxRadius) throw new IllegalArgumentException("ChestConfig.MinRadius (" + chestConfig.minRadius + ") needs to be greater than ChestConfig.MaxRadius (" + chestConfig.maxRadius + ")");  })).build();
/*     */     }
/*  97 */     protected float minRadius = 10.0F;
/*  98 */     protected float maxRadius = 20.0F;
/*     */     protected String droplistId;
/*     */     protected WorldLocationProvider worldLocationProvider;
/*     */     protected String chestBlockTypeKey;
/*     */     
/*     */     public float getMinRadius() {
/* 104 */       return this.minRadius;
/*     */     }
/*     */     
/*     */     public float getMaxRadius() {
/* 108 */       return this.maxRadius;
/*     */     }
/*     */     
/*     */     public String getDroplistId() {
/* 112 */       return this.droplistId;
/*     */     }
/*     */     
/*     */     public WorldLocationProvider getWorldLocationProvider() {
/* 116 */       return this.worldLocationProvider;
/*     */     }
/*     */     
/*     */     public String getChestBlockTypeKey() {
/* 120 */       return this.chestBlockTypeKey;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 125 */       if (this == o) return true; 
/* 126 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 128 */       ChestConfig that = (ChestConfig)o;
/*     */       
/* 130 */       if (Float.compare(that.minRadius, this.minRadius) != 0) return false; 
/* 131 */       if (Float.compare(that.maxRadius, this.maxRadius) != 0) return false; 
/* 132 */       if ((this.droplistId != null) ? !this.droplistId.equals(that.droplistId) : (that.droplistId != null)) return false; 
/* 133 */       if ((this.worldLocationProvider != null) ? !this.worldLocationProvider.equals(that.worldLocationProvider) : (that.worldLocationProvider != null))
/* 134 */         return false; 
/* 135 */       return (this.chestBlockTypeKey != null) ? this.chestBlockTypeKey.equals(that.chestBlockTypeKey) : ((that.chestBlockTypeKey == null));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 140 */       int result = (this.minRadius != 0.0F) ? Float.floatToIntBits(this.minRadius) : 0;
/* 141 */       result = 31 * result + ((this.maxRadius != 0.0F) ? Float.floatToIntBits(this.maxRadius) : 0);
/* 142 */       result = 31 * result + ((this.droplistId != null) ? this.droplistId.hashCode() : 0);
/* 143 */       result = 31 * result + ((this.worldLocationProvider != null) ? this.worldLocationProvider.hashCode() : 0);
/* 144 */       result = 31 * result + ((this.chestBlockTypeKey != null) ? this.chestBlockTypeKey.hashCode() : 0);
/* 145 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 151 */       return "ChestConfig{minRadius=" + this.minRadius + ", maxRadius=" + this.maxRadius + ", droplistId='" + this.droplistId + "', worldLocationCondition=" + String.valueOf(this.worldLocationProvider) + ", chestBlockTypeKey=" + this.chestBlockTypeKey + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\TreasureMapObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */