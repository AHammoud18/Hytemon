/*    */ package com.hypixel.hytale.server.core.universe.world.events;
/*    */ 
/*    */ import com.hypixel.hytale.event.ICancellable;
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
/*    */ 
/*    */ public class AddWorldEvent
/*    */   extends WorldEvent
/*    */   implements ICancellable
/*    */ {
/*    */   private boolean cancelled = false;
/*    */   
/*    */   public AddWorldEvent(@Nonnull World world) {
/* 26 */     super(world);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 32 */     return "AddWorldEvent{cancelled=" + this.cancelled + "} " + super
/*    */       
/* 34 */       .toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 39 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 44 */     this.cancelled = cancelled;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\events\AddWorldEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */