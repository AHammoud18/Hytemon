/*     */ package com.hypixel.hytale.server.core.command.system.basecommands;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionHolder;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractTargetPlayerCommand
/*     */   extends AbstractAsyncCommand
/*     */ {
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  38 */   private final OptionalArg<PlayerRef> playerArg = withOptionalArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTargetPlayerCommand(@Nonnull String name, @Nonnull String description) {
/*  47 */     super(name, description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTargetPlayerCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
/*  58 */     super(name, description, requiresConfirmation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTargetPlayerCommand(@Nonnull String description) {
/*  68 */     super(description);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*     */     Ref<EntityStore> sourceRef, targetRef;
/*  76 */     if (context.isPlayer()) {
/*  77 */       sourceRef = context.senderAsPlayerRef();
/*     */     } else {
/*  79 */       sourceRef = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (this.playerArg.provided(context)) {
/*  85 */       targetRef = ((PlayerRef)this.playerArg.get(context)).getReference();
/*  86 */       CommandUtil.requirePermission((PermissionHolder)context.sender(), HytalePermissions.fromCommand(getPermission() + ".other"));
/*  87 */     } else if (context.isPlayer()) {
/*  88 */       targetRef = context.senderAsPlayerRef();
/*     */     } else {
/*  90 */       context.sendMessage(Message.translation("server.commands.errors.playerOrArg")
/*  91 */           .param("option", "player"));
/*  92 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/*  96 */     if (targetRef == null || !targetRef.isValid()) {
/*  97 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*  98 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     Store<EntityStore> targetStore = targetRef.getStore();
/* 103 */     World targetWorld = ((EntityStore)targetStore.getExternalData()).getWorld();
/*     */ 
/*     */     
/* 106 */     return runAsync(context, () -> { PlayerRef playerRefComponent = (PlayerRef)targetStore.getComponent(targetRef, PlayerRef.getComponentType()); assert playerRefComponent != null; execute(context, sourceRef, targetRef, playerRefComponent, targetWorld, targetStore); }(Executor)targetWorld);
/*     */   }
/*     */   
/*     */   protected abstract void execute(@Nonnull CommandContext paramCommandContext, @Nullable Ref<EntityStore> paramRef1, @Nonnull Ref<EntityStore> paramRef2, @Nonnull PlayerRef paramPlayerRef, @Nonnull World paramWorld, @Nonnull Store<EntityStore> paramStore);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\basecommands\AbstractTargetPlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */