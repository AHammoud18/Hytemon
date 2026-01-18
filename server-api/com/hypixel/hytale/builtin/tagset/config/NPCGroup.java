/*     */ package com.hypixel.hytale.builtin.tagset.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.tagset.TagSet;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
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
/*     */ public class NPCGroup
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, NPCGroup>>, TagSet
/*     */ {
/*     */   public static final AssetBuilderCodec<String, NPCGroup> CODEC;
/*     */   
/*     */   static {
/*  58 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(NPCGroup.class, NPCGroup::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("Defines a group or collection of NPC types.")).append(new KeyedCodec("IncludeRoles", (Codec)Codec.STRING_ARRAY), (npcGroup, strings) -> npcGroup.includedRoles = strings, npcGroup -> npcGroup.includedRoles).documentation("A list of individual types to include.").add()).append(new KeyedCodec("ExcludeRoles", (Codec)Codec.STRING_ARRAY), (npcGroup, strings) -> npcGroup.excludedRoles = strings, npcGroup -> npcGroup.excludedRoles).documentation("A list of individual types to exclude.").add()).append(new KeyedCodec("IncludeGroups", (Codec)Codec.STRING_ARRAY), (npcGroup, strings) -> npcGroup.includedGroupTags = strings, npcGroup -> npcGroup.includedGroupTags).documentation("A list of other groups to include.").add()).append(new KeyedCodec("ExcludeGroups", (Codec)Codec.STRING_ARRAY), (npcGroup, strings) -> npcGroup.excludedGroupTags = strings, npcGroup -> npcGroup.excludedGroupTags).documentation("A list of other groups to exclude.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  64 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(NPCGroup.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY;
/*     */   static {
/*  66 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/*     */   }
/*  68 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(NPCGroup::getAssetStore)); private static AssetStore<String, NPCGroup, IndexedLookupTableAssetMap<String, NPCGroup>> ASSET_STORE; protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, NPCGroup, IndexedLookupTableAssetMap<String, NPCGroup>> getAssetStore() {
/*  72 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(NPCGroup.class); 
/*  73 */     return ASSET_STORE;
/*     */   }
/*     */   protected String[] includedGroupTags; protected String[] excludedGroupTags; protected String[] includedRoles; protected String[] excludedRoles;
/*     */   public static IndexedLookupTableAssetMap<String, NPCGroup> getAssetMap() {
/*  77 */     return (IndexedLookupTableAssetMap<String, NPCGroup>)getAssetStore().getAssetMap();
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
/*     */   public NPCGroup(String id) {
/*  90 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NPCGroup() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/*  98 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getIncludedTagSets() {
/* 103 */     return this.includedGroupTags;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getExcludedTagSets() {
/* 108 */     return this.excludedGroupTags;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getIncludedTags() {
/* 113 */     return this.includedRoles;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getExcludedTags() {
/* 118 */     return this.excludedRoles;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\tagset\config\NPCGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */