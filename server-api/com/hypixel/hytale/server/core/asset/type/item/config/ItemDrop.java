/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.codec.validation.validator.RangeRefValidator;
/*    */ import java.util.Random;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bson.BsonDocument;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemDrop
/*    */ {
/*    */   public static final BuilderCodec<ItemDrop> CODEC;
/*    */   protected String itemId;
/*    */   protected BsonDocument metadata;
/*    */   
/*    */   static {
/* 42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemDrop.class, ItemDrop::new).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (itemDrop, s) -> itemDrop.itemId = s, itemDrop -> itemDrop.itemId).addValidatorLate(() -> Item.VALIDATOR_CACHE.getValidator().late()).add()).addField(new KeyedCodec("Metadata", (Codec)Codec.BSON_DOCUMENT), (itemDrop, document) -> itemDrop.metadata = document, itemDrop -> itemDrop.metadata)).append(new KeyedCodec("QuantityMin", (Codec)Codec.INTEGER), (itemDrop, i) -> itemDrop.quantityMin = i.intValue(), itemDrop -> Integer.valueOf(itemDrop.quantityMin)).addValidator((Validator)new RangeRefValidator(null, "1/QuantityMax", true)).add()).append(new KeyedCodec("QuantityMax", (Codec)Codec.INTEGER), (itemDrop, i) -> itemDrop.quantityMax = i.intValue(), itemDrop -> Integer.valueOf(itemDrop.quantityMax)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).build();
/*    */   }
/*    */ 
/*    */   
/* 46 */   protected int quantityMin = 1;
/* 47 */   protected int quantityMax = 1;
/*    */   
/*    */   public ItemDrop(String itemId, BsonDocument metadata, int quantityMin, int quantityMax) {
/* 50 */     this.itemId = itemId;
/* 51 */     this.metadata = metadata;
/* 52 */     this.quantityMin = quantityMin;
/* 53 */     this.quantityMax = quantityMax;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getItemId() {
/* 60 */     return this.itemId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BsonDocument getMetadata() {
/* 65 */     if (this.metadata == null) return null; 
/* 66 */     return this.metadata.clone();
/*    */   }
/*    */   
/*    */   public int getQuantityMin() {
/* 70 */     return this.quantityMin;
/*    */   }
/*    */   
/*    */   public int getQuantityMax() {
/* 74 */     return this.quantityMax;
/*    */   }
/*    */   
/*    */   public int getRandomQuantity(@Nonnull Random random) {
/* 78 */     return random.nextInt(Math.max(this.quantityMax - this.quantityMin + 1, 1)) + this.quantityMin;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 84 */     return "ItemDrop{itemId='" + this.itemId + "', metadata=" + String.valueOf(this.metadata) + ", quantityMin=" + this.quantityMin + ", quantityMax=" + this.quantityMax + "}";
/*    */   }
/*    */   
/*    */   protected ItemDrop() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemDrop.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */