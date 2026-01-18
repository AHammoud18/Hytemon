/*    */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionStage;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractAsyncPlayerCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG = Message.translation("server.commands.errors.playerOrArg")
/* 25 */     .param("option", "player");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncPlayerCommand(@Nonnull String name, @Nonnull String description) {
/* 34 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncPlayerCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/* 45 */     super(name, description, requiresConfirmation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractAsyncPlayerCommand(@Nonnull String description) {
/* 54 */     super(description);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected final CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 61 */     Ref<EntityStore> ref = context.senderAsPlayerRef();
/* 62 */     if (ref == null) {
/* 63 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG);
/* 64 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */ 
/*    */     
/* 68 */     if (!ref.isValid()) {
/* 69 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/* 70 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */ 
/*    */     
/* 74 */     Store<EntityStore> store = ref.getStore();
/* 75 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */ 
/*    */     
/* 78 */     return CompletableFuture.supplyAsync(() -> { PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType()); if (playerRefComponent == null) { context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD); return null; }  return executeAsync(context, store, ref, playerRefComponent, world); }(Executor)world)
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 85 */       .thenCompose(future -> (future != null) ? future : CompletableFuture.completedFuture(null));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected abstract CompletableFuture<Void> executeAsync(@Nonnull CommandContext paramCommandContext, @Nonnull Store<EntityStore> paramStore, @Nonnull Ref<EntityStore> paramRef, @Nonnull PlayerRef paramPlayerRef, @Nonnull World paramWorld);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractAsyncPlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */