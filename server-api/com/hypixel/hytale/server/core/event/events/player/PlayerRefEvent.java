/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PlayerRefEvent<KeyType>
/*    */   implements IEvent<KeyType>
/*    */ {
/*    */   @Nonnull
/*    */   final PlayerRef playerRef;
/*    */   
/*    */   public PlayerRefEvent(@Nonnull PlayerRef playerRef) {
/* 27 */     this.playerRef = playerRef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PlayerRef getPlayerRef() {
/* 35 */     return this.playerRef;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "PlayerRefEvent{playerRef=" + String.valueOf(this.playerRef) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerRefEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */