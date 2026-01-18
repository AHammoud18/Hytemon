/*    */ package com.hypixel.hytale.builtin.teleport.commands.warp;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*    */ import com.hypixel.hytale.builtin.teleport.Warp;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarpReloadCommand
/*    */   extends CommandBase
/*    */ {
/* 24 */   private static final HytaleLogger logger = HytaleLogger.forEnclosingClass();
/*    */   
/* 26 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED = Message.translation("server.commands.teleport.warp.notLoaded");
/* 27 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_RELOADED = Message.translation("server.commands.teleport.warp.reloaded");
/* 28 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_FAILED_TO_RELOAD = Message.translation("server.commands.teleport.warp.failedToReload");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WarpReloadCommand() {
/* 34 */     super("reload", "server.commands.warp.reload.desc");
/* 35 */     requirePermission(HytalePermissions.fromCommand("warp.reload"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 45 */     if (!TeleportPlugin.get().isWarpsLoaded()) {
/* 46 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED);
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     Map<String, Warp> warps = TeleportPlugin.get().getWarps();
/*    */     
/*    */     try {
/* 53 */       TeleportPlugin.get().loadWarps();
/* 54 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_RELOADED.param("count", warps.size()));
/* 55 */     } catch (Throwable t) {
/* 56 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_FAILED_TO_RELOAD);
/* 57 */       ((HytaleLogger.Api)logger.at(Level.SEVERE).withCause(t)).log("Failed to reload warps:");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\warp\WarpReloadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */