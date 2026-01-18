/*    */ package com.hypixel.hytale.server.core.asset.monitor;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PathEvent {
/*    */   private final EventKind eventKind;
/*    */   private final long timestamp;
/*    */   
/*    */   public PathEvent(EventKind eventKind, long timestamp) {
/* 10 */     this.eventKind = eventKind;
/* 11 */     this.timestamp = timestamp;
/*    */   }
/*    */   
/*    */   public EventKind getEventKind() {
/* 15 */     return this.eventKind;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 19 */     return this.timestamp;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 25 */     return "PathEvent{eventKind=" + String.valueOf(this.eventKind) + ", timestamp=" + this.timestamp + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\monitor\PathEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */