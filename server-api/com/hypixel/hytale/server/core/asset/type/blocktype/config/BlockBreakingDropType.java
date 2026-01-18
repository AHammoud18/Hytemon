/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.protocol.BlockBreaking;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ public class BlockBreakingDropType
/*     */   implements NetworkSerializable<BlockBreaking>
/*     */ {
/*     */   public static final BuilderCodec<BlockBreakingDropType> CODEC;
/*     */   protected String gatherType;
/*     */   protected int quality;
/*     */   protected String itemId;
/*     */   protected String dropListId;
/*     */   
/*     */   static {
/*  53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockBreakingDropType.class, BlockBreakingDropType::new).append(new KeyedCodec("GatherType", (Codec)Codec.STRING), (blockBreaking, s) -> blockBreaking.gatherType = s, blockBreaking -> blockBreaking.gatherType).add()).append(new KeyedCodec("Quality", (Codec)Codec.INTEGER), (blockBreaking, s) -> blockBreaking.quality = s.intValue(), blockBreaking -> Integer.valueOf(blockBreaking.quality)).add()).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (blockBreaking, s) -> blockBreaking.itemId = s, blockBreaking -> blockBreaking.itemId).addValidatorLate(() -> Item.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (blockBreaking, s) -> blockBreaking.quantity = s.intValue(), blockBreaking -> Integer.valueOf(blockBreaking.quantity)).add()).append(new KeyedCodec("DropList", (Codec)new ContainedAssetCodec(ItemDropList.class, (AssetCodec)ItemDropList.CODEC)), (blockBreaking, s) -> blockBreaking.dropListId = s, blockBreaking -> blockBreaking.dropListId).addValidatorLate(() -> ItemDropList.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   protected int quantity = 1;
/*     */   
/*     */   public BlockBreakingDropType(String gatherType, int quality, int quantity, String itemId, String dropListId) {
/*  62 */     this.gatherType = gatherType;
/*  63 */     this.quality = quality;
/*  64 */     this.quantity = quantity;
/*  65 */     this.itemId = itemId;
/*  66 */     this.dropListId = dropListId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockBreaking toPacket() {
/*  75 */     BlockBreaking packet = new BlockBreaking();
/*  76 */     packet.gatherType = this.gatherType;
/*  77 */     packet.quality = this.quality;
/*  78 */     packet.quantity = this.quantity;
/*  79 */     if (this.itemId != null) packet.itemId = this.itemId.toString(); 
/*  80 */     packet.dropListId = this.dropListId;
/*  81 */     return packet;
/*     */   }
/*     */   
/*     */   public String getGatherType() {
/*  85 */     return this.gatherType;
/*     */   }
/*     */   
/*     */   public int getQuality() {
/*  89 */     return this.quality;
/*     */   }
/*     */   
/*     */   public int getQuantity() {
/*  93 */     return this.quantity;
/*     */   }
/*     */   
/*     */   public String getItemId() {
/*  97 */     return this.itemId;
/*     */   }
/*     */   
/*     */   public String getDropListId() {
/* 101 */     return this.dropListId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockBreakingDropType withoutDrops() {
/* 106 */     return new BlockBreakingDropType(this.gatherType, 0, 0, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 112 */     return "BlockBreakingDropType{gatherType='" + this.gatherType + "', quality=" + this.quality + ", quantity=" + this.quantity + ", itemId=" + this.itemId + ", dropListId='" + this.dropListId + "'}";
/*     */   }
/*     */   
/*     */   protected BlockBreakingDropType() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\BlockBreakingDropType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */