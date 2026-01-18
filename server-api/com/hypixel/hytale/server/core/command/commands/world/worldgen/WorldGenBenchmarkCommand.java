/*     */ package com.hypixel.hytale.server.core.command.commands.world.worldgen;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector2i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IBenchmarkableWorldGen;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class WorldGenBenchmarkCommand
/*     */   extends CommandBase
/*     */ {
/*  32 */   private static final AtomicBoolean IS_RUNNING = new AtomicBoolean(false);
/*     */   
/*  34 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_SAVING = Message.translation("server.commands.worldgenbenchmark.saving");
/*  35 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_DONE = Message.translation("server.commands.worldgenbenchmark.done");
/*  36 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_SAVE_FAILED = Message.translation("server.commands.worldgenbenchmark.saveFailed");
/*  37 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_SAVE_DONE = Message.translation("server.commands.worldgenbenchmark.saveDone");
/*  38 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_PROGRESS = Message.translation("server.commands.worldgenbenchmark.progress");
/*  39 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_STARTED = Message.translation("server.commands.worldgenbenchmark.started");
/*  40 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_ABORT = Message.translation("server.commands.worldgenbenchmark.abort");
/*  41 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_BENCHMARK_NOT_SUPPORTED = Message.translation("server.commands.worldgenbenchmark.benchmarkNotSupported");
/*  42 */   public static final Message MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_ALREADY_IN_PROGRESS = Message.translation("server.commands.worldgenbenchmark.alreadyInProgress");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   private final OptionalArg<World> worldArg = withOptionalArg("world", "server.commands.worldthread.arg.desc", (ArgumentType)ArgTypes.WORLD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   private final OptionalArg<Integer> seedArg = withOptionalArg("seed", "server.commands.worldgenbenchmark.seed.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   private final RequiredArg<Vector2i> pos1Arg = withRequiredArg("pos1", "server.commands.worldgenbenchmark.pos1.desc", ArgTypes.VECTOR2I);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  66 */   private final RequiredArg<Vector2i> pos2Arg = withRequiredArg("pos2", "server.commands.worldgenbenchmark.pos2.desc", ArgTypes.VECTOR2I);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenBenchmarkCommand() {
/*  72 */     super("benchmark", "server.commands.worldgenbenchmark.desc");
/*     */   }
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/*     */     IBenchmarkableWorldGen benchmarkableWorldGen;
/*     */     int minX, maxX, minZ, maxZ;
/*  77 */     if (IS_RUNNING.get()) {
/*  78 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_ALREADY_IN_PROGRESS);
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     World world = (World)this.worldArg.getProcessed(context);
/*  83 */     String worldName = world.getName();
/*  84 */     int seed = this.seedArg.provided(context) ? ((Integer)this.seedArg.get(context)).intValue() : (int)world.getWorldConfig().getSeed();
/*     */     
/*  86 */     IWorldGen worldGen = world.getChunkStore().getGenerator();
/*  87 */     if (worldGen instanceof IBenchmarkableWorldGen) { benchmarkableWorldGen = (IBenchmarkableWorldGen)worldGen; }
/*  88 */     else { context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_BENCHMARK_NOT_SUPPORTED);
/*     */       
/*     */       return; }
/*     */     
/*  92 */     Vector2i corner1 = (Vector2i)this.pos1Arg.get(context);
/*  93 */     Vector2i corner2 = (Vector2i)this.pos2Arg.get(context);
/*     */ 
/*     */     
/*  96 */     if (corner1.x < corner2.x) {
/*  97 */       minX = ChunkUtil.chunkCoordinate(corner1.x);
/*  98 */       maxX = ChunkUtil.chunkCoordinate(corner2.x);
/*     */     } else {
/* 100 */       minX = ChunkUtil.chunkCoordinate(corner2.x);
/* 101 */       maxX = ChunkUtil.chunkCoordinate(corner1.x);
/*     */     } 
/* 103 */     if (corner1.y < corner2.y) {
/* 104 */       minZ = ChunkUtil.chunkCoordinate(corner1.y);
/* 105 */       maxZ = ChunkUtil.chunkCoordinate(corner2.y);
/*     */     } else {
/* 107 */       minZ = ChunkUtil.chunkCoordinate(corner2.y);
/* 108 */       maxZ = ChunkUtil.chunkCoordinate(corner1.y);
/*     */     } 
/*     */     
/* 111 */     LongArrayList generatingChunks = new LongArrayList();
/* 112 */     for (int x = minX; x <= maxX; x++) {
/* 113 */       for (int z = minZ; z <= maxZ; z++) {
/* 114 */         generatingChunks.add(ChunkUtil.indexChunk(x, z));
/*     */       }
/*     */     } 
/*     */     
/* 118 */     if (IS_RUNNING.getAndSet(true)) {
/* 119 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_ABORT);
/*     */       
/*     */       return;
/*     */     } 
/* 123 */     context.sendMessage(MESSAGE_COMMANDS_WORLD_GEN_BENCHMARK_STARTED
/* 124 */         .param("seed", seed)
/* 125 */         .param("worldName", worldName)
/* 126 */         .param("size", generatingChunks.size()));
/* 127 */     benchmarkableWorldGen.getBenchmark().start();
/*     */     
/* 129 */     int chunkCount = generatingChunks.size();
/* 130 */     long startTime = System.nanoTime();
/* 131 */     (new Thread(() -> {
/*     */           Set<CompletableFuture<GeneratedChunk>> currentChunks = new HashSet<>();
/*     */           
/*     */           long nextBroadcast = System.nanoTime();
/*     */           
/*     */           do {
/*     */             long thisTime = System.nanoTime();
/*     */             if (thisTime >= nextBroadcast) {
/*     */               world.execute(());
/*     */               nextBroadcast = thisTime + 5000000000L;
/*     */             } 
/*     */             currentChunks.removeIf(CompletableFuture::isDone);
/*     */             int i = currentChunks.size();
/*     */             while (i < 20 && !generatingChunks.isEmpty()) {
/*     */               long index = generatingChunks.removeLong(generatingChunks.size() - 1);
/*     */               CompletableFuture<GeneratedChunk> future = worldGen.generate(seed, index, ChunkUtil.xOfChunkIndex(index), ChunkUtil.zOfChunkIndex(index), ());
/*     */               currentChunks.add(future);
/*     */               i++;
/*     */             } 
/*     */           } while (!currentChunks.isEmpty());
/*     */           String duration = FormatUtil.nanosToString(System.nanoTime() - startTime);
/*     */           world.execute(());
/*     */           world.execute(());
/*     */           String fileName = "quant." + System.currentTimeMillis() + "." + maxX - minX + "x" + maxZ - minZ + "." + worldName + ".txt";
/*     */           File folder = new File("quantification");
/*     */           File file = new File("quantification" + File.separator + fileName);
/*     */           folder.mkdirs();
/*     */           try {
/*     */             FileWriter fw = new FileWriter(file);
/*     */             
/*     */             try { fw.write(benchmarkableWorldGen.getBenchmark().buildReport().join());
/*     */               world.execute(());
/*     */               fw.close(); }
/* 164 */             catch (Throwable t$) { try { fw.close(); } catch (Throwable x2)
/*     */               { t$.addSuppressed(x2); }
/*     */                throw t$; }
/*     */           
/* 168 */           } catch (Exception e) {
/*     */             ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to save worldgen benchmark report!");
/*     */             world.execute(());
/*     */           } 
/*     */           benchmarkableWorldGen.getBenchmark().stop();
/*     */           IS_RUNNING.set(false);
/* 174 */         }"WorldGenBenchmarkCommand")).start();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\worldgen\WorldGenBenchmarkCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */