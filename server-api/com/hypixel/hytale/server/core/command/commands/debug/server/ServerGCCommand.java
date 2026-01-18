/*    */ package com.hypixel.hytale.server.core.command.commands.debug.server;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.FormatUtil;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerGCCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public ServerGCCommand() {
/* 19 */     super("gc", "server.commands.server.gc.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 24 */     long before = Runtime.getRuntime().freeMemory();
/* 25 */     System.gc();
/* 26 */     long after = Runtime.getRuntime().freeMemory();
/* 27 */     long freedBytes = before - after;
/*    */     
/* 29 */     if (freedBytes >= 0L) {
/* 30 */       context.sendMessage(Message.translation("server.commands.server.gc.forcedgc")
/* 31 */           .param("bytes", FormatUtil.bytesToString(freedBytes)));
/*    */     } else {
/* 33 */       context.sendMessage(Message.translation("server.commands.server.gc.forcedgc.increased")
/* 34 */           .param("bytes", FormatUtil.bytesToString(-freedBytes)));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\server\ServerGCCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */