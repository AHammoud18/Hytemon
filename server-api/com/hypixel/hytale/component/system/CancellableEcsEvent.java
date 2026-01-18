/*    */ package com.hypixel.hytale.component.system;
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
/*    */ public abstract class CancellableEcsEvent
/*    */   extends EcsEvent
/*    */   implements ICancellableEcsEvent
/*    */ {
/*    */   private boolean cancelled = false;
/*    */   
/*    */   public final boolean isCancelled() {
/* 22 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void setCancelled(boolean cancelled) {
/* 32 */     this.cancelled = cancelled;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\CancellableEcsEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */