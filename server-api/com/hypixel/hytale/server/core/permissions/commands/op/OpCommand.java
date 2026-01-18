/*    */ package com.hypixel.hytale.server.core.permissions.commands.op;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OpCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public OpCommand() {
/* 14 */     super("op", "server.commands.op.desc");
/*    */     
/* 16 */     addSubCommand((AbstractCommand)new OpSelfCommand());
/* 17 */     addSubCommand((AbstractCommand)new OpAddCommand());
/* 18 */     addSubCommand((AbstractCommand)new OpRemoveCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canGeneratePermission() {
/* 23 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\op\OpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */