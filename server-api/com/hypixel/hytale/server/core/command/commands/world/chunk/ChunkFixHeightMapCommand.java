/*     */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeChunkPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.lighting.ChunkLightingManager;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ChunkFixHeightMapCommand
/*     */   extends AbstractWorldCommand {
/*     */   @Nonnull
/*  28 */   private static final Message MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED = Message.translation("server.commands.errors.chunkNotLoaded");
/*     */   @Nonnull
/*  30 */   private static final Message MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_STARTED = Message.translation("server.commands.chunk.fixHeightMap.started");
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_DONE = Message.translation("server.commands.chunk.fixHeightMap.done");
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_INVALIDATING_LIGHTING = Message.translation("server.commands.chunk.fixHeightMap.invalidatingLighting");
/*     */   @Nonnull
/*  36 */   private static final Message MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_WAITING_FOR_LIGHTING = Message.translation("server.commands.chunk.fixHeightMap.waitingForLighting");
/*     */   @Nonnull
/*  38 */   private static final Message MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_LIGHTING_FINISHED = Message.translation("server.commands.chunk.fixHeightMap.lightingFinished");
/*     */   @Nonnull
/*  40 */   private static final Message MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_LIGHTING_ERROR = Message.translation("server.commands.chunk.fixHeightMap.lightingError");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   private final RequiredArg<RelativeChunkPosition> chunkPosArg = withRequiredArg("x z", "server.commands.chunk.fixheight.position.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkFixHeightMapCommand() {
/*  52 */     super("fixheight", "server.commands.chunk.fixheight.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  57 */     RelativeChunkPosition chunkPosition = (RelativeChunkPosition)this.chunkPosArg.get(context);
/*  58 */     Vector2i position = chunkPosition.getChunkPosition(context, (ComponentAccessor)store);
/*  59 */     fixHeightMap(context, world, position.x, position.y);
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
/*     */   private static void fixHeightMap(@Nonnull CommandContext context, @Nonnull World world, int chunkX, int chunkZ) {
/*  71 */     ChunkLightingManager chunkLighting = world.getChunkLighting();
/*  72 */     ChunkStore chunkStore = world.getChunkStore();
/*  73 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/*  74 */     long chunkIndex = ChunkUtil.indexChunk(chunkX, chunkZ);
/*  75 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*     */ 
/*     */     
/*  78 */     if (chunkRef == null || !chunkRef.isValid()) {
/*  79 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED
/*  80 */           .param("chunkX", chunkX)
/*  81 */           .param("chunkZ", chunkZ)
/*  82 */           .param("world", world.getName()));
/*     */       
/*     */       return;
/*     */     } 
/*  86 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStoreStore.getComponent(chunkRef, WorldChunk.getComponentType());
/*  87 */     assert worldChunkComponent != null;
/*     */     
/*  89 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkRef, BlockChunk.getComponentType());
/*  90 */     assert blockChunkComponent != null;
/*     */     
/*  92 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_STARTED);
/*  93 */     blockChunkComponent.updateHeightmap();
/*  94 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_DONE);
/*     */     
/*  96 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_INVALIDATING_LIGHTING);
/*     */ 
/*     */     
/*  99 */     for (int chunkSectionY = 0; chunkSectionY < 10; chunkSectionY++) {
/* 100 */       blockChunkComponent.getSectionAtIndex(chunkSectionY).invalidateLocalLight();
/*     */     }
/*     */ 
/*     */     
/* 104 */     chunkLighting.invalidateLightInChunk(worldChunkComponent);
/* 105 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_DONE);
/*     */     
/* 107 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_WAITING_FOR_LIGHTING
/* 108 */         .param("x", chunkX)
/* 109 */         .param("z", chunkZ));
/* 110 */     int[] count = { 0 };
/* 111 */     ScheduledFuture[] arrayOfScheduledFuture = new ScheduledFuture[1];
/* 112 */     arrayOfScheduledFuture[0] = HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> { if (chunkLighting.isQueued(chunkX, chunkZ)) { count[0] = count[0] + 1; if (count[0] > 60) { scheduledFuture[0].cancel(false); context.sendMessage(MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_LIGHTING_ERROR.param("x", chunkX).param("z", chunkZ)); }  return; }  world.getNotificationHandler().updateChunk(chunkIndex); context.sendMessage(MESSAGE_COMMANDS_CHUNK_FIXHEIGHTMAP_LIGHTING_FINISHED.param("x", chunkX).param("z", chunkZ)); scheduledFuture[0].cancel(false); }1L, 1L, TimeUnit.SECONDS);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkFixHeightMapCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */