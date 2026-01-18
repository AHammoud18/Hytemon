/*    */ package org.jline.reader.impl.completer;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import org.jline.reader.Candidate;
/*    */ import org.jline.reader.Completer;
/*    */ import org.jline.reader.LineReader;
/*    */ import org.jline.reader.ParsedLine;
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
/*    */ public class AggregateCompleter
/*    */   implements Completer
/*    */ {
/*    */   private final Collection<Completer> completers;
/*    */   
/*    */   public AggregateCompleter(Completer... completers) {
/* 36 */     this(Arrays.asList(completers));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AggregateCompleter(Collection<Completer> completers) {
/* 46 */     assert completers != null;
/* 47 */     this.completers = completers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<Completer> getCompleters() {
/* 56 */     return this.completers;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
/* 68 */     Objects.requireNonNull(line);
/* 69 */     Objects.requireNonNull(candidates);
/* 70 */     this.completers.forEach(c -> c.complete(reader, line, candidates));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return getClass().getSimpleName() + "{completers=" + this.completers + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\completer\AggregateCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */