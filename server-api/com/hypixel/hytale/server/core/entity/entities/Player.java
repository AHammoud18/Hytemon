/*      */ package com.hypixel.hytale.server.core.entity.entities;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.component.Component;
/*      */ import com.hypixel.hytale.component.ComponentAccessor;
/*      */ import com.hypixel.hytale.component.Holder;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.component.Store;
/*      */ import com.hypixel.hytale.component.system.EcsEvent;
/*      */ import com.hypixel.hytale.event.IEventDispatcher;
/*      */ import com.hypixel.hytale.math.shape.Box;
/*      */ import com.hypixel.hytale.math.vector.Transform;
/*      */ import com.hypixel.hytale.math.vector.Vector3d;
/*      */ import com.hypixel.hytale.math.vector.Vector3f;
/*      */ import com.hypixel.hytale.metrics.MetricResults;
/*      */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*      */ import com.hypixel.hytale.protocol.GameMode;
/*      */ import com.hypixel.hytale.protocol.InteractionType;
/*      */ import com.hypixel.hytale.protocol.MovementStates;
/*      */ import com.hypixel.hytale.protocol.Packet;
/*      */ import com.hypixel.hytale.protocol.SavedMovementStates;
/*      */ import com.hypixel.hytale.protocol.SoundCategory;
/*      */ import com.hypixel.hytale.protocol.packets.player.SetBlockPlacementOverride;
/*      */ import com.hypixel.hytale.protocol.packets.player.SetGameMode;
/*      */ import com.hypixel.hytale.server.core.HytaleServer;
/*      */ import com.hypixel.hytale.server.core.Message;
/*      */ import com.hypixel.hytale.server.core.asset.type.gamemode.GameModeType;
/*      */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*      */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*      */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*      */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*      */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*      */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.CameraManager;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.HotbarManager;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerRespawnPointData;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.windows.WindowManager;
/*      */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*      */ import com.hypixel.hytale.server.core.event.events.ecs.ChangeGameModeEvent;
/*      */ import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
/*      */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*      */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*      */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*      */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*      */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*      */ import com.hypixel.hytale.server.core.modules.collision.CollisionModule;
/*      */ import com.hypixel.hytale.server.core.modules.collision.CollisionResult;
/*      */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.CollisionResultComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.RespondToHit;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*      */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*      */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*      */ import com.hypixel.hytale.server.core.permissions.PermissionHolder;
/*      */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*      */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*      */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.core.util.TempAssetIdUtil;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.ScheduledFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class Player extends LivingEntity implements CommandSender, PermissionHolder, MetricProvider {
/*      */   @Nonnull
/*   87 */   public static final MetricsRegistry<Player> METRICS_REGISTRY = (new MetricsRegistry())
/*   88 */     .register("Uuid", Entity::getUuid, (Codec)Codec.UUID_STRING)
/*   89 */     .register("ClientViewRadius", Player::getClientViewRadius, (Codec)Codec.INTEGER);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*   95 */   public static final KeyedCodec<PlayerConfigData> PLAYER_CONFIG_DATA = new KeyedCodec("PlayerData", (Codec)PlayerConfigData.CODEC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static final BuilderCodec<Player> CODEC;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int DEFAULT_VIEW_RADIUS_CHUNKS = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  125 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Player.class, Player::new, LivingEntity.CODEC).append(PLAYER_CONFIG_DATA, (player, data) -> player.data = data, player -> player.data).add()).append(new KeyedCodec("BlockPlacementOverride", (Codec)Codec.BOOLEAN), (player, blockPlacementOverride) -> player.overrideBlockPlacementRestrictions = blockPlacementOverride.booleanValue(), player -> Boolean.valueOf(player.overrideBlockPlacementRestrictions)).add()).append(new KeyedCodec("HotbarManager", (Codec)HotbarManager.CODEC), (player, hotbarManager) -> player.hotbarManager = hotbarManager, player -> player.hotbarManager).add()).appendInherited(new KeyedCodec("GameMode", (Codec)ProtocolCodecs.GAMEMODE_LEGACY), (player, s) -> player.gameMode = s, player -> player.gameMode, (player, parent) -> player.gameMode = parent.gameMode).documentation("The last known game-mode of the entity.").add()).build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static ComponentType<EntityStore, Player> getComponentType() {
/*  137 */     return EntityModule.get().getPlayerComponentType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   public static final long RESPAWN_INVULNERABILITY_TIME_NANOS = TimeUnit.MILLISECONDS.toNanos(3000L);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final long MAX_TELEPORT_INVULNERABILITY_MILLIS = 10000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated(forRemoval = true)
/*      */   private PlayerRef playerRef;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  160 */   private PlayerConfigData data = new PlayerConfigData();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  166 */   private final WorldMapTracker worldMapTracker = new WorldMapTracker(this);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  172 */   private final WindowManager windowManager = new WindowManager();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  178 */   private final PageManager pageManager = new PageManager();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  184 */   private final HudManager hudManager = new HudManager();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  190 */   private HotbarManager hotbarManager = new HotbarManager();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GameMode gameMode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  201 */   private int clientViewRadius = 6;
/*      */ 
/*      */   
/*      */   protected long lastSpawnTimeNanos;
/*      */ 
/*      */   
/*      */   private static final int MAX_VELOCITY_SAMPLE_COUNT = 2;
/*      */ 
/*      */   
/*      */   private static final int VELOCITY_SAMPLE_LENGTH = 12;
/*      */ 
/*      */   
/*  213 */   private static final double[][] velocitySampleWeights = new double[][] { { 1.0D }, { 0.9D, 0.1D } };
/*      */   
/*  215 */   private final double[] velocitySamples = new double[12];
/*      */   
/*      */   private int velocitySampleCount;
/*  218 */   private int velocitySampleIndex = 4;
/*      */   
/*      */   private boolean overrideBlockPlacementRestrictions;
/*  221 */   private final AtomicInteger readyId = new AtomicInteger();
/*      */   
/*  223 */   private final AtomicReference<ScheduledFuture<?>> waitingForClientReady = new AtomicReference<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean executeTriggers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean executeBlockDamage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstSpawn;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int mountEntityId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyFrom(@Nonnull Player oldPlayerComponent) {
/*  258 */     init(this.legacyUuid, this.playerRef);
/*      */     
/*  260 */     this.worldMapTracker.copyFrom(oldPlayerComponent.worldMapTracker);
/*      */     
/*  262 */     this.clientViewRadius = oldPlayerComponent.clientViewRadius;
/*      */     
/*  264 */     this.readyId.set(oldPlayerComponent.readyId.get());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void init(@Nonnull UUID uuid, @Nonnull PlayerRef playerRef) {
/*  274 */     this.legacyUuid = uuid;
/*  275 */     this.playerRef = playerRef;
/*  276 */     this.windowManager.init(playerRef);
/*  277 */     this.pageManager.init(playerRef, this.windowManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNetworkId(int id) {
/*  286 */     this.networkId = id;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   protected Inventory createDefaultInventory() {
/*  292 */     return new Inventory();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Inventory setInventory(Inventory inventory) {
/*  299 */     return setInventory(inventory, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove() {
/*  304 */     if (this.wasRemoved.getAndSet(true)) return false;
/*      */     
/*  306 */     this.removedBy = new Throwable();
/*  307 */     if (this.world != null && this.world.isAlive())
/*  308 */       if (this.world.isInThread()) {
/*  309 */         Ref<EntityStore> ref = this.playerRef.getReference();
/*  310 */         if (ref != null) {
/*  311 */           Store<EntityStore> store = ref.getStore();
/*  312 */           ChunkTracker tracker = (ChunkTracker)store.getComponent(ref, ChunkTracker.getComponentType());
/*  313 */           if (tracker != null) tracker.clear(); 
/*  314 */           this.playerRef.removeFromStore();
/*      */         } 
/*      */       } else {
/*  317 */         this.world.execute(() -> {
/*      */               Ref<EntityStore> ref = this.playerRef.getReference();
/*      */               if (ref == null) {
/*      */                 return;
/*      */               }
/*      */               Store<EntityStore> store = ref.getStore();
/*      */               ChunkTracker tracker = (ChunkTracker)store.getComponent(ref, ChunkTracker.getComponentType());
/*      */               if (tracker != null)
/*      */                 tracker.clear(); 
/*      */               this.playerRef.removeFromStore();
/*      */             });
/*      */       }  
/*  329 */     if (this.playerRef.getPacketHandler().getChannel().isActive()) {
/*  330 */       this.playerRef.getPacketHandler().disconnect("Player removed from world!");
/*  331 */       ((HytaleLogger.Api)LOGGER.at(Level.WARNING).withCause(this.removedBy)).log("Player removed from world! %s", this);
/*      */     } 
/*      */     ScheduledFuture<?> task;
/*  334 */     if ((task = this.waitingForClientReady.getAndSet(null)) != null) {
/*  335 */       task.cancel(false);
/*      */     }
/*  337 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void moveTo(@Nonnull Ref<EntityStore> ref, double locX, double locY, double locZ, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  342 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  343 */     assert transformComponent != null;
/*      */     
/*  345 */     Vector3d position = transformComponent.getPosition();
/*  346 */     addLocationChange(ref, locX - position.getX(), locY - position.getY(), locZ - position.getZ(), componentAccessor);
/*      */     
/*  348 */     super.moveTo(ref, locX, locY, locZ, componentAccessor);
/*      */     
/*  350 */     this.windowManager.validateWindows();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public PlayerConfigData getPlayerConfigData() {
/*  358 */     return this.data;
/*      */   }
/*      */ 
/*      */   
/*      */   public void markNeedsSave() {
/*  363 */     this.data.markChanged();
/*      */   }
/*      */ 
/*      */   
/*      */   public void unloadFromWorld() {
/*  368 */     super.unloadFromWorld();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyMovementStates(@Nonnull Ref<EntityStore> ref, @Nonnull SavedMovementStates savedMovementStates, @Nonnull MovementStates movementStates, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  375 */     movementStates.flying = savedMovementStates.flying;
/*      */     
/*  377 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  378 */     assert playerRefComponent != null;
/*      */     
/*  380 */     playerRefComponent.getPacketHandler().writeNoCache((Packet)new SetMovementStates(new SavedMovementStates(movementStates.flying)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void startClientReadyTimeout() {
/*  385 */     ScheduledFuture<?> task = HytaleServer.SCHEDULED_EXECUTOR.schedule(() -> handleClientReady(true), 10000L, TimeUnit.MILLISECONDS);
/*  386 */     ScheduledFuture<?> oldTask = this.waitingForClientReady.getAndSet(task);
/*  387 */     if (oldTask != null) oldTask.cancel(false); 
/*      */   }
/*      */   
/*      */   public void handleClientReady(boolean forced) {
/*      */     ScheduledFuture<?> task;
/*  392 */     if ((task = this.waitingForClientReady.getAndSet(null)) != null) {
/*  393 */       task.cancel(false);
/*      */       
/*  395 */       if (this.world == null)
/*  396 */         return;  IEventDispatcher<PlayerReadyEvent, PlayerReadyEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(PlayerReadyEvent.class, this.world.getName());
/*  397 */       if (dispatcher.hasListener()) dispatcher.dispatch((IBaseEvent)new PlayerReadyEvent(this.reference, this, this.readyId.getAndIncrement())); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void sendInventory() {
/*  402 */     getInventory().consumeIsDirty();
/*  403 */     this.playerRef.getPacketHandler().write((Packet)getInventory().toPacket());
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public CompletableFuture<Void> saveConfig(@Nonnull World world, @Nonnull Holder<EntityStore> holder) {
/*  408 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)holder.getComponent(MovementStatesComponent.getComponentType());
/*  409 */     assert movementStatesComponent != null;
/*      */     
/*  411 */     UUIDComponent uuidComponent = (UUIDComponent)holder.getComponent(UUIDComponent.getComponentType());
/*  412 */     assert uuidComponent != null;
/*      */ 
/*      */     
/*  415 */     this.data.getPerWorldData(world.getName())
/*  416 */       .setLastMovementStates(movementStatesComponent.getMovementStates(), false);
/*  417 */     return Universe.get().getPlayerStorage().save(uuidComponent.getUuid(), holder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated(forRemoval = true)
/*      */   public PacketHandler getPlayerConnection() {
/*  428 */     return this.playerRef.getPacketHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public WorldMapTracker getWorldMapTracker() {
/*  436 */     return this.worldMapTracker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public WindowManager getWindowManager() {
/*  444 */     return this.windowManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public PageManager getPageManager() {
/*  452 */     return this.pageManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public HudManager getHudManager() {
/*  460 */     return this.hudManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public HotbarManager getHotbarManager() {
/*  468 */     return this.hotbarManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFirstSpawn() {
/*  475 */     return this.firstSpawn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFirstSpawn(boolean firstSpawn) {
/*  484 */     this.firstSpawn = firstSpawn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetManagers(@Nonnull Holder<EntityStore> holder) {
/*  493 */     PlayerRef playerRef = this.playerRef;
/*      */     
/*  495 */     LegacyEntityTrackerSystems.clear(this, holder);
/*  496 */     this.worldMapTracker.clear();
/*      */     
/*  498 */     this.windowManager.closeAllWindows();
/*  499 */     this.hudManager.resetUserInterface(this.playerRef);
/*  500 */     this.hudManager.resetHud(this.playerRef);
/*      */     
/*  502 */     CameraManager cameraManagerComponent = (CameraManager)playerRef.getComponent(CameraManager.getComponentType());
/*  503 */     assert cameraManagerComponent != null;
/*  504 */     cameraManagerComponent.resetCamera(playerRef);
/*      */     
/*  506 */     MovementManager movementManagerComponent = (MovementManager)playerRef.getComponent(MovementManager.getComponentType());
/*  507 */     assert movementManagerComponent != null;
/*  508 */     movementManagerComponent.applyDefaultSettings();
/*  509 */     movementManagerComponent.update(playerRef.getPacketHandler());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyPickupItem(@Nonnull Ref<EntityStore> ref, @Nonnull ItemStack itemStack, @Nullable Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  524 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*      */     
/*  526 */     if (world.getGameplayConfig().getShowItemPickupNotifications()) {
/*  527 */       PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  528 */       assert playerRefComponent != null;
/*      */       
/*  530 */       Message itemNameMessage = Message.translation(itemStack.getItem().getTranslationKey());
/*  531 */       NotificationUtil.sendNotification(playerRefComponent
/*  532 */           .getPacketHandler(), 
/*  533 */           Message.translation("server.general.pickedUpItem").param("item", itemNameMessage), null, itemStack
/*      */           
/*  535 */           .toPacket());
/*      */     } 
/*      */     
/*  538 */     if (position != null) {
/*  539 */       SoundUtil.playSoundEvent3dToPlayer(ref, TempAssetIdUtil.getSoundEventIndex("SFX_Player_Pickup_Item"), SoundCategory.UI, position, componentAccessor);
/*      */     } else {
/*  541 */       SoundUtil.playSoundEvent2d(ref, TempAssetIdUtil.getSoundEventIndex("SFX_Player_Pickup_Item"), SoundCategory.UI, componentAccessor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOverrideBlockPlacementRestrictions() {
/*  551 */     return this.overrideBlockPlacementRestrictions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOverrideBlockPlacementRestrictions(@Nonnull Ref<EntityStore> ref, boolean overrideBlockPlacementRestrictions, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  562 */     this.overrideBlockPlacementRestrictions = overrideBlockPlacementRestrictions;
/*      */     
/*  564 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  565 */     assert playerRefComponent != null;
/*      */     
/*  567 */     playerRefComponent.getPacketHandler().writeNoCache((Packet)new SetBlockPlacementOverride(overrideBlockPlacementRestrictions));
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessage(@Nonnull Message message) {
/*  572 */     this.playerRef.sendMessage(message);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasPermission(@Nonnull String id) {
/*  577 */     return PermissionsModule.get().hasPermission(getUuid(), id);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasPermission(@Nonnull String id, boolean def) {
/*  582 */     return PermissionsModule.get().hasPermission(getUuid(), id, def);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLocationChange(@Nonnull Ref<EntityStore> ref, double deltaX, double deltaY, double deltaZ, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  591 */     CollisionResultComponent collisionResultComponent = (CollisionResultComponent)componentAccessor.getComponent(ref, CollisionResultComponent.getComponentType());
/*  592 */     assert collisionResultComponent != null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  598 */     collisionResultComponent.getCollisionPositionOffset().add(deltaX, deltaY, deltaZ);
/*  599 */     if (!collisionResultComponent.isPendingCollisionCheck()) {
/*  600 */       TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  601 */       assert transformComponent != null;
/*      */       
/*  603 */       Vector3d position = transformComponent.getPosition();
/*  604 */       collisionResultComponent.getCollisionStartPosition().assign(position);
/*  605 */       collisionResultComponent.markPendingCollisionCheck();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configTriggerBlockProcessing(boolean triggers, boolean blockDamage, @Nonnull CollisionResultComponent collisionResultComponent) {
/*  619 */     this.executeTriggers = triggers;
/*  620 */     this.executeBlockDamage = blockDamage;
/*      */     
/*  622 */     if (triggers || blockDamage) {
/*  623 */       collisionResultComponent.getCollisionResult().enableTriggerBlocks();
/*      */     } else {
/*  625 */       collisionResultComponent.getCollisionResult().disableTriggerBlocks();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void resetVelocity(@Nonnull Velocity velocity) {
/*  630 */     Arrays.fill(this.velocitySamples, 0.0D);
/*  631 */     this.velocitySampleIndex = 4;
/*  632 */     this.velocitySampleCount = 0;
/*  633 */     velocity.setZero();
/*      */   }
/*      */   
/*      */   public void processVelocitySample(double dt, @Nonnull Vector3d position, @Nonnull Velocity velocity) {
/*  637 */     double x = position.x;
/*  638 */     double y = position.y;
/*  639 */     double z = position.z;
/*      */ 
/*      */ 
/*      */     
/*  643 */     if (dt == 0.0D) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  648 */     this.velocitySamples[this.velocitySampleIndex] = x;
/*  649 */     this.velocitySamples[this.velocitySampleIndex + 1] = y;
/*  650 */     this.velocitySamples[this.velocitySampleIndex + 2] = z;
/*  651 */     this.velocitySamples[this.velocitySampleIndex + 3] = dt;
/*      */     
/*  653 */     int index = this.velocitySampleIndex;
/*      */ 
/*      */     
/*  656 */     this.velocitySampleIndex += 4;
/*  657 */     if (this.velocitySampleIndex >= 12) {
/*  658 */       this.velocitySampleIndex = 4;
/*      */     }
/*  660 */     if (this.velocitySampleCount < 2) {
/*  661 */       this.velocitySampleCount++;
/*      */     }
/*      */     
/*  664 */     if (this.velocitySampleCount < 2) {
/*  665 */       velocity.setZero();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  670 */     for (int i = 0; i < 4; i++) {
/*  671 */       this.velocitySamples[i] = 0.0D;
/*      */     }
/*      */     
/*  674 */     double[] weights = velocitySampleWeights[this.velocitySampleCount - 2];
/*      */ 
/*      */     
/*  677 */     for (int j = 0; j < this.velocitySampleCount - 1; j++) {
/*  678 */       int previousIndex = index - 4;
/*  679 */       if (previousIndex < 4) {
/*  680 */         previousIndex = 8;
/*      */       }
/*  682 */       double k = weights[j] / this.velocitySamples[index + 3];
/*  683 */       this.velocitySamples[0] = this.velocitySamples[0] + k * (this.velocitySamples[index] - this.velocitySamples[previousIndex]);
/*  684 */       this.velocitySamples[1] = this.velocitySamples[1] + k * (this.velocitySamples[index + 1] - this.velocitySamples[previousIndex + 1]);
/*  685 */       this.velocitySamples[2] = this.velocitySamples[2] + k * (this.velocitySamples[index + 2] - this.velocitySamples[previousIndex + 2]);
/*  686 */       index = previousIndex;
/*      */     } 
/*      */     
/*  689 */     velocity.set(this.velocitySamples[0], this.velocitySamples[1], this.velocitySamples[2]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Transform getRespawnPosition(@Nonnull Ref<EntityStore> ref, @Nonnull String worldName, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  702 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, getComponentType());
/*  703 */     assert playerComponent != null;
/*      */     
/*  705 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  706 */     PlayerConfigData playerConfigData = playerComponent.data;
/*      */     
/*  708 */     PlayerRespawnPointData[] respawnPoints = playerConfigData.getPerWorldData(worldName).getRespawnPoints();
/*  709 */     if (respawnPoints == null || respawnPoints.length == 0) {
/*      */       
/*  711 */       Transform transform = world.getWorldConfig().getSpawnProvider().getSpawnPoint(ref, componentAccessor);
/*  712 */       transform.setRotation(Vector3f.ZERO);
/*  713 */       return transform;
/*      */     } 
/*      */     
/*  716 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  717 */     assert transformComponent != null;
/*      */     
/*  719 */     Vector3d playerPosition = transformComponent.getPosition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  726 */     List<PlayerRespawnPointData> sortedRespawnPoints = Arrays.<PlayerRespawnPointData>stream(respawnPoints).sorted((a, b) -> { Vector3d posA = a.getRespawnPosition(); Vector3d posB = b.getRespawnPosition(); double distA = playerPosition.distanceSquaredTo(posA.x, playerPosition.y, posA.z); double distB = playerPosition.distanceSquaredTo(posB.x, playerPosition.y, posB.z); return Double.compare(distA, distB); }).toList();
/*      */     
/*  728 */     BoundingBox playerBoundingBoxComponent = (BoundingBox)componentAccessor.getComponent(ref, BoundingBox.getComponentType());
/*  729 */     if (playerBoundingBoxComponent == null) return new Transform(((PlayerRespawnPointData)sortedRespawnPoints.getFirst()).getRespawnPosition());
/*      */     
/*  731 */     for (PlayerRespawnPointData respawnPoint : sortedRespawnPoints) {
/*  732 */       Pair<Boolean, Vector3d> respawnPointResult = ensureNoCollisionAtRespawnPosition(respawnPoint, playerBoundingBoxComponent.getBoundingBox(), world);
/*      */       
/*  734 */       if (((Boolean)respawnPointResult.left()).booleanValue())
/*      */       {
/*  736 */         return new Transform((Vector3d)respawnPointResult.right(), Vector3f.ZERO);
/*      */       }
/*  738 */       playerComponent.sendMessage(Message.translation("server.general.respawnPointObstructed").param("respawnPointName", respawnPoint.getName()));
/*      */     } 
/*      */ 
/*      */     
/*  742 */     playerComponent.sendMessage(Message.translation("server.general.allRespawnPointsObstructed"));
/*      */ 
/*      */     
/*  745 */     Transform worldSpawnPoint = world.getWorldConfig().getSpawnProvider().getSpawnPoint(ref, componentAccessor);
/*  746 */     worldSpawnPoint.setRotation(Vector3f.ZERO);
/*  747 */     return worldSpawnPoint;
/*      */   }
/*      */   
/*      */   private static Pair<Boolean, Vector3d> ensureNoCollisionAtRespawnPosition(PlayerRespawnPointData playerRespawnPointData, Box playerHitbox, World world) {
/*  751 */     Vector3d respawnPosition = new Vector3d(playerRespawnPointData.getRespawnPosition());
/*  752 */     if (CollisionModule.get().validatePosition(world, playerHitbox, respawnPosition, new CollisionResult()) != -1) {
/*  753 */       return Pair.of(Boolean.TRUE, respawnPosition);
/*      */     }
/*      */ 
/*      */     
/*  757 */     respawnPosition.x = ((playerRespawnPointData.getBlockPosition()).x + 0.5F);
/*  758 */     respawnPosition.y = (playerRespawnPointData.getBlockPosition()).y;
/*  759 */     respawnPosition.z = ((playerRespawnPointData.getBlockPosition()).z + 0.5F);
/*      */ 
/*      */ 
/*      */     
/*  763 */     for (int distance = 1; distance <= 2; distance++) {
/*  764 */       int offset; for (offset = -distance; offset <= distance; offset++) {
/*      */         
/*  766 */         Vector3d newPosition = new Vector3d(respawnPosition.x + offset, respawnPosition.y, respawnPosition.z - distance);
/*  767 */         if (CollisionModule.get().validatePosition(world, playerHitbox, newPosition, new CollisionResult()) != -1) {
/*  768 */           return Pair.of(Boolean.TRUE, newPosition);
/*      */         }
/*      */ 
/*      */         
/*  772 */         newPosition = new Vector3d(respawnPosition.x + offset, respawnPosition.y, respawnPosition.z + distance);
/*  773 */         if (CollisionModule.get().validatePosition(world, playerHitbox, newPosition, new CollisionResult()) != -1) {
/*  774 */           return Pair.of(Boolean.TRUE, newPosition);
/*      */         }
/*      */       } 
/*      */       
/*  778 */       for (offset = -distance + 1; offset < distance; offset++) {
/*      */         
/*  780 */         Vector3d newPosition = new Vector3d(respawnPosition.x - distance, respawnPosition.y, respawnPosition.z + offset);
/*  781 */         if (CollisionModule.get().validatePosition(world, playerHitbox, newPosition, new CollisionResult()) != -1) {
/*  782 */           return Pair.of(Boolean.TRUE, newPosition);
/*      */         }
/*      */ 
/*      */         
/*  786 */         newPosition = new Vector3d(respawnPosition.x + distance, respawnPosition.y, respawnPosition.z + offset);
/*  787 */         if (CollisionModule.get().validatePosition(world, playerHitbox, newPosition, new CollisionResult()) != -1) {
/*  788 */           return Pair.of(Boolean.TRUE, newPosition);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  793 */     return Pair.of(Boolean.FALSE, respawnPosition);
/*      */   }
/*      */   
/*      */   public boolean hasSpawnProtection() {
/*  797 */     return (System.nanoTime() - this.lastSpawnTimeNanos <= RESPAWN_INVULNERABILITY_TIME_NANOS || this.waitingForClientReady
/*  798 */       .get() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWaitingForClientReady() {
/*  805 */     return (this.waitingForClientReady.get() != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHiddenFromLivingEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  810 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/*  811 */     assert uuidComponent != null;
/*      */     
/*  813 */     PlayerRef targetPlayerComponent = (PlayerRef)componentAccessor.getComponent(targetRef, PlayerRef.getComponentType());
/*  814 */     return (targetPlayerComponent != null && targetPlayerComponent.getHiddenPlayersManager().isPlayerHidden(uuidComponent.getUuid()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientViewRadius(int clientViewRadius) {
/*  823 */     this.clientViewRadius = clientViewRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getClientViewRadius() {
/*  830 */     return this.clientViewRadius;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewRadius() {
/*  837 */     return Math.min(this.clientViewRadius, HytaleServer.get().getConfig().getMaxViewRadius());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canDecreaseItemStackDurability(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  842 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, getComponentType());
/*  843 */     assert playerComponent != null;
/*      */     
/*  845 */     return (playerComponent.gameMode != GameMode.Creative);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canApplyItemStackPenalties(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  850 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, getComponentType());
/*  851 */     assert playerComponent != null;
/*      */     
/*  853 */     return (playerComponent.gameMode != GameMode.Creative);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ItemStackSlotTransaction updateItemStackDurability(@Nonnull Ref<EntityStore> ref, @Nonnull ItemStack itemStack, ItemContainer container, int slotId, double durabilityChange, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  864 */     ItemStackSlotTransaction transaction = super.updateItemStackDurability(ref, itemStack, container, slotId, durabilityChange, componentAccessor);
/*  865 */     if (transaction != null && transaction.getSlotAfter().isBroken() && !itemStack.isBroken()) {
/*  866 */       Message itemNameMessage = Message.translation(itemStack.getItem().getTranslationKey());
/*  867 */       sendMessage(Message.translation("server.general.repair.itemBroken").param("itemName", itemNameMessage).color("#ff5555"));
/*      */       
/*  869 */       PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  870 */       assert playerRefComponent != null;
/*      */       
/*  872 */       int soundEventIndex = TempAssetIdUtil.getSoundEventIndex("SFX_Item_Break");
/*  873 */       SoundUtil.playSoundEvent2dToPlayer(playerRefComponent, soundEventIndex, SoundCategory.SFX);
/*      */     } 
/*  875 */     return transaction;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public MetricResults toMetricResults() {
/*  881 */     return METRICS_REGISTRY.toMetricResults(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLastSpawnTimeNanos(long lastSpawnTimeNanos) {
/*  890 */     this.lastSpawnTimeNanos = lastSpawnTimeNanos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSinceLastSpawnNanos() {
/*  897 */     return System.nanoTime() - this.lastSpawnTimeNanos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated(forRemoval = true)
/*      */   public PlayerRef getPlayerRef() {
/*  907 */     return this.playerRef;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMountEntityId() {
/*  914 */     return this.mountEntityId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMountEntityId(int mountEntityId) {
/*  923 */     this.mountEntityId = mountEntityId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameMode getGameMode() {
/*  930 */     return this.gameMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setGameMode(@Nonnull Ref<EntityStore> playerRef, @Nonnull GameMode gameMode, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  943 */     MovementManager movementManagerComponent = (MovementManager)componentAccessor.getComponent(playerRef, MovementManager.getComponentType());
/*  944 */     assert movementManagerComponent != null;
/*      */     
/*  946 */     Player playerComponent = (Player)componentAccessor.getComponent(playerRef, getComponentType());
/*  947 */     assert playerComponent != null;
/*      */     
/*  949 */     GameMode oldGameMode = playerComponent.gameMode;
/*  950 */     if (oldGameMode != gameMode) {
/*  951 */       ChangeGameModeEvent event = new ChangeGameModeEvent(gameMode);
/*  952 */       componentAccessor.invoke(playerRef, (EcsEvent)event);
/*  953 */       if (event.isCancelled())
/*      */         return; 
/*  955 */       setGameModeInternal(playerRef, event.getGameMode(), movementManagerComponent, componentAccessor);
/*  956 */       runOnSwitchToGameMode(playerRef, gameMode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void initGameMode(@Nonnull Ref<EntityStore> playerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  969 */     MovementManager movementManagerComponent = (MovementManager)componentAccessor.getComponent(playerRef, MovementManager.getComponentType());
/*  970 */     assert movementManagerComponent != null;
/*      */     
/*  972 */     Player playerComponent = (Player)componentAccessor.getComponent(playerRef, getComponentType());
/*  973 */     assert playerComponent != null;
/*      */ 
/*      */     
/*  976 */     GameMode gameMode = playerComponent.gameMode;
/*  977 */     if (gameMode == null) {
/*  978 */       World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  979 */       gameMode = world.getWorldConfig().getGameMode();
/*  980 */       LOGGER.at(Level.INFO).log("Assigning default gamemode %s to player!", gameMode);
/*      */     } 
/*      */     
/*  983 */     setGameModeInternal(playerRef, gameMode, movementManagerComponent, componentAccessor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setGameModeInternal(@Nonnull Ref<EntityStore> playerRef, @Nonnull GameMode gameMode, @Nonnull MovementManager movementManager, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  998 */     Player playerComponent = (Player)componentAccessor.getComponent(playerRef, getComponentType());
/*  999 */     assert playerComponent != null;
/*      */     
/* 1001 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/* 1002 */     assert playerRefComponent != null;
/*      */     
/* 1004 */     GameMode oldGameMode = playerComponent.gameMode;
/* 1005 */     playerComponent.gameMode = gameMode;
/* 1006 */     playerRefComponent.getPacketHandler().writeNoCache((Packet)new SetGameMode(gameMode));
/*      */ 
/*      */ 
/*      */     
/* 1010 */     if (movementManager.getDefaultSettings() != null) {
/* 1011 */       (movementManager.getDefaultSettings()).canFly = (gameMode == GameMode.Creative);
/* 1012 */       (movementManager.getSettings()).canFly = (gameMode == GameMode.Creative);
/* 1013 */       movementManager.update(playerRefComponent.getPacketHandler());
/*      */     } 
/*      */     
/* 1016 */     PermissionsModule permissionsModule = PermissionsModule.get();
/* 1017 */     if (oldGameMode != null) {
/* 1018 */       GameModeType oldGameModeType = GameModeType.fromGameMode(oldGameMode);
/* 1019 */       for (String group : oldGameModeType.getPermissionGroups()) {
/* 1020 */         permissionsModule.removeUserFromGroup(playerRefComponent.getUuid(), group);
/*      */       }
/*      */     } 
/*      */     
/* 1024 */     GameModeType gameModeType = GameModeType.fromGameMode(gameMode);
/* 1025 */     for (String group : gameModeType.getPermissionGroups()) {
/* 1026 */       permissionsModule.addUserToGroup(playerRefComponent.getUuid(), group);
/*      */     }
/*      */ 
/*      */     
/* 1030 */     if (gameMode == GameMode.Creative) {
/* 1031 */       componentAccessor.putComponent(playerRef, Invulnerable.getComponentType(), (Component)Invulnerable.INSTANCE);
/*      */     } else {
/* 1033 */       componentAccessor.tryRemoveComponent(playerRef, Invulnerable.getComponentType());
/*      */     } 
/*      */     
/* 1036 */     if (gameMode == GameMode.Creative) {
/* 1037 */       PlayerSettings settings = (PlayerSettings)componentAccessor.getComponent(playerRef, PlayerSettings.getComponentType());
/* 1038 */       if (settings == null) settings = PlayerSettings.defaults(); 
/* 1039 */       if (settings.creativeSettings().respondToHit()) {
/* 1040 */         componentAccessor.putComponent(playerRef, RespondToHit.getComponentType(), (Component)RespondToHit.INSTANCE);
/*      */       } else {
/* 1042 */         componentAccessor.tryRemoveComponent(playerRef, RespondToHit.getComponentType());
/*      */       } 
/*      */     } else {
/* 1045 */       componentAccessor.tryRemoveComponent(playerRef, RespondToHit.getComponentType());
/*      */     } 
/*      */ 
/*      */     
/* 1049 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 1050 */     playerComponent.worldMapTracker.sendSettings(world);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void runOnSwitchToGameMode(@Nonnull Ref<EntityStore> ref, @Nonnull GameMode gameMode) {
/* 1062 */     Store<EntityStore> store = ref.getStore();
/* 1063 */     GameModeType gameModeType = GameModeType.fromGameMode(gameMode);
/*      */     
/* 1065 */     InteractionManager interactionManagerComponent = (InteractionManager)store.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
/* 1066 */     if (interactionManagerComponent == null)
/*      */       return; 
/* 1068 */     String interactions = gameModeType.getInteractionsOnEnter();
/* 1069 */     if (interactions == null)
/*      */       return; 
/* 1071 */     InteractionContext context = InteractionContext.forInteraction(interactionManagerComponent, ref, InteractionType.GameModeSwap, (ComponentAccessor)store);
/* 1072 */     RootInteraction rootInteraction = RootInteraction.getRootInteractionOrUnknown(interactions);
/* 1073 */     if (rootInteraction == null)
/*      */       return; 
/* 1075 */     InteractionChain chain = interactionManagerComponent.initChain(InteractionType.EntityStatEffect, context, rootInteraction, true);
/* 1076 */     interactionManagerComponent.queueExecuteChain(chain);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1081 */     int result = super.hashCode();
/* 1082 */     result = 31 * result + ((getUuid() != null) ? getUuid().hashCode() : 0);
/* 1083 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(@Nullable Object o) {
/* 1088 */     if (this == o) return true; 
/* 1089 */     if (o == null || getClass() != o.getClass()) return false; 
/* 1090 */     if (!super.equals(o)) return false;
/*      */     
/* 1092 */     Player player = (Player)o;
/*      */     
/* 1094 */     return (getUuid() != null) ? getUuid().equals(player.getUuid()) : ((player.getUuid() == null));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public String toString() {
/* 1101 */     return "Player{uuid=" + String.valueOf(getUuid()) + ", clientViewRadius='" + this.clientViewRadius + "', " + super
/*      */       
/* 1103 */       .toString() + "}";
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDisplayName() {
/* 1108 */     return this.playerRef.getUsername();
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\Player.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */