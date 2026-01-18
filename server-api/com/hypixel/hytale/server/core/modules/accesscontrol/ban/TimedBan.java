/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.ban;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import java.time.Duration;
/*    */ import java.time.Instant;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimedBan
/*    */   extends AbstractBan
/*    */ {
/*    */   private final Instant expiresOn;
/*    */   
/*    */   @Nonnull
/*    */   public static TimedBan fromJsonObject(@Nonnull JsonObject object) throws JsonParseException {
/*    */     try {
/* 23 */       UUID target = UUID.fromString(object.get("target").getAsString());
/* 24 */       UUID by = UUID.fromString(object.get("by").getAsString());
/*    */       
/* 26 */       Instant timestamp = Instant.ofEpochMilli(object.get("timestamp").getAsLong());
/* 27 */       Instant expiresOn = Instant.ofEpochMilli(object.get("expiresOn").getAsLong());
/*    */       
/* 29 */       String reason = null;
/*    */ 
/*    */       
/* 32 */       if (object.has("reason")) {
/* 33 */         reason = object.get("reason").getAsString();
/*    */       }
/*    */       
/* 36 */       return new TimedBan(target, by, timestamp, expiresOn, reason);
/* 37 */     } catch (Throwable throwable) {
/* 38 */       throw new JsonParseException(throwable);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TimedBan(UUID target, UUID by, Instant timestamp, Instant expiresOn, String reason) {
/* 45 */     super(target, by, timestamp, reason);
/*    */     
/* 47 */     this.expiresOn = expiresOn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInEffect() {
/* 52 */     return this.expiresOn.isAfter(Instant.now());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getType() {
/* 58 */     return "timed";
/*    */   }
/*    */   
/*    */   public Instant getExpiresOn() {
/* 62 */     return this.expiresOn;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Optional<String>> getDisconnectReason(UUID uuid) {
/* 68 */     Duration timeRemaining = Duration.between(Instant.now(), this.expiresOn);
/* 69 */     StringBuilder message = (new StringBuilder("You are temporarily banned for ")).append(StringUtil.humanizeTime(timeRemaining)).append('!');
/* 70 */     this.reason.ifPresent(s -> message.append(" Reason: ").append(s));
/* 71 */     return CompletableFuture.completedFuture(Optional.of(message.toString()));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public JsonObject toJsonObject() {
/* 77 */     JsonObject object = super.toJsonObject();
/*    */     
/* 79 */     object.addProperty("expiresOn", Long.valueOf(this.expiresOn.toEpochMilli()));
/*    */     
/* 81 */     return object;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\ban\TimedBan.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */