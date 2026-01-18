/*    */ package com.hypixel.hytale.builtin.instances.command;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
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
/*    */ 
/*    */ 
/*    */ public class InstancesEditCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public InstancesEditCommand() {
/* 37 */     super("edit", "server.commands.instances.edit.desc");
/* 38 */     addAliases(new String[] { "modify" });
/*    */     
/* 40 */     addSubCommand((AbstractCommand)new InstanceEditNewCommand());
/* 41 */     addSubCommand((AbstractCommand)new InstanceEditCopyCommand());
/* 42 */     addSubCommand((AbstractCommand)new InstanceEditLoadCommand());
/* 43 */     addSubCommand((AbstractCommand)new InstanceEditListCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstancesCommand$InstancesEditCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */