/*    */ package com.hypixel.hytale.builtin.parkour;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.parkour.commands.CheckpointAddCommand;
/*    */ import com.hypixel.hytale.builtin.parkour.commands.CheckpointRemoveCommand;
/*    */ import com.hypixel.hytale.builtin.parkour.commands.CheckpointResetCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParkourCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public ParkourCommand() {
/* 17 */     super("checkpoint", "server.commands.checkpoint.desc");
/* 18 */     addSubCommand((AbstractCommand)new CheckpointAddCommand());
/* 19 */     addSubCommand((AbstractCommand)new CheckpointRemoveCommand());
/* 20 */     addSubCommand((AbstractCommand)new CheckpointResetCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\ParkourCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */