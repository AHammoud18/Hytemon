/*     */ package com.hypixel.hytale.server.core.command.commands.utility;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.prefab.config.SelectionPrefabSerializer;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.provider.EmptyChunkStorageProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.provider.IChunkStorageProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.resources.EmptyResourceStorageProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.resources.IResourceStorageProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.DummyWorldGenProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.core.util.io.FileUtil;
/*     */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class ConvertPrefabsCommand extends AbstractAsyncCommand {
/*     */   private static final String UNABLE_TO_LOAD_MODEL = "Unable to load entity with model ";
/*     */   private static final String FAILED_TO_FIND_BLOCK = "Failed to find block ";
/*     */   private static final int BATCH_SIZE = 10;
/*     */   private static final long DELAY_BETWEEN_BATCHES_MS = 50L;
/*     */   @Nonnull
/*  52 */   private static final Message MESSAGE_COMMANDS_CONVERT_PREFABS_FAILED = Message.translation("server.commands.convertprefabs.failed");
/*     */   @Nonnull
/*  54 */   private static final Message MESSAGE_COMMANDS_CONVERT_PREFABS_DEFAULT_WORLD_NULL = Message.translation("server.commands.convertprefabs.defaultWorldNull");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   private final FlagArg blocksFlag = withFlagArg("blocks", "server.commands.convertprefabs.blocks.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  66 */   private final FlagArg fillerFlag = withFlagArg("filler", "server.commands.convertprefabs.filler.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  72 */   private final FlagArg relativeFlag = withFlagArg("relative", "server.commands.convertprefabs.relative.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  78 */   private final FlagArg entitiesFlag = withFlagArg("entities", "server.commands.convertprefabs.entities.desc");
/*     */   
/*  80 */   private final FlagArg destructiveFlag = withFlagArg("destructive", "server.commands.convertprefabs.destructive.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  86 */   private final OptionalArg<String> pathArg = withOptionalArg("path", "server.commands.convertprefabs.path.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  92 */   private final DefaultArg<String> storeArg = withDefaultArg("store", "server.commands.convertprefabs.store.desc", (ArgumentType)ArgTypes.STRING, "asset", "server.commands.convertprefabs.store.defaultDesc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvertPrefabsCommand() {
/*  98 */     super("convertprefabs", "server.commands.convertprefabs.desc");
/*     */   }
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*     */     Path assetPath;
/* 103 */     boolean blocks = ((Boolean)this.blocksFlag.get(context)).booleanValue();
/* 104 */     boolean filler = ((Boolean)this.fillerFlag.get(context)).booleanValue();
/* 105 */     boolean relative = ((Boolean)this.relativeFlag.get(context)).booleanValue();
/* 106 */     boolean entities = ((Boolean)this.entitiesFlag.get(context)).booleanValue();
/* 107 */     boolean destructive = ((Boolean)this.destructiveFlag.get(context)).booleanValue();
/*     */     
/* 109 */     World defaultWorld = Universe.get().getDefaultWorld();
/* 110 */     if (defaultWorld == null) {
/* 111 */       context.sendMessage(MESSAGE_COMMANDS_CONVERT_PREFABS_DEFAULT_WORLD_NULL);
/* 112 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 116 */     defaultWorld.getChunk(ChunkUtil.indexChunk(0, 0));
/*     */     
/* 118 */     ObjectArrayList objectArrayList1 = new ObjectArrayList();
/* 119 */     ObjectArrayList objectArrayList2 = new ObjectArrayList();
/*     */ 
/*     */     
/* 122 */     String storeOption = (String)this.storeArg.get(context);
/* 123 */     if (this.pathArg.provided(context)) {
/* 124 */       Path path = Paths.get((String)this.pathArg.get(context), new String[0]);
/* 125 */       return convertPath(path, blocks, filler, relative, entities, destructive, (List<String>)objectArrayList1, (List<String>)objectArrayList2)
/* 126 */         .thenApply(_v -> {
/*     */             sendCompletionMessages(context, assetPath, failed, skipped);
/*     */             return null;
/*     */           });
/*     */     } 
/* 131 */     switch (storeOption) {
/*     */       case "server":
/* 133 */         assetPath = PrefabStore.get().getServerPrefabsPath();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case "asset":
/* 141 */         assetPath = PrefabStore.get().getAssetPrefabsPath();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case "worldgen":
/* 149 */         assetPath = PrefabStore.get().getWorldGenPrefabsPath();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case "all":
/* 157 */         assetPath = Path.of("", new String[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     context.sendMessage(Message.translation("server.commands.convertprefabs.invalidStore").param("store", storeOption));
/* 169 */     return CompletableFuture.completedFuture(null);
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
/*     */   private void sendCompletionMessages(@Nonnull CommandContext context, @Nonnull Path assetPath, @Nonnull List<String> failed, @Nonnull List<String> skipped) {
/* 184 */     if (!skipped.isEmpty()) {
/* 185 */       Message header = Message.translation("server.commands.convertprefabs.skipped");
/* 186 */       context.sendMessage(MessageFormat.list(header, (Collection)skipped.stream().map(Message::raw).collect(Collectors.toSet())));
/*     */     } 
/*     */     
/* 189 */     if (!failed.isEmpty()) {
/* 190 */       context.sendMessage(MessageFormat.list(MESSAGE_COMMANDS_CONVERT_PREFABS_FAILED, (Collection)failed.stream().map(Message::raw).collect(Collectors.toSet())));
/*     */     }
/*     */     
/* 193 */     context.sendMessage(Message.translation("server.commands.prefabConvertionDone")
/* 194 */         .param("path", assetPath.toString()));
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
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> convertPath(@Nonnull Path assetPath, boolean blocks, boolean filler, boolean relative, boolean entities, boolean destructive, @Nonnull List<String> failed, @Nonnull List<String> skipped) {
/*     */     CompletableFuture<World> conversionWorldFuture;
/* 219 */     if (!Files.exists(assetPath, new java.nio.file.LinkOption[0])) {
/* 220 */       return CompletableFuture.completedFuture(null);
/*     */     }
/*     */ 
/*     */     
/* 224 */     if (entities || blocks) {
/* 225 */       Universe universe = Universe.get();
/* 226 */       WorldConfig config = new WorldConfig();
/* 227 */       config.setWorldGenProvider((IWorldGenProvider)new DummyWorldGenProvider());
/* 228 */       config.setChunkStorageProvider((IChunkStorageProvider)EmptyChunkStorageProvider.INSTANCE);
/* 229 */       config.setResourceStorageProvider((IResourceStorageProvider)EmptyResourceStorageProvider.INSTANCE);
/*     */       try {
/* 231 */         conversionWorldFuture = universe.makeWorld("ConvertPrefabs-" + String.valueOf(UUID.randomUUID()), Files.createTempDirectory("convertprefab", (FileAttribute<?>[])new FileAttribute[0]), config);
/* 232 */       } catch (IOException e) {
/* 233 */         throw SneakyThrow.sneakyThrow(e);
/*     */       } 
/*     */     } else {
/* 236 */       conversionWorldFuture = null;
/*     */     } 
/*     */     
/* 239 */     try { Stream<Path> stream = Files.walk(assetPath, FileUtil.DEFAULT_WALK_TREE_OPTIONS_ARRAY);
/*     */ 
/*     */       
/* 242 */       try { List<Path> prefabPaths = (List<Path>)stream.filter(path -> (Files.isRegularFile(path, new java.nio.file.LinkOption[0]) && path.toString().endsWith(".prefab.json"))).collect(Collectors.toList());
/*     */         
/* 244 */         if (prefabPaths.isEmpty())
/* 245 */         { if (conversionWorldFuture != null) {
/* 246 */             conversionWorldFuture.thenAccept(world -> Universe.get().removeWorld(world.getName()));
/*     */           }
/* 248 */           CompletableFuture<?> completableFuture1 = CompletableFuture.completedFuture(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 259 */           if (stream != null) stream.close();  return (CompletableFuture)completableFuture1; }  CompletableFuture<?> completableFuture = processPrefabsInBatches(prefabPaths, blocks, filler, relative, entities, destructive, conversionWorldFuture, failed, skipped).thenApply(_v -> { if (conversionWorldFuture != null) conversionWorldFuture.thenAccept(());  return null; }); if (stream != null) stream.close();  return (CompletableFuture)completableFuture; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 260 */     { throw SneakyThrow.sneakyThrow(e); }
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
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> processPrefabsInBatches(@Nonnull List<Path> prefabPaths, boolean blocks, boolean filler, boolean relative, boolean entities, boolean destructive, @Nullable CompletableFuture<World> conversionWorldFuture, @Nonnull List<String> failed, @Nonnull List<String> skipped) {
/* 278 */     CompletableFuture<Void> result = CompletableFuture.completedFuture(null);
/*     */     
/* 280 */     for (int i = 0; i < prefabPaths.size(); i += 10) {
/* 281 */       int batchEnd = Math.min(i + 10, prefabPaths.size());
/* 282 */       List<Path> batch = prefabPaths.subList(i, batchEnd);
/* 283 */       int batchIndex = i / 10;
/*     */ 
/*     */       
/* 286 */       if (batchIndex > 0) {
/* 287 */         result = result.thenCompose(_v -> CompletableFuture.runAsync((), CompletableFuture.delayedExecutor(50L, TimeUnit.MILLISECONDS)));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       CompletableFuture[] arrayOfCompletableFuture = (CompletableFuture[])batch.stream().map(path -> processPrefab(path, blocks, filler, relative, entities, destructive, conversionWorldFuture, failed, skipped)).toArray(x$0 -> new CompletableFuture[x$0]);
/*     */       
/* 299 */       result = result.thenCompose(_v -> CompletableFuture.allOf((CompletableFuture<?>[])batchFutures));
/*     */     } 
/*     */     
/* 302 */     return result;
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
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> processPrefab(@Nonnull Path path, boolean blocks, boolean filler, boolean relative, boolean entities, boolean destructive, @Nullable CompletableFuture<World> conversionWorldFuture, @Nonnull List<String> failed, @Nonnull List<String> skipped) {
/* 319 */     return BsonUtil.readDocument(path, false)
/* 320 */       .thenApply(document -> {
/*     */           BlockSelection prefab = SelectionPrefabSerializer.deserialize(document);
/*     */           
/*     */           if (filler) {
/*     */             prefab.tryFixFiller(destructive);
/*     */           }
/*     */           
/*     */           if (relative) {
/*     */             prefab = prefab.relativize();
/*     */           }
/*     */           
/*     */           return prefab;
/* 332 */         }).thenCompose(prefab -> 
/* 333 */         (entities && conversionWorldFuture != null) ? conversionWorldFuture.thenCompose(()).thenApply(()) : CompletableFuture.completedFuture(prefab))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 344 */       .thenCompose(prefab -> 
/* 345 */         (blocks && conversionWorldFuture != null) ? conversionWorldFuture.thenCompose(()).thenApply(()) : CompletableFuture.completedFuture(prefab))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 351 */       .thenCompose(prefab -> {
/*     */           BsonDocument newDocument = SelectionPrefabSerializer.serialize(prefab);
/*     */           
/*     */           return BsonUtil.writeDocument(path, newDocument, false);
/* 355 */         }).exceptionally(throwable -> {
/*     */           String message = (throwable.getCause() != null) ? throwable.getCause().getMessage() : null;
/*     */           if (message != null) {
/*     */             if (message.contains("Failed to find block ")) {
/*     */               if (message.substring("Failed to find block ".length()).contains("%")) {
/*     */                 skipped.add("Skipped prefab " + String.valueOf(path) + " because it contains block % chance.");
/*     */                 return null;
/*     */               } 
/*     */               failed.add("Failed to update " + String.valueOf(path) + " because " + message);
/*     */               return null;
/*     */             } 
/*     */             if (message.contains("Unable to load entity with model ")) {
/*     */               failed.add("Failed to update " + String.valueOf(path) + " because " + message);
/*     */               return null;
/*     */             } 
/*     */           } 
/*     */           failed.add("Failed to update " + String.valueOf(path) + " because " + String.valueOf((message != null) ? message : ((throwable.getCause() != null) ? throwable.getCause().getClass() : throwable.getClass())));
/*     */           if (throwable.getCause() != null)
/*     */             (new Exception("Failed to update " + String.valueOf(path), throwable.getCause())).printStackTrace(); 
/*     */           return null;
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\ConvertPrefabsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */