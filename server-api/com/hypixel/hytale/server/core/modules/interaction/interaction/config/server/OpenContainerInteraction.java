/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ContainerBlockWindow;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.SimpleBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class OpenContainerInteraction extends SimpleBlockInteraction {
/*  32 */   public static final BuilderCodec<OpenContainerInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(OpenContainerInteraction.class, OpenContainerInteraction::new, SimpleBlockInteraction.CODEC)
/*  33 */     .documentation("Opens the container of the block currently being interacted with."))
/*  34 */     .build();
/*     */   
/*     */   protected void interactWithBlock(@Nonnull World world, @Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull Vector3i pos, @Nonnull CooldownHandler cooldownHandler) {
/*     */     ItemContainerState itemContainerState;
/*  38 */     Ref<EntityStore> ref = context.getEntity();
/*  39 */     Store<EntityStore> store = ref.getStore();
/*     */     
/*  41 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  42 */     if (playerComponent == null)
/*     */       return; 
/*  44 */     BlockState container = world.getState(pos.x, pos.y, pos.z, true);
/*  45 */     if (container instanceof ItemContainerState) { itemContainerState = (ItemContainerState)container; }
/*  46 */     else { playerComponent.sendMessage(Message.translation("server.interactions.invalidBlockState")
/*  47 */           .param("interaction", getClass().getSimpleName())
/*  48 */           .param("blockState", (container != null) ? container.getClass().getSimpleName() : "null"));
/*     */       
/*     */       return; }
/*     */     
/*  52 */     BlockType blockType = world.getBlockType(pos.x, pos.y, pos.z);
/*     */     
/*  54 */     if (!itemContainerState.isAllowViewing() || !itemContainerState.canOpen(ref, (ComponentAccessor)commandBuffer)) {
/*     */       return;
/*     */     }
/*     */     
/*  58 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/*  59 */     assert uuidComponent != null;
/*     */     
/*  61 */     UUID uuid = uuidComponent.getUuid();
/*  62 */     WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(pos.x, pos.z));
/*  63 */     ContainerBlockWindow window = new ContainerBlockWindow(pos.x, pos.y, pos.z, chunk.getRotationIndex(pos.x, pos.y, pos.z), blockType, itemContainerState.getItemContainer());
/*  64 */     Map<UUID, ContainerBlockWindow> windows = itemContainerState.getWindows();
/*     */ 
/*     */ 
/*     */     
/*  68 */     if (windows.putIfAbsent(uuid, window) == null) {
/*  69 */       if (playerComponent.getPageManager().setPageWithWindows(ref, store, Page.Bench, true, new Window[] { (Window)window })) {
/*  70 */         window.registerCloseEvent(event -> {
/*     */               windows.remove(uuid, window); BlockType currentBlockType = world.getBlockType(pos);
/*     */               if (windows.isEmpty())
/*     */                 world.setBlockInteractionState(pos, currentBlockType, "CloseWindow"); 
/*     */               BlockType interactionState = currentBlockType.getBlockForState("CloseWindow");
/*     */               if (interactionState == null)
/*     */                 return; 
/*     */               int soundEventIndex = interactionState.getInteractionSoundEventIndex();
/*     */               if (soundEventIndex == 0)
/*     */                 return; 
/*     */               int rotationIndex = chunk.getRotationIndex(pos.x, pos.y, pos.z);
/*     */               Vector3d soundPos = new Vector3d();
/*     */               blockType.getBlockCenter(rotationIndex, soundPos);
/*     */               soundPos.add(pos);
/*     */               SoundUtil.playSoundEvent3d(ref, soundEventIndex, soundPos, (ComponentAccessor)commandBuffer);
/*     */             });
/*  86 */         if (windows.size() == 1) {
/*  87 */           world.setBlockInteractionState(pos, blockType, "OpenWindow");
/*     */         }
/*     */         
/*  90 */         BlockType interactionState = blockType.getBlockForState("OpenWindow");
/*  91 */         if (interactionState == null)
/*     */           return; 
/*  93 */         int soundEventIndex = interactionState.getInteractionSoundEventIndex();
/*  94 */         if (soundEventIndex == 0)
/*     */           return; 
/*  96 */         int rotationIndex = chunk.getRotationIndex(pos.x, pos.y, pos.z);
/*  97 */         Vector3d soundPos = new Vector3d();
/*  98 */         blockType.getBlockCenter(rotationIndex, soundPos);
/*  99 */         soundPos.add(pos);
/* 100 */         SoundUtil.playSoundEvent3d(ref, soundEventIndex, soundPos, (ComponentAccessor)commandBuffer);
/*     */       } else {
/* 102 */         windows.remove(uuid, window);
/*     */       } 
/*     */     }
/*     */     
/* 106 */     itemContainerState.onOpen(ref, world, store);
/*     */   }
/*     */   
/*     */   protected void simulateInteractWithBlock(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nullable ItemStack itemInHand, @Nonnull World world, @Nonnull Vector3i targetBlock) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\OpenContainerInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */