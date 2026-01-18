/*     */ package org.jline.reader;
/*     */ 
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import org.jline.reader.impl.LineReaderImpl;
/*     */ import org.jline.reader.impl.history.DefaultHistory;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.TerminalBuilder;
/*     */ import org.jline.utils.Log;
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
/*     */ public final class LineReaderBuilder
/*     */ {
/*     */   Terminal terminal;
/*     */   String appName;
/*     */   
/*     */   public static LineReaderBuilder builder() {
/*  57 */     return new LineReaderBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  62 */   Map<String, Object> variables = new HashMap<>();
/*  63 */   Map<LineReader.Option, Boolean> options = new HashMap<>();
/*     */ 
/*     */   
/*     */   History history;
/*     */ 
/*     */   
/*     */   Completer completer;
/*     */   
/*     */   History memoryHistory;
/*     */   
/*     */   Highlighter highlighter;
/*     */   
/*     */   Parser parser;
/*     */   
/*     */   Expander expander;
/*     */   
/*     */   CompletionMatcher completionMatcher;
/*     */ 
/*     */   
/*     */   public LineReaderBuilder terminal(Terminal terminal) {
/*  83 */     this.terminal = terminal;
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public LineReaderBuilder appName(String appName) {
/*  88 */     this.appName = appName;
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   public LineReaderBuilder variables(Map<String, Object> variables) {
/*  93 */     Map<String, Object> old = this.variables;
/*  94 */     this.variables = Objects.<Map<String, Object>>requireNonNull(variables);
/*  95 */     this.variables.putAll(old);
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public LineReaderBuilder variable(String name, Object value) {
/* 100 */     this.variables.put(name, value);
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public LineReaderBuilder option(LineReader.Option option, boolean value) {
/* 105 */     this.options.put(option, Boolean.valueOf(value));
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public LineReaderBuilder history(History history) {
/* 110 */     this.history = history;
/* 111 */     return this;
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
/*     */   public LineReaderBuilder completer(Completer completer) {
/* 124 */     this.completer = completer;
/* 125 */     return this;
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
/*     */   public LineReaderBuilder highlighter(Highlighter highlighter) {
/* 138 */     this.highlighter = highlighter;
/* 139 */     return this;
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
/*     */   public LineReaderBuilder parser(Parser parser) {
/* 158 */     if (parser != null) {
/*     */       try {
/* 160 */         if (!Boolean.getBoolean("org.jline.reader.support.parsedline") && 
/* 161 */           !(parser.parse("", 0) instanceof CompletingParsedLine)) {
/* 162 */           Log.warn(new Object[] { "The Parser of class " + parser.getClass().getName() + " does not support the CompletingParsedLine interface. Completion with escaped or quoted words won't work correctly." });
/*     */         
/*     */         }
/*     */       }
/* 166 */       catch (Throwable throwable) {}
/*     */     }
/*     */ 
/*     */     
/* 170 */     this.parser = parser;
/* 171 */     return this;
/*     */   }
/*     */   
/*     */   public LineReaderBuilder expander(Expander expander) {
/* 175 */     this.expander = expander;
/* 176 */     return this;
/*     */   }
/*     */   
/*     */   public LineReaderBuilder completionMatcher(CompletionMatcher completionMatcher) {
/* 180 */     this.completionMatcher = completionMatcher;
/* 181 */     return this;
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
/*     */   public LineReader build() {
/* 204 */     Terminal terminal = this.terminal;
/* 205 */     if (terminal == null) {
/*     */       try {
/* 207 */         terminal = TerminalBuilder.terminal();
/* 208 */       } catch (IOException e) {
/* 209 */         throw new IOError(e);
/*     */       } 
/*     */     }
/*     */     
/* 213 */     String appName = this.appName;
/* 214 */     if (null == appName) {
/* 215 */       appName = terminal.getName();
/*     */     }
/*     */     
/* 218 */     LineReaderImpl reader = new LineReaderImpl(terminal, appName, this.variables);
/* 219 */     if (this.history != null) {
/* 220 */       reader.setHistory(this.history);
/*     */     } else {
/* 222 */       if (this.memoryHistory == null) {
/* 223 */         this.memoryHistory = (History)new DefaultHistory();
/*     */       }
/* 225 */       reader.setHistory(this.memoryHistory);
/*     */     } 
/* 227 */     if (this.completer != null) {
/* 228 */       reader.setCompleter(this.completer);
/*     */     }
/* 230 */     if (this.highlighter != null) {
/* 231 */       reader.setHighlighter(this.highlighter);
/*     */     }
/* 233 */     if (this.parser != null) {
/* 234 */       reader.setParser(this.parser);
/*     */     }
/* 236 */     if (this.expander != null) {
/* 237 */       reader.setExpander(this.expander);
/*     */     }
/* 239 */     if (this.completionMatcher != null) {
/* 240 */       reader.setCompletionMatcher(this.completionMatcher);
/*     */     }
/* 242 */     for (Map.Entry<LineReader.Option, Boolean> e : this.options.entrySet()) {
/* 243 */       reader.option(e.getKey(), ((Boolean)e.getValue()).booleanValue());
/*     */     }
/* 245 */     return (LineReader)reader;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\LineReaderBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */