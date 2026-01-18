/*    */ package com.hypixel.hytale.server.core.universe.world.events;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
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
/*    */ public abstract class WorldEvent
/*    */   implements IEvent<String>
/*    */ {
/*    */   @Nonnull
/*    */   private final World world;
/*    */   
/*    */   public WorldEvent(@Nonnull World world) {
/* 25 */     this.world = world;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public World getWorld() {
/* 33 */     return this.world;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 39 */     return "WorldEvent{world=" + String.valueOf(this.world) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\events\WorldEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */