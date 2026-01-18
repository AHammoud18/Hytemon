/*    */ package org.fusesource.jansi;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.fusesource.jansi.io.AnsiOutputStream;
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
/*    */ public class AnsiPrintStream
/*    */   extends PrintStream
/*    */ {
/*    */   public AnsiPrintStream(AnsiOutputStream out, boolean autoFlush) {
/* 31 */     super((OutputStream)out, autoFlush);
/*    */   }
/*    */ 
/*    */   
/*    */   public AnsiPrintStream(AnsiOutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
/* 36 */     super((OutputStream)out, autoFlush, encoding);
/*    */   }
/*    */   
/*    */   protected AnsiOutputStream getOut() {
/* 40 */     return (AnsiOutputStream)this.out;
/*    */   }
/*    */   
/*    */   public AnsiType getType() {
/* 44 */     return getOut().getType();
/*    */   }
/*    */   
/*    */   public AnsiColors getColors() {
/* 48 */     return getOut().getColors();
/*    */   }
/*    */   
/*    */   public AnsiMode getMode() {
/* 52 */     return getOut().getMode();
/*    */   }
/*    */   
/*    */   public void setMode(AnsiMode ansiMode) {
/* 56 */     getOut().setMode(ansiMode);
/*    */   }
/*    */   
/*    */   public boolean isResetAtUninstall() {
/* 60 */     return getOut().isResetAtUninstall();
/*    */   }
/*    */   
/*    */   public void setResetAtUninstall(boolean resetAtClose) {
/* 64 */     getOut().setResetAtUninstall(resetAtClose);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTerminalWidth() {
/* 72 */     return getOut().getTerminalWidth();
/*    */   }
/*    */   
/*    */   public void install() throws IOException {
/* 76 */     getOut().install();
/*    */   }
/*    */ 
/*    */   
/*    */   public void uninstall() throws IOException {
/* 81 */     AnsiOutputStream out = getOut();
/* 82 */     if (out != null) {
/* 83 */       out.uninstall();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 89 */     return "AnsiPrintStream{type=" + 
/* 90 */       getType() + ", colors=" + 
/* 91 */       getColors() + ", mode=" + 
/* 92 */       getMode() + ", resetAtUninstall=" + 
/* 93 */       isResetAtUninstall() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\AnsiPrintStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */