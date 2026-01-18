/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.LongBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.Arrays;
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
/*     */ public class LongMappedBigList
/*     */   extends AbstractLongBigList
/*     */ {
/*  55 */   public static int LOG2_BYTES = 63 - Long.numberOfLeadingZeros(8L);
/*     */   
/*     */   @Deprecated
/*  58 */   public static int LOG2_BITS = 63 - Long.numberOfLeadingZeros(8L);
/*  59 */   private static int CHUNK_SHIFT = 30 - LOG2_BYTES;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final long CHUNK_SIZE = 1L << CHUNK_SHIFT;
/*     */   
/*  66 */   private static final long CHUNK_MASK = CHUNK_SIZE - 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final LongBuffer[] buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean[] readyToUse;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int n;
/*     */ 
/*     */ 
/*     */   
/*     */   private final long size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LongMappedBigList(LongBuffer[] buffer, long size, boolean[] readyToUse) {
/*  91 */     this.buffer = buffer;
/*  92 */     this.n = buffer.length;
/*  93 */     this.size = size;
/*  94 */     this.readyToUse = readyToUse;
/*  95 */     for (int i = 0; i < this.n; ) { if (i < this.n - 1 && buffer[i].capacity() != CHUNK_SIZE) throw new IllegalArgumentException();  i++; }
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
/*     */   public static LongMappedBigList map(FileChannel fileChannel) throws IOException {
/* 108 */     return map(fileChannel, ByteOrder.BIG_ENDIAN);
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
/*     */   public static LongMappedBigList map(FileChannel fileChannel, ByteOrder byteOrder) throws IOException {
/* 121 */     return map(fileChannel, byteOrder, FileChannel.MapMode.READ_ONLY);
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
/*     */   public static LongMappedBigList map(FileChannel fileChannel, ByteOrder byteOrder, FileChannel.MapMode mapMode) throws IOException {
/* 134 */     long size = fileChannel.size() / 8L;
/* 135 */     int chunks = (int)((size + CHUNK_SIZE - 1L) / CHUNK_SIZE);
/* 136 */     LongBuffer[] buffer = new LongBuffer[chunks];
/* 137 */     for (int i = 0; i < chunks; ) { buffer[i] = fileChannel.map(mapMode, i * CHUNK_SIZE * 8L, Math.min(CHUNK_SIZE, size - i * CHUNK_SIZE) * 8L).order(byteOrder).asLongBuffer(); i++; }
/* 138 */      boolean[] readyToUse = new boolean[chunks];
/* 139 */     Arrays.fill(readyToUse, true);
/* 140 */     return new LongMappedBigList(buffer, size, readyToUse);
/*     */   }
/*     */   
/*     */   private LongBuffer LongBuffer(int n) {
/* 144 */     if (this.readyToUse[n]) return this.buffer[n]; 
/* 145 */     this.readyToUse[n] = true;
/* 146 */     this.buffer[n] = this.buffer[n].duplicate(); return this.buffer[n].duplicate();
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
/*     */   public LongMappedBigList copy() {
/* 158 */     return new LongMappedBigList((LongBuffer[])this.buffer.clone(), this.size, new boolean[this.n]);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(long index) {
/* 163 */     return LongBuffer((int)(index >>> CHUNK_SHIFT)).get((int)(index & CHUNK_MASK));
/*     */   }
/*     */ 
/*     */   
/*     */   public void getElements(long from, long[] a, int offset, int length) {
/* 168 */     int chunk = (int)(from >>> CHUNK_SHIFT);
/* 169 */     int displ = (int)(from & CHUNK_MASK);
/* 170 */     while (length > 0) {
/* 171 */       LongBuffer b = LongBuffer(chunk);
/* 172 */       int l = Math.min(b.capacity() - displ, length);
/* 173 */       if (l == 0) throw new ArrayIndexOutOfBoundsException();
/*     */       
/* 175 */       b.position(displ);
/* 176 */       b.get(a, offset, l);
/* 177 */       if ((displ += l) == CHUNK_SIZE) {
/* 178 */         displ = 0;
/* 179 */         chunk++;
/*     */       } 
/* 181 */       offset += l;
/* 182 */       length -= l;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long set(long index, long value) {
/* 188 */     LongBuffer b = LongBuffer((int)(index >>> CHUNK_SHIFT));
/* 189 */     int i = (int)(index & CHUNK_MASK);
/* 190 */     long previousValue = b.get(i);
/* 191 */     b.put(i, value);
/* 192 */     return previousValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public long size64() {
/* 197 */     return this.size;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongMappedBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */