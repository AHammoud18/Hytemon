/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldLoadCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 24 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.loadworld.arg.name.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldLoadCommand() {
/* 30 */     super("load", "server.commands.loadworld.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 35 */     CommandSender sender = context.sender();
/* 36 */     String name = (String)context.get((Argument)this.nameArg);
/*    */     
/* 38 */     if (Universe.get().getWorld(name) != null) {
/* 39 */       sender.sendMessage(Message.translation("server.universe.loadWorld.alreadyExists")
/* 40 */           .param("worldName", name));
/*    */       return;
/*    */     } 
/* 43 */     if (!Universe.get().isWorldLoadable(name)) {
/* 44 */       sender.sendMessage(Message.translation("server.universe.loadWorld.notExist")
/* 45 */           .param("worldName", name));
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     CompletableFutureUtil._catch(Universe.get().loadWorld(name)
/* 50 */         .thenRun(() -> sender.sendMessage(Message.translation("server.universe.loadWorld.worldCreated").param("worldName", name)))
/*    */         
/* 52 */         .exceptionally(throwable -> {
/*    */             ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to load world '%s'", name);
/*    */             sender.sendMessage(Message.translation("server.universe.loadWorld.failed").param("worldName", name).param("error", (throwable.getCause() != null) ? throwable.getCause().getMessage() : throwable.getMessage()));
/*    */             return null;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldLoadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */