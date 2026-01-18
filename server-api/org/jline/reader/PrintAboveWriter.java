/*    */ package org.jline.reader;
/*    */ 
/*    */ import java.io.StringWriter;
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
/*    */ public class PrintAboveWriter
/*    */   extends StringWriter
/*    */ {
/*    */   private final LineReader reader;
/*    */   
/*    */   public PrintAboveWriter(LineReader reader) {
/* 30 */     this.reader = reader;
/*    */   }
/*    */ 
/*    */   
/*    */   public void flush() {
/* 35 */     StringBuffer buffer = getBuffer();
/* 36 */     int lastNewline = buffer.lastIndexOf("\n");
/* 37 */     if (lastNewline >= 0) {
/* 38 */       this.reader.printAbove(buffer.substring(0, lastNewline + 1));
/* 39 */       buffer.delete(0, lastNewline + 1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\PrintAboveWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */