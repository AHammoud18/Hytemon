/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.provider.IWorldGenProvider;
/*    */ import java.util.Objects;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldAddCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 27 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.addworld.arg.name.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final DefaultArg<String> genArg = withDefaultArg("gen", "server.commands.addworld.arg.gen.desc", (ArgumentType)ArgTypes.STRING, "default", "");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final DefaultArg<String> storageArg = withDefaultArg("storage", "server.commands.addworld.arg.gen.desc", (ArgumentType)ArgTypes.STRING, "default", "");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldAddCommand() {
/* 45 */     super("add", "server.commands.addworld.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 50 */     CommandSender sender = context.sender();
/* 51 */     String name = (String)context.get((Argument)this.nameArg);
/*    */     
/* 53 */     if (Universe.get().getWorld(name) != null) {
/* 54 */       sender.sendMessage(Message.translation("server.universe.addWorld.alreadyExists")
/* 55 */           .param("worldName", name));
/*    */       return;
/*    */     } 
/* 58 */     if (Universe.get().isWorldLoadable(name)) {
/* 59 */       sender.sendMessage(Message.translation("server.universe.addWorld.alreadyExistsDisk")
/* 60 */           .param("worldName", name));
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     String generatorType = (String)context.get((Argument)this.genArg);
/* 65 */     String chunkStorageType = (String)context.get((Argument)this.storageArg);
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
/* 76 */     if (generatorType != null && !"default".equals(generatorType)) {
/* 77 */       BuilderCodec<? extends IWorldGenProvider> providerCodec = (BuilderCodec<? extends IWorldGenProvider>)IWorldGenProvider.CODEC.getCodecFor(generatorType);
/* 78 */       if (providerCodec == null) {
/* 79 */         throw new IllegalArgumentException("Unknown generatorType '" + generatorType + "'");
/*    */       }
/*    */     } 
/*    */     
/* 83 */     CompletableFutureUtil._catch(Universe.get().addWorld(name, generatorType, chunkStorageType)
/* 84 */         .thenRun(() -> sender.sendMessage(Message.translation("server.universe.addWorld.worldCreated").param("worldName", name).param("generator", Objects.<String>requireNonNullElse(generatorType, "default")).param("storage", Objects.<String>requireNonNullElse(chunkStorageType, "default"))))
/*    */ 
/*    */ 
/*    */         
/* 88 */         .exceptionally(throwable -> {
/*    */             ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to add world '%s'", name);
/*    */             sender.sendMessage(Message.translation("server.universe.addWorld.failed").param("worldName", name).param("error", (throwable.getCause() != null) ? throwable.getCause().getMessage() : throwable.getMessage()));
/*    */             return null;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */