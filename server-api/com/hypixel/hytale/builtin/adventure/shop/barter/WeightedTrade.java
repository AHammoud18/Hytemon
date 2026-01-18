/*     */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.map.IWeightedElement;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeightedTrade
/*     */   implements IWeightedElement
/*     */ {
/*     */   public static final BuilderCodec<WeightedTrade> CODEC;
/*     */   
/*     */   static {
/*  61 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WeightedTrade.class, WeightedTrade::new).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE), (wt, w) -> wt.weight = w.doubleValue(), wt -> Double.valueOf(wt.weight)).add()).append(new KeyedCodec("Output", (Codec)BarterItemStack.CODEC), (wt, stack) -> wt.output = stack, wt -> wt.output).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Input", (Codec)new ArrayCodec((Codec)BarterItemStack.CODEC, x$0 -> new BarterItemStack[x$0])), (wt, stacks) -> wt.input = stacks, wt -> wt.input).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Stock", (Codec)Codec.INT_ARRAY), (wt, arr) -> wt.stockRange = arr, wt -> wt.stockRange).add()).build();
/*     */   }
/*  63 */   public static final WeightedTrade[] EMPTY_ARRAY = new WeightedTrade[0];
/*     */   
/*  65 */   protected double weight = 100.0D;
/*     */   protected BarterItemStack output;
/*     */   protected BarterItemStack[] input;
/*  68 */   protected int[] stockRange = new int[] { 10 };
/*     */   
/*     */   public WeightedTrade(double weight, @Nonnull BarterItemStack output, @Nonnull BarterItemStack[] input, int stock) {
/*  71 */     this.weight = weight;
/*  72 */     this.output = output;
/*  73 */     this.input = input;
/*  74 */     this.stockRange = new int[] { stock };
/*     */   }
/*     */   
/*     */   public WeightedTrade(double weight, @Nonnull BarterItemStack output, @Nonnull BarterItemStack[] input, int stockMin, int stockMax) {
/*  78 */     this.weight = weight;
/*  79 */     this.output = output;
/*  80 */     this.input = input;
/*  81 */     this.stockRange = new int[] { stockMin, stockMax };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWeight() {
/*  89 */     return this.weight;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BarterItemStack getOutput() {
/*  94 */     return this.output;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BarterItemStack[] getInput() {
/*  99 */     return this.input;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public int[] getStockRange() {
/* 108 */     return this.stockRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasStockRange() {
/* 115 */     return (this.stockRange != null && this.stockRange.length == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStockMin() {
/* 122 */     return (this.stockRange != null && this.stockRange.length > 0) ? this.stockRange[0] : 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStockMax() {
/* 129 */     return (this.stockRange != null && this.stockRange.length > 1) ? this.stockRange[1] : getStockMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int resolveStock(@Nonnull Random random) {
/* 138 */     if (!hasStockRange()) {
/* 139 */       return getStockMin();
/*     */     }
/* 141 */     int min = getStockMin();
/* 142 */     int max = getStockMax();
/* 143 */     if (min >= max) {
/* 144 */       return min;
/*     */     }
/* 146 */     return min + random.nextInt(max - min + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BarterTrade toBarterTrade(@Nonnull Random random) {
/* 155 */     return new BarterTrade(this.output, this.input, resolveStock(random));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BarterTrade toBarterTrade() {
/* 164 */     return new BarterTrade(this.output, this.input, getStockMin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 172 */     String stockStr = hasStockRange() ? ("[" + getStockMin() + ", " + getStockMax() + "]") : String.valueOf(getStockMin());
/* 173 */     return "WeightedTrade{weight=" + this.weight + ", output=" + String.valueOf(this.output) + ", input=" + 
/* 174 */       Arrays.toString((Object[])this.input) + ", stock=" + stockStr + "}";
/*     */   }
/*     */   
/*     */   protected WeightedTrade() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\WeightedTrade.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */