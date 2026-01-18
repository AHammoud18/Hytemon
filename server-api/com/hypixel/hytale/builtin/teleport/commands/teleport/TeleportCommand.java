/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.commands.teleport.variant.TeleportOtherToPlayerCommand;
/*    */ import com.hypixel.hytale.builtin.teleport.commands.teleport.variant.TeleportPlayerToCoordinatesCommand;
/*    */ import com.hypixel.hytale.builtin.teleport.commands.teleport.variant.TeleportToCoordinatesCommand;
/*    */ import com.hypixel.hytale.builtin.teleport.commands.teleport.variant.TeleportToPlayerCommand;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeleportCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public TeleportCommand() {
/* 21 */     super("tp", "server.commands.tp.desc");
/* 22 */     setPermissionGroup(GameMode.Creative);
/* 23 */     addAliases(new String[] { "teleport" });
/*    */ 
/*    */     
/* 26 */     addUsageVariant((AbstractCommand)new TeleportToCoordinatesCommand());
/*    */ 
/*    */     
/* 29 */     addUsageVariant((AbstractCommand)new TeleportPlayerToCoordinatesCommand());
/*    */ 
/*    */     
/* 32 */     addUsageVariant((AbstractCommand)new TeleportToPlayerCommand());
/*    */ 
/*    */     
/* 35 */     addUsageVariant((AbstractCommand)new TeleportOtherToPlayerCommand());
/*    */ 
/*    */     
/* 38 */     addSubCommand((AbstractCommand)new TeleportAllCommand());
/* 39 */     addSubCommand((AbstractCommand)new TeleportHomeCommand());
/* 40 */     addSubCommand((AbstractCommand)new TeleportTopCommand());
/* 41 */     addSubCommand((AbstractCommand)new TeleportBackCommand());
/* 42 */     addSubCommand((AbstractCommand)new TeleportForwardCommand());
/* 43 */     addSubCommand((AbstractCommand)new TeleportHistoryCommand());
/* 44 */     addSubCommand((AbstractCommand)new TeleportWorldCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\TeleportCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */