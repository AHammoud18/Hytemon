/*    */ package com.hypixel.hytale.server.core.universe.world.commands.block;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.universe.world.commands.block.bulk.BlockBulkCommand;
/*    */ 
/*    */ public class BlockCommand extends AbstractCommandCollection {
/*    */   public BlockCommand() {
/*  9 */     super("block", "server.commands.block.desc");
/* 10 */     setPermissionGroup(GameMode.Creative);
/* 11 */     addAliases(new String[] { "blocks" });
/*    */     
/* 13 */     addSubCommand((AbstractCommand)new BlockSetCommand());
/* 14 */     addSubCommand((AbstractCommand)new BlockSetTickingCommand());
/* 15 */     addSubCommand((AbstractCommand)new BlockGetCommand());
/* 16 */     addSubCommand((AbstractCommand)new BlockGetStateCommand());
/* 17 */     addSubCommand((AbstractCommand)new BlockRowCommand());
/* 18 */     addSubCommand((AbstractCommand)new BlockBulkCommand());
/* 19 */     addSubCommand((AbstractCommand)new BlockInspectPhysicsCommand());
/* 20 */     addSubCommand((AbstractCommand)new BlockInspectFillerCommand());
/* 21 */     addSubCommand((AbstractCommand)new BlockInspectRotationCommand());
/* 22 */     addSubCommand((AbstractCommand)new BlockSetStateCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\block\BlockCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */