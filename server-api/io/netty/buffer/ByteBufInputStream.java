/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
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
/*     */ 
/*     */ public class ByteBufInputStream
/*     */   extends InputStream
/*     */   implements DataInput
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   private final int startIndex;
/*     */   private final int endIndex;
/*     */   private boolean closed;
/*     */   private final boolean releaseOnClose;
/*     */   private StringBuilder lineBuf;
/*     */   
/*     */   public ByteBufInputStream(ByteBuf buffer) {
/*  66 */     this(buffer, buffer.readableBytes());
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
/*     */   public ByteBufInputStream(ByteBuf buffer, int length) {
/*  80 */     this(buffer, length, false);
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
/*     */   public ByteBufInputStream(ByteBuf buffer, boolean releaseOnClose) {
/*  92 */     this(buffer, buffer.readableBytes(), releaseOnClose);
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
/*     */   public ByteBufInputStream(ByteBuf buffer, int length, boolean releaseOnClose) {
/* 108 */     ObjectUtil.checkNotNull(buffer, "buffer");
/* 109 */     if (length < 0) {
/* 110 */       if (releaseOnClose) {
/* 111 */         buffer.release();
/*     */       }
/* 113 */       ObjectUtil.checkPositiveOrZero(length, "length");
/*     */     } 
/* 115 */     if (length > buffer.readableBytes()) {
/* 116 */       if (releaseOnClose) {
/* 117 */         buffer.release();
/*     */       }
/* 119 */       throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + length + ", maximum is " + buffer
/* 120 */           .readableBytes());
/*     */     } 
/*     */     
/* 123 */     this.releaseOnClose = releaseOnClose;
/* 124 */     this.buffer = buffer;
/* 125 */     this.startIndex = buffer.readerIndex();
/* 126 */     this.endIndex = this.startIndex + length;
/* 127 */     buffer.markReaderIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readBytes() {
/* 134 */     return this.buffer.readerIndex() - this.startIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*     */     try {
/* 140 */       super.close();
/*     */     } finally {
/*     */       
/* 143 */       if (this.releaseOnClose && !this.closed) {
/* 144 */         this.closed = true;
/* 145 */         this.buffer.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 152 */     return this.endIndex - this.buffer.readerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int readlimit) {
/* 158 */     this.buffer.markReaderIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 168 */     int available = available();
/* 169 */     if (available == 0) {
/* 170 */       return -1;
/*     */     }
/* 172 */     return this.buffer.readByte() & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 177 */     int available = available();
/* 178 */     if (available == 0) {
/* 179 */       return -1;
/*     */     }
/*     */     
/* 182 */     len = Math.min(available, len);
/* 183 */     this.buffer.readBytes(b, off, len);
/* 184 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() throws IOException {
/* 190 */     this.buffer.resetReaderIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 195 */     if (n > 2147483647L) {
/* 196 */       return skipBytes(2147483647);
/*     */     }
/* 198 */     return skipBytes((int)n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean readBoolean() throws IOException {
/* 204 */     checkAvailable(1);
/* 205 */     return (read() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() throws IOException {
/* 210 */     checkAvailable(1);
/* 211 */     return this.buffer.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() throws IOException {
/* 216 */     return (char)readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() throws IOException {
/* 221 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() throws IOException {
/* 226 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b) throws IOException {
/* 231 */     readFully(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b, int off, int len) throws IOException {
/* 236 */     checkAvailable(len);
/* 237 */     this.buffer.readBytes(b, off, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() throws IOException {
/* 242 */     checkAvailable(4);
/* 243 */     return this.buffer.readInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String readLine() throws IOException {
/* 250 */     int available = available();
/* 251 */     if (available == 0) {
/* 252 */       return null;
/*     */     }
/*     */     
/* 255 */     if (this.lineBuf != null) {
/* 256 */       this.lineBuf.setLength(0);
/*     */     }
/*     */     
/*     */     do {
/* 260 */       int c = this.buffer.readUnsignedByte();
/* 261 */       available--;
/* 262 */       switch (c) {
/*     */         case 10:
/*     */           break;
/*     */         
/*     */         case 13:
/* 267 */           if (available > 0 && (char)this.buffer.getUnsignedByte(this.buffer.readerIndex()) == '\n') {
/* 268 */             this.buffer.skipBytes(1);
/* 269 */             available--;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 274 */       if (this.lineBuf == null) {
/* 275 */         this.lineBuf = new StringBuilder();
/*     */       }
/* 277 */       this.lineBuf.append((char)c);
/*     */     }
/* 279 */     while (available > 0);
/*     */     
/* 281 */     return (this.lineBuf != null && this.lineBuf.length() > 0) ? this.lineBuf.toString() : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() throws IOException {
/* 286 */     checkAvailable(8);
/* 287 */     return this.buffer.readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() throws IOException {
/* 292 */     checkAvailable(2);
/* 293 */     return this.buffer.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public String readUTF() throws IOException {
/* 298 */     return DataInputStream.readUTF(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedByte() throws IOException {
/* 303 */     return readByte() & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() throws IOException {
/* 308 */     return readShort() & 0xFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public int skipBytes(int n) throws IOException {
/* 313 */     int nBytes = Math.min(available(), n);
/* 314 */     this.buffer.skipBytes(nBytes);
/* 315 */     return nBytes;
/*     */   }
/*     */   
/*     */   private void checkAvailable(int fieldSize) throws IOException {
/* 319 */     if (fieldSize < 0) {
/* 320 */       throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
/*     */     }
/* 322 */     if (fieldSize > available())
/* 323 */       throw new EOFException("fieldSize is too long! Length is " + fieldSize + ", but maximum is " + 
/* 324 */           available()); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\ByteBufInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */