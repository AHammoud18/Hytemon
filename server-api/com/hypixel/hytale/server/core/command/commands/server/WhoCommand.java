/*    */ package com.hypixel.hytale.server.core.command.commands.server;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WhoCommand extends AbstractAsyncCommand {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_WHO_PLAYER_WITH_DISPLAY_NAME = Message.translation("server.commands.who.playerWithDisplayName");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WhoCommand() {
/* 28 */     super("who", "server.commands.who.desc");
/* 29 */     setPermissionGroup(GameMode.Adventure);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 34 */     Map<String, World> worlds = Universe.get().getWorlds();
/* 35 */     ObjectArrayList<CompletableFuture<Void>> futures = new ObjectArrayList();
/*    */ 
/*    */     
/* 38 */     for (Iterator<Map.Entry<String, World>> iterator = worlds.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<String, World> entry = iterator.next();
/* 39 */       World world = entry.getValue();
/*    */       
/* 41 */       CompletableFuture<Void> future = runAsync(context, () -> { Store<EntityStore> store = world.getEntityStore().getStore(); Collection<PlayerRef> playerRefs = world.getPlayerRefs(); ObjectArrayList<Message> objectArrayList = new ObjectArrayList(); for (PlayerRef playerRef : playerRefs) { Ref<EntityStore> ref = playerRef.getReference(); if (ref == null || !ref.isValid()) continue;  Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) continue;  DisplayNameComponent displayNameComponent = (DisplayNameComponent)store.getComponent(ref, DisplayNameComponent.getComponentType()); assert displayNameComponent != null; Message displayName = displayNameComponent.getDisplayName(); if (displayName != null) { objectArrayList.add(MESSAGE_COMMANDS_WHO_PLAYER_WITH_DISPLAY_NAME.param("displayName", displayName).param("username", playerRef.getUsername())); continue; }  objectArrayList.add(Message.raw(playerRef.getUsername())); }  context.sendMessage(MessageFormat.list(Message.raw(world.getName()), (Collection)objectArrayList)); }(Executor)world);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 75 */       futures.add(future); }
/*    */ 
/*    */ 
/*    */     
/* 79 */     return CompletableFuture.allOf((CompletableFuture<?>[])futures.toArray((Object[])new CompletableFuture[0]));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\WhoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */