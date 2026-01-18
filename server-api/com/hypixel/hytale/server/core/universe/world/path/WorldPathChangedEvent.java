/*    */ package com.hypixel.hytale.server.core.universe.world.path;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPathChangedEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   private WorldPath worldPath;
/*    */   
/*    */   public WorldPathChangedEvent(WorldPath worldPath) {
/* 15 */     Objects.requireNonNull(worldPath, "World path must not be null in an event");
/* 16 */     this.worldPath = worldPath;
/*    */   }
/*    */   
/*    */   public WorldPath getWorldPath() {
/* 20 */     return this.worldPath;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 26 */     return "WorldPathChangedEvent{worldPath=" + String.valueOf(this.worldPath) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\path\WorldPathChangedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */