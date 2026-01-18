/*    */ package org.jline.reader.impl.completer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import org.jline.reader.Candidate;
/*    */ import org.jline.reader.Completer;
/*    */ import org.jline.reader.LineReader;
/*    */ import org.jline.reader.ParsedLine;
/*    */ import org.jline.utils.AttributedString;
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
/*    */ public class StringsCompleter
/*    */   implements Completer
/*    */ {
/*    */   protected Collection<Candidate> candidates;
/*    */   protected Supplier<Collection<String>> stringsSupplier;
/*    */   
/*    */   public StringsCompleter() {
/* 34 */     this(Collections.emptyList());
/*    */   }
/*    */   
/*    */   public StringsCompleter(Supplier<Collection<String>> stringsSupplier) {
/* 38 */     assert stringsSupplier != null;
/* 39 */     this.candidates = null;
/* 40 */     this.stringsSupplier = stringsSupplier;
/*    */   }
/*    */   
/*    */   public StringsCompleter(String... strings) {
/* 44 */     this(Arrays.asList(strings));
/*    */   }
/*    */   
/*    */   public StringsCompleter(Iterable<String> strings) {
/* 48 */     assert strings != null;
/* 49 */     this.candidates = new ArrayList<>();
/* 50 */     for (String string : strings) {
/* 51 */       this.candidates.add(new Candidate(AttributedString.stripAnsi(string), string, null, null, null, null, true));
/*    */     }
/*    */   }
/*    */   
/*    */   public StringsCompleter(Candidate... candidates) {
/* 56 */     this(Arrays.asList(candidates));
/*    */   }
/*    */   
/*    */   public StringsCompleter(Collection<Candidate> candidates) {
/* 60 */     assert candidates != null;
/* 61 */     this.candidates = new ArrayList<>(candidates);
/*    */   }
/*    */ 
/*    */   
/*    */   public void complete(LineReader reader, ParsedLine commandLine, List<Candidate> candidates) {
/* 66 */     assert commandLine != null;
/* 67 */     assert candidates != null;
/* 68 */     if (this.candidates != null) {
/* 69 */       candidates.addAll(this.candidates);
/*    */     } else {
/* 71 */       for (String string : this.stringsSupplier.get()) {
/* 72 */         candidates.add(new Candidate(AttributedString.stripAnsi(string), string, null, null, null, null, true));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 79 */     String value = (this.candidates != null) ? this.candidates.toString() : ("{" + this.stringsSupplier.toString() + "}");
/* 80 */     return "StringsCompleter" + value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\completer\StringsCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */