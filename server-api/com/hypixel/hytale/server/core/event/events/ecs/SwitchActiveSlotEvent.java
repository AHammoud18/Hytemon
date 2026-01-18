/*    */ package com.hypixel.hytale.server.core.event.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
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
/*    */ 
/*    */ 
/*    */ public class SwitchActiveSlotEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   private final int previousSlot;
/*    */   private final int inventorySectionId;
/*    */   private byte newSlot;
/*    */   private final boolean serverRequest;
/*    */   
/*    */   public SwitchActiveSlotEvent(int inventorySectionId, int previousSlot, byte newSlot, boolean serverRequest) {
/* 41 */     this.inventorySectionId = inventorySectionId;
/* 42 */     this.previousSlot = previousSlot;
/* 43 */     this.newSlot = newSlot;
/* 44 */     this.serverRequest = serverRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPreviousSlot() {
/* 51 */     return this.previousSlot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getNewSlot() {
/* 58 */     return this.newSlot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNewSlot(byte newSlot) {
/* 65 */     this.newSlot = newSlot;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isServerRequest() {
/* 72 */     return this.serverRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isClientRequest() {
/* 79 */     return !this.serverRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInventorySectionId() {
/* 86 */     return this.inventorySectionId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\SwitchActiveSlotEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */