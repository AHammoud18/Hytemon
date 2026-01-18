/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public abstract class NonBlockingInputStream
/*     */   extends InputStream
/*     */ {
/*     */   public static final int EOF = -1;
/*     */   public static final int READ_EXPIRED = -2;
/*     */   
/*     */   public int read() throws IOException {
/*  63 */     return read(0L, false);
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
/*  76 */     return read(timeout, true);
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
/*  89 */     return read(timeout, false);
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/*  93 */     if (b == null)
/*  94 */       throw new NullPointerException(); 
/*  95 */     if (off < 0 || len < 0 || len > b.length - off)
/*  96 */       throw new IndexOutOfBoundsException(); 
/*  97 */     if (len == 0) {
/*  98 */       return 0;
/*     */     }
/* 100 */     int c = read();
/* 101 */     if (c == -1) {
/* 102 */       return -1;
/*     */     }
/* 104 */     b[off] = (byte)c;
/* 105 */     return 1;
/*     */   }
/*     */   
/*     */   public int readBuffered(byte[] b) throws IOException {
/* 109 */     return readBuffered(b, 0L);
/*     */   }
/*     */   
/*     */   public int readBuffered(byte[] b, long timeout) throws IOException {
/* 113 */     return readBuffered(b, 0, b.length, timeout);
/*     */   }
/*     */   
/*     */   public int readBuffered(byte[] b, int off, int len, long timeout) throws IOException {
/* 117 */     if (b == null)
/* 118 */       throw new NullPointerException(); 
/* 119 */     if (off < 0 || len < 0 || off + len < b.length)
/* 120 */       throw new IllegalArgumentException(); 
/* 121 */     if (len == 0) {
/* 122 */       return 0;
/*     */     }
/* 124 */     Timeout t = new Timeout(timeout);
/* 125 */     int nb = 0;
/* 126 */     while (!t.elapsed()) {
/* 127 */       int r = read((nb > 0) ? 1L : t.timeout());
/* 128 */       if (r < 0) {
/* 129 */         return (nb > 0) ? nb : r;
/*     */       }
/* 131 */       b[off + nb++] = (byte)r;
/* 132 */       if (nb >= len || t.isInfinite()) {
/*     */         break;
/*     */       }
/*     */     } 
/* 136 */     return nb;
/*     */   }
/*     */   
/*     */   public void shutdown() {}
/*     */   
/*     */   public abstract int read(long paramLong, boolean paramBoolean) throws IOException;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\NonBlockingInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */