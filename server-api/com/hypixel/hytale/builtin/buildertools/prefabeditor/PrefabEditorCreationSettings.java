/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.commands.PrefabEditLoadCommand;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabAlignment;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabRootDirectory;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabRowSplitMode;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabStackingAxis;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.WorldGenType;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabEditorCreationSettings
/*     */   implements PrefabEditorCreationContext, JsonAssetWithMap<String, DefaultAssetMap<String, PrefabEditorCreationSettings>>
/*     */ {
/*     */   private static final int RECURSIVE_SEARCH_MAX_DEPTH = 10;
/*     */   public static final AssetBuilderCodec<String, PrefabEditorCreationSettings> CODEC;
/*     */   private static AssetStore<String, PrefabEditorCreationSettings, DefaultAssetMap<String, PrefabEditorCreationSettings>> ASSET_STORE;
/*     */   private String id;
/*     */   private AssetExtraInfo.Data data;
/*     */   private transient Player player;
/*     */   private transient PlayerRef playerRef;
/*     */   
/*     */   static {
/* 136 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(PrefabEditorCreationSettings.class, PrefabEditorCreationSettings::new, (Codec)Codec.STRING, (builder, id) -> builder.id = id, builder -> builder.id, (builder, data) -> builder.data = data, builder -> builder.data).append(new KeyedCodec("RootDirectory", (Codec)new EnumCodec(PrefabRootDirectory.class)), (o, rootDirectory) -> o.prefabRootDirectory = rootDirectory, o -> o.prefabRootDirectory).add()).append(new KeyedCodec("UnprocessedPrefabPaths", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (o, unprocessedPrefabPaths) -> o.unprocessedPrefabPaths = List.of(unprocessedPrefabPaths), o -> (String[])o.unprocessedPrefabPaths.toArray(())).add()).append(new KeyedCodec("PasteYLevelGoal", (Codec)Codec.INTEGER), (o, pasteYLevelGoal) -> o.pasteYLevelGoal = pasteYLevelGoal.intValue(), o -> Integer.valueOf(o.pasteYLevelGoal)).add()).append(new KeyedCodec("BlocksBetweenEachPrefab", (Codec)Codec.INTEGER), (o, blocksBetweenEachPrefab) -> o.blocksBetweenEachPrefab = blocksBetweenEachPrefab.intValue(), o -> Integer.valueOf(o.blocksBetweenEachPrefab)).add()).append(new KeyedCodec("WorldGenType", (Codec)new EnumCodec(WorldGenType.class)), (o, worldGenType) -> o.worldGenType = worldGenType, o -> o.worldGenType).add()).append(new KeyedCodec("BlocksAboveSurface", (Codec)Codec.INTEGER), (o, blocksAboveSurface) -> o.blocksAboveSurface = blocksAboveSurface.intValue(), o -> Integer.valueOf(o.blocksAboveSurface)).add()).append(new KeyedCodec("PrefabStackingAxis", (Codec)new EnumCodec(PrefabStackingAxis.class)), (o, stackingAxis) -> o.stackingAxis = stackingAxis, o -> o.stackingAxis).add()).append(new KeyedCodec("PrefabAlignment", (Codec)new EnumCodec(PrefabAlignment.class)), (o, alignment) -> o.alignment = alignment, o -> o.alignment).add()).append(new KeyedCodec("RecursiveSearch", (Codec)Codec.BOOLEAN), (o, recursive) -> o.recursive = recursive.booleanValue(), o -> Boolean.valueOf(o.recursive)).add()).append(new KeyedCodec("LoadChildren", (Codec)Codec.BOOLEAN), (o, loadChildren) -> o.loadChildren = loadChildren.booleanValue(), o -> Boolean.valueOf(o.loadChildren)).add()).append(new KeyedCodec("LoadEntities", (Codec)Codec.BOOLEAN), (o, loadEntities) -> o.loadEntities = loadEntities.booleanValue(), o -> Boolean.valueOf(o.loadEntities)).add()).append(new KeyedCodec("EnableWorldTicking", (Codec)Codec.BOOLEAN), (o, enableWorldTicking) -> o.enableWorldTicking = enableWorldTicking.booleanValue(), o -> Boolean.valueOf(o.enableWorldTicking)).add()).append(new KeyedCodec("RowSplitMode", (Codec)new EnumCodec(PrefabRowSplitMode.class)), (o, rowSplitMode) -> o.rowSplitMode = rowSplitMode, o -> o.rowSplitMode).add()).append(new KeyedCodec("Environment", (Codec)Codec.STRING), (o, environment) -> o.environment = environment, o -> o.environment).addValidator(Environment.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("GrassTint", (Codec)Codec.STRING), (o, grassTint) -> o.grassTint = grassTint, o -> o.grassTint).add()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static AssetStore<String, PrefabEditorCreationSettings, DefaultAssetMap<String, PrefabEditorCreationSettings>> getAssetStore() {
/* 141 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(PrefabEditorCreationSettings.class); 
/* 142 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, PrefabEditorCreationSettings> getAssetMap() {
/* 146 */     return (DefaultAssetMap<String, PrefabEditorCreationSettings>)getAssetStore().getAssetMap();
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
/* 170 */   private PrefabRootDirectory prefabRootDirectory = PrefabRootDirectory.ASSET;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   private final transient List<Path> prefabPaths = (List<Path>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   private List<String> unprocessedPrefabPaths = (List<String>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   private int pasteYLevelGoal = 55;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   private int blocksBetweenEachPrefab = 15;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   private WorldGenType worldGenType = PrefabEditLoadCommand.DEFAULT_WORLD_GEN_TYPE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   private int blocksAboveSurface = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   private PrefabStackingAxis stackingAxis = PrefabEditLoadCommand.DEFAULT_PREFAB_STACKING_AXIS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   private PrefabAlignment alignment = PrefabEditLoadCommand.DEFAULT_PREFAB_ALIGNMENT;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean recursive;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean loadChildren;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean loadEntities;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean enableWorldTicking = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   private PrefabRowSplitMode rowSplitMode = PrefabRowSplitMode.BY_ALL_SUBFOLDERS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 245 */   private String environment = "Env_Zone1_Plains";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   private String grassTint = "#5B9E28";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabEditorCreationSettings(PrefabRootDirectory prefabRootDirectory, List<String> unprocessedPrefabPaths, int pasteYLevelGoal, int blocksBetweenEachPrefab, WorldGenType worldGenType, int blocksAboveSurface, PrefabStackingAxis stackingAxis, PrefabAlignment alignment, boolean recursive, boolean loadChildren, boolean loadEntities, boolean enableWorldTicking, PrefabRowSplitMode rowSplitMode, String environment, String grassTint) {
/* 260 */     this.prefabRootDirectory = prefabRootDirectory;
/* 261 */     this.unprocessedPrefabPaths = unprocessedPrefabPaths;
/* 262 */     this.pasteYLevelGoal = pasteYLevelGoal;
/* 263 */     this.blocksBetweenEachPrefab = blocksBetweenEachPrefab;
/* 264 */     this.worldGenType = worldGenType;
/* 265 */     this.blocksAboveSurface = blocksAboveSurface;
/* 266 */     this.stackingAxis = stackingAxis;
/* 267 */     this.alignment = alignment;
/* 268 */     this.recursive = recursive;
/* 269 */     this.loadChildren = loadChildren;
/* 270 */     this.loadEntities = loadEntities;
/* 271 */     this.enableWorldTicking = enableWorldTicking;
/* 272 */     this.rowSplitMode = rowSplitMode;
/* 273 */     this.environment = environment;
/* 274 */     this.grassTint = grassTint;
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
/*     */   @Nullable
/*     */   PrefabEditorCreationContext finishProcessing(Player editor, PlayerRef playerRef, boolean creatingNewPrefab) {
/* 290 */     this.prefabPaths.clear();
/* 291 */     this.player = editor;
/* 292 */     this.playerRef = playerRef;
/*     */ 
/*     */     
/* 295 */     for (String inputPrefabName : this.unprocessedPrefabPaths) {
/*     */       
/* 297 */       inputPrefabName = StringUtil.stripQuotes(inputPrefabName);
/*     */       
/* 299 */       inputPrefabName = inputPrefabName.replace('/', File.separatorChar);
/* 300 */       inputPrefabName = inputPrefabName.replace('\\', File.separatorChar);
/*     */ 
/*     */       
/* 303 */       if (!SingleplayerModule.isOwner(playerRef) && !inputPrefabName.isEmpty() && Path.of(inputPrefabName, new String[0]).isAbsolute()) {
/* 304 */         this.player.sendMessage(Message.translation("server.commands.editprefab.error.absolutePathNotAllowed"));
/* 305 */         return null;
/*     */       } 
/*     */       
/* 308 */       if (inputPrefabName.endsWith(File.separator)) {
/*     */         
/* 310 */         Path rootPath = resolveRootPathForInput(inputPrefabName);
/* 311 */         String relativePath = getRelativePathForInput(inputPrefabName);
/* 312 */         Path resolvedDir = rootPath.resolve(relativePath);
/* 313 */         if (!SingleplayerModule.isOwner(playerRef) && !PathUtil.isChildOf(rootPath, resolvedDir)) {
/* 314 */           this.player.sendMessage(Message.translation("server.commands.editprefab.error.pathTraversal"));
/* 315 */           return null;
/*     */         }  
/* 317 */         try { Stream<Path> walk = Files.walk(resolvedDir, this.recursive ? 10 : 1, new java.nio.file.FileVisitOption[0]);
/*     */ 
/*     */           
/* 320 */           try { Objects.requireNonNull(this.prefabPaths); walk.filter(x$0 -> Files.isRegularFile(x$0, new java.nio.file.LinkOption[0])).filter(path -> path.toString().endsWith(".prefab.json")).forEach(this.prefabPaths::add);
/* 321 */             if (walk != null) walk.close();  } catch (Throwable throwable) { if (walk != null) try { walk.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 322 */         { e.printStackTrace(); }
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 328 */       if (!stringEndsWithPrefabPath(inputPrefabName)) inputPrefabName = inputPrefabName + ".prefab.json";
/*     */       
/*     */       try {
/* 331 */         Path rootPath = resolveRootPathForInput(inputPrefabName);
/* 332 */         String relativePath = getRelativePathForInput(inputPrefabName);
/* 333 */         Path resolvedPath = rootPath.resolve(relativePath);
/* 334 */         if (!SingleplayerModule.isOwner(playerRef) && !PathUtil.isChildOf(rootPath, resolvedPath)) {
/* 335 */           this.player.sendMessage(Message.translation("server.commands.editprefab.error.pathTraversal"));
/* 336 */           return null;
/*     */         } 
/* 338 */         this.prefabPaths.add(resolvedPath);
/* 339 */       } catch (Exception e) {
/* 340 */         e.printStackTrace();
/* 341 */         this.player.sendMessage(Message.translation("server.commands.editprefab.finishProcessingError")
/* 342 */             .param("error", e.getMessage()));
/* 343 */         return null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 348 */     if (!creatingNewPrefab) {
/* 349 */       for (Path processedPrefabPath : this.prefabPaths) {
/* 350 */         if (!Files.exists(processedPrefabPath, new java.nio.file.LinkOption[0])) {
/* 351 */           this.player.sendMessage(Message.translation("server.commands.editprefab.load.error.prefabNotFound").param("path", processedPrefabPath.toString()));
/* 352 */           return null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 357 */     if (this.prefabPaths.isEmpty()) {
/* 358 */       Message header = Message.translation("server.commands.editprefab.noPrefabsInPath");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 363 */       Set<Message> values = (Set<Message>)this.unprocessedPrefabPaths.stream().map(p -> this.prefabRootDirectory.getPrefabPath().resolve(p)).map(Path::toString).map(Message::raw).collect(Collectors.toSet());
/* 364 */       this.player.sendMessage(MessageFormat.list(header, values));
/* 365 */       return null;
/*     */     } 
/*     */     
/* 368 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private Path resolveRootPathForInput(@Nonnull String inputPath) {
/* 377 */     if (this.prefabRootDirectory != PrefabRootDirectory.ASSET) {
/* 378 */       return this.prefabRootDirectory.getPrefabPath();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     String firstComponent = inputPath.contains(File.separator) ? inputPath.substring(0, inputPath.indexOf(File.separator)) : inputPath;
/*     */     
/* 386 */     for (PrefabStore.AssetPackPrefabPath packPath : PrefabStore.get().getAllAssetPrefabPaths()) {
/* 387 */       if (packPath.getDisplayName().equals(firstComponent)) {
/* 388 */         return packPath.prefabsPath();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 393 */     return this.prefabRootDirectory.getPrefabPath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private String getRelativePathForInput(@Nonnull String inputPath) {
/* 402 */     if (this.prefabRootDirectory != PrefabRootDirectory.ASSET) {
/* 403 */       return inputPath;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     String firstComponent = inputPath.contains(File.separator) ? inputPath.substring(0, inputPath.indexOf(File.separator)) : inputPath;
/*     */     
/* 411 */     for (PrefabStore.AssetPackPrefabPath packPath : PrefabStore.get().getAllAssetPrefabPaths()) {
/* 412 */       if (packPath.getDisplayName().equals(firstComponent)) {
/*     */         
/* 414 */         if (inputPath.contains(File.separator)) {
/* 415 */           return inputPath.substring(inputPath.indexOf(File.separator) + 1);
/*     */         }
/* 417 */         return "";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 422 */     return inputPath;
/*     */   }
/*     */   
/*     */   public static boolean stringEndsWithPrefabPath(@Nonnull String input) {
/* 426 */     return (input.endsWith(".prefab.json") || input.endsWith(".prefab.json.lpf") || input
/* 427 */       .endsWith(".lpf"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<PrefabEditorCreationSettings> load(@Nonnull String name) {
/* 438 */     return CompletableFuture.supplyAsync(() -> (PrefabEditorCreationSettings)getAssetMap().getAsset(name));
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
/*     */   public static CompletableFuture<Void> save(@Nonnull String name, PrefabEditorCreationSettings settings) {
/* 450 */     return CompletableFuture.runAsync(() -> {
/*     */           try {
/*     */             getAssetStore().writeAssetToDisk(AssetModule.get().getBaseAssetPack(), Map.of(Path.of(name + ".json", new String[0]), settings));
/* 453 */           } catch (IOException e) {
/*     */             e.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getEditor() {
/* 461 */     return this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerRef getEditorRef() {
/* 466 */     return this.playerRef;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Path> getPrefabPaths() {
/* 471 */     return this.prefabPaths;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlocksBetweenEachPrefab() {
/* 476 */     return this.blocksBetweenEachPrefab;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPasteLevelGoal() {
/* 481 */     return this.pasteYLevelGoal;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadChildPrefabs() {
/* 486 */     return this.loadChildren;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldLoadEntities() {
/* 491 */     return this.loadEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrefabStackingAxis getStackingAxis() {
/* 496 */     return this.stackingAxis;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenType getWorldGenType() {
/* 501 */     return this.worldGenType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlocksAboveSurface() {
/* 506 */     return this.blocksAboveSurface;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrefabAlignment getAlignment() {
/* 511 */     return this.alignment;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 516 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrefabRootDirectory getPrefabRootDirectory() {
/* 521 */     return this.prefabRootDirectory;
/*     */   }
/*     */   
/*     */   public List<String> getUnprocessedPrefabPaths() {
/* 525 */     return this.unprocessedPrefabPaths;
/*     */   }
/*     */   
/*     */   public int getPasteYLevelGoal() {
/* 529 */     return this.pasteYLevelGoal;
/*     */   }
/*     */   
/*     */   public boolean isRecursive() {
/* 533 */     return this.recursive;
/*     */   }
/*     */   
/*     */   public boolean isLoadChildren() {
/* 537 */     return this.loadChildren;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWorldTickingEnabled() {
/* 542 */     return this.enableWorldTicking;
/*     */   }
/*     */ 
/*     */   
/*     */   public PrefabRowSplitMode getRowSplitMode() {
/* 547 */     return this.rowSplitMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEnvironment() {
/* 552 */     return this.environment;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGrassTint() {
/* 557 */     return this.grassTint;
/*     */   }
/*     */   
/*     */   private PrefabEditorCreationSettings() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabEditorCreationSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */