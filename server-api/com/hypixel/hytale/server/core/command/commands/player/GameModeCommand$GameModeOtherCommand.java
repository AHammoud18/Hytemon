/*     */ package com.hypixel.hytale.server.core.command.commands.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ 
/*     */ class GameModeOtherCommand
/*     */   extends CommandBase
/*     */ {
/*  75 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  81 */   private final RequiredArg<GameMode> gameModeArg = withRequiredArg("gamemode", "server.commands.gamemode.gamemode.desc", (ArgumentType)ArgTypes.GAME_MODE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  87 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GameModeOtherCommand() {
/*  93 */     super("server.commands.gamemode.other.desc");
/*  94 */     requirePermission(HytalePermissions.fromCommand("gamemode.other"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/*  99 */     PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 100 */     Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */     
/* 102 */     if (ref == null || !ref.isValid()) {
/* 103 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     Store<EntityStore> store = ref.getStore();
/* 108 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 110 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (playerComponent == null) {
/*     */             context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */             return;
/*     */           } 
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */           assert playerRefComponent != null;
/*     */           GameMode gameMode = (GameMode)this.gameModeArg.get(context);
/*     */           if (playerComponent.getGameMode() == gameMode) {
/*     */             context.sendMessage(Message.translation("server.commands.gamemode.alreadyInMode.other").param("username", playerRefComponent.getUsername()));
/*     */             return;
/*     */           } 
/*     */           Player.setGameMode(ref, gameMode, (ComponentAccessor)store);
/*     */           Message gameModeMessage = Message.translation("server.general.gamemodes." + gameMode.name().toLowerCase());
/*     */           context.sendMessage(Message.translation("server.commands.gamemode.success.other").param("mode", gameModeMessage).param("username", playerRefComponent.getUsername()));
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\GameModeCommand$GameModeOtherCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */