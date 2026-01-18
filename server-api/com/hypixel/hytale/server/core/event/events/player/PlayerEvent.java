/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PlayerEvent<KeyType>
/*    */   implements IEvent<KeyType>
/*    */ {
/*    */   @Nonnull
/*    */   private final Ref<EntityStore> playerRef;
/*    */   @Nonnull
/*    */   private final Player player;
/*    */   
/*    */   public PlayerEvent(@Nonnull Ref<EntityStore> playerRef, @Nonnull Player player) {
/* 36 */     this.playerRef = playerRef;
/* 37 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Ref<EntityStore> getPlayerRef() {
/* 45 */     return this.playerRef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Player getPlayer() {
/* 53 */     return this.player;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "PlayerEvent{player=" + String.valueOf(this.player) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */