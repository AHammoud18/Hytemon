/*     */ package com.hypixel.hytale.server.core.io.handlers.login;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.HostAddress;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.netty.ProtocolUtil;
/*     */ import com.hypixel.hytale.protocol.packets.auth.PasswordAccepted;
/*     */ import com.hypixel.hytale.protocol.packets.auth.PasswordRejected;
/*     */ import com.hypixel.hytale.protocol.packets.auth.PasswordResponse;
/*     */ import com.hypixel.hytale.protocol.packets.connection.Disconnect;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.ProtocolVersion;
/*     */ import com.hypixel.hytale.server.core.io.handlers.GenericConnectionPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.netty.NettyUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.time.Duration;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class PasswordPacketHandler
/*     */   extends GenericConnectionPacketHandler
/*     */ {
/*  35 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static final int PASSWORD_TIMEOUT_SECONDS = 30;
/*     */   private static final int MAX_PASSWORD_ATTEMPTS = 3;
/*     */   private static final int CHALLENGE_LENGTH = 32;
/*     */   private final UUID playerUuid;
/*     */   private final String username;
/*     */   private final byte[] referralData;
/*     */   private final HostAddress referralSource;
/*     */   private byte[] passwordChallenge;
/*     */   private final SetupHandlerSupplier setupHandlerSupplier;
/*  46 */   private int attemptsRemaining = 3;
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
/*     */   public PasswordPacketHandler(@Nonnull Channel channel, @Nonnull ProtocolVersion protocolVersion, @Nonnull String language, @Nonnull UUID playerUuid, @Nonnull String username, @Nullable byte[] referralData, @Nullable HostAddress referralSource, @Nullable byte[] passwordChallenge, @Nonnull SetupHandlerSupplier setupHandlerSupplier) {
/*  59 */     super(channel, protocolVersion, language);
/*  60 */     this.playerUuid = playerUuid;
/*  61 */     this.username = username;
/*  62 */     this.referralData = referralData;
/*  63 */     this.referralSource = referralSource;
/*  64 */     this.passwordChallenge = passwordChallenge;
/*  65 */     this.setupHandlerSupplier = setupHandlerSupplier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getIdentifier() {
/*  70 */     return "{Password(" + NettyUtil.formatRemoteAddress(this.channel) + "), " + this.username + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registered0(PacketHandler oldHandler) {
/*  76 */     Duration playTimeout = HytaleServer.get().getConfig().getConnectionTimeouts().getPlayTimeout();
/*  77 */     this.channel.pipeline().replace("timeOut", "timeOut", (ChannelHandler)new ReadTimeoutHandler(playTimeout
/*  78 */           .toMillis(), TimeUnit.MILLISECONDS));
/*     */     
/*  80 */     if (this.passwordChallenge == null || this.passwordChallenge.length == 0) {
/*     */       
/*  82 */       LOGGER.at(Level.FINE).log("No password required for %s, proceeding to setup", this.username);
/*  83 */       proceedToSetup();
/*     */     } else {
/*     */       
/*  86 */       LOGGER.at(Level.FINE).log("Waiting for password response from %s", this.username);
/*  87 */       setTimeout("password-timeout", () -> !this.registered, 30L, TimeUnit.SECONDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(@Nonnull Packet packet) {
/*  93 */     switch (packet.getId()) { case 1:
/*  94 */         handle((Disconnect)packet); return;
/*  95 */       case 15: handle((PasswordResponse)packet); return; }
/*  96 */      disconnect("Protocol error: unexpected packet " + packet.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(@Nonnull Disconnect packet) {
/* 101 */     this.disconnectReason.setClientDisconnectType(packet.type);
/* 102 */     LOGGER.at(Level.INFO).log("%s (%s) at %s left with reason: %s - %s", this.playerUuid, this.username, 
/*     */         
/* 104 */         NettyUtil.formatRemoteAddress(this.channel), packet.type
/* 105 */         .name(), packet.reason);
/* 106 */     ProtocolUtil.closeApplicationConnection(this.channel);
/*     */   }
/*     */   
/*     */   public void handle(@Nonnull PasswordResponse packet) {
/* 110 */     clearTimeout();
/*     */     
/* 112 */     if (this.passwordChallenge == null || this.passwordChallenge.length == 0) {
/* 113 */       LOGGER.at(Level.WARNING).log("Received unexpected PasswordResponse from %s - no password required", 
/* 114 */           NettyUtil.formatRemoteAddress(this.channel));
/* 115 */       disconnect("Protocol error: unexpected PasswordResponse");
/*     */       
/*     */       return;
/*     */     } 
/* 119 */     byte[] clientHash = packet.hash;
/* 120 */     if (clientHash == null || clientHash.length == 0) {
/* 121 */       LOGGER.at(Level.WARNING).log("Received empty password hash from %s", 
/* 122 */           NettyUtil.formatRemoteAddress(this.channel));
/* 123 */       disconnect("Invalid password response");
/*     */       
/*     */       return;
/*     */     } 
/* 127 */     String password = HytaleServer.get().getConfig().getPassword();
/* 128 */     if (password == null || password.isEmpty()) {
/*     */       
/* 130 */       LOGGER.at(Level.SEVERE).log("Password validation failed - no password configured but challenge was sent");
/* 131 */       disconnect("Server configuration error");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 136 */     byte[] expectedHash = computePasswordHash(this.passwordChallenge, password);
/* 137 */     if (expectedHash == null) {
/* 138 */       LOGGER.at(Level.SEVERE).log("Failed to compute password hash");
/* 139 */       disconnect("Server error");
/*     */       
/*     */       return;
/*     */     } 
/* 143 */     if (!MessageDigest.isEqual(expectedHash, clientHash)) {
/* 144 */       this.attemptsRemaining--;
/* 145 */       LOGGER.at(Level.WARNING).log("Invalid password from %s (%s), %d attempts remaining", this.username, 
/* 146 */           NettyUtil.formatRemoteAddress(this.channel), Integer.valueOf(this.attemptsRemaining));
/*     */       
/* 148 */       if (this.attemptsRemaining <= 0) {
/* 149 */         disconnect("Too many failed password attempts");
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 154 */       this.passwordChallenge = generateChallenge();
/* 155 */       write((Packet)new PasswordRejected(this.passwordChallenge, this.attemptsRemaining));
/*     */ 
/*     */       
/* 158 */       setTimeout("password-timeout", () -> !this.registered, 30L, TimeUnit.SECONDS);
/*     */       
/*     */       return;
/*     */     } 
/* 162 */     LOGGER.at(Level.INFO).log("Password accepted for %s (%s)", this.username, this.playerUuid);
/* 163 */     write((Packet)new PasswordAccepted());
/* 164 */     proceedToSetup();
/*     */   }
/*     */   
/*     */   private static byte[] generateChallenge() {
/* 168 */     byte[] challenge = new byte[32];
/* 169 */     (new SecureRandom()).nextBytes(challenge);
/* 170 */     return challenge;
/*     */   }
/*     */ 
/*     */   
/*     */   private void proceedToSetup() {
/* 175 */     this.auth = new PlayerAuthentication(this.playerUuid, this.username);
/* 176 */     if (this.referralData != null) {
/* 177 */       this.auth.setReferralData(this.referralData);
/*     */     }
/* 179 */     if (this.referralSource != null) {
/* 180 */       this.auth.setReferralSource(this.referralSource);
/*     */     }
/*     */     
/* 183 */     LOGGER.at(Level.INFO).log("Connection complete for %s (%s), transitioning to setup", this.username, this.playerUuid);
/*     */ 
/*     */     
/* 186 */     NettyUtil.setChannelHandler(this.channel, this.setupHandlerSupplier.create(this.channel, this.protocolVersion, this.language, this.auth));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static byte[] computePasswordHash(byte[] challenge, String password) {
/*     */     try {
/* 194 */       MessageDigest digest = MessageDigest.getInstance("SHA-256");
/* 195 */       digest.update(challenge);
/* 196 */       digest.update(password.getBytes(StandardCharsets.UTF_8));
/* 197 */       return digest.digest();
/* 198 */     } catch (NoSuchAlgorithmException e) {
/* 199 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface SetupHandlerSupplier {
/*     */     PacketHandler create(Channel param1Channel, ProtocolVersion param1ProtocolVersion, String param1String, PlayerAuthentication param1PlayerAuthentication);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\login\PasswordPacketHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */