/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.BlockFace;
/*     */ import com.hypixel.hytale.protocol.BlockMatcher;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFace;
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
/*     */ public class BlockMatcher
/*     */   implements NetworkSerializable<BlockMatcher>
/*     */ {
/*     */   @Nonnull
/*     */   public static BuilderCodec<BlockMatcher> CODEC;
/*     */   protected BlockConditionInteraction.BlockIdMatcher block;
/*     */   
/*     */   static {
/* 274 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockMatcher.class, BlockMatcher::new).appendInherited(new KeyedCodec("Block", (Codec)BlockConditionInteraction.BlockIdMatcher.CODEC), (blockMatcher, blockIdMatcher) -> blockMatcher.block = blockIdMatcher, blockMatcher -> blockMatcher.block, (blockMatcher, parent) -> blockMatcher.block = parent.block).documentation("Match against block values").add()).appendInherited(new KeyedCodec("Face", (Codec)BlockFace.CODEC), (blockMatcher, face) -> blockMatcher.face = BlockFace.toProtocolFace(face), blockMatcher -> BlockFace.fromProtocolFace(blockMatcher.face), (blockMatcher, parent) -> blockMatcher.face = parent.face).documentation("Match against a specific block face.").add()).appendInherited(new KeyedCodec("StaticFace", (Codec)Codec.BOOLEAN), (blockMatcher, aBoolean) -> blockMatcher.staticFace = aBoolean.booleanValue(), blockMatcher -> Boolean.valueOf(blockMatcher.staticFace), (blockMatcher, parent) -> blockMatcher.staticFace = parent.staticFace).documentation("Whether the face matching is unaffected by the block rotation or not.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 284 */   protected BlockFace face = BlockFace.None;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean staticFace;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockMatcher toPacket() {
/* 294 */     BlockMatcher packet = new BlockMatcher();
/* 295 */     if (this.block != null) packet.block = this.block.toPacket(); 
/* 296 */     if (this.face != null) packet.face = this.face; 
/* 297 */     packet.staticFace = this.staticFace;
/* 298 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 304 */     return "BlockMatcher{block=" + String.valueOf(this.block) + ", face=" + String.valueOf(this.face) + ", staticFace=" + this.staticFace + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\BlockConditionInteraction$BlockMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */