/*     */ package com.hypixel.hytale.server.core.command.system;
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.commands.ParticleCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.DebugPlayerPositionCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.HitDetectionCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.HudManagerTestCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.LogCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.PIDCheckCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.TagPatternCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.component.hitboxcollision.HitboxCollisionCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.component.repulsion.RepulsionCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.debug.packs.PacksCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.player.DamageCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.player.GameModeCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.player.HideCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.player.ToggleBlockPlacementOverrideCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.player.WhereAmICommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.player.inventory.GiveCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.server.KickCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.utility.BackupCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.utility.ValidateCPBCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.utility.metacommands.CommandsCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.utility.net.NetworkCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.utility.sleep.SleepCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.world.SpawnBlockCommand;
/*     */ import com.hypixel.hytale.server.core.command.commands.world.entity.EntityCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.CommandException;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Deque;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CommandManager implements CommandOwner {
/*  51 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private static CommandManager instance;
/*     */   
/*     */   public static CommandManager get() {
/*  56 */     return instance;
/*     */   }
/*     */   
/*  59 */   private final Map<String, AbstractCommand> commandRegistration = (Map<String, AbstractCommand>)new Object2ObjectOpenHashMap();
/*  60 */   private final Map<String, String> aliases = (Map<String, String>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   public CommandManager() {
/*  63 */     instance = this;
/*     */   }
/*     */   
/*     */   public void shutdown() {
/*  67 */     this.aliases.clear();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, AbstractCommand> getCommandRegistration() {
/*  72 */     return this.commandRegistration;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerCommands() {
/*  77 */     registerSystemCommand((AbstractCommand)new ChunkCommand());
/*  78 */     registerSystemCommand((AbstractCommand)new LogCommand());
/*  79 */     registerSystemCommand((AbstractCommand)new PIDCheckCommand());
/*  80 */     registerSystemCommand((AbstractCommand)new PingCommand());
/*  81 */     registerSystemCommand((AbstractCommand)new WorldGenCommand());
/*  82 */     registerSystemCommand((AbstractCommand)new HitDetectionCommand());
/*  83 */     registerSystemCommand((AbstractCommand)new PacketStatsCommand());
/*  84 */     registerSystemCommand((AbstractCommand)new AssetsCommand());
/*  85 */     registerSystemCommand((AbstractCommand)new PacksCommand());
/*  86 */     registerSystemCommand((AbstractCommand)new ServerCommand());
/*  87 */     registerSystemCommand((AbstractCommand)new StressTestCommand());
/*  88 */     registerSystemCommand((AbstractCommand)new HitboxCollisionCommand());
/*  89 */     registerSystemCommand((AbstractCommand)new DebugPlayerPositionCommand());
/*  90 */     registerSystemCommand((AbstractCommand)new MessageTranslationTestCommand());
/*  91 */     registerSystemCommand((AbstractCommand)new HudManagerTestCommand());
/*  92 */     registerSystemCommand((AbstractCommand)new RepulsionCommand());
/*  93 */     registerSystemCommand((AbstractCommand)new StopNetworkChunkSendingCommand());
/*  94 */     registerSystemCommand((AbstractCommand)new ShowBuilderToolsHudCommand());
/*  95 */     registerSystemCommand((AbstractCommand)new VersionCommand());
/*  96 */     registerSystemCommand((AbstractCommand)new ParticleCommand());
/*  97 */     registerSystemCommand((AbstractCommand)new TagPatternCommand());
/*     */ 
/*     */     
/* 100 */     registerSystemCommand((AbstractCommand)new GameModeCommand());
/* 101 */     registerSystemCommand((AbstractCommand)new HideCommand());
/* 102 */     registerSystemCommand((AbstractCommand)new KillCommand());
/* 103 */     registerSystemCommand((AbstractCommand)new DamageCommand());
/* 104 */     registerSystemCommand((AbstractCommand)new SudoCommand());
/* 105 */     registerSystemCommand((AbstractCommand)new WhereAmICommand());
/* 106 */     registerSystemCommand((AbstractCommand)new WhoAmICommand());
/* 107 */     registerSystemCommand((AbstractCommand)new ReferCommand());
/* 108 */     registerSystemCommand((AbstractCommand)new ToggleBlockPlacementOverrideCommand());
/*     */ 
/*     */     
/* 111 */     registerSystemCommand((AbstractCommand)new GiveCommand());
/* 112 */     registerSystemCommand((AbstractCommand)new InventoryCommand());
/* 113 */     registerSystemCommand((AbstractCommand)new ItemStateCommand());
/*     */ 
/*     */     
/* 116 */     registerSystemCommand((AbstractCommand)new AuthCommand());
/* 117 */     registerSystemCommand((AbstractCommand)new KickCommand());
/* 118 */     registerSystemCommand((AbstractCommand)new MaxPlayersCommand());
/* 119 */     registerSystemCommand((AbstractCommand)new StopCommand());
/* 120 */     registerSystemCommand((AbstractCommand)new WhoCommand());
/*     */ 
/*     */     
/* 123 */     registerSystemCommand((AbstractCommand)new BackupCommand());
/* 124 */     registerSystemCommand((AbstractCommand)new ConvertPrefabsCommand());
/* 125 */     registerSystemCommand((AbstractCommand)new HelpCommand());
/* 126 */     registerSystemCommand((AbstractCommand)new NotifyCommand());
/* 127 */     registerSystemCommand((AbstractCommand)new EventTitleCommand());
/* 128 */     registerSystemCommand((AbstractCommand)new ValidateCPBCommand());
/* 129 */     registerSystemCommand((AbstractCommand)new WorldMapCommand());
/* 130 */     registerSystemCommand((AbstractCommand)new SoundCommand());
/* 131 */     registerSystemCommand((AbstractCommand)new StashCommand());
/* 132 */     registerSystemCommand((AbstractCommand)new SpawnBlockCommand());
/* 133 */     registerSystemCommand((AbstractCommand)new EntityCommand());
/* 134 */     registerSystemCommand((AbstractCommand)new PlayerCommand());
/* 135 */     registerSystemCommand((AbstractCommand)new LightingCommand());
/* 136 */     registerSystemCommand((AbstractCommand)new SleepCommand());
/* 137 */     registerSystemCommand((AbstractCommand)new NetworkCommand());
/* 138 */     registerSystemCommand((AbstractCommand)new CommandsCommand());
/*     */ 
/*     */     
/* 141 */     registerSystemCommand((AbstractCommand)new UpdateCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Set<String>> createVirtualPermissionGroups() {
/* 151 */     Object2ObjectOpenHashMap<String, Set<String>> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 152 */     for (AbstractCommand command : this.commandRegistration.values()) {
/* 153 */       for (Map.Entry<String, Set<String>> entry : command.getPermissionGroupsRecursive().entrySet()) {
/* 154 */         Set<String> permissionsForGroup = object2ObjectOpenHashMap.computeIfAbsent(entry.getKey(), k -> new HashSet());
/* 155 */         permissionsForGroup.addAll(entry.getValue());
/*     */       } 
/*     */     } 
/* 158 */     return (Map<String, Set<String>>)object2ObjectOpenHashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSystemCommand(@Nonnull AbstractCommand command) {
/* 167 */     command.setOwner(this);
/* 168 */     register(command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CommandRegistration register(@Nonnull AbstractCommand command) {
/* 179 */     String name = command.getName();
/* 180 */     if (name == null || name.isEmpty()) throw new IllegalArgumentException("Registered commands must define a name");
/*     */     
/* 182 */     this.commandRegistration.put(command.getName(), command);
/*     */     try {
/* 184 */       command.completeRegistration();
/* 185 */     } catch (Exception e) {
/* 186 */       String errorMessage = e.getMessage();
/* 187 */       if (e instanceof GeneralCommandException) { GeneralCommandException generalException = (GeneralCommandException)e;
/* 188 */         String messageText = generalException.getMessageText();
/* 189 */         if (messageText != null) {
/* 190 */           errorMessage = messageText;
/*     */         } }
/*     */       
/* 193 */       ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(e)).log("Failed to register command: %s%s", command
/* 194 */           .getName(), (errorMessage != null) ? (" - " + errorMessage) : "");
/* 195 */       return null;
/*     */     } 
/*     */     
/* 198 */     for (String alias : command.getAliases()) {
/* 199 */       this.aliases.put(alias, name);
/*     */     }
/*     */     
/* 202 */     return new CommandRegistration(command, () -> true, () -> {
/*     */           AbstractCommand remove = this.commandRegistration.remove(name);
/*     */           if (remove != null) {
/*     */             for (String alias : remove.getAliases()) {
/*     */               this.aliases.remove(alias);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> handleCommand(@Nonnull PlayerRef playerRef, @Nonnull String command) {
/* 214 */     Ref<EntityStore> ref = playerRef.getReference();
/* 215 */     if (ref == null) return new CompletableFuture<>();
/*     */     
/* 217 */     Store<EntityStore> store = ref.getStore();
/* 218 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 219 */     return handleCommand((CommandSender)playerComponent, command);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> handleCommand(@Nonnull CommandSender commandSender, @Nonnull String commandString) {
/* 224 */     Objects.requireNonNull(commandSender, "Command sender must not be null!");
/* 225 */     Objects.requireNonNull(commandString, "Command must not be null!");
/*     */     
/* 227 */     CompletableFuture<Void> future = new CompletableFuture<>();
/* 228 */     ForkJoinPool.commonPool().execute(() -> {
/*     */           Thread thread = Thread.currentThread();
/*     */           
/*     */           String oldName = thread.getName();
/*     */           
/*     */           thread.setName(oldName + " -- Running: " + oldName);
/*     */           
/*     */           try {
/*     */             LOGGER.at(Level.FINE).log("%s sent command: %s", commandSender.getDisplayName(), commandString);
/*     */             
/*     */             int endIndex = commandString.indexOf(' ');
/*     */             
/*     */             String commandName = ((endIndex < 0) ? commandString : commandString.substring(0, endIndex)).toLowerCase();
/*     */             
/*     */             AbstractCommand command = this.commandRegistration.get(commandName);
/*     */             if (command == null) {
/*     */               String key = this.aliases.get(commandName);
/*     */               if (key != null && this.commandRegistration.containsKey(key)) {
/*     */                 command = this.commandRegistration.get(key);
/*     */               }
/*     */             } 
/*     */             if (command == null) {
/*     */               commandSender.sendMessage(Message.translation("server.modules.command.notFound").param("cmd", commandString));
/*     */               future.complete(null);
/*     */               return;
/*     */             } 
/*     */             runCommand(commandSender, commandString, command, future);
/*     */           } finally {
/*     */             thread.setName(oldName);
/*     */           } 
/*     */         });
/* 259 */     return future;
/*     */   }
/*     */   
/*     */   private void runCommand(@Nonnull CommandSender commandSender, @Nonnull String commandInput, @Nonnull AbstractCommand abstractCommand, @Nonnull CompletableFuture<Void> future) {
/*     */     try {
/* 264 */       LOGGER.at(Level.INFO).log("%s executed command: %s", commandSender.getDisplayName(), commandInput);
/* 265 */       ParseResult parseResult = new ParseResult();
/* 266 */       List<String> tokens = Tokenizer.parseArguments(commandInput, parseResult);
/* 267 */       if (parseResult.failed()) {
/* 268 */         parseResult.sendMessages(commandSender);
/* 269 */         future.complete(null);
/*     */         return;
/*     */       } 
/* 272 */       ParserContext parserContext = ParserContext.of(tokens, parseResult);
/* 273 */       if (parseResult.failed()) {
/* 274 */         parseResult.sendMessages(commandSender);
/* 275 */         future.complete(null);
/*     */         
/*     */         return;
/*     */       } 
/* 279 */       CompletableFuture<Void> commandFuture = abstractCommand.acceptCall(commandSender, parserContext, parseResult);
/* 280 */       if (parseResult.failed()) {
/* 281 */         parseResult.sendMessages(commandSender);
/* 282 */         future.complete(null);
/*     */         
/*     */         return;
/*     */       } 
/* 286 */       if (commandFuture != null) {
/* 287 */         commandFuture.whenComplete((aVoid, throwable) -> {
/*     */               if (throwable != null) {
/*     */                 if (!CompletableFutureUtil.isCanceled(throwable) && !isInternalException(throwable)) {
/*     */                   ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause((Throwable)new SkipSentryException(throwable))).log("Failed to execute command %s for %s", commandInput, commandSender.getDisplayName());
/*     */                   commandSender.sendMessage(Message.translation("server.modules.command.error").param("cmd", commandInput).param("msg", throwable.getMessage()));
/*     */                 } 
/*     */                 future.completeExceptionally(throwable);
/*     */               } else {
/*     */                 future.complete(aVoid);
/*     */               } 
/*     */             });
/*     */       } else {
/* 299 */         future.complete(null);
/*     */       } 
/* 301 */     } catch (Throwable t) {
/* 302 */       if (t instanceof CommandException) { CommandException commandException = (CommandException)t;
/* 303 */         commandException.sendTranslatedMessage(commandSender); }
/*     */       else
/* 305 */       { ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(t)).log("Failed to execute command %s for %s", commandInput, commandSender.getDisplayName());
/*     */ 
/*     */         
/* 308 */         Message errorMsg = (t.getMessage() == null) ? Message.translation("server.modules.command.noProvidedExceptionMessage") : Message.raw(t.getMessage());
/* 309 */         commandSender.sendMessage(Message.translation("server.modules.command.error").param("cmd", commandInput).param("msg", errorMsg)); }
/*     */ 
/*     */       
/* 312 */       future.completeExceptionally(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInternalException(@Nonnull Throwable throwable) {
/* 323 */     if (throwable instanceof CommandException) {
/* 324 */       return true;
/*     */     }
/* 326 */     if (throwable instanceof java.util.concurrent.CompletionException && throwable.getCause() != null && throwable.getCause() != throwable) {
/* 327 */       return isInternalException(throwable.getCause());
/*     */     }
/* 329 */     return false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> handleCommands(@Nonnull CommandSender sender, @Nonnull Deque<String> commands) {
/* 334 */     return handleCommands0(sender, commands);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private CompletableFuture<Void> handleCommands0(@Nonnull CommandSender sender, @Nonnull Deque<String> commands) {
/* 339 */     if (commands.isEmpty()) return CompletableFuture.completedFuture(null); 
/* 340 */     return handleCommand(sender, commands.poll()).thenCompose(aVoid -> handleCommands0(sender, commands));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/* 346 */     return "HytaleServer";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\CommandManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */