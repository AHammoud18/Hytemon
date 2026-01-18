/*     */ package com.hypixel.hytale.server.core.io.handlers;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.netty.ProtocolUtil;
/*     */ import com.hypixel.hytale.protocol.packets.auth.ConnectAccept;
/*     */ import com.hypixel.hytale.protocol.packets.connection.ClientType;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Connect;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*     */ import com.hypixel.hytale.server.core.Constants;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*     */ import com.hypixel.hytale.server.core.io.handlers.login.AuthenticationPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.handlers.login.PasswordPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginManager;
/*     */ import io.netty.channel.Channel;
/*     */ import java.security.SecureRandom;
/*     */ import java.time.Duration;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InitialPacketHandler
/*     */   extends PacketHandler
/*     */ {
/*     */   private static final int MAX_REFERRAL_DATA_SIZE = 4096;
/*     */   @Nullable
/*     */   public static AuthenticationPacketHandler.AuthHandlerSupplier EDITOR_PACKET_HANDLER_SUPPLIER;
/*     */   private boolean receivedConnect;
/*     */   
/*     */   public InitialPacketHandler(@Nonnull Channel channel) {
/*  43 */     super(channel, null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getIdentifier() {
/*  48 */     return "{Initial(" + NettyUtil.formatRemoteAddress(this.channel) + ")}";
/*     */   }
/*     */ 
/*     */   
/*     */   public void registered0(PacketHandler oldHandler) {
/*  53 */     Duration initialTimeout = HytaleServer.get().getConfig().getConnectionTimeouts().getInitialTimeout();
/*  54 */     setTimeout("initial", () -> !this.registered, initialTimeout.toMillis(), TimeUnit.MILLISECONDS);
/*  55 */     PacketHandler.logConnectionTimings(this.channel, "Registered", Level.FINE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(@Nonnull Packet packet) {
/*  60 */     if (packet.getId() == 0) {
/*  61 */       handle((Connect)packet);
/*  62 */     } else if (packet.getId() == 1) {
/*  63 */       handle((Disconnect)packet);
/*     */     } else {
/*  65 */       disconnect("Protocol error: unexpected packet " + packet.getId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(@Nonnull String message) {
/*  71 */     if (this.receivedConnect) {
/*  72 */       super.disconnect(message);
/*     */     } else {
/*  74 */       HytaleLogger.getLogger().at(Level.INFO).log("Silently disconnecting %s because no Connect packet!", NettyUtil.formatRemoteAddress(this.channel));
/*  75 */       ProtocolUtil.closeConnection(this.channel);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull Connect packet) {
/*  80 */     this.receivedConnect = true;
/*  81 */     clearTimeout();
/*     */     
/*  83 */     PacketHandler.logConnectionTimings(this.channel, "Connect", Level.FINE);
/*     */     
/*  85 */     String clientProtocolHash = packet.protocolHash;
/*  86 */     if (clientProtocolHash.length() > 64) {
/*  87 */       disconnect("Invalid Protocol Hash! " + clientProtocolHash.length());
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     String expectedHash = "6708f121966c1c443f4b0eb525b2f81d0a8dc61f5003a692a8fa157e5e02cea9";
/*  92 */     if (!clientProtocolHash.equals(expectedHash)) {
/*  93 */       disconnect("Incompatible protocol!\nServer: " + expectedHash + "\nClient: " + clientProtocolHash);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  99 */     if (HytaleServer.get().isShuttingDown()) {
/* 100 */       disconnect("Server is shutting down!");
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     if (!HytaleServer.get().isBooted()) {
/* 105 */       disconnect("Server is booting up! Please try again in a moment. [" + String.valueOf(PluginManager.get().getState()) + "]");
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     ProtocolVersion protocolVersion = new ProtocolVersion(clientProtocolHash);
/*     */     
/* 111 */     String language = packet.language;
/* 112 */     if (language == null) language = "en-US";
/*     */ 
/*     */     
/* 115 */     boolean isTcpConnection = !(this.channel instanceof io.netty.handler.codec.quic.QuicStreamChannel);
/* 116 */     if (isTcpConnection) {
/* 117 */       HytaleLogger.getLogger().at(Level.INFO).log("TCP connection from %s - only insecure auth supported", 
/* 118 */           NettyUtil.formatRemoteAddress(this.channel));
/*     */     }
/*     */ 
/*     */     
/* 122 */     if (packet.uuid == null) {
/* 123 */       disconnect("Missing UUID");
/*     */       return;
/*     */     } 
/* 126 */     if (packet.username == null || packet.username.isEmpty()) {
/* 127 */       disconnect("Missing username");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 132 */     if (packet.referralData != null && packet.referralData.length > 4096) {
/* 133 */       HytaleLogger.getLogger().at(Level.WARNING).log("Rejecting connection from %s - referral data too large: %d bytes (max: %d)", packet.username, 
/*     */           
/* 135 */           Integer.valueOf(packet.referralData.length), Integer.valueOf(4096));
/* 136 */       disconnect("Referral data exceeds maximum size of 4096 bytes");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 141 */     boolean hasIdentityToken = (packet.identityToken != null && !packet.identityToken.isEmpty());
/* 142 */     boolean isEditorClient = (packet.clientType == ClientType.Editor);
/*     */     
/* 144 */     Options.AuthMode authMode = (Options.AuthMode)Options.getOptionSet().valueOf(Options.AUTH_MODE);
/* 145 */     if (hasIdentityToken && authMode == Options.AuthMode.AUTHENTICATED) {
/*     */       
/* 147 */       if (isTcpConnection) {
/* 148 */         HytaleLogger.getLogger().at(Level.WARNING).log("Rejecting authenticated connection from %s - TCP only supports insecure auth", 
/*     */             
/* 150 */             NettyUtil.formatRemoteAddress(this.channel));
/* 151 */         disconnect("TCP connections only support insecure authentication. Use QUIC for authenticated connections.");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 157 */       AuthenticationPacketHandler.AuthHandlerSupplier supplier = isEditorClient ? EDITOR_PACKET_HANDLER_SUPPLIER : SetupPacketHandler::new;
/* 158 */       if (isEditorClient && supplier == null) {
/* 159 */         disconnect("Editor isn't supported on this server!");
/*     */         
/*     */         return;
/*     */       } 
/* 163 */       HytaleLogger.getLogger().at(Level.INFO).log("Starting authenticated flow for %s (%s) from %s", packet.username, packet.uuid, 
/* 164 */           NettyUtil.formatRemoteAddress(this.channel));
/*     */       
/* 166 */       NettyUtil.setChannelHandler(this.channel, (PacketHandler)new AuthenticationPacketHandler(this.channel, protocolVersion, language, supplier, packet.clientType, packet.identityToken, packet.uuid, packet.username, packet.referralData, packet.referralSource));
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 172 */       if (authMode == Options.AuthMode.AUTHENTICATED) {
/* 173 */         HytaleLogger.getLogger().at(Level.WARNING).log("Rejecting development connection from %s - server requires authentication (auth-mode=%s)", 
/*     */             
/* 175 */             NettyUtil.formatRemoteAddress(this.channel), authMode);
/* 176 */         disconnect("This server requires authentication!");
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 181 */       if (authMode == Options.AuthMode.OFFLINE) {
/* 182 */         if (!Constants.SINGLEPLAYER) {
/* 183 */           HytaleLogger.getLogger().at(Level.WARNING).log("Rejecting connection from %s - offline mode is only valid in singleplayer", 
/*     */               
/* 185 */               NettyUtil.formatRemoteAddress(this.channel));
/* 186 */           disconnect("Offline mode is only available in singleplayer.");
/*     */           return;
/*     */         } 
/* 189 */         if (!SingleplayerModule.isOwner(null, packet.uuid)) {
/* 190 */           HytaleLogger.getLogger().at(Level.WARNING).log("Rejecting connection from %s (%s) - offline mode only allows the world owner (%s)", packet.username, packet.uuid, 
/*     */               
/* 192 */               SingleplayerModule.getUuid());
/* 193 */           disconnect("This world is in offline mode and only the owner can connect.");
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 198 */       HytaleLogger.getLogger().at(Level.INFO).log("Starting development flow for %s (%s) from %s", packet.username, packet.uuid, 
/* 199 */           NettyUtil.formatRemoteAddress(this.channel));
/*     */ 
/*     */       
/* 202 */       byte[] passwordChallenge = generatePasswordChallengeIfNeeded(packet.uuid);
/* 203 */       write((Packet)new ConnectAccept(passwordChallenge));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       PasswordPacketHandler.SetupHandlerSupplier setupSupplier = (isEditorClient && EDITOR_PACKET_HANDLER_SUPPLIER != null) ? ((ch, pv, lang, auth) -> EDITOR_PACKET_HANDLER_SUPPLIER.create(ch, pv, lang, auth)) : SetupPacketHandler::new;
/*     */       
/* 211 */       NettyUtil.setChannelHandler(this.channel, (PacketHandler)new PasswordPacketHandler(this.channel, protocolVersion, language, packet.uuid, packet.username, packet.referralData, packet.referralSource, passwordChallenge, setupSupplier));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] generatePasswordChallengeIfNeeded(UUID playerUuid) {
/* 220 */     String password = HytaleServer.get().getConfig().getPassword();
/* 221 */     if (password == null || password.isEmpty()) {
/* 222 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (Constants.SINGLEPLAYER) {
/* 227 */       UUID ownerUuid = SingleplayerModule.getUuid();
/* 228 */       if (ownerUuid != null && ownerUuid.equals(playerUuid)) {
/* 229 */         return null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 234 */     byte[] challenge = new byte[32];
/* 235 */     (new SecureRandom()).nextBytes(challenge);
/* 236 */     return challenge;
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull Disconnect packet) {
/* 240 */     this.disconnectReason.setClientDisconnectType(packet.type);
/* 241 */     HytaleLogger.getLogger().at(Level.WARNING).log("Disconnecting %s - Sent disconnect packet???", NettyUtil.formatRemoteAddress(this.channel));
/* 242 */     ProtocolUtil.closeApplicationConnection(this.channel);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\InitialPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */