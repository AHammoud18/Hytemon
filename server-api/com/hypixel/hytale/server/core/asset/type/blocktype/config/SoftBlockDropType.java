/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.protocol.SoftBlock;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*    */ 
/*    */ 
/*    */ public class SoftBlockDropType
/*    */   implements NetworkSerializable<SoftBlock>
/*    */ {
/*    */   public static final BuilderCodec<SoftBlockDropType> CODEC;
/*    */   protected String itemId;
/*    */   protected String dropListId;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SoftBlockDropType.class, SoftBlockDropType::new).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (softBlock, o) -> softBlock.itemId = o, softBlock -> softBlock.itemId).addValidatorLate(() -> Item.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("DropList", (Codec)new ContainedAssetCodec(ItemDropList.class, (AssetCodec)ItemDropList.CODEC)), (softBlock, o) -> softBlock.dropListId = o, softBlock -> softBlock.dropListId).addValidatorLate(() -> ItemDropList.VALIDATOR_CACHE.getValidator().late()).add()).addField(new KeyedCodec("IsWeaponBreakable", (Codec)Codec.BOOLEAN), (blockGathering, o) -> blockGathering.isWeaponBreakable = o.booleanValue(), blockGathering -> Boolean.valueOf(blockGathering.isWeaponBreakable))).build();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isWeaponBreakable = true;
/*    */   
/*    */   public SoftBlockDropType(String itemId, String dropListId, boolean isWeaponBreakable) {
/* 47 */     this.itemId = itemId;
/* 48 */     this.dropListId = dropListId;
/* 49 */     this.isWeaponBreakable = isWeaponBreakable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SoftBlock toPacket() {
/* 58 */     SoftBlock packet = new SoftBlock();
/* 59 */     if (this.itemId != null) packet.itemId = this.itemId.toString(); 
/* 60 */     packet.dropListId = this.dropListId;
/* 61 */     packet.isWeaponBreakable = this.isWeaponBreakable;
/* 62 */     return packet;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 66 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public String getDropListId() {
/* 70 */     return this.dropListId;
/*    */   }
/*    */   
/*    */   public boolean isWeaponBreakable() {
/* 74 */     return this.isWeaponBreakable;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SoftBlockDropType withoutDrops() {
/* 79 */     return new SoftBlockDropType(null, null, this.isWeaponBreakable);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 85 */     return "SoftBlockDropType{itemId=" + this.itemId + ", dropListId='" + this.dropListId + "', isWeaponBreakable='" + this.isWeaponBreakable + "'}";
/*    */   }
/*    */   
/*    */   protected SoftBlockDropType() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\SoftBlockDropType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */