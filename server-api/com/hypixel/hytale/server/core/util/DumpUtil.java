/*     */ package com.hypixel.hytale.server.core.util;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*     */ import com.hypixel.hytale.plugin.early.ClassTransformer;
/*     */ import com.hypixel.hytale.protocol.io.PacketStatsRecorder;
/*     */ import com.hypixel.hytale.protocol.packets.connection.PongType;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*     */ import com.hypixel.hytale.server.core.ShutdownReason;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
/*     */ import com.hypixel.hytale.storage.IndexedStorageFile;
/*     */ import com.sun.management.OperatingSystemMXBean;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.management.ClassLoadingMXBean;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryMXBean;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.management.MemoryUsage;
/*     */ import java.lang.management.OperatingSystemMXBean;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public class DumpUtil {
/*     */   public static final class PlayerTextData extends Record {
/*     */     @Nonnull
/*     */     private final UUID uuid;
/*     */     @Nullable
/*     */     private final String movementStates;
/*     */     @Nullable
/*     */     private final String movementManager;
/*     */     @Nullable
/*     */     private final String cameraManager;
/*     */     
/*  60 */     public PlayerTextData(@Nonnull UUID uuid, @Nullable String movementStates, @Nullable String movementManager, @Nullable String cameraManager) { this.uuid = uuid; this.movementStates = movementStates; this.movementManager = movementManager; this.cameraManager = cameraManager; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #60	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #60	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #60	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/util/DumpUtil$PlayerTextData;
/*  60 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public UUID uuid() { return this.uuid; } @Nullable public String movementStates() { return this.movementStates; } @Nullable public String movementManager() { return this.movementManager; } @Nullable public String cameraManager() { return this.cameraManager; }
/*     */   
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
/*     */   @Nonnull
/*     */   public static Path dumpToJson() throws IOException {
/*  77 */     Map<UUID, BsonDocument> playerComponents = collectPlayerComponentMetrics();
/*     */ 
/*     */     
/*  80 */     BsonDocument bson = HytaleServer.METRICS_REGISTRY.dumpToBson(HytaleServer.get()).asDocument();
/*     */ 
/*     */     
/*  83 */     BsonDocument universeBson = Universe.METRICS_REGISTRY.dumpToBson(Universe.get()).asDocument();
/*     */ 
/*     */     
/*  86 */     BsonArray playersArray = new BsonArray();
/*  87 */     for (PlayerRef ref : Universe.get().getPlayers()) {
/*  88 */       BsonDocument playerBson = PlayerRef.METRICS_REGISTRY.dumpToBson(ref).asDocument();
/*     */       
/*  90 */       BsonDocument componentData = playerComponents.get(ref.getUuid());
/*  91 */       if (componentData != null) {
/*  92 */         playerBson.putAll((Map)componentData);
/*     */       }
/*  94 */       playersArray.add((BsonValue)playerBson);
/*     */     } 
/*  96 */     universeBson.put("Players", (BsonValue)playersArray);
/*     */     
/*  98 */     bson.put("Universe", (BsonValue)universeBson);
/*     */ 
/*     */     
/* 101 */     BsonArray earlyPluginsArray = new BsonArray();
/* 102 */     for (ClassTransformer transformer : EarlyPluginLoader.getTransformers()) {
/* 103 */       earlyPluginsArray.add((BsonValue)new BsonString(transformer.getClass().getName()));
/*     */     }
/* 105 */     bson.put("EarlyPlugins", (BsonValue)earlyPluginsArray);
/*     */ 
/*     */     
/* 108 */     Path path = MetricsRegistry.createDumpPath(".dump.json");
/* 109 */     Files.writeString(path, BsonUtil.toJson(bson), new java.nio.file.OpenOption[0]);
/* 110 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Map<UUID, BsonDocument> collectPlayerComponentMetrics() {
/* 121 */     ConcurrentHashMap<UUID, BsonDocument> result = new ConcurrentHashMap<>();
/* 122 */     Collection<World> worlds = Universe.get().getWorlds().values();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     CompletableFuture[] futures = (CompletableFuture[])worlds.stream().map(world -> CompletableFuture.runAsync((), (Executor)world).orTimeout(30L, TimeUnit.SECONDS)).toArray(x$0 -> new CompletableFuture[x$0]);
/*     */     
/* 133 */     CompletableFuture.allOf((CompletableFuture<?>[])futures).join();
/* 134 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Map<UUID, PlayerTextData> collectPlayerTextData() {
/* 145 */     ConcurrentHashMap<UUID, PlayerTextData> result = new ConcurrentHashMap<>();
/* 146 */     Collection<World> worlds = Universe.get().getWorlds().values();
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
/* 167 */     CompletableFuture[] futures = (CompletableFuture[])worlds.stream().map(world -> CompletableFuture.runAsync((), (Executor)world).orTimeout(30L, TimeUnit.SECONDS)).toArray(x$0 -> new CompletableFuture[x$0]);
/*     */     
/* 169 */     CompletableFuture.allOf((CompletableFuture<?>[])futures).join();
/* 170 */     return result;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String hexDump(@Nonnull ByteBuf buf) {
/* 175 */     int readerIndex = buf.readerIndex();
/* 176 */     byte[] data = new byte[buf.readableBytes()];
/* 177 */     buf.readBytes(data);
/* 178 */     buf.readerIndex(readerIndex);
/* 179 */     return hexDump(data);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String hexDump(@Nonnull byte[] data) {
/* 184 */     if (data.length == 0) return "[EMPTY ARRAY]"; 
/* 185 */     return ByteBufUtil.hexDump(data);
/*     */   }
/*     */   @Nonnull
/*     */   public static Path dump(boolean crash, boolean printToConsole) {
/*     */     OutputStream outputStream;
/* 190 */     Path filePath = createDumpPath(crash, "dump.txt");
/*     */     
/* 192 */     FileOutputStream fileOutputStream = null;
/*     */     
/*     */     try {
/* 195 */       fileOutputStream = new FileOutputStream(filePath.toFile());
/* 196 */       if (printToConsole) {
/* 197 */         TeeOutputStream teeOutputStream = new TeeOutputStream(fileOutputStream, System.err);
/*     */       } else {
/* 199 */         outputStream = fileOutputStream;
/*     */       } 
/* 201 */     } catch (IOException e) {
/* 202 */       e.printStackTrace();
/*     */       
/* 204 */       System.err.println();
/* 205 */       System.err.println("FAILED TO GET OUTPUT STREAM FOR " + String.valueOf(filePath));
/* 206 */       System.err.println("FAILED TO GET OUTPUT STREAM FOR " + String.valueOf(filePath));
/* 207 */       System.err.println();
/*     */       
/* 209 */       outputStream = System.err;
/*     */     } 
/*     */     
/*     */     try {
/* 213 */       write(new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)), true));
/*     */     } finally {
/* 215 */       if (fileOutputStream != null) {
/*     */         try {
/* 217 */           fileOutputStream.close();
/* 218 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 223 */     return filePath;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Path createDumpPath(boolean crash, String ext) {
/* 228 */     Path path = Paths.get("dumps", new String[0]);
/*     */     try {
/* 230 */       if (!Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 231 */         Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/* 233 */     } catch (IOException e) {
/* 234 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 237 */     String name = (crash ? "crash-" : "") + (crash ? "crash-" : "");
/* 238 */     Path filePath = path.resolve(name + "." + name);
/*     */     
/* 240 */     int i = 0;
/* 241 */     while (Files.exists(filePath, new java.nio.file.LinkOption[0])) {
/* 242 */       filePath = path.resolve(name + "_" + name + "." + i++);
/*     */     }
/* 244 */     return filePath;
/*     */   }
/*     */   
/*     */   private static void write(@Nonnull PrintWriter writer) {
/* 248 */     int width = 200;
/* 249 */     int height = 20;
/*     */     
/* 251 */     long startNanos = System.nanoTime();
/*     */     
/* 253 */     section("Summary", () -> { Universe universe = Universe.get(); writer.println("World Count: " + universe.getWorlds().size()); for (World world : universe.getWorlds().values()) { writer.println("- " + world.getName()); HistoricMetric metrics = world.getBufferedTickLengthMetricSet(); long[] periodsNanos = metrics.getPeriodsNanos(); int periodIndex = periodsNanos.length - 1; long lastTime = periodsNanos[periodIndex]; double average = metrics.getAverage(periodIndex); long max = metrics.calculateMax(periodIndex); long min = metrics.calculateMin(periodIndex); String length = FormatUtil.timeUnitToString(lastTime, TimeUnit.NANOSECONDS, true); String value = FormatUtil.simpleTimeUnitFormat(min, average, max, TimeUnit.NANOSECONDS, TimeUnit.MILLISECONDS, 3); String limit = FormatUtil.simpleTimeUnitFormat(world.getTickStepNanos(), TimeUnit.NANOSECONDS, 3); writer.printf("\tTick (%s): %s (Limit: %s)\n", new Object[] { length, value, limit }); writer.printf("\tPlayer count: %d\n", new Object[] { Integer.valueOf(world.getPlayerCount()) }); }  writer.println("Player count: " + universe.getPlayerCount()); for (PlayerRef ref : universe.getPlayers()) { writer.printf("- %s (%s)\n", new Object[] { ref.getUsername(), ref.getUuid() }); PacketHandler.PingInfo pingInfo = ref.getPacketHandler().getPingInfo(PongType.Raw); HistoricMetric pingMetricSet = pingInfo.getPingMetricSet(); long min = pingMetricSet.calculateMin(1); long average = (long)pingMetricSet.getAverage(1); long max = pingMetricSet.calculateMax(1); writer.println("\tPing(raw) Min: " + FormatUtil.timeUnitToString(min, PacketHandler.PingInfo.TIME_UNIT) + ", Avg: " + FormatUtil.timeUnitToString(average, PacketHandler.PingInfo.TIME_UNIT) + ", Max: " + FormatUtil.timeUnitToString(max, PacketHandler.PingInfo.TIME_UNIT)); }  }writer);
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
/* 294 */     section("Server Lifecycle", () -> { HytaleServer server = HytaleServer.get(); writer.println("Boot Timestamp: " + String.valueOf(server.getBoot())); writer.println("Boot Start (nanos): " + server.getBootStart()); writer.println("Booting: " + server.isBooting()); writer.println("Booted: " + server.isBooted()); writer.println("Shutting Down: " + server.isShuttingDown()); ShutdownReason shutdownReason = server.getShutdownReason(); if (shutdownReason != null) writer.println("Shutdown Reason: " + String.valueOf(shutdownReason));  }writer);
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
/* 307 */     section("Early Plugins", () -> { List<ClassTransformer> transformers = EarlyPluginLoader.getTransformers(); writer.println("Class Transformer Count: " + transformers.size()); for (ClassTransformer transformer : transformers) writer.println("- " + transformer.getClass().getName() + " (priority=" + transformer.priority() + ")");  }writer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     section("Plugins", () -> { // Byte code:
/*     */           //   0: invokestatic get : ()Lcom/hypixel/hytale/server/core/HytaleServer;
/*     */           //   3: invokevirtual getPluginManager : ()Lcom/hypixel/hytale/server/core/plugin/PluginManager;
/*     */           //   6: invokevirtual getPlugins : ()Ljava/util/List;
/*     */           //   9: astore_1
/*     */           //   10: aload_0
/*     */           //   11: aload_1
/*     */           //   12: invokeinterface size : ()I
/*     */           //   17: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
/*     */           //   22: invokevirtual println : (Ljava/lang/String;)V
/*     */           //   25: aload_1
/*     */           //   26: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */           //   31: astore_2
/*     */           //   32: aload_2
/*     */           //   33: invokeinterface hasNext : ()Z
/*     */           //   38: ifeq -> 183
/*     */           //   41: aload_2
/*     */           //   42: invokeinterface next : ()Ljava/lang/Object;
/*     */           //   47: checkcast com/hypixel/hytale/server/core/plugin/PluginBase
/*     */           //   50: astore_3
/*     */           //   51: aload_3
/*     */           //   52: instanceof com/hypixel/hytale/server/core/plugin/JavaPlugin
/*     */           //   55: ifeq -> 79
/*     */           //   58: aload_3
/*     */           //   59: checkcast com/hypixel/hytale/server/core/plugin/JavaPlugin
/*     */           //   62: astore #5
/*     */           //   64: aload #5
/*     */           //   66: invokevirtual getClassLoader : ()Lcom/hypixel/hytale/server/core/plugin/PluginClassLoader;
/*     */           //   69: invokevirtual isInServerClassPath : ()Z
/*     */           //   72: ifeq -> 79
/*     */           //   75: iconst_1
/*     */           //   76: goto -> 80
/*     */           //   79: iconst_0
/*     */           //   80: istore #4
/*     */           //   82: aload_0
/*     */           //   83: aload_3
/*     */           //   84: invokevirtual getIdentifier : ()Lcom/hypixel/hytale/common/plugin/PluginIdentifier;
/*     */           //   87: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*     */           //   90: iload #4
/*     */           //   92: ifeq -> 101
/*     */           //   95: ldc_w ' [Builtin]'
/*     */           //   98: goto -> 104
/*     */           //   101: ldc_w ' [External]'
/*     */           //   104: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */           //   109: invokevirtual println : (Ljava/lang/String;)V
/*     */           //   112: aload_0
/*     */           //   113: aload_3
/*     */           //   114: invokevirtual getType : ()Lcom/hypixel/hytale/server/core/plugin/PluginType;
/*     */           //   117: invokevirtual getDisplayName : ()Ljava/lang/String;
/*     */           //   120: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*     */           //   125: invokevirtual println : (Ljava/lang/String;)V
/*     */           //   128: aload_0
/*     */           //   129: aload_3
/*     */           //   130: invokevirtual getState : ()Lcom/hypixel/hytale/server/core/plugin/PluginState;
/*     */           //   133: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*     */           //   136: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
/*     */           //   141: invokevirtual println : (Ljava/lang/String;)V
/*     */           //   144: aload_0
/*     */           //   145: ldc_w '\\tManifest:'
/*     */           //   148: invokevirtual println : (Ljava/lang/String;)V
/*     */           //   151: getstatic com/hypixel/hytale/common/plugin/PluginManifest.CODEC : Lcom/hypixel/hytale/codec/Codec;
/*     */           //   154: aload_3
/*     */           //   155: invokevirtual getManifest : ()Lcom/hypixel/hytale/common/plugin/PluginManifest;
/*     */           //   158: invokeinterface encode : (Ljava/lang/Object;)Lorg/bson/BsonValue;
/*     */           //   163: invokevirtual asDocument : ()Lorg/bson/BsonDocument;
/*     */           //   166: astore #5
/*     */           //   168: aload_0
/*     */           //   169: aload #5
/*     */           //   171: invokestatic toJson : (Lorg/bson/BsonDocument;)Ljava/lang/String;
/*     */           //   174: ldc_w '\\t\\t'
/*     */           //   177: invokestatic printIndented : (Ljava/io/PrintWriter;Ljava/lang/String;Ljava/lang/String;)V
/*     */           //   180: goto -> 32
/*     */           //   183: return
/*     */           // Line number table:
/*     */           //   Java source line number -> byte code offset
/*     */           //   #316	-> 0
/*     */           //   #317	-> 10
/*     */           //   #318	-> 25
/*     */           //   #319	-> 51
/*     */           //   #320	-> 82
/*     */           //   #321	-> 112
/*     */           //   #322	-> 128
/*     */           //   #323	-> 144
/*     */           //   #324	-> 151
/*     */           //   #325	-> 168
/*     */           //   #326	-> 180
/*     */           //   #327	-> 183
/*     */           // Local variable table:
/*     */           //   start	length	slot	name	descriptor
/*     */           //   64	15	5	javaPlugin	Lcom/hypixel/hytale/server/core/plugin/JavaPlugin;
/*     */           //   82	98	4	isBuiltin	Z
/*     */           //   168	12	5	manifestBson	Lorg/bson/BsonDocument;
/*     */           //   51	129	3	plugin	Lcom/hypixel/hytale/server/core/plugin/PluginBase;
/*     */           //   32	151	2	i$	Ljava/util/Iterator;
/*     */           //   0	184	0	writer	Ljava/io/PrintWriter;
/*     */           //   10	174	1	plugins	Ljava/util/List;
/*     */           // Local variable type table:
/*     */           //   start	length	slot	name	signature
/* 315 */           //   10	174	1	plugins	Ljava/util/List<Lcom/hypixel/hytale/server/core/plugin/PluginBase;>; }writer);
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
/* 329 */     section("Server Config", () -> { HytaleServerConfig config = HytaleServer.get().getConfig(); BsonDocument bson = HytaleServerConfig.CODEC.encode(config).asDocument(); printIndented(writer, BsonUtil.toJson(bson), "\t"); }writer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     Map<UUID, PlayerTextData> playerTextData = collectPlayerTextData();
/*     */     
/* 338 */     section("Server Info", () -> { writer.println("HytaleServer:"); writer.println("\t- " + String.valueOf(HytaleServer.get())); writer.println("\tBooted: " + HytaleServer.get().isBooting()); writer.println("\tShutting Down: " + HytaleServer.get().isShuttingDown()); writer.println(); writer.println("Worlds: "); Map<String, World> worlds = Universe.get().getWorlds(); worlds.forEach(()); List<PlayerRef> playersNotInWorld = Universe.get().getPlayers().stream().filter(()).toList(); if (!playersNotInWorld.isEmpty()) { writer.println(); writer.println("Players not in world (" + playersNotInWorld.size() + "):"); for (PlayerRef ref : playersNotInWorld) { writer.println("- " + ref.getUsername() + " (" + String.valueOf(ref.getUuid()) + ")"); writer.println("\tQueued Packets: " + ref.getPacketHandler().getQueuedPacketsCount()); PacketHandler.PingInfo pingInfo = ref.getPacketHandler().getPingInfo(PongType.Raw); HistoricMetric pingMetricSet = pingInfo.getPingMetricSet(); long min = pingMetricSet.calculateMin(1); long avg = (long)pingMetricSet.getAverage(1); long max = pingMetricSet.calculateMax(1); writer.println("\tPing(raw): Min: " + FormatUtil.timeUnitToString(min, PacketHandler.PingInfo.TIME_UNIT) + ", Avg: " + FormatUtil.timeUnitToString(avg, PacketHandler.PingInfo.TIME_UNIT) + ", Max: " + FormatUtil.timeUnitToString(max, PacketHandler.PingInfo.TIME_UNIT)); }  }  }writer);
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
/* 641 */     section("System info", () -> {
/*     */           RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
/*     */           OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
/*     */           long currentTimeMillis = System.currentTimeMillis();
/*     */           writer.println("Start Time: " + String.valueOf(new Date(runtimeMXBean.getStartTime())) + " (" + runtimeMXBean.getStartTime() + "ms)");
/*     */           writer.println("Current Time: " + String.valueOf(new Date(currentTimeMillis)) + " (" + currentTimeMillis + "ms)");
/*     */           writer.println("Process Uptime: " + FormatUtil.timeUnitToString(runtimeMXBean.getUptime(), TimeUnit.MILLISECONDS) + " (" + runtimeMXBean.getUptime() + "ms)");
/*     */           writer.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors() + " - " + operatingSystemMXBean.getAvailableProcessors());
/*     */           writer.println("System Load Average: " + operatingSystemMXBean.getSystemLoadAverage());
/*     */           writer.println();
/*     */           if (operatingSystemMXBean instanceof OperatingSystemMXBean) {
/*     */             OperatingSystemMXBean sunOSBean = (OperatingSystemMXBean)operatingSystemMXBean;
/*     */             writer.println("Total Physical Memory: " + FormatUtil.bytesToString(sunOSBean.getTotalPhysicalMemorySize()) + " (" + sunOSBean.getTotalPhysicalMemorySize() + " Bytes)");
/*     */             writer.println("Free Physical Memory: " + FormatUtil.bytesToString(sunOSBean.getFreePhysicalMemorySize()) + " (" + sunOSBean.getFreePhysicalMemorySize() + " Bytes)");
/*     */             writer.println("Total Swap Memory: " + FormatUtil.bytesToString(sunOSBean.getTotalSwapSpaceSize()) + " (" + sunOSBean.getTotalSwapSpaceSize() + " Bytes)");
/*     */             writer.println("Free Swap Memory: " + FormatUtil.bytesToString(sunOSBean.getFreeSwapSpaceSize()) + " (" + sunOSBean.getFreeSwapSpaceSize() + " Bytes)");
/*     */             writer.println("System CPU Load: " + sunOSBean.getSystemCpuLoad());
/*     */             writer.println("Process CPU Load: " + sunOSBean.getProcessCpuLoad());
/*     */             writer.println();
/*     */           } 
/*     */           writer.println("Processor Identifier: " + System.getenv("PROCESSOR_IDENTIFIER"));
/*     */           writer.println("Processor Architecture: " + System.getenv("PROCESSOR_ARCHITECTURE"));
/*     */           writer.println("Processor Architecture W64/32: " + System.getenv("PROCESSOR_ARCHITEW6432"));
/*     */           writer.println("Number of Processors: " + System.getenv("NUMBER_OF_PROCESSORS"));
/*     */           writer.println();
/*     */           writer.println("Runtime Name: " + runtimeMXBean.getName());
/*     */           writer.println();
/*     */           writer.println("OS Name: " + operatingSystemMXBean.getName());
/*     */           writer.println("OS Arch: " + operatingSystemMXBean.getArch());
/*     */           writer.println("OS Version: " + operatingSystemMXBean.getVersion());
/*     */           writer.println();
/*     */           writer.println("Spec Name: " + runtimeMXBean.getSpecName());
/*     */           writer.println("Spec Vendor: " + runtimeMXBean.getSpecVendor());
/*     */           writer.println("Spec Version: " + runtimeMXBean.getSpecVersion());
/*     */           writer.println();
/*     */           writer.println("VM Name: " + runtimeMXBean.getVmName());
/*     */           writer.println("VM Vendor: " + runtimeMXBean.getVmVendor());
/*     */           writer.println("VM Version: " + runtimeMXBean.getVmVersion());
/*     */           writer.println();
/*     */           writer.println("Management Spec Version: " + runtimeMXBean.getManagementSpecVersion());
/*     */           writer.println();
/*     */           writer.println("Library Path: " + runtimeMXBean.getLibraryPath());
/*     */           try {
/*     */             writer.println("Boot ClassPath: " + runtimeMXBean.getBootClassPath());
/* 685 */           } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */           
/*     */           writer.println("ClassPath: " + runtimeMXBean.getClassPath());
/*     */           
/*     */           writer.println();
/*     */           writer.println("Input Arguments: " + String.valueOf(runtimeMXBean.getInputArguments()));
/*     */           writer.println("System Properties: " + String.valueOf(runtimeMXBean.getSystemProperties()));
/*     */         }writer);
/* 693 */     section("Current process info", () -> { MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean(); writeMemoryUsage(writer, "Heap Memory Usage: ", memoryMXBean.getHeapMemoryUsage()); writeMemoryUsage(writer, "Non-Heap Memory Usage: ", memoryMXBean.getNonHeapMemoryUsage()); writer.println("Objects Pending Finalization Count: " + memoryMXBean.getObjectPendingFinalizationCount()); }writer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 700 */     section("Garbage collector", () -> { for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) { writer.println("Name: " + garbageCollectorMXBean.getName()); writer.println("\tMemory Pool Names: " + Arrays.toString((Object[])garbageCollectorMXBean.getMemoryPoolNames())); writer.println("\tCollection Count: " + garbageCollectorMXBean.getCollectionCount()); writer.println("\tCollection Time: " + garbageCollectorMXBean.getCollectionTime()); writer.println(); }  }writer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 710 */     section("Memory pools", () -> { for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) { writer.println("Name: " + memoryPoolMXBean.getName()); writer.println("\tType: " + String.valueOf(memoryPoolMXBean.getType())); writer.println("\tPeak Usage: " + String.valueOf(memoryPoolMXBean.getPeakUsage())); writer.println("\tUsage: " + String.valueOf(memoryPoolMXBean.getUsage())); writer.println("\tUsage Threshold Supported: " + memoryPoolMXBean.isUsageThresholdSupported()); if (memoryPoolMXBean.isUsageThresholdSupported()) { writer.println("\tUsage Threshold: " + memoryPoolMXBean.getUsageThreshold()); writer.println("\tUsage Threshold Count: " + memoryPoolMXBean.getUsageThresholdCount()); writer.println("\tUsage Threshold Exceeded: " + memoryPoolMXBean.isUsageThresholdExceeded()); }  writer.println("\tCollection Usage: " + String.valueOf(memoryPoolMXBean.getCollectionUsage())); writer.println("\tCollection Usage Threshold Supported: " + memoryPoolMXBean.isCollectionUsageThresholdSupported()); if (memoryPoolMXBean.isCollectionUsageThresholdSupported()) { writer.println("\tCollection Usage Threshold: " + memoryPoolMXBean.getCollectionUsageThreshold()); writer.println("\tCollection Usage Threshold Count: " + memoryPoolMXBean.getCollectionUsageThresholdCount()); writer.println("\tCollection Usage Threshold Exceeded: " + memoryPoolMXBean.isCollectionUsageThresholdExceeded()); }  writer.println(); }  }writer);
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
/* 736 */     ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
/* 737 */     ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
/* 738 */     section("Threads (Count: " + threadInfos.length + ")", () -> { Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces(); Long2ObjectOpenHashMap long2ObjectOpenHashMap = new Long2ObjectOpenHashMap(); for (Thread thread : allStackTraces.keySet()) long2ObjectOpenHashMap.put(thread.getId(), thread);  for (ThreadInfo threadInfo : threadInfos) { Thread thread = (Thread)long2ObjectOpenHashMap.get(threadInfo.getThreadId()); if (thread != null) { writer.println("Name: " + thread.getName()); writer.println("State: " + String.valueOf(threadInfo.getThreadState())); writer.println("Thread Class: " + String.valueOf(thread.getClass())); writer.println("Thread Group: " + String.valueOf(thread.getThreadGroup())); writer.println("Priority: " + thread.getPriority()); writer.println("CPU Time: " + threadMXBean.getThreadCpuTime(threadInfo.getThreadId())); writer.println("Waited Time: " + threadInfo.getWaitedTime()); writer.println("Waited Count: " + threadInfo.getWaitedCount()); writer.println("Blocked Time: " + threadInfo.getBlockedTime()); writer.println("Blocked Count: " + threadInfo.getBlockedCount()); writer.println("Lock Name: " + threadInfo.getLockName()); writer.println("Lock Owner Id: " + threadInfo.getLockOwnerId()); writer.println("Lock Owner Name: " + threadInfo.getLockOwnerName()); writer.println("Daemon: " + thread.isDaemon()); writer.println("Interrupted: " + thread.isInterrupted()); writer.println("Uncaught Exception Handler: " + String.valueOf(thread.getUncaughtExceptionHandler().getClass())); if (thread instanceof InitStackThread) { writer.println("Init Stack: "); StackTraceElement[] arrayOfStackTraceElement = ((InitStackThread)thread).getInitStack(); for (StackTraceElement traceElement : arrayOfStackTraceElement) writer.println("\tat " + String.valueOf(traceElement));  }  writer.println("Current Stack: "); StackTraceElement[] trace = allStackTraces.get(thread); for (StackTraceElement traceElement : trace) writer.println("\tat " + String.valueOf(traceElement));  } else { writer.println("Failed to find thread!!!"); }  writer.println(threadInfo); }  }writer);
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
/* 780 */     section("Security Manager", () -> { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) { writer.println("Class: " + securityManager.getClass().getName()); } else { writer.println("No Security Manager found!"); }  }writer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 789 */     section("Classes", () -> { ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean(); writer.println("Loaded Class Count: " + classLoadingMXBean.getLoadedClassCount()); writer.println("Unloaded Class Count: " + classLoadingMXBean.getUnloadedClassCount()); writer.println("Total Loaded Class Count: " + classLoadingMXBean.getTotalLoadedClassCount()); }writer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 796 */     section("System Classloader", () -> { ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader(); writeClassLoader(writer, systemClassLoader); }writer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 801 */     section("DumpUtil Classloader", () -> { ClassLoader systemClassLoader = DumpUtil.class.getClassLoader(); writeClassLoader(writer, systemClassLoader); }writer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void printPacketStats(@Nonnull PrintWriter writer, @Nonnull String indent, @Nonnull String label, int count, long uncompressedTotal, long compressedTotal, long uncompressedMin, long uncompressedMax, long compressedMin, long compressedMax, double uncompressedAvg, double compressedAvg, int recentSeconds) {
/* 847 */     StringBuilder sb = new StringBuilder();
/* 848 */     sb.append(label).append(": ").append(count).append(" packet").append((count != 1) ? "s" : "");
/*     */     
/* 850 */     if (recentSeconds > 0) {
/* 851 */       sb.append(String.format(" (%.1f/sec)", new Object[] { Double.valueOf(count / recentSeconds) }));
/*     */     }
/*     */     
/* 854 */     sb.append("\n").append(indent).append("  Size: ").append(FormatUtil.bytesToString(uncompressedTotal));
/* 855 */     if (compressedTotal > 0L) {
/* 856 */       sb.append(" -> ").append(FormatUtil.bytesToString(compressedTotal)).append(" wire");
/* 857 */       double ratio = 100.0D * compressedTotal / uncompressedTotal;
/* 858 */       sb.append(String.format(" (%.1f%%)", new Object[] { Double.valueOf(ratio) }));
/*     */     } 
/*     */     
/* 861 */     sb.append("\n").append(indent).append("  Avg: ").append(FormatUtil.bytesToString((long)uncompressedAvg));
/* 862 */     if (compressedAvg > 0.0D) {
/* 863 */       sb.append(" -> ").append(FormatUtil.bytesToString((long)compressedAvg)).append(" wire");
/*     */     }
/*     */     
/* 866 */     sb.append("\n").append(indent).append("  Range: ")
/* 867 */       .append(FormatUtil.bytesToString(uncompressedMin)).append(" - ").append(FormatUtil.bytesToString(uncompressedMax));
/* 868 */     if (compressedMax > 0L) {
/* 869 */       sb.append(" (wire: ").append(FormatUtil.bytesToString(compressedMin)).append(" - ")
/* 870 */         .append(FormatUtil.bytesToString(compressedMax)).append(")");
/*     */     }
/*     */     
/* 873 */     writer.println(indent + indent);
/*     */   }
/*     */   
/*     */   private static void printComponentStore(@Nonnull PrintWriter writer, int width, int height, String name, long startNanos, @Nonnull Store<?> componentStore) {
/* 877 */     writer.println("\t- " + name + ":");
/* 878 */     writer.println("\t Archetype Chunk Count: " + componentStore.getArchetypeChunkCount());
/* 879 */     writer.println("\t Entity Count: " + componentStore.getEntityCount());
/*     */     
/* 881 */     ComponentRegistry.Data<?> data = componentStore.getRegistry().getData();
/* 882 */     HistoricMetric[] systemMetrics = componentStore.getSystemMetrics();
/* 883 */     for (int systemIndex = 0; systemIndex < data.getSystemSize(); systemIndex++) {
/* 884 */       ISystem<?> system = data.getSystem(systemIndex);
/* 885 */       HistoricMetric systemMetric = systemMetrics[systemIndex];
/*     */       
/* 887 */       writer.println("\t\t " + system.getClass().getName());
/* 888 */       writer.println("\t\t " + String.valueOf(system));
/* 889 */       writer.println("\t\t Archetype Chunk Count: " + componentStore.getArchetypeChunkCountFor(systemIndex));
/* 890 */       writer.println("\t\t Entity Count: " + componentStore.getEntityCountFor(systemIndex));
/*     */       
/* 892 */       if (systemMetric != null) {
/*     */         
/* 894 */         long[] periods = systemMetric.getPeriodsNanos();
/* 895 */         for (int i = 0; i < periods.length; i++) {
/* 896 */           long period = periods[i];
/* 897 */           String historyLengthFormatted = FormatUtil.timeUnitToString(period, TimeUnit.NANOSECONDS, true);
/*     */           
/* 899 */           double average = systemMetric.getAverage(i);
/* 900 */           long min = systemMetric.calculateMin(i);
/* 901 */           long max = systemMetric.calculateMax(i);
/*     */           
/* 903 */           writer.println("\t\t\t(" + historyLengthFormatted + "): Min: " + FormatUtil.timeUnitToString(min, TimeUnit.NANOSECONDS) + ", Avg: " + 
/* 904 */               FormatUtil.timeUnitToString((long)average, TimeUnit.NANOSECONDS) + ", Max: " + 
/* 905 */               FormatUtil.timeUnitToString(max, TimeUnit.NANOSECONDS));
/*     */           
/* 907 */           long[] historyTimestamps = systemMetric.getTimestamps(i);
/* 908 */           long[] historyValues = systemMetric.getValues(i);
/*     */           
/* 910 */           StringBuilder sb = new StringBuilder();
/* 911 */           StringUtil.generateGraph(sb, width, height, startNanos - period, startNanos, min, max, value -> FormatUtil.timeUnitToString(MathUtil.fastCeil(value), TimeUnit.NANOSECONDS), historyTimestamps.length, ii -> historyTimestamps[ii], ii -> historyValues[ii]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 919 */           writer.println(sb);
/*     */         } 
/*     */       } 
/*     */     } 
/* 923 */     writer.println("\t\t Archetype Chunks:");
/* 924 */     for (ArchetypeChunkData chunkData : componentStore.collectArchetypeChunkData()) {
/* 925 */       writer.println("\t\t\t- Entities: " + chunkData.getEntityCount() + ", Components: " + Arrays.toString((Object[])chunkData.getComponentTypes()));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void section(String name, @Nonnull Runnable runnable, @Nonnull PrintWriter writer) {
/* 930 */     writer.println("**** " + name + " ****");
/*     */     try {
/* 932 */       runnable.run();
/* 933 */     } catch (Throwable t) {
/* 934 */       (new RuntimeException("Failed to get data for section: " + name, t)).printStackTrace(writer);
/*     */     } 
/* 936 */     writer.println();
/* 937 */     writer.println();
/*     */   }
/*     */   
/*     */   private static void printIndented(@Nonnull PrintWriter writer, @Nonnull String text, @Nonnull String indent) {
/* 941 */     for (String line : text.split("\n")) {
/* 942 */       writer.println(indent + indent);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void writeMemoryUsage(@Nonnull PrintWriter writer, String title, @Nonnull MemoryUsage memoryUsage) {
/* 947 */     writer.println(title);
/* 948 */     writer.println("\tInit: " + FormatUtil.bytesToString(memoryUsage.getInit()) + " (" + memoryUsage.getInit() + " Bytes)");
/* 949 */     writer.println("\tUsed: " + FormatUtil.bytesToString(memoryUsage.getUsed()) + " (" + memoryUsage.getUsed() + " Bytes)");
/* 950 */     writer.println("\tCommitted: " + FormatUtil.bytesToString(memoryUsage.getCommitted()) + " (" + memoryUsage.getCommitted() + " Bytes)");
/* 951 */     long max = memoryUsage.getMax();
/* 952 */     if (max > 0L) {
/* 953 */       writer.println("\tMax: " + FormatUtil.bytesToString(max) + " (" + max + " Bytes)");
/* 954 */       long free = max - memoryUsage.getCommitted();
/* 955 */       writer.println("\tFree: " + FormatUtil.bytesToString(free) + " (" + free + " Bytes)");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeClassLoader(@Nonnull PrintWriter writer, @Nullable ClassLoader systemClassLoader) {
/* 960 */     if (systemClassLoader != null) {
/* 961 */       writer.println("Class: " + systemClassLoader.getClass().getName());
/* 962 */       while (systemClassLoader.getParent() != null) {
/* 963 */         systemClassLoader = systemClassLoader.getParent();
/* 964 */         writer.println(" - Parent: " + systemClassLoader.getClass().getName());
/*     */       } 
/*     */     } else {
/* 967 */       writer.println("No class loader found!");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\DumpUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */