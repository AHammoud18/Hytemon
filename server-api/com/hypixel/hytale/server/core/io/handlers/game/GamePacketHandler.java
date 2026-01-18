/*      */ package com.hypixel.hytale.server.core.io.handlers.game;
/*      */ import com.hypixel.hytale.component.Component;
/*      */ import com.hypixel.hytale.component.ComponentAccessor;
/*      */ import com.hypixel.hytale.component.ComponentType;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.component.Store;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.math.util.ChunkUtil;
/*      */ import com.hypixel.hytale.math.util.MathUtil;
/*      */ import com.hypixel.hytale.math.vector.Vector3d;
/*      */ import com.hypixel.hytale.math.vector.Vector3i;
/*      */ import com.hypixel.hytale.protocol.BlockRotation;
/*      */ import com.hypixel.hytale.protocol.GameMode;
/*      */ import com.hypixel.hytale.protocol.HostAddress;
/*      */ import com.hypixel.hytale.protocol.Packet;
/*      */ import com.hypixel.hytale.protocol.packets.camera.RequestFlyCameraMode;
/*      */ import com.hypixel.hytale.protocol.packets.camera.SetFlyCameraMode;
/*      */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*      */ import com.hypixel.hytale.protocol.packets.connection.Pong;
/*      */ import com.hypixel.hytale.protocol.packets.entities.MountMovement;
/*      */ import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChain;
/*      */ import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChains;
/*      */ import com.hypixel.hytale.protocol.packets.interface_.ChatMessage;
/*      */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageEvent;
/*      */ import com.hypixel.hytale.protocol.packets.interface_.UpdateLanguage;
/*      */ import com.hypixel.hytale.protocol.packets.machinima.RequestMachinimaActorModel;
/*      */ import com.hypixel.hytale.protocol.packets.machinima.UpdateMachinimaScene;
/*      */ import com.hypixel.hytale.protocol.packets.player.ClientMovement;
/*      */ import com.hypixel.hytale.protocol.packets.player.ClientPlaceBlock;
/*      */ import com.hypixel.hytale.protocol.packets.player.ClientReady;
/*      */ import com.hypixel.hytale.protocol.packets.player.MouseInteraction;
/*      */ import com.hypixel.hytale.protocol.packets.player.RemoveMapMarker;
/*      */ import com.hypixel.hytale.protocol.packets.player.SyncPlayerPreferences;
/*      */ import com.hypixel.hytale.protocol.packets.serveraccess.SetServerAccess;
/*      */ import com.hypixel.hytale.protocol.packets.serveraccess.UpdateServerAccess;
/*      */ import com.hypixel.hytale.protocol.packets.setup.RequestAssets;
/*      */ import com.hypixel.hytale.protocol.packets.setup.ViewRadius;
/*      */ import com.hypixel.hytale.protocol.packets.window.ClientOpenWindow;
/*      */ import com.hypixel.hytale.protocol.packets.window.CloseWindow;
/*      */ import com.hypixel.hytale.protocol.packets.window.SendWindowAction;
/*      */ import com.hypixel.hytale.protocol.packets.window.UpdateWindow;
/*      */ import com.hypixel.hytale.protocol.packets.world.SetPaused;
/*      */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*      */ import com.hypixel.hytale.protocol.packets.worldmap.TeleportToWorldMapMarker;
/*      */ import com.hypixel.hytale.protocol.packets.worldmap.TeleportToWorldMapPosition;
/*      */ import com.hypixel.hytale.protocol.packets.worldmap.UpdateWorldMapVisible;
/*      */ import com.hypixel.hytale.server.core.Constants;
/*      */ import com.hypixel.hytale.server.core.HytaleServer;
/*      */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*      */ import com.hypixel.hytale.server.core.Message;
/*      */ import com.hypixel.hytale.server.core.NameMatching;
/*      */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*      */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*      */ import com.hypixel.hytale.server.core.console.ConsoleModule;
/*      */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ValidatedWindow;
/*      */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*      */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*      */ import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
/*      */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*      */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*      */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*      */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*      */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerCreativeSettings;
/*      */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerInput;
/*      */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*      */ import com.hypixel.hytale.server.core.modules.entity.teleport.PendingTeleport;
/*      */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*      */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.BlockPlaceUtils;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*      */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*      */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*      */ import com.hypixel.hytale.server.core.universe.Universe;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*      */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.core.util.MessageUtil;
/*      */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*      */ import com.hypixel.hytale.server.core.util.ValidateUtil;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.util.Deque;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ 
/*      */ public class GamePacketHandler extends GenericPacketHandler implements IPacketHandler {
/*      */   @Nonnull
/*  105 */   private final Deque<SyncInteractionChain> interactionPacketQueue = new ConcurrentLinkedDeque<>();
/*      */ 
/*      */   
/*      */   private static final double RELATIVE_POSITION_DELTA_SCALE = 10000.0D;
/*      */ 
/*      */   
/*      */   private PlayerRef playerRef;
/*      */   
/*      */   @Deprecated
/*      */   private Player playerComponent;
/*      */ 
/*      */   
/*      */   public GamePacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, @Nonnull PlayerAuthentication auth) {
/*  118 */     super(channel, protocolVersion);
/*  119 */     this.auth = auth;
/*  120 */     ServerManager.get().populateSubPacketHandlers(this);
/*  121 */     registerHandlers();
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public Deque<SyncInteractionChain> getInteractionPacketQueue() {
/*  126 */     return this.interactionPacketQueue;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public PlayerRef getPlayerRef() {
/*  132 */     return this.playerRef;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerRef(@Nonnull PlayerRef playerRef, @Nonnull Player playerComponent) {
/*  142 */     this.playerRef = playerRef;
/*  143 */     this.playerComponent = playerComponent;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public String getIdentifier() {
/*  148 */     return "{Playing(" + NettyUtil.formatRemoteAddress(this.channel) + "), " + ((this.playerRef != null) ? (String.valueOf(this.playerRef.getUuid()) + ", " + String.valueOf(this.playerRef.getUuid())) : "null player") + "}";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void registerHandlers() {
/*  155 */     registerHandler(1, p -> handle((Disconnect)p));
/*  156 */     registerHandler(3, p -> handlePong((Pong)p));
/*  157 */     registerHandler(108, p -> handle((ClientMovement)p));
/*  158 */     registerHandler(211, p -> handle((ChatMessage)p));
/*  159 */     registerHandler(23, p -> handle((RequestAssets)p));
/*  160 */     registerHandler(219, p -> handle((CustomPageEvent)p));
/*  161 */     registerHandler(32, p -> handle((ViewRadius)p));
/*  162 */     registerHandler(232, p -> handle((UpdateLanguage)p));
/*  163 */     registerHandler(111, p -> handle((MouseInteraction)p));
/*  164 */     registerHandler(251, p -> handle((UpdateServerAccess)p));
/*  165 */     registerHandler(252, p -> handle((SetServerAccess)p));
/*  166 */     registerHandler(204, p -> handle((ClientOpenWindow)p));
/*  167 */     registerHandler(203, p -> handle((SendWindowAction)p));
/*  168 */     registerHandler(202, p -> handle((CloseWindow)p));
/*  169 */     registerHandler(260, p -> handle((RequestMachinimaActorModel)p));
/*  170 */     registerHandler(262, p -> handle((UpdateMachinimaScene)p));
/*  171 */     registerHandler(105, p -> handle((ClientReady)p));
/*  172 */     registerHandler(166, p -> handle((MountMovement)p));
/*  173 */     registerHandler(116, p -> handle((SyncPlayerPreferences)p));
/*  174 */     registerHandler(117, p -> handle((ClientPlaceBlock)p));
/*      */     
/*  176 */     registerHandler(119, p -> handle((RemoveMapMarker)p));
/*      */     
/*  178 */     registerHandler(243, p -> handle((UpdateWorldMapVisible)p));
/*  179 */     registerHandler(244, p -> handle((TeleportToWorldMapMarker)p));
/*  180 */     registerHandler(245, p -> handle((TeleportToWorldMapPosition)p));
/*      */     
/*  182 */     registerHandler(290, p -> handle((SyncInteractionChains)p));
/*      */     
/*  184 */     registerHandler(158, p -> handle((SetPaused)p));
/*      */     
/*  186 */     registerHandler(282, p -> handle((RequestFlyCameraMode)p));
/*      */     
/*  188 */     this.packetHandlers.forEach(SubPacketHandler::registerHandlers);
/*      */   }
/*      */ 
/*      */   
/*      */   public void closed(ChannelHandlerContext ctx) {
/*  193 */     super.closed(ctx);
/*  194 */     Universe.get().removePlayer(this.playerRef);
/*      */   }
/*      */ 
/*      */   
/*      */   public void disconnect(@Nonnull String message) {
/*  199 */     this.disconnectReason.setServerDisconnectReason(message);
/*      */     
/*  201 */     if (this.playerRef != null) {
/*      */       
/*  203 */       HytaleLogger.getLogger().at(Level.INFO).log("Disconnecting %s at %s with the message: %s", this.playerRef
/*  204 */           .getUsername(), 
/*  205 */           NettyUtil.formatRemoteAddress(this.channel), message);
/*      */ 
/*      */ 
/*      */       
/*  209 */       disconnect0(message);
/*      */       
/*  211 */       Universe.get().removePlayer(this.playerRef);
/*      */     } else {
/*  213 */       super.disconnect(message);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull Disconnect packet) {
/*  223 */     this.disconnectReason.setClientDisconnectType(packet.type);
/*      */     
/*  225 */     HytaleLogger.getLogger().at(Level.INFO).log("%s - %s at %s left with reason: %s - %s", this.playerRef
/*  226 */         .getUuid(), this.playerRef
/*  227 */         .getUsername(), 
/*  228 */         NettyUtil.formatRemoteAddress(this.channel), packet.type
/*  229 */         .name(), packet.reason);
/*      */ 
/*      */     
/*  232 */     ProtocolUtil.closeApplicationConnection(this.channel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull MouseInteraction packet) {
/*  241 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  242 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  246 */     Store<EntityStore> store = ref.getStore();
/*  247 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  249 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           InteractionModule.get().doMouseInteraction(ref, (ComponentAccessor)store, packet, playerComponent, this.playerRef);
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
/*      */   public void handle(@Nonnull ClientMovement packet) {
/*  266 */     if (packet.absolutePosition != null && !ValidateUtil.isSafePosition(packet.absolutePosition)) {
/*  267 */       disconnect("Sent impossible position data!");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  272 */     if ((packet.bodyOrientation != null && !ValidateUtil.isSafeDirection(packet.bodyOrientation)) || (packet.lookOrientation != null && !ValidateUtil.isSafeDirection(packet.lookOrientation))) {
/*  273 */       disconnect("Sent impossible orientation data!");
/*      */       
/*      */       return;
/*      */     } 
/*  277 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  278 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  284 */     Store<EntityStore> store = ref.getStore();
/*  285 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  287 */     world.execute(() -> {
/*      */           if (!ref.isValid()) {
/*      */             return;
/*      */           }
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           if (playerComponent.isWaitingForClientReady()) {
/*      */             return;
/*      */           }
/*      */           PlayerInput playerInputComponent = (PlayerInput)store.getComponent(ref, PlayerInput.getComponentType());
/*      */           if (playerInputComponent == null) {
/*      */             return;
/*      */           }
/*      */           if (packet.movementStates != null) {
/*      */             playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.SetMovementStates(packet.movementStates));
/*      */           }
/*      */           if (packet.velocity != null) {
/*      */             playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.SetClientVelocity(packet.velocity));
/*      */           }
/*      */           PendingTeleport pendingTeleport = (PendingTeleport)store.getComponent(ref, PendingTeleport.getComponentType());
/*      */           if (pendingTeleport != null) {
/*      */             if (packet.teleportAck == null) {
/*      */               return;
/*      */             }
/*      */             switch (pendingTeleport.validate(packet.teleportAck.teleportId, packet.absolutePosition)) {
/*      */               case INVALID_ID:
/*      */                 disconnect("Incorrect teleportId");
/*      */                 return;
/*      */               case INVALID_POSITION:
/*      */                 disconnect("Invalid teleport");
/*      */                 return;
/*      */             } 
/*      */             if (!pendingTeleport.isEmpty()) {
/*      */               return;
/*      */             }
/*      */             store.removeComponent(ref, PendingTeleport.getComponentType());
/*      */           } 
/*      */           if (packet.mountedTo != 0) {
/*      */             if (packet.mountedTo != playerInputComponent.getMountId()) {
/*      */               return;
/*      */             }
/*      */             if (packet.riderMovementStates != null) {
/*      */               playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.SetRiderMovementStates(packet.riderMovementStates));
/*      */             }
/*      */           } 
/*      */           if (packet.bodyOrientation != null) {
/*      */             playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.SetBody(packet.bodyOrientation));
/*      */           }
/*      */           if (packet.lookOrientation != null) {
/*      */             playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.SetHead(packet.lookOrientation));
/*      */           }
/*      */           if (packet.wishMovement != null) {
/*      */             playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.WishMovement(packet.wishMovement.x, packet.wishMovement.y, packet.wishMovement.z));
/*      */           }
/*      */           if (packet.absolutePosition != null) {
/*      */             playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.AbsoluteMovement(packet.absolutePosition.x, packet.absolutePosition.y, packet.absolutePosition.z));
/*      */           } else if (packet.relativePosition != null) {
/*      */             if (packet.relativePosition.x != 0 || packet.relativePosition.y != 0 || packet.relativePosition.z != 0 || packet.movementStates != null) {
/*      */               playerInputComponent.queue((PlayerInput.InputUpdate)new PlayerInput.RelativeMovement(packet.relativePosition.x / 10000.0D, packet.relativePosition.y / 10000.0D, packet.relativePosition.z / 10000.0D));
/*      */             }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull ChatMessage packet) {
/*  376 */     if (packet.message == null || packet.message.isEmpty()) {
/*  377 */       disconnect("Invalid chat message packet! Message was empty.");
/*      */       
/*      */       return;
/*      */     } 
/*  381 */     String message = packet.message;
/*  382 */     char firstChar = message.charAt(0);
/*  383 */     if (firstChar == '/') {
/*  384 */       CommandManager.get().handleCommand((CommandSender)this.playerComponent, message.substring(1));
/*  385 */     } else if (firstChar == '.') {
/*  386 */       this.playerRef.sendMessage(Message.translation("server.io.gamepackethandler.localCommandDenied")
/*  387 */           .param("msg", message));
/*      */     } else {
/*  389 */       Ref<EntityStore> ref = this.playerRef.getReference();
/*  390 */       if (ref == null || !ref.isValid())
/*      */         return; 
/*  392 */       UUID playerUUID = this.playerRef.getUuid();
/*      */       
/*  394 */       ObjectArrayList objectArrayList = new ObjectArrayList(Universe.get().getPlayers());
/*  395 */       objectArrayList.removeIf(targetPlayerRef -> targetPlayerRef.getHiddenPlayersManager().isPlayerHidden(playerUUID));
/*      */       
/*  397 */       ((CompletableFuture)HytaleServer.get().getEventBus().dispatchForAsync(PlayerChatEvent.class)
/*  398 */         .dispatch((IBaseEvent)new PlayerChatEvent(this.playerRef, (List)objectArrayList, message)))
/*  399 */         .whenComplete((playerChatEvent, throwable) -> {
/*      */             if (throwable != null) {
/*      */               ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(throwable)).log("An error occurred while dispatching PlayerChatEvent for player %s", this.playerRef.getUsername());
/*      */               return;
/*      */             } 
/*      */             if (playerChatEvent.isCancelled()) {
/*      */               return;
/*      */             }
/*      */             Message sentMessage = playerChatEvent.getFormatter().format(this.playerRef, playerChatEvent.getContent());
/*      */             HytaleLogger.getLogger().at(Level.INFO).log(MessageUtil.toAnsiString(sentMessage).toAnsi(ConsoleModule.get().getTerminal()));
/*      */             for (PlayerRef targetPlayerRef : playerChatEvent.getTargets()) {
/*      */               targetPlayerRef.sendMessage(sentMessage);
/*      */             }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull RequestAssets packet) {
/*  428 */     CommonAssetModule.get().sendAssetsToPlayer((PacketHandler)this, packet.assets, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull CustomPageEvent packet) {
/*  438 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  439 */     if (ref == null || !ref.isValid()) {
/*      */ 
/*      */       
/*  442 */       this.playerRef.getPacketHandler().writeNoCache((Packet)new SetPage(Page.None, true));
/*      */       
/*      */       return;
/*      */     } 
/*  446 */     Store<EntityStore> store = ref.getStore();
/*  447 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  449 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           PageManager pageManager = playerComponent.getPageManager();
/*      */           pageManager.handleEvent(ref, store, packet);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull ViewRadius packet) {
/*  464 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  465 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  469 */     Store<EntityStore> store = ref.getStore();
/*  470 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  472 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)store.getComponent(ref, EntityTrackerSystems.EntityViewer.getComponentType());
/*      */           assert entityViewerComponent != null;
/*      */           int viewRadiusChunks = MathUtil.ceil((packet.value / 32.0F));
/*      */           playerComponent.setClientViewRadius(viewRadiusChunks);
/*      */           entityViewerComponent.viewRadiusBlocks = playerComponent.getViewRadius() * 32;
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
/*      */   public void handle(@Nonnull UpdateLanguage packet) {
/*  492 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  493 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  497 */     this.playerRef.setLanguage(packet.language);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handle(@Nonnull ClientOpenWindow packet) {
/*  506 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  507 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  511 */     Supplier<? extends Window> supplier = (Supplier<? extends Window>)Window.CLIENT_REQUESTABLE_WINDOW_TYPES.get(packet.type);
/*  512 */     if (supplier == null) throw new RuntimeException("Unable to process ClientOpenWindow packet. Window type is not supported!");
/*      */     
/*  514 */     Store<EntityStore> store = ref.getStore();
/*  515 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  517 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           UpdateWindow updateWindowPacket = playerComponent.getWindowManager().clientOpenWindow(supplier.get());
/*      */           if (updateWindowPacket != null) {
/*      */             writeNoCache((Packet)updateWindowPacket);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull SendWindowAction packet) {
/*  532 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  533 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  537 */     Store<EntityStore> store = ref.getStore();
/*  538 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  540 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           Window window = playerComponent.getWindowManager().getWindow(packet.id);
/*      */           if (window == null) {
/*      */             return;
/*      */           }
/*      */           if (window instanceof ValidatedWindow && !((ValidatedWindow)window).validate()) {
/*      */             window.close();
/*      */             return;
/*      */           } 
/*      */           window.handleAction(this.playerRef.getReference(), store, packet.action);
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
/*      */   public void handle(@Nonnull SyncPlayerPreferences packet) {
/*  564 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  565 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  569 */     Store<EntityStore> store = ref.getStore();
/*  570 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  572 */     world.execute(() -> {
/*      */           ComponentType<EntityStore, PlayerSettings> componentType = EntityModule.get().getPlayerSettingsComponentType();
/*      */           store.putComponent(ref, componentType, (Component)new PlayerSettings(packet.showEntityMarkers, packet.armorItemsPreferredPickupLocation, packet.weaponAndToolItemsPreferredPickupLocation, packet.usableItemsItemsPreferredPickupLocation, packet.solidBlockItemsPreferredPickupLocation, packet.miscItemsPreferredPickupLocation, new PlayerCreativeSettings(packet.allowNPCDetection, packet.respondToHit)));
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
/*      */   public void handle(@Nonnull ClientPlaceBlock packet) {
/*  586 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  587 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  591 */     Store<EntityStore> store = ref.getStore();
/*  592 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  594 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           Inventory inventory = playerComponent.getInventory();
/*      */           Vector3i targetBlock = new Vector3i(packet.position.x, packet.position.y, packet.position.z);
/*      */           BlockRotation blockRotation = new BlockRotation(packet.rotation.rotationYaw, packet.rotation.rotationPitch, packet.rotation.rotationRoll);
/*      */           TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*      */           if (transformComponent != null && playerComponent.getGameMode() != GameMode.Creative) {
/*      */             Vector3d position = transformComponent.getPosition();
/*      */             Vector3d blockCenter = new Vector3d(targetBlock.x + 0.5D, targetBlock.y + 0.5D, targetBlock.z + 0.5D);
/*      */             if (position.distanceSquaredTo(blockCenter) > 36.0D) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */           Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*      */           long chunkIndex = ChunkUtil.indexChunkFromBlock(targetBlock.x, targetBlock.z);
/*      */           Ref<ChunkStore> chunkReference = ((ChunkStore)chunkStore.getExternalData()).getChunkReference(chunkIndex);
/*      */           if (chunkReference == null) {
/*      */             return;
/*      */           }
/*      */           BlockChunk blockChunk = (BlockChunk)chunkStore.getComponent(chunkReference, BlockChunk.getComponentType());
/*      */           if (blockChunk == null) {
/*      */             return;
/*      */           }
/*      */           BlockSection section = blockChunk.getSectionAtBlockY(targetBlock.y);
/*      */           if (section == null) {
/*      */             return;
/*      */           }
/*      */           ItemStack itemInHand = playerComponent.getInventory().getItemInHand();
/*      */           if (itemInHand == null) {
/*      */             section.invalidateBlock(targetBlock.x, targetBlock.y, targetBlock.z);
/*      */             return;
/*      */           } 
/*      */           String heldBlockKey = itemInHand.getBlockKey();
/*      */           if (heldBlockKey == null) {
/*      */             section.invalidateBlock(targetBlock.x, targetBlock.y, targetBlock.z);
/*      */             return;
/*      */           } 
/*      */           if (packet.placedBlockId != -1) {
/*      */             String clientPlacedBlockTypeKey = ((BlockType)BlockType.getAssetMap().getAsset(packet.placedBlockId)).getId();
/*      */             BlockType heldBlockType = (BlockType)BlockType.getAssetMap().getAsset(heldBlockKey);
/*      */             if (heldBlockType != null && BlockPlaceUtils.canPlaceBlock(heldBlockType, clientPlacedBlockTypeKey)) {
/*      */               heldBlockKey = clientPlacedBlockTypeKey;
/*      */             }
/*      */           } 
/*      */           BlockPlaceUtils.placeBlock(ref, itemInHand, heldBlockKey, inventory.getHotbar(), Vector3i.ZERO, targetBlock, blockRotation, inventory, inventory.getActiveHotbarSlot(), (playerComponent.getGameMode() != GameMode.Creative), chunkReference, (ComponentAccessor)chunkStore, (ComponentAccessor)store);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull RemoveMapMarker packet) {
/*  668 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  669 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  673 */     Store<EntityStore> store = ref.getStore();
/*  674 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  676 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           PlayerWorldData perWorldData = playerComponent.getPlayerConfigData().getPerWorldData(world.getName());
/*      */           perWorldData.removeLastDeath(packet.markerId);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull CloseWindow packet) {
/*  691 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  692 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  696 */     Store<EntityStore> store = ref.getStore();
/*  697 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  699 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           playerComponent.getWindowManager().closeWindow(packet.id);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull UpdateServerAccess packet) {
/*  713 */     if (!Constants.SINGLEPLAYER) throw new IllegalArgumentException("UpdateServerAccess can only be used in singleplayer!"); 
/*  714 */     if (!SingleplayerModule.isOwner(this.playerRef)) {
/*  715 */       throw new IllegalArgumentException("UpdateServerAccess can only be by the owner of the singleplayer world!");
/*      */     }
/*      */     
/*  718 */     List<InetSocketAddress> publicAddresses = new CopyOnWriteArrayList<>();
/*      */     
/*  720 */     for (HostAddress host : packet.hosts) {
/*  721 */       publicAddresses.add(InetSocketAddress.createUnresolved(host.host, host.port & 0xFFFF));
/*      */     }
/*      */     
/*  724 */     SingleplayerModule singleplayerModule = SingleplayerModule.get();
/*  725 */     singleplayerModule.setPublicAddresses(publicAddresses);
/*  726 */     singleplayerModule.updateAccess(packet.access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull SetServerAccess packet) {
/*  735 */     if (!Constants.SINGLEPLAYER) throw new IllegalArgumentException("SetServerAccess can only be used in singleplayer!"); 
/*  736 */     if (!SingleplayerModule.isOwner(this.playerRef)) {
/*  737 */       throw new IllegalArgumentException("SetServerAccess can only be used by the owner of the singleplayer world!");
/*      */     }
/*      */     
/*  740 */     HytaleServerConfig config = HytaleServer.get().getConfig();
/*  741 */     if (config != null) {
/*  742 */       config.setPassword((packet.password != null) ? packet.password : "");
/*  743 */       HytaleServerConfig.save(config);
/*      */     } 
/*      */     
/*  746 */     SingleplayerModule.get().requestServerAccess(packet.access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull RequestMachinimaActorModel packet) {
/*  755 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(packet.modelId);
/*      */     
/*  757 */     writeNoCache((Packet)new SetMachinimaActorModel(
/*  758 */           Model.createUnitScaleModel(modelAsset).toPacket(), packet.sceneName, packet.actorName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull UpdateMachinimaScene packet) {
/*  769 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  770 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  774 */     Store<EntityStore> store = ref.getStore();
/*  775 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  777 */     world.execute(() -> {
/*      */           UpdateMachinimaScene updatePacket = new UpdateMachinimaScene(this.playerRef.getUsername(), packet.sceneName, packet.frame, packet.updateType, packet.scene);
/*      */           if ("*".equals(packet.player)) {
/*      */             for (PlayerRef otherPlayerRef : world.getPlayerRefs()) {
/*      */               if (!Objects.equals(otherPlayerRef, this.playerRef)) {
/*      */                 otherPlayerRef.getPacketHandler().writeNoCache((Packet)updatePacket);
/*      */               }
/*      */             } 
/*      */             this.playerRef.sendMessage(Message.translation("server.io.gamepackethandler.sceneUpdateSent"));
/*      */           } else {
/*      */             PlayerRef target = (PlayerRef)NameMatching.DEFAULT.find(Universe.get().getPlayers(), packet.player, PlayerRef::getUsername);
/*      */             if (target != null && ((EntityStore)target.getReference().getStore().getExternalData()).getWorld() == world) {
/*      */               target.getPacketHandler().write((Packet)updatePacket);
/*      */               this.playerRef.sendMessage(Message.translation("server.io.gamepackethander.sceneUpdateSentToPlayer").param("name", target.getUsername()));
/*      */             } else {
/*      */               this.playerRef.sendMessage(Message.translation("server.io.gamepackethandler.playerNotFound").param("name", packet.player));
/*      */             } 
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
/*      */   public void handle(@Nonnull ClientReady packet) {
/*  807 */     HytaleLogger.getLogger().at(Level.WARNING).log("%s: Received %s", getIdentifier(), packet);
/*      */     
/*  809 */     CompletableFuture<Void> future = this.clientReadyForChunksFuture;
/*  810 */     if (packet.readyForChunks && !packet.readyForGameplay && future != null) {
/*  811 */       this.clientReadyForChunksFutureStack = null;
/*  812 */       this.clientReadyForChunksFuture = null;
/*      */       
/*  814 */       future.completeAsync(() -> null);
/*      */     } 
/*      */     
/*  817 */     if (packet.readyForGameplay) {
/*  818 */       Ref<EntityStore> ref = this.playerRef.getReference();
/*  819 */       if (ref == null || !ref.isValid()) {
/*      */         return;
/*      */       }
/*      */       
/*  823 */       Store<EntityStore> store = ref.getStore();
/*  824 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */       
/*  826 */       world.execute(() -> {
/*      */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */             assert playerComponent != null;
/*      */             playerComponent.handleClientReady(false);
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
/*      */   public void handle(@Nonnull UpdateWorldMapVisible packet) {
/*  841 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  842 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  846 */     Store<EntityStore> store = ref.getStore();
/*  847 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  849 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           playerComponent.getWorldMapTracker().setClientHasWorldMapVisible(packet.visible);
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull TeleportToWorldMapMarker packet) {
/*  863 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  864 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  868 */     Store<EntityStore> store = ref.getStore();
/*  869 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  871 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           WorldMapTracker worldMapTracker = playerComponent.getWorldMapTracker();
/*      */           if (!worldMapTracker.isAllowTeleportToMarkers()) {
/*      */             disconnect("You are not allowed to use TeleportToWorldMapMarker!");
/*      */             return;
/*      */           } 
/*      */           MapMarker marker = (MapMarker)worldMapTracker.getSentMarkers().get(packet.id);
/*      */           if (marker != null) {
/*      */             world.getEntityStore().getStore().addComponent(this.playerRef.getReference(), Teleport.getComponentType(), (Component)new Teleport(null, PositionUtil.toTransform(marker.transform)));
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
/*      */   public void handle(@Nonnull TeleportToWorldMapPosition packet) {
/*  899 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  900 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  904 */     Store<EntityStore> store = ref.getStore();
/*  905 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  907 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           WorldMapTracker worldMapTracker = playerComponent.getWorldMapTracker();
/*      */           if (!worldMapTracker.isAllowTeleportToCoordinates()) {
/*      */             disconnect("You are not allowed to use TeleportToWorldMapMarker!");
/*      */             return;
/*      */           } 
/*      */           world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunkFromBlock(packet.x, packet.y)).thenAcceptAsync((), (Executor)world);
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
/*      */   public void handle(@Nonnull SyncInteractionChains packet) {
/*  937 */     Collections.addAll(this.interactionPacketQueue, packet.updates);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handle(@Nonnull MountMovement packet) {
/*  946 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  947 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/*  951 */     Store<EntityStore> store = ref.getStore();
/*  952 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/*  954 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           Ref<EntityStore> entityReference = world.getEntityStore().getRefFromNetworkId(playerComponent.getMountEntityId());
/*      */           if (entityReference == null || !entityReference.isValid()) {
/*      */             return;
/*      */           }
/*      */           TransformComponent transformComponent = (TransformComponent)store.getComponent(entityReference, TransformComponent.getComponentType());
/*      */           assert transformComponent != null;
/*      */           transformComponent.setPosition(PositionUtil.toVector3d(packet.absolutePosition));
/*      */           transformComponent.setRotation(PositionUtil.toRotation(packet.bodyOrientation));
/*      */           MovementStatesComponent movementStatesComponent = (MovementStatesComponent)store.getComponent(entityReference, MovementStatesComponent.getComponentType());
/*      */           assert movementStatesComponent != null;
/*      */           movementStatesComponent.setMovementStates(packet.movementStates);
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
/*      */   public void handle(@Nonnull SetPaused packet) {
/*  980 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  981 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  986 */     Store<EntityStore> store = ref.getStore();
/*  987 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  988 */     world.execute(() -> {
/*      */           if (world.getPlayerCount() != 1 || !Constants.SINGLEPLAYER) {
/*      */             return;
/*      */           }
/*      */           world.setPaused(packet.paused);
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
/*      */   public void handle(@Nonnull RequestFlyCameraMode packet) {
/* 1004 */     Ref<EntityStore> ref = this.playerRef.getReference();
/* 1005 */     if (ref == null || !ref.isValid()) {
/*      */       return;
/*      */     }
/*      */     
/* 1009 */     Store<EntityStore> store = ref.getStore();
/* 1010 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*      */     
/* 1012 */     world.execute(() -> {
/*      */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*      */           assert playerComponent != null;
/*      */           if (playerComponent.hasPermission("hytale.camera.flycam")) {
/*      */             writeNoCache((Packet)new SetFlyCameraMode(packet.entering));
/*      */             if (packet.entering) {
/*      */               this.playerRef.sendMessage(Message.translation("server.general.flyCamera.enabled"));
/*      */             } else {
/*      */               this.playerRef.sendMessage(Message.translation("server.general.flyCamera.disabled"));
/*      */             } 
/*      */           } else {
/*      */             this.playerRef.sendMessage(Message.translation("server.general.flyCamera.noPermission"));
/*      */           } 
/*      */         });
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\game\GamePacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */