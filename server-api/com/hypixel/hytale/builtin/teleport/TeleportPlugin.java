/*     */ package com.hypixel.hytale.builtin.teleport;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.teleport.commands.teleport.TeleportCommand;
/*     */ import com.hypixel.hytale.builtin.teleport.commands.warp.WarpCommand;
/*     */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandRegistry;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.AllWorldsLoadedEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ChunkPreLoadProcessEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class TeleportPlugin extends JavaPlugin {
/*     */   private static TeleportPlugin instance;
/*     */   public static final String WARP_MODEL_ID = "Warp";
/*     */   private ComponentType<EntityStore, TeleportHistory> teleportHistoryComponentType;
/*     */   private ComponentType<EntityStore, WarpComponent> warpComponentType;
/*     */   
/*     */   @Nonnull
/*     */   public static TeleportPlugin get() {
/*  59 */     return instance;
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
/*     */   @Nonnull
/*  80 */   private final AtomicBoolean loaded = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  85 */   private final ReentrantLock saveLock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  90 */   private final AtomicBoolean postSaveRedo = new AtomicBoolean(false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  96 */   private final Map<String, Warp> warps = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Model warpModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeleportPlugin(@Nonnull JavaPluginInit init) {
/* 110 */     super(init);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, TeleportHistory> getTeleportHistoryComponentType() {
/* 118 */     return this.teleportHistoryComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWarpsLoaded() {
/* 125 */     return this.loaded.get();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/* 130 */     instance = this;
/*     */     
/* 132 */     CommandRegistry commandRegistry = getCommandRegistry();
/* 133 */     EventRegistry eventRegistry = getEventRegistry();
/*     */     
/* 135 */     commandRegistry.registerCommand((AbstractCommand)new TeleportCommand());
/* 136 */     commandRegistry.registerCommand((AbstractCommand)new WarpCommand());
/* 137 */     commandRegistry.registerCommand((AbstractCommand)new SpawnCommand());
/*     */     
/* 139 */     eventRegistry.register(LoadedAssetsEvent.class, ModelAsset.class, this::onModelAssetChange);
/* 140 */     eventRegistry.registerGlobal(ChunkPreLoadProcessEvent.class, this::onChunkPreLoadProcess);
/*     */     
/* 142 */     eventRegistry.registerGlobal(AddWorldEvent.class, event -> event.getWorld().getWorldMapManager().addMarkerProvider("warps", WarpMarkerProvider.INSTANCE));
/* 143 */     eventRegistry.registerGlobal(AllWorldsLoadedEvent.class, event -> loadWarps());
/*     */     
/* 145 */     this.teleportHistoryComponentType = EntityStore.REGISTRY.registerComponent(TeleportHistory.class, TeleportHistory::new);
/*     */     
/* 147 */     this.warpComponentType = EntityStore.REGISTRY.registerComponent(WarpComponent.class, () -> {
/*     */           throw new UnsupportedOperationException("WarpComponent must be created manually");
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start() {
/* 154 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset("Warp");
/* 155 */     if (modelAsset == null) {
/* 156 */       throw new IllegalStateException(String.format("Default warp model '%s' not found", new Object[] { "Warp" }));
/*     */     }
/*     */     
/* 159 */     this.warpModel = Model.createUnitScaleModel(modelAsset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutdown() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadWarps() {
/* 170 */     BsonDocument document = null;
/*     */     
/* 172 */     Path universePath = Universe.get().getPath();
/*     */     
/* 174 */     Path oldPath = universePath.resolve("warps.bson");
/* 175 */     Path path = universePath.resolve("warps.json");
/*     */     
/* 177 */     if (Files.exists(oldPath, new java.nio.file.LinkOption[0]) && !Files.exists(path, new java.nio.file.LinkOption[0])) {
/*     */       try {
/* 179 */         Files.move(oldPath, path, new java.nio.file.CopyOption[0]);
/* 180 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 184 */     if (Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 185 */       document = BsonUtil.readDocument(path).join();
/*     */     }
/*     */     
/* 188 */     if (document != null) {
/*     */ 
/*     */       
/* 191 */       BsonArray bsonWarps = document.containsKey("Warps") ? document.getArray("Warps") : document.getArray("warps");
/*     */       
/* 193 */       this.warps.clear();
/* 194 */       for (Warp warp : (Warp[])Warp.ARRAY_CODEC.decode((BsonValue)bsonWarps)) {
/* 195 */         this.warps.put(warp.getId().toLowerCase(), warp);
/*     */       }
/* 197 */       getLogger().at(Level.INFO).log("Loaded %d warps", bsonWarps.size());
/*     */     } else {
/* 199 */       getLogger().at(Level.INFO).log("Loaded 0 warps (No warps.json found)");
/*     */     } 
/*     */     
/* 202 */     this.loaded.set(true);
/*     */   }
/*     */   
/*     */   private void saveWarps0() {
/* 206 */     Warp[] array = (Warp[])this.warps.values().toArray(x$0 -> new Warp[x$0]);
/* 207 */     BsonDocument document = new BsonDocument("Warps", Warp.ARRAY_CODEC.encode(array));
/*     */     
/* 209 */     Path path = Universe.get().getPath().resolve("warps.json");
/* 210 */     BsonUtil.writeDocument(path, document).join();
/* 211 */     getLogger().at(Level.INFO).log("Saved %d warps to warps.json", array.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWarps() {
/* 218 */     if (this.saveLock.tryLock()) {
/*     */       try {
/* 220 */         saveWarps0();
/* 221 */       } catch (Throwable e) {
/* 222 */         ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to save warps:");
/*     */       } finally {
/* 224 */         this.saveLock.unlock();
/*     */       } 
/* 226 */       if (this.postSaveRedo.getAndSet(false)) {
/* 227 */         saveWarps();
/*     */       }
/*     */     } else {
/* 230 */       this.postSaveRedo.set(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Warp> getWarps() {
/* 238 */     return this.warps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onModelAssetChange(@Nonnull LoadedAssetsEvent<String, ModelAsset, DefaultAssetMap<String, ModelAsset>> event) {
/* 247 */     Map<String, ModelAsset> modelMap = event.getLoadedAssets();
/* 248 */     ModelAsset modelAsset = modelMap.get("Warp");
/* 249 */     if (modelAsset == null)
/* 250 */       return;  this.warpModel = Model.createUnitScaleModel(modelAsset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onChunkPreLoadProcess(@Nonnull ChunkPreLoadProcessEvent event) {
/* 259 */     WorldChunk chunk = event.getChunk();
/* 260 */     BlockChunk blockChunk = chunk.getBlockChunk();
/* 261 */     if (blockChunk == null)
/*     */       return; 
/* 263 */     int chunkX = blockChunk.getX();
/* 264 */     int chunkZ = blockChunk.getZ();
/* 265 */     World world = chunk.getWorld();
/* 266 */     String worldName = world.getName();
/*     */     
/* 268 */     for (Map.Entry<String, Warp> warpEntry : this.warps.entrySet()) {
/* 269 */       Warp warp = warpEntry.getValue();
/* 270 */       Transform transform = warp.getTransform();
/* 271 */       if (transform == null)
/*     */         continue; 
/* 273 */       Vector3d position = transform.getPosition();
/*     */       
/* 275 */       if (!ChunkUtil.isInsideChunk(chunkX, chunkZ, MathUtil.floor(position.x), MathUtil.floor(position.z)) || 
/* 276 */         !warp.getWorld().equals(worldName))
/*     */         continue; 
/* 278 */       world.execute(() -> {
/*     */             Store<EntityStore> store = world.getEntityStore().getStore();
/*     */             store.addEntity(createWarp(warp, store), AddReason.LOAD);
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Holder<EntityStore> createWarp(@Nonnull Warp warp, @Nonnull Store<EntityStore> store) {
/* 291 */     Transform transform = warp.getTransform();
/* 292 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 293 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(transform.getPosition(), transform.getRotation()));
/* 294 */     holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/* 295 */     holder.ensureComponent(Intangible.getComponentType());
/* 296 */     holder.addComponent(BoundingBox.getComponentType(), (Component)new BoundingBox(this.warpModel.getBoundingBox()));
/* 297 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(this.warpModel));
/* 298 */     holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(warp.getId()));
/* 299 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/* 300 */     holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType());
/* 301 */     holder.addComponent(this.warpComponentType, new WarpComponent(warp));
/* 302 */     return holder;
/*     */   }
/*     */   
/*     */   public static final class WarpComponent
/*     */     extends Record implements Component<EntityStore> {
/*     */     private final Warp warp;
/*     */     
/*     */     public WarpComponent(Warp warp) {
/* 310 */       this.warp = warp; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/teleport/TeleportPlugin$WarpComponent;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #310	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 310 */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/teleport/TeleportPlugin$WarpComponent; } public Warp warp() { return this.warp; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/teleport/TeleportPlugin$WarpComponent;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #310	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/teleport/TeleportPlugin$WarpComponent; }
/*     */     public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/teleport/TeleportPlugin$WarpComponent;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #310	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/teleport/TeleportPlugin$WarpComponent;
/*     */       //   0	8	1	o	Ljava/lang/Object; } public static ComponentType<EntityStore, WarpComponent> getComponentType() {
/* 313 */       return (TeleportPlugin.get()).warpComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<EntityStore> clone() {
/* 319 */       return new WarpComponent(this.warp);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class WarpMarkerProvider
/*     */     implements WorldMapManager.MarkerProvider
/*     */   {
/* 332 */     public static final WarpMarkerProvider INSTANCE = new WarpMarkerProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 346 */       Map<String, Warp> warps = TeleportPlugin.get().getWarps();
/* 347 */       if (warps.isEmpty())
/*     */         return; 
/* 349 */       if (!gameplayConfig.getWorldMapConfig().isDisplayWarps())
/*     */         return; 
/* 351 */       for (Warp warp : warps.values()) {
/* 352 */         if (!warp.getWorld().equals(world.getName()))
/*     */           continue; 
/* 354 */         tracker.trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, warp
/* 355 */             .getTransform().getPosition(), warp.getTransform().getRotation().getYaw(), "Warp-" + warp.getId(), "Warp: " + warp.getId(), warp, (id, name, w) -> new MapMarker(id, name, "Warp.png", PositionUtil.toTransformPacket(w.getTransform()), null));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\TeleportPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */