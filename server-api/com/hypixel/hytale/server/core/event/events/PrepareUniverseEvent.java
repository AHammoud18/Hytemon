/*    */ package com.hypixel.hytale.server.core.event.events;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfigProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ @Deprecated
/*    */ public class PrepareUniverseEvent
/*    */   implements IEvent<Void> {
/*    */   private WorldConfigProvider worldConfigProvider;
/*    */   
/*    */   public PrepareUniverseEvent(WorldConfigProvider worldConfigProvider) {
/* 13 */     this.worldConfigProvider = worldConfigProvider;
/*    */   }
/*    */   
/*    */   public WorldConfigProvider getWorldConfigProvider() {
/* 17 */     return this.worldConfigProvider;
/*    */   }
/*    */   
/*    */   public void setWorldConfigProvider(WorldConfigProvider worldConfigProvider) {
/* 21 */     this.worldConfigProvider = worldConfigProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 27 */     return "PrepareUniverseEvent{worldConfigProvider=" + String.valueOf(this.worldConfigProvider) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\PrepareUniverseEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */