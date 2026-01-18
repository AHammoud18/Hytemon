/*    */ package org.jline.reader.impl.completer;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import org.jline.reader.Candidate;
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
/*    */ public class EnumCompleter
/*    */   extends StringsCompleter
/*    */ {
/*    */   public EnumCompleter(Class<? extends Enum<?>> source) {
/* 23 */     Objects.requireNonNull(source);
/* 24 */     for (Enum<?> n : (Enum[])source.getEnumConstants())
/* 25 */       this.candidates.add(new Candidate(n.name().toLowerCase())); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\completer\EnumCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */