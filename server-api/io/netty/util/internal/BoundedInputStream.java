/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ public final class BoundedInputStream
/*    */   extends FilterInputStream
/*    */ {
/*    */   private final int maxBytesRead;
/*    */   private int numRead;
/*    */   
/*    */   public BoundedInputStream(@NotNull InputStream in, int maxBytesRead) {
/* 30 */     super(in);
/* 31 */     this.maxBytesRead = ObjectUtil.checkPositive(maxBytesRead, "maxRead");
/*    */   }
/*    */   
/*    */   public BoundedInputStream(@NotNull InputStream in) {
/* 35 */     this(in, 8192);
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 40 */     checkMaxBytesRead();
/*    */     
/* 42 */     int b = super.read();
/* 43 */     if (b != -1) {
/* 44 */       this.numRead++;
/*    */     }
/* 46 */     return b;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] buf, int off, int len) throws IOException {
/* 51 */     checkMaxBytesRead();
/*    */ 
/*    */     
/* 54 */     int num = Math.min(len, this.maxBytesRead - this.numRead + 1);
/*    */     
/* 56 */     int b = super.read(buf, off, num);
/*    */     
/* 58 */     if (b != -1) {
/* 59 */       this.numRead += b;
/*    */     }
/* 61 */     return b;
/*    */   }
/*    */   
/*    */   private void checkMaxBytesRead() throws IOException {
/* 65 */     if (this.numRead > this.maxBytesRead)
/* 66 */       throw new IOException("Maximum number of bytes read: " + this.numRead); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\BoundedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */