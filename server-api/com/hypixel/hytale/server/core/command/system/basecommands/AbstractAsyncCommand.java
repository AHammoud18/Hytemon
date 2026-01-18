/*    */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import java.awt.Color;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractAsyncCommand
/*    */   extends AbstractCommand
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_MODULES_COMMAND_RUNTIME_ERROR = Message.translation("server.modules.command.runtimeError").color(Color.RED);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncCommand(@Nonnull String name, @Nonnull String description) {
/* 27 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/* 38 */     super(name, description, requiresConfirmation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncCommand(@Nonnull String description) {
/* 47 */     super(description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected final CompletableFuture<Void> execute(@Nonnull CommandContext context) {
/* 52 */     return executeAsync(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected abstract CompletableFuture<Void> executeAsync(@Nonnull CommandContext paramCommandContext);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Void> runAsync(@Nonnull CommandContext context, @Nonnull Runnable runnable, @Nonnull Executor executor) {
/* 74 */     return CompletableFuture.runAsync(() -> {
/*    */           try {
/*    */             runnable.run();
/* 77 */           } catch (Exception e) {
/*    */             context.sendMessage(MESSAGE_MODULES_COMMAND_RUNTIME_ERROR);
/*    */             ((HytaleLogger.Api)AbstractCommand.LOGGER.at(Level.SEVERE).withCause(e)).log("Exception while running that command:");
/*    */           } 
/*    */         }executor);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractAsyncCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */