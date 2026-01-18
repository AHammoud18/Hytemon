/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
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
/*    */ public class PhysicsDropType
/*    */ {
/*    */   public static final BuilderCodec<PhysicsDropType> CODEC;
/*    */   protected String itemId;
/*    */   protected String dropListId;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PhysicsDropType.class, PhysicsDropType::new).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (softBlock, o) -> softBlock.itemId = o, softBlock -> softBlock.itemId).addValidatorLate(() -> Item.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("DropList", (Codec)new ContainedAssetCodec(ItemDropList.class, (AssetCodec)ItemDropList.CODEC)), (softBlock, o) -> softBlock.dropListId = o, softBlock -> softBlock.dropListId).addValidatorLate(() -> ItemDropList.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PhysicsDropType(String itemId, String dropListId) {
/* 39 */     this.itemId = itemId;
/* 40 */     this.dropListId = dropListId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected PhysicsDropType() {}
/*    */   
/*    */   public String getItemId() {
/* 47 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public String getDropListId() {
/* 51 */     return this.dropListId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public PhysicsDropType withoutDrops() {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 62 */     return "PhysicsDropType{itemId=" + this.itemId + ", dropListId='" + this.dropListId + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\PhysicsDropType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */