/*     */ package org.jline.reader.impl.completer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.jline.reader.Candidate;
/*     */ import org.jline.reader.Completer;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.ParsedLine;
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
/*     */ public class ArgumentCompleter
/*     */   implements Completer
/*     */ {
/*  31 */   private final List<Completer> completers = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private boolean strict = true;
/*     */ 
/*     */   
/*     */   private boolean strictCommand = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentCompleter(Collection<Completer> completers) {
/*  42 */     Objects.requireNonNull(completers);
/*  43 */     this.completers.addAll(completers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentCompleter(Completer... completers) {
/*  52 */     this(Arrays.asList(completers));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStrict(boolean strict) {
/*  62 */     this.strict = strict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStrictCommand(boolean strictCommand) {
/*  72 */     this.strictCommand = strictCommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStrict() {
/*  82 */     return this.strict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Completer> getCompleters() {
/*  91 */     return this.completers;
/*     */   }
/*     */   public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
/*     */     Completer completer;
/*  95 */     Objects.requireNonNull(line);
/*  96 */     Objects.requireNonNull(candidates);
/*     */     
/*  98 */     if (line.wordIndex() < 0) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     List<Completer> completers = getCompleters();
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (line.wordIndex() >= completers.size()) {
/* 107 */       completer = completers.get(completers.size() - 1);
/*     */     } else {
/* 109 */       completer = completers.get(line.wordIndex());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 114 */     for (int i = this.strictCommand ? 0 : 1; isStrict() && i < line.wordIndex(); i++) {
/* 115 */       int idx = (i >= completers.size()) ? (completers.size() - 1) : i;
/* 116 */       if (idx != 0 || this.strictCommand) {
/*     */ 
/*     */         
/* 119 */         Completer sub = completers.get(idx);
/* 120 */         List<? extends CharSequence> args = line.words();
/* 121 */         String arg = (args == null || i >= args.size()) ? "" : ((CharSequence)args.get(i)).toString();
/*     */         
/* 123 */         List<Candidate> subCandidates = new LinkedList<>();
/* 124 */         sub.complete(reader, new ArgumentLine(arg, arg.length()), subCandidates);
/*     */         
/* 126 */         boolean found = false;
/* 127 */         for (Candidate cand : subCandidates) {
/* 128 */           if (cand.value().equals(arg)) {
/* 129 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 133 */         if (!found) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 138 */     completer.complete(reader, line, candidates);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ArgumentLine
/*     */     implements ParsedLine
/*     */   {
/*     */     private final String word;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int cursor;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArgumentLine(String word, int cursor) {
/* 159 */       this.word = word;
/* 160 */       this.cursor = cursor;
/*     */     }
/*     */ 
/*     */     
/*     */     public String word() {
/* 165 */       return this.word;
/*     */     }
/*     */ 
/*     */     
/*     */     public int wordCursor() {
/* 170 */       return this.cursor;
/*     */     }
/*     */ 
/*     */     
/*     */     public int wordIndex() {
/* 175 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> words() {
/* 180 */       return Collections.singletonList(this.word);
/*     */     }
/*     */ 
/*     */     
/*     */     public String line() {
/* 185 */       return this.word;
/*     */     }
/*     */ 
/*     */     
/*     */     public int cursor() {
/* 190 */       return this.cursor;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\completer\ArgumentCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */