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
/*    */ public class Reference
/*    */   implements Binding
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   public Reference(String name) {
/* 36 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String name() {
/* 45 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 50 */     if (this == o) return true; 
/* 51 */     if (o == null || getClass() != o.getClass()) return false; 
/* 52 */     Reference func = (Reference)o;
/* 53 */     return this.name.equals(func.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     return this.name.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "Reference[" + this.name + ']';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\Reference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */