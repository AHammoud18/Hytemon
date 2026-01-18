/*    */ package com.hypixel.hytale.builtin.adventure.shop.barter;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import java.util.List;
/*    */ import java.util.Random;
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
/*    */ public abstract class TradeSlot
/*    */ {
/* 21 */   public static final CodecMapCodec<TradeSlot> CODEC = new CodecMapCodec("Type");
/*    */   
/* 23 */   public static final TradeSlot[] EMPTY_ARRAY = new TradeSlot[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public abstract List<BarterTrade> resolve(@Nonnull Random paramRandom);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract int getSlotCount();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 45 */     CODEC.register("Fixed", FixedTradeSlot.class, (Codec)FixedTradeSlot.CODEC);
/* 46 */     CODEC.register("Pool", PoolTradeSlot.class, (Codec)PoolTradeSlot.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\TradeSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */