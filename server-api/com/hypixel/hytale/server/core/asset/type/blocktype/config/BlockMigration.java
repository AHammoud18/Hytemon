/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.StringIntegerCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
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
/*     */ public class BlockMigration
/*     */   implements JsonAssetWithMap<Integer, DefaultAssetMap<Integer, BlockMigration>>
/*     */ {
/*     */   public static final AssetBuilderCodec<Integer, BlockMigration> CODEC;
/*     */   private static DefaultAssetMap<Integer, BlockMigration> ASSET_MAP;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected int id;
/*     */   
/*     */   static {
/*  45 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockMigration.class, BlockMigration::new, (Codec)new StringIntegerCodec(), (blockMigration, i) -> blockMigration.id = i.intValue(), blockMigration -> Integer.valueOf(blockMigration.id), (asset, data) -> asset.data = data, asset -> asset.data).addField(new KeyedCodec("DirectMigrations", (Codec)new MapCodec((Codec)Codec.STRING, java.util.HashMap::new)), (blockMigration, document) -> blockMigration.directMigrations = document, blockMigration -> blockMigration.directMigrations)).addField(new KeyedCodec("NameMigrations", (Codec)new MapCodec((Codec)Codec.STRING, java.util.HashMap::new)), (blockMigration, document) -> blockMigration.nameMigrations = document, blockMigration -> blockMigration.nameMigrations)).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DefaultAssetMap<Integer, BlockMigration> getAssetMap() {
/*  50 */     if (ASSET_MAP == null) ASSET_MAP = (DefaultAssetMap<Integer, BlockMigration>)AssetRegistry.getAssetStore(BlockMigration.class).getAssetMap(); 
/*  51 */     return ASSET_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   protected Map<String, String> directMigrations = Collections.emptyMap();
/*  58 */   protected Map<String, String> nameMigrations = Collections.emptyMap();
/*     */   
/*     */   public BlockMigration(int id, Map<String, String> directMigrations, Map<String, String> nameMigrations) {
/*  61 */     this.id = id;
/*  62 */     this.directMigrations = directMigrations;
/*  63 */     this.nameMigrations = nameMigrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Integer getId() {
/*  72 */     return Integer.valueOf(this.id);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getMigration(@Nonnull String blockTypeKey) {
/*  77 */     String direct = this.directMigrations.get(blockTypeKey);
/*  78 */     if (direct != null) return direct;
/*     */     
/*  80 */     String name = this.nameMigrations.get(blockTypeKey);
/*  81 */     if (name != null) return name;
/*     */     
/*  83 */     return blockTypeKey;
/*     */   }
/*     */   
/*     */   public String getDirectMigration(String blockTypeKey) {
/*  87 */     return this.directMigrations.getOrDefault(blockTypeKey, blockTypeKey);
/*     */   }
/*     */   
/*     */   public String getNameMigration(@Nonnull String blockTypeKey) {
/*  91 */     return this.nameMigrations.getOrDefault(blockTypeKey, blockTypeKey);
/*     */   }
/*     */   
/*     */   public Map<String, String> getDirectMigrations() {
/*  95 */     return this.directMigrations;
/*     */   }
/*     */   
/*     */   public Map<String, String> getNameMigrations() {
/*  99 */     return this.nameMigrations;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 105 */     return "BlockMigration{id='" + this.id + "', directMigrations=" + String.valueOf(this.directMigrations) + ", nameMigrations=" + String.valueOf(this.nameMigrations) + "}";
/*     */   }
/*     */   
/*     */   protected BlockMigration() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\BlockMigration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */