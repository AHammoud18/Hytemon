/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.protocol.Harvesting;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class HarvestingDropType
/*    */   implements NetworkSerializable<Harvesting>
/*    */ {
/*    */   public static final BuilderCodec<HarvestingDropType> CODEC;
/*    */   protected String itemId;
/*    */   protected String dropListId;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HarvestingDropType.class, HarvestingDropType::new).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (harvesting, o) -> harvesting.itemId = o, harvesting -> harvesting.itemId).addValidatorLate(() -> Item.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("DropList", (Codec)new ContainedAssetCodec(ItemDropList.class, (AssetCodec)ItemDropList.CODEC)), (harvesting, o) -> harvesting.dropListId = o, harvesting -> harvesting.dropListId).addValidatorLate(() -> ItemDropList.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public HarvestingDropType(String itemId, String dropListId) {
/* 42 */     this.itemId = itemId;
/* 43 */     this.dropListId = dropListId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected HarvestingDropType() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Harvesting toPacket() {
/* 52 */     Harvesting packet = new Harvesting();
/* 53 */     if (this.itemId != null) packet.itemId = this.itemId.toString(); 
/* 54 */     packet.dropListId = this.dropListId;
/* 55 */     return packet;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 59 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public String getDropListId() {
/* 63 */     return this.dropListId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public HarvestingDropType withoutDrops() {
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 74 */     return "HarvestingDropType{itemId=" + this.itemId + ", dropListId='" + this.dropListId + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\HarvestingDropType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */