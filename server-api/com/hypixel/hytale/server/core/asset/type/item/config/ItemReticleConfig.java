/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
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
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.ItemReticleClientEvent;
/*     */ import com.hypixel.hytale.protocol.ItemReticleConfig;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemReticleConfig
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, ItemReticleConfig>>, NetworkSerializable<ItemReticleConfig>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ItemReticleConfig> CODEC;
/*     */   public static final int DEFAULT_INDEX = 0;
/*     */   public static final String DEFAULT_ID = "Default";
/*     */   
/*     */   static {
/*  69 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ItemReticleConfig.class, ItemReticleConfig::new, (Codec)Codec.STRING, (itemReticleConfig, s) -> itemReticleConfig.id = s, itemReticleConfig -> itemReticleConfig.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Base", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (itemReticleConfig, o) -> itemReticleConfig.base = o, itemReticleConfig -> itemReticleConfig.base, (itemReticleConfig, parent) -> itemReticleConfig.base = parent.base).documentation("Paths to the parts that should be displayed for the base reticle.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)CommonAssetValidator.UI_RETICLE_PARTS_ARRAY).add()).appendInherited(new KeyedCodec("ServerEvents", (Codec)new MapCodec((Codec)ItemReticleWithDuration.CODEC, java.util.HashMap::new)), (itemReticleConfig, o) -> itemReticleConfig.serverEvents = o, itemReticleConfig -> itemReticleConfig.serverEvents, (itemReticleConfig, parent) -> itemReticleConfig.serverEvents = parent.serverEvents).documentation("A map of reticle configurations for server-side events.").add()).appendInherited(new KeyedCodec("ClientEvents", (Codec)new EnumMapCodec(ItemReticleClientEvent.class, (Codec)ItemReticle.CODEC)), (itemReticleConfig, o) -> itemReticleConfig.clientEvents = o, itemReticleConfig -> itemReticleConfig.clientEvents, (itemReticleConfig, parent) -> itemReticleConfig.clientEvents = parent.clientEvents).documentation("A map of reticle configurations for client-side events.").add()).afterDecode(ItemReticleConfig::processConfig)).build();
/*     */   }
/*     */ 
/*     */   
/*  73 */   public static final ItemReticleConfig DEFAULT = new ItemReticleConfig("Default")
/*     */     {
/*     */     
/*     */     };
/*  77 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ItemReticleConfig::getAssetStore)); private static AssetStore<String, ItemReticleConfig, IndexedLookupTableAssetMap<String, ItemReticleConfig>> ASSET_STORE; protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   protected String[] base;
/*     */   
/*     */   public static AssetStore<String, ItemReticleConfig, IndexedLookupTableAssetMap<String, ItemReticleConfig>> getAssetStore() {
/*  82 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ItemReticleConfig.class); 
/*  83 */     return ASSET_STORE;
/*     */   }
/*     */   protected Map<String, ItemReticleWithDuration> serverEvents; protected Int2ObjectMap<ItemReticleWithDuration> indexedServerEvents; protected Map<ItemReticleClientEvent, ItemReticle> clientEvents; private SoftReference<ItemReticleConfig> cachedPacket;
/*     */   public static IndexedLookupTableAssetMap<String, ItemReticleConfig> getAssetMap() {
/*  87 */     return (IndexedLookupTableAssetMap<String, ItemReticleConfig>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemReticleConfig() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemReticleConfig(String id) {
/* 104 */     this.id = id;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 108 */     if (this.serverEvents != null && !this.serverEvents.isEmpty()) {
/* 109 */       this.indexedServerEvents = (Int2ObjectMap<ItemReticleWithDuration>)new Int2ObjectOpenHashMap();
/* 110 */       this.serverEvents.forEach((event, config) -> this.indexedServerEvents.put(AssetRegistry.getOrCreateTagIndex(event), config));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 116 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemReticleConfig toPacket() {
/* 122 */     ItemReticleConfig cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 123 */     if (cached != null) return cached;
/*     */     
/* 125 */     ItemReticleConfig packet = new ItemReticleConfig();
/* 126 */     packet.base = this.base;
/*     */     
/* 128 */     if (this.indexedServerEvents != null) {
/* 129 */       packet.serverEvents = (Map)new Int2ObjectOpenHashMap();
/* 130 */       for (ObjectIterator<Int2ObjectMap.Entry<ItemReticleWithDuration>> objectIterator = this.indexedServerEvents.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<ItemReticleWithDuration> event = objectIterator.next();
/* 131 */         packet.serverEvents.put(Integer.valueOf(event.getIntKey()), ((ItemReticleWithDuration)event.getValue()).toPacket()); }
/*     */     
/*     */     } 
/*     */     
/* 135 */     if (this.clientEvents != null) {
/* 136 */       packet.clientEvents = new EnumMap<>(ItemReticleClientEvent.class);
/* 137 */       for (Map.Entry<ItemReticleClientEvent, ItemReticle> event : this.clientEvents.entrySet()) {
/* 138 */         packet.clientEvents.put(event.getKey(), ((ItemReticle)event.getValue()).toPacket());
/*     */       }
/*     */     } 
/*     */     
/* 142 */     this.cachedPacket = new SoftReference<>(packet);
/* 143 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 149 */     return "ItemReticleConfig{id='" + this.id + "', base=" + 
/*     */       
/* 151 */       Arrays.toString((Object[])this.base) + ", serverEvents=" + String.valueOf(this.serverEvents) + ", indexedServerEvents=" + String.valueOf(this.indexedServerEvents) + ", clientEvents=" + String.valueOf(this.clientEvents) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ItemReticle
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.ItemReticle>
/*     */   {
/*     */     public static final BuilderCodec<ItemReticle> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean hideBase;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String[] parts;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 177 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemReticle.class, ItemReticle::new).append(new KeyedCodec("HideBase", (Codec)Codec.BOOLEAN), (itemReticle, o) -> itemReticle.hideBase = o.booleanValue(), itemReticle -> Boolean.valueOf(itemReticle.hideBase)).documentation("Specifies whether the base reticle should be hidden while the configured parts are shown.").add()).append(new KeyedCodec("Parts", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (itemReticle, o) -> itemReticle.parts = o, itemReticle -> itemReticle.parts).documentation("A list of reticle parts that should be displayed for this configuration.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)CommonAssetValidator.UI_RETICLE_PARTS_ARRAY).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemReticle(boolean hideBase, String[] parts) {
/* 183 */       this.hideBase = hideBase;
/* 184 */       this.parts = parts;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemReticle() {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.ItemReticle toPacket() {
/* 193 */       com.hypixel.hytale.protocol.ItemReticle packet = new com.hypixel.hytale.protocol.ItemReticle();
/* 194 */       packet.hideBase = this.hideBase;
/* 195 */       packet.parts = this.parts;
/* 196 */       return packet;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 202 */       return "ItemReticle{, hideBase=" + this.hideBase + ", parts=" + 
/*     */         
/* 204 */         Arrays.toString((Object[])this.parts) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ItemReticleWithDuration
/*     */     extends ItemReticle
/*     */   {
/*     */     public static final BuilderCodec<ItemReticleWithDuration> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 219 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ItemReticleWithDuration.class, ItemReticleWithDuration::new, ItemReticleConfig.ItemReticle.CODEC).append(new KeyedCodec("Duration", (Codec)Codec.FLOAT), (itemReticle, value) -> itemReticle.duration = value.floatValue(), itemReticle -> Float.valueOf(itemReticle.duration)).documentation("The duration (in seconds) this reticle configuration should be displayed for.").addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).build();
/*     */     }
/* 221 */     protected float duration = 0.25F;
/*     */     
/*     */     public ItemReticleWithDuration(boolean hideBase, String[] parts, float duration) {
/* 224 */       this.hideBase = hideBase;
/* 225 */       this.parts = parts;
/* 226 */       this.duration = duration;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.ItemReticle toPacket() {
/* 235 */       com.hypixel.hytale.protocol.ItemReticle packet = new com.hypixel.hytale.protocol.ItemReticle();
/* 236 */       packet.hideBase = this.hideBase;
/* 237 */       packet.parts = this.parts;
/* 238 */       packet.duration = this.duration;
/* 239 */       return packet;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 245 */       return "ItemReticleWithDuration{, duration=" + this.duration + "}" + super
/*     */         
/* 247 */         .toString();
/*     */     }
/*     */     
/*     */     public ItemReticleWithDuration() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemReticleConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */