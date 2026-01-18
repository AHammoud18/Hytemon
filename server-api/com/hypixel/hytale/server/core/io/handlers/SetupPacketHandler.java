/*     */ package com.hypixel.hytale.server.core.io.handlers;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.event.IEventDispatcher;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.Asset;
/*     */ import com.hypixel.hytale.protocol.HostAddress;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.netty.ProtocolUtil;
/*     */ import com.hypixel.hytale.protocol.packets.auth.ClientReferral;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*     */ import com.hypixel.hytale.protocol.packets.connection.DisconnectType;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.ServerInfo;
/*     */ import com.hypixel.hytale.protocol.packets.setup.PlayerOptions;
/*     */ import com.hypixel.hytale.protocol.packets.setup.RequestAssets;
/*     */ import com.hypixel.hytale.protocol.packets.setup.ViewRadius;
/*     */ import com.hypixel.hytale.protocol.packets.setup.WorldLoadFinished;
/*     */ import com.hypixel.hytale.protocol.packets.setup.WorldLoadProgress;
/*     */ import com.hypixel.hytale.protocol.packets.setup.WorldSettings;
/*     */ import com.hypixel.hytale.server.core.Constants;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.AssetRegistryLoader;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetModule;
/*     */ import com.hypixel.hytale.server.core.asset.common.PlayerCommonAssets;
/*     */ import com.hypixel.hytale.server.core.asset.common.events.SendCommonAssetsEvent;
/*     */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*     */ import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerSetupConnectEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerSetupDisconnectEvent;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*     */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.DumpUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SetupPacketHandler
/*     */   extends GenericConnectionPacketHandler
/*     */ {
/*     */   private final UUID uuid;
/*     */   private final String username;
/*     */   private final byte[] referralData;
/*     */   private final HostAddress referralSource;
/*     */   private PlayerCommonAssets assets;
/*     */   private boolean receivedRequest;
/*  62 */   private int clientViewRadiusChunks = 6;
/*     */   
/*     */   public SetupPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, String language, UUID uuid, String username) {
/*  65 */     this(channel, protocolVersion, language, uuid, username, (byte[])null, (HostAddress)null);
/*     */   }
/*     */   
/*     */   public SetupPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, String language, UUID uuid, String username, byte[] referralData, HostAddress referralSource) {
/*  69 */     super(channel, protocolVersion, language);
/*  70 */     this.uuid = uuid;
/*  71 */     this.username = username;
/*  72 */     this.referralData = referralData;
/*  73 */     this.referralSource = referralSource;
/*  74 */     this.auth = null;
/*     */ 
/*     */     
/*  77 */     if (referralData != null && referralData.length > 0) {
/*  78 */       HytaleLogger.getLogger().at(Level.INFO).log("Player %s connecting with %d bytes of referral data from %s:%d (unauthenticated - plugins must validate!)", username, 
/*     */           
/*  80 */           Integer.valueOf(referralData.length), 
/*  81 */           (referralSource != null) ? referralSource.host : "unknown", 
/*  82 */           Short.valueOf((referralSource != null) ? referralSource.port : 0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SetupPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, String language, @Nonnull PlayerAuthentication auth) {
/*  88 */     super(channel, protocolVersion, language);
/*  89 */     this.uuid = auth.getUuid();
/*  90 */     this.username = auth.getUsername();
/*  91 */     this.auth = auth;
/*  92 */     this.referralData = auth.getReferralData();
/*  93 */     this.referralSource = auth.getReferralSource();
/*     */     
/*  95 */     if (this.referralData != null && this.referralData.length > 0) {
/*  96 */       HytaleLogger.getLogger().at(Level.INFO).log("Player %s connecting with %d bytes of referral data from %s:%d (authenticated)", this.username, 
/*     */           
/*  98 */           Integer.valueOf(this.referralData.length), 
/*  99 */           (this.referralSource != null) ? this.referralSource.host : "unknown", 
/* 100 */           Short.valueOf((this.referralSource != null) ? this.referralSource.port : 0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getIdentifier() {
/* 107 */     return "{Setup(" + NettyUtil.formatRemoteAddress(this.channel) + "), " + this.username + ", " + String.valueOf(this.uuid) + ", " + ((this.auth != null) ? "SECURE" : "INSECURE") + "}";
/*     */   }
/*     */   
/*     */   public void registered0(@Nonnull PacketHandler oldHandler) {
/*     */     boolean enableCompression;
/* 112 */     setTimeout("send-world-settings", () -> (this.assets != null), 1L, TimeUnit.SECONDS);
/*     */ 
/*     */     
/* 115 */     PlayerSetupConnectEvent event = (PlayerSetupConnectEvent)HytaleServer.get().getEventBus().dispatchFor(PlayerSetupConnectEvent.class).dispatch((IBaseEvent)new PlayerSetupConnectEvent(this, this.username, this.uuid, this.auth, this.referralData, this.referralSource));
/* 116 */     if (event.isCancelled()) {
/* 117 */       disconnect(event.getReason());
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     ClientReferral clientReferral = event.getClientReferral();
/* 122 */     if (clientReferral != null) {
/* 123 */       writeNoCache((Packet)clientReferral);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 129 */     HytaleServerConfig serverConfig = HytaleServer.get().getConfig();
/* 130 */     if (!serverConfig.isLocalCompressionEnabled()) {
/*     */       
/* 132 */       enableCompression = !oldHandler.isLocalConnection();
/*     */     } else {
/*     */       
/* 135 */       enableCompression = true;
/*     */     } 
/* 137 */     oldHandler.setCompressionEnabled(enableCompression);
/*     */ 
/*     */     
/* 140 */     PlayerRef otherPlayer = Universe.get().getPlayer(this.uuid);
/* 141 */     if (otherPlayer != null) {
/* 142 */       HytaleLogger.getLogger().at(Level.INFO).log("Found match of player %s on %s", this.uuid, otherPlayer.getUsername());
/*     */       
/* 144 */       Channel otherPlayerChannel = otherPlayer.getPacketHandler().getChannel();
/*     */       
/* 146 */       if (NettyUtil.isFromSameOrigin(otherPlayerChannel, this.channel)) {
/*     */ 
/*     */         
/* 149 */         Ref<EntityStore> reference = otherPlayer.getReference();
/* 150 */         if (reference != null) {
/* 151 */           World world = ((EntityStore)reference.getStore().getExternalData()).getWorld();
/* 152 */           if (world != null) {
/* 153 */             CompletableFuture<Void> removalFuture = new CompletableFuture<>();
/*     */             
/* 155 */             world.execute(() -> {
/*     */                   otherPlayer.getPacketHandler().disconnect("You logged in again with the account!");
/*     */ 
/*     */                   
/*     */                   world.execute(());
/*     */                 });
/*     */ 
/*     */             
/* 163 */             removalFuture.join();
/*     */           } else {
/* 165 */             otherPlayer.getPacketHandler().disconnect("You logged in again with the account!");
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 172 */         disconnect("You are already logged in on that account!");
/* 173 */         otherPlayer.sendMessage(Message.translation("server.io.setuppackethandler.otherLoginAttempt"));
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 178 */     PacketHandler.logConnectionTimings(this.channel, "Load Player Config", Level.FINE);
/*     */     
/* 180 */     WorldSettings worldSettings = new WorldSettings();
/* 181 */     worldSettings.worldHeight = 320;
/*     */     
/* 183 */     Asset[] requiredAssets = CommonAssetModule.get().getRequiredAssets();
/* 184 */     this.assets = new PlayerCommonAssets(requiredAssets);
/* 185 */     worldSettings.requiredAssets = requiredAssets;
/*     */     
/* 187 */     write((Packet)worldSettings);
/* 188 */     write((Packet)new ServerInfo(HytaleServer.get().getServerName(), serverConfig.getMotd(), serverConfig.getMaxPlayers()));
/*     */     
/* 190 */     setTimeout("receive-assets-request", () -> this.receivedRequest, 120L, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(@Nonnull Packet packet) {
/* 195 */     switch (packet.getId()) { case 1:
/* 196 */         handle((Disconnect)packet); return;
/* 197 */       case 23: handle((RequestAssets)packet); return;
/* 198 */       case 32: handle((ViewRadius)packet); return;
/* 199 */       case 33: handle((PlayerOptions)packet); return; }
/* 200 */      disconnect("Protocol error: unexpected packet " + packet.getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closed(ChannelHandlerContext ctx) {
/* 206 */     super.closed(ctx);
/* 207 */     IEventDispatcher<PlayerSetupDisconnectEvent, PlayerSetupDisconnectEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(PlayerSetupDisconnectEvent.class);
/* 208 */     if (dispatcher.hasListener()) dispatcher.dispatch((IBaseEvent)new PlayerSetupDisconnectEvent(this.username, this.uuid, this.auth, this.disconnectReason));
/*     */     
/* 210 */     if (Constants.SINGLEPLAYER) {
/* 211 */       if (Universe.get().getPlayerCount() == 0) {
/* 212 */         HytaleLogger.getLogger().at(Level.INFO).log("No players left on singleplayer server shutting down!");
/* 213 */         HytaleServer.get().shutdownServer();
/* 214 */       } else if (SingleplayerModule.isOwner(this.auth, this.uuid)) {
/* 215 */         HytaleLogger.getLogger().at(Level.INFO).log("Owner left the singleplayer server shutting down!");
/* 216 */         Universe.get().getPlayers().forEach(p -> p.getPacketHandler().disconnect(this.username + " left! Shutting down singleplayer world!"));
/* 217 */         HytaleServer.get().shutdownServer();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull Disconnect packet) {
/* 223 */     this.disconnectReason.setClientDisconnectType(packet.type);
/* 224 */     HytaleLogger.getLogger().at(Level.INFO).log("%s - %s at %s left with reason: %s - %s", this.uuid, this.username, 
/*     */         
/* 226 */         NettyUtil.formatRemoteAddress(this.channel), packet.type
/* 227 */         .name(), packet.reason);
/* 228 */     ProtocolUtil.closeApplicationConnection(this.channel);
/*     */     
/* 230 */     if (packet.type == DisconnectType.Crash && Constants.SINGLEPLAYER && (
/* 231 */       Universe.get().getPlayerCount() == 0 || SingleplayerModule.isOwner(this.auth, this.uuid))) {
/* 232 */       DumpUtil.dump(true, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull RequestAssets packet) {
/* 237 */     if (this.receivedRequest) throw new IllegalArgumentException("Received duplicate RequestAssets!"); 
/* 238 */     this.receivedRequest = true;
/*     */     
/* 240 */     PacketHandler.logConnectionTimings(this.channel, "Request Assets", Level.FINE);
/*     */     
/* 242 */     CompletableFuture<Void> future = CompletableFutureUtil._catch(((CompletableFuture)HytaleServer.get().getEventBus().dispatchForAsync(SendCommonAssetsEvent.class).dispatch((IBaseEvent)new SendCommonAssetsEvent(this, packet.assets)))
/* 243 */         .thenAccept(event -> {
/*     */             if (!this.channel.isActive()) {
/*     */               return;
/*     */             }
/*     */             
/*     */             PacketHandler.logConnectionTimings(this.channel, "Send Common Assets", Level.FINE);
/*     */             
/*     */             this.assets.sent(event.getRequestedAssets());
/*     */             AssetRegistryLoader.sendAssets(this);
/*     */             I18nModule.get().sendTranslations(this, this.language);
/*     */             PacketHandler.logConnectionTimings(this.channel, "Send Config Assets", Level.FINE);
/*     */             write((Packet)new WorldLoadProgress("Loading world...", 0, 0));
/*     */             write((Packet)new WorldLoadFinished());
/* 256 */           }).exceptionally(throwable -> {
/*     */             if (!this.channel.isActive())
/*     */               return null; 
/*     */             disconnect("An exception occurred while trying to login!");
/*     */             throw new RuntimeException("Exception when player was joining", throwable);
/*     */           }));
/* 262 */     setTimeout("send-assets", () -> (future.isDone() || !future.cancel(true)), 120L, TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull ViewRadius packet) {
/* 266 */     this.clientViewRadiusChunks = MathUtil.ceil((packet.value / 32.0F));
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull PlayerOptions packet) {
/* 270 */     if (!this.receivedRequest) throw new IllegalArgumentException("Hasn't received RequestAssets yet!"); 
/* 271 */     PacketHandler.logConnectionTimings(this.channel, "Player Options", Level.FINE);
/*     */     
/* 273 */     if (!this.channel.isActive())
/*     */       return; 
/* 275 */     if (packet.skin != null) {
/*     */       try {
/* 277 */         CosmeticsModule.get().validateSkin(packet.skin);
/* 278 */       } catch (com.hypixel.hytale.server.core.cosmetics.CosmeticsModule.InvalidSkinException e) {
/* 279 */         disconnect("Invalid skin! " + e.getMessage());
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/* 284 */     CompletableFuture<Void> future = CompletableFutureUtil._catch(Universe.get().addPlayer(this.channel, this.language, this.protocolVersion, this.uuid, this.username, this.auth, this.clientViewRadiusChunks, packet.skin)
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 289 */         .thenAccept(player -> {
/*     */             if (!this.channel.isActive())
/*     */               return;  PacketHandler.logConnectionTimings(this.channel, "Add To Universe", Level.FINE);
/*     */             clearTimeout();
/* 293 */           }).exceptionally(throwable -> {
/*     */             if (!this.channel.isActive())
/*     */               return null; 
/*     */             disconnect("An exception occurred when adding to the universe!");
/*     */             throw new RuntimeException("Exception when player adding to universe", throwable);
/*     */           }));
/* 299 */     setTimeout("add-to-universe", () -> (future.isDone() || !future.cancel(true)), 60L, TimeUnit.SECONDS);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\SetupPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */