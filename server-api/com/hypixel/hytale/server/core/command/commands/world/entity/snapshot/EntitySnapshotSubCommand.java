/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity.snapshot;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntitySnapshotSubCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public EntitySnapshotSubCommand() {
/* 14 */     super("snapshot", "server.commands.entity.snapshot.desc");
/* 15 */     addAliases(new String[] { "snap" });
/* 16 */     addSubCommand((AbstractCommand)new EntitySnapshotLengthCommand());
/* 17 */     addSubCommand((AbstractCommand)new EntitySnapshotHistoryCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\snapshot\EntitySnapshotSubCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */