/*     */ package com.hypixel.hytale.builtin.instances.command;
/*     */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentRegistry;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.migrations.ChunkColumnMigrationSystem;
/*     */ import com.hypixel.hytale.server.core.modules.migrations.ChunkSectionMigrationSystem;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.EntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.component.ChunkSavingSystems;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.nio.file.Path;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class InstanceMigrateCommand extends AbstractAsyncCommand {
/*     */   public InstanceMigrateCommand() {
/*  37 */     super("migrate", "");
/*     */   }
/*     */   private static final long CHUNK_UPDATE_INTERVAL = 100L;
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  42 */     InstancesPlugin instancePlugin = InstancesPlugin.get();
/*  43 */     List<String> instancesToMigrate = instancePlugin.getInstanceAssets();
/*     */     
/*  45 */     CompletableFuture[] futures = new CompletableFuture[instancesToMigrate.size()];
/*  46 */     AtomicLong chunkCount = new AtomicLong();
/*  47 */     AtomicLong chunksMigrated = new AtomicLong();
/*  48 */     for (int i = 0; i < instancesToMigrate.size(); i++) {
/*  49 */       String asset = instancesToMigrate.get(i);
/*  50 */       Path instancePath = InstancesPlugin.getInstanceAssetPath(asset);
/*  51 */       CompletableFuture<WorldConfig> configFuture = WorldConfig.load(instancePath.resolve("instance.bson"));
/*  52 */       futures[i] = CompletableFutureUtil._catch(configFuture.thenCompose(config -> migrateInstance(context, asset, config, chunkCount, chunksMigrated)));
/*  53 */       futures[i].join();
/*     */     } 
/*     */     
/*  56 */     return CompletableFuture.allOf((CompletableFuture<?>[])futures).whenComplete((result, throwable) -> {
/*     */           if (throwable != null) {
/*     */             context.sendMessage(Message.translation("server.commands.instances.migrate.failed").param("error", throwable.getMessage()));
/*     */             return;
/*     */           } 
/*     */           context.sendMessage(Message.translation("server.commands.instances.migrate.complete").param("worlds", futures.length).param("chunks", chunkCount.get()));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static CompletableFuture<Void> migrateInstance(@Nonnull CommandContext context, @Nonnull String asset, @Nonnull WorldConfig config, @Nonnull AtomicLong chunkCount, @Nonnull AtomicLong chunksMigrated) {
/*  70 */     Path instancePath = InstancesPlugin.getInstanceAssetPath(asset);
/*  71 */     Universe universe = Universe.get();
/*  72 */     config.setUuid(UUID.randomUUID());
/*  73 */     config.setSavingPlayers(false);
/*  74 */     config.setIsAllNPCFrozen(true);
/*  75 */     config.setSavingConfig(false);
/*  76 */     config.setTicking(false);
/*  77 */     config.setGameMode(GameMode.Creative);
/*  78 */     config.setDeleteOnRemove(false);
/*  79 */     config.setCompassUpdating(false);
/*  80 */     InstanceWorldConfig.ensureAndGet(config).setRemovalConditions(RemovalCondition.EMPTY);
/*     */     
/*  82 */     config.markChanged();
/*  83 */     String worldName = "instance-migrate-" + InstancesPlugin.safeName(asset);
/*  84 */     return universe.makeWorld(worldName, instancePath, config, true).thenCompose(world -> {
/*     */           IChunkLoader loader = world.getChunkStore().getLoader();
/*     */           IChunkSaver saver = world.getChunkStore().getSaver();
/*     */           return CompletableFuture.supplyAsync((), (Executor)world).thenCompose(()).thenComposeAsync(SneakyThrow.sneakyFunction(()));
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstanceMigrateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */