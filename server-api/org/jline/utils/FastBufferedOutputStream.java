/*    */ package org.jline.utils;
/*    */ 
/*    */ import java.io.FilterOutputStream;
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
/*    */ public class FastBufferedOutputStream
/*    */   extends FilterOutputStream
/*    */ {
/* 47 */   protected final byte[] buf = new byte[8192];
/*    */   protected int count;
/*    */   
/*    */   public FastBufferedOutputStream(OutputStream out) {
/* 51 */     super(out);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 56 */     if (this.count >= this.buf.length) {
/* 57 */       flushBuffer();
/*    */     }
/* 59 */     this.buf[this.count++] = (byte)b;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 64 */     if (len >= this.buf.length) {
/* 65 */       flushBuffer();
/* 66 */       this.out.write(b, off, len);
/*    */       return;
/*    */     } 
/* 69 */     if (len > this.buf.length - this.count) {
/* 70 */       flushBuffer();
/*    */     }
/* 72 */     System.arraycopy(b, off, this.buf, this.count, len);
/* 73 */     this.count += len;
/*    */   }
/*    */   
/*    */   private void flushBuffer() throws IOException {
/* 77 */     if (this.count > 0) {
/* 78 */       this.out.write(this.buf, 0, this.count);
/* 79 */       this.count = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {
/* 85 */     flushBuffer();
/* 86 */     this.out.flush();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\FastBufferedOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */