/*     */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ public class PoolTradeSlot
/*     */   extends TradeSlot
/*     */ {
/*     */   public static final BuilderCodec<PoolTradeSlot> CODEC;
/*     */   
/*     */   static {
/*  46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PoolTradeSlot.class, PoolTradeSlot::new).append(new KeyedCodec("SlotCount", (Codec)Codec.INTEGER), (slot, count) -> slot.slotCount = count.intValue(), slot -> Integer.valueOf(slot.slotCount)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).append(new KeyedCodec("Trades", (Codec)new ArrayCodec((Codec)WeightedTrade.CODEC, x$0 -> new WeightedTrade[x$0])), (slot, trades) -> slot.trades = trades, slot -> slot.trades).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*  48 */   protected int slotCount = 1;
/*  49 */   protected WeightedTrade[] trades = WeightedTrade.EMPTY_ARRAY;
/*     */   
/*     */   public PoolTradeSlot(int slotCount, @Nonnull WeightedTrade[] trades) {
/*  52 */     this.slotCount = slotCount;
/*  53 */     this.trades = trades;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPoolSlotCount() {
/*  60 */     return this.slotCount;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public WeightedTrade[] getTrades() {
/*  65 */     return this.trades;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<BarterTrade> resolve(@Nonnull Random random) {
/*  71 */     ObjectArrayList<BarterTrade> objectArrayList = new ObjectArrayList(this.slotCount);
/*     */     
/*  73 */     if (this.trades.length == 0) {
/*  74 */       return (List<BarterTrade>)objectArrayList;
/*     */     }
/*     */ 
/*     */     
/*  78 */     ObjectArrayList<WeightedTrade> available = new ObjectArrayList(this.trades.length);
/*  79 */     available.addAll(Arrays.asList(this.trades));
/*     */ 
/*     */     
/*  82 */     int toSelect = Math.min(this.slotCount, available.size());
/*  83 */     for (int i = 0; i < toSelect; i++) {
/*  84 */       int selectedIndex = selectWeightedIndex((List<WeightedTrade>)available, random);
/*  85 */       if (selectedIndex >= 0) {
/*  86 */         WeightedTrade selected = (WeightedTrade)available.remove(selectedIndex);
/*  87 */         objectArrayList.add(selected.toBarterTrade(random));
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     return (List<BarterTrade>)objectArrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotCount() {
/*  96 */     return this.slotCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int selectWeightedIndex(@Nonnull List<WeightedTrade> trades, @Nonnull Random random) {
/* 107 */     if (trades.isEmpty()) {
/* 108 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 112 */     double totalWeight = 0.0D;
/* 113 */     for (WeightedTrade trade : trades) {
/* 114 */       totalWeight += trade.getWeight();
/*     */     }
/*     */     
/* 117 */     if (totalWeight <= 0.0D)
/*     */     {
/* 119 */       return random.nextInt(trades.size());
/*     */     }
/*     */ 
/*     */     
/* 123 */     double roll = random.nextDouble() * totalWeight;
/* 124 */     double cumulative = 0.0D;
/* 125 */     for (int i = 0; i < trades.size(); i++) {
/* 126 */       cumulative += ((WeightedTrade)trades.get(i)).getWeight();
/* 127 */       if (roll < cumulative) {
/* 128 */         return i;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 133 */     return trades.size() - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 139 */     return "PoolTradeSlot{slotCount=" + this.slotCount + ", trades=" + Arrays.toString((Object[])this.trades) + "}";
/*     */   }
/*     */   
/*     */   protected PoolTradeSlot() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\PoolTradeSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */