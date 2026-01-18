/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.Reader;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PumpReader
/*     */   extends Reader
/*     */ {
/*     */   private static final int EOF = -1;
/*     */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*     */   private final CharBuffer readBuffer;
/*     */   private final CharBuffer writeBuffer;
/*     */   private final Writer writer;
/*     */   private boolean closed;
/*     */   
/*     */   public PumpReader() {
/*  66 */     this(4096);
/*     */   }
/*     */   
/*     */   public PumpReader(int bufferSize) {
/*  70 */     char[] buf = new char[Math.max(bufferSize, 2)];
/*  71 */     this.readBuffer = CharBuffer.wrap(buf);
/*  72 */     this.writeBuffer = CharBuffer.wrap(buf);
/*  73 */     this.writer = new Writer(this);
/*     */ 
/*     */     
/*  76 */     this.readBuffer.limit(0);
/*     */   }
/*     */   
/*     */   public java.io.Writer getWriter() {
/*  80 */     return this.writer;
/*     */   }
/*     */   
/*     */   public java.io.InputStream createInputStream(Charset charset) {
/*  84 */     return new InputStream(this, charset);
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
/*     */   private boolean waitForMoreInput() throws InterruptedIOException {
/*  96 */     if (!this.writeBuffer.hasRemaining()) {
/*  97 */       throw new AssertionError("No space in write buffer");
/*     */     }
/*     */     
/* 100 */     int oldRemaining = this.readBuffer.remaining();
/*     */     
/*     */     do {
/* 103 */       if (this.closed) {
/* 104 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 108 */       notifyAll();
/*     */       
/*     */       try {
/* 111 */         wait();
/* 112 */       } catch (InterruptedException e) {
/* 113 */         throw new InterruptedIOException();
/*     */       } 
/* 115 */     } while (this.readBuffer.remaining() <= oldRemaining);
/*     */     
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean wait(CharBuffer buffer) throws InterruptedIOException {
/* 128 */     while (!buffer.hasRemaining()) {
/* 129 */       if (this.closed) {
/* 130 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 134 */       notifyAll();
/*     */       
/*     */       try {
/* 137 */         wait();
/* 138 */       } catch (InterruptedException e) {
/* 139 */         throw new InterruptedIOException();
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean waitForInput() throws InterruptedIOException {
/* 153 */     return wait(this.readBuffer);
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
/*     */   private void waitForBufferSpace() throws InterruptedIOException, ClosedException {
/* 165 */     if (!wait(this.writeBuffer) || this.closed) {
/* 166 */       throw new ClosedException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean rewind(CharBuffer buffer, CharBuffer other) {
/* 172 */     if (buffer.position() > other.position()) {
/* 173 */       other.limit(buffer.position());
/*     */     }
/*     */ 
/*     */     
/* 177 */     if (buffer.position() == buffer.capacity()) {
/* 178 */       buffer.rewind();
/* 179 */       buffer.limit(other.position());
/* 180 */       return true;
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean rewindReadBuffer() {
/* 193 */     boolean rw = (rewind(this.readBuffer, this.writeBuffer) && this.readBuffer.hasRemaining());
/* 194 */     notifyAll();
/* 195 */     return rw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rewindWriteBuffer() {
/* 203 */     rewind(this.writeBuffer, this.readBuffer);
/* 204 */     notifyAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean ready() {
/* 209 */     return this.readBuffer.hasRemaining();
/*     */   }
/*     */   
/*     */   public synchronized int available() {
/* 213 */     int count = this.readBuffer.remaining();
/* 214 */     if (this.writeBuffer.position() < this.readBuffer.position()) {
/* 215 */       count += this.writeBuffer.position();
/*     */     }
/* 217 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read() throws IOException {
/* 222 */     if (!waitForInput()) {
/* 223 */       return -1;
/*     */     }
/*     */     
/* 226 */     int b = this.readBuffer.get();
/* 227 */     rewindReadBuffer();
/* 228 */     return b;
/*     */   }
/*     */   
/*     */   private int copyFromBuffer(char[] cbuf, int off, int len) {
/* 232 */     len = Math.min(len, this.readBuffer.remaining());
/* 233 */     this.readBuffer.get(cbuf, off, len);
/* 234 */     return len;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(char[] cbuf, int off, int len) throws IOException {
/* 239 */     if (len == 0) {
/* 240 */       return 0;
/*     */     }
/*     */     
/* 243 */     if (!waitForInput()) {
/* 244 */       return -1;
/*     */     }
/*     */     
/* 247 */     int count = copyFromBuffer(cbuf, off, len);
/* 248 */     if (rewindReadBuffer() && count < len) {
/* 249 */       count += copyFromBuffer(cbuf, off + count, len - count);
/* 250 */       rewindReadBuffer();
/*     */     } 
/*     */     
/* 253 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int read(CharBuffer target) throws IOException {
/* 258 */     if (!target.hasRemaining()) {
/* 259 */       return 0;
/*     */     }
/*     */     
/* 262 */     if (!waitForInput()) {
/* 263 */       return -1;
/*     */     }
/*     */     
/* 266 */     int count = this.readBuffer.read(target);
/* 267 */     if (rewindReadBuffer() && target.hasRemaining()) {
/* 268 */       count += this.readBuffer.read(target);
/* 269 */       rewindReadBuffer();
/*     */     } 
/*     */     
/* 272 */     return count;
/*     */   }
/*     */   
/*     */   private void encodeBytes(CharsetEncoder encoder, ByteBuffer output) throws IOException {
/* 276 */     int oldPos = output.position();
/* 277 */     CoderResult result = encoder.encode(this.readBuffer, output, false);
/* 278 */     int encodedCount = output.position() - oldPos;
/*     */     
/* 280 */     if (result.isUnderflow()) {
/* 281 */       boolean hasMoreInput = rewindReadBuffer();
/* 282 */       boolean reachedEndOfInput = false;
/*     */ 
/*     */       
/* 285 */       if (encodedCount == 0 && !hasMoreInput) {
/* 286 */         reachedEndOfInput = !waitForMoreInput();
/*     */       }
/*     */       
/* 289 */       result = encoder.encode(this.readBuffer, output, reachedEndOfInput);
/* 290 */       if (result.isError()) {
/* 291 */         result.throwException();
/*     */       }
/* 293 */       if (!reachedEndOfInput && output.position() - oldPos == 0) {
/* 294 */         throw new AssertionError("Failed to encode any chars");
/*     */       }
/* 296 */       rewindReadBuffer();
/* 297 */     } else if (result.isOverflow()) {
/* 298 */       if (encodedCount == 0) {
/* 299 */         throw new AssertionError("Output buffer has not enough space");
/*     */       }
/*     */     } else {
/* 302 */       result.throwException();
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized int readBytes(CharsetEncoder encoder, byte[] b, int off, int len) throws IOException {
/* 307 */     if (!waitForInput()) {
/* 308 */       return 0;
/*     */     }
/*     */     
/* 311 */     ByteBuffer output = ByteBuffer.wrap(b, off, len);
/* 312 */     encodeBytes(encoder, output);
/* 313 */     return output.position() - off;
/*     */   }
/*     */   
/*     */   synchronized void readBytes(CharsetEncoder encoder, ByteBuffer output) throws IOException {
/* 317 */     if (!waitForInput()) {
/*     */       return;
/*     */     }
/*     */     
/* 321 */     encodeBytes(encoder, output);
/*     */   }
/*     */   
/*     */   synchronized void write(char c) throws IOException {
/* 325 */     waitForBufferSpace();
/* 326 */     this.writeBuffer.put(c);
/* 327 */     rewindWriteBuffer();
/*     */   }
/*     */   
/*     */   synchronized void write(char[] cbuf, int off, int len) throws IOException {
/* 331 */     while (len > 0) {
/* 332 */       waitForBufferSpace();
/*     */ 
/*     */       
/* 335 */       int count = Math.min(len, this.writeBuffer.remaining());
/* 336 */       this.writeBuffer.put(cbuf, off, count);
/*     */       
/* 338 */       off += count;
/* 339 */       len -= count;
/*     */ 
/*     */       
/* 342 */       rewindWriteBuffer();
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized void write(String str, int off, int len) throws IOException {
/* 347 */     char[] buf = this.writeBuffer.array();
/*     */     
/* 349 */     while (len > 0) {
/* 350 */       waitForBufferSpace();
/*     */ 
/*     */       
/* 353 */       int count = Math.min(len, this.writeBuffer.remaining());
/*     */       
/* 355 */       str.getChars(off, off + count, buf, this.writeBuffer.position());
/* 356 */       this.writeBuffer.position(this.writeBuffer.position() + count);
/*     */       
/* 358 */       off += count;
/* 359 */       len -= count;
/*     */ 
/*     */       
/* 362 */       rewindWriteBuffer();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void flush() {
/* 368 */     if (this.readBuffer.hasRemaining())
/*     */     {
/* 370 */       notifyAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 376 */     this.closed = true;
/* 377 */     notifyAll();
/*     */   }
/*     */   
/*     */   private static class Writer
/*     */     extends java.io.Writer {
/*     */     private final PumpReader reader;
/*     */     
/*     */     private Writer(PumpReader reader) {
/* 385 */       this.reader = reader;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(int c) throws IOException {
/* 390 */       this.reader.write((char)c);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(char[] cbuf, int off, int len) throws IOException {
/* 395 */       this.reader.write(cbuf, off, len);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(String str, int off, int len) throws IOException {
/* 400 */       this.reader.write(str, off, len);
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {
/* 405 */       this.reader.flush();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 410 */       this.reader.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class InputStream
/*     */     extends java.io.InputStream
/*     */   {
/*     */     private final PumpReader reader;
/*     */     
/*     */     private final CharsetEncoder encoder;
/*     */     
/*     */     private final ByteBuffer buffer;
/*     */ 
/*     */     
/*     */     private InputStream(PumpReader reader, Charset charset) {
/* 426 */       this.reader = reader;
/* 427 */       this
/*     */         
/* 429 */         .encoder = charset.newEncoder().onUnmappableCharacter(CodingErrorAction.REPLACE).onMalformedInput(CodingErrorAction.REPLACE);
/* 430 */       this.buffer = ByteBuffer.allocate((int)Math.ceil((this.encoder.maxBytesPerChar() * 2.0F)));
/*     */ 
/*     */       
/* 433 */       this.buffer.limit(0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int available() throws IOException {
/* 438 */       return (int)(this.reader.available() * this.encoder.averageBytesPerChar()) + this.buffer.remaining();
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 443 */       if (!this.buffer.hasRemaining() && !readUsingBuffer()) {
/* 444 */         return -1;
/*     */       }
/*     */       
/* 447 */       return this.buffer.get() & 0xFF;
/*     */     }
/*     */     
/*     */     private boolean readUsingBuffer() throws IOException {
/* 451 */       this.buffer.clear();
/* 452 */       this.reader.readBytes(this.encoder, this.buffer);
/* 453 */       this.buffer.flip();
/* 454 */       return this.buffer.hasRemaining();
/*     */     }
/*     */     
/*     */     private int copyFromBuffer(byte[] b, int off, int len) {
/* 458 */       len = Math.min(len, this.buffer.remaining());
/* 459 */       this.buffer.get(b, off, len);
/* 460 */       return len;
/*     */     }
/*     */     
/*     */     public int read(byte[] b, int off, int len) throws IOException {
/*     */       int read;
/* 465 */       if (len == 0) {
/* 466 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 470 */       if (this.buffer.hasRemaining()) {
/* 471 */         read = copyFromBuffer(b, off, len);
/* 472 */         if (read == len) {
/* 473 */           return len;
/*     */         }
/*     */         
/* 476 */         off += read;
/* 477 */         len -= read;
/*     */       } else {
/* 479 */         read = 0;
/*     */       } 
/*     */ 
/*     */       
/* 483 */       if (len >= this.buffer.capacity()) {
/* 484 */         read += this.reader.readBytes(this.encoder, b, off, len);
/* 485 */       } else if (readUsingBuffer()) {
/* 486 */         read += copyFromBuffer(b, off, len);
/*     */       } 
/*     */ 
/*     */       
/* 490 */       return (read == 0) ? -1 : read;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 495 */       this.reader.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\PumpReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */