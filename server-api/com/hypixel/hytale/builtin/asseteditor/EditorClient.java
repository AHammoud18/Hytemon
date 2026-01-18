/*     */ package com.hypixel.hytale.builtin.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.FormattedMessage;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPopupNotification;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.AssetEditorPopupNotificationType;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.FailureReply;
/*     */ import com.hypixel.hytale.protocol.packets.asseteditor.SuccessReply;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionHolder;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class EditorClient
/*     */   implements PermissionHolder
/*     */ {
/*     */   private String language;
/*     */   private final UUID uuid;
/*     */   private final String username;
/*     */   @Nullable
/*     */   private final PlayerAuthentication auth;
/*     */   private final PacketHandler packetHandler;
/*     */   
/*     */   public EditorClient(String language, @Nonnull PlayerAuthentication auth, PacketHandler packetHandler) {
/*  32 */     this.language = language;
/*  33 */     this.uuid = auth.getUuid();
/*  34 */     this.username = auth.getUsername();
/*  35 */     this.auth = auth;
/*  36 */     this.packetHandler = packetHandler;
/*     */   }
/*     */   
/*     */   public EditorClient(String language, UUID uuid, String username, PacketHandler packetHandler) {
/*  40 */     this.language = language;
/*  41 */     this.uuid = uuid;
/*  42 */     this.username = username;
/*  43 */     this.auth = null;
/*  44 */     this.packetHandler = packetHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EditorClient(@Nonnull PlayerRef playerRef) {
/*  55 */     this.language = playerRef.getLanguage();
/*  56 */     this.uuid = playerRef.getUuid();
/*  57 */     this.username = playerRef.getUsername();
/*  58 */     this.auth = null;
/*  59 */     this.packetHandler = playerRef.getPacketHandler();
/*     */   }
/*     */   
/*     */   public String getLanguage() {
/*  63 */     return this.language;
/*     */   }
/*     */   
/*     */   public void setLanguage(String language) {
/*  67 */     this.language = language;
/*     */   }
/*     */   
/*     */   public UUID getUuid() {
/*  71 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/*  75 */     return this.username;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerAuthentication getAuth() {
/*  80 */     return this.auth;
/*     */   }
/*     */   
/*     */   public PacketHandler getPacketHandler() {
/*  84 */     return this.packetHandler;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PlayerRef tryGetPlayer() {
/*  89 */     return Universe.get().getPlayer(this.uuid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission(@Nonnull String id) {
/*  94 */     return PermissionsModule.get().hasPermission(this.uuid, id);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission(@Nonnull String id, boolean def) {
/*  99 */     return PermissionsModule.get().hasPermission(this.uuid, id, def);
/*     */   }
/*     */   
/*     */   public void sendPopupNotification(AssetEditorPopupNotificationType type, @Nonnull Message message) {
/* 103 */     FormattedMessage msg = message.getFormattedMessage();
/* 104 */     getPacketHandler().write((Packet)new AssetEditorPopupNotification(type, msg));
/*     */   }
/*     */   
/*     */   public void sendSuccessReply(int token) {
/* 108 */     sendSuccessReply(token, null);
/*     */   }
/*     */   
/*     */   public void sendSuccessReply(int token, @Nullable Message message) {
/* 112 */     FormattedMessage msg = (message != null) ? message.getFormattedMessage() : null;
/* 113 */     getPacketHandler().write((Packet)new SuccessReply(token, msg));
/*     */   }
/*     */   
/*     */   public void sendFailureReply(int token, @Nonnull Message message) {
/* 117 */     FormattedMessage msg = message.getFormattedMessage();
/* 118 */     getPacketHandler().write((Packet)new FailureReply(token, msg));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\asseteditor\EditorClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */