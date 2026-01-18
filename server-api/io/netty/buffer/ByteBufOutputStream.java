/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteBufOutputStream
/*     */   extends OutputStream
/*     */   implements DataOutput
/*     */ {
/*     */   private final ByteBuf buffer;
/*     */   private final int startIndex;
/*     */   private DataOutputStream utf8out;
/*     */   private boolean closed;
/*     */   private final boolean releaseOnClose;
/*     */   
/*     */   public ByteBufOutputStream(ByteBuf buffer) {
/*  50 */     this(buffer, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBufOutputStream(ByteBuf buffer, boolean releaseOnClose) {
/*  61 */     this.releaseOnClose = releaseOnClose;
/*  62 */     this.buffer = (ByteBuf)ObjectUtil.checkNotNull(buffer, "buffer");
/*  63 */     this.startIndex = buffer.writerIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int writtenBytes() {
/*  70 */     return this.buffer.writerIndex() - this.startIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  75 */     this.buffer.writeBytes(b, off, len);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  80 */     this.buffer.writeBytes(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*  85 */     this.buffer.writeByte(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBoolean(boolean v) throws IOException {
/*  90 */     this.buffer.writeBoolean(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeByte(int v) throws IOException {
/*  95 */     this.buffer.writeByte(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(String s) throws IOException {
/* 103 */     int length = s.length();
/* 104 */     this.buffer.ensureWritable(length);
/* 105 */     int offset = this.buffer.writerIndex();
/* 106 */     for (int i = 0; i < length; i++) {
/* 107 */       this.buffer.setByte(offset + i, (byte)s.charAt(i));
/*     */     }
/* 109 */     this.buffer.writerIndex(offset + length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChar(int v) throws IOException {
/* 114 */     this.buffer.writeChar(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChars(String s) throws IOException {
/* 119 */     int len = s.length();
/* 120 */     for (int i = 0; i < len; i++) {
/* 121 */       this.buffer.writeChar(s.charAt(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDouble(double v) throws IOException {
/* 127 */     this.buffer.writeDouble(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloat(float v) throws IOException {
/* 132 */     this.buffer.writeFloat(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt(int v) throws IOException {
/* 137 */     this.buffer.writeInt(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLong(long v) throws IOException {
/* 142 */     this.buffer.writeLong(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShort(int v) throws IOException {
/* 147 */     this.buffer.writeShort((short)v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeUTF(String s) throws IOException {
/* 152 */     DataOutputStream out = this.utf8out;
/* 153 */     if (out == null) {
/* 154 */       if (this.closed) {
/* 155 */         throw new IOException("The stream is closed");
/*     */       }
/*     */       
/* 158 */       this.utf8out = out = new DataOutputStream(this);
/*     */     } 
/* 160 */     out.writeUTF(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf buffer() {
/* 167 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 172 */     if (this.closed) {
/*     */       return;
/*     */     }
/* 175 */     this.closed = true;
/*     */     
/*     */     try {
/* 178 */       super.close();
/*     */     } finally {
/* 180 */       if (this.utf8out != null) {
/* 181 */         this.utf8out.close();
/*     */       }
/* 183 */       if (this.releaseOnClose)
/* 184 */         this.buffer.release(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\ByteBufOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */