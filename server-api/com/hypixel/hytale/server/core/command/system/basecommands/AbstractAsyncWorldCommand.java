/*    */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractAsyncWorldCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_ERRORS_NO_WORLD = Message.translation("server.commands.errors.noWorld");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 26 */   private final OptionalArg<World> worldArg = withOptionalArg("world", "server.commands.worldthread.arg.desc", (ArgumentType)ArgTypes.WORLD);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncWorldCommand(@Nonnull String name, @Nonnull String description) {
/* 35 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncWorldCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/* 46 */     super(name, description, requiresConfirmation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncWorldCommand(@Nonnull String description) {
/* 55 */     super(description);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected final CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 62 */     World world = (World)this.worldArg.getProcessed(context);
/* 63 */     if (world == null) {
/* 64 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_NO_WORLD);
/* 65 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */ 
/*    */     
/* 69 */     return executeAsync(context, world);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected abstract CompletableFuture<Void> executeAsync(@Nonnull CommandContext paramCommandContext, @Nonnull World paramWorld);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractAsyncWorldCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */