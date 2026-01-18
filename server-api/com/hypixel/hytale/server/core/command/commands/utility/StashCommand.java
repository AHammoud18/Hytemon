/*     */ package com.hypixel.hytale.server.core.command.commands.utility;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class StashCommand extends AbstractPlayerCommand {
/*     */   @Nonnull
/*  29 */   private static final Message MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED = Message.translation("server.commands.errors.chunkNotLoaded");
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_COMMANDS_STASH_DROP_LIST_SET = Message.translation("server.commands.stash.droplistSet");
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_COMMANDS_STASH_NO_DROP_LIST = Message.translation("server.commands.stash.noDroplist");
/*     */   @Nonnull
/*  35 */   private static final Message MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE = Message.translation("server.general.blockTargetNotInRange");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DISTANCE_MAX = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   private final OptionalArg<String> setArg = withOptionalArg("set", "server.commands.stash.setDroplist.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StashCommand() {
/*  52 */     super("stash", "server.commands.stash.getDroplist.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  62 */     ItemContainerState itemContainerState = getItemContainerState(ref, world, context, (ComponentAccessor<EntityStore>)store);
/*  63 */     if (itemContainerState == null)
/*     */       return; 
/*  65 */     if (this.setArg.provided(context)) {
/*  66 */       String dropList = (String)this.setArg.get(context);
/*  67 */       itemContainerState.setDroplist(dropList);
/*  68 */       context.sendMessage(MESSAGE_COMMANDS_STASH_DROP_LIST_SET);
/*     */     } else {
/*  70 */       String droplist = itemContainerState.getDroplist();
/*  71 */       if (droplist != null) {
/*  72 */         context.sendMessage(Message.translation("server.commands.stash.currentDroplist")
/*  73 */             .param("droplist", droplist));
/*     */       } else {
/*  75 */         context.sendMessage(MESSAGE_COMMANDS_STASH_NO_DROP_LIST);
/*     */       } 
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
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ItemContainerState getItemContainerState(@Nonnull Ref<EntityStore> ref, @Nonnull World world, @Nonnull CommandContext context, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  94 */     Vector3i block = TargetUtil.getTargetBlock(ref, 10.0D, componentAccessor);
/*  95 */     if (block == null) {
/*  96 */       context.sendMessage(MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE);
/*  97 */       return null;
/*     */     } 
/*     */     
/* 100 */     ChunkStore chunkStore = world.getChunkStore();
/* 101 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(block.x, block.z);
/* 102 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*     */ 
/*     */     
/* 105 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 106 */       int chunkX = ChunkUtil.chunkCoordinate(block.x);
/* 107 */       int chunkZ = ChunkUtil.chunkCoordinate(block.z);
/*     */       
/* 109 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED
/* 110 */           .param("chunkX", chunkX)
/* 111 */           .param("chunkZ", chunkZ)
/* 112 */           .param("world", world.getName()));
/* 113 */       return null;
/*     */     } 
/*     */     
/* 116 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/*     */     
/* 118 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 119 */     assert blockChunkComponent != null;
/*     */     
/* 121 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStoreStore.getComponent(chunkRef, WorldChunk.getComponentType());
/* 122 */     assert worldChunkComponent != null;
/*     */     
/* 124 */     BlockSection section = blockChunkComponent.getSectionAtBlockY(block.y);
/* 125 */     int filler = section.getFiller(block.x, block.y, block.z);
/* 126 */     if (filler != 0) {
/* 127 */       block.x -= FillerBlockUtil.unpackX(filler);
/* 128 */       block.y -= FillerBlockUtil.unpackY(filler);
/* 129 */       block.z -= FillerBlockUtil.unpackZ(filler);
/*     */     } 
/*     */     
/* 132 */     BlockState state = worldChunkComponent.getState(block.x, block.y, block.z);
/* 133 */     if (!(state instanceof ItemContainerState)) {
/* 134 */       context.sendMessage(Message.translation("server.general.containerNotFound")
/* 135 */           .param("block", block.toString()));
/* 136 */       return null;
/*     */     } 
/*     */     
/* 139 */     return (ItemContainerState)state;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\StashCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */