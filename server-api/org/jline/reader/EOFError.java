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
/*    */ public class EOFError
/*    */   extends SyntaxError
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final String missing;
/*    */   private final int openBrackets;
/*    */   private final String nextClosingBracket;
/*    */   
/*    */   public EOFError(int line, int column, String message) {
/* 38 */     this(line, column, message, null);
/*    */   }
/*    */   
/*    */   public EOFError(int line, int column, String message, String missing) {
/* 42 */     this(line, column, message, missing, 0, null);
/*    */   }
/*    */   
/*    */   public EOFError(int line, int column, String message, String missing, int openBrackets, String nextClosingBracket) {
/* 46 */     super(line, column, message);
/* 47 */     this.missing = missing;
/* 48 */     this.openBrackets = openBrackets;
/* 49 */     this.nextClosingBracket = nextClosingBracket;
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
/*    */   public String getMissing() {
/* 61 */     return this.missing;
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
/*    */   public int getOpenBrackets() {
/* 73 */     return this.openBrackets;
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
/*    */   public String getNextClosingBracket() {
/* 85 */     return this.nextClosingBracket;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\EOFError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */