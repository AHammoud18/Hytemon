/*     */ package org.bson.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bson.ByteBuf;
/*     */ import org.bson.ByteBufNIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicOutputBuffer
/*     */   extends OutputBuffer
/*     */ {
/*     */   private byte[] buffer;
/*     */   private int position;
/*     */   
/*     */   public BasicOutputBuffer() {
/*  42 */     this(1024);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicOutputBuffer(int initialSize) {
/*  51 */     this.buffer = new byte[initialSize];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getInternalBuffer() {
/*  61 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) {
/*  66 */     ensureOpen();
/*  67 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] bytes, int offset, int length) {
/*  72 */     ensureOpen();
/*     */     
/*  74 */     ensure(length);
/*  75 */     System.arraycopy(bytes, offset, this.buffer, this.position, length);
/*  76 */     this.position += length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeByte(int value) {
/*  81 */     ensureOpen();
/*     */     
/*  83 */     ensure(1);
/*  84 */     this.buffer[this.position++] = (byte)(0xFF & value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void write(int absolutePosition, int value) {
/*  89 */     ensureOpen();
/*     */     
/*  91 */     if (absolutePosition < 0) {
/*  92 */       throw new IllegalArgumentException(String.format("position must be >= 0 but was %d", new Object[] { Integer.valueOf(absolutePosition) }));
/*     */     }
/*  94 */     if (absolutePosition > this.position - 1) {
/*  95 */       throw new IllegalArgumentException(String.format("position must be <= %d but was %d", new Object[] { Integer.valueOf(this.position - 1), Integer.valueOf(absolutePosition) }));
/*     */     }
/*     */     
/*  98 */     this.buffer[absolutePosition] = (byte)(0xFF & value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 103 */     ensureOpen();
/* 104 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 112 */     ensureOpen();
/* 113 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public int pipe(OutputStream out) throws IOException {
/* 118 */     ensureOpen();
/* 119 */     out.write(this.buffer, 0, this.position);
/* 120 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void truncateToPosition(int newPosition) {
/* 125 */     ensureOpen();
/* 126 */     if (newPosition > this.position || newPosition < 0) {
/* 127 */       throw new IllegalArgumentException();
/*     */     }
/* 129 */     this.position = newPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ByteBuf> getByteBuffers() {
/* 134 */     ensureOpen();
/* 135 */     return Arrays.asList(new ByteBuf[] { (ByteBuf)new ByteBufNIO(ByteBuffer.wrap(this.buffer, 0, this.position).duplicate().order(ByteOrder.LITTLE_ENDIAN)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 140 */     this.buffer = null;
/*     */   }
/*     */   
/*     */   private void ensureOpen() {
/* 144 */     if (this.buffer == null) {
/* 145 */       throw new IllegalStateException("The output is closed");
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensure(int more) {
/* 150 */     int need = this.position + more;
/* 151 */     if (need <= this.buffer.length) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     int newSize = this.buffer.length * 2;
/* 156 */     if (newSize < need) {
/* 157 */       newSize = need + 128;
/*     */     }
/*     */     
/* 160 */     byte[] n = new byte[newSize];
/* 161 */     System.arraycopy(this.buffer, 0, n, 0, this.position);
/* 162 */     this.buffer = n;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\io\BasicOutputBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */