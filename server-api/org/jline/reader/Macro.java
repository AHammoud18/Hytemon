/*    */ package org.jline.reader;
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
/*    */ public class Macro
/*    */   implements Binding
/*    */ {
/*    */   private final String sequence;
/*    */   
/*    */   public Macro(String sequence) {
/* 38 */     this.sequence = sequence;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSequence() {
/* 47 */     return this.sequence;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 52 */     if (this == o) return true; 
/* 53 */     if (o == null || getClass() != o.getClass()) return false; 
/* 54 */     Macro macro = (Macro)o;
/* 55 */     return this.sequence.equals(macro.sequence);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 60 */     return this.sequence.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "Macro[" + this.sequence + ']';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\Macro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */