/*    */ package com.hypixel.hytale.server.core.modules.singleplayer;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.protocol.packets.serveraccess.Access;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleplayerRequestAccessEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   private final Access access;
/*    */   
/*    */   public SingleplayerRequestAccessEvent(Access access) {
/* 17 */     this.access = access;
/*    */   }
/*    */   
/*    */   public Access getAccess() {
/* 21 */     return this.access;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 27 */     return "SingleplayerRequestAccessEvent{access=" + String.valueOf(this.access) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\singleplayer\SingleplayerRequestAccessEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */