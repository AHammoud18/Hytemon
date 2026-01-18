/*     */ package com.hypixel.hytale.builtin.crafting.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.crafting.state.ProcessingBenchState;
/*     */ import com.hypixel.hytale.builtin.crafting.window.ProcessingBenchWindow;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.bench.Bench;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenProcessingBenchInteraction
/*     */   extends SimpleBlockInteraction
/*     */ {
/*  42 */   public static final BuilderCodec<OpenProcessingBenchInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(OpenProcessingBenchInteraction.class, OpenProcessingBenchInteraction::new, SimpleBlockInteraction.CODEC)
/*  43 */     .documentation("Opens the processing bench page."))
/*  44 */     .build();
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i pos, @Nonnull CooldownHandler cooldownHandler) {
/*     */     ProcessingBenchState benchState;
/*  48 */     Ref<EntityStore> ref = context.getEntity();
/*  49 */     Store<EntityStore> store = ref.getStore();
/*     */     
/*  51 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  52 */     if (playerComponent == null)
/*     */       return; 
/*  54 */     BlockState state = world.getState(pos.x, pos.y, pos.z, true);
/*  55 */     if (state instanceof ProcessingBenchState) { benchState = (ProcessingBenchState)state; }
/*  56 */     else { playerComponent.sendMessage(Message.translation("server.interactions.invalidBlockState")
/*  57 */           .param("interaction", getClass().getSimpleName())
/*  58 */           .param("blockState", (state != null) ? state.getClass().getSimpleName() : "null"));
/*     */       
/*     */       return; }
/*     */     
/*  62 */     BlockType blockType = world.getBlockType(pos.x, pos.y, pos.z);
/*  63 */     Bench blockTypeBench = blockType.getBench();
/*     */ 
/*     */     
/*  66 */     if ((blockTypeBench == null || !blockTypeBench.equals(benchState.getBench())) && 
/*  67 */       !benchState.initialize(blockType)) {
/*  68 */       ProcessingBenchState.LOGGER.at(Level.WARNING).log("Failed to re-initialize: %s, %s", blockType.getId(), pos);
/*  69 */       int x = pos.getX();
/*  70 */       int z = pos.getZ();
/*  71 */       world.getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setState(x, pos.getY(), z, (BlockState)null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  76 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/*  77 */     assert uuidComponent != null;
/*  78 */     UUID uuid = uuidComponent.getUuid();
/*  79 */     ProcessingBenchWindow window = new ProcessingBenchWindow(benchState);
/*     */     
/*  81 */     Map<UUID, ProcessingBenchWindow> windows = benchState.getWindows();
/*  82 */     if (windows.putIfAbsent(uuid, window) == null) {
/*  83 */       benchState.updateFuelValues();
/*     */       
/*  85 */       if (playerComponent.getPageManager().setPageWithWindows(ref, store, Page.Bench, true, new Window[] { (Window)window })) {
/*  86 */         window.registerCloseEvent(event -> {
/*     */               windows.remove(uuid, window);
/*     */               
/*     */               BlockType currentBlockType = world.getBlockType(pos);
/*     */               
/*     */               String interactionState = BlockAccessor.getCurrentInteractionState(currentBlockType);
/*     */               if (windows.isEmpty() && !"Processing".equals(interactionState) && !"ProcessCompleted".equals(interactionState)) {
/*     */                 world.setBlockInteractionState(pos, currentBlockType, "default");
/*     */               }
/*     */               int soundEventIndex = blockType.getBench().getLocalCloseSoundEventIndex();
/*     */               if (soundEventIndex == 0) {
/*     */                 return;
/*     */               }
/*     */               SoundUtil.playSoundEvent2d(ref, soundEventIndex, SoundCategory.UI, (ComponentAccessor)commandBuffer);
/*     */             });
/* 101 */         int soundEventIndex = blockType.getBench().getLocalOpenSoundEventIndex();
/* 102 */         if (soundEventIndex == 0)
/*     */           return; 
/* 104 */         SoundUtil.playSoundEvent2d(ref, soundEventIndex, SoundCategory.UI, (ComponentAccessor)commandBuffer);
/*     */       } else {
/* 106 */         windows.remove(uuid, window);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\interaction\OpenProcessingBenchInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */