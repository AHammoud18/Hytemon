/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.protocol.BlockIdMatcher;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
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
/*     */ public class BlockIdMatcher
/*     */   implements NetworkSerializable<BlockIdMatcher>
/*     */ {
/*     */   @Nonnull
/*     */   public static BuilderCodec<BlockIdMatcher> CODEC;
/*     */   protected String id;
/*     */   protected String state;
/*     */   protected String tag;
/*     */   
/*     */   static {
/* 197 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockIdMatcher.class, BlockIdMatcher::new).appendInherited(new KeyedCodec("Id", (Codec)Codec.STRING), (blockIdMatcher, s) -> blockIdMatcher.id = s, blockIdMatcher -> blockIdMatcher.id, (blockIdMatcher, parent) -> blockIdMatcher.id = parent.id).addValidatorLate(() -> BlockType.VALIDATOR_CACHE.getValidator().late()).documentation("Match against a specific block id.").add()).appendInherited(new KeyedCodec("State", (Codec)Codec.STRING), (blockIdMatcher, s) -> blockIdMatcher.state = s, blockIdMatcher -> blockIdMatcher.state, (blockIdMatcher, parent) -> blockIdMatcher.state = parent.state).documentation("Match against specific block state.").add()).appendInherited(new KeyedCodec("Tag", (Codec)Codec.STRING), (blockIdMatcher, s) -> blockIdMatcher.tag = s, blockIdMatcher -> blockIdMatcher.tag, (blockIdMatcher, parent) -> blockIdMatcher.tag = parent.tag).documentation("Match against specific block tag.").add()).afterDecode(blockIdMatcher -> { if (blockIdMatcher.tag != null) blockIdMatcher.tagIndex = AssetRegistry.getOrCreateTagIndex(blockIdMatcher.tag);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   protected int tagIndex = Integer.MIN_VALUE;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockIdMatcher toPacket() {
/* 222 */     BlockIdMatcher packet = new BlockIdMatcher();
/* 223 */     if (this.id != null) packet.id = this.id; 
/* 224 */     if (this.state != null) packet.state = this.state; 
/* 225 */     packet.tagIndex = this.tagIndex;
/* 226 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 232 */     return "BlockIdMatcher{id='" + this.id + "', state='" + this.state + "', tag='" + this.tag + "'}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\BlockConditionInteraction$BlockIdMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */