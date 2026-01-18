/*    */ package com.hypixel.hytale.server.core.command.commands.utility.lighting;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LightingSendCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public LightingSendCommand() {
/* 15 */     super("send", "server.commands.sendlighting.desc");
/* 16 */     addSubCommand((AbstractCommand)new LightingSendLocalCommand());
/* 17 */     addSubCommand((AbstractCommand)new LightingSendGlobalCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   private static class LightingSendLocalCommand
/*    */     extends LightingSendToggleCommand
/*    */   {
/*    */     public LightingSendLocalCommand() {
/* 25 */       super("local", "server.commands.sendlighting.local.desc", "server.commands.sendlighting.local.enabled.desc", "server.commands.sendlighting.localLightingStatus", () -> BlockChunk.SEND_LOCAL_LIGHTING_DATA, value -> BlockChunk.SEND_LOCAL_LIGHTING_DATA = value.booleanValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private static class LightingSendGlobalCommand
/*    */     extends LightingSendToggleCommand
/*    */   {
/*    */     public LightingSendGlobalCommand() {
/* 34 */       super("global", "server.commands.sendlighting.global.desc", "server.commands.sendlighting.global.enabled.desc", "server.commands.sendlighting.globalLightingStatus", () -> BlockChunk.SEND_GLOBAL_LIGHTING_DATA, value -> BlockChunk.SEND_GLOBAL_LIGHTING_DATA = value.booleanValue());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\lighting\LightingSendCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */