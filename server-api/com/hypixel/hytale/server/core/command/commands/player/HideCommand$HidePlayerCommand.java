/*     */ package com.hypixel.hytale.server.core.command.commands.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HidePlayerCommand
/*     */   extends AbstractAsyncCommand
/*     */ {
/*     */   @Nonnull
/*  53 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  59 */   private final OptionalArg<PlayerRef> targetArg = withOptionalArg("target", "server.commands.hide.target.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HidePlayerCommand() {
/*  65 */     super("server.commands.hide.player.desc");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  70 */     PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/*  71 */     Ref<EntityStore> ref = playerRef.getReference();
/*     */     
/*  73 */     if (ref == null || !ref.isValid()) {
/*  74 */       context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*  75 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/*  78 */     Store<EntityStore> store = ref.getStore();
/*  79 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  81 */     return runAsync(context, () -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType()); if (uuidComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  UUID playerUuid = uuidComponent.getUuid(); if (this.targetArg.provided(context)) { PlayerRef targetPlayerRef = (PlayerRef)this.targetArg.get(context); Ref<EntityStore> targetRef = targetPlayerRef.getReference(); if (targetRef == null || !targetRef.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  if (targetRef.equals(ref)) { context.sendMessage(Message.translation("server.commands.hide.cantHideFromSelf")); return; }  Store<EntityStore> targetStore = targetRef.getStore(); Player targetPlayerComponent = (Player)targetStore.getComponent(targetRef, Player.getComponentType()); if (targetPlayerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  targetPlayerRef.getHiddenPlayersManager().hidePlayer(playerUuid); context.sendMessage(Message.translation("server.commands.hide.hiddenFrom").param("username", playerRef.getUsername()).param("targetUsername", targetPlayerRef.getUsername())); } else { Universe.get().getWorlds().forEach(()); context.sendMessage(Message.translation("server.commands.hide.hiddenFromAll").param("username", playerRef.getUsername())); }  }(Executor)world);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\HideCommand$HidePlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */