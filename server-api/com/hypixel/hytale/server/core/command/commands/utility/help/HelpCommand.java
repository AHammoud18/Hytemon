/*     */ package com.hypixel.hytale.server.core.command.commands.utility.help;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.pages.CommandListPage;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class HelpCommand extends AbstractAsyncCommand {
/*     */   @Nonnull
/*  24 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HelpCommand() {
/*  30 */     super("help", "server.commands.help.desc");
/*  31 */     addAliases(new String[] { "?" });
/*  32 */     setPermissionGroup(GameMode.Adventure);
/*     */ 
/*     */     
/*  35 */     addUsageVariant((AbstractCommand)new HelpCommandVariant());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  41 */     return openHelpUI(context, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   static CompletableFuture<Void> openHelpUI(@Nonnull CommandContext context, @Nullable String initialCommand) {
/*  54 */     if (context.isPlayer()) {
/*  55 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*  56 */       if (playerRef == null || !playerRef.isValid()) {
/*  57 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*  58 */         return CompletableFuture.completedFuture(null);
/*     */       } 
/*     */       
/*  61 */       Store<EntityStore> store = playerRef.getStore();
/*  62 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */ 
/*     */       
/*  65 */       String resolvedCommand = resolveCommandName(initialCommand);
/*     */       
/*  67 */       return CompletableFuture.runAsync(() -> { Player playerComponent = (Player)store.getComponent(playerRef, Player.getComponentType()); PlayerRef playerRefComponent = (PlayerRef)store.getComponent(playerRef, PlayerRef.getComponentType()); if (playerComponent != null && playerRefComponent != null) playerComponent.getPageManager().openCustomPage(playerRef, store, (CustomUIPage)new CommandListPage(playerRefComponent, resolvedCommand));  }(Executor)world);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     return CompletableFuture.completedFuture(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String resolveCommandName(@Nullable String commandNameOrAlias) {
/*  88 */     if (commandNameOrAlias == null) {
/*  89 */       return null;
/*     */     }
/*     */     
/*  92 */     String lowerName = commandNameOrAlias.toLowerCase();
/*  93 */     Map<String, AbstractCommand> commands = CommandManager.get().getCommandRegistration();
/*     */ 
/*     */     
/*  96 */     if (commands.containsKey(lowerName)) {
/*  97 */       return lowerName;
/*     */     }
/*     */ 
/*     */     
/* 101 */     for (Map.Entry<String, AbstractCommand> entry : commands.entrySet()) {
/* 102 */       Set<String> aliases = ((AbstractCommand)entry.getValue()).getAliases();
/* 103 */       if (aliases != null && aliases.contains(lowerName)) {
/* 104 */         return entry.getKey();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 109 */     return lowerName;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class HelpCommandVariant
/*     */     extends AbstractAsyncCommand
/*     */   {
/*     */     @Nonnull
/* 117 */     private final RequiredArg<String> commandArg = withRequiredArg("command", "server.commands.help.command.name.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */     
/*     */     HelpCommandVariant() {
/* 121 */       super("server.commands.help.command.desc");
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 126 */       String commandName = (String)this.commandArg.get(context);
/* 127 */       return HelpCommand.openHelpUI(context, commandName);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\help\HelpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */