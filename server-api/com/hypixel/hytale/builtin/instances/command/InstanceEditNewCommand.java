/*    */ package com.hypixel.hytale.builtin.instances.command;
/*    */ import com.hypixel.hytale.assetstore.AssetPack;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InstanceEditNewCommand extends AbstractAsyncCommand {
/* 20 */   private final RequiredArg<String> instanceNameArg = withRequiredArg("instanceName", "server.commands.instances.edit.arg.name", (ArgumentType)ArgTypes.STRING);
/* 21 */   private final OptionalArg<String> packName = withOptionalArg("pack", "server.commands.instances.edit.arg.packName", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   public InstanceEditNewCommand() {
/* 24 */     super("new", "server.commands.instances.edit.new.desc");
/*    */   }
/*    */   @Nonnull
/*    */   public CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*    */     AssetPack pack;
/* 29 */     if (AssetModule.get().getBaseAssetPack().isImmutable()) {
/* 30 */       context.sendMessage(Message.translation("server.commands.instances.edit.assetsImmutable"));
/* 31 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 34 */     String packId = (String)this.packName.get(context);
/*    */     
/* 36 */     if (packId != null) {
/* 37 */       pack = AssetModule.get().getAssetPack(packId);
/* 38 */       if (pack == null) throw new IllegalArgumentException("Unknown asset pack: " + packId); 
/*    */     } else {
/* 40 */       pack = AssetModule.get().getBaseAssetPack();
/*    */     } 
/*    */     
/* 43 */     String name = (String)this.instanceNameArg.get(context);
/*    */ 
/*    */     
/* 46 */     Path path = pack.getRoot().resolve("Server").resolve("Instances").resolve(name);
/* 47 */     WorldConfig defaultConfig = new WorldConfig();
/*    */     try {
/* 49 */       Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
/* 50 */     } catch (IOException e) {
/* 51 */       context.sendMessage(Message.translation("server.commands.instances.createDirectory.failed").param("errormsg", e.getMessage()));
/* 52 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 55 */     return WorldConfig.save(path.resolve("instance.bson"), defaultConfig).thenRun(() -> context.sendMessage(Message.translation("server.commands.instances.createdInstanceAssetConfig").param("name", name)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstanceEditNewCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */