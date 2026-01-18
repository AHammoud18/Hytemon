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
/*    */ public class UserInterruptException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 6172232572140736750L;
/*    */   private final String partialLine;
/*    */   
/*    */   public UserInterruptException(Throwable cause) {
/* 23 */     super(cause);
/* 24 */     this.partialLine = null;
/*    */   }
/*    */   
/*    */   public UserInterruptException(String partialLine) {
/* 28 */     this.partialLine = partialLine;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPartialLine() {
/* 35 */     return this.partialLine;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\UserInterruptException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */