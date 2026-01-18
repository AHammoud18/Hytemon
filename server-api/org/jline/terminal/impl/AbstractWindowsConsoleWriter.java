/*    */ package org.jline.terminal.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractWindowsConsoleWriter
/*    */   extends Writer
/*    */ {
/*    */   protected abstract void writeConsole(char[] paramArrayOfchar, int paramInt) throws IOException;
/*    */   
/*    */   public void write(char[] cbuf, int off, int len) throws IOException {
/* 74 */     char[] text = cbuf;
/* 75 */     if (off != 0) {
/* 76 */       text = new char[len];
/* 77 */       System.arraycopy(cbuf, off, text, 0, len);
/*    */     } 
/*    */     
/* 80 */     synchronized (this.lock) {
/* 81 */       writeConsole(text, len);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void flush() {}
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\AbstractWindowsConsoleWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */