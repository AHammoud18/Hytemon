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
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.ItemCategory;
/*     */ import com.hypixel.hytale.protocol.ItemGridInfoDisplayMode;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
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
/*     */ public class ItemCategory
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ItemCategory>>, NetworkSerializable<ItemCategory>
/*     */ {
/*     */   private static final AssetBuilderCodec.Builder<String, ItemCategory> CODEC_BUILDER;
/*     */   
/*     */   static {
/*  66 */     CODEC_BUILDER = (AssetBuilderCodec.Builder<String, ItemCategory>)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ItemCategory.class, ItemCategory::new, (Codec)Codec.STRING, (itemCategory, k) -> itemCategory.id = k, itemCategory -> itemCategory.id, (asset, data) -> asset.data = data, asset -> asset.data).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (itemCategory, s) -> itemCategory.id = s, itemCategory -> itemCategory.id)).addField(new KeyedCodec("Name", (Codec)Codec.STRING), (itemCategory, s) -> itemCategory.name = s, itemCategory -> itemCategory.name)).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (itemCategory, s) -> itemCategory.icon = s, itemCategory -> itemCategory.icon).addValidator((Validator)CommonAssetValidator.ICON_ITEM_CATEGORIES).add()).append(new KeyedCodec("InfoDisplayMode", (Codec)new EnumCodec(ItemGridInfoDisplayMode.class), false, true), (itemCategory, s) -> itemCategory.infoDisplayMode = s, itemCategory -> itemCategory.infoDisplayMode).addValidator(Validators.nonNull()).add()).addField(new KeyedCodec("Order", (Codec)Codec.INTEGER), (itemCategory, s) -> itemCategory.order = s.intValue(), itemCategory -> Integer.valueOf(itemCategory.order))).afterDecode(itemCategory -> {
/*     */           if (itemCategory.children != null)
/*     */             Arrays.sort(itemCategory.children, Comparator.comparingInt(())); 
/*  69 */         }); } public static final AssetBuilderCodec<String, ItemCategory> CODEC = CODEC_BUILDER.build();
/*     */   
/*     */   private static AssetStore<String, ItemCategory, DefaultAssetMap<String, ItemCategory>> ASSET_STORE;
/*     */   
/*     */   static {
/*  74 */     CODEC_BUILDER.addField(new KeyedCodec("Children", (Codec)new ArrayCodec((Codec)CODEC, x$0 -> new ItemCategory[x$0])), (itemCategory, l) -> itemCategory.children = l, itemCategory -> itemCategory.children);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AssetStore<String, ItemCategory, DefaultAssetMap<String, ItemCategory>> getAssetStore() {
/*  84 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ItemCategory.class); 
/*  85 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, ItemCategory> getAssetMap() {
/*  89 */     return (DefaultAssetMap<String, ItemCategory>)getAssetStore().getAssetMap();
/*     */   }
/*     */   
/*  92 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ItemCategory::getAssetStore));
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   protected String name;
/*     */   protected String icon;
/*     */   protected int order;
/*     */   @Nonnull
/* 100 */   protected ItemGridInfoDisplayMode infoDisplayMode = ItemGridInfoDisplayMode.Tooltip;
/*     */   
/*     */   protected ItemCategory[] children;
/*     */   
/*     */   private SoftReference<ItemCategory> cachedPacket;
/*     */   
/*     */   public ItemCategory(String id, String name, String icon, ItemGridInfoDisplayMode infoDisplayMode, ItemCategory[] children) {
/* 107 */     this.id = id;
/* 108 */     this.name = name;
/* 109 */     this.icon = icon;
/* 110 */     this.infoDisplayMode = infoDisplayMode;
/* 111 */     this.children = children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemCategory toPacket() {
/* 120 */     ItemCategory cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 121 */     if (cached != null) return cached;
/*     */     
/* 123 */     ItemCategory packet = new ItemCategory();
/* 124 */     packet.id = this.id;
/* 125 */     packet.name = this.name;
/* 126 */     packet.icon = this.icon;
/* 127 */     packet.order = this.order;
/* 128 */     packet.infoDisplayMode = this.infoDisplayMode;
/*     */     
/* 130 */     if (this.children != null && this.children.length > 0) {
/* 131 */       packet.children = (ItemCategory[])ArrayUtil.copyAndMutate((Object[])this.children, ItemCategory::toPacket, x$0 -> new ItemCategory[x$0]);
/*     */     }
/*     */     
/* 134 */     this.cachedPacket = new SoftReference<>(packet);
/* 135 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 140 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 144 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 148 */     return this.icon;
/*     */   }
/*     */   
/*     */   public int getOrder() {
/* 152 */     return this.order;
/*     */   }
/*     */   
/*     */   public ItemGridInfoDisplayMode getInfoDisplayMode() {
/* 156 */     return this.infoDisplayMode;
/*     */   }
/*     */   
/*     */   public ItemCategory[] getChildren() {
/* 160 */     return this.children;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 166 */     return "ItemCategory{id='" + this.id + "', name='" + this.name + "', icon='" + this.icon + "', order=" + this.order + ", infoDisplayMode='" + String.valueOf(this.infoDisplayMode) + "', children=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       Arrays.toString((Object[])this.children) + "}";
/*     */   }
/*     */   
/*     */   protected ItemCategory() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */