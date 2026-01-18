/*     */ package com.hypixel.hytale.builtin.adventure.reputation.assets;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
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
/*     */ public class ReputationRank
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ReputationRank>>
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, ReputationRank> CODEC;
/*     */   
/*     */   static {
/*  52 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ReputationRank.class, ReputationRank::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data).addField(new KeyedCodec("MinValue", (Codec)Codec.INTEGER), (reputationRank, s) -> reputationRank.minValue = s.intValue(), reputationRank -> Integer.valueOf(reputationRank.minValue))).addField(new KeyedCodec("MaxValue", (Codec)Codec.INTEGER), (reputationRank, s) -> reputationRank.maxValue = s.intValue(), reputationRank -> Integer.valueOf(reputationRank.maxValue))).addField(new KeyedCodec("Attitude", (Codec)Attitude.CODEC, true), (reputationRank, s) -> reputationRank.attitude = s, reputationRank -> reputationRank.attitude)).validator((asset, results) -> { if (asset.getMinValue() >= asset.getMaxValue()) results.fail("Min value must be strictly inferior than the max value");  })).build();
/*     */   }
/*  54 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ReputationRank::getAssetStore)); private static AssetStore<String, ReputationRank, DefaultAssetMap<String, ReputationRank>> ASSET_STORE; protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, ReputationRank, DefaultAssetMap<String, ReputationRank>> getAssetStore() {
/*  58 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ReputationRank.class); 
/*  59 */     return ASSET_STORE;
/*     */   }
/*     */   protected int minValue; protected int maxValue; protected Attitude attitude;
/*     */   public static DefaultAssetMap<String, ReputationRank> getAssetMap() {
/*  63 */     return (DefaultAssetMap<String, ReputationRank>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReputationRank(String id, int minValue, int maxValue, Attitude attitude) {
/*  74 */     this.id = id;
/*  75 */     this.minValue = minValue;
/*  76 */     this.maxValue = maxValue;
/*  77 */     this.attitude = attitude;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ReputationRank() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/*  85 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getMinValue() {
/*  89 */     return this.minValue;
/*     */   }
/*     */   
/*     */   public int getMaxValue() {
/*  93 */     return this.maxValue;
/*     */   }
/*     */   
/*     */   public Attitude getAttitude() {
/*  97 */     return this.attitude;
/*     */   }
/*     */   
/*     */   public boolean containsValue(int value) {
/* 101 */     return (value >= this.minValue && value < this.maxValue);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 107 */     return "ReputationRank{id='" + this.id + "', minValue=" + this.minValue + ", maxValue=" + this.maxValue + ", attitude=" + String.valueOf(this.attitude) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\assets\ReputationRank.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */