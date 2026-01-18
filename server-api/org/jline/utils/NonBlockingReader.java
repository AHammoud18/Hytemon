/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class NonBlockingReader
/*     */   extends Reader
/*     */ {
/*     */   public static final int EOF = -1;
/*     */   public static final int READ_EXPIRED = -2;
/*     */   
/*     */   public void shutdown() {}
/*     */   
/*     */   public int read() throws IOException {
/*  75 */     return read(0L, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int peek(long timeout) throws IOException {
/*  88 */     return read(timeout, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(long timeout) throws IOException {
/* 101 */     return read(timeout, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(char[] b, int off, int len) throws IOException {
/* 115 */     if (b == null)
/* 116 */       throw new NullPointerException(); 
/* 117 */     if (off < 0 || len < 0 || len > b.length - off)
/* 118 */       throw new IndexOutOfBoundsException(); 
/* 119 */     if (len == 0) {
/* 120 */       return 0;
/*     */     }
/*     */     
/* 123 */     int c = read(0L);
/*     */     
/* 125 */     if (c == -1) {
/* 126 */       return -1;
/*     */     }
/* 128 */     b[off] = (char)c;
/* 129 */     return 1;
/*     */   }
/*     */   
/*     */   public int readBuffered(char[] b) throws IOException {
/* 133 */     return readBuffered(b, 0L);
/*     */   }
/*     */   
/*     */   public int readBuffered(char[] b, long timeout) throws IOException {
/* 137 */     return readBuffered(b, 0, b.length, timeout);
/*     */   }
/*     */   
/*     */   public abstract int readBuffered(char[] paramArrayOfchar, int paramInt1, int paramInt2, long paramLong) throws IOException;
/*     */   
/*     */   public int available() {
/* 143 */     return 0;
/*     */   }
/*     */   
/*     */   protected abstract int read(long paramLong, boolean paramBoolean) throws IOException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\NonBlockingReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */