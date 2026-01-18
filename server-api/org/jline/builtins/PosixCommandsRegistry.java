/*     */ package org.jline.builtins;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import org.jline.terminal.Terminal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PosixCommandsRegistry
/*     */ {
/*     */   private final PosixCommands.Context context;
/*     */   private final Map<String, CommandFunction> commands;
/*     */   
/*     */   public PosixCommandsRegistry(InputStream in, PrintStream out, PrintStream err, Path currentDir, Terminal terminal, Function<String, Object> variables) {
/*  74 */     this.context = new PosixCommands.Context(in, out, err, currentDir, terminal, variables);
/*  75 */     this.commands = new HashMap<>();
/*  76 */     populateDefaultCommands(this.commands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PosixCommandsRegistry(PosixCommands.Context context) {
/*  85 */     this.context = context;
/*  86 */     this.commands = new HashMap<>();
/*  87 */     populateDefaultCommands(this.commands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void populateDefaultCommands(Map<String, CommandFunction> commands) {
/*  94 */     commands.put("cat", PosixCommands::cat);
/*  95 */     commands.put("echo", PosixCommands::echo);
/*  96 */     commands.put("grep", PosixCommands::grep);
/*  97 */     commands.put("ls", PosixCommands::ls);
/*  98 */     commands.put("pwd", PosixCommands::pwd);
/*  99 */     commands.put("head", PosixCommands::head);
/* 100 */     commands.put("tail", PosixCommands::tail);
/* 101 */     commands.put("wc", PosixCommands::wc);
/* 102 */     commands.put("date", PosixCommands::date);
/* 103 */     commands.put("sleep", PosixCommands::sleep);
/* 104 */     commands.put("sort", PosixCommands::sort);
/* 105 */     commands.put("clear", PosixCommands::clear);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDefaultCommands() {
/* 112 */     populateDefaultCommands(this.commands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(String name, CommandFunction command) {
/* 122 */     this.commands.put(name, command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(String name) {
/* 131 */     this.commands.remove(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCommand(String name) {
/* 141 */     return this.commands.containsKey(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getCommandNames() {
/* 150 */     return (String[])this.commands.keySet().toArray((Object[])new String[0]);
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
/*     */   public void execute(String name, String[] argv) throws Exception {
/* 162 */     CommandFunction command = this.commands.get(name);
/* 163 */     if (command == null) {
/* 164 */       throw new IllegalArgumentException("Unknown command: " + name);
/*     */     }
/* 166 */     command.execute(this.context, argv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(String commandLine) throws Exception {
/* 177 */     if (commandLine == null || commandLine.trim().isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     String[] parts = commandLine.trim().split("\\s+");
/* 182 */     execute(parts[0], parts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PosixCommands.Context getContext() {
/* 191 */     return this.context;
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
/*     */   public PosixCommandsRegistry withCurrentDirectory(Path newCurrentDir) {
/* 203 */     Objects.requireNonNull(this.context); PosixCommands.Context newContext = new PosixCommands.Context(this.context.in(), this.context.out(), this.context.err(), newCurrentDir, this.context.terminal(), this.context::get);
/* 204 */     return new PosixCommandsRegistry(newContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printHelp() {
/* 211 */     this.context.out().println("Available POSIX commands:");
/* 212 */     String[] names = getCommandNames();
/* 213 */     Arrays.sort((Object[])names);
/* 214 */     for (String name : names) {
/* 215 */       this.context.out().println("  " + name);
/*     */     }
/* 217 */     this.context.out().println();
/* 218 */     this.context.out().println("Use '<command> --help' for detailed help on each command.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printHelp(String commandName) throws Exception {
/* 228 */     if (!hasCommand(commandName)) {
/* 229 */       this.context.err().println("Unknown command: " + commandName);
/*     */       return;
/*     */     } 
/* 232 */     execute(commandName, new String[] { commandName, "--help" });
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface CommandFunction {
/*     */     void execute(PosixCommands.Context param1Context, String[] param1ArrayOfString) throws Exception;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\PosixCommandsRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */