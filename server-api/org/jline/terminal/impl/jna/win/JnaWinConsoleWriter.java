/*    */ package org.jline.terminal.impl.jna.win;
/*    */ 
/*    */ import com.sun.jna.LastErrorException;
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.ptr.IntByReference;
/*    */ import java.io.IOException;
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
/*    */ class JnaWinConsoleWriter
/*    */   extends AbstractWindowsConsoleWriter
/*    */ {
/*    */   private final Pointer console;
/* 22 */   private final IntByReference writtenChars = new IntByReference();
/*    */   
/*    */   JnaWinConsoleWriter(Pointer console) {
/* 25 */     this.console = console;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void writeConsole(char[] text, int len) throws IOException {
/*    */     try {
/* 31 */       Kernel32.INSTANCE.WriteConsoleW(this.console, text, len, this.writtenChars, null);
/* 32 */     } catch (LastErrorException e) {
/* 33 */       throw new IOException("Failed to write to console", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\win\JnaWinConsoleWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */