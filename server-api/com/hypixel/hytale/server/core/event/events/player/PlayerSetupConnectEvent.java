/*     */ package com.hypixel.hytale.server.core.event.events.player;
/*     */ 
/*     */ import com.hypixel.hytale.event.ICancellable;
/*     */ import com.hypixel.hytale.event.IEvent;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.HostAddress;
/*     */ import com.hypixel.hytale.protocol.packets.auth.ClientReferral;
/*     */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerSetupConnectEvent
/*     */   implements IEvent<Void>, ICancellable
/*     */ {
/*     */   public static final String DEFAULT_REASON = "You have been disconnected from the server!";
/*     */   private final PacketHandler packetHandler;
/*     */   private final String username;
/*     */   private final UUID uuid;
/*     */   private final PlayerAuthentication auth;
/*     */   private final byte[] referralData;
/*     */   private final HostAddress referralSource;
/*     */   private boolean cancelled;
/*     */   private String reason;
/*     */   private ClientReferral clientReferral;
/*     */   
/*     */   public PlayerSetupConnectEvent(PacketHandler packetHandler, String username, UUID uuid, PlayerAuthentication auth, byte[] referralData, HostAddress referralSource) {
/*  37 */     this.packetHandler = packetHandler;
/*  38 */     this.username = username;
/*  39 */     this.uuid = uuid;
/*  40 */     this.auth = auth;
/*  41 */     this.referralData = referralData;
/*  42 */     this.referralSource = referralSource;
/*  43 */     this.reason = "You have been disconnected from the server!";
/*  44 */     this.cancelled = false;
/*     */   }
/*     */   
/*     */   public PacketHandler getPacketHandler() {
/*  48 */     return this.packetHandler;
/*     */   }
/*     */   
/*     */   public UUID getUuid() {
/*  52 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/*  56 */     return this.username;
/*     */   }
/*     */   
/*     */   public PlayerAuthentication getAuth() {
/*  60 */     return this.auth;
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
/*     */   @Nullable
/*     */   public byte[] getReferralData() {
/*  73 */     return this.referralData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReferralConnection() {
/*  82 */     return (this.referralData != null && this.referralData.length > 0);
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
/*     */   @Nullable
/*     */   public HostAddress getReferralSource() {
/*  95 */     return this.referralSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ClientReferral getClientReferral() {
/* 104 */     return this.clientReferral;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void referToServer(@Nonnull String host, int port) {
/* 114 */     referToServer(host, port, null);
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
/*     */   public void referToServer(@Nonnull String host, int port, @Nullable byte[] data) {
/* 126 */     int MAX_REFERRAL_DATA_SIZE = 4096;
/*     */     
/* 128 */     Objects.requireNonNull(host, "Host cannot be null");
/* 129 */     if (port <= 0 || port > 65535) {
/* 130 */       throw new IllegalArgumentException("Port must be between 1 and 65535");
/*     */     }
/*     */     
/* 133 */     if (data != null && data.length > 4096) {
/* 134 */       throw new IllegalArgumentException("Referral data exceeds maximum size of 4096 bytes (got " + data.length + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 139 */     HytaleLogger.getLogger().at(Level.INFO).log("Referring player %s (%s) to %s:%d with %d bytes of data", this.username, this.uuid, host, 
/*     */         
/* 141 */         Integer.valueOf(port), Integer.valueOf((data != null) ? data.length : 0));
/*     */ 
/*     */     
/* 144 */     this.clientReferral = new ClientReferral(new HostAddress(host, (short)port), data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReason() {
/* 151 */     return this.reason;
/*     */   }
/*     */   
/*     */   public void setReason(String reason) {
/* 155 */     Objects.requireNonNull(reason, "Reason can't be null");
/* 156 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 161 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCancelled(boolean cancelled) {
/* 166 */     this.cancelled = cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 172 */     return "PlayerSetupConnectEvent{username='" + this.username + "', uuid=" + String.valueOf(this.uuid) + ", auth=" + String.valueOf(this.auth) + ", referralData=" + (
/*     */ 
/*     */ 
/*     */       
/* 176 */       (this.referralData != null) ? ("" + this.referralData.length + " bytes") : "null") + ", referralSource=" + (
/* 177 */       (this.referralSource != null) ? (this.referralSource.host + ":" + this.referralSource.host) : "null") + ", cancelled=" + this.cancelled + ", reason='" + this.reason + "'}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerSetupConnectEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */