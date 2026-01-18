/*    */ package com.hypixel.hytale.server.core.event.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.protocol.GameMode;
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
/*    */ public class ChangeGameModeEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nonnull
/*    */   private GameMode gameMode;
/*    */   
/*    */   public ChangeGameModeEvent(@Nonnull GameMode gameMode) {
/* 27 */     this.gameMode = gameMode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public GameMode getGameMode() {
/* 35 */     return this.gameMode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setGameMode(@Nonnull GameMode gameMode) {
/* 44 */     this.gameMode = gameMode;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\ChangeGameModeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */