/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config;
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
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectiveLineAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ObjectiveLineAsset>>
/*     */ {
/*     */   @Nonnull
/*     */   public static AssetBuilderCodec<String, ObjectiveLineAsset> CODEC;
/*     */   
/*     */   static {
/*  69 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ObjectiveLineAsset.class, ObjectiveLineAsset::new, (Codec)Codec.STRING, (objectiveLine, s) -> objectiveLine.id = s, objectiveLine -> objectiveLine.id, (objectiveLine, data) -> objectiveLine.extraData = data, objectiveLine -> objectiveLine.extraData).appendInherited(new KeyedCodec("Category", (Codec)Codec.STRING), (objectiveAsset, s) -> objectiveAsset.category = s, objectiveAsset -> objectiveAsset.category, (objectiveAsset, parent) -> objectiveAsset.category = parent.category).add()).appendInherited(new KeyedCodec("ObjectiveIds", (Codec)Codec.STRING_ARRAY), (objectiveLineAsset, strings) -> objectiveLineAsset.objectiveIds = strings, objectiveLineAsset -> objectiveLineAsset.objectiveIds, (objectiveLineAsset, parent) -> objectiveLineAsset.objectiveIds = parent.objectiveIds).addValidator(Validators.nonEmptyArray()).addValidator(Validators.uniqueInArray()).addValidator((Validator)ObjectiveAsset.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("NextObjectiveLineIds", (Codec)Codec.STRING_ARRAY), (objectiveLineAsset, strings) -> objectiveLineAsset.nextObjectiveLineIds = strings, objectiveLineAsset -> objectiveLineAsset.nextObjectiveLineIds, (objectiveLineAsset, parent) -> objectiveLineAsset.nextObjectiveLineIds = parent.nextObjectiveLineIds).addValidator(Validators.uniqueInArray()).add()).appendInherited(new KeyedCodec("TitleId", (Codec)Codec.STRING), (objectiveLineAsset, s) -> objectiveLineAsset.objectiveTitleKey = s, objectiveLineAsset -> objectiveLineAsset.objectiveTitleKey, (objectiveLineAsset, parent) -> objectiveLineAsset.objectiveTitleKey = parent.objectiveTitleKey).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("objectivelines.{assetId}.title", true))).add()).appendInherited(new KeyedCodec("DescriptionId", (Codec)Codec.STRING), (objectiveLineAsset, s) -> objectiveLineAsset.objectiveDescriptionKey = s, objectiveLineAsset -> objectiveLineAsset.objectiveDescriptionKey, (objectiveLineAsset, parent) -> objectiveLineAsset.objectiveDescriptionKey = parent.objectiveDescriptionKey).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("objectivelines.{assetId}.desc", true))).add()).afterDecode(objectiveAsset -> { if (objectiveAsset.objectiveTitleKey != null) objectiveAsset.objectiveTitleKey = MessageFormat.format("objectivelines.{0}.title", new Object[] { objectiveAsset.id });  if (objectiveAsset.objectiveDescriptionKey != null) objectiveAsset.objectiveDescriptionKey = MessageFormat.format("objectivelines.{0}.desc", new Object[] { objectiveAsset.id });  })).build();
/*     */   }
/*  71 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ObjectiveLineAsset::getAssetStore)); private static AssetStore<String, ObjectiveLineAsset, DefaultAssetMap<String, ObjectiveLineAsset>> ASSET_STORE; protected AssetExtraInfo.Data extraData;
/*     */   protected String id;
/*     */   protected String category;
/*     */   
/*     */   public static AssetStore<String, ObjectiveLineAsset, DefaultAssetMap<String, ObjectiveLineAsset>> getAssetStore() {
/*  76 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ObjectiveLineAsset.class); 
/*  77 */     return ASSET_STORE;
/*     */   }
/*     */   protected String[] objectiveIds; protected String objectiveTitleKey; protected String objectiveDescriptionKey; protected String[] nextObjectiveLineIds;
/*     */   public static DefaultAssetMap<String, ObjectiveLineAsset> getAssetMap() {
/*  81 */     return (DefaultAssetMap<String, ObjectiveLineAsset>)getAssetStore().getAssetMap();
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
/*     */   public ObjectiveLineAsset(String id, String category, String[] objectiveIds, String objectiveTitleKey, String objectiveDescriptionKey, String[] nextObjectiveLineIds) {
/*  94 */     this.id = id;
/*  95 */     this.category = category;
/*  96 */     this.objectiveIds = objectiveIds;
/*  97 */     this.objectiveTitleKey = objectiveTitleKey;
/*  98 */     this.objectiveDescriptionKey = objectiveDescriptionKey;
/*  99 */     this.nextObjectiveLineIds = nextObjectiveLineIds;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ObjectiveLineAsset() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/* 107 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 111 */     return this.category;
/*     */   }
/*     */   
/*     */   public String[] getObjectiveIds() {
/* 115 */     return this.objectiveIds;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getNextObjectiveId(String currentObjectiveId) {
/* 120 */     if (this.objectiveIds == null || this.objectiveIds.length == 0) return null;
/*     */ 
/*     */     
/* 123 */     for (int i = 0; i < this.objectiveIds.length - 1; i++) {
/* 124 */       if (this.objectiveIds[i].equals(currentObjectiveId)) {
/* 125 */         return this.objectiveIds[i + 1];
/*     */       }
/*     */     } 
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   public String getObjectiveTitleKey() {
/* 132 */     return this.objectiveTitleKey;
/*     */   }
/*     */   
/*     */   public String getObjectiveDescriptionKey() {
/* 136 */     return this.objectiveDescriptionKey;
/*     */   }
/*     */   
/*     */   public String[] getNextObjectiveLineIds() {
/* 140 */     return this.nextObjectiveLineIds;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 146 */     return "ObjectiveLineAsset{id='" + this.id + "', category='" + this.category + "', objectiveIds=" + 
/*     */ 
/*     */       
/* 149 */       Arrays.toString((Object[])this.objectiveIds) + ", objectiveTitleKey='" + this.objectiveTitleKey + "', objectiveDescriptionKey='" + this.objectiveDescriptionKey + "', nextObjectiveLineIds=" + 
/*     */ 
/*     */       
/* 152 */       Arrays.toString((Object[])this.nextObjectiveLineIds) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\ObjectiveLineAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */