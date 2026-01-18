/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
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
/*     */ import com.hypixel.hytale.protocol.ResourceType;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
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
/*     */ public class ResourceType
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ResourceType>>, NetworkSerializable<ResourceType>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ResourceType> CODEC;
/*     */   
/*     */   static {
/*  49 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ResourceType.class, ResourceType::new, (Codec)Codec.STRING, (resourceType, k) -> resourceType.id = k, resourceType -> resourceType.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Name", (Codec)Codec.STRING), (resourceType, s) -> resourceType.name = s, resourceType -> resourceType.name, (resourceType, parent) -> resourceType.name = parent.name).add()).appendInherited(new KeyedCodec("Description", (Codec)Codec.STRING), (resourceType, s) -> resourceType.description = s, resourceType -> resourceType.description, (resourceType, parent) -> resourceType.description = parent.description).add()).appendInherited(new KeyedCodec("Icon", (Codec)Codec.STRING), (resourceType, s) -> resourceType.icon = s, resourceType -> resourceType.icon, (resourceType, parent) -> resourceType.icon = parent.icon).addValidator((Validator)CommonAssetValidator.ICON_RESOURCE).add()).build();
/*     */   }
/*  51 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ResourceType::getAssetStore)); private static AssetStore<String, ResourceType, DefaultAssetMap<String, ResourceType>> ASSET_STORE; protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, ResourceType, DefaultAssetMap<String, ResourceType>> getAssetStore() {
/*  55 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ResourceType.class); 
/*  56 */     return ASSET_STORE;
/*     */   }
/*     */   protected String name; protected String description; protected String icon; private SoftReference<ResourceType> cachedPacket;
/*     */   public static DefaultAssetMap<String, ResourceType> getAssetMap() {
/*  60 */     return (DefaultAssetMap<String, ResourceType>)getAssetStore().getAssetMap();
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
/*     */   public ResourceType(String id, String name, String description, String icon) {
/*  73 */     this.id = id;
/*  74 */     this.name = name;
/*  75 */     this.description = description;
/*  76 */     this.icon = icon;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceType() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/*  84 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  88 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  92 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/*  96 */     return this.icon;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResourceType toPacket() {
/* 102 */     ResourceType cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 103 */     if (cached != null) return cached;
/*     */     
/* 105 */     ResourceType packet = new ResourceType(this.id, this.icon);
/*     */     
/* 107 */     this.cachedPacket = new SoftReference<>(packet);
/* 108 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 114 */     return "ResourceType{id='" + this.id + "', name='" + this.name + "', description='" + this.description + "', icon='" + this.icon + "'}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ResourceType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */