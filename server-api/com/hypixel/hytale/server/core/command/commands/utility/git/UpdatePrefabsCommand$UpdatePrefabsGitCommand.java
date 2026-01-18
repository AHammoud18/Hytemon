/*    */ package com.hypixel.hytale.server.core.command.commands.utility.git;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.PathUtil;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
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
/*    */ 
/*    */ 
/*    */ abstract class UpdatePrefabsGitCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   protected UpdatePrefabsGitCommand(@Nonnull String name, @Nonnull String description) {
/* 44 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected abstract String[][] getCommands(@Nonnull String paramString);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 59 */     return CompletableFuture.runAsync(() -> {
/*    */           Path prefabsPath = PrefabStore.get().getServerPrefabsPath();
/*    */           
/*    */           Path gitPath = null;
/*    */           
/*    */           if (Files.isDirectory(prefabsPath.resolve(".git"), new java.nio.file.LinkOption[0])) {
/*    */             gitPath = prefabsPath;
/*    */           } else {
/*    */             Path parent = PathUtil.getParent(prefabsPath);
/*    */             
/*    */             if (Files.isDirectory(parent.resolve(".git"), new java.nio.file.LinkOption[0])) {
/*    */               gitPath = parent;
/*    */             }
/*    */           } 
/*    */           
/*    */           if (gitPath == null) {
/*    */             context.sendMessage(Message.translation("server.general.pathNotGitRepo").param("path", prefabsPath.toString()));
/*    */             
/*    */             return;
/*    */           } 
/*    */           
/*    */           String senderDisplayName = context.sender().getDisplayName();
/*    */           
/*    */           String[][] cmds = getCommands(senderDisplayName);
/*    */           
/*    */           for (String[] processCommand : cmds) {
/*    */             try {
/*    */               String commandDisplay = String.join(" ", (CharSequence[])processCommand);
/*    */               
/*    */               context.sendMessage(Message.translation("server.commands.update.runningCmd").param("cmd", commandDisplay));
/*    */               
/*    */               Process process = (new ProcessBuilder(processCommand)).directory(gitPath.toFile()).start();
/*    */               
/*    */               try {
/*    */                 process.waitFor();
/*    */                 
/*    */                 BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
/*    */                 
/*    */                 String line;
/*    */                 
/*    */                 while ((line = reader.readLine()) != null) {
/*    */                   context.sendMessage(Message.translation("server.commands.update.runningStdOut").param("cmd", commandDisplay).param("line", line));
/*    */                 }
/*    */                 reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
/*    */                 while ((line = reader.readLine()) != null) {
/*    */                   context.sendMessage(Message.translation("server.commands.update.runningStdErr").param("cmd", commandDisplay).param("line", line));
/*    */                 }
/*    */                 context.sendMessage(Message.translation("server.commands.update.done").param("cmd", commandDisplay));
/* :7 */               } catch (InterruptedException e) {
/*    */                 Thread.currentThread().interrupt();
/*    */                 break;
/*    */               } 
/* ;1 */             } catch (IOException e) {
/*    */               context.sendMessage(Message.translation("server.commands.update.failed").param("cmd", String.join(" ", (CharSequence[])processCommand)).param("msg", e.getMessage()));
/*    */               break;
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\git\UpdatePrefabsCommand$UpdatePrefabsGitCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */