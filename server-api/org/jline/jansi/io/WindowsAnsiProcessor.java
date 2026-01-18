/*    */ package org.jline.jansi.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public final class WindowsAnsiProcessor
/*    */   extends AnsiProcessor
/*    */ {
/*    */   public WindowsAnsiProcessor(OutputStream ps, long console) throws IOException {
/* 28 */     super(ps);
/*    */   }
/*    */   
/*    */   public WindowsAnsiProcessor(OutputStream ps, boolean stdout) throws IOException {
/* 32 */     super(ps);
/*    */   }
/*    */   
/*    */   public WindowsAnsiProcessor(OutputStream ps) throws IOException {
/* 36 */     super(ps);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\jansi\io\WindowsAnsiProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */