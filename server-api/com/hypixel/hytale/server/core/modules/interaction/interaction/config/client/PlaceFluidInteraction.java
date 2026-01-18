/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFace;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
/*     */ import com.hypixel.hytale.server.core.asset.type.fluid.FluidTicker;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class PlaceFluidInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PlaceFluidInteraction> CODEC;
/*     */   @Nullable
/*     */   protected String fluidKey;
/*     */   
/*     */   static {
/*  52 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlaceFluidInteraction.class, PlaceFluidInteraction::new, SimpleInteraction.CODEC).documentation("Places the current or given block.")).append(new KeyedCodec("FluidToPlace", (Codec)Codec.STRING), (placeBlockInteraction, blockTypeKey) -> placeBlockInteraction.fluidKey = blockTypeKey, placeBlockInteraction -> placeBlockInteraction.fluidKey).addValidatorLate(() -> Fluid.VALIDATOR_CACHE.getValidator().late()).add()).append(new KeyedCodec("RemoveItemInHand", (Codec)Codec.BOOLEAN), (placeBlockInteraction, aBoolean) -> placeBlockInteraction.removeItemInHand = aBoolean.booleanValue(), placeBlockInteraction -> Boolean.valueOf(placeBlockInteraction.removeItemInHand)).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean removeItemInHand = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getFluidKey() {
/*  72 */     return this.fluidKey;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  78 */     return WaitForDataFrom.Client;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull CooldownHandler cooldownHandler) {
/*  83 */     Store<ChunkStore> store = world.getChunkStore().getStore();
/*     */     
/*  85 */     int fluidIndex = Fluid.getFluidIdOrUnknown(this.fluidKey, "Unknown fluid: %s", new Object[] { this.fluidKey });
/*  86 */     Fluid fluid = (Fluid)Fluid.getAssetMap().getAsset(fluidIndex);
/*     */     
/*  88 */     Vector3i target = targetBlock;
/*  89 */     BlockType targetBlockType = world.getBlockType(target);
/*  90 */     if (FluidTicker.isSolid(targetBlockType)) {
/*  91 */       target = targetBlock.clone();
/*  92 */       BlockFace face = BlockFace.fromProtocolFace((context.getClientState()).blockFace);
/*  93 */       target.add(face.getDirection());
/*     */     } 
/*     */     
/*  96 */     Ref<ChunkStore> section = world.getChunkStore().getChunkSectionReference(
/*  97 */         ChunkUtil.chunkCoordinate(target.x), 
/*  98 */         ChunkUtil.chunkCoordinate(target.y), 
/*  99 */         ChunkUtil.chunkCoordinate(target.z));
/*     */     
/* 101 */     if (section == null)
/*     */       return; 
/* 103 */     FluidSection fluidSectionComponent = (FluidSection)store.getComponent(section, FluidSection.getComponentType());
/* 104 */     if (fluidSectionComponent == null)
/*     */       return; 
/* 106 */     fluidSectionComponent.setFluid(target.x, target.y, target.z, fluid, (byte)fluid.getMaxFluidLevel());
/*     */     
/* 108 */     Ref<ChunkStore> chunkColumn = world.getChunkStore().getChunkReference(ChunkUtil.indexChunkFromBlock(target.x, target.z));
/* 109 */     if (chunkColumn == null)
/*     */       return; 
/* 111 */     BlockChunk blockChunkComponent = (BlockChunk)store.getComponent(chunkColumn, BlockChunk.getComponentType());
/* 112 */     blockChunkComponent.setTicking(target.x, target.y, target.z, true);
/*     */     
/* 114 */     Ref<EntityStore> ref = context.getEntity();
/* 115 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 116 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/*     */ 
/*     */     
/* 119 */     if ((playerRefComponent == null || (playerComponent != null && playerComponent.getGameMode() == GameMode.Adventure)) && itemInHand.getQuantity() == 1 && this.removeItemInHand) {
/* 120 */       context.setHeldItem(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsRemoteSync() {
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 137 */     return "PlaceBlockInteraction{blockTypeKey=" + this.fluidKey + ", removeItemInHand=" + this.removeItemInHand + "} " + super
/*     */ 
/*     */       
/* 140 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\PlaceFluidInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */