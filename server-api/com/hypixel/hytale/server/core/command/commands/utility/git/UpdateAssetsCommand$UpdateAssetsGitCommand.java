/*    */ package com.hypixel.hytale.server.core.command.commands.utility.git;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.PathUtil;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.util.AssetUtil;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
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
/*    */ abstract class UpdateAssetsGitCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   protected UpdateAssetsGitCommand(@Nonnull String name, @Nonnull String description) {
/* 42 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected abstract String[] getCommand(@Nonnull Path paramPath);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 57 */     return CompletableFuture.runAsync(() -> {
/*    */           Path assetPath = AssetUtil.getHytaleAssetsPath();
/*    */           
/*    */           Path gitPath = null;
/*    */           
/*    */           if (Files.exists(assetPath.resolve(".git"), new java.nio.file.LinkOption[0])) {
/*    */             gitPath = assetPath;
/*    */           } else {
/*    */             Path parent = PathUtil.getParent(assetPath.toAbsolutePath());
/*    */             
/*    */             if (Files.exists(parent.resolve(".git"), new java.nio.file.LinkOption[0])) {
/*    */               gitPath = parent;
/*    */             }
/*    */           } 
/*    */           
/*    */           if (gitPath == null) {
/*    */             context.sendMessage(Message.translation("server.general.pathNotGitRepo").param("path", assetPath.toString()));
/*    */             
/*    */             return;
/*    */           } 
/*    */           
/*    */           String[] processCommand = getCommand(gitPath);
/*    */           
/*    */           String commandDisplay = String.join(" ", (CharSequence[])processCommand);
/*    */           
/*    */           try {
/*    */             context.sendMessage(Message.translation("server.commands.update.running").param("cmd", commandDisplay));
/*    */             
/*    */             Process process = (new ProcessBuilder(processCommand)).directory(gitPath.toFile()).start();
/*    */             
/*    */             try {
/*    */               process.waitFor();
/*    */               
/*    */               BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
/*    */               
/*    */               String line;
/*    */               
/*    */               while ((line = reader.readLine()) != null) {
/*    */                 context.sendMessage(Message.translation("server.commands.update.runningStdOut").param("cmd", commandDisplay).param("line", line));
/*    */               }
/*    */               reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
/*    */               while ((line = reader.readLine()) != null) {
/*    */                 context.sendMessage(Message.translation("server.commands.update.runningStdErr").param("cmd", commandDisplay).param("line", line));
/*    */               }
/*    */               context.sendMessage(Message.translation("server.commands.update.done").param("cmd", commandDisplay));
/* :2 */             } catch (InterruptedException e) {
/*    */               Thread.currentThread().interrupt();
/*    */             } 
/* :5 */           } catch (IOException e) {
/*    */             context.sendMessage(Message.translation("server.commands.update.failed").param("cmd", commandDisplay).param("msg", e.getMessage()));
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\git\UpdateAssetsCommand$UpdateAssetsGitCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */