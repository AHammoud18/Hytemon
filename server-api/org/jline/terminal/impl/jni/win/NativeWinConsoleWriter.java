/*    */ package org.jline.terminal.impl.jni.win;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jline.nativ.Kernel32;
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
/*    */ class NativeWinConsoleWriter
/*    */   extends AbstractWindowsConsoleWriter
/*    */ {
/*    */   private final long console;
/* 19 */   private final int[] writtenChars = new int[1];
/*    */   
/*    */   public NativeWinConsoleWriter(long console) {
/* 22 */     this.console = console;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void writeConsole(char[] text, int len) throws IOException {
/* 27 */     if (Kernel32.WriteConsoleW(this.console, text, len, this.writtenChars, 0L) == 0)
/* 28 */       throw new IOException("Failed to write to console: " + Kernel32.getLastErrorMessage()); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jni\win\NativeWinConsoleWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */