/*     */ package com.hypixel.hytale.server.core.asset.type.portalworld;
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
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortalType
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, PortalType>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, PortalType> CODEC;
/*     */   private static AssetStore<String, PortalType, DefaultAssetMap<String, PortalType>> ASSET_STORE;
/*     */   
/*     */   static {
/*  86 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(PortalType.class, PortalType::new, (Codec)Codec.STRING, (portalType, s) -> portalType.id = s, portalType -> portalType.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("InstanceId", (Codec)Codec.STRING), (portalType, o) -> portalType.instanceId = o, portalType -> portalType.instanceId).documentation("The instance id (folder name in Assets/Server/Instances) that this instance loads.").add()).append(new KeyedCodec("Description", (Codec)PortalDescription.CODEC), (portalType, o) -> portalType.description = o, portalType -> portalType.description).documentation("The qualitative description of the portal.").add()).append(new KeyedCodec("PlayerSpawn", (Codec)PortalSpawn.CODEC), (portalType, o) -> portalType.portalSpawn = o, portalType -> portalType.portalSpawn).documentation("If set, overrides the Instance's SpawnProvider with the portal's spawning system.").add()).append(new KeyedCodec("CursedItems", (Codec)Codec.STRING_ARRAY), (portalType, o) -> portalType.cursedItems = (o == null) ? Collections.<String>emptySet() : Set.<String>of(o), portalType -> (String[])portalType.cursedItems.toArray(())).documentation("Which item drops are cursed when spawned in this portal.").add()).append(new KeyedCodec("VoidInvasionEnabled", (Codec)Codec.BOOLEAN), (portalType, o) -> portalType.voidInvasionEnabled = o.booleanValue(), portalType -> Boolean.valueOf(portalType.voidInvasionEnabled)).documentation("Whether the void invasion is enabled for this portal.").add()).append(new KeyedCodec("GameplayConfig", (Codec)Codec.STRING), (config, o) -> config.gameplayConfig = o, config -> config.gameplayConfig).documentation("The ID of the GameplayConfig asset for worlds spawned with this portal type.").add()).build();
/*     */   }
/*     */   
/*     */   public static AssetStore<String, PortalType, DefaultAssetMap<String, PortalType>> getAssetStore() {
/*  90 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(PortalType.class); 
/*  91 */     return ASSET_STORE;
/*     */   }
/*  93 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(PortalType::getAssetStore));
/*     */   public static DefaultAssetMap<String, PortalType> getAssetMap() {
/*  95 */     return (DefaultAssetMap<String, PortalType>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */   
/*     */   private AssetExtraInfo.Data data;
/*     */   
/*     */   private String id;
/*     */   
/*     */   private String instanceId;
/*     */   private PortalDescription description;
/* 105 */   private String gameplayConfig = "Portal";
/*     */   
/*     */   private boolean voidInvasionEnabled = false;
/*     */   
/*     */   private PortalSpawn portalSpawn;
/* 110 */   private Set<String> cursedItems = Collections.emptySet();
/*     */ 
/*     */   
/*     */   public String getId() {
/* 114 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getInstanceId() {
/* 118 */     return this.instanceId;
/*     */   }
/*     */   
/*     */   public Message getDisplayName() {
/* 122 */     return this.description.getDisplayName();
/*     */   }
/*     */   
/*     */   public PortalDescription getDescription() {
/* 126 */     return this.description;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PortalSpawn getPortalSpawn() {
/* 131 */     return this.portalSpawn;
/*     */   }
/*     */   
/*     */   public Set<String> getCursedItems() {
/* 135 */     return this.cursedItems;
/*     */   }
/*     */   
/*     */   public String getGameplayConfigId() {
/* 139 */     return this.gameplayConfig;
/*     */   }
/*     */   
/*     */   public boolean isVoidInvasionEnabled() {
/* 143 */     return this.voidInvasionEnabled;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public GameplayConfig getGameplayConfig() {
/* 148 */     return (GameplayConfig)GameplayConfig.getAssetMap().getAsset(this.gameplayConfig);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\portalworld\PortalType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */