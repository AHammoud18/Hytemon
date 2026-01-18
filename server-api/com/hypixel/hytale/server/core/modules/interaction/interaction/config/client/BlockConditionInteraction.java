/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockFace;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFace;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockConditionInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<BlockConditionInteraction> CODEC;
/*     */   private BlockMatcher[] matchers;
/*     */   
/*     */   static {
/*  48 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockConditionInteraction.class, BlockConditionInteraction::new, SimpleBlockInteraction.CODEC).documentation("Tests the target block and executes `Next` if it matches all the conditions, otherwise `Failed` is run.")).appendInherited(new KeyedCodec("Matchers", (Codec)new ArrayCodec((Codec)BlockMatcher.CODEC, x$0 -> new BlockMatcher[x$0])), (o, i) -> o.matchers = i, o -> o.matchers, (o, p) -> o.matchers = p.matchers).documentation("The matchers to test the block against.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  58 */     BlockFace face = (context.getClientState()).blockFace;
/*  59 */     doInteraction(context, world, targetBlock, face);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {
/*  66 */     (context.getState()).blockFace = BlockFace.Up;
/*  67 */     doInteraction(context, world, targetBlock, (context.getState()).blockFace);
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
/*     */   private void doInteraction(@Nonnull InteractionContext context, @Nonnull World world, @Nonnull Vector3i targetBlock, @Nonnull BlockFace face) {
/*  79 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/*  80 */     if (chunk == null)
/*  81 */       return;  BlockType blockType = chunk.getBlockType(targetBlock);
/*  82 */     RotationTuple blockRotation = chunk.getRotation(targetBlock.x, targetBlock.y, targetBlock.z);
/*  83 */     Item itemType = blockType.getItem();
/*     */ 
/*     */     
/*  86 */     if (itemType == null) {
/*  87 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     boolean ok = false;
/*     */     
/*  93 */     for (BlockMatcher matcher : this.matchers) {
/*  94 */       if (matcher.face != BlockFace.None) {
/*  95 */         BlockFace transformedFace = matcher.face;
/*  96 */         if (!matcher.staticFace) {
/*  97 */           Rotation yaw = blockRotation.yaw();
/*  98 */           Rotation pitch = blockRotation.pitch();
/*  99 */           BlockFace newFace = BlockFace.rotate(BlockFace.fromProtocolFace(transformedFace), yaw, pitch);
/* 100 */           transformedFace = BlockFace.toProtocolFace(newFace);
/*     */         } 
/* 102 */         if (!transformedFace.equals(face))
/*     */           continue; 
/*     */       } 
/* 105 */       if (matcher.block != null) {
/* 106 */         if (matcher.block.id != null && !matcher.block.id.equals(itemType.getId()))
/* 107 */           continue;  if (matcher.block.state != null) {
/* 108 */           String state = blockType.getStateForBlock(blockType);
/* 109 */           if (state == null) state = "default"; 
/* 110 */           if (!matcher.block.state.equals(state))
/*     */             continue; 
/* 112 */         }  if (matcher.block.tag != null) {
/* 113 */           AssetExtraInfo.Data data = blockType.getData();
/* 114 */           if (data == null)
/*     */             continue; 
/* 116 */           Int2ObjectMap<IntSet> tags = data.getTags();
/* 117 */           if (tags == null || !tags.containsKey(matcher.block.tagIndex))
/*     */             continue; 
/*     */         } 
/*     */       } 
/* 121 */       ok = true;
/*     */     } 
/*     */ 
/*     */     
/* 125 */     if (ok) {
/* 126 */       (context.getState()).state = InteractionState.Finished;
/*     */     } else {
/* 128 */       (context.getState()).state = InteractionState.Failed;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 135 */     return (Interaction)new com.hypixel.hytale.protocol.BlockConditionInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 140 */     super.configurePacket(packet);
/* 141 */     com.hypixel.hytale.protocol.BlockConditionInteraction p = (com.hypixel.hytale.protocol.BlockConditionInteraction)packet;
/* 142 */     if (this.matchers != null) {
/* 143 */       p.matchers = new com.hypixel.hytale.protocol.BlockMatcher[this.matchers.length];
/* 144 */       for (int i = 0; i < this.matchers.length; i++) {
/* 145 */         p.matchers[i] = this.matchers[i].toPacket();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 154 */     return "BlockConditionInteraction{matchers=" + Arrays.toString((Object[])this.matchers) + "} " + super
/* 155 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BlockIdMatcher
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.BlockIdMatcher>
/*     */   {
/*     */     @Nonnull
/*     */     public static BuilderCodec<BlockIdMatcher> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String tag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 197 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockIdMatcher.class, BlockIdMatcher::new).appendInherited(new KeyedCodec("Id", (Codec)Codec.STRING), (blockIdMatcher, s) -> blockIdMatcher.id = s, blockIdMatcher -> blockIdMatcher.id, (blockIdMatcher, parent) -> blockIdMatcher.id = parent.id).addValidatorLate(() -> BlockType.VALIDATOR_CACHE.getValidator().late()).documentation("Match against a specific block id.").add()).appendInherited(new KeyedCodec("State", (Codec)Codec.STRING), (blockIdMatcher, s) -> blockIdMatcher.state = s, blockIdMatcher -> blockIdMatcher.state, (blockIdMatcher, parent) -> blockIdMatcher.state = parent.state).documentation("Match against specific block state.").add()).appendInherited(new KeyedCodec("Tag", (Codec)Codec.STRING), (blockIdMatcher, s) -> blockIdMatcher.tag = s, blockIdMatcher -> blockIdMatcher.tag, (blockIdMatcher, parent) -> blockIdMatcher.tag = parent.tag).documentation("Match against specific block tag.").add()).afterDecode(blockIdMatcher -> { if (blockIdMatcher.tag != null) blockIdMatcher.tagIndex = AssetRegistry.getOrCreateTagIndex(blockIdMatcher.tag);  })).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     protected int tagIndex = Integer.MIN_VALUE;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.BlockIdMatcher toPacket() {
/* 222 */       com.hypixel.hytale.protocol.BlockIdMatcher packet = new com.hypixel.hytale.protocol.BlockIdMatcher();
/* 223 */       if (this.id != null) packet.id = this.id; 
/* 224 */       if (this.state != null) packet.state = this.state; 
/* 225 */       packet.tagIndex = this.tagIndex;
/* 226 */       return packet;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 232 */       return "BlockIdMatcher{id='" + this.id + "', state='" + this.state + "', tag='" + this.tag + "'}";
/*     */     }
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
/*     */   public static class BlockMatcher
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.BlockMatcher>
/*     */   {
/*     */     @Nonnull
/*     */     public static BuilderCodec<BlockMatcher> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected BlockConditionInteraction.BlockIdMatcher block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 274 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockMatcher.class, BlockMatcher::new).appendInherited(new KeyedCodec("Block", (Codec)BlockConditionInteraction.BlockIdMatcher.CODEC), (blockMatcher, blockIdMatcher) -> blockMatcher.block = blockIdMatcher, blockMatcher -> blockMatcher.block, (blockMatcher, parent) -> blockMatcher.block = parent.block).documentation("Match against block values").add()).appendInherited(new KeyedCodec("Face", (Codec)BlockFace.CODEC), (blockMatcher, face) -> blockMatcher.face = BlockFace.toProtocolFace(face), blockMatcher -> BlockFace.fromProtocolFace(blockMatcher.face), (blockMatcher, parent) -> blockMatcher.face = parent.face).documentation("Match against a specific block face.").add()).appendInherited(new KeyedCodec("StaticFace", (Codec)Codec.BOOLEAN), (blockMatcher, aBoolean) -> blockMatcher.staticFace = aBoolean.booleanValue(), blockMatcher -> Boolean.valueOf(blockMatcher.staticFace), (blockMatcher, parent) -> blockMatcher.staticFace = parent.staticFace).documentation("Whether the face matching is unaffected by the block rotation or not.").add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     protected BlockFace face = BlockFace.None;
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean staticFace;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.BlockMatcher toPacket() {
/* 294 */       com.hypixel.hytale.protocol.BlockMatcher packet = new com.hypixel.hytale.protocol.BlockMatcher();
/* 295 */       if (this.block != null) packet.block = this.block.toPacket(); 
/* 296 */       if (this.face != null) packet.face = this.face; 
/* 297 */       packet.staticFace = this.staticFace;
/* 298 */       return packet;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 304 */       return "BlockMatcher{block=" + String.valueOf(this.block) + ", face=" + String.valueOf(this.face) + ", staticFace=" + this.staticFace + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\BlockConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */