/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
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
/*     */ public class ChangeStateInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ChangeStateInteraction> CODEC;
/*     */   private static final int SET_SETTINGS = 260;
/*     */   protected Map<String, String> stateKeys;
/*     */   
/*     */   static {
/*  54 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChangeStateInteraction.class, ChangeStateInteraction::new, SimpleBlockInteraction.CODEC).documentation("Changes the state of the target block to another state based on the mapping provided.")).appendInherited(new KeyedCodec("Changes", (Codec)new MapCodec((Codec)Codec.STRING, java.util.HashMap::new)), (interaction, changeMap) -> interaction.stateKeys = changeMap, interaction -> interaction.stateKeys, (o, p) -> o.stateKeys = p.stateKeys).documentation("The map of state changes to execute. `\"default\"` can be used for the initial state of a block.").add()).appendInherited(new KeyedCodec("UpdateBlockState", (Codec)Codec.BOOLEAN), (o, i) -> o.updateBlockState = i.booleanValue(), o -> Boolean.valueOf(o.updateBlockState), (o, p) -> o.updateBlockState = p.updateBlockState).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean updateBlockState = false;
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  63 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z));
/*  64 */     if (chunk == null)
/*  65 */       return;  BlockType current = chunk.getBlockType(targetBlock);
/*  66 */     String currentState = current.getStateForBlock(current);
/*  67 */     if (currentState == null) currentState = "default";
/*     */     
/*  69 */     String newState = this.stateKeys.get(currentState);
/*  70 */     if (newState != null) {
/*  71 */       String newBlock = current.getBlockKeyForState(newState);
/*  72 */       if (newBlock != null) {
/*  73 */         int newBlockId = BlockType.getAssetMap().getIndex(newBlock);
/*  74 */         if (newBlockId == Integer.MIN_VALUE) {
/*  75 */           (context.getState()).state = InteractionState.Failed;
/*     */           
/*     */           return;
/*     */         } 
/*  79 */         BlockType newBlockType = (BlockType)BlockType.getAssetMap().getAsset(newBlockId);
/*  80 */         int rotation = chunk.getRotationIndex(targetBlock.x, targetBlock.y, targetBlock.z);
/*     */         
/*  82 */         int settings = 260;
/*  83 */         if (!this.updateBlockState) settings |= 0x2; 
/*  84 */         chunk.setBlock(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ(), newBlockId, newBlockType, rotation, 0, settings);
/*     */         
/*  86 */         BlockType interactionStateBlock = current.getBlockForState(newState);
/*  87 */         if (interactionStateBlock == null)
/*     */           return; 
/*  89 */         int soundEventIndex = interactionStateBlock.getInteractionSoundEventIndex();
/*  90 */         if (soundEventIndex == 0)
/*     */           return; 
/*  92 */         Ref<EntityStore> ref = context.getEntity();
/*  93 */         SoundUtil.playSoundEvent3d(ref, soundEventIndex, targetBlock.x + 0.5D, targetBlock.y + 0.5D, targetBlock.z + 0.5D, (ComponentAccessor)commandBuffer);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  98 */     (context.getState()).state = InteractionState.Failed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 110 */     return (Interaction)new com.hypixel.hytale.protocol.ChangeStateInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 115 */     super.configurePacket(packet);
/* 116 */     com.hypixel.hytale.protocol.ChangeStateInteraction p = (com.hypixel.hytale.protocol.ChangeStateInteraction)packet;
/* 117 */     p.stateChanges = this.stateKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 123 */     return "ChangeStateInteraction{stateKeys=" + String.valueOf(this.stateKeys) + "} " + super
/*     */       
/* 125 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\ChangeStateInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */