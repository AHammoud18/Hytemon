/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.AllowEmptyObject;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.protocol.BlockGathering;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
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
/*     */ public class BlockGathering
/*     */   implements NetworkSerializable<BlockGathering>
/*     */ {
/*     */   public static final BuilderCodec<BlockGathering> CODEC;
/*     */   protected BlockBreakingDropType breaking;
/*     */   protected HarvestingDropType harvest;
/*     */   protected SoftBlockDropType soft;
/*     */   protected PhysicsDropType physics;
/*     */   protected BlockToolData[] toolDataRaw;
/*     */   
/*     */   static {
/*  76 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockGathering.class, BlockGathering::new).append(new KeyedCodec("Breaking", (Codec)BlockBreakingDropType.CODEC), (blockGathering, o) -> blockGathering.breaking = o, blockGathering -> blockGathering.breaking).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).append(new KeyedCodec("Harvest", (Codec)HarvestingDropType.CODEC), (blockGathering, o) -> blockGathering.harvest = o, blockGathering -> blockGathering.harvest).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).append(new KeyedCodec("Soft", (Codec)SoftBlockDropType.CODEC), (blockGathering, o) -> blockGathering.soft = o, blockGathering -> blockGathering.soft).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).append(new KeyedCodec("Physics", (Codec)PhysicsDropType.CODEC), (blockGathering, o) -> blockGathering.physics = o, blockGathering -> blockGathering.physics).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).append(new KeyedCodec("Tools", (Codec)new ArrayCodec((Codec)BlockToolData.CODEC, x$0 -> new BlockToolData[x$0])), (blockGathering, o) -> blockGathering.toolDataRaw = o, blockGathering -> blockGathering.toolDataRaw).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).appendInherited(new KeyedCodec("UseDefaultDropWhenPlaced", (Codec)Codec.BOOLEAN), (o, v) -> o.useDefaultDropWhenPlaced = v.booleanValue(), o -> Boolean.valueOf(o.useDefaultDropWhenPlaced), (o, p) -> o.useDefaultDropWhenPlaced = p.useDefaultDropWhenPlaced).documentation("If this is set then player placed blocks will use the default drop behaviour instead of using the droplists.").add()).afterDecode(g -> { if (g.toolDataRaw != null) { g.toolData = (Map<String, BlockToolData>)new Object2ObjectOpenHashMap(); for (BlockToolData t : g.toolDataRaw) g.toolData.put(t.getTypeId(), t);  }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  84 */   protected Map<String, BlockToolData> toolData = Collections.emptyMap();
/*     */ 
/*     */   
/*     */   protected boolean useDefaultDropWhenPlaced = false;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockGathering toPacket() {
/*  93 */     BlockGathering packet = new BlockGathering();
/*  94 */     if (this.breaking != null) packet.breaking = this.breaking.toPacket(); 
/*  95 */     if (this.harvest != null) packet.harvest = this.harvest.toPacket(); 
/*  96 */     if (this.soft != null) packet.soft = this.soft.toPacket(); 
/*  97 */     return packet;
/*     */   }
/*     */   
/*     */   public BlockBreakingDropType getBreaking() {
/* 101 */     return this.breaking;
/*     */   }
/*     */   
/*     */   public HarvestingDropType getHarvest() {
/* 105 */     return this.harvest;
/*     */   }
/*     */   
/*     */   public SoftBlockDropType getSoft() {
/* 109 */     return this.soft;
/*     */   }
/*     */   
/*     */   public boolean isHarvestable() {
/* 113 */     return (this.harvest != null);
/*     */   }
/*     */   
/*     */   public boolean isSoft() {
/* 117 */     return (this.soft != null);
/*     */   }
/*     */   
/*     */   public PhysicsDropType getPhysics() {
/* 121 */     return this.physics;
/*     */   }
/*     */   
/*     */   public boolean shouldUseDefaultDropWhenPlaced() {
/* 125 */     return this.useDefaultDropWhenPlaced;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 131 */     return "BlockGathering{breaking=" + String.valueOf(this.breaking) + ", harvest=" + String.valueOf(this.harvest) + ", harvest=" + String.valueOf(this.soft) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, BlockToolData> getToolData() {
/* 140 */     return this.toolData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BlockToolData
/*     */   {
/*     */     public static final BuilderCodec<BlockToolData> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String typeId;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String stateId;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String itemId;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String dropListId;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 173 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockToolData.class, BlockToolData::new).append(new KeyedCodec("Type", (Codec)Codec.STRING), (toolData, o) -> toolData.typeId = o, toolData -> toolData.typeId).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).append(new KeyedCodec("State", (Codec)Codec.STRING), (toolData, o) -> toolData.stateId = o, toolData -> toolData.stateId).metadata((Metadata)AllowEmptyObject.INSTANCE).add()).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (toolData, s) -> toolData.itemId = s, toolData -> toolData.itemId).addValidatorLate(() -> Item.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("DropList", (Codec)new ContainedAssetCodec(ItemDropList.class, (AssetCodec)ItemDropList.CODEC)), (toolData, s) -> toolData.dropListId = s, toolData -> toolData.dropListId).addValidatorLate(() -> ItemDropList.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTypeId() {
/* 181 */       return this.typeId;
/*     */     }
/*     */     
/*     */     public String getStateId() {
/* 185 */       return this.stateId;
/*     */     }
/*     */     
/*     */     public String getItemId() {
/* 189 */       return this.itemId;
/*     */     }
/*     */     
/*     */     public String getDropListId() {
/* 193 */       return this.dropListId;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\BlockGathering.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */