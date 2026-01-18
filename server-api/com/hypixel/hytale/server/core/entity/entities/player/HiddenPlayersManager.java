/*    */ package com.hypixel.hytale.server.core.entity.entities.player;
/*    */ 
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HiddenPlayersManager
/*    */ {
/*    */   @Nonnull
/* 17 */   private final Set<UUID> hiddenPlayers = ConcurrentHashMap.newKeySet();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void hidePlayer(@Nonnull UUID uuid) {
/* 25 */     this.hiddenPlayers.add(uuid);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void showPlayer(@Nonnull UUID uuid) {
/* 37 */     this.hiddenPlayers.remove(uuid);
/*    */   }
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
/*    */   public boolean isPlayerHidden(@Nonnull UUID uuid) {
/* 53 */     return this.hiddenPlayers.contains(uuid);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\HiddenPlayersManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */