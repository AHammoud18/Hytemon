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
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractPlayerCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG = Message.translation("server.commands.errors.playerOrArg")
/* 26 */     .param("option", "player");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractPlayerCommand(@Nonnull String name, @Nonnull String description) {
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
/*    */   public AbstractPlayerCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/* 46 */     super(name, description, requiresConfirmation);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractPlayerCommand(@Nonnull String description) {
/* 55 */     super(description);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected final CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 62 */     Ref<EntityStore> ref = context.senderAsPlayerRef();
/* 63 */     if (ref == null) {
/* 64 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG);
/* 65 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */ 
/*    */     
/* 69 */     if (!ref.isValid()) {
/* 70 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/* 71 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */ 
/*    */     
/* 75 */     Store<EntityStore> store = ref.getStore();
/* 76 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */ 
/*    */     
/* 79 */     return runAsync(context, () -> { PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType()); if (playerRefComponent == null) { context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD); return; }  execute(context, store, ref, playerRefComponent, world); }(Executor)world);
/*    */   }
/*    */   
/*    */   protected abstract void execute(@Nonnull CommandContext paramCommandContext, @Nonnull Store<EntityStore> paramStore, @Nonnull Ref<EntityStore> paramRef, @Nonnull PlayerRef paramPlayerRef, @Nonnull World paramWorld);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractPlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */