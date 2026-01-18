/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
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
/*    */ 
/*    */ 
/*    */ public class AddPlayerToWorldEvent
/*    */   implements IEvent<String>
/*    */ {
/*    */   @Nonnull
/*    */   private final Holder<EntityStore> holder;
/*    */   @Nonnull
/*    */   private final World world;
/*    */   private boolean broadcastJoinMessage = true;
/*    */   
/*    */   public AddPlayerToWorldEvent(@Nonnull Holder<EntityStore> holder, @Nonnull World world) {
/* 39 */     this.holder = holder;
/* 40 */     this.world = world;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Holder<EntityStore> getHolder() {
/* 48 */     return this.holder;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public World getWorld() {
/* 56 */     return this.world;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldBroadcastJoinMessage() {
/* 63 */     return this.broadcastJoinMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBroadcastJoinMessage(boolean broadcastJoinMessage) {
/* 72 */     this.broadcastJoinMessage = broadcastJoinMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 78 */     return "AddPlayerToWorldEvent{world=" + String.valueOf(this.world) + ", broadcastJoinMessage=" + this.broadcastJoinMessage + "} " + super
/*    */ 
/*    */       
/* 81 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\AddPlayerToWorldEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */