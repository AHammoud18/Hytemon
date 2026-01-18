/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WorldPruneCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_WORLD_PRUNE_NONE_TO_PRUNE = Message.translation("server.commands.world.prune.noneToPrune");
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_WORLD_PRUNE_PRUNE_ERROR = Message.translation("server.commands.world.prune.pruneError");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldPruneCommand() {
/* 30 */     super("prune", "server.commands.world.prune.desc", true);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 35 */     CommandSender sender = context.sender();
/* 36 */     World defaultWorld = Universe.get().getDefaultWorld();
/*    */     
/* 38 */     Map<String, World> worlds = Universe.get().getWorlds();
/* 39 */     Set<String> toRemove = new HashSet<>();
/* 40 */     worlds.forEach((worldKey, world) -> {
/*    */           if (world != defaultWorld && world.getPlayerCount() == 0) {
/*    */             toRemove.add(worldKey);
/*    */             
/*    */             world.getWorldConfig().setDeleteOnRemove(true);
/*    */           } 
/*    */         });
/* 47 */     if (toRemove.isEmpty()) {
/* 48 */       sender.sendMessage(MESSAGE_COMMANDS_WORLD_PRUNE_NONE_TO_PRUNE);
/* 49 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 52 */     return CompletableFuture.runAsync(() -> {
/*    */           toRemove.forEach(());
/*    */           sender.sendMessage(Message.translation("server.commands.world.prune.done").param("count", toRemove.size()));
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldPruneCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */