/*     */ package com.hypixel.hytale.builtin.adventure.shop.barter;
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
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
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
/*     */ public class BarterShopAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, BarterShopAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, BarterShopAsset> CODEC;
/*     */   
/*     */   static {
/*  58 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BarterShopAsset.class, BarterShopAsset::new, (Codec)Codec.STRING, (asset, s) -> asset.id = s, asset -> asset.id, (asset, data) -> asset.extraData = data, asset -> asset.extraData).addField(new KeyedCodec("DisplayNameKey", (Codec)Codec.STRING), (asset, s) -> asset.displayNameKey = s, asset -> asset.displayNameKey)).addField(new KeyedCodec("RefreshInterval", (Codec)RefreshInterval.CODEC), (asset, interval) -> asset.refreshInterval = interval, asset -> asset.refreshInterval)).addField(new KeyedCodec("Trades", (Codec)new ArrayCodec((Codec)BarterTrade.CODEC, x$0 -> new BarterTrade[x$0])), (asset, trades) -> asset.trades = trades, asset -> asset.trades)).addField(new KeyedCodec("TradeSlots", (Codec)new ArrayCodec((Codec)TradeSlot.CODEC, x$0 -> new TradeSlot[x$0])), (asset, slots) -> asset.tradeSlots = slots, asset -> asset.tradeSlots)).addField(new KeyedCodec("RestockHour", (Codec)Codec.INTEGER, true), (asset, hour) -> asset.restockHour = hour, asset -> asset.restockHour)).build();
/*     */   }
/*  60 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BarterShopAsset::getAssetStore)); private static AssetStore<String, BarterShopAsset, DefaultAssetMap<String, BarterShopAsset>> ASSET_STORE; protected AssetExtraInfo.Data extraData;
/*     */   public static final int DEFAULT_RESTOCK_HOUR = 7;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, BarterShopAsset, DefaultAssetMap<String, BarterShopAsset>> getAssetStore() {
/*  65 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BarterShopAsset.class); 
/*  66 */     return ASSET_STORE;
/*     */   }
/*     */   protected String displayNameKey; protected RefreshInterval refreshInterval; protected BarterTrade[] trades; protected TradeSlot[] tradeSlots; protected Integer restockHour;
/*     */   public static DefaultAssetMap<String, BarterShopAsset> getAssetMap() {
/*  70 */     return (DefaultAssetMap<String, BarterShopAsset>)getAssetStore().getAssetMap();
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
/*     */   public BarterShopAsset(String id, String displayNameKey, RefreshInterval refreshInterval, BarterTrade[] trades, TradeSlot[] tradeSlots, @Nullable Integer restockHour) {
/*  86 */     this.id = id;
/*  87 */     this.displayNameKey = displayNameKey;
/*  88 */     this.refreshInterval = refreshInterval;
/*  89 */     this.trades = trades;
/*  90 */     this.tradeSlots = tradeSlots;
/*  91 */     this.restockHour = restockHour;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BarterShopAsset() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/*  99 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getDisplayNameKey() {
/* 103 */     return this.displayNameKey;
/*     */   }
/*     */   
/*     */   public RefreshInterval getRefreshInterval() {
/* 107 */     return this.refreshInterval;
/*     */   }
/*     */   
/*     */   public BarterTrade[] getTrades() {
/* 111 */     return this.trades;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TradeSlot[] getTradeSlots() {
/* 119 */     return this.tradeSlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTradeSlots() {
/* 127 */     return (this.tradeSlots != null && this.tradeSlots.length > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRestockHour() {
/* 135 */     return (this.restockHour != null) ? this.restockHour.intValue() : 7;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 141 */     return "BarterShopAsset{id='" + this.id + "', displayNameKey='" + this.displayNameKey + "', refreshInterval=" + String.valueOf(this.refreshInterval) + ", restockHour=" + 
/* 142 */       getRestockHour() + ", trades=" + 
/* 143 */       Arrays.toString((Object[])this.trades) + ", tradeSlots=" + Arrays.toString((Object[])this.tradeSlots) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\BarterShopAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */