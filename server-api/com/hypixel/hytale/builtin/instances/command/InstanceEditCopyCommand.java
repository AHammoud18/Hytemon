/*    */ package com.hypixel.hytale.builtin.instances.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import com.hypixel.hytale.server.core.util.io.FileUtil;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InstanceEditCopyCommand extends AbstractAsyncCommand {
/* 21 */   private final RequiredArg<String> originNameArg = withRequiredArg("instanceToCopy", "server.commands.instances.editcopy.origin.name", (ArgumentType)ArgTypes.STRING);
/* 22 */   private final RequiredArg<String> destinationNameArg = withRequiredArg("newInstanceName", "server.commands.instances.editcopy.destination.name", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   public InstanceEditCopyCommand() {
/* 25 */     super("copy", "server.commands.instances.edit.copy.desc");
/*    */   }
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*    */     WorldConfig worldConfig;
/* 30 */     if (AssetModule.get().getBaseAssetPack().isImmutable()) {
/* 31 */       context.sendMessage(Message.translation("server.commands.instances.edit.assetsImmutable"));
/* 32 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 35 */     String instanceToCopy = (String)this.originNameArg.get(context);
/*    */     
/* 37 */     Path originPath = InstancesPlugin.getInstanceAssetPath(instanceToCopy);
/* 38 */     if (!Files.exists(originPath, new java.nio.file.LinkOption[0])) {
/* 39 */       context.sendMessage(Message.translation("server.commands.instances.edit.copy.originNotFound").param("path", originPath.toAbsolutePath().toString()));
/* 40 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 43 */     String destinationName = (String)this.destinationNameArg.get(context);
/* 44 */     Path destinationPath = originPath.getParent().resolve(destinationName);
/*    */     
/* 46 */     if (Files.exists(destinationPath, new java.nio.file.LinkOption[0])) {
/* 47 */       context.sendMessage(Message.translation("server.commands.instances.edit.copy.destinationExists").param("path", destinationPath.toAbsolutePath().toString()));
/* 48 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */ 
/*    */     
/*    */     try {
/* 53 */       worldConfig = WorldConfig.load(originPath.resolve("instance.bson")).join();
/* 54 */     } catch (Throwable t) {
/* 55 */       context.sendMessage(Message.translation("server.commands.instances.edit.copy.errorLoading"));
/* 56 */       InstancesPlugin.get().getLogger().at(Level.SEVERE).log("Error loading origin instance config for copy", t);
/* 57 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 60 */     worldConfig.setUuid(UUID.randomUUID());
/*    */     
/* 62 */     Path destinationConfigFile = destinationPath.resolve("instance.bson");
/*    */     
/*    */     try {
/* 65 */       FileUtil.copyDirectory(originPath, destinationPath);
/* 66 */       Files.deleteIfExists(destinationConfigFile);
/* 67 */     } catch (Throwable t) {
/* 68 */       context.sendMessage(Message.translation("server.commands.instances.edit.copy.errorCopying"));
/* 69 */       InstancesPlugin.get().getLogger().at(Level.SEVERE).log("Error copying instance folder for copy", t);
/* 70 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 73 */     return WorldConfig.save(destinationConfigFile, worldConfig).thenRun(() -> context.sendMessage(Message.translation("server.commands.instances.copiedInstanceAssetConfig").param("origin", instanceToCopy).param("destination", destinationName)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstanceEditCopyCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */