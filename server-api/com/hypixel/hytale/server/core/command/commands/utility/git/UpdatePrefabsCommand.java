/*     */ package com.hypixel.hytale.server.core.command.commands.utility.git;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdatePrefabsCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public UpdatePrefabsCommand() {
/*  28 */     super("prefabs", "server.commands.update.prefabs.desc");
/*  29 */     addSubCommand((AbstractCommand)new UpdatePrefabsStatusCommand());
/*  30 */     addSubCommand((AbstractCommand)new UpdatePrefabsCommitCommand());
/*  31 */     addSubCommand((AbstractCommand)new UpdatePrefabsPullCommand());
/*  32 */     addSubCommand((AbstractCommand)new UpdatePrefabsPushCommand());
/*  33 */     addSubCommand((AbstractCommand)new UpdatePrefabsAllCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class UpdatePrefabsGitCommand
/*     */     extends AbstractAsyncCommand
/*     */   {
/*     */     protected UpdatePrefabsGitCommand(@Nonnull String name, @Nonnull String description) {
/*  44 */       super(name, description);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected abstract String[][] getCommands(@Nonnull String param1String);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  59 */       return CompletableFuture.runAsync(() -> {
/*     */             Path prefabsPath = PrefabStore.get().getServerPrefabsPath();
/*     */             
/*     */             Path gitPath = null;
/*     */             
/*     */             if (Files.isDirectory(prefabsPath.resolve(".git"), new java.nio.file.LinkOption[0])) {
/*     */               gitPath = prefabsPath;
/*     */             } else {
/*     */               Path parent = PathUtil.getParent(prefabsPath);
/*     */               
/*     */               if (Files.isDirectory(parent.resolve(".git"), new java.nio.file.LinkOption[0])) {
/*     */                 gitPath = parent;
/*     */               }
/*     */             } 
/*     */             
/*     */             if (gitPath == null) {
/*     */               context.sendMessage(Message.translation("server.general.pathNotGitRepo").param("path", prefabsPath.toString()));
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             String senderDisplayName = context.sender().getDisplayName();
/*     */             
/*     */             String[][] cmds = getCommands(senderDisplayName);
/*     */             
/*     */             for (String[] processCommand : cmds) {
/*     */               try {
/*     */                 String commandDisplay = String.join(" ", (CharSequence[])processCommand);
/*     */                 
/*     */                 context.sendMessage(Message.translation("server.commands.update.runningCmd").param("cmd", commandDisplay));
/*     */                 
/*     */                 Process process = (new ProcessBuilder(processCommand)).directory(gitPath.toFile()).start();
/*     */                 
/*     */                 try {
/*     */                   process.waitFor();
/*     */                   
/*     */                   BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
/*     */                   
/*     */                   String line;
/*     */                   
/*     */                   while ((line = reader.readLine()) != null) {
/*     */                     context.sendMessage(Message.translation("server.commands.update.runningStdOut").param("cmd", commandDisplay).param("line", line));
/*     */                   }
/*     */                   reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
/*     */                   while ((line = reader.readLine()) != null) {
/*     */                     context.sendMessage(Message.translation("server.commands.update.runningStdErr").param("cmd", commandDisplay).param("line", line));
/*     */                   }
/*     */                   context.sendMessage(Message.translation("server.commands.update.done").param("cmd", commandDisplay));
/* 107 */                 } catch (InterruptedException e) {
/*     */                   Thread.currentThread().interrupt();
/*     */                   break;
/*     */                 } 
/* 111 */               } catch (IOException e) {
/*     */                 context.sendMessage(Message.translation("server.commands.update.failed").param("cmd", String.join(" ", (CharSequence[])processCommand)).param("msg", e.getMessage()));
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class UpdatePrefabsStatusCommand
/*     */     extends UpdatePrefabsGitCommand
/*     */   {
/*     */     public UpdatePrefabsStatusCommand() {
/* 130 */       super("status", "server.commands.update.prefabs.status.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected String[][] getCommands(@Nonnull String senderDisplayName) {
/* 136 */       return new String[][] { { "git", "status" }, { "git", "submodule", "foreach", "git", "status" } };
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
/*     */   private static class UpdatePrefabsCommitCommand
/*     */     extends UpdatePrefabsGitCommand
/*     */   {
/*     */     public UpdatePrefabsCommitCommand() {
/* 151 */       super("commit", "server.commands.update.prefabs.commit.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected String[][] getCommands(@Nonnull String senderDisplayName) {
/* 157 */       return new String[][] { { "git", "add", "--all", "." }, { "git", "commit", "-am", "\"Update prefabs by " + senderDisplayName + "\"" }, { "git", "submodule", "foreach", "git", "add", "--all", "." }, { "git", "submodule", "foreach", "git", "commit", "-am", "\"Update prefabs by " + senderDisplayName + "\"" } };
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
/*     */   private static class UpdatePrefabsPullCommand
/*     */     extends UpdatePrefabsGitCommand
/*     */   {
/*     */     public UpdatePrefabsPullCommand() {
/* 174 */       super("pull", "server.commands.update.prefabs.pull.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected String[][] getCommands(@Nonnull String senderDisplayName) {
/* 180 */       return new String[][] { { "git", "pull" }, { "git", "submodule", "foreach", "git", "pull" } };
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
/*     */   private static class UpdatePrefabsPushCommand
/*     */     extends UpdatePrefabsGitCommand
/*     */   {
/*     */     public UpdatePrefabsPushCommand() {
/* 195 */       super("push", "server.commands.update.prefabs.push.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected String[][] getCommands(@Nonnull String senderDisplayName) {
/* 201 */       return new String[][] { { "git", "push", "origin", "master" }, { "git", "submodule", "foreach", "git", "push" } };
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
/*     */   private static class UpdatePrefabsAllCommand
/*     */     extends UpdatePrefabsGitCommand
/*     */   {
/*     */     public UpdatePrefabsAllCommand() {
/* 216 */       super("all", "server.commands.update.prefabs.all.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     protected String[][] getCommands(@Nonnull String senderDisplayName) {
/* 222 */       return new String[][] { { "git", "submodule", "foreach", "git", "add", "--all", "." }, { "git", "submodule", "foreach", "git", "commit", "-am", "\"Update prefabs by " + senderDisplayName + "\"" }, { "git", "submodule", "foreach", "git", "pull" }, { "git", "submodule", "foreach", "git", "push" }, { "git", "add", "--all", "." }, { "git", "commit", "-am", "\"Update prefabs by " + senderDisplayName + "\"" }, { "git", "pull" }, { "git", "push" } };
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\git\UpdatePrefabsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */