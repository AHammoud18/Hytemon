/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
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
/*    */ public class PlayerReadyEvent
/*    */   extends PlayerEvent<String>
/*    */ {
/*    */   private final int readyId;
/*    */   
/*    */   public PlayerReadyEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, int readyId) {
/* 27 */     super(ref, player);
/* 28 */     this.readyId = readyId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getReadyId() {
/* 35 */     return this.readyId;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "PlayerReadyEvent{readyId=" + this.readyId + "} " + super
/*    */       
/* 43 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerReadyEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */