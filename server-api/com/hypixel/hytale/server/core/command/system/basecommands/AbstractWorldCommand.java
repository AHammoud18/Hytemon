/*    */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractWorldCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_ERRORS_NO_WORLD = Message.translation("server.commands.errors.noWorld");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 28 */   private final OptionalArg<World> worldArg = withOptionalArg("world", "server.commands.worldthread.arg.desc", (ArgumentType)ArgTypes.WORLD);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractWorldCommand(@Nonnull String name, @Nonnull String description) {
/* 37 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractWorldCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/* 48 */     super(name, description, requiresConfirmation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractWorldCommand(@Nonnull String description) {
/* 57 */     super(description);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected final CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 63 */     World world = (World)this.worldArg.getProcessed(context);
/* 64 */     if (world == null) {
/* 65 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_NO_WORLD);
/* 66 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 69 */     Store<EntityStore> store = world.getEntityStore().getStore();
/*    */     
/* 71 */     return runAsync(context, () -> execute(context, world, store), (Executor)world);
/*    */   }
/*    */   
/*    */   protected abstract void execute(@Nonnull CommandContext paramCommandContext, @Nonnull World paramWorld, @Nonnull Store<EntityStore> paramStore);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractWorldCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */