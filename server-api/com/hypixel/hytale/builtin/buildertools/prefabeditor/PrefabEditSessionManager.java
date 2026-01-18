/*      */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*      */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabRowSplitMode;
/*      */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.enums.PrefabStackingAxis;
/*      */ import com.hypixel.hytale.common.map.IWeightedMap;
/*      */ import com.hypixel.hytale.component.Component;
/*      */ import com.hypixel.hytale.component.ComponentAccessor;
/*      */ import com.hypixel.hytale.component.Holder;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.component.Store;
/*      */ import com.hypixel.hytale.event.EventRegistry;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.math.vector.Transform;
/*      */ import com.hypixel.hytale.math.vector.Vector3i;
/*      */ import com.hypixel.hytale.protocol.Color;
/*      */ import com.hypixel.hytale.protocol.MovementStates;
/*      */ import com.hypixel.hytale.protocol.Packet;
/*      */ import com.hypixel.hytale.protocol.SavedMovementStates;
/*      */ import com.hypixel.hytale.protocol.packets.inventory.SetActiveSlot;
/*      */ import com.hypixel.hytale.server.core.Message;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*      */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*      */ import com.hypixel.hytale.server.core.asset.type.environment.config.WeatherForecast;
/*      */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*      */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*      */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*      */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*      */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*      */ import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
/*      */ import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
/*      */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*      */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*      */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*      */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*      */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*      */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferUtil;
/*      */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.IPrefabBuffer;
/*      */ import com.hypixel.hytale.server.core.prefab.selection.buffer.impl.PrefabBuffer;
/*      */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*      */ import com.hypixel.hytale.server.core.universe.Universe;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*      */ import com.hypixel.hytale.server.core.universe.world.spawn.GlobalSpawnProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.FlatWorldGenProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*      */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.VoidWorldGenProvider;
/*      */ import com.hypixel.hytale.server.core.util.PrefabUtil;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.CompletionStage;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class PrefabEditSessionManager {
/*      */   @Nonnull
/*   75 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*      */   
/*      */   @Nonnull
/*   78 */   private static final Message MESSAGE_COMMANDS_PREFAB_EDIT_SESSION_MANAGER_EXISTING_EDIT_SESSION = Message.translation("server.commands.prefabeditsessionmanager.existingEditSession");
/*      */   @Nonnull
/*   80 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_SOMETHING_WENT_WRONG = Message.translation("server.commands.editprefab.somethingWentWrong");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final float NOON_TIME = 0.5F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DEFAULT_NEW_WORLD_ZERO_COORDINATE_BLOCK_NAME = "Rock_Stone";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DEFAULT_ENVIRONMENT = "Zone1_Sunny";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PREFAB_SELECTOR_TOOL_ID = "EditorTool_PrefabEditing_SelectPrefab";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DEFAULT_CHUNK_ENVIRONMENT = "Env_Zone1_Plains";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String PREFAB_EDITING_WORLD_NAME_PREFIX = "prefabEditor-";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  117 */   public static final Color DEFAULT_TINT = new Color((byte)91, (byte)-98, (byte)40);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long PROGRESS_UPDATE_INTERVAL_NANOS = 100000000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String DEFAULT_GRASS_TINT_HEX = "#5B9E28";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  133 */   private final Map<UUID, PrefabEditSession> activeEditSessions = (Map<UUID, PrefabEditSession>)new Object2ObjectOpenHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  140 */   private final HashSet<Path> prefabsBeingEdited = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  149 */   private final Map<UUID, UUID> inProgressTeleportations = (Map<UUID, UUID>)new Object2ObjectOpenHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  156 */   private final HashSet<UUID> inProgressLoading = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*  163 */   private final HashSet<UUID> cancelledLoading = new HashSet<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrefabEditSessionManager(@Nonnull JavaPlugin plugin) {
/*  172 */     EventRegistry eventRegistry = plugin.getEventRegistry();
/*      */     
/*  174 */     eventRegistry.registerGlobal(AddPlayerToWorldEvent.class, this::onPlayerAddedToWorld);
/*  175 */     eventRegistry.registerGlobal(PlayerReadyEvent.class, this::onPlayerReady);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onPlayerReady(@Nonnull PlayerReadyEvent event) {
/*  185 */     Ref<EntityStore> playerRef = event.getPlayer().getReference();
/*  186 */     assert playerRef != null && !playerRef.isValid();
/*      */     
/*  188 */     Store<EntityStore> store = playerRef.getStore();
/*  189 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  191 */     world.execute(() -> {
/*      */           UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(playerRef, UUIDComponent.getComponentType());
/*      */           assert uuidComponent != null;
/*      */           UUID playerUUID = uuidComponent.getUuid();
/*      */           if (!this.inProgressTeleportations.containsKey(playerUUID)) {
/*      */             return;
/*      */           }
/*      */           this.inProgressTeleportations.remove(playerUUID);
/*      */           MovementStatesComponent movementStatesComponent = (MovementStatesComponent)store.getComponent(playerRef, MovementStatesComponent.getComponentType());
/*      */           assert movementStatesComponent != null;
/*      */           MovementStates movementStates = movementStatesComponent.getMovementStates();
/*      */           Player playerComponent = (Player)store.getComponent(playerRef, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           playerComponent.applyMovementStates(playerRef, new SavedMovementStates(true), movementStates, (ComponentAccessor)store);
/*      */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(playerRef, PlayerRef.getComponentType());
/*      */           if (playerRefComponent != null) {
/*      */             givePrefabSelectorTool(playerComponent, playerRefComponent);
/*      */           }
/*      */         });
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void givePrefabSelectorTool(@Nonnull Player playerComponent, @Nonnull PlayerRef playerRef) {
/*  231 */     Inventory inventory = playerComponent.getInventory();
/*  232 */     ItemContainer hotbar = inventory.getHotbar();
/*      */     
/*  234 */     int hotbarSize = hotbar.getCapacity();
/*      */     
/*      */     short slot;
/*  237 */     for (slot = 0; slot < hotbarSize; slot = (short)(slot + 1)) {
/*  238 */       ItemStack itemStack = hotbar.getItemStack(slot);
/*  239 */       if (itemStack != null && !itemStack.isEmpty() && 
/*  240 */         "EditorTool_PrefabEditing_SelectPrefab".equals(itemStack.getItemId())) {
/*      */         
/*  242 */         inventory.setActiveHotbarSlot((byte)slot);
/*  243 */         playerRef.getPacketHandler().writeNoCache((Packet)new SetActiveSlot(-1, (byte)slot));
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  251 */     short emptySlot = -1; short s1;
/*  252 */     for (s1 = 0; s1 < hotbarSize; s1 = (short)(s1 + 1)) {
/*  253 */       ItemStack itemStack = hotbar.getItemStack(s1);
/*  254 */       if (itemStack == null || itemStack.isEmpty()) {
/*  255 */         emptySlot = s1;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  260 */     if (emptySlot == -1)
/*      */     {
/*  262 */       emptySlot = 0;
/*      */     }
/*      */ 
/*      */     
/*  266 */     hotbar.setItemStackForSlot(emptySlot, new ItemStack("EditorTool_PrefabEditing_SelectPrefab"));
/*      */ 
/*      */     
/*  269 */     inventory.setActiveHotbarSlot((byte)emptySlot);
/*  270 */     playerRef.getPacketHandler().writeNoCache((Packet)new SetActiveSlot(-1, (byte)emptySlot));
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
/*      */   public void onPlayerAddedToWorld(@Nonnull AddPlayerToWorldEvent event) {
/*  282 */     World world = event.getWorld();
/*      */     
/*  284 */     if (world.getName().startsWith("prefabEditor-")) {
/*  285 */       world.execute(() -> {
/*      */             Holder<EntityStore> playerHolder = event.getHolder();
/*      */             UUIDComponent uuidComponent = (UUIDComponent)playerHolder.getComponent(UUIDComponent.getComponentType());
/*      */             assert uuidComponent != null;
/*      */             this.inProgressTeleportations.put(uuidComponent.getUuid(), world.getWorldConfig().getUuid());
/*      */           });
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
/*      */   public void updatePathOfLoadedPrefab(@Nonnull Path oldPath, @Nonnull Path newPath) {
/*  302 */     this.prefabsBeingEdited.remove(oldPath);
/*  303 */     this.prefabsBeingEdited.add(newPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditingAPrefab(@Nonnull UUID playerUUID) {
/*  311 */     return this.activeEditSessions.containsKey(playerUUID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PrefabEditSession getPrefabEditSession(@Nonnull UUID playerUUID) {
/*  321 */     return this.activeEditSessions.get(playerUUID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public Map<UUID, PrefabEditSession> getActiveEditSessions() {
/*  329 */     return this.activeEditSessions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void populateActiveEditSession(@Nonnull UUID playerUuid, @Nonnull PrefabEditSession editSession) {
/*  337 */     this.activeEditSessions.put(playerUuid, editSession);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void populatePrefabsBeingEdited(@Nonnull Path prefabPath) {
/*  345 */     this.prefabsBeingEdited.add(prefabPath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void scheduleAnchorEntityRecreation(@Nonnull PrefabEditSession editSession) {
/*  356 */     CompletableFuture.runAsync(() -> {
/*      */           World world = Universe.get().getWorld(editSession.getWorldName());
/*      */           if (world != null) {
/*      */             world.execute(());
/*      */           }
/*      */         });
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
/*      */   public boolean hasInProgressLoading(@Nonnull UUID playerUuid) {
/*  375 */     return this.inProgressLoading.contains(playerUuid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancelLoading(@Nonnull UUID playerUuid) {
/*  385 */     this.cancelledLoading.add(playerUuid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLoadingCancelled(@Nonnull UUID playerUuid) {
/*  395 */     return this.cancelledLoading.contains(playerUuid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearLoadingState(@Nonnull UUID playerUuid) {
/*  405 */     this.inProgressLoading.remove(playerUuid);
/*  406 */     this.cancelledLoading.remove(playerUuid);
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
/*      */   
/*      */   @Nonnull
/*      */   public CompletableFuture<Void> createEditSessionForNewPrefab(@Nonnull Ref<EntityStore> ref, @Nonnull Player editor, @Nonnull PrefabEditorCreationSettings settings, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  423 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  424 */     assert playerRefComponent != null;
/*      */     
/*  426 */     PrefabEditorCreationContext prefabEditorCreationContext = settings.finishProcessing(editor, playerRefComponent, true);
/*  427 */     if (prefabEditorCreationContext == null) {
/*  428 */       playerRefComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SOMETHING_WENT_WRONG);
/*  429 */       return CompletableFuture.completedFuture(null);
/*      */     } 
/*      */     
/*  432 */     return createEditSession(ref, prefabEditorCreationContext, true, componentAccessor);
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
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public CompletableFuture<Void> loadPrefabAndCreateEditSession(@Nonnull Ref<EntityStore> ref, @Nonnull Player editor, @Nonnull PrefabEditorCreationSettings settings, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  450 */     return loadPrefabAndCreateEditSession(ref, editor, settings, componentAccessor, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public CompletableFuture<Void> loadPrefabAndCreateEditSession(@Nonnull Ref<EntityStore> ref, @Nonnull Player editor, @Nonnull PrefabEditorCreationSettings settings, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nullable Consumer<PrefabLoadingState> progressCallback) {
/*  470 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  471 */     assert playerRefComponent != null;
/*      */     
/*  473 */     UUID playerUuid = playerRefComponent.getUuid();
/*      */ 
/*      */     
/*  476 */     if (this.inProgressLoading.contains(playerUuid)) {
/*  477 */       PrefabLoadingState prefabLoadingState = new PrefabLoadingState();
/*  478 */       prefabLoadingState.addError("server.commands.editprefab.error.loadingInProgress");
/*  479 */       notifyProgress(progressCallback, prefabLoadingState);
/*  480 */       playerRefComponent.sendMessage(Message.translation("server.commands.editprefab.error.loadingInProgress"));
/*  481 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  485 */     this.inProgressLoading.add(playerUuid);
/*  486 */     this.cancelledLoading.remove(playerUuid);
/*      */     
/*  488 */     PrefabLoadingState loadingState = new PrefabLoadingState();
/*  489 */     loadingState.setPhase(PrefabLoadingState.Phase.INITIALIZING);
/*  490 */     notifyProgress(progressCallback, loadingState);
/*      */     
/*  492 */     PrefabEditorCreationContext prefabEditorCreationContext = settings.finishProcessing(editor, playerRefComponent, false);
/*  493 */     if (prefabEditorCreationContext == null) {
/*  494 */       loadingState.addError("server.commands.editprefab.error.processingFailed");
/*  495 */       notifyProgress(progressCallback, loadingState);
/*  496 */       playerRefComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SOMETHING_WENT_WRONG);
/*  497 */       return null;
/*      */     } 
/*      */     
/*  500 */     if (prefabEditorCreationContext.getPrefabPaths().isEmpty()) {
/*  501 */       loadingState.addError("server.commands.editprefab.error.noPrefabsFound");
/*  502 */       notifyProgress(progressCallback, loadingState);
/*  503 */       playerRefComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SOMETHING_WENT_WRONG);
/*  504 */       return null;
/*      */     } 
/*      */     
/*  507 */     loadingState.setTotalPrefabs(prefabEditorCreationContext.getPrefabPaths().size());
/*  508 */     notifyProgress(progressCallback, loadingState);
/*      */     
/*  510 */     return createEditSession(ref, prefabEditorCreationContext, false, componentAccessor, loadingState, progressCallback);
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
/*      */   private void notifyProgress(@Nullable Consumer<PrefabLoadingState> progressCallback, @Nonnull PrefabLoadingState loadingState) {
/*  522 */     if (progressCallback == null) {
/*      */       return;
/*      */     }
/*      */     
/*  526 */     PrefabLoadingState.Phase phase = loadingState.getCurrentPhase();
/*  527 */     if (phase != PrefabLoadingState.Phase.LOADING_PREFABS && phase != PrefabLoadingState.Phase.PASTING_PREFABS) {
/*      */       
/*  529 */       progressCallback.accept(loadingState);
/*      */       
/*      */       return;
/*      */     } 
/*  533 */     long now = System.nanoTime();
/*  534 */     if (now - loadingState.getLastNotifyTimeNanos() >= 100000000L) {
/*  535 */       loadingState.setLastNotifyTimeNanos(now);
/*  536 */       progressCallback.accept(loadingState);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private CompletableFuture<Void> createEditSession(@Nonnull Ref<EntityStore> ref, @Nonnull PrefabEditorCreationContext context, boolean createNewPrefab, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  545 */     return createEditSession(ref, context, createNewPrefab, componentAccessor, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private CompletableFuture<Void> createEditSession(@Nonnull Ref<EntityStore> ref, @Nonnull PrefabEditorCreationContext context, boolean createNewPrefab, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nullable PrefabLoadingState loadingState, @Nullable Consumer<PrefabLoadingState> progressCallback) {
/*      */     CompletableFuture<World> future;
/*  555 */     World sourceWorld = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*      */     
/*  557 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/*  558 */     assert playerRefComponent != null;
/*      */     
/*  560 */     UUID playerUUID = playerRefComponent.getUuid();
/*      */ 
/*      */     
/*  563 */     if (this.activeEditSessions.containsKey(playerUUID)) {
/*  564 */       if (loadingState != null) {
/*  565 */         loadingState.addError("server.commands.editprefab.error.existingSession");
/*  566 */         notifyProgress(progressCallback, loadingState);
/*      */       } 
/*  568 */       playerRefComponent.sendMessage(MESSAGE_COMMANDS_PREFAB_EDIT_SESSION_MANAGER_EXISTING_EDIT_SESSION);
/*  569 */       return CompletableFuture.completedFuture(null);
/*      */     } 
/*      */ 
/*      */     
/*  573 */     for (Path prefabPath : context.getPrefabPaths()) {
/*  574 */       if (this.prefabsBeingEdited.contains(prefabPath)) {
/*  575 */         if (loadingState != null) {
/*  576 */           loadingState.addError("server.commands.editprefab.error.alreadyBeingEdited", prefabPath.toString());
/*  577 */           notifyProgress(progressCallback, loadingState);
/*      */         } 
/*  579 */         playerRefComponent.sendMessage(Message.translation("server.commands.prefabeditsessionmanager.alreadyBeingEdited")
/*  580 */             .param("path", prefabPath.toString()));
/*  581 */         return CompletableFuture.completedFuture(null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  586 */     if (loadingState != null) {
/*  587 */       loadingState.setPhase(PrefabLoadingState.Phase.CREATING_WORLD);
/*  588 */       notifyProgress(progressCallback, loadingState);
/*      */     } 
/*      */ 
/*      */     
/*  592 */     WorldConfig config = new WorldConfig();
/*      */     
/*  594 */     boolean enableTicking = context.isWorldTickingEnabled();
/*  595 */     config.setBlockTicking(enableTicking);
/*  596 */     config.setSpawningNPC(false);
/*  597 */     config.setIsSpawnMarkersEnabled(false);
/*  598 */     config.setObjectiveMarkersEnabled(false);
/*  599 */     config.setGameMode(GameMode.Creative);
/*  600 */     config.setDeleteOnRemove(true);
/*  601 */     config.setUuid(UUID.randomUUID());
/*  602 */     config.setGameTimePaused(true);
/*  603 */     config.setIsAllNPCFrozen(true);
/*  604 */     config.setSavingPlayers(true);
/*  605 */     config.setCanSaveChunks(true);
/*  606 */     config.setTicking(enableTicking);
/*  607 */     config.setForcedWeather(getWeatherFromEnvironment(context.getEnvironment()));
/*      */     
/*  609 */     String worldName = getWorldName(context);
/*      */ 
/*      */     
/*      */     try {
/*  613 */       Files.createDirectories(getSavePath(context), (FileAttribute<?>[])new FileAttribute[0]);
/*  614 */     } catch (IOException e) {
/*  615 */       if (loadingState != null) {
/*  616 */         loadingState.addError("server.commands.editprefab.error.createDirectoryFailed", e.getMessage());
/*  617 */         notifyProgress(progressCallback, loadingState);
/*      */       } 
/*  619 */       playerRefComponent.sendMessage(Message.translation("server.commands.instances.createDirectory.failed")
/*  620 */           .param("errormsg", e.getMessage()));
/*  621 */       return CompletableFuture.completedFuture(null);
/*      */     } 
/*      */     
/*  624 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  625 */     assert transformComponent != null;
/*      */ 
/*      */     
/*  628 */     Transform transform = transformComponent.getTransform().clone();
/*  629 */     PrefabEditSession prefabEditSession = new PrefabEditSession(worldName, playerUUID, sourceWorld.getWorldConfig().getUuid(), transform);
/*      */ 
/*      */ 
/*      */     
/*  633 */     if (createNewPrefab) {
/*  634 */       future = getPrefabCreatingCompletableFuture(context, prefabEditSession, config);
/*      */     } else {
/*  636 */       future = getPrefabLoadingCompletableFuture(context, prefabEditSession, config, loadingState, progressCallback, playerUUID);
/*      */     } 
/*      */ 
/*      */     
/*  640 */     if (future == null) {
/*  641 */       if (loadingState != null) {
/*  642 */         loadingState.addError("server.commands.editprefab.error.loadFailed");
/*  643 */         notifyProgress(progressCallback, loadingState);
/*      */       } 
/*  645 */       return CompletableFuture.completedFuture(null);
/*      */     } 
/*      */     
/*  648 */     return future
/*  649 */       .exceptionally(throwable -> {
/*      */           if (isLoadingCancelled(playerUUID)) {
/*      */             return null;
/*      */           }
/*      */           
/*      */           ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Error occurred during prefab editor session creation");
/*      */           
/*      */           if (loadingState != null) {
/*      */             loadingState.addError("server.commands.editprefab.error.exception", throwable.getMessage());
/*      */             
/*      */             notifyProgress(progressCallback, loadingState);
/*      */           } 
/*      */           
/*      */           playerRefComponent.sendMessage(Message.translation("server.commands.editprefab.error.exception").param("details", (throwable.getMessage() != null) ? throwable.getMessage() : "Unknown error"));
/*      */           return null;
/*  664 */         }).thenAcceptAsync(targetWorld -> {
/*      */           if (isLoadingCancelled(playerUUID)) {
/*      */             return;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           if (targetWorld == null) {
/*      */             return;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           if (loadingState != null) {
/*      */             loadingState.setPhase(PrefabLoadingState.Phase.FINALIZING);
/*      */ 
/*      */ 
/*      */             
/*      */             notifyProgress(progressCallback, loadingState);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           Vector3i spawnPoint = prefabEditSession.getSpawnPoint();
/*      */ 
/*      */ 
/*      */           
/*      */           targetWorld.getWorldConfig().setSpawnProvider((ISpawnProvider)new GlobalSpawnProvider(new Transform(spawnPoint)));
/*      */ 
/*      */           
/*      */           CompletableFuture.runAsync((), (Executor)targetWorld);
/*      */ 
/*      */           
/*      */           CompletableFuture.runAsync((), (Executor)sourceWorld);
/*  698 */         }).thenRun(() -> {
/*      */           if (isLoadingCancelled(playerUUID)) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*      */           this.prefabsBeingEdited.addAll(context.getPrefabPaths());
/*      */           
/*      */           this.activeEditSessions.put(playerUUID, prefabEditSession);
/*      */           
/*      */           if (loadingState != null) {
/*      */             loadingState.markComplete();
/*      */             
/*      */             notifyProgress(progressCallback, loadingState);
/*      */           } 
/*      */           
/*      */           playerRefComponent.sendMessage(Message.translation("server.commands.prefabeditsessionmanager.success." + (createNewPrefab ? "new" : "load")));
/*  715 */         }).whenComplete((result, throwable) -> this.inProgressLoading.remove(playerUUID));
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
/*      */   @Nonnull
/*      */   private CompletableFuture<World> getWorldCreatingFuture(@Nonnull PrefabEditorCreationContext context, @Nonnull WorldConfig config) {
/*  729 */     return Universe.get().makeWorld(getWorldName(context), getSavePath(context), config, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private String getWorldName(@Nonnull PrefabEditorCreationContext context) {
/*  737 */     return "prefabEditor-" + context.getEditorRef().getUsername();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private String getWeatherFromEnvironment(@Nullable String environmentId) {
/*  746 */     if (environmentId == null || environmentId.isEmpty()) {
/*  747 */       return "Zone1_Sunny";
/*      */     }
/*      */     
/*  750 */     Environment environment = (Environment)Environment.getAssetMap().getAsset(environmentId);
/*  751 */     if (environment == null) {
/*  752 */       return "Zone1_Sunny";
/*      */     }
/*      */     
/*  755 */     IWeightedMap<WeatherForecast> forecast = environment.getWeatherForecast(12);
/*  756 */     if (forecast == null || forecast.size() == 0) {
/*  757 */       return "Zone1_Sunny";
/*      */     }
/*      */     
/*  760 */     String[] bestWeatherId = { null };
/*  761 */     double[] highestWeight = { Double.NEGATIVE_INFINITY };
/*      */     
/*  763 */     forecast.forEachEntry((weatherForecast, weight) -> {
/*      */           if (weight > highestWeight[0]) {
/*      */             highestWeight[0] = weight;
/*      */             
/*      */             bestWeatherId[0] = weatherForecast.getWeatherId();
/*      */           } 
/*      */         });
/*  770 */     return (bestWeatherId[0] != null) ? bestWeatherId[0] : "Zone1_Sunny";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private Path getSavePath(@Nonnull PrefabEditorCreationContext context) {
/*  778 */     return Constants.UNIVERSE_PATH.resolve("worlds").resolve(getWorldName(context));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyWorldGenWorldConfig(@Nonnull PrefabEditorCreationContext context, int yLevelToPastePrefabsAt, @Nonnull WorldConfig worldConfig) {
/*  788 */     String environment = (context.getEnvironment() != null) ? context.getEnvironment() : "Env_Zone1_Plains";
/*  789 */     Color tint = DEFAULT_TINT;
/*  790 */     if (context.getGrassTint() != null && !context.getGrassTint().isEmpty()) {
/*  791 */       Color parsed = ColorParseUtil.parseColor(context.getGrassTint());
/*  792 */       if (parsed != null) {
/*  793 */         tint = parsed;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  798 */     if (context.getWorldGenType().equals(WorldGenType.FLAT)) {
/*      */ 
/*      */       
/*  801 */       int yLevelForFlatWorldExclusive = Math.max(1, yLevelToPastePrefabsAt - context.getBlocksAboveSurface());
/*      */       
/*  803 */       FlatWorldGenProvider.Layer topLayer = new FlatWorldGenProvider.Layer();
/*  804 */       topLayer.blockType = "Soil_Grass";
/*  805 */       topLayer.to = yLevelForFlatWorldExclusive;
/*  806 */       topLayer.from = yLevelForFlatWorldExclusive - 1;
/*  807 */       topLayer.environment = environment;
/*      */       
/*  809 */       FlatWorldGenProvider.Layer airLayer = new FlatWorldGenProvider.Layer();
/*  810 */       airLayer.blockType = "Empty";
/*  811 */       airLayer.to = 320;
/*  812 */       airLayer.from = yLevelForFlatWorldExclusive;
/*  813 */       airLayer.environment = environment;
/*      */       
/*  815 */       if (yLevelForFlatWorldExclusive - 2 >= 0) {
/*  816 */         FlatWorldGenProvider.Layer bottomLayer = new FlatWorldGenProvider.Layer();
/*  817 */         bottomLayer.blockType = "Soil_Clay";
/*  818 */         bottomLayer.to = yLevelForFlatWorldExclusive - 1;
/*  819 */         bottomLayer.from = 0;
/*  820 */         bottomLayer.environment = environment;
/*  821 */         worldConfig.setWorldGenProvider((IWorldGenProvider)new FlatWorldGenProvider(tint, new FlatWorldGenProvider.Layer[] { airLayer, topLayer, bottomLayer }));
/*      */       } else {
/*  823 */         worldConfig.setWorldGenProvider((IWorldGenProvider)new FlatWorldGenProvider(tint, new FlatWorldGenProvider.Layer[] { airLayer, topLayer }));
/*      */       } 
/*      */     } else {
/*  826 */       worldConfig.setWorldGenProvider((IWorldGenProvider)new VoidWorldGenProvider(tint, environment));
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
/*      */   @Nonnull
/*      */   private CompletableFuture<World> getPrefabCreatingCompletableFuture(@Nonnull PrefabEditorCreationContext context, @Nonnull PrefabEditSession editSession, @Nonnull WorldConfig worldConfig) {
/*  841 */     applyWorldGenWorldConfig(context, context.getPasteLevelGoal() - 1, worldConfig);
/*      */ 
/*      */     
/*  844 */     return getWorldCreatingFuture(context, worldConfig).thenCompose(world -> CompletableFuture.supplyAsync((), (Executor)world));
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private CompletableFuture<World> getPrefabLoadingCompletableFuture(@Nonnull PrefabEditorCreationContext context, @Nonnull PrefabEditSession editSession, @Nonnull WorldConfig worldConfig, @Nullable PrefabLoadingState loadingState, @Nullable Consumer<PrefabLoadingState> progressCallback, @Nonnull UUID playerUuid) {
/*  888 */     CompletableFuture[] initializationFutures = new CompletableFuture[context.getPrefabPaths().size()];
/*      */     
/*  890 */     if (loadingState != null) {
/*  891 */       loadingState.setPhase(PrefabLoadingState.Phase.LOADING_PREFABS);
/*  892 */       notifyProgress(progressCallback, loadingState);
/*      */     } 
/*      */ 
/*      */     
/*  896 */     for (int i = 0; i < context.getPrefabPaths().size(); i++) {
/*      */       
/*  898 */       Path prefabPath = context.getPrefabPaths().get(i);
/*  899 */       CompletableFuture<IPrefabBuffer> prefabLoadingFuture = getPrefabBuffer((CommandSender)context.getEditor(), prefabPath);
/*      */       
/*  901 */       if (prefabLoadingFuture == null) {
/*  902 */         if (loadingState != null) {
/*  903 */           loadingState.addError("server.commands.editprefab.error.prefabLoadFailed", prefabPath.toString());
/*  904 */           notifyProgress(progressCallback, loadingState);
/*      */         } 
/*  906 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  910 */       Path pathForCallback = prefabPath;
/*  911 */       initializationFutures[i] = prefabLoadingFuture.thenApply(buffer -> {
/*      */             if (loadingState != null) {
/*      */               loadingState.onPrefabLoaded(pathForCallback);
/*      */               
/*      */               notifyProgress(progressCallback, loadingState);
/*      */             } 
/*      */             return buffer;
/*      */           });
/*      */     } 
/*  920 */     return 
/*      */ 
/*      */       
/*  923 */       CompletableFuture.allOf((CompletableFuture<?>[])initializationFutures)
/*  924 */       .thenApply(unused -> {
/*      */           if (isLoadingCancelled(playerUuid)) {
/*      */             return null;
/*      */           }
/*      */ 
/*      */           
/*      */           ObjectArrayList<IPrefabBuffer> objectArrayList = new ObjectArrayList(initializationFutures.length);
/*      */ 
/*      */           
/*      */           int heightOfTallestPrefab = 0;
/*      */ 
/*      */           
/*      */           for (CompletableFuture<IPrefabBuffer> initializationFuture : initializationFutures) {
/*      */             IPrefabBuffer prefabAccessor = initializationFuture.join();
/*      */ 
/*      */             
/*      */             objectArrayList.add(prefabAccessor);
/*      */ 
/*      */             
/*      */             if (context.loadChildPrefabs()) {
/*      */               for (PrefabBuffer.ChildPrefab childPrefab : prefabAccessor.getChildPrefabs());
/*      */             }
/*      */ 
/*      */             
/*      */             int prefabHeight = Math.abs(prefabAccessor.getMaxY() - prefabAccessor.getMinY());
/*      */             
/*      */             if (prefabHeight > heightOfTallestPrefab) {
/*      */               heightOfTallestPrefab = prefabHeight;
/*      */             }
/*      */           } 
/*      */           
/*      */           int yLevelToPastePrefabsAt = getAmountOfBlocksBelowPrefab(heightOfTallestPrefab, context.getPasteLevelGoal());
/*      */           
/*      */           applyWorldGenWorldConfig(context, yLevelToPastePrefabsAt, worldConfig);
/*      */           
/*      */           if (loadingState != null) {
/*      */             loadingState.setPhase(PrefabLoadingState.Phase.PASTING_PREFABS);
/*      */             
/*      */             notifyProgress(progressCallback, loadingState);
/*      */           } 
/*      */           
/*      */           if (isLoadingCancelled(playerUuid)) {
/*      */             return null;
/*      */           }
/*      */           
/*      */           String worldName = getWorldName(context);
/*      */           
/*      */           if (Universe.get().getWorld(worldName) != null) {
/*      */             LOGGER.at(Level.WARNING).log("Aborting prefab editor creation for %s: world '%s' already exists", playerUuid, worldName);
/*      */             
/*      */             return null;
/*      */           } 
/*      */           
/*      */           return new Tri<>(objectArrayList, Integer.valueOf(yLevelToPastePrefabsAt), getWorldCreatingFuture(context, worldConfig).join());
/*  978 */         }).thenCompose(passedData -> (passedData == null) ? CompletableFuture.completedFuture(null) : CompletableFuture.supplyAsync((), (Executor)passedData.getRight()));
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   private int[] calculateRowGroups(@Nonnull PrefabEditorCreationContext context, int prefabCount) {
/* 1166 */     int[] rowGroups = new int[prefabCount];
/* 1167 */     PrefabRowSplitMode rowSplitMode = context.getRowSplitMode();
/* 1168 */     List<Path> prefabPaths = context.getPrefabPaths();
/*      */     
/* 1170 */     if (rowSplitMode == PrefabRowSplitMode.NONE || prefabCount == 0)
/*      */     {
/* 1172 */       return rowGroups;
/*      */     }
/*      */     
/* 1175 */     if (rowSplitMode == PrefabRowSplitMode.BY_SPECIFIED_FOLDER) {
/*      */       
/* 1177 */       List<String> unprocessedPaths = context.getUnprocessedPrefabPaths();
/* 1178 */       Path rootPath = context.getPrefabRootDirectory().getPrefabPath();
/*      */       
/* 1180 */       int currentGroup = 0;
/* 1181 */       int prefabIndex = 0;
/*      */       
/* 1183 */       for (String unprocessedPath : unprocessedPaths) {
/*      */         
/* 1185 */         Path resolvedPath = rootPath.resolve(unprocessedPath.replace('/', File.separatorChar)
/* 1186 */             .replace('\\', File.separatorChar));
/*      */         
/* 1188 */         while (prefabIndex < prefabCount) {
/* 1189 */           Path prefabPath = prefabPaths.get(prefabIndex);
/*      */           
/* 1191 */           if (prefabPath.startsWith(resolvedPath) || (
/* 1192 */             unprocessedPath.endsWith("\\") ? prefabPath
/* 1193 */             .startsWith(resolvedPath) : prefabPath
/* 1194 */             .equals(resolvedPath))) {
/* 1195 */             rowGroups[prefabIndex] = currentGroup;
/* 1196 */             prefabIndex++;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1201 */         currentGroup++;
/*      */       } 
/* 1203 */     } else if (rowSplitMode == PrefabRowSplitMode.BY_ALL_SUBFOLDERS) {
/*      */       
/* 1205 */       Object2ObjectOpenHashMap<Path, Integer> parentDirToGroup = new Object2ObjectOpenHashMap();
/* 1206 */       int nextGroup = 0;
/*      */       
/* 1208 */       for (int i = 0; i < prefabCount; i++) {
/* 1209 */         Path prefabPath = prefabPaths.get(i);
/* 1210 */         Path parentDir = prefabPath.getParent();
/*      */         
/* 1212 */         if (parentDir != null) {
/* 1213 */           Integer group = (Integer)parentDirToGroup.get(parentDir);
/* 1214 */           if (group == null) {
/* 1215 */             parentDirToGroup.put(parentDir, Integer.valueOf(nextGroup));
/* 1216 */             rowGroups[i] = nextGroup;
/* 1217 */             nextGroup++;
/*      */           } else {
/* 1219 */             rowGroups[i] = group.intValue();
/*      */           } 
/*      */         } else {
/* 1222 */           rowGroups[i] = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1227 */     return rowGroups;
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
/*      */   private int getAmountOfBlocksBelowPrefab(int prefabHeight, int desiredYLevel) {
/* 1239 */     if (desiredYLevel < 0) throw new IllegalArgumentException("Cannot have a negative y level for pasting prefabs"); 
/* 1240 */     if (desiredYLevel >= 320) throw new IllegalArgumentException("Cannot paste above or at the world height"); 
/* 1241 */     return Math.min(desiredYLevel, 320 - prefabHeight);
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
/*      */   
/*      */   @Nullable
/*      */   public CompletableFuture<Void> exitEditSession(@Nonnull Ref<EntityStore> ref, @Nonnull World world, @Nonnull PlayerRef playerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 1258 */     PrefabEditSession prefabEditSession = this.activeEditSessions.get(playerRef.getUuid());
/* 1259 */     if (prefabEditSession == null) return null;
/*      */     
/* 1261 */     prefabEditSession.hidePrefabAnchors(playerRef.getPacketHandler());
/*      */     
/* 1263 */     World returnWorld = Universe.get().getWorld(prefabEditSession.getWorldArrivedFrom());
/* 1264 */     Transform returnLocation = prefabEditSession.getTransformArrivedFrom();
/*      */ 
/*      */     
/* 1267 */     if (returnWorld == null || returnLocation == null) {
/* 1268 */       LOGGER.at(Level.WARNING).log("Prefab editor exit fallback triggered for player %s: returnWorld=%s (worldArrivedFrom=%s), returnLocation=%s. Using default world spawn.", playerRef
/*      */           
/* 1270 */           .getUuid(), 
/* 1271 */           (returnWorld != null) ? returnWorld.getName() : "null", prefabEditSession
/* 1272 */           .getWorldArrivedFrom(), returnLocation);
/*      */ 
/*      */       
/* 1275 */       returnWorld = Universe.get().getDefaultWorld();
/* 1276 */       returnLocation = returnWorld.getWorldConfig().getSpawnProvider().getSpawnPoint(ref, componentAccessor);
/*      */     } 
/*      */     
/* 1279 */     World finalReturnWorld = returnWorld;
/* 1280 */     Transform finalReturnLocation = returnLocation;
/* 1281 */     return CompletableFuture.runAsync(() -> componentAccessor.putComponent(ref, Teleport.getComponentType(), (Component)new Teleport(finalReturnWorld, finalReturnLocation)), (Executor)world)
/* 1282 */       .thenRunAsync(() -> {
/*      */           World worldToRemove = Universe.get().getWorld(prefabEditSession.getWorldName());
/*      */           if (worldToRemove != null) {
/*      */             Universe.get().removeWorld(prefabEditSession.getWorldName());
/*      */           }
/*      */           Collection<PrefabEditingMetadata> prefabsBeingEditedInEditSession = prefabEditSession.getLoadedPrefabMetadata().values();
/*      */           for (PrefabEditingMetadata prefab : prefabsBeingEditedInEditSession) {
/*      */             this.prefabsBeingEdited.remove(prefab.getPrefabPath());
/*      */           }
/*      */           this.activeEditSessions.remove(playerRef.getUuid());
/*      */         });
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public CompletableFuture<Void> cleanupCancelledSession(@Nonnull UUID playerUuid, @Nonnull String worldName, @Nullable Consumer<PrefabLoadingState> progressCallback) {
/* 1312 */     cancelLoading(playerUuid);
/*      */     
/* 1314 */     PrefabLoadingState loadingState = new PrefabLoadingState();
/* 1315 */     loadingState.setPhase(PrefabLoadingState.Phase.CANCELLING);
/* 1316 */     notifyProgress(progressCallback, loadingState);
/*      */     
/* 1318 */     return CompletableFuture.runAsync(() -> {
/*      */           World world = Universe.get().getWorld(worldName);
/*      */           if (world != null) {
/*      */             loadingState.setPhase(PrefabLoadingState.Phase.SHUTTING_DOWN_WORLD);
/*      */             notifyProgress(progressCallback, loadingState);
/*      */             world.getWorldConfig().setDeleteOnRemove(true);
/*      */             loadingState.setPhase(PrefabLoadingState.Phase.DELETING_WORLD);
/*      */             notifyProgress(progressCallback, loadingState);
/*      */             Universe.get().removeWorld(worldName);
/*      */           } 
/*      */           PrefabEditSession session = this.activeEditSessions.remove(playerUuid);
/*      */           if (session != null) {
/*      */             Collection<PrefabEditingMetadata> prefabsInSession = session.getLoadedPrefabMetadata().values();
/*      */             for (PrefabEditingMetadata prefab : prefabsInSession) {
/*      */               this.prefabsBeingEdited.remove(prefab.getPrefabPath());
/*      */             }
/*      */           } 
/*      */           this.inProgressLoading.remove(playerUuid);
/*      */           loadingState.setPhase(PrefabLoadingState.Phase.SHUTDOWN_COMPLETE);
/*      */           notifyProgress(progressCallback, loadingState);
/*      */         });
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
/*      */   @Nonnull
/*      */   public CompletableFuture<Void> cleanupCancelledSession(@Nonnull UUID playerUuid, @Nonnull String worldName) {
/* 1364 */     return cleanupCancelledSession(playerUuid, worldName, null);
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
/*      */   @Nullable
/*      */   private CompletableFuture<IPrefabBuffer> getPrefabBuffer(@Nonnull CommandSender sender, @Nonnull Path path) {
/* 1377 */     if (!Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 1378 */       sender.sendMessage(Message.translation("server.commands.editprefab.prefabNotFound")
/* 1379 */           .param("name", path.toString()));
/* 1380 */       return null;
/*      */     } 
/* 1382 */     return CompletableFuture.supplyAsync(() -> PrefabBufferUtil.getCached(path));
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabEditSessionManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */