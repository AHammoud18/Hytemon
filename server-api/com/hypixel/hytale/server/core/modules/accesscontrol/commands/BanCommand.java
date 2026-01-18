/*     */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.ban.InfiniteBan;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleBanProvider;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.util.AuthUtil;
/*     */ import java.time.Instant;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BanCommand
/*     */   extends AbstractAsyncCommand
/*     */ {
/*     */   @Nonnull
/*     */   private final HytaleBanProvider banProvider;
/*     */   @Nonnull
/*  38 */   private final RequiredArg<String> usernameArg = withRequiredArg("username", "server.commands.ban.username.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  44 */   private final OptionalArg<String> reasonArg = withOptionalArg("reason", "server.commands.ban.reason.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BanCommand(@Nonnull HytaleBanProvider banProvider) {
/*  52 */     super("ban", "server.commands.ban.desc");
/*  53 */     setUnavailableInSingleplayer(true);
/*  54 */     this.banProvider = banProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  60 */     String reason, username = (String)this.usernameArg.get(context);
/*     */ 
/*     */ 
/*     */     
/*  64 */     String rawInput = context.getInputString();
/*  65 */     int usernameIndex = rawInput.indexOf(username);
/*  66 */     if (usernameIndex != -1 && usernameIndex + username.length() < rawInput.length()) {
/*     */       
/*  68 */       String afterUsername = rawInput.substring(usernameIndex + username.length()).trim();
/*  69 */       reason = afterUsername.isEmpty() ? "No reason." : afterUsername;
/*     */     } else {
/*  71 */       reason = "No reason.";
/*     */     } 
/*     */     
/*  74 */     return AuthUtil.lookupUuid(username).thenCompose(uuid -> {
/*     */           if (this.banProvider.hasBan(uuid)) {
/*     */             context.sendMessage(Message.translation("server.modules.ban.alreadyBanned").param("name", username));
/*     */             return CompletableFuture.completedFuture(null);
/*     */           } 
/*     */           InfiniteBan ban = new InfiniteBan(uuid, context.sender().getUuid(), Instant.now(), reason);
/*     */           this.banProvider.modify(());
/*     */           PlayerRef player = Universe.get().getPlayer(uuid);
/*     */           if (player != null) {
/*     */             CompletableFuture<Optional<String>> disconnectReason = ban.getDisconnectReason(uuid);
/*     */             return disconnectReason.whenComplete(()).thenApply(());
/*     */           } 
/*     */           context.sendMessage(Message.translation("server.modules.ban.bannedWithReason").param("name", username).param("reason", reason));
/*     */           return CompletableFuture.completedFuture(null);
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\BanCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */