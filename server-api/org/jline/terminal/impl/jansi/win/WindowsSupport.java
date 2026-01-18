/*    */ package org.jline.terminal.impl.jansi.win;
/*    */ 
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import org.fusesource.jansi.internal.Kernel32;
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
/*    */ class WindowsSupport
/*    */ {
/*    */   public static String getLastErrorMessage() {
/* 20 */     int errorCode = Kernel32.GetLastError();
/* 21 */     int bufferSize = 160;
/* 22 */     byte[] data = new byte[bufferSize];
/* 23 */     Kernel32.FormatMessageW(Kernel32.FORMAT_MESSAGE_FROM_SYSTEM, 0L, errorCode, 0, data, bufferSize, null);
/* 24 */     return (new String(data, StandardCharsets.UTF_16LE)).trim();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jansi\win\WindowsSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */