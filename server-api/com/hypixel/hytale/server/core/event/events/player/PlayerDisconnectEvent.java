/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerDisconnectEvent
/*    */   extends PlayerRefEvent<Void>
/*    */ {
/*    */   public PlayerDisconnectEvent(@Nonnull PlayerRef playerRef) {
/* 19 */     super(playerRef);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PacketHandler.DisconnectReason getDisconnectReason() {
/* 27 */     return this.playerRef.getPacketHandler().getDisconnectReason();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 33 */     return "PlayerDisconnectEvent{playerRef=" + String.valueOf(this.playerRef) + "} " + super
/*    */       
/* 35 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerDisconnectEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */