/*    */ package org.jline.jansi;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.jline.jansi.io.AnsiOutputStream;
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
/*    */ public class AnsiPrintStream
/*    */   extends PrintStream
/*    */ {
/*    */   public AnsiPrintStream(AnsiOutputStream out, boolean autoFlush) {
/* 24 */     super((OutputStream)out, autoFlush);
/*    */   }
/*    */ 
/*    */   
/*    */   public AnsiPrintStream(AnsiOutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
/* 29 */     super((OutputStream)out, autoFlush, encoding);
/*    */   }
/*    */   
/*    */   protected AnsiOutputStream getOut() {
/* 33 */     return (AnsiOutputStream)this.out;
/*    */   }
/*    */   
/*    */   public AnsiType getType() {
/* 37 */     return getOut().getType();
/*    */   }
/*    */   
/*    */   public AnsiColors getColors() {
/* 41 */     return getOut().getColors();
/*    */   }
/*    */   
/*    */   public AnsiMode getMode() {
/* 45 */     return getOut().getMode();
/*    */   }
/*    */   
/*    */   public void setMode(AnsiMode ansiMode) {
/* 49 */     getOut().setMode(ansiMode);
/*    */   }
/*    */   
/*    */   public boolean isResetAtUninstall() {
/* 53 */     return getOut().isResetAtUninstall();
/*    */   }
/*    */   
/*    */   public void setResetAtUninstall(boolean resetAtClose) {
/* 57 */     getOut().setResetAtUninstall(resetAtClose);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTerminalWidth() {
/* 65 */     return getOut().getTerminalWidth();
/*    */   }
/*    */   
/*    */   public void install() throws IOException {
/* 69 */     getOut().install();
/*    */   }
/*    */ 
/*    */   
/*    */   public void uninstall() throws IOException {
/* 74 */     AnsiOutputStream out = getOut();
/* 75 */     if (out != null) {
/* 76 */       out.uninstall();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     return "AnsiPrintStream{type=" + 
/* 83 */       getType() + ", colors=" + 
/* 84 */       getColors() + ", mode=" + 
/* 85 */       getMode() + ", resetAtUninstall=" + 
/* 86 */       isResetAtUninstall() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\jansi\AnsiPrintStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */