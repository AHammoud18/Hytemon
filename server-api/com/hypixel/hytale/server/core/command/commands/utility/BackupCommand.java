/*    */ package com.hypixel.hytale.server.core.command.commands.utility;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.Options;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BackupCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_COMMANDS_ERRORS_WAIT_FOR_BOOT = Message.translation("server.commands.errors.waitForBoot");
/*    */   
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_BACKUP_NOT_CONFIGURED = Message.translation("server.commands.backup.notConfigured");
/*    */   
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_BACKUP_STARTING = Message.translation("server.commands.backup.starting");
/*    */   
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_COMMANDS_BACKUP_COMPLETE = Message.translation("server.commands.backup.complete");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BackupCommand() {
/* 33 */     super("backup", "server.commands.backup.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 40 */     if (!HytaleServer.get().isBooted()) {
/* 41 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_WAIT_FOR_BOOT);
/* 42 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */ 
/*    */     
/* 46 */     if (!Options.getOptionSet().has(Options.BACKUP_DIRECTORY)) {
/* 47 */       context.sendMessage(MESSAGE_COMMANDS_BACKUP_NOT_CONFIGURED);
/* 48 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 51 */     context.sendMessage(MESSAGE_COMMANDS_BACKUP_STARTING);
/*    */ 
/*    */     
/* 54 */     return Universe.get().runBackup().thenAccept(aVoid -> context.sendMessage(MESSAGE_COMMANDS_BACKUP_COMPLETE));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\BackupCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */