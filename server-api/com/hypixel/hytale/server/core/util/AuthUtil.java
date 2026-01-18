/*    */ package com.hypixel.hytale.server.core.util;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.NameMatching;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthUtil
/*    */ {
/*    */   @Nonnull
/*    */   @Deprecated
/*    */   public static CompletableFuture<UUID> lookupUuid(String username) {
/* 18 */     PlayerRef player = Universe.get().getPlayerByUsername(username, NameMatching.EXACT);
/* 19 */     if (player != null) {
/* 20 */       return CompletableFuture.completedFuture(player.getUuid());
/*    */     }
/* 22 */     return CompletableFuture.completedFuture(UUID.nameUUIDFromBytes(("NO_AUTH|" + username).getBytes(StandardCharsets.UTF_8)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\AuthUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */