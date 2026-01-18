/*    */ package com.hypixel.hytale.server.core.command.commands.world.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public WorldGenCommand() {
/* 14 */     super("worldgen", "server.commands.worldgen.desc");
/* 15 */     addAliases(new String[] { "wg" });
/* 16 */     addSubCommand((AbstractCommand)new WorldGenBenchmarkCommand());
/* 17 */     addSubCommand((AbstractCommand)new WorldGenReloadCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\worldgen\WorldGenCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */