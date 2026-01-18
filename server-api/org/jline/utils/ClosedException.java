/*    */ package org.jline.utils;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class ClosedException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 3085420657077696L;
/*    */   
/*    */   public ClosedException() {}
/*    */   
/*    */   public ClosedException(String message) {
/* 36 */     super(message);
/*    */   }
/*    */   
/*    */   public ClosedException(String message, Throwable cause) {
/* 40 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public ClosedException(Throwable cause) {
/* 44 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\ClosedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */