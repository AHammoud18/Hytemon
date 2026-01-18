/*     */ package com.hypixel.hytale.server.core.asset.type.blockset.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.protocol.BlockSet;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
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
/*     */ @Deprecated(forRemoval = true)
/*     */ public class BlockSet
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, BlockSet>>, NetworkSerializable<BlockSet>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, BlockSet> CODEC;
/*     */   
/*     */   static {
/*  88 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockSet.class, BlockSet::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("Parent", (Codec)Codec.STRING), (blockSet, b) -> blockSet.parent = b, blockSet -> blockSet.parent).metadata((Metadata)new UIEditorSectionStart("General")).add()).addField(new KeyedCodec("IncludeAll", (Codec)Codec.BOOLEAN), (blockSet, b) -> blockSet.includeAll = b.booleanValue(), blockSet -> Boolean.valueOf(blockSet.includeAll))).addField(new KeyedCodec("IncludeBlockTypes", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.includeBlockTypes = strings, blockSet -> blockSet.includeBlockTypes)).addField(new KeyedCodec("ExcludeBlockTypes", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.excludeBlockTypes = strings, blockSet -> blockSet.excludeBlockTypes)).addField(new KeyedCodec("IncludeBlockGroups", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.includeBlockGroups = strings, blockSet -> blockSet.includeBlockGroups)).addField(new KeyedCodec("ExcludeBlockGroups", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.excludeBlockGroups = strings, blockSet -> blockSet.excludeBlockGroups)).addField(new KeyedCodec("IncludeHitboxTypes", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.includeHitboxTypes = strings, blockSet -> blockSet.includeHitboxTypes)).addField(new KeyedCodec("ExcludeHitboxTypes", (Codec)Codec.STRING_ARRAY), (blockSet, strings) -> blockSet.excludeHitboxTypes = strings, blockSet -> blockSet.excludeHitboxTypes)).addField(new KeyedCodec("IncludeCategories", (Codec)new ArrayCodec((Codec)Codec.STRING_ARRAY, x$0 -> new String[x$0][])), (blockSet, strings) -> blockSet.includeCategories = strings, blockSet -> blockSet.includeCategories)).addField(new KeyedCodec("ExcludeCategories", (Codec)new ArrayCodec((Codec)Codec.STRING_ARRAY, x$0 -> new String[x$0][])), (blockSet, strings) -> blockSet.excludeCategories = strings, blockSet -> blockSet.excludeCategories)).build();
/*     */   }
/*  90 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BlockSet::getAssetStore)); private static AssetStore<String, BlockSet, IndexedLookupTableAssetMap<String, BlockSet>> ASSET_STORE; protected AssetExtraInfo.Data data; protected String id; protected String parent;
/*     */   protected boolean includeAll;
/*     */   protected String[] includeBlockTypes;
/*     */   
/*     */   public static AssetStore<String, BlockSet, IndexedLookupTableAssetMap<String, BlockSet>> getAssetStore() {
/*  95 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BlockSet.class); 
/*  96 */     return ASSET_STORE;
/*     */   }
/*     */   protected String[] excludeBlockTypes; protected String[] includeBlockGroups; protected String[] excludeBlockGroups; protected String[] includeHitboxTypes; protected String[] excludeHitboxTypes; protected String[][] includeCategories; protected String[][] excludeCategories;
/*     */   public static IndexedLookupTableAssetMap<String, BlockSet> getAssetMap() {
/* 100 */     return (IndexedLookupTableAssetMap<String, BlockSet>)getAssetStore().getAssetMap();
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
/*     */   public BlockSet(String id) {
/* 120 */     this.id = id;
/*     */   }
/*     */   
/*     */   public BlockSet(String id, String parent, boolean includeAll, String[] includeBlockTypes, String[] excludeBlockTypes, String[] includeBlockGroups, String[] excludeBlockGroups, String[] includeHitboxTypes, String[] excludeHitboxTypes, String[][] includeCategories, String[][] excludeCategories) {
/* 124 */     this.id = id;
/* 125 */     this.parent = parent;
/* 126 */     this.includeAll = includeAll;
/* 127 */     this.includeBlockTypes = includeBlockTypes;
/* 128 */     this.excludeBlockTypes = excludeBlockTypes;
/* 129 */     this.includeBlockGroups = includeBlockGroups;
/* 130 */     this.excludeBlockGroups = excludeBlockGroups;
/* 131 */     this.includeHitboxTypes = includeHitboxTypes;
/* 132 */     this.excludeHitboxTypes = excludeHitboxTypes;
/* 133 */     this.includeCategories = includeCategories;
/* 134 */     this.excludeCategories = excludeCategories;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockSet() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/* 142 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getParent() {
/* 146 */     return this.parent;
/*     */   }
/*     */   
/*     */   public boolean isIncludeAll() {
/* 150 */     return this.includeAll;
/*     */   }
/*     */   
/*     */   public String[] getIncludeBlockTypes() {
/* 154 */     return this.includeBlockTypes;
/*     */   }
/*     */   
/*     */   public String[] getExcludeBlockTypes() {
/* 158 */     return this.excludeBlockTypes;
/*     */   }
/*     */   
/*     */   public String[] getIncludeBlockGroups() {
/* 162 */     return this.includeBlockGroups;
/*     */   }
/*     */   
/*     */   public String[] getExcludeBlockGroups() {
/* 166 */     return this.excludeBlockGroups;
/*     */   }
/*     */   
/*     */   public String[] getIncludeHitboxTypes() {
/* 170 */     return this.includeHitboxTypes;
/*     */   }
/*     */   
/*     */   public String[] getExcludeHitboxTypes() {
/* 174 */     return this.excludeHitboxTypes;
/*     */   }
/*     */   
/*     */   public String[][] getIncludeCategories() {
/* 178 */     return this.includeCategories;
/*     */   }
/*     */   
/*     */   public String[][] getExcludeCategories() {
/* 182 */     return this.excludeCategories;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 188 */     return "BlockSet{name='" + this.id + "', parent='" + this.parent + "', includeAll=" + this.includeAll + ", includeBlockTypes=" + 
/*     */ 
/*     */ 
/*     */       
/* 192 */       Arrays.toString((Object[])this.includeBlockTypes) + ", excludeBlockTypes=" + 
/* 193 */       Arrays.toString((Object[])this.excludeBlockTypes) + ", includeBlockGroups=" + 
/* 194 */       Arrays.toString((Object[])this.includeBlockGroups) + ", excludeBlockGroups=" + 
/* 195 */       Arrays.toString((Object[])this.excludeBlockGroups) + ", includeHitboxTypes=" + 
/* 196 */       Arrays.toString((Object[])this.includeHitboxTypes) + ", excludeHitboxTypes=" + 
/* 197 */       Arrays.toString((Object[])this.excludeHitboxTypes) + ", includeCategories=" + 
/* 198 */       Arrays.deepToString((Object[])this.includeCategories) + ", excludeCategories=" + 
/* 199 */       Arrays.deepToString((Object[])this.excludeCategories) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockSet toPacket() {
/* 206 */     BlockSet packet = new BlockSet();
/*     */     
/* 208 */     int index = getAssetMap().getIndex(this.id);
/* 209 */     IntSet allBlocks = (IntSet)BlockSetModule.getInstance().getBlockSets().get(index);
/*     */     
/* 211 */     packet.name = this.id;
/* 212 */     packet.blocks = allBlocks.toIntArray();
/*     */     
/* 214 */     return packet;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blockset\config\BlockSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */