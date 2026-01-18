/*    */ package com.hypixel.hytale.server.core.universe.world.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.EcsEvent;
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
/*    */ public class MoonPhaseChangeEvent
/*    */   extends EcsEvent
/*    */ {
/*    */   private final int newMoonPhase;
/*    */   
/*    */   public MoonPhaseChangeEvent(int newMoonPhase) {
/* 21 */     this.newMoonPhase = newMoonPhase;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNewMoonPhase() {
/* 28 */     return this.newMoonPhase;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\events\ecs\MoonPhaseChangeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */