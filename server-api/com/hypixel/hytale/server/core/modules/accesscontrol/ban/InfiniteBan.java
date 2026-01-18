/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.ban;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.time.Instant;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InfiniteBan
/*    */   extends AbstractBan
/*    */ {
/*    */   @Nonnull
/*    */   public static InfiniteBan fromJsonObject(@Nonnull JsonObject object) throws JsonParseException {
/*    */     try {
/* 21 */       UUID target = UUID.fromString(object.get("target").getAsString());
/* 22 */       UUID by = UUID.fromString(object.get("by").getAsString());
/*    */       
/* 24 */       Instant timestamp = Instant.ofEpochMilli(object.get("timestamp").getAsLong());
/*    */       
/* 26 */       String reason = null;
/*    */ 
/*    */       
/* 29 */       if (object.has("reason")) {
/* 30 */         reason = object.get("reason").getAsString();
/*    */       }
/*    */       
/* 33 */       return new InfiniteBan(target, by, timestamp, reason);
/* 34 */     } catch (Throwable throwable) {
/* 35 */       throw new JsonParseException(throwable);
/*    */     } 
/*    */   }
/*    */   
/*    */   public InfiniteBan(UUID target, UUID by, Instant timestamp, String reason) {
/* 40 */     super(target, by, timestamp, reason);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInEffect() {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getType() {
/* 51 */     return "infinite";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Optional<String>> getDisconnectReason(UUID uuid) {
/* 57 */     StringBuilder message = new StringBuilder("You are permanently banned!");
/* 58 */     this.reason.ifPresent(s -> message.append(" Reason: ").append(s));
/* 59 */     return CompletableFuture.completedFuture(Optional.of(message.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\ban\InfiniteBan.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */