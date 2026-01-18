/*     */ package com.hypixel.hytale.server.spawning.assets.spawnsuppression;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
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
/*     */ public class SpawnSuppression
/*     */   implements JsonAssetWithMap<String, IndexedAssetMap<String, SpawnSuppression>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, SpawnSuppression> CODEC;
/*     */   
/*     */   static {
/*  78 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(SpawnSuppression.class, SpawnSuppression::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("A configuration designed to prevent certain NPCs spawns within a given region.")).appendInherited(new KeyedCodec("SuppressionRadius", (Codec)Codec.DOUBLE), (suppressor, d) -> suppressor.radius = d.doubleValue(), suppressor -> Double.valueOf(suppressor.radius), (suppressor, parent) -> suppressor.radius = parent.radius).documentation("The radius this spawn suppression should cover. Any chunk which falls even partially within this radius will be affected by the suppression on the x and z axes, but will use exact distance for the y axis. This allows NPCs to continue to spawn in caves below the position or in the skies above, but is slightly more efficient and provides no noticeable differences in world spawns.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("SuppressedGroups", (Codec)Codec.STRING_ARRAY), (suppressor, s) -> suppressor.suppressedGroups = s, suppressor -> suppressor.suppressedGroups, (suppressor, parent) -> suppressor.suppressedGroups = parent.suppressedGroups).documentation("An array of NPCGroup ids that will be suppressed.").addValidator((Validator)NPCGroup.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("SuppressSpawnMarkers", (Codec)Codec.BOOLEAN), (suppressor, b) -> suppressor.suppressSpawnMarkers = b.booleanValue(), suppressor -> Boolean.valueOf(suppressor.suppressSpawnMarkers), (suppressor, parent) -> suppressor.suppressSpawnMarkers = parent.suppressSpawnMarkers).documentation("Whether or not to suppress any spawn markers within the range of this suppression. If set to true, any spawn marker within this range will cease to function while the suppression exists").add()).afterDecode(suppressor -> { if (suppressor.suppressedGroups != null && suppressor.suppressedGroups.length > 0) { IndexedLookupTableAssetMap<String, NPCGroup> npcGroups = NPCGroup.getAssetMap(); IntOpenHashSet set = new IntOpenHashSet(); for (String group : suppressor.suppressedGroups) set.add(npcGroups.getIndex(group));  suppressor.suppressedGroupIds = set.toIntArray(); }  })).build();
/*     */   }
/*  80 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(SpawnSuppression::getAssetStore)); private static AssetStore<String, SpawnSuppression, IndexedAssetMap<String, SpawnSuppression>> ASSET_STORE;
/*     */   private AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, SpawnSuppression, IndexedAssetMap<String, SpawnSuppression>> getAssetStore() {
/*  85 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(SpawnSuppression.class); 
/*  86 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedAssetMap<String, SpawnSuppression> getAssetMap() {
/*  90 */     return (IndexedAssetMap<String, SpawnSuppression>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   protected double radius = 10.0D;
/*     */   
/*     */   protected String[] suppressedGroups;
/*     */   
/*     */   protected int[] suppressedGroupIds;
/*     */   protected boolean suppressSpawnMarkers;
/*     */   
/*     */   public SpawnSuppression(String id) {
/* 105 */     this.id = id;
/*     */   }
/*     */   
/*     */   public SpawnSuppression(String id, double radius, String[] suppressedGroups, int[] suppressedGroupIds, boolean suppressSpawnMarkers) {
/* 109 */     this.id = id;
/* 110 */     this.radius = radius;
/* 111 */     this.suppressedGroups = suppressedGroups;
/* 112 */     this.suppressedGroupIds = suppressedGroupIds;
/* 113 */     this.suppressSpawnMarkers = suppressSpawnMarkers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 121 */     return this.id;
/*     */   }
/*     */   
/*     */   public double getRadius() {
/* 125 */     return this.radius;
/*     */   }
/*     */   
/*     */   public int[] getSuppressedGroupIds() {
/* 129 */     return this.suppressedGroupIds;
/*     */   }
/*     */   
/*     */   public boolean isSuppressSpawnMarkers() {
/* 133 */     return this.suppressSpawnMarkers;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 139 */     return "SpawnSuppression{radius=" + this.radius + ", suppressedGroups=" + 
/*     */       
/* 141 */       Arrays.toString((Object[])this.suppressedGroups) + ", suppressSpawnMarkers=" + this.suppressSpawnMarkers + "}";
/*     */   }
/*     */   
/*     */   protected SpawnSuppression() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\assets\spawnsuppression\SpawnSuppression.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */