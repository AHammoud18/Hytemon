/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block.bulk;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ public class BlockBulkCommand extends AbstractCommandCollection {
/*    */   public BlockBulkCommand() {
/*  7 */     super("bulk", "server.commands.block.bulk.desc");
/*  8 */     setPermissionGroup(null);
/*    */     
/* 10 */     addSubCommand((AbstractCommand)new BlockBulkFindCommand());
/* 11 */     addSubCommand((AbstractCommand)new BlockBulkFindHereCommand());
/* 12 */     addSubCommand((AbstractCommand)new BlockBulkReplaceCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\bulk\BlockBulkCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */