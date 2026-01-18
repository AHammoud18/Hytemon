/*    */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Random;
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
/*    */ 
/*    */ public class FixedTradeSlot
/*    */   extends TradeSlot
/*    */ {
/*    */   public static final BuilderCodec<FixedTradeSlot> CODEC;
/*    */   protected BarterTrade trade;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(FixedTradeSlot.class, FixedTradeSlot::new).append(new KeyedCodec("Trade", (Codec)BarterTrade.CODEC), (slot, trade) -> slot.trade = trade, slot -> slot.trade).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public FixedTradeSlot(@Nonnull BarterTrade trade) {
/* 39 */     this.trade = trade;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FixedTradeSlot() {}
/*    */   
/*    */   @Nonnull
/*    */   public BarterTrade getTrade() {
/* 47 */     return this.trade;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<BarterTrade> resolve(@Nonnull Random random) {
/* 53 */     return Collections.singletonList(this.trade);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSlotCount() {
/* 58 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 64 */     return "FixedTradeSlot{trade=" + String.valueOf(this.trade) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\FixedTradeSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */