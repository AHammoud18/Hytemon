/*    */ package com.hypixel.hytale.server.core.command.commands.utility.metacommands;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandManager;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandOwner;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Function;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DumpCommandsCommand extends CommandBase {
/*    */   public DumpCommandsCommand() {
/* 28 */     super("dump", "server.commands.meta.dump.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     JsonObject outputJson = new JsonObject();
/*    */     
/* 35 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*    */     
/* 37 */     List<CommandDef> modernDefs = gatherCommandDefs();
/* 38 */     outputJson.add("modern", gson.toJsonTree(modernDefs));
/*    */     
/* 40 */     CompletableFuture.runAsync(() -> {
/*    */           try {
/*    */             String outputStr = gson.toJson((JsonElement)outputJson);
/*    */             
/*    */             Path path = Paths.get("dumps/commands.dump.json", new String[0]);
/*    */             Files.createDirectories(path.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/*    */             Files.writeString(path, outputStr, new java.nio.file.OpenOption[0]);
/*    */             context.sendMessage(Message.translation("server.commands.meta.dump.success").param("file", path.toAbsolutePath().toString()));
/* 48 */           } catch (Throwable t) {
/*    */             throw new RuntimeException(t);
/*    */           } 
/* 51 */         }).exceptionally(t -> {
/*    */           context.sendMessage(Message.translation("server.commands.meta.dump.error"));
/*    */           ((HytaleLogger.Api)HytaleLogger.getLogger().at(Level.SEVERE).withCause(t)).log("Couldn't write command dump");
/*    */           return null;
/*    */         });
/*    */   }
/*    */   
/*    */   private List<CommandDef> gatherCommandDefs() {
/* 59 */     Map<String, AbstractCommand> registrations = CommandManager.get().getCommandRegistration();
/* 60 */     ObjectArrayList objectArrayList = new ObjectArrayList(registrations.size() * 2);
/* 61 */     registrations.forEach((name, command) -> extractCommand(command, defs));
/*    */ 
/*    */     
/* 64 */     return (List<CommandDef>)objectArrayList;
/*    */   }
/*    */   
/*    */   private void extractCommand(@Nonnull AbstractCommand command, @Nonnull List<CommandDef> defs) {
/* 68 */     String outputName = "/" + command.getFullyQualifiedName();
/* 69 */     String className = command.getClass().getName();
/* 70 */     String owner = formatNullable(command.getOwner(), CommandOwner::getName);
/* 71 */     String ownerClass = formatNullable(command.getOwner(), o -> o.getClass().getName());
/* 72 */     String permission = formatPermission(command.getPermission());
/* 73 */     List<String> permissionGroups = command.getPermissionGroups();
/* 74 */     defs.add(new CommandDef(outputName, className, owner, ownerClass, permission, permissionGroups));
/*    */     
/* 76 */     for (AbstractCommand subCommand : command.getSubCommands().values()) {
/* 77 */       extractCommand(subCommand, defs);
/*    */     }
/*    */   }
/*    */   
/*    */   private <T> String formatNullable(@Nullable T something, Function<T, String> func) {
/*    */     try {
/* 83 */       if (something == null) return "NULL"; 
/* 84 */       return func.apply(something);
/* 85 */     } catch (Throwable t) {
/* 86 */       return "ERROR";
/*    */     } 
/*    */   }
/*    */   
/*    */   private String formatPermission(@Nullable String permission) {
/* 91 */     return (permission == null) ? "NULL" : permission;
/*    */   }
/*    */   private static final class CommandDef extends Record { private final String name; private final String className; private final String owner; private final String ownerClass; private final String permission; private final List<String> permissionGroups;
/* 94 */     private CommandDef(String name, String className, String owner, String ownerClass, String permission, List<String> permissionGroups) { this.name = name; this.className = className; this.owner = owner; this.ownerClass = ownerClass; this.permission = permission; this.permissionGroups = permissionGroups; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/command/commands/utility/metacommands/DumpCommandsCommand$CommandDef;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #94	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 94 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/command/commands/utility/metacommands/DumpCommandsCommand$CommandDef; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/command/commands/utility/metacommands/DumpCommandsCommand$CommandDef;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #94	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/command/commands/utility/metacommands/DumpCommandsCommand$CommandDef; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/command/commands/utility/metacommands/DumpCommandsCommand$CommandDef;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #94	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/command/commands/utility/metacommands/DumpCommandsCommand$CommandDef;
/* 94 */       //   0	8	1	o	Ljava/lang/Object; } public String className() { return this.className; } public String owner() { return this.owner; } public String ownerClass() { return this.ownerClass; } public String permission() { return this.permission; } public List<String> permissionGroups() { return this.permissionGroups; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\metacommands\DumpCommandsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */