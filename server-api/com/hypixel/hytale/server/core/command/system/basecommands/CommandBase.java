/*    */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public abstract class CommandBase
/*    */   extends AbstractCommand
/*    */ {
/*    */   public CommandBase(@Nonnull String name, @Nonnull String description) {
/* 22 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommandBase(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/* 33 */     super(name, description, requiresConfirmation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommandBase(@Nonnull String description) {
/* 42 */     super(description);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected final CompletableFuture<Void> execute(@Nonnull CommandContext context) {
/* 48 */     executeSync(context);
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   protected abstract void executeSync(@Nonnull CommandContext paramCommandContext);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\CommandBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */