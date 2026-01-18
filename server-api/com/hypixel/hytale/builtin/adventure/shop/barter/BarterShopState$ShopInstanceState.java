/*     */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.time.Instant;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ThreadLocalRandom;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShopInstanceState
/*     */ {
/* 157 */   private int[] currentStock = new int[0];
/*     */   
/*     */   private Instant nextRefreshTime;
/*     */   
/*     */   private Long resolveSeed;
/*     */   
/*     */   private transient BarterTrade[] resolvedTrades;
/*     */ 
/*     */   
/*     */   public ShopInstanceState() {}
/*     */ 
/*     */   
/*     */   public ShopInstanceState(int tradeCount) {
/* 170 */     this.currentStock = new int[tradeCount];
/* 171 */     this.nextRefreshTime = null;
/*     */   }
/*     */   
/*     */   public int[] getCurrentStock() {
/* 175 */     return this.currentStock;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instant getNextRefreshTime() {
/* 180 */     return this.nextRefreshTime;
/*     */   }
/*     */   
/*     */   public void setNextRefreshTime(Instant time) {
/* 184 */     this.nextRefreshTime = time;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Long getResolveSeed() {
/* 189 */     return this.resolveSeed;
/*     */   }
/*     */   
/*     */   public void setResolveSeed(Long seed) {
/* 193 */     this.resolveSeed = seed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BarterTrade[] getResolvedTrades(@Nonnull BarterShopAsset asset) {
/* 203 */     if (!asset.hasTradeSlots())
/*     */     {
/* 205 */       return (asset.getTrades() != null) ? asset.getTrades() : new BarterTrade[0];
/*     */     }
/*     */ 
/*     */     
/* 209 */     if (this.resolvedTrades != null) {
/* 210 */       return this.resolvedTrades;
/*     */     }
/*     */ 
/*     */     
/* 214 */     if (this.resolveSeed == null) {
/* 215 */       this.resolveSeed = Long.valueOf(ThreadLocalRandom.current().nextLong());
/*     */     }
/* 217 */     this.resolvedTrades = resolveTradeSlots(asset, this.resolveSeed.longValue());
/* 218 */     return this.resolvedTrades;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static BarterTrade[] resolveTradeSlots(@Nonnull BarterShopAsset asset, long seed) {
/* 226 */     TradeSlot[] slots = asset.getTradeSlots();
/* 227 */     if (slots == null || slots.length == 0) {
/* 228 */       return new BarterTrade[0];
/*     */     }
/*     */     
/* 231 */     Random random = new Random(seed);
/* 232 */     ObjectArrayList<BarterTrade> objectArrayList = new ObjectArrayList();
/* 233 */     for (TradeSlot slot : slots) {
/* 234 */       objectArrayList.addAll(slot.resolve(random));
/*     */     }
/* 236 */     return objectArrayList.<BarterTrade>toArray(new BarterTrade[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetStockAndResolve(@Nonnull BarterShopAsset asset) {
/* 243 */     if (asset.hasTradeSlots()) {
/*     */       
/* 245 */       this.resolveSeed = Long.valueOf(ThreadLocalRandom.current().nextLong());
/* 246 */       this.resolvedTrades = resolveTradeSlots(asset, this.resolveSeed.longValue());
/*     */     } else {
/* 248 */       this.resolvedTrades = null;
/*     */     } 
/*     */     
/* 251 */     BarterTrade[] trades = getResolvedTrades(asset);
/* 252 */     this.currentStock = new int[trades.length];
/* 253 */     for (int i = 0; i < trades.length; i++) {
/* 254 */       this.currentStock[i] = trades[i].getMaxStock();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetStock(BarterShopAsset asset) {
/* 264 */     BarterTrade[] trades = getResolvedTrades(asset);
/*     */     
/* 266 */     if (this.currentStock.length != trades.length) {
/* 267 */       this.currentStock = new int[trades.length];
/*     */     }
/* 269 */     for (int i = 0; i < trades.length; i++) {
/* 270 */       this.currentStock[i] = trades[i].getMaxStock();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean expandStockIfNeeded(BarterShopAsset asset) {
/* 281 */     BarterTrade[] trades = getResolvedTrades(asset);
/* 282 */     if (this.currentStock.length >= trades.length) {
/* 283 */       return false;
/*     */     }
/* 285 */     int[] newStock = new int[trades.length];
/*     */     
/* 287 */     System.arraycopy(this.currentStock, 0, newStock, 0, this.currentStock.length);
/*     */     
/* 289 */     for (int i = this.currentStock.length; i < trades.length; i++) {
/* 290 */       newStock[i] = trades[i].getMaxStock();
/*     */     }
/* 292 */     this.currentStock = newStock;
/* 293 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasStock(int tradeIndex, int quantity) {
/* 300 */     if (tradeIndex < 0 || tradeIndex >= this.currentStock.length) return false; 
/* 301 */     return (this.currentStock[tradeIndex] >= quantity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decrementStock(int tradeIndex, int quantity) {
/* 310 */     if (!hasStock(tradeIndex, quantity)) return false; 
/* 311 */     this.currentStock[tradeIndex] = this.currentStock[tradeIndex] - quantity;
/* 312 */     return true;
/*     */   }
/*     */   
/*     */   public int getStock(int tradeIndex) {
/* 316 */     if (tradeIndex < 0 || tradeIndex >= this.currentStock.length) return 0; 
/* 317 */     return this.currentStock[tradeIndex];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\BarterShopState$ShopInstanceState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */