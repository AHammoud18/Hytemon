/*     */ package com.hypixel.hytale.server.core.command.commands.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
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
/*     */ public class HideCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public HideCommand() {
/*  33 */     super("hide", "server.commands.hide.desc");
/*  34 */     addUsageVariant((AbstractCommand)new HidePlayerCommand());
/*  35 */     addSubCommand((AbstractCommand)new ShowPlayerCommand());
/*  36 */     addSubCommand((AbstractCommand)new HideAllCommand());
/*  37 */     addSubCommand((AbstractCommand)new ShowAllCommand());
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
/*     */   
/*     */   static class HidePlayerCommand
/*     */     extends AbstractAsyncCommand
/*     */   {
/*     */     @Nonnull
/*  53 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  59 */     private final OptionalArg<PlayerRef> targetArg = withOptionalArg("target", "server.commands.hide.target.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HidePlayerCommand() {
/*  65 */       super("server.commands.hide.player.desc");
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  70 */       PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/*  71 */       Ref<EntityStore> ref = playerRef.getReference();
/*     */       
/*  73 */       if (ref == null || !ref.isValid()) {
/*  74 */         context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*  75 */         return CompletableFuture.completedFuture(null);
/*     */       } 
/*     */       
/*  78 */       Store<EntityStore> store = ref.getStore();
/*  79 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/*  81 */       return runAsync(context, () -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType()); if (uuidComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  UUID playerUuid = uuidComponent.getUuid(); if (this.targetArg.provided(context)) { PlayerRef targetPlayerRef = (PlayerRef)this.targetArg.get(context); Ref<EntityStore> targetRef = targetPlayerRef.getReference(); if (targetRef == null || !targetRef.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  if (targetRef.equals(ref)) { context.sendMessage(Message.translation("server.commands.hide.cantHideFromSelf")); return; }  Store<EntityStore> targetStore = targetRef.getStore(); Player targetPlayerComponent = (Player)targetStore.getComponent(targetRef, Player.getComponentType()); if (targetPlayerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  targetPlayerRef.getHiddenPlayersManager().hidePlayer(playerUuid); context.sendMessage(Message.translation("server.commands.hide.hiddenFrom").param("username", playerRef.getUsername()).param("targetUsername", targetPlayerRef.getUsername())); } else { Universe.get().getWorlds().forEach(()); context.sendMessage(Message.translation("server.commands.hide.hiddenFromAll").param("username", playerRef.getUsername())); }  }(Executor)world);
/*     */     }
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
/*     */   static class ShowPlayerCommand
/*     */     extends AbstractAsyncCommand
/*     */   {
/*     */     @Nonnull
/* 163 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 169 */     private final OptionalArg<PlayerRef> targetArg = withOptionalArg("target", "server.commands.hide.target.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ShowPlayerCommand() {
/* 175 */       super("show", "server.commands.hide.showPlayer.desc");
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 180 */       PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/* 181 */       Ref<EntityStore> ref = playerRef.getReference();
/*     */       
/* 183 */       if (ref == null || !ref.isValid()) {
/* 184 */         context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/* 185 */         return CompletableFuture.completedFuture(null);
/*     */       } 
/*     */       
/* 188 */       Store<EntityStore> store = ref.getStore();
/* 189 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/* 191 */       return runAsync(context, () -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType()); if (uuidComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  UUID playerUuid = uuidComponent.getUuid(); if (this.targetArg.provided(context)) { PlayerRef targetPlayerRef = (PlayerRef)this.targetArg.get(context); Ref<EntityStore> targetRef = targetPlayerRef.getReference(); if (targetRef == null || !targetRef.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  if (targetRef.equals(ref)) { context.sendMessage(Message.translation("server.commands.hide.cantHideFromSelf")); return; }  Store<EntityStore> targetStore = targetRef.getStore(); Player targetPlayerComponent = (Player)targetStore.getComponent(targetRef, Player.getComponentType()); if (targetPlayerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  targetPlayerRef.getHiddenPlayersManager().showPlayer(playerUuid); context.sendMessage(Message.translation("server.commands.hide.shownTo").param("username", playerRef.getUsername()).param("targetUsername", targetPlayerRef.getUsername())); } else { Universe.get().getWorlds().forEach(()); context.sendMessage(Message.translation("server.commands.hide.shownToAll").param("username", playerRef.getUsername())); }  }(Executor)world);
/*     */     }
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
/*     */   static class HideAllCommand
/*     */     extends CommandBase
/*     */   {
/*     */     HideAllCommand() {
/* 271 */       super("all", "server.commands.hide.all.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 276 */       Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
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
/* 301 */       context.sendMessage(Message.translation("server.commands.hide.allHiddenFromAll"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ShowAllCommand
/*     */     extends CommandBase
/*     */   {
/*     */     ShowAllCommand() {
/* 316 */       super("showall", "server.commands.hide.showAll.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 321 */       Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
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
/* 346 */       context.sendMessage(Message.translation("server.commands.hide.allShownToAll"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\HideCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */