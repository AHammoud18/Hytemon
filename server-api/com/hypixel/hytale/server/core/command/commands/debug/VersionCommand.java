/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.java.ManifestUtil;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class VersionCommand
/*    */   extends CommandBase
/*    */ {
/* 12 */   private static final Message MESSAGE_RESPONSE = Message.translation("server.commands.version.response");
/* 13 */   private static final Message MESSAGE_RESPONSE_WITH_ENV = Message.translation("server.commands.version.response.withEnvironment");
/*    */   
/*    */   public VersionCommand() {
/* 16 */     super("version", "Displays version information about the currently running server");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 21 */     String version = ManifestUtil.getImplementationVersion();
/* 22 */     String patchline = ManifestUtil.getPatchline();
/*    */     
/* 24 */     if ("release".equals(patchline)) {
/* 25 */       context.sendMessage(MESSAGE_RESPONSE.param("version", version).param("patchline", patchline));
/*    */     } else {
/* 27 */       context.sendMessage(MESSAGE_RESPONSE_WITH_ENV
/* 28 */           .param("version", version)
/* 29 */           .param("patchline", patchline)
/* 30 */           .param("environment", "release"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\VersionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */