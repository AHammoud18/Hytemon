/*    */ package com.hypixel.hytale.server.core.permissions.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PermCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PermCommand() {
/* 14 */     super("perm", "server.commands.perm.desc");
/* 15 */     addSubCommand((AbstractCommand)new PermGroupCommand());
/* 16 */     addSubCommand((AbstractCommand)new PermUserCommand());
/* 17 */     addSubCommand((AbstractCommand)new PermTestCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */