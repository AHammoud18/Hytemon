/*     */ package org.jline.console;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jline.reader.Candidate;
/*     */ import org.jline.reader.impl.completer.SystemCompleter;
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
/*     */ public interface CommandRegistry
/*     */ {
/*     */   static SystemCompleter aggregateCompleters(CommandRegistry... commandRegistries) {
/*  46 */     SystemCompleter out = new SystemCompleter();
/*  47 */     for (CommandRegistry r : commandRegistries) {
/*  48 */       out.add(r.compileCompleters());
/*     */     }
/*  50 */     return out;
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
/*     */   
/*     */   static SystemCompleter compileCompleters(CommandRegistry... commandRegistries) {
/*  64 */     SystemCompleter out = aggregateCompleters(commandRegistries);
/*  65 */     out.compile(s -> createCandidate(commandRegistries, s));
/*  66 */     return out;
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
/*     */   
/*     */   static Candidate createCandidate(CommandRegistry[] commandRegistries, String command) {
/*  80 */     String group = null, desc = null;
/*  81 */     for (CommandRegistry registry : commandRegistries) {
/*  82 */       if (registry.hasCommand(command)) {
/*  83 */         group = registry.name();
/*  84 */         desc = registry.commandInfo(command).stream().findFirst().orElse(null);
/*     */         break;
/*     */       } 
/*     */     } 
/*  88 */     return new Candidate(command, command, group, desc, null, null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default String name() {
/*  96 */     return getClass().getSimpleName();
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
/*     */   Object invoke(CommandSession session, String command, Object... args) throws Exception {
/* 149 */     throw new IllegalStateException("CommandRegistry method invoke(session, command, ... args) is not implemented!");
/*     */   }
/*     */ 
/*     */   
/*     */   Set<String> commandNames();
/*     */ 
/*     */   
/*     */   Map<String, String> commandAliases();
/*     */ 
/*     */   
/*     */   List<String> commandInfo(String paramString);
/*     */   
/*     */   boolean hasCommand(String paramString);
/*     */   
/*     */   SystemCompleter compileCompleters();
/*     */   
/*     */   CmdDesc commandDescription(List<String> paramList);
/*     */   
/*     */   public static class CommandSession
/*     */   {
/*     */     private final Terminal terminal;
/*     */     private final InputStream in;
/*     */     private final PrintStream out;
/*     */     private final PrintStream err;
/*     */     
/*     */     public CommandSession() {
/* 175 */       this.in = System.in;
/* 176 */       this.out = System.out;
/* 177 */       this.err = System.err;
/* 178 */       this.terminal = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CommandSession(Terminal terminal) {
/* 188 */       this(terminal, terminal.input(), new PrintStream(terminal.output()), new PrintStream(terminal.output()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CommandSession(Terminal terminal, InputStream in, PrintStream out, PrintStream err) {
/* 200 */       this.terminal = terminal;
/* 201 */       this.in = in;
/* 202 */       this.out = out;
/* 203 */       this.err = err;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Terminal terminal() {
/* 212 */       return this.terminal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream in() {
/* 221 */       return this.in;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PrintStream out() {
/* 230 */       return this.out;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PrintStream err() {
/* 239 */       return this.err;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\CommandRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */