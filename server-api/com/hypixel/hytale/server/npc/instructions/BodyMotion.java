/*    */ package com.hypixel.hytale.server.npc.instructions;
/*    */ 
/*    */ import javax.annotation.Nullable;
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
/*    */ public interface BodyMotion
/*    */   extends Motion
/*    */ {
/*    */   @Nullable
/*    */   default BodyMotion getSteeringMotion() {
/* 20 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\instructions\BodyMotion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */