/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PlayerSetupDisconnectEvent
/*    */   implements IEvent<Void> {
/*    */   private final String username;
/*    */   private final UUID uuid;
/*    */   private final PlayerAuthentication auth;
/*    */   private final PacketHandler.DisconnectReason disconnectReason;
/*    */   
/*    */   public PlayerSetupDisconnectEvent(String username, UUID uuid, PlayerAuthentication auth, PacketHandler.DisconnectReason disconnectReason) {
/* 17 */     this.username = username;
/* 18 */     this.uuid = uuid;
/* 19 */     this.auth = auth;
/* 20 */     this.disconnectReason = disconnectReason;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 24 */     return this.username;
/*    */   }
/*    */   
/*    */   public UUID getUuid() {
/* 28 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public PlayerAuthentication getAuth() {
/* 32 */     return this.auth;
/*    */   }
/*    */   
/*    */   public PacketHandler.DisconnectReason getDisconnectReason() {
/* 36 */     return this.disconnectReason;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 42 */     return "PlayerSetupDisconnectEvent{username='" + this.username + "', uuid=" + String.valueOf(this.uuid) + ", auth=" + String.valueOf(this.auth) + ", disconnectReason=" + String.valueOf(this.disconnectReason) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerSetupDisconnectEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */