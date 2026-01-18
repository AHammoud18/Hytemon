/*     */ package com.hypixel.hytale.builtin.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorActivateButtonEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorFetchAutoCompleteDataEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorRequestDataSetEvent;
/*     */ import com.hypixel.hytale.builtin.asseteditor.event.AssetEditorUpdateWeatherPreviewLockEvent;
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.event.IEventDispatcher;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.HostAddress;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorActivateButton;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorCreateAsset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorCreateAssetPack;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorCreateDirectory;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorDeleteAsset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorDeleteAssetPack;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorDeleteDirectory;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorExportAssets;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorFetchAsset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorFetchAutoCompleteData;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorFetchAutoCompleteDataReply;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorFetchJsonAssetWithParents;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorFetchLastModifiedAssets;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPopupNotificationType;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorRedoChanges;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorRenameAsset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorRenameDirectory;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorRequestChildrenList;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorRequestDataset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorRequestDatasetReply;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorSelectAsset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorSetGameTime;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorSubscribeModifiedAssetsChanges;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUndoChanges;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateAsset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateAssetPack;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateJsonAsset;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorUpdateWeatherPreviewLock;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetPath;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*     */ import com.hypixel.hytale.protocol.packets.connection.DisconnectType;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Pong;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.UpdateLanguage;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateEditorTimeOverride;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*     */ import com.hypixel.hytale.server.core.io.handlers.GenericPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class AssetEditorPacketHandler
/*     */   extends GenericPacketHandler
/*     */ {
/*  69 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   @Nonnull
/*     */   private final EditorClient editorClient;
/*     */   
/*     */   public AssetEditorPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, String language, @Nonnull PlayerAuthentication auth) {
/*  75 */     super(channel, protocolVersion);
/*  76 */     this.auth = auth;
/*  77 */     this.editorClient = new EditorClient(language, auth, (PacketHandler)this);
/*  78 */     init();
/*     */   }
/*     */   
/*     */   public AssetEditorPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, String language, UUID uuid, String username) {
/*  82 */     this(channel, protocolVersion, language, uuid, username, (byte[])null, (HostAddress)null);
/*     */   }
/*     */   
/*     */   public AssetEditorPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, String language, UUID uuid, String username, byte[] referralData, HostAddress referralSource) {
/*  86 */     super(channel, protocolVersion);
/*  87 */     this.auth = null;
/*  88 */     this.editorClient = new EditorClient(language, uuid, username, (PacketHandler)this);
/*     */     
/*  90 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  94 */     registerHandlers();
/*  95 */     AssetEditorPlugin.get().handleInitializeClient(this.editorClient);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public EditorClient getEditorClient() {
/* 100 */     return this.editorClient;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getIdentifier() {
/* 105 */     return "{Editor(" + NettyUtil.formatRemoteAddress(this.channel) + "), " + String.valueOf(this.editorClient.getUuid()) + ", " + this.editorClient.getUsername() + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public void closed(ChannelHandlerContext ctx) {
/* 110 */     AssetEditorPlugin.get().handleEditorClientDisconnected(this.editorClient, this.disconnectReason);
/*     */   }
/*     */   
/*     */   public void registerHandlers() {
/* 114 */     registerHandler(1, p -> handle((Disconnect)p));
/* 115 */     registerHandler(3, p -> handlePong((Pong)p));
/*     */     
/* 117 */     registerHandler(321, p -> handle((AssetEditorRequestChildrenList)p));
/* 118 */     registerHandler(324, p -> handle((AssetEditorUpdateAsset)p));
/* 119 */     registerHandler(323, p -> handle((AssetEditorUpdateJsonAsset)p));
/* 120 */     registerHandler(336, p -> handle((AssetEditorSelectAsset)p));
/* 121 */     registerHandler(310, p -> handle((AssetEditorFetchAsset)p));
/* 122 */     registerHandler(311, p -> handle((AssetEditorFetchJsonAssetWithParents)p));
/* 123 */     registerHandler(327, p -> handle((AssetEditorCreateAsset)p));
/* 124 */     registerHandler(307, p -> handle((AssetEditorCreateDirectory)p));
/* 125 */     registerHandler(333, p -> handle((AssetEditorRequestDataset)p));
/* 126 */     registerHandler(331, p -> handle((AssetEditorFetchAutoCompleteData)p));
/* 127 */     registerHandler(335, p -> handle((AssetEditorActivateButton)p));
/* 128 */     registerHandler(329, p -> handle((AssetEditorDeleteAsset)p));
/* 129 */     registerHandler(328, p -> handle((AssetEditorRenameAsset)p));
/* 130 */     registerHandler(308, p -> handle((AssetEditorDeleteDirectory)p));
/* 131 */     registerHandler(309, p -> handle((AssetEditorRenameDirectory)p));
/* 132 */     registerHandler(342, p -> handle((AssetEditorExportAssets)p));
/* 133 */     registerHandler(338, p -> handle((AssetEditorFetchLastModifiedAssets)p));
/* 134 */     registerHandler(349, p -> handle((AssetEditorUndoChanges)p));
/* 135 */     registerHandler(350, p -> handle((AssetEditorRedoChanges)p));
/* 136 */     registerHandler(341, p -> handle((AssetEditorSubscribeModifiedAssetsChanges)p));
/* 137 */     registerHandler(352, p -> handle((AssetEditorSetGameTime)p));
/* 138 */     registerHandler(354, p -> handle((AssetEditorUpdateWeatherPreviewLock)p));
/* 139 */     registerHandler(316, p -> handle((AssetEditorCreateAssetPack)p));
/* 140 */     registerHandler(315, p -> handle((AssetEditorUpdateAssetPack)p));
/* 141 */     registerHandler(317, p -> handle((AssetEditorDeleteAssetPack)p));
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorSubscribeModifiedAssetsChanges packet) {
/* 145 */     if (lacksPermission())
/*     */       return; 
/* 147 */     if (packet.subscribe) {
/* 148 */       AssetEditorPlugin.get().handleSubscribeToModifiedAssetsChanges(this.editorClient);
/*     */     } else {
/* 150 */       AssetEditorPlugin.get().handleUnsubscribeFromModifiedAssetsChanges(this.editorClient);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorUndoChanges packet) {
/* 155 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 157 */     LOGGER.at(Level.INFO).log("%s undoing last change", this.editorClient.getUsername());
/*     */     
/* 159 */     AssetEditorPlugin.get().handleUndo(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorRedoChanges packet) {
/* 163 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 165 */     LOGGER.at(Level.INFO).log("%s redoing last change", this.editorClient.getUsername());
/*     */     
/* 167 */     AssetEditorPlugin.get().handleRedo(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(AssetEditorFetchLastModifiedAssets packet) {
/* 171 */     if (lacksPermission())
/*     */       return; 
/* 173 */     AssetEditorPlugin.get().handleFetchLastModifiedAssets(this.editorClient);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorExportAssets packet) {
/* 177 */     if (lacksPermission())
/*     */       return; 
/* 179 */     StringBuilder assets = new StringBuilder();
/* 180 */     for (AssetPath assetPath : packet.paths) {
/* 181 */       if (!assets.isEmpty()) {
/* 182 */         assets.append(", ");
/*     */       }
/*     */       
/* 185 */       assets.append(assetPath.toString());
/*     */     } 
/*     */     
/* 188 */     LOGGER.at(Level.INFO).log("%s is exporting: %s", this.editorClient.getUsername(), assets.toString());
/*     */     
/* 190 */     ObjectArrayList<AssetPath> objectArrayList = new ObjectArrayList();
/* 191 */     for (AssetPath assetPath : packet.paths) {
/* 192 */       objectArrayList.add(new AssetPath(assetPath.pack, Path.of(assetPath.path, new String[0])));
/*     */     }
/*     */     
/* 195 */     AssetEditorPlugin.get().handleExportAssets(this.editorClient, (List<AssetPath>)objectArrayList);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorCreateAsset packet) {
/* 199 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 201 */     LOGGER.at(Level.INFO).log("%s is creating asset %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 203 */     AssetEditorPlugin.get().handleCreateAsset(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])), packet.data, packet.rebuildCaches, packet.buttonId, packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorFetchAsset packet) {
/* 207 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 209 */     LOGGER.at(Level.INFO).log("%s is fetching asset %s, from opened tab: %s", this.editorClient.getUsername(), packet.path, Boolean.valueOf(packet.isFromOpenedTab));
/*     */     
/* 211 */     AssetEditorPlugin.get().handleFetchAsset(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorFetchJsonAssetWithParents packet) {
/* 215 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 217 */     LOGGER.at(Level.INFO).log("%s is fetching json asset %s, from opened tab: %s", this.editorClient.getUsername(), packet.path, Boolean.valueOf(packet.isFromOpenedTab));
/*     */     
/* 219 */     AssetEditorPlugin.get().handleFetchJsonAssetWithParents(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])), packet.isFromOpenedTab, packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorRequestChildrenList packet) {
/* 223 */     if (lacksPermission())
/*     */       return; 
/* 225 */     LOGGER.at(Level.INFO).log("%s is requesting child ids for %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 227 */     AssetEditorPlugin.get().handleRequestChildIds(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])));
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorUpdateAsset packet) {
/* 231 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 233 */     LOGGER.at(Level.INFO).log("%s updating asset at %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 235 */     AssetEditorPlugin.get().handleAssetUpdate(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])), packet.data, packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorUpdateJsonAsset packet) {
/* 239 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 241 */     LOGGER.at(Level.INFO).log("%s updating json asset at %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 243 */     AssetEditorPlugin.get().handleJsonAssetUpdate(this.editorClient, new AssetPath(packet.path.pack, Path.of(packet.path.path, new String[0])), packet.assetType, packet.assetIndex, packet.commands, packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorFetchAutoCompleteData packet) {
/* 247 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 249 */     CompletableFutureUtil._catch(((CompletableFuture)HytaleServer.get().getEventBus().dispatchForAsync(AssetEditorFetchAutoCompleteDataEvent.class, packet.dataset).dispatch((IBaseEvent)new AssetEditorFetchAutoCompleteDataEvent(this.editorClient, packet.dataset, packet.query))).thenAccept(event -> {
/*     */             if (event.getResults() == null) {
/*     */               HytaleLogger.getLogger().at(Level.WARNING).log("Tried to request unknown autocomplete dataset for asset editor: %s", packet.dataset);
/*     */               return;
/*     */             } 
/*     */             this.editorClient.getPacketHandler().write((Packet)new AssetEditorFetchAutoCompleteDataReply(packet.token, event.getResults()));
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull AssetEditorRenameAsset packet) {
/* 260 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 262 */     LOGGER.at(Level.WARNING).log("%s is renaming %s to %s", this.editorClient.getUsername(), packet.path, packet.newPath);
/*     */     
/* 264 */     AssetEditorPlugin.get().handleRenameAsset(this.editorClient, new AssetPath(packet.path), new AssetPath(packet.newPath), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorDeleteAsset packet) {
/* 268 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 270 */     LOGGER.at(Level.INFO).log("%s is deleting asset %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 272 */     AssetEditorPlugin.get().handleDeleteAsset(this.editorClient, new AssetPath(packet.path), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorActivateButton packet) {
/* 276 */     if (lacksPermission())
/*     */       return; 
/* 278 */     AssetEditorPlugin.get().getLogger().at(Level.INFO).log("%s is activating button %s", this.editorClient.getUsername(), packet.buttonId);
/*     */     
/* 280 */     IEventDispatcher<AssetEditorActivateButtonEvent, AssetEditorActivateButtonEvent> dispatch = HytaleServer.get().getEventBus().dispatchFor(AssetEditorActivateButtonEvent.class, packet.buttonId);
/* 281 */     if (dispatch.hasListener()) dispatch.dispatch((IBaseEvent)new AssetEditorActivateButtonEvent(this.editorClient, packet.buttonId)); 
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorRequestDataset packet) {
/* 285 */     if (lacksPermission())
/*     */       return; 
/* 287 */     CompletableFutureUtil._catch(((CompletableFuture)HytaleServer.get().getEventBus().dispatchForAsync(AssetEditorRequestDataSetEvent.class, packet.name).dispatch((IBaseEvent)new AssetEditorRequestDataSetEvent(this.editorClient, packet.name, null))).thenAccept(event -> {
/*     */             if (event.getResults() == null) {
/*     */               HytaleLogger.getLogger().at(Level.WARNING).log("Tried to request unknown dataset list for asset editor: %s", packet.name);
/*     */               return;
/*     */             } 
/*     */             this.editorClient.getPacketHandler().write((Packet)new AssetEditorRequestDatasetReply(packet.name, event.getResults()));
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull AssetEditorSelectAsset packet) {
/* 298 */     if (lacksPermission())
/*     */       return; 
/* 300 */     LOGGER.at(Level.INFO).log("%s selecting %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 302 */     AssetEditorPlugin.get().handleSelectAsset(this.editorClient, (packet.path != null) ? new AssetPath(packet.path) : null);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorCreateDirectory packet) {
/* 306 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 308 */     LOGGER.at(Level.INFO).log("%s is creating directory %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 310 */     AssetEditorPlugin.get().handleCreateDirectory(this.editorClient, new AssetPath(packet.path), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorDeleteDirectory packet) {
/* 314 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 316 */     LOGGER.at(Level.INFO).log("%s is deleting directory %s", this.editorClient.getUsername(), packet.path);
/*     */     
/* 318 */     AssetEditorPlugin.get().handleDeleteDirectory(this.editorClient, new AssetPath(packet.path), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorRenameDirectory packet) {
/* 322 */     if (lacksPermission(packet.token))
/*     */       return; 
/* 324 */     LOGGER.at(Level.INFO).log("%s is renaming directory %s to $s", this.editorClient.getUsername(), packet.path, packet.newPath);
/*     */     
/* 326 */     AssetEditorPlugin.get().handleRenameDirectory(this.editorClient, new AssetPath(packet.path), new AssetPath(packet.newPath), packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull UpdateLanguage packet) {
/* 330 */     if (lacksPermission())
/*     */       return; 
/* 332 */     this.editorClient.setLanguage(packet.language);
/*     */     
/* 334 */     I18nModule.get().sendTranslations(this.editorClient.getPacketHandler(), this.editorClient.getLanguage());
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorSetGameTime packet) {
/* 338 */     if (lacksPermission())
/*     */       return; 
/* 340 */     PlayerRef player = this.editorClient.tryGetPlayer();
/* 341 */     if (player == null)
/*     */       return; 
/* 343 */     player.getPacketHandler().write((Packet)new UpdateEditorTimeOverride(packet.gameTime, packet.paused));
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorUpdateWeatherPreviewLock packet) {
/* 347 */     if (lacksPermission())
/*     */       return; 
/* 349 */     IEventDispatcher<AssetEditorUpdateWeatherPreviewLockEvent, AssetEditorUpdateWeatherPreviewLockEvent> dispatch = HytaleServer.get().getEventBus().dispatchFor(AssetEditorUpdateWeatherPreviewLockEvent.class);
/* 350 */     if (dispatch.hasListener()) dispatch.dispatch((IBaseEvent)new AssetEditorUpdateWeatherPreviewLockEvent(this.editorClient, packet.locked)); 
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorUpdateAssetPack packet) {
/* 354 */     if (lacksPermission("hytale.editor.packs.edit"))
/*     */       return; 
/* 356 */     LOGGER.at(Level.INFO).log("%s is updating the asset pack manifest for %s", this.editorClient.getUsername(), packet.id);
/*     */     
/* 358 */     AssetEditorPlugin.get().handleUpdateAssetPack(this.editorClient, packet.id, packet.manifest);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorDeleteAssetPack packet) {
/* 362 */     if (lacksPermission("hytale.editor.packs.delete"))
/*     */       return; 
/* 364 */     LOGGER.at(Level.INFO).log("%s is deleting the asset pack %s", this.editorClient.getUsername(), packet.id);
/*     */     
/* 366 */     AssetEditorPlugin.get().handleDeleteAssetPack(this.editorClient, packet.id);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull AssetEditorCreateAssetPack packet) {
/* 370 */     if (lacksPermission(packet.token, "hytale.editor.packs.create"))
/*     */       return; 
/* 372 */     LOGGER.at(Level.INFO).log("%s is creating a new asset pack: %s:%s", this.editorClient.getUsername(), packet.manifest.group, packet.manifest.name);
/*     */     
/* 374 */     AssetEditorPlugin.get().handleCreateAssetPack(this.editorClient, packet.manifest, packet.token);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull Disconnect packet) {
/* 378 */     switch (packet.type) { case Disconnect:
/* 379 */         this.disconnectReason.setClientDisconnectType(DisconnectType.Disconnect); break;
/* 380 */       case Crash: this.disconnectReason.setClientDisconnectType(DisconnectType.Crash);
/*     */         break; }
/*     */     
/* 383 */     LOGGER.at(Level.INFO).log("%s - %s at %s left with reason: %s - %s", this.editorClient
/* 384 */         .getUuid(), this.editorClient
/* 385 */         .getUsername(), 
/* 386 */         NettyUtil.formatRemoteAddress(this.channel), packet.type
/* 387 */         .name(), packet.reason);
/*     */ 
/*     */ 
/*     */     
/* 391 */     this.channel.close();
/*     */   }
/*     */   
/*     */   private boolean lacksPermission(int token) {
/* 395 */     if (!this.editorClient.hasPermission("hytale.editor.asset")) {
/* 396 */       this.editorClient.sendFailureReply(token, Messages.USAGE_DENIED_MESSAGE);
/* 397 */       return true;
/*     */     } 
/* 399 */     return false;
/*     */   }
/*     */   
/*     */   private boolean lacksPermission() {
/* 403 */     return lacksPermission("hytale.editor.asset");
/*     */   }
/*     */   
/*     */   private boolean lacksPermission(String permissionId) {
/* 407 */     if (!this.editorClient.hasPermission(permissionId)) {
/* 408 */       this.editorClient.sendPopupNotification(AssetEditorPopupNotificationType.Error, Messages.USAGE_DENIED_MESSAGE);
/* 409 */       return true;
/*     */     } 
/* 411 */     return false;
/*     */   }
/*     */   
/*     */   private boolean lacksPermission(int token, String permissionId) {
/* 415 */     if (!this.editorClient.hasPermission(permissionId)) {
/* 416 */       this.editorClient.sendFailureReply(token, Messages.USAGE_DENIED_MESSAGE);
/* 417 */       return true;
/*     */     } 
/* 419 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\AssetEditorPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */