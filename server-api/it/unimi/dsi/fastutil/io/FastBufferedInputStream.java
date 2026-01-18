/*     */ package it.unimi.dsi.fastutil.io;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.EnumSet;
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
/*     */ public class FastBufferedInputStream
/*     */   extends MeasurableInputStream
/*     */   implements RepositionableStream
/*     */ {
/*     */   public static final int DEFAULT_BUFFER_SIZE = 8192;
/*     */   
/*     */   public enum LineTerminator
/*     */   {
/*  87 */     CR,
/*     */     
/*  89 */     LF,
/*     */     
/*  91 */     CR_LF;
/*     */   }
/*     */ 
/*     */   
/*  95 */   public static final EnumSet<LineTerminator> ALL_TERMINATORS = EnumSet.allOf(LineTerminator.class);
/*     */ 
/*     */   
/*     */   protected InputStream is;
/*     */ 
/*     */   
/*     */   protected byte[] buffer;
/*     */ 
/*     */   
/*     */   protected int pos;
/*     */ 
/*     */   
/*     */   protected long readBytes;
/*     */ 
/*     */   
/*     */   protected int avail;
/*     */ 
/*     */   
/*     */   private FileChannel fileChannel;
/*     */ 
/*     */   
/*     */   private RepositionableStream repositionableStream;
/*     */ 
/*     */   
/*     */   private MeasurableStream measurableStream;
/*     */ 
/*     */ 
/*     */   
/*     */   private static int ensureBufferSize(int bufferSize) {
/* 124 */     if (bufferSize <= 0) throw new IllegalArgumentException("Illegal buffer size: " + bufferSize); 
/* 125 */     return bufferSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedInputStream(InputStream is, byte[] buffer) {
/* 134 */     this.is = is;
/* 135 */     ensureBufferSize(buffer.length);
/* 136 */     this.buffer = buffer;
/*     */     
/* 138 */     if (is instanceof RepositionableStream) this.repositionableStream = (RepositionableStream)is; 
/* 139 */     if (is instanceof MeasurableStream) this.measurableStream = (MeasurableStream)is;
/*     */     
/* 141 */     if (this.repositionableStream == null) {
/*     */ 
/*     */       
/* 144 */       try { this.fileChannel = (FileChannel)is.getClass().getMethod("getChannel", new Class[0]).invoke(is, new Object[0]); }
/*     */       
/* 146 */       catch (IllegalAccessException illegalAccessException) {  }
/* 147 */       catch (IllegalArgumentException illegalArgumentException) {  }
/* 148 */       catch (NoSuchMethodException noSuchMethodException) {  }
/* 149 */       catch (InvocationTargetException invocationTargetException) {  }
/* 150 */       catch (ClassCastException classCastException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedInputStream(InputStream is, int bufferSize) {
/* 160 */     this(is, new byte[ensureBufferSize(bufferSize)]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastBufferedInputStream(InputStream is) {
/* 168 */     this(is, 8192);
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
/*     */   protected boolean noMoreCharacters() throws IOException {
/* 180 */     if (this.avail == 0) {
/* 181 */       this.avail = this.is.read(this.buffer);
/* 182 */       if (this.avail <= 0) {
/* 183 */         this.avail = 0;
/* 184 */         return true;
/*     */       } 
/* 186 */       this.pos = 0;
/*     */     } 
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 193 */     if (noMoreCharacters()) return -1; 
/* 194 */     this.avail--;
/* 195 */     this.readBytes++;
/* 196 */     return this.buffer[this.pos++] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) throws IOException {
/* 201 */     if (length <= this.avail) {
/* 202 */       System.arraycopy(this.buffer, this.pos, b, offset, length);
/* 203 */       this.pos += length;
/* 204 */       this.avail -= length;
/* 205 */       this.readBytes += length;
/* 206 */       return length;
/*     */     } 
/*     */     
/* 209 */     int head = this.avail;
/*     */     
/* 211 */     System.arraycopy(this.buffer, this.pos, b, offset, head);
/* 212 */     this.pos = this.avail = 0;
/* 213 */     this.readBytes += head;
/*     */     
/* 215 */     if (length > this.buffer.length) {
/*     */       
/* 217 */       int result = this.is.read(b, offset + head, length - head);
/* 218 */       if (result > 0) this.readBytes += result; 
/* 219 */       return (result < 0) ? ((head == 0) ? -1 : head) : (result + head);
/*     */     } 
/*     */     
/* 222 */     if (noMoreCharacters()) return (head == 0) ? -1 : head;
/*     */     
/* 224 */     int toRead = Math.min(length - head, this.avail);
/* 225 */     this.readBytes += toRead;
/* 226 */     System.arraycopy(this.buffer, 0, b, offset + head, toRead);
/* 227 */     this.pos = toRead;
/* 228 */     this.avail -= toRead;
/*     */ 
/*     */     
/* 231 */     return toRead + head;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readLine(byte[] array) throws IOException {
/* 242 */     return readLine(array, 0, array.length, ALL_TERMINATORS);
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
/*     */   public int readLine(byte[] array, EnumSet<LineTerminator> terminators) throws IOException {
/* 255 */     return readLine(array, 0, array.length, terminators);
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
/*     */   public int readLine(byte[] array, int off, int len) throws IOException {
/* 267 */     return readLine(array, off, len, ALL_TERMINATORS);
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
/*     */   public int readLine(byte[] array, int off, int len, EnumSet<LineTerminator> terminators) throws IOException {
/* 318 */     ByteArrays.ensureOffsetLength(array, off, len);
/* 319 */     if (len == 0) return 0; 
/* 320 */     if (noMoreCharacters()) return -1; 
/* 321 */     int k = 0, remaining = len, read = 0; while (true) {
/*     */       int i;
/* 323 */       for (i = 0; i < this.avail && i < remaining && (k = this.buffer[this.pos + i]) != 10 && k != 13; i++);
/* 324 */       System.arraycopy(this.buffer, this.pos, array, off + read, i);
/* 325 */       this.pos += i;
/* 326 */       this.avail -= i;
/* 327 */       read += i;
/* 328 */       remaining -= i;
/* 329 */       if (remaining == 0) {
/* 330 */         this.readBytes += read;
/* 331 */         return read;
/*     */       } 
/*     */       
/* 334 */       if (this.avail > 0) {
/* 335 */         if (k == 10) {
/* 336 */           this.pos++;
/* 337 */           this.avail--;
/* 338 */           if (terminators.contains(LineTerminator.LF)) {
/* 339 */             this.readBytes += (read + 1);
/* 340 */             return read;
/*     */           } 
/*     */           
/* 343 */           array[off + read++] = 10;
/* 344 */           remaining--;
/*     */           continue;
/*     */         } 
/* 347 */         if (k == 13) {
/* 348 */           this.pos++;
/* 349 */           this.avail--;
/*     */           
/* 351 */           if (terminators.contains(LineTerminator.CR_LF)) {
/* 352 */             if (this.avail > 0) {
/* 353 */               if (this.buffer[this.pos] == 10) {
/* 354 */                 this.pos++;
/* 355 */                 this.avail--;
/* 356 */                 this.readBytes += (read + 2);
/* 357 */                 return read;
/*     */               } 
/*     */             } else {
/*     */               
/* 361 */               if (noMoreCharacters()) {
/*     */ 
/*     */                 
/* 364 */                 if (!terminators.contains(LineTerminator.CR)) {
/* 365 */                   array[off + read++] = 13;
/* 366 */                   remaining--;
/* 367 */                   this.readBytes += read;
/*     */                 } else {
/* 369 */                   this.readBytes += (read + 1);
/*     */                 } 
/* 371 */                 return read;
/*     */               } 
/* 373 */               if (this.buffer[0] == 10) {
/*     */                 
/* 375 */                 this.pos++;
/* 376 */                 this.avail--;
/* 377 */                 this.readBytes += (read + 2);
/* 378 */                 return read;
/*     */               } 
/*     */             } 
/*     */           }
/*     */           
/* 383 */           if (terminators.contains(LineTerminator.CR)) {
/* 384 */             this.readBytes += (read + 1);
/* 385 */             return read;
/*     */           } 
/*     */           
/* 388 */           array[off + read++] = 13;
/* 389 */           remaining--;
/*     */         }  continue;
/*     */       } 
/* 392 */       if (noMoreCharacters()) {
/* 393 */         this.readBytes += read;
/* 394 */         return read;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void position(long newPosition) throws IOException {
/* 402 */     long position = this.readBytes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     if (newPosition <= position + this.avail && newPosition >= position - this.pos) {
/* 410 */       this.pos = (int)(this.pos + newPosition - position);
/* 411 */       this.avail = (int)(this.avail - newPosition - position);
/* 412 */       this.readBytes = newPosition;
/*     */       
/*     */       return;
/*     */     } 
/* 416 */     if (this.repositionableStream != null) { this.repositionableStream.position(newPosition); }
/* 417 */     else if (this.fileChannel != null) { this.fileChannel.position(newPosition); }
/* 418 */     else { throw new UnsupportedOperationException("position() can only be called if the underlying byte stream implements the RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel"); }
/* 419 */      this.readBytes = newPosition;
/*     */     
/* 421 */     this.avail = this.pos = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() throws IOException {
/* 426 */     return this.readBytes;
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
/*     */   public long length() throws IOException {
/* 438 */     if (this.measurableStream != null) return this.measurableStream.length(); 
/* 439 */     if (this.fileChannel != null) return this.fileChannel.size(); 
/* 440 */     throw new UnsupportedOperationException();
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
/*     */   private long skipByReading(long n) throws IOException {
/* 453 */     long toSkip = n;
/*     */     
/* 455 */     while (toSkip > 0L) {
/* 456 */       int len = this.is.read(this.buffer, 0, (int)Math.min(this.buffer.length, toSkip));
/* 457 */       if (len > 0) toSkip -= len;
/*     */     
/*     */     } 
/*     */     
/* 461 */     return n - toSkip;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 481 */     if (n <= this.avail) {
/* 482 */       int m = (int)n;
/* 483 */       this.pos += m;
/* 484 */       this.avail -= m;
/* 485 */       this.readBytes += n;
/* 486 */       return n;
/*     */     } 
/*     */     
/* 489 */     long toSkip = n - this.avail, result = 0L;
/* 490 */     this.avail = 0;
/*     */     
/* 492 */     while (toSkip != 0L) { if ((result = (this.is == System.in) ? skipByReading(toSkip) : this.is.skip(toSkip)) < toSkip) {
/* 493 */         if (result == 0L) {
/* 494 */           if (this.is.read() == -1)
/* 495 */             break;  toSkip--; continue;
/*     */         } 
/* 497 */         toSkip -= result;
/*     */       }  }
/*     */     
/* 500 */     long t = n - toSkip - result;
/* 501 */     this.readBytes += t;
/* 502 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 507 */     return (int)Math.min(this.is.available() + this.avail, 2147483647L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 512 */     if (this.is == null)
/* 513 */       return;  if (this.is != System.in) this.is.close(); 
/* 514 */     this.is = null;
/* 515 */     this.buffer = null;
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
/*     */   public void flush() {
/* 529 */     if (this.is == null)
/* 530 */       return;  this.readBytes += this.avail;
/* 531 */     this.avail = this.pos = 0;
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
/*     */   @Deprecated
/*     */   public void reset() {
/* 544 */     flush();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\io\FastBufferedInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */