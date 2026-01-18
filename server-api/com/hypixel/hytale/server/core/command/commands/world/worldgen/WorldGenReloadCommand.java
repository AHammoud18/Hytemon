/*     */ package com.hypixel.hytale.server.core.command.commands.world.worldgen;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.component.ChunkSavingSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.IWorldMap;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class WorldGenReloadCommand extends AbstractAsyncWorldCommand {
/*  29 */   private static final AtomicBoolean IS_RUNNING = new AtomicBoolean(false);
/*     */   
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_COMMANDS_WORLD_GEN_RELOAD_STARTED = Message.translation("server.commands.worldgen.reload.started");
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_COMMANDS_WORLD_GEN_RELOAD_COMPLETE = Message.translation("server.commands.worldgen.reload.complete");
/*     */   @Nonnull
/*  36 */   private static final Message MESSAGE_COMMANDS_WORLD_GEN_RELOAD_CHUNK_SAVING_DISABLED = Message.translation("server.commands.worldgen.reload.chunkSavingDisabled");
/*     */   @Nonnull
/*  38 */   private static final Message MESSAGE_COMMANDS_WORLD_GEN_RELOAD_DELETING_CHUNKS = Message.translation("server.commands.worldgen.reload.deletingChunks");
/*     */   @Nonnull
/*  40 */   private static final Message MKESSAGE_COMMANDS_WORLD_GEN_RELOAD_CHUNK_SAVING_ENABLED = Message.translation("server.commands.worldgen.reload.chunkSavingEnabled");
/*     */   @Nonnull
/*  42 */   private static final Message MESSAGE_COMMANDS_WORLD_GEN_RELOAD_REGENERATING_LOADED_CHUNKS = Message.translation("server.commands.worldgen.reload.regeneratingLoadedChunks");
/*     */   @Nonnull
/*  44 */   private static final Message MESSAGE_COMMANDS_WORLD_GEN_RELOAD_CHUNK_SAVING_ENABLED = Message.translation("server.commands.worldgen.reload.chunkSavingEnabled");
/*     */   @Nonnull
/*  46 */   private static final Message MESSAGE_COMMANDS_WORLD_GEN_RELOAD_ALREADY_IN_PROGRESS = Message.translation("server.commands.worldgen.reload.alreadyInProgress");
/*     */   @Nonnull
/*  48 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_ABORT = Message.translation("server.commands.worldgen.reload.abort");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   private final FlagArg clearArg = withFlagArg("clear", "server.commands.worldgen.reload.clear.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenReloadCommand() {
/*  60 */     super("reload", "server.commands.worldgen.reload.desc");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context, @Nonnull World world) {
/*  65 */     if (IS_RUNNING.get()) {
/*  66 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_RELOAD_ALREADY_IN_PROGRESS);
/*  67 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/*  70 */     context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_RELOAD_STARTED);
/*     */     
/*  72 */     WorldConfig worldConfig = world.getWorldConfig();
/*  73 */     ChunkStore chunkComponentStore = world.getChunkStore();
/*     */     
/*  75 */     if (IS_RUNNING.getAndSet(true)) {
/*  76 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_ABORT);
/*  77 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  82 */       IWorldGen worldGen = worldConfig.getWorldGenProvider().getGenerator();
/*  83 */       chunkComponentStore.setGenerator(worldGen);
/*     */       
/*  85 */       worldConfig.setDefaultSpawnProvider(worldGen);
/*  86 */       worldConfig.markChanged();
/*     */       
/*  88 */       IWorldMap worldMap = worldConfig.getWorldMapProvider().getGenerator(world);
/*  89 */       world.getWorldMapManager().setGenerator(worldMap);
/*     */       
/*  91 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_RELOAD_COMPLETE);
/*  92 */     } catch (WorldGenLoadException e) {
/*  93 */       context.sendMessage(Message.translation("server.commands.worldgen.reload.failed")
/*  94 */           .param("error", e.getTraceMessage("\n")));
/*  95 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause((Throwable)new SkipSentryException((Throwable)e))).log("Failed to load WorldGen!");
/*  96 */       return (CompletableFuture)CompletableFuture.completedFuture(null);
/*  97 */     } catch (Exception e) {
/*  98 */       context.sendMessage(Message.translation("server.commands.worldgen.reload.failed")
/*  99 */           .param("error", e.getMessage()));
/* 100 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Exception when trying to load WorldGen!");
/* 101 */       return (CompletableFuture)CompletableFuture.completedFuture(null);
/*     */     } finally {
/* 103 */       IS_RUNNING.set(false);
/*     */     } 
/*     */     
/* 106 */     if (this.clearArg.provided(context)) {
/* 107 */       return clearChunks(context, world);
/*     */     }
/*     */     
/* 110 */     return CompletableFuture.completedFuture(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static CompletableFuture<Void> clearChunks(@Nonnull CommandContext context, @Nonnull World world) {
/* 122 */     ChunkStore chunkComponentStore = world.getChunkStore();
/* 123 */     Store<ChunkStore> componentStore = chunkComponentStore.getStore();
/* 124 */     ChunkSavingSystems.Data data = (ChunkSavingSystems.Data)componentStore.getResource(ChunkStore.SAVE_RESOURCE);
/* 125 */     data.isSaving = false;
/* 126 */     data.clearSaveQueue();
/* 127 */     context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_RELOAD_CHUNK_SAVING_DISABLED);
/*     */     
/* 129 */     context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_RELOAD_DELETING_CHUNKS);
/*     */     
/* 131 */     IChunkSaver saver = chunkComponentStore.getSaver();
/* 132 */     if (saver == null) {
/* 133 */       context.sendMessage(MKESSAGE_COMMANDS_WORLD_GEN_RELOAD_CHUNK_SAVING_ENABLED);
/* 134 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 137 */     return CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> {
/*     */             try {
/*     */               return saver.getIndexes();
/* 140 */             } catch (IOException e) {
/*     */               ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to get chunk indexes for clearing!");
/*     */               
/*     */               context.sendMessage(Message.translation("server.commands.worldgen.reload.failed").param("error", e.getMessage()));
/*     */               throw SneakyThrow.sneakyThrow(e);
/*     */             } 
/* 146 */           }), (Executor)world).thenComposeAsync(indexes -> {
/*     */           AtomicInteger counter = new AtomicInteger();
/*     */ 
/*     */           
/*     */           double total = indexes.size();
/*     */ 
/*     */           
/*     */           ObjectArrayList<CompletableFuture<Void>> futures = new ObjectArrayList();
/*     */ 
/*     */           
/*     */           LongIterator iterator = indexes.iterator();
/*     */           
/*     */           while (iterator.hasNext()) {
/*     */             long index = iterator.nextLong();
/*     */             
/*     */             int x = ChunkUtil.xOfChunkIndex(index);
/*     */             
/*     */             int z = ChunkUtil.zOfChunkIndex(index);
/*     */             
/*     */             futures.add(saver.removeHolder(x, z).thenRun(()));
/*     */           } 
/*     */           
/*     */           return CompletableFuture.allOf((CompletableFuture<?>[])futures.toArray(()));
/* 169 */         }(Executor)world).thenComposeAsync(aVoid -> {
/*     */           context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_RELOAD_REGENERATING_LOADED_CHUNKS);
/*     */           
/*     */           LongSet chunkIndexes = chunkComponentStore.getChunkIndexes();
/*     */           
/*     */           ObjectArrayList<CompletableFuture<?>> regenerateFutures = new ObjectArrayList();
/*     */           LongIterator chunkIterator = chunkIndexes.iterator();
/*     */           while (chunkIterator.hasNext()) {
/*     */             long index = chunkIterator.nextLong();
/*     */             regenerateFutures.add(chunkComponentStore.getChunkReferenceAsync(index, 9));
/*     */           } 
/*     */           return CompletableFuture.allOf((CompletableFuture<?>[])regenerateFutures.toArray(()));
/* 181 */         }(Executor)world).thenRunAsync(() -> {
/*     */           Store<ChunkStore> chunkStore = chunkComponentStore.getStore();
/*     */           ChunkSavingSystems.Data saveData = (ChunkSavingSystems.Data)chunkStore.getResource(ChunkStore.SAVE_RESOURCE);
/*     */           saveData.isSaving = true;
/*     */           context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_RELOAD_CHUNK_SAVING_ENABLED);
/*     */         }(Executor)world);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\worldgen\WorldGenReloadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */