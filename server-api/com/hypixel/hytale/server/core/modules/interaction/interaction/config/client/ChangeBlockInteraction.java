/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.BlockRotation;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChangeBlockInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ChangeBlockInteraction> CODEC;
/*     */   private static final int SET_BLOCK_SETTINGS = 256;
/*     */   protected Map<String, String> blockTypeKeys;
/*     */   protected Int2IntMap changeMapIds;
/*     */   
/*     */   static {
/*  74 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChangeBlockInteraction.class, ChangeBlockInteraction::new, SimpleBlockInteraction.CODEC).documentation("Changes the target block to another block based on the block types provided.")).appendInherited(new KeyedCodec("Changes", (Codec)new MapCodec((Codec)Codec.STRING, java.util.HashMap::new)), (interaction, changeMap) -> interaction.blockTypeKeys = changeMap, interaction -> interaction.blockTypeKeys, (o, p) -> o.blockTypeKeys = p.blockTypeKeys).documentation("A map of the target block to the new block.\n\nWhen the interaction runs it will look for the block that was interacted with in this map and if found it will replace it with specified block").addValidator((Validator)BlockType.VALIDATOR_CACHE.getMapKeyValidator().late()).addValidator((Validator)BlockType.VALIDATOR_CACHE.getMapValueValidator().late()).add()).appendInherited(new KeyedCodec("WorldSoundEventId", (Codec)Codec.STRING), (interaction, s) -> interaction.soundEventId = s, interaction -> interaction.soundEventId, (interaction, parent) -> interaction.soundEventId = parent.soundEventId).documentation("Sound event to play at the block location on block change.").addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("RequireNotBroken", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.requireNotBroken = s.booleanValue(), interaction -> Boolean.valueOf(interaction.requireNotBroken), (interaction, parent) -> interaction.requireNotBroken = parent.requireNotBroken).documentation("If true, the interaction will fail if the held item is broken (durability = 0).").add()).afterDecode(ChangeBlockInteraction::processConfig)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  81 */   protected String soundEventId = null;
/*     */   
/*  83 */   protected transient int soundEventIndex = 0;
/*     */   
/*     */   protected boolean requireNotBroken = false;
/*     */   
/*     */   protected void processConfig() {
/*  88 */     if (this.soundEventId != null) {
/*  89 */       this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEventId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  96 */     if (this.requireNotBroken && itemInHand != null && itemInHand.isBroken()) {
/*  97 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     int x = targetBlock.getX();
/* 102 */     int y = targetBlock.getY();
/* 103 */     int z = targetBlock.getZ();
/* 104 */     WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/*     */     
/* 106 */     int current = chunk.getBlock(x, y, z);
/* 107 */     int to = getChangeMapIds().get(current);
/* 108 */     if (to != Integer.MIN_VALUE) {
/* 109 */       BlockType toBlockType = (BlockType)BlockType.getAssetMap().getAsset(to);
/* 110 */       int rotationBefore = chunk.getRotationIndex(x, y, z);
/*     */       
/* 112 */       chunk.setBlock(x, y, z, to, toBlockType, rotationBefore, 0, 256);
/*     */ 
/*     */       
/* 115 */       (context.getState()).blockPosition = new BlockPosition(x, y, z);
/* 116 */       (context.getState()).placedBlockId = to;
/* 117 */       RotationTuple resultRotation = RotationTuple.get(rotationBefore);
/* 118 */       (context.getState()).blockRotation = new BlockRotation(resultRotation.yaw().toPacket(), resultRotation.pitch().toPacket(), resultRotation.roll().toPacket());
/*     */       
/* 120 */       if (this.soundEventIndex != 0) {
/* 121 */         Ref<EntityStore> ref = context.getEntity();
/* 122 */         Vector3d pos = new Vector3d(x + 0.5D, y + 0.5D, z + 0.5D);
/* 123 */         SoundUtil.playSoundEvent3d(ref, this.soundEventIndex, pos, true, (ComponentAccessor)commandBuffer);
/*     */       } 
/*     */     } else {
/* 126 */       (context.getState()).state = InteractionState.Failed;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Int2IntMap getChangeMapIds() {
/* 132 */     if (this.changeMapIds == null) {
/* 133 */       Int2IntOpenHashMap ids = new Int2IntOpenHashMap(this.blockTypeKeys.size());
/* 134 */       ids.defaultReturnValue(-2147483648);
/* 135 */       this.blockTypeKeys.forEach((fromKey, toKey) -> {
/*     */             int fromId = BlockType.getAssetMap().getIndex(fromKey);
/*     */             int toId = BlockType.getAssetMap().getIndex(toKey);
/*     */             if (fromId == Integer.MIN_VALUE) {
/*     */               HytaleLogger.getLogger().at(Level.SEVERE).log("Invalid BlockType: Interaction: %s, BlockType: %s", this.id, fromKey);
/*     */               return;
/*     */             } 
/*     */             if (toId == Integer.MIN_VALUE) {
/*     */               HytaleLogger.getLogger().at(Level.SEVERE).log("Invalid BlockType: Interaction: %s, BlockType: %s", this.id, toKey);
/*     */               return;
/*     */             } 
/*     */             ids.put(fromId, toId);
/*     */           });
/* 148 */       this.changeMapIds = (Int2IntMap)ids;
/*     */     } 
/* 150 */     return this.changeMapIds;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {
/* 156 */     if (this.requireNotBroken && itemInHand != null && itemInHand.isBroken()) {
/* 157 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/* 161 */     int current = world.getBlock(targetBlock);
/* 162 */     int to = getChangeMapIds().get(current);
/* 163 */     if (to == Integer.MIN_VALUE) {
/* 164 */       (context.getState()).state = InteractionState.Failed;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 171 */     return (Interaction)new com.hypixel.hytale.protocol.ChangeBlockInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 176 */     super.configurePacket(packet);
/* 177 */     com.hypixel.hytale.protocol.ChangeBlockInteraction p = (com.hypixel.hytale.protocol.ChangeBlockInteraction)packet;
/* 178 */     p.blockChanges = (Map)getChangeMapIds();
/* 179 */     p.worldSoundEventIndex = this.soundEventIndex;
/* 180 */     p.requireNotBroken = this.requireNotBroken;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 186 */     return "ChangeBlockInteraction{blockTypeKeys=" + String.valueOf(this.blockTypeKeys) + ", changeMapIds=" + String.valueOf(this.changeMapIds) + ", soundEventId='" + this.soundEventId + "', soundEventIndex=" + this.soundEventIndex + ", requireNotBroken=" + this.requireNotBroken + "} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\ChangeBlockInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */