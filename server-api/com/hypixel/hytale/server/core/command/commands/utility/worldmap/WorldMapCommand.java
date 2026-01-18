/*    */ package com.hypixel.hytale.server.core.command.commands.utility.worldmap;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMapCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public WorldMapCommand() {
/* 14 */     super("worldmap", "server.commands.worldmap.desc");
/* 15 */     addAliases(new String[] { "map" });
/* 16 */     addSubCommand((AbstractCommand)new WorldMapReloadCommand());
/* 17 */     addSubCommand((AbstractCommand)new WorldMapDiscoverCommand());
/* 18 */     addSubCommand((AbstractCommand)new WorldMapUndiscoverCommand());
/* 19 */     addSubCommand((AbstractCommand)new WorldMapClearMarkersCommand());
/* 20 */     addSubCommand((AbstractCommand)new WorldMapViewRadiusSubCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\worldmap\WorldMapCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */