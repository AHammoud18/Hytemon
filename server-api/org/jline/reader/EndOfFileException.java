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
/*    */ public class EndOfFileException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 528485360925144689L;
/*    */   private String partialLine;
/*    */   
/*    */   public EndOfFileException() {}
/*    */   
/*    */   public EndOfFileException(String message) {
/* 23 */     super(message);
/*    */   }
/*    */   
/*    */   public EndOfFileException(String message, Throwable cause) {
/* 27 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public EndOfFileException(Throwable cause) {
/* 31 */     super(cause);
/*    */   }
/*    */   
/*    */   public EndOfFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
/* 35 */     super(message, cause, enableSuppression, writableStackTrace);
/*    */   }
/*    */   
/*    */   public EndOfFileException partialLine(String partialLine) {
/* 39 */     this.partialLine = partialLine;
/* 40 */     return this;
/*    */   }
/*    */   
/*    */   public String getPartialLine() {
/* 44 */     return this.partialLine;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\EndOfFileException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */