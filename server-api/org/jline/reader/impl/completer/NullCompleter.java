/*    */ package org.jline.reader.impl.completer;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public final class NullCompleter
/*    */   implements Completer
/*    */ {
/* 24 */   public static final NullCompleter INSTANCE = new NullCompleter();
/*    */   
/*    */   public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\completer\NullCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */