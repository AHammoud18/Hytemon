/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.ban;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.time.Instant;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class AbstractBan
/*    */   implements Ban
/*    */ {
/*    */   protected final UUID target;
/*    */   protected final UUID by;
/*    */   protected final Instant timestamp;
/*    */   @Nonnull
/*    */   protected final Optional<String> reason;
/*    */   
/*    */   public AbstractBan(UUID target, UUID by, Instant timestamp, String reason) {
/* 26 */     this.target = target;
/* 27 */     this.by = by;
/* 28 */     this.timestamp = timestamp;
/*    */     
/* 30 */     this.reason = Optional.ofNullable(reason);
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getTarget() {
/* 35 */     return this.target;
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getBy() {
/* 40 */     return this.by;
/*    */   }
/*    */ 
/*    */   
/*    */   public Instant getTimestamp() {
/* 45 */     return this.timestamp;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Optional<String> getReason() {
/* 51 */     return this.reason;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public JsonObject toJsonObject() {
/* 57 */     JsonObject object = new JsonObject();
/*    */     
/* 59 */     object.addProperty("type", getType());
/*    */     
/* 61 */     object.addProperty("target", this.target.toString());
/* 62 */     object.addProperty("by", this.by.toString());
/*    */     
/* 64 */     object.addProperty("timestamp", Long.valueOf(this.timestamp.toEpochMilli()));
/*    */     
/* 66 */     this.reason.ifPresent(s -> object.addProperty("reason", s));
/*    */     
/* 68 */     return object;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\ban\AbstractBan.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */