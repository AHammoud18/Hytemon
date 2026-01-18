/*    */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BarterTrade
/*    */ {
/*    */   public static final BuilderCodec<BarterTrade> CODEC;
/*    */   protected BarterItemStack output;
/*    */   protected BarterItemStack[] input;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BarterTrade.class, BarterTrade::new).append(new KeyedCodec("Output", (Codec)BarterItemStack.CODEC), (trade, stack) -> trade.output = stack, trade -> trade.output).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Input", (Codec)new ArrayCodec((Codec)BarterItemStack.CODEC, x$0 -> new BarterItemStack[x$0])), (trade, stacks) -> trade.input = stacks, trade -> trade.input).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Stock", (Codec)Codec.INTEGER), (trade, i) -> trade.maxStock = i.intValue(), trade -> Integer.valueOf(trade.maxStock)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).build();
/*    */   }
/*    */ 
/*    */   
/* 36 */   protected int maxStock = 10;
/*    */   
/*    */   public BarterTrade(BarterItemStack output, BarterItemStack[] input, int maxStock) {
/* 39 */     this.output = output;
/* 40 */     this.input = input;
/* 41 */     this.maxStock = maxStock;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BarterItemStack getOutput() {
/* 51 */     return this.output;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BarterItemStack[] getInput() {
/* 58 */     return this.input;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxStock() {
/* 65 */     return this.maxStock;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 71 */     return "BarterTrade{output=" + String.valueOf(this.output) + ", input=" + Arrays.toString((Object[])this.input) + ", maxStock=" + this.maxStock + "}";
/*    */   }
/*    */   
/*    */   protected BarterTrade() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\BarterTrade.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */