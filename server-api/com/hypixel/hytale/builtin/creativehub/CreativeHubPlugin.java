/*     */ package com.hypixel.hytale.builtin.creativehub;
/*     */ import com.hypixel.hytale.builtin.creativehub.command.HubCommand;
/*     */ import com.hypixel.hytale.builtin.creativehub.config.CreativeHubEntityConfig;
/*     */ import com.hypixel.hytale.builtin.creativehub.config.CreativeHubWorldConfig;
/*     */ import com.hypixel.hytale.builtin.creativehub.interactions.HubPortalInteraction;
/*     */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*     */ import com.hypixel.hytale.builtin.instances.config.InstanceEntityConfig;
/*     */ import com.hypixel.hytale.builtin.instances.config.InstanceWorldConfig;
/*     */ import com.hypixel.hytale.builtin.instances.config.WorldReturnPoint;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.RemoveWorldEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.io.FileUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CreativeHubPlugin extends JavaPlugin {
/*     */   @Nonnull
/*  49 */   private static final Message MESSAGE_HUB_RETURN_HINT = Message.translation("server.creativehub.portal.returnHint");
/*     */ 
/*     */   
/*     */   private static CreativeHubPlugin instance;
/*     */   
/*  54 */   private final Map<UUID, World> activeHubInstances = new ConcurrentHashMap<>();
/*     */   
/*     */   private ComponentType<EntityStore, CreativeHubEntityConfig> creativeHubEntityConfigComponentType;
/*     */ 
/*     */   
/*     */   public static CreativeHubPlugin get() {
/*  60 */     return instance;
/*     */   }
/*     */   
/*     */   public CreativeHubPlugin(@Nonnull JavaPluginInit init) {
/*  64 */     super(init);
/*  65 */     instance = this;
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
/*     */   public World getOrSpawnHubInstance(@Nonnull World parentWorld, @Nonnull CreativeHubWorldConfig hubConfig, @Nonnull Transform returnPoint) {
/*  82 */     UUID parentUuid = parentWorld.getWorldConfig().getUuid();
/*  83 */     return this.activeHubInstances.compute(parentUuid, (uuid, existingInstance) -> {
/*     */           if (existingInstance != null && existingInstance.isAlive()) {
/*     */             return existingInstance;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*     */             return InstancesPlugin.get().spawnInstance(hubConfig.getStartupInstance(), parentWorld, returnPoint).join();
/*  93 */           } catch (Exception e) {
/*     */             ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to spawn hub instance");
/*     */             throw new RuntimeException("Failed to spawn hub instance", e);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public World getActiveHubInstance(@Nonnull World parentWorld) {
/* 108 */     World hubInstance = this.activeHubInstances.get(parentWorld.getWorldConfig().getUuid());
/* 109 */     if (hubInstance != null && hubInstance.isAlive()) {
/* 110 */       return hubInstance;
/*     */     }
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearHubInstance(@Nonnull UUID parentWorldUuid) {
/* 122 */     this.activeHubInstances.remove(parentWorldUuid);
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
/*     */   @Nonnull
/*     */   public CompletableFuture<World> spawnPermanentWorldFromTemplate(@Nonnull String instanceAssetName, @Nonnull String permanentWorldName) {
/* 142 */     Universe universe = Universe.get();
/*     */ 
/*     */     
/* 145 */     World existingWorld = universe.getWorld(permanentWorldName);
/* 146 */     if (existingWorld != null) {
/* 147 */       return CompletableFuture.completedFuture(existingWorld);
/*     */     }
/*     */ 
/*     */     
/* 151 */     if (universe.isWorldLoadable(permanentWorldName)) {
/* 152 */       return universe.loadWorld(permanentWorldName);
/*     */     }
/*     */ 
/*     */     
/* 156 */     Path assetPath = InstancesPlugin.getInstanceAssetPath(instanceAssetName);
/* 157 */     Path worldPath = universe.getPath().resolve("worlds").resolve(permanentWorldName);
/*     */     
/* 159 */     return WorldConfig.load(assetPath.resolve("instance.bson"))
/* 160 */       .thenApplyAsync(SneakyThrow.sneakyFunction(config -> {
/*     */             config.setUuid(UUID.randomUUID());
/*     */             config.setDeleteOnRemove(false);
/*     */             config.setDisplayName(WorldConfig.formatDisplayName(instanceAssetName));
/*     */             config.getPluginConfig().remove(InstanceWorldConfig.class);
/*     */             config.markChanged();
/*     */             long start = System.nanoTime();
/*     */             getLogger().at(Level.INFO).log("Copying instance template %s to permanent world %s", instanceAssetName, permanentWorldName);
/*     */             Stream<Path> files = Files.walk(assetPath, FileUtil.DEFAULT_WALK_TREE_OPTIONS_ARRAY);
/*     */             
/*     */             try { files.forEach(SneakyThrow.sneakyConsumer(()));
/*     */               if (files != null) {
/*     */                 files.close();
/*     */               } }
/* 174 */             catch (Throwable t$) { if (files != null) try { files.close(); } catch (Throwable x2)
/*     */                 { t$.addSuppressed(x2); }
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               throw t$; }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             getLogger().at(Level.INFO).log("Completed copying instance template %s to permanent world %s in %s", instanceAssetName, permanentWorldName, FormatUtil.nanosToString(System.nanoTime() - start));
/*     */ 
/*     */ 
/*     */             
/*     */             return config;
/* 192 */           })).thenCompose(config -> universe.makeWorld(permanentWorldName, worldPath, config));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, CreativeHubEntityConfig> getCreativeHubEntityConfigComponentType() {
/* 200 */     return this.creativeHubEntityConfigComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/* 205 */     getCommandRegistry().registerCommand((AbstractCommand)new HubCommand());
/*     */     
/* 207 */     getCodecRegistry(Interaction.CODEC)
/* 208 */       .register("HubPortal", HubPortalInteraction.class, HubPortalInteraction.CODEC);
/*     */     
/* 210 */     getCodecRegistry(WorldConfig.PLUGIN_CODEC)
/* 211 */       .register(CreativeHubWorldConfig.class, "CreativeHub", (Codec)CreativeHubWorldConfig.CODEC);
/*     */ 
/*     */     
/* 214 */     this.creativeHubEntityConfigComponentType = getEntityStoreRegistry().registerComponent(CreativeHubEntityConfig.class, "CreativeHub", CreativeHubEntityConfig.CODEC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     getEventRegistry().registerGlobal(PlayerConnectEvent.class, CreativeHubPlugin::onPlayerConnect);
/*     */ 
/*     */     
/* 224 */     getEventRegistry().registerGlobal(RemoveWorldEvent.class, CreativeHubPlugin::onWorldRemove);
/*     */ 
/*     */     
/* 227 */     getEventRegistry().registerGlobal(AddPlayerToWorldEvent.class, CreativeHubPlugin::onPlayerAddToWorld);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onWorldRemove(@Nonnull RemoveWorldEvent event) {
/* 236 */     World world = event.getWorld();
/* 237 */     UUID worldUuid = world.getWorldConfig().getUuid();
/*     */ 
/*     */     
/* 240 */     (get()).activeHubInstances.entrySet().removeIf(entry -> {
/*     */           World hubInstance = (World)entry.getValue();
/* 242 */           return (hubInstance != null && hubInstance.getWorldConfig().getUuid().equals(worldUuid));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void onPlayerConnect(@Nonnull PlayerConnectEvent event) {
/* 254 */     World targetWorld = event.getWorld();
/* 255 */     Holder<EntityStore> holder = event.getHolder();
/*     */ 
/*     */     
/* 258 */     CreativeHubEntityConfig existingHubConfig = CreativeHubEntityConfig.get(holder);
/* 259 */     if (existingHubConfig != null && existingHubConfig.getParentHubWorldUuid() != null) {
/* 260 */       World parentWorld = Universe.get().getWorld(existingHubConfig.getParentHubWorldUuid());
/* 261 */       if (parentWorld != null) {
/* 262 */         CreativeHubWorldConfig parentHubConfig = CreativeHubWorldConfig.get(parentWorld.getWorldConfig());
/* 263 */         if (parentHubConfig != null && parentHubConfig.getStartupInstance() != null)
/*     */         {
/* 265 */           if (targetWorld == null) {
/* 266 */             event.setWorld(parentWorld);
/* 267 */             targetWorld = parentWorld;
/*     */             
/* 269 */             holder.removeComponent(TransformComponent.getComponentType());
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 275 */     if (targetWorld == null) {
/*     */       return;
/*     */     }
/*     */     
/* 279 */     WorldConfig worldConfig = targetWorld.getWorldConfig();
/* 280 */     CreativeHubWorldConfig hubConfig = CreativeHubWorldConfig.get(worldConfig);
/* 281 */     if (hubConfig == null || hubConfig.getStartupInstance() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 285 */     PlayerRef playerRef = event.getPlayerRef();
/*     */ 
/*     */     
/* 288 */     ISpawnProvider spawnProvider = worldConfig.getSpawnProvider();
/*     */ 
/*     */     
/* 291 */     Transform returnPoint = (spawnProvider != null) ? spawnProvider.getSpawnPoint(targetWorld, playerRef.getUuid()) : new Transform();
/*     */ 
/*     */     
/*     */     try {
/* 295 */       World hubInstance = get().getOrSpawnHubInstance(targetWorld, hubConfig, returnPoint);
/*     */ 
/*     */       
/* 298 */       InstanceEntityConfig instanceConfig = InstanceEntityConfig.ensureAndGet(holder);
/* 299 */       instanceConfig.setReturnPoint(new WorldReturnPoint(targetWorld
/* 300 */             .getWorldConfig().getUuid(), returnPoint, false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       CreativeHubEntityConfig hubEntityConfig = CreativeHubEntityConfig.ensureAndGet(holder);
/* 307 */       hubEntityConfig.setParentHubWorldUuid(targetWorld.getWorldConfig().getUuid());
/*     */ 
/*     */       
/* 310 */       event.setWorld(hubInstance);
/*     */     }
/* 312 */     catch (Exception e) {
/* 313 */       ((HytaleLogger.Api)get().getLogger().at(Level.SEVERE).withCause(e))
/* 314 */         .log("Failed to get/spawn hub instance for player %s, falling back to default world", playerRef.getUuid());
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
/*     */   private static void onPlayerAddToWorld(@Nonnull AddPlayerToWorldEvent event) {
/* 327 */     Holder<EntityStore> holder = event.getHolder();
/* 328 */     World world = event.getWorld();
/*     */ 
/*     */     
/* 331 */     CreativeHubEntityConfig hubEntityConfig = (CreativeHubEntityConfig)holder.getComponent(CreativeHubEntityConfig.getComponentType());
/* 332 */     if (hubEntityConfig == null || hubEntityConfig.getParentHubWorldUuid() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 337 */     World parentWorld = Universe.get().getWorld(hubEntityConfig.getParentHubWorldUuid());
/* 338 */     if (parentWorld == null) {
/*     */       return;
/*     */     }
/*     */     
/* 342 */     World hubInstance = get().getActiveHubInstance(parentWorld);
/* 343 */     if (hubInstance != null && world.equals(hubInstance)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 348 */     PlayerRef playerRef = (PlayerRef)holder.getComponent(PlayerRef.getComponentType());
/* 349 */     if (playerRef != null)
/* 350 */       world.execute(() -> playerRef.sendMessage(MESSAGE_HUB_RETURN_HINT)); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\creativehub\CreativeHubPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */