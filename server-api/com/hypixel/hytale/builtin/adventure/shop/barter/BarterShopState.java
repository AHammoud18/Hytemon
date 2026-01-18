/*     */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneOffset;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BarterShopState
/*     */ {
/*  36 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BarterShopState instance;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Path saveDirectory;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<ShopInstanceState> SHOP_INSTANCE_CODEC;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<BarterShopState> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  60 */     SHOP_INSTANCE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ShopInstanceState.class, ShopInstanceState::new).append(new KeyedCodec("Stock", (Codec)Codec.INT_ARRAY), (state, stock) -> state.currentStock = stock, state -> state.currentStock).add()).append(new KeyedCodec("NextRefresh", (Codec)Codec.INSTANT, true), (state, instant) -> state.nextRefreshTime = instant, state -> state.nextRefreshTime).add()).append(new KeyedCodec("ResolveSeed", (Codec)Codec.LONG, true), (state, seed) -> state.resolveSeed = seed, state -> state.resolveSeed).add()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BarterShopState.class, BarterShopState::new).append(new KeyedCodec("Shops", (Codec)new MapCodec((Codec)SHOP_INSTANCE_CODEC, Object2ObjectOpenHashMap::new, false)), (state, shops) -> state.shopStates.putAll(shops), state -> new Object2ObjectOpenHashMap(state.shopStates)).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initialize(@Nonnull Path dataDirectory) {
/*  77 */     saveDirectory = dataDirectory;
/*  78 */     load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BarterShopState get() {
/*  86 */     if (instance == null) {
/*  87 */       instance = new BarterShopState();
/*     */     }
/*  89 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void load() {
/*  96 */     if (saveDirectory == null) {
/*  97 */       LOGGER.at(Level.WARNING).log("Cannot load barter shop state: save directory not set");
/*  98 */       instance = new BarterShopState();
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     Path file = saveDirectory.resolve("barter_shop_state.json");
/* 103 */     if (!Files.exists(file, new java.nio.file.LinkOption[0])) {
/* 104 */       LOGGER.at(Level.INFO).log("No saved barter shop state found, starting fresh");
/* 105 */       instance = new BarterShopState();
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 110 */       BsonDocument document = BsonUtil.readDocumentNow(file);
/* 111 */       if (document != null) {
/* 112 */         ExtraInfo extraInfo = ExtraInfo.THREAD_LOCAL.get();
/* 113 */         instance = (BarterShopState)CODEC.decode((BsonValue)document, extraInfo);
/* 114 */         extraInfo.getValidationResults().logOrThrowValidatorExceptions(LOGGER);
/* 115 */         LOGGER.at(Level.INFO).log("Loaded barter shop state with %d shops", instance.shopStates.size());
/*     */       } else {
/* 117 */         instance = new BarterShopState();
/*     */       } 
/* 119 */     } catch (Exception e) {
/* 120 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to load barter shop state, starting fresh");
/* 121 */       instance = new BarterShopState();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void save() {
/* 129 */     if (saveDirectory == null || instance == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 134 */       if (!Files.exists(saveDirectory, new java.nio.file.LinkOption[0])) {
/* 135 */         Files.createDirectories(saveDirectory, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/* 137 */       Path file = saveDirectory.resolve("barter_shop_state.json");
/* 138 */       BsonUtil.writeSync(file, (Codec)CODEC, instance, LOGGER);
/* 139 */       LOGGER.at(Level.FINE).log("Saved barter shop state");
/* 140 */     } catch (IOException e) {
/* 141 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(e)).log("Failed to save barter shop state");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/* 149 */     save();
/* 150 */     instance = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ShopInstanceState
/*     */   {
/* 157 */     private int[] currentStock = new int[0];
/*     */     
/*     */     private Instant nextRefreshTime;
/*     */     
/*     */     private Long resolveSeed;
/*     */     
/*     */     private transient BarterTrade[] resolvedTrades;
/*     */ 
/*     */     
/*     */     public ShopInstanceState() {}
/*     */ 
/*     */     
/*     */     public ShopInstanceState(int tradeCount) {
/* 170 */       this.currentStock = new int[tradeCount];
/* 171 */       this.nextRefreshTime = null;
/*     */     }
/*     */     
/*     */     public int[] getCurrentStock() {
/* 175 */       return this.currentStock;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Instant getNextRefreshTime() {
/* 180 */       return this.nextRefreshTime;
/*     */     }
/*     */     
/*     */     public void setNextRefreshTime(Instant time) {
/* 184 */       this.nextRefreshTime = time;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Long getResolveSeed() {
/* 189 */       return this.resolveSeed;
/*     */     }
/*     */     
/*     */     public void setResolveSeed(Long seed) {
/* 193 */       this.resolveSeed = seed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public BarterTrade[] getResolvedTrades(@Nonnull BarterShopAsset asset) {
/* 203 */       if (!asset.hasTradeSlots())
/*     */       {
/* 205 */         return (asset.getTrades() != null) ? asset.getTrades() : new BarterTrade[0];
/*     */       }
/*     */ 
/*     */       
/* 209 */       if (this.resolvedTrades != null) {
/* 210 */         return this.resolvedTrades;
/*     */       }
/*     */ 
/*     */       
/* 214 */       if (this.resolveSeed == null) {
/* 215 */         this.resolveSeed = Long.valueOf(ThreadLocalRandom.current().nextLong());
/*     */       }
/* 217 */       this.resolvedTrades = resolveTradeSlots(asset, this.resolveSeed.longValue());
/* 218 */       return this.resolvedTrades;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private static BarterTrade[] resolveTradeSlots(@Nonnull BarterShopAsset asset, long seed) {
/* 226 */       TradeSlot[] slots = asset.getTradeSlots();
/* 227 */       if (slots == null || slots.length == 0) {
/* 228 */         return new BarterTrade[0];
/*     */       }
/*     */       
/* 231 */       Random random = new Random(seed);
/* 232 */       ObjectArrayList<BarterTrade> objectArrayList = new ObjectArrayList();
/* 233 */       for (TradeSlot slot : slots) {
/* 234 */         objectArrayList.addAll(slot.resolve(random));
/*     */       }
/* 236 */       return objectArrayList.<BarterTrade>toArray(new BarterTrade[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void resetStockAndResolve(@Nonnull BarterShopAsset asset) {
/* 243 */       if (asset.hasTradeSlots()) {
/*     */         
/* 245 */         this.resolveSeed = Long.valueOf(ThreadLocalRandom.current().nextLong());
/* 246 */         this.resolvedTrades = resolveTradeSlots(asset, this.resolveSeed.longValue());
/*     */       } else {
/* 248 */         this.resolvedTrades = null;
/*     */       } 
/*     */       
/* 251 */       BarterTrade[] trades = getResolvedTrades(asset);
/* 252 */       this.currentStock = new int[trades.length];
/* 253 */       for (int i = 0; i < trades.length; i++) {
/* 254 */         this.currentStock[i] = trades[i].getMaxStock();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void resetStock(BarterShopAsset asset) {
/* 264 */       BarterTrade[] trades = getResolvedTrades(asset);
/*     */       
/* 266 */       if (this.currentStock.length != trades.length) {
/* 267 */         this.currentStock = new int[trades.length];
/*     */       }
/* 269 */       for (int i = 0; i < trades.length; i++) {
/* 270 */         this.currentStock[i] = trades[i].getMaxStock();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean expandStockIfNeeded(BarterShopAsset asset) {
/* 281 */       BarterTrade[] trades = getResolvedTrades(asset);
/* 282 */       if (this.currentStock.length >= trades.length) {
/* 283 */         return false;
/*     */       }
/* 285 */       int[] newStock = new int[trades.length];
/*     */       
/* 287 */       System.arraycopy(this.currentStock, 0, newStock, 0, this.currentStock.length);
/*     */       
/* 289 */       for (int i = this.currentStock.length; i < trades.length; i++) {
/* 290 */         newStock[i] = trades[i].getMaxStock();
/*     */       }
/* 292 */       this.currentStock = newStock;
/* 293 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasStock(int tradeIndex, int quantity) {
/* 300 */       if (tradeIndex < 0 || tradeIndex >= this.currentStock.length) return false; 
/* 301 */       return (this.currentStock[tradeIndex] >= quantity);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean decrementStock(int tradeIndex, int quantity) {
/* 310 */       if (!hasStock(tradeIndex, quantity)) return false; 
/* 311 */       this.currentStock[tradeIndex] = this.currentStock[tradeIndex] - quantity;
/* 312 */       return true;
/*     */     }
/*     */     
/*     */     public int getStock(int tradeIndex) {
/* 316 */       if (tradeIndex < 0 || tradeIndex >= this.currentStock.length) return 0; 
/* 317 */       return this.currentStock[tradeIndex];
/*     */     }
/*     */   }
/*     */   
/* 321 */   private final Map<String, ShopInstanceState> shopStates = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Instant calculateNextScheduledRestock(@Nonnull Instant gameTime, int intervalDays, int restockHour) {
/* 336 */     LocalDateTime dateTime = LocalDateTime.ofInstant(gameTime, ZoneOffset.UTC);
/*     */ 
/*     */     
/* 339 */     long daysSinceEpoch = Duration.between(WorldTimeResource.ZERO_YEAR, gameTime).toDays();
/*     */ 
/*     */ 
/*     */     
/* 343 */     long currentCycle = daysSinceEpoch / intervalDays;
/* 344 */     long nextRestockDaySinceEpoch = (currentCycle + 1L) * intervalDays;
/*     */ 
/*     */     
/* 347 */     boolean isTodayRestockDay = (daysSinceEpoch % intervalDays == 0L);
/* 348 */     if (isTodayRestockDay && dateTime.getHour() < restockHour) {
/* 349 */       nextRestockDaySinceEpoch = daysSinceEpoch;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     Instant nextRestockInstant = WorldTimeResource.ZERO_YEAR.plus(Duration.ofDays(nextRestockDaySinceEpoch)).plus(Duration.ofHours(restockHour));
/*     */     
/* 357 */     return nextRestockInstant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ShopInstanceState getOrCreateShopState(BarterShopAsset asset, @Nonnull Instant gameTime) {
/* 367 */     return this.shopStates.computeIfAbsent(asset.getId(), id -> {
/*     */           ShopInstanceState state = new ShopInstanceState();
/*     */           state.resetStockAndResolve(asset);
/*     */           RefreshInterval interval = asset.getRefreshInterval();
/*     */           if (interval != null) {
/*     */             state.setNextRefreshTime(calculateNextScheduledRestock(gameTime, interval.getDays(), asset.getRestockHour()));
/*     */           }
/*     */           return state;
/*     */         });
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
/*     */   public void checkRefresh(BarterShopAsset asset, @Nonnull Instant gameTime) {
/* 387 */     RefreshInterval interval = asset.getRefreshInterval();
/* 388 */     if (interval == null)
/*     */       return; 
/* 390 */     ShopInstanceState state = getOrCreateShopState(asset, gameTime);
/* 391 */     Instant nextRefresh = state.getNextRefreshTime();
/*     */     
/* 393 */     if (nextRefresh == null) {
/*     */       
/* 395 */       state.setNextRefreshTime(calculateNextScheduledRestock(gameTime, interval.getDays(), asset.getRestockHour()));
/* 396 */       save();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 401 */     if (!gameTime.isBefore(nextRefresh)) {
/* 402 */       state.resetStockAndResolve(asset);
/*     */       
/* 404 */       state.setNextRefreshTime(calculateNextScheduledRestock(gameTime, interval.getDays(), asset.getRestockHour()));
/* 405 */       save();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getStockArray(BarterShopAsset asset, @Nonnull Instant gameTime) {
/* 415 */     checkRefresh(asset, gameTime);
/* 416 */     ShopInstanceState state = getOrCreateShopState(asset, gameTime);
/*     */     
/* 418 */     if (state.expandStockIfNeeded(asset)) {
/* 419 */       save();
/*     */     }
/* 421 */     return (int[])state.getCurrentStock().clone();
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
/*     */   public BarterTrade[] getResolvedTrades(BarterShopAsset asset, @Nonnull Instant gameTime) {
/* 433 */     checkRefresh(asset, gameTime);
/* 434 */     ShopInstanceState state = getOrCreateShopState(asset, gameTime);
/* 435 */     return state.getResolvedTrades(asset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeTrade(BarterShopAsset asset, int tradeIndex, int quantity, @Nonnull Instant gameTime) {
/* 445 */     checkRefresh(asset, gameTime);
/* 446 */     ShopInstanceState state = getOrCreateShopState(asset, gameTime);
/* 447 */     boolean success = state.decrementStock(tradeIndex, quantity);
/* 448 */     if (success) {
/* 449 */       save();
/*     */     }
/* 451 */     return success;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\BarterShopState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */