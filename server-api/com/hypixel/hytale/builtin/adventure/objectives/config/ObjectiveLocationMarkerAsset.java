/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.markerarea.ObjectiveLocationMarkerArea;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.objectivesetup.ObjectiveTypeSetup;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.triggercondition.ObjectiveLocationTriggerCondition;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import java.util.Arrays;
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
/*     */ public class ObjectiveLocationMarkerAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ObjectiveLocationMarkerAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ObjectiveLocationMarkerAsset> CODEC;
/*     */   
/*     */   static {
/*  67 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ObjectiveLocationMarkerAsset.class, ObjectiveLocationMarkerAsset::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("Setup", (Codec)ObjectiveTypeSetup.CODEC), (objectiveLocationMarkerAsset, objectiveTypeSetup) -> objectiveLocationMarkerAsset.objectiveTypeSetup = objectiveTypeSetup, objectiveLocationMarkerAsset -> objectiveLocationMarkerAsset.objectiveTypeSetup).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Area", (Codec)ObjectiveLocationMarkerArea.CODEC), (objectiveLocationMarkerAsset, area) -> objectiveLocationMarkerAsset.area = area, objectiveLocationMarkerAsset -> objectiveLocationMarkerAsset.area).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("EnvironmentIds", (Codec)Codec.STRING_ARRAY), (objectiveLocationMarkerAsset, strings) -> objectiveLocationMarkerAsset.environmentIds = strings, objectiveLocationMarkerAsset -> objectiveLocationMarkerAsset.environmentIds).addValidator((Validator)Environment.VALIDATOR_CACHE.getArrayValidator()).add()).append(new KeyedCodec("TriggerConditions", (Codec)new ArrayCodec((Codec)ObjectiveLocationTriggerCondition.CODEC, x$0 -> new ObjectiveLocationTriggerCondition[x$0])), (objectiveLocationMarkerAsset, objectiveLocationTriggerConditions) -> objectiveLocationMarkerAsset.triggerConditions = objectiveLocationTriggerConditions, objectiveLocationMarkerAsset -> objectiveLocationMarkerAsset.triggerConditions).add()).afterDecode(objectiveLocationMarkerAsset -> { if (objectiveLocationMarkerAsset.environmentIds != null && objectiveLocationMarkerAsset.environmentIds.length > 0) { objectiveLocationMarkerAsset.environmentIndexes = new int[objectiveLocationMarkerAsset.environmentIds.length]; for (int i = 0; i < objectiveLocationMarkerAsset.environmentIds.length; i++) { String key = objectiveLocationMarkerAsset.environmentIds[i]; int index = Environment.getAssetMap().getIndex(key); if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key);  objectiveLocationMarkerAsset.environmentIndexes[i] = index; }  Arrays.sort(objectiveLocationMarkerAsset.environmentIndexes); }  })).build();
/*     */   }
/*  69 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ObjectiveLocationMarkerAsset::getAssetStore)); private static AssetStore<String, ObjectiveLocationMarkerAsset, DefaultAssetMap<String, ObjectiveLocationMarkerAsset>> ASSET_STORE; protected AssetExtraInfo.Data data; protected String id; protected ObjectiveTypeSetup objectiveTypeSetup; protected ObjectiveLocationMarkerArea area; protected String[] environmentIds;
/*     */   protected int[] environmentIndexes;
/*     */   protected ObjectiveLocationTriggerCondition[] triggerConditions;
/*     */   
/*     */   public static AssetStore<String, ObjectiveLocationMarkerAsset, DefaultAssetMap<String, ObjectiveLocationMarkerAsset>> getAssetStore() {
/*  74 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ObjectiveLocationMarkerAsset.class); 
/*  75 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, ObjectiveLocationMarkerAsset> getAssetMap() {
/*  79 */     return (DefaultAssetMap<String, ObjectiveLocationMarkerAsset>)getAssetStore().getAssetMap();
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
/*     */   public String getId() {
/*  96 */     return this.id;
/*     */   }
/*     */   
/*     */   public ObjectiveTypeSetup getObjectiveTypeSetup() {
/* 100 */     return this.objectiveTypeSetup;
/*     */   }
/*     */   
/*     */   public ObjectiveLocationMarkerArea getArea() {
/* 104 */     return this.area;
/*     */   }
/*     */   
/*     */   public String[] getEnvironmentIds() {
/* 108 */     return this.environmentIds;
/*     */   }
/*     */   
/*     */   public int[] getEnvironmentIndexes() {
/* 112 */     return this.environmentIndexes;
/*     */   }
/*     */   
/*     */   public ObjectiveLocationTriggerCondition[] getTriggerConditions() {
/* 116 */     return this.triggerConditions;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 122 */     return "ObjectiveLocationMarkerAsset{id='" + this.id + "', objectiveTypeSetup=" + String.valueOf(this.objectiveTypeSetup) + ", area=" + String.valueOf(this.area) + ", environmentIds=" + 
/*     */ 
/*     */ 
/*     */       
/* 126 */       Arrays.toString((Object[])this.environmentIds) + ", triggerConditions=" + 
/* 127 */       Arrays.toString((Object[])this.triggerConditions) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\ObjectiveLocationMarkerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */