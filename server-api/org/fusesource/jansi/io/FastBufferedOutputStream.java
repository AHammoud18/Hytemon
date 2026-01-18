/*    */ package org.fusesource.jansi.io;
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
/*    */ public class FastBufferedOutputStream
/*    */   extends FilterOutputStream
/*    */ {
/* 27 */   protected final byte[] buf = new byte[8192];
/*    */   protected int count;
/*    */   
/*    */   public FastBufferedOutputStream(OutputStream out) {
/* 31 */     super(out);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 36 */     if (this.count >= this.buf.length) {
/* 37 */       flushBuffer();
/*    */     }
/* 39 */     this.buf[this.count++] = (byte)b;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 44 */     if (len >= this.buf.length) {
/* 45 */       flushBuffer();
/* 46 */       this.out.write(b, off, len);
/*    */       return;
/*    */     } 
/* 49 */     if (len > this.buf.length - this.count) {
/* 50 */       flushBuffer();
/*    */     }
/* 52 */     System.arraycopy(b, off, this.buf, this.count, len);
/* 53 */     this.count += len;
/*    */   }
/*    */   
/*    */   private void flushBuffer() throws IOException {
/* 57 */     if (this.count > 0) {
/* 58 */       this.out.write(this.buf, 0, this.count);
/* 59 */       this.count = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {
/* 65 */     flushBuffer();
/* 66 */     this.out.flush();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\io\FastBufferedOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */