/*     */ package com.hypixel.hytale.server.core.asset.type.gamemode;
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
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
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
/*     */ public class GameModeType
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, GameModeType>>
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetBuilderCodec<String, GameModeType> CODEC;
/*     */   private static AssetStore<String, GameModeType, DefaultAssetMap<String, GameModeType>> ASSET_STORE;
/*     */   
/*     */   static {
/*  51 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(GameModeType.class, GameModeType::new, (Codec)Codec.STRING, (gmType, s) -> gmType.id = s, gmType -> gmType.id, (gmType, data) -> gmType.data = data, gmType -> gmType.data).append(new KeyedCodec("PermissionGroups", (Codec)Codec.STRING_ARRAY), (gmType, o) -> gmType.permissionGroups = o, gmType -> gmType.permissionGroups).add()).append(new KeyedCodec("InteractionsOnEnter", (Codec)RootInteraction.CHILD_ASSET_CODEC), (gmType, interactions) -> gmType.interactionsOnEnter = interactions, gmType -> gmType.interactionsOnEnter).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, GameModeType, DefaultAssetMap<String, GameModeType>> getAssetStore() {
/*  63 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(GameModeType.class); 
/*  64 */     return ASSET_STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  71 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(GameModeType::getAssetStore)); protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   private String[] permissionGroups;
/*     */   private String interactionsOnEnter;
/*     */   
/*     */   public static DefaultAssetMap<String, GameModeType> getAssetMap() {
/*  77 */     return (DefaultAssetMap<String, GameModeType>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static GameModeType fromGameMode(@Nonnull GameMode gameMode) {
/*  88 */     GameModeType type = (GameModeType)((DefaultAssetMap)getAssetStore().getAssetMap()).getAsset(gameMode.name());
/*  89 */     return (type == null) ? new GameModeType() : type;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getInteractionsOnEnter() {
/* 125 */     return this.interactionsOnEnter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String[] getPermissionGroups() {
/* 133 */     if (this.permissionGroups == null) return ArrayUtil.EMPTY_STRING_ARRAY; 
/* 134 */     return this.permissionGroups;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 139 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gamemode\GameModeType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */