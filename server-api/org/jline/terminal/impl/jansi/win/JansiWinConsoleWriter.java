/*    */ package org.jline.terminal.impl.jansi.win;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.fusesource.jansi.internal.Kernel32;
/*    */ import org.jline.terminal.impl.AbstractWindowsConsoleWriter;
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
/*    */ class JansiWinConsoleWriter
/*    */   extends AbstractWindowsConsoleWriter
/*    */ {
/*    */   private final long console;
/* 21 */   private final int[] writtenChars = new int[1];
/*    */   
/*    */   public JansiWinConsoleWriter(long console) {
/* 24 */     this.console = console;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void writeConsole(char[] text, int len) throws IOException {
/* 29 */     if (Kernel32.WriteConsoleW(this.console, text, len, this.writtenChars, 0L) == 0)
/* 30 */       throw new IOException("Failed to write to console: " + WindowsSupport.getLastErrorMessage()); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jansi\win\JansiWinConsoleWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */