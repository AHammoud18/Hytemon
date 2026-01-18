/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import java.io.EOFException;
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
/*     */ public class FastMultiByteArrayInputStream
/*     */   extends MeasurableInputStream
/*     */   implements RepositionableStream
/*     */ {
/*     */   public static final int SLICE_BITS = 30;
/*     */   public static final int SLICE_SIZE = 1073741824;
/*     */   public static final int SLICE_MASK = 1073741823;
/*     */   public byte[][] array;
/*     */   public byte[] current;
/*     */   public long length;
/*     */   private long position;
/*     */   
/*     */   public FastMultiByteArrayInputStream(MeasurableInputStream is) throws IOException {
/*  63 */     this(is, is.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastMultiByteArrayInputStream(InputStream is, long size) throws IOException {
/*  73 */     this.length = size;
/*  74 */     this.array = new byte[(int)((size + 1073741824L - 1L) / 1073741824L) + 1][];
/*     */     
/*  76 */     for (int i = 0; i < this.array.length - 1; i++) {
/*  77 */       this.array[i] = new byte[(size >= 1073741824L) ? 1073741824 : (int)size];
/*     */       
/*  79 */       if (read(is, this.array[i]) != (this.array[i]).length) throw new EOFException(); 
/*  80 */       size -= (this.array[i]).length;
/*     */     } 
/*     */     
/*  83 */     this.current = this.array[0];
/*     */   }
/*     */   
/*     */   private static long read(InputStream is, byte[] a) throws IOException {
/*  87 */     if (a.length == 0) return 0L;
/*     */     
/*  89 */     int read = 0;
/*     */     do {
/*  91 */       int result = is.read(a, read, Math.min(a.length - read, 1048576));
/*  92 */       if (result < 0) return read; 
/*  93 */       read += result;
/*  94 */     } while (read < a.length);
/*     */     
/*  96 */     return read;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastMultiByteArrayInputStream(FastMultiByteArrayInputStream is) {
/* 105 */     this.array = is.array;
/* 106 */     this.length = is.length;
/* 107 */     this.current = this.array[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastMultiByteArrayInputStream(byte[] array) {
/* 116 */     if (array.length == 0) { this.array = new byte[1][]; }
/*     */     else
/* 118 */     { this.array = new byte[2][];
/* 119 */       this.array[0] = array;
/* 120 */       this.length = array.length;
/* 121 */       this.current = array; }
/*     */   
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
/*     */   public int available() {
/* 135 */     return (int)Math.min(2147483647L, this.length - this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) {
/* 140 */     if (n > this.length - this.position) n = this.length - this.position; 
/* 141 */     this.position += n;
/* 142 */     updateCurrent();
/* 143 */     return n;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() {
/* 148 */     if (this.length == this.position) return -1; 
/* 149 */     int disp = (int)(this.position++ & 0x3FFFFFFFL);
/* 150 */     if (disp == 0) updateCurrent(); 
/* 151 */     return this.current[disp] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) {
/* 156 */     long remaining = this.length - this.position;
/* 157 */     if (remaining == 0L) return (length == 0) ? 0 : -1; 
/* 158 */     int n = (int)Math.min(length, remaining);
/* 159 */     int m = n;
/*     */     
/*     */     while (true) {
/* 162 */       int disp = (int)(this.position & 0x3FFFFFFFL);
/* 163 */       if (disp == 0) updateCurrent(); 
/* 164 */       int res = Math.min(n, this.current.length - disp);
/* 165 */       System.arraycopy(this.current, disp, b, offset, res);
/*     */       
/* 167 */       n -= res;
/* 168 */       this.position += res;
/* 169 */       if (n == 0) return m; 
/* 170 */       offset += res;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateCurrent() {
/* 175 */     this.current = this.array[(int)(this.position >>> 30L)];
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() {
/* 180 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void position(long newPosition) {
/* 185 */     this.position = Math.min(newPosition, this.length);
/* 186 */     updateCurrent();
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() throws IOException {
/* 191 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mark(int dummy) {
/* 205 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 210 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\io\FastMultiByteArrayInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */