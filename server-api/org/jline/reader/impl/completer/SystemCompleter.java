/*     */ package org.jline.reader.impl.completer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import org.jline.reader.Candidate;
/*     */ import org.jline.reader.Completer;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.ParsedLine;
/*     */ import org.jline.utils.AttributedString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SystemCompleter
/*     */   implements Completer
/*     */ {
/*  25 */   private Map<String, List<Completer>> completers = new HashMap<>();
/*  26 */   private Map<String, String> aliasCommand = new HashMap<>();
/*  27 */   private Map<String, String> descriptions = new HashMap<>();
/*     */   
/*     */   private StringsCompleter commands;
/*     */   
/*     */   private boolean compiled = false;
/*     */ 
/*     */   
/*     */   public void complete(LineReader reader, ParsedLine commandLine, List<Candidate> candidates) {
/*  35 */     if (!this.compiled) {
/*  36 */       throw new IllegalStateException();
/*     */     }
/*  38 */     assert commandLine != null;
/*  39 */     assert candidates != null;
/*  40 */     if (commandLine.words().size() > 0) {
/*  41 */       if (commandLine.words().size() == 1) {
/*  42 */         String buffer = commandLine.words().get(0);
/*  43 */         int eq = buffer.indexOf('=');
/*  44 */         if (eq < 0) {
/*  45 */           this.commands.complete(reader, commandLine, candidates);
/*  46 */         } else if (reader.getParser().validVariableName(buffer.substring(0, eq))) {
/*  47 */           String curBuf = buffer.substring(0, eq + 1);
/*  48 */           for (String c : this.completers.keySet()) {
/*  49 */             candidates.add(new Candidate(
/*  50 */                   AttributedString.stripAnsi(curBuf + c), c, null, null, null, null, true));
/*     */           }
/*     */         } 
/*     */       } else {
/*  54 */         String cmd = reader.getParser().getCommand(commandLine.words().get(0));
/*  55 */         if (command(cmd) != null) {
/*  56 */           ((Completer)((List<Completer>)this.completers.get(command(cmd))).get(0)).complete(reader, commandLine, candidates);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCompiled() {
/*  63 */     return this.compiled;
/*     */   }
/*     */   
/*     */   private String command(String cmd) {
/*  67 */     String out = null;
/*  68 */     if (cmd != null) {
/*  69 */       if (this.completers.containsKey(cmd)) {
/*  70 */         out = cmd;
/*     */       } else {
/*  72 */         out = this.aliasCommand.get(cmd);
/*     */       } 
/*     */     }
/*  75 */     return out;
/*     */   }
/*     */   
/*     */   public void add(String command, List<Completer> completers) {
/*  79 */     for (Completer c : completers) {
/*  80 */       add(command, c);
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(List<String> commands, Completer completer) {
/*  85 */     for (String c : commands) {
/*  86 */       add(c, completer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(String command, Completer completer) {
/*  91 */     Objects.requireNonNull(command);
/*  92 */     if (this.compiled) {
/*  93 */       throw new IllegalStateException();
/*     */     }
/*  95 */     if (!this.completers.containsKey(command)) {
/*  96 */       this.completers.put(command, new ArrayList<>());
/*     */     }
/*  98 */     if (completer instanceof ArgumentCompleter) {
/*  99 */       ((ArgumentCompleter)completer).setStrictCommand(false);
/*     */     }
/* 101 */     ((List<Completer>)this.completers.get(command)).add(completer);
/*     */   }
/*     */   
/*     */   public void add(SystemCompleter other) {
/* 105 */     if (other.isCompiled()) {
/* 106 */       throw new IllegalStateException();
/*     */     }
/* 108 */     for (Map.Entry<String, List<Completer>> entry : other.getCompleters().entrySet()) {
/* 109 */       for (Completer c : entry.getValue()) {
/* 110 */         add(entry.getKey(), c);
/*     */       }
/*     */     } 
/* 113 */     addAliases(other.getAliases());
/*     */   }
/*     */   
/*     */   public void addAliases(Map<String, String> aliasCommand) {
/* 117 */     if (this.compiled) {
/* 118 */       throw new IllegalStateException();
/*     */     }
/* 120 */     this.aliasCommand.putAll(aliasCommand);
/*     */   }
/*     */   
/*     */   private Map<String, String> getAliases() {
/* 124 */     return this.aliasCommand;
/*     */   }
/*     */   
/*     */   public void compile() {
/* 128 */     compile(s -> new Candidate(AttributedString.stripAnsi(s), s, null, null, null, null, true));
/*     */   }
/*     */   
/*     */   public void compile(Function<String, Candidate> candidateBuilder) {
/* 132 */     if (this.compiled) {
/*     */       return;
/*     */     }
/* 135 */     Map<String, List<Completer>> compiledCompleters = new HashMap<>();
/* 136 */     for (Map.Entry<String, List<Completer>> entry : this.completers.entrySet()) {
/* 137 */       if (((List)entry.getValue()).size() == 1) {
/* 138 */         compiledCompleters.put(entry.getKey(), entry.getValue()); continue;
/*     */       } 
/* 140 */       compiledCompleters.put(entry.getKey(), new ArrayList<>());
/* 141 */       ((List<AggregateCompleter>)compiledCompleters.get(entry.getKey())).add(new AggregateCompleter(entry.getValue()));
/*     */     } 
/*     */     
/* 144 */     this.completers = compiledCompleters;
/* 145 */     Set<String> cmds = new HashSet<>(this.completers.keySet());
/* 146 */     cmds.addAll(this.aliasCommand.keySet());
/* 147 */     this.commands = new StringsCompleter((Collection<Candidate>)cmds.stream().<Candidate>map(candidateBuilder).collect(Collectors.toList()));
/* 148 */     this.compiled = true;
/*     */   }
/*     */   
/*     */   public Map<String, List<Completer>> getCompleters() {
/* 152 */     return this.completers;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\completer\SystemCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */