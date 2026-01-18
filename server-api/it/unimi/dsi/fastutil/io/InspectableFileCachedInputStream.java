/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.WritableByteChannel;
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
/*     */ public class InspectableFileCachedInputStream
/*     */   extends MeasurableInputStream
/*     */   implements RepositionableStream, WritableByteChannel
/*     */ {
/*     */   public static final boolean DEBUG = false;
/*     */   public static final int DEFAULT_BUFFER_SIZE = 65536;
/*     */   public final byte[] buffer;
/*     */   public int inspectable;
/*     */   private final File overflowFile;
/*     */   private final RandomAccessFile randomAccessFile;
/*     */   private final FileChannel fileChannel;
/*     */   private long position;
/*     */   private long mark;
/*     */   private long writePosition;
/*     */   
/*     */   public InspectableFileCachedInputStream(int bufferSize, File overflowFile) throws IOException {
/*  99 */     if (bufferSize <= 0) throw new IllegalArgumentException("Illegal buffer size " + bufferSize); 
/* 100 */     if (overflowFile != null) { this.overflowFile = overflowFile; }
/* 101 */     else { (this.overflowFile = File.createTempFile(getClass().getSimpleName(), "overflow")).deleteOnExit(); }
/* 102 */      this.buffer = new byte[bufferSize];
/* 103 */     this.randomAccessFile = new RandomAccessFile(this.overflowFile, "rw");
/* 104 */     this.fileChannel = this.randomAccessFile.getChannel();
/* 105 */     this.mark = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InspectableFileCachedInputStream(int bufferSize) throws IOException {
/* 113 */     this(bufferSize, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public InspectableFileCachedInputStream() throws IOException {
/* 118 */     this(65536);
/*     */   }
/*     */   
/*     */   private void ensureOpen() throws IOException {
/* 122 */     if (this.position == -1L) throw new IOException("This " + getClass().getSimpleName() + " is closed");
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() throws IOException {
/* 128 */     if (!this.fileChannel.isOpen()) throw new IOException("This " + getClass().getSimpleName() + " is closed"); 
/* 129 */     this.writePosition = this.position = (this.inspectable = 0);
/* 130 */     this.mark = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int write(ByteBuffer byteBuffer) throws IOException {
/* 140 */     ensureOpen();
/* 141 */     int remaining = byteBuffer.remaining();
/*     */     
/* 143 */     if (this.inspectable < this.buffer.length) {
/*     */       
/* 145 */       int toBuffer = Math.min(this.buffer.length - this.inspectable, remaining);
/* 146 */       byteBuffer.get(this.buffer, this.inspectable, toBuffer);
/* 147 */       this.inspectable += toBuffer;
/*     */     } 
/*     */     
/* 150 */     if (byteBuffer.hasRemaining()) {
/* 151 */       this.fileChannel.position(this.writePosition);
/* 152 */       this.writePosition += this.fileChannel.write(byteBuffer);
/*     */     } 
/*     */     
/* 155 */     return remaining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void truncate(long size) throws FileNotFoundException, IOException {
/* 164 */     this.fileChannel.truncate(Math.max(size, this.writePosition));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 173 */     this.position = -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reopen() throws IOException {
/* 181 */     if (!this.fileChannel.isOpen()) throw new IOException("This " + getClass().getSimpleName() + " is closed"); 
/* 182 */     this.position = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() throws IOException {
/* 187 */     this.position = -1L;
/* 188 */     this.randomAccessFile.close();
/* 189 */     this.overflowFile.delete();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 195 */       dispose();
/*     */     } finally {
/*     */       
/* 198 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 204 */     ensureOpen();
/* 205 */     return (int)Math.min(2147483647L, length() - this.position);
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) throws IOException {
/* 210 */     ensureOpen();
/* 211 */     if (length == 0) return 0; 
/* 212 */     if (this.position == length()) return -1; 
/* 213 */     ByteArrays.ensureOffsetLength(b, offset, length);
/* 214 */     int read = 0;
/*     */     
/* 216 */     if (this.position < this.inspectable) {
/*     */       
/* 218 */       int toCopy = Math.min(this.inspectable - (int)this.position, length);
/* 219 */       System.arraycopy(this.buffer, (int)this.position, b, offset, toCopy);
/* 220 */       length -= toCopy;
/* 221 */       offset += toCopy;
/* 222 */       this.position += toCopy;
/* 223 */       read = toCopy;
/*     */     } 
/*     */     
/* 226 */     if (length > 0) {
/* 227 */       if (this.position == length()) return (read != 0) ? read : -1; 
/* 228 */       this.fileChannel.position(this.position - this.inspectable);
/* 229 */       int toRead = (int)Math.min(length() - this.position, length);
/*     */       
/* 231 */       int t = this.randomAccessFile.read(b, offset, toRead);
/* 232 */       this.position += t;
/* 233 */       read += t;
/*     */     } 
/*     */     
/* 236 */     return read;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 241 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 246 */     ensureOpen();
/* 247 */     long toSkip = Math.min(n, length() - this.position);
/* 248 */     this.position += toSkip;
/* 249 */     return toSkip;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 254 */     ensureOpen();
/* 255 */     if (this.position == length()) return -1; 
/* 256 */     if (this.position < this.inspectable) return this.buffer[(int)this.position++] & 0xFF; 
/* 257 */     this.fileChannel.position(this.position - this.inspectable);
/* 258 */     this.position++;
/* 259 */     return this.randomAccessFile.read();
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() throws IOException {
/* 264 */     ensureOpen();
/* 265 */     return this.inspectable + this.writePosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() throws IOException {
/* 270 */     ensureOpen();
/* 271 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void position(long position) throws IOException {
/* 280 */     this.position = Math.min(position, length());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 285 */     return (this.position != -1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mark(int readlimit) {
/* 290 */     this.mark = this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 295 */     ensureOpen();
/* 296 */     if (this.mark == -1L) throw new IOException("Mark has not been set"); 
/* 297 */     position(this.mark);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 302 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\io\InspectableFileCachedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */