/*    */ package com.hypixel.hytale.server.core.command.commands.utility.lighting;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
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
/*    */ class LightingSendGlobalCommand
/*    */   extends LightingSendToggleCommand
/*    */ {
/*    */   public LightingSendGlobalCommand() {
/* 34 */     super("global", "server.commands.sendlighting.global.desc", "server.commands.sendlighting.global.enabled.desc", "server.commands.sendlighting.globalLightingStatus", () -> BlockChunk.SEND_GLOBAL_LIGHTING_DATA, value -> BlockChunk.SEND_GLOBAL_LIGHTING_DATA = value.booleanValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\lighting\LightingSendCommand$LightingSendGlobalCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */