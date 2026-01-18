/*    */ package com.hypixel.hytale.builtin.adventure.objectives.historydata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ItemObjectiveRewardHistoryData
/*    */   extends ObjectiveRewardHistoryData
/*    */ {
/*    */   public static final BuilderCodec<ItemObjectiveRewardHistoryData> CODEC;
/*    */   protected String itemId;
/*    */   protected int quantity;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemObjectiveRewardHistoryData.class, ItemObjectiveRewardHistoryData::new, BASE_CODEC).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (itemObjectiveRewardDetails, blockTypeKey) -> itemObjectiveRewardDetails.itemId = blockTypeKey, itemObjectiveRewardDetails -> itemObjectiveRewardDetails.itemId).addValidator(Item.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (itemObjectiveRewardDetails, integer) -> itemObjectiveRewardDetails.quantity = integer.intValue(), itemObjectiveRewardDetails -> Integer.valueOf(itemObjectiveRewardDetails.quantity)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemObjectiveRewardHistoryData(String itemId, int quantity) {
/* 28 */     this.itemId = itemId;
/* 29 */     this.quantity = quantity;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemObjectiveRewardHistoryData() {}
/*    */   
/*    */   public String getItemId() {
/* 36 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getQuantity() {
/* 40 */     return this.quantity;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 46 */     return "ItemObjectiveRewardHistoryData{itemId=" + this.itemId + ", quantity=" + this.quantity + "} " + super
/*    */ 
/*    */       
/* 49 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\historydata\ItemObjectiveRewardHistoryData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */