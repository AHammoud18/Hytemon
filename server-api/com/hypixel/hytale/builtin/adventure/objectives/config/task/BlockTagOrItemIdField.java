/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockTagOrItemIdField
/*    */ {
/*    */   public static final BuilderCodec<BlockTagOrItemIdField> CODEC;
/*    */   protected String blockTag;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockTagOrItemIdField.class, BlockTagOrItemIdField::new).append(new KeyedCodec("BlockTag", (Codec)Codec.STRING), (blockTagOrItemIdField, s) -> blockTagOrItemIdField.blockTag = s, blockTagOrItemIdField -> blockTagOrItemIdField.blockTag).add()).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (blockTagOrItemIdField, blockTypeKey) -> blockTagOrItemIdField.itemId = blockTypeKey, blockTagOrItemIdField -> blockTagOrItemIdField.itemId).addValidator(Item.VALIDATOR_CACHE.getValidator()).add()).validator((task, validationResults) -> { if (task.blockTag == null && task.itemId == null) validationResults.fail("One and only one of BlockTag or ItemId must be set!");  })).afterDecode(blockTagOrItemIdField -> { if (blockTagOrItemIdField.blockTag != null) blockTagOrItemIdField.blockTagIndex = AssetRegistry.getOrCreateTagIndex(blockTagOrItemIdField.blockTag);  })).build();
/*    */   }
/*    */   
/* 39 */   protected int blockTagIndex = Integer.MIN_VALUE;
/*    */   protected String itemId;
/*    */   
/*    */   public BlockTagOrItemIdField(String blockTag, String itemId) {
/* 43 */     this.blockTag = blockTag;
/* 44 */     this.itemId = itemId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBlockTagIndex() {
/* 51 */     return this.blockTagIndex;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 55 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public boolean isBlockTypeIncluded(String blockTypeToCheck) {
/* 59 */     if (this.blockTagIndex != Integer.MIN_VALUE)
/* 60 */       return Item.getAssetMap().getKeysForTag(this.blockTagIndex).contains(blockTypeToCheck); 
/* 61 */     if (this.itemId != null) {
/* 62 */       return this.itemId.equals(blockTypeToCheck);
/*    */     }
/*    */     
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public void consumeItemStacks(@Nonnull ItemContainer container, int quantity) {
/* 69 */     if (this.itemId != null) {
/* 70 */       container.removeItemStack(new ItemStack(this.itemId, quantity));
/* 71 */     } else if (this.blockTagIndex != Integer.MIN_VALUE) {
/* 72 */       container.removeTag(this.blockTagIndex, quantity);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 78 */     if (this == o) return true; 
/* 79 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 81 */     BlockTagOrItemIdField that = (BlockTagOrItemIdField)o;
/*    */     
/* 83 */     if ((this.blockTag != null) ? !this.blockTag.equals(that.blockTag) : (that.blockTag != null)) return false; 
/* 84 */     return (this.itemId != null) ? this.itemId.equals(that.itemId) : ((that.itemId == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     int result = (this.blockTag != null) ? this.blockTag.hashCode() : 0;
/* 90 */     result = 31 * result + ((this.itemId != null) ? this.itemId.hashCode() : 0);
/* 91 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 97 */     return "BlockTagOrItemIdField{blockTag='" + this.blockTag + "', itemId=" + this.itemId + "}";
/*    */   }
/*    */   
/*    */   protected BlockTagOrItemIdField() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\BlockTagOrItemIdField.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */