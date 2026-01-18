/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.protocol.BlockTextures;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockTypeTextures
/*     */ {
/*     */   public static final BuilderCodec<BlockTypeTextures> CODEC;
/*     */   
/*     */   static {
/* 108 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockTypeTextures.class, BlockTypeTextures::new).append(new KeyedCodec("All", (Codec)Codec.STRING), (blockType, o) -> { blockType.up = o; blockType.down = o; blockType.north = o; blockType.south = o; blockType.west = o; blockType.east = o; }blockType -> null).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).append(new KeyedCodec("Sides", (Codec)Codec.STRING), (blockType, o) -> { blockType.north = o; blockType.south = o; blockType.west = o; blockType.east = o; }blockType -> null).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).append(new KeyedCodec("UpDown", (Codec)Codec.STRING), (blockType, o) -> { blockType.up = o; blockType.down = o; }blockType -> null).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).appendInherited(new KeyedCodec("Up", (Codec)Codec.STRING), (blockType, o) -> blockType.up = o, blockType -> blockType.up, (blockType, parent) -> blockType.up = parent.up).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).appendInherited(new KeyedCodec("Down", (Codec)Codec.STRING), (blockType, o) -> blockType.down = o, blockType -> blockType.down, (blockType, parent) -> blockType.down = parent.down).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).appendInherited(new KeyedCodec("North", (Codec)Codec.STRING), (blockType, o) -> blockType.north = o, blockType -> blockType.north, (blockType, parent) -> blockType.north = parent.north).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).appendInherited(new KeyedCodec("South", (Codec)Codec.STRING), (blockType, o) -> blockType.south = o, blockType -> blockType.south, (blockType, parent) -> blockType.south = parent.south).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).appendInherited(new KeyedCodec("West", (Codec)Codec.STRING), (blockType, o) -> blockType.west = o, blockType -> blockType.west, (blockType, parent) -> blockType.west = parent.west).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).appendInherited(new KeyedCodec("East", (Codec)Codec.STRING), (blockType, o) -> blockType.east = o, blockType -> blockType.east, (blockType, parent) -> blockType.east = parent.east).addValidator((Validator)CommonAssetValidator.TEXTURE_ITEM).add()).appendInherited(new KeyedCodec("Weight", (Codec)Codec.INTEGER), (blockType, o) -> blockType.weight = o.intValue(), blockType -> Integer.valueOf(blockType.weight), (blockType, parent) -> blockType.weight = parent.weight).add()).build();
/*     */   }
/* 110 */   protected String up = "BlockTextures/Unknown.png";
/* 111 */   protected String down = "BlockTextures/Unknown.png";
/* 112 */   protected String north = "BlockTextures/Unknown.png";
/* 113 */   protected String south = "BlockTextures/Unknown.png";
/* 114 */   protected String east = "BlockTextures/Unknown.png";
/* 115 */   protected String west = "BlockTextures/Unknown.png";
/* 116 */   protected int weight = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockTypeTextures(String all) {
/* 122 */     this.up = all;
/* 123 */     this.down = all;
/* 124 */     this.north = all;
/* 125 */     this.south = all;
/* 126 */     this.east = all;
/* 127 */     this.west = all;
/*     */   }
/*     */   
/*     */   public BlockTypeTextures(String up, String down, String north, String south, String east, String west, int weight) {
/* 131 */     this.up = up;
/* 132 */     this.down = down;
/* 133 */     this.north = north;
/* 134 */     this.south = south;
/* 135 */     this.east = east;
/* 136 */     this.west = west;
/* 137 */     this.weight = weight;
/*     */   }
/*     */   
/*     */   public String getUp() {
/* 141 */     return this.up;
/*     */   }
/*     */   
/*     */   public String getDown() {
/* 145 */     return this.down;
/*     */   }
/*     */   
/*     */   public String getNorth() {
/* 149 */     return this.north;
/*     */   }
/*     */   
/*     */   public String getSouth() {
/* 153 */     return this.south;
/*     */   }
/*     */   
/*     */   public String getEast() {
/* 157 */     return this.east;
/*     */   }
/*     */   
/*     */   public String getWest() {
/* 161 */     return this.west;
/*     */   }
/*     */   
/*     */   public float getWeight() {
/* 165 */     return this.weight;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockTextures toPacket(float totalWeight) {
/* 170 */     BlockTextures packet = new BlockTextures();
/* 171 */     packet.top = this.up;
/* 172 */     packet.bottom = this.down;
/* 173 */     packet.front = this.south;
/* 174 */     packet.back = this.north;
/* 175 */     packet.left = this.west;
/* 176 */     packet.right = this.east;
/* 177 */     packet.weight = this.weight / totalWeight;
/* 178 */     return packet;
/*     */   }
/*     */   
/*     */   public BlockTypeTextures() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\BlockTypeTextures.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */