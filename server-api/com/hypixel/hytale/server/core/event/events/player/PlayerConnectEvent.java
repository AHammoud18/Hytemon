/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerConnectEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   private final Holder<EntityStore> holder;
/*    */   private final PlayerRef playerRef;
/*    */   @Nullable
/*    */   private World world;
/*    */   
/*    */   public PlayerConnectEvent(@Nonnull Holder<EntityStore> holder, @Nonnull PlayerRef playerRef, @Nullable World world) {
/* 23 */     this.holder = holder;
/* 24 */     this.playerRef = playerRef;
/* 25 */     this.world = world;
/*    */   }
/*    */   
/*    */   public Holder<EntityStore> getHolder() {
/* 29 */     return this.holder;
/*    */   }
/*    */   
/*    */   public PlayerRef getPlayerRef() {
/* 33 */     return this.playerRef;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   @Deprecated
/*    */   public Player getPlayer() {
/* 39 */     return (Player)this.holder.getComponent(Player.getComponentType());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public World getWorld() {
/* 44 */     return this.world;
/*    */   }
/*    */   
/*    */   public void setWorld(@Nullable World world) {
/* 48 */     this.world = world;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 54 */     return "PlayerConnectEvent{world=" + String.valueOf(this.world) + "} " + super
/*    */       
/* 56 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerConnectEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */