/*    */ package org.jline.terminal.impl.ffm;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.foreign.Arena;
/*    */ import java.lang.foreign.MemorySegment;
/*    */ import java.lang.foreign.ValueLayout;
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
/*    */ 
/*    */ 
/*    */ class NativeWinConsoleWriter
/*    */   extends AbstractWindowsConsoleWriter
/*    */ {
/* 24 */   private final MemorySegment console = Kernel32.GetStdHandle(-11);
/*    */ 
/*    */   
/*    */   protected void writeConsole(char[] text, int len) throws IOException {
/* 28 */     Arena arena = Arena.ofConfined(); try {
/* 29 */       MemorySegment txt = arena.allocateFrom(ValueLayout.JAVA_CHAR, text);
/* 30 */       if (Kernel32.WriteConsoleW(this.console, txt, len, MemorySegment.NULL, MemorySegment.NULL) == 0) {
/* 31 */         throw new IOException("Failed to write to console: " + Kernel32.getLastErrorMessage());
/*    */       }
/* 33 */       if (arena != null) arena.close(); 
/*    */     } catch (Throwable throwable) {
/*    */       if (arena != null)
/*    */         try {
/*    */           arena.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\NativeWinConsoleWriter.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */