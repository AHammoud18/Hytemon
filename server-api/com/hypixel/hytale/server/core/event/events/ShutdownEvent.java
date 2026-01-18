/*    */ package com.hypixel.hytale.server.core.event.events;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ShutdownEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   public static final short DISCONNECT_PLAYERS = -48;
/*    */   public static final short UNBIND_LISTENERS = -40;
/*    */   public static final short SHUTDOWN_WORLDS = -32;
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 15 */     return "ShutdownEvent{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ShutdownEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */