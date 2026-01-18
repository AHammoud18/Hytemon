/*    */ package org.bson;
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
/*    */ public class BSONException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -4415279469780082174L;
/* 26 */   private Integer errorCode = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BSONException(String msg) {
/* 32 */     super(msg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BSONException(int errorCode, String msg) {
/* 40 */     super(msg);
/* 41 */     this.errorCode = Integer.valueOf(errorCode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BSONException(String msg, Throwable t) {
/* 49 */     super(msg, t);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BSONException(int errorCode, String msg, Throwable t) {
/* 58 */     super(msg, t);
/* 59 */     this.errorCode = Integer.valueOf(errorCode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getErrorCode() {
/* 68 */     return this.errorCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasErrorCode() {
/* 77 */     return (this.errorCode != null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BSONException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */