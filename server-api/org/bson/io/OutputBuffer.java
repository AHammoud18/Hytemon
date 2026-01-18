/*     */ package org.bson.io;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import org.bson.BsonSerializationException;
/*     */ import org.bson.ByteBuf;
/*     */ import org.bson.types.ObjectId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OutputBuffer
/*     */   extends OutputStream
/*     */   implements BsonOutput
/*     */ {
/*     */   public void write(byte[] b) {
/*  37 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public void write(byte[] bytes, int offset, int length) {
/*  46 */     writeBytes(bytes, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] bytes) {
/*  51 */     writeBytes(bytes, 0, bytes.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt32(int value) {
/*  56 */     write(value >> 0);
/*  57 */     write(value >> 8);
/*  58 */     write(value >> 16);
/*  59 */     write(value >> 24);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt32(int position, int value) {
/*  64 */     write(position, value >> 0);
/*  65 */     write(position + 1, value >> 8);
/*  66 */     write(position + 2, value >> 16);
/*  67 */     write(position + 3, value >> 24);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt64(long value) {
/*  72 */     write((byte)(int)(0xFFL & value >> 0L));
/*  73 */     write((byte)(int)(0xFFL & value >> 8L));
/*  74 */     write((byte)(int)(0xFFL & value >> 16L));
/*  75 */     write((byte)(int)(0xFFL & value >> 24L));
/*  76 */     write((byte)(int)(0xFFL & value >> 32L));
/*  77 */     write((byte)(int)(0xFFL & value >> 40L));
/*  78 */     write((byte)(int)(0xFFL & value >> 48L));
/*  79 */     write((byte)(int)(0xFFL & value >> 56L));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDouble(double x) {
/*  84 */     writeLong(Double.doubleToRawLongBits(x));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeString(String str) {
/*  89 */     writeInt(0);
/*  90 */     int strLen = writeCharacters(str, false);
/*  91 */     writeInt32(getPosition() - strLen - 4, strLen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCString(String value) {
/*  96 */     writeCharacters(value, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeObjectId(ObjectId value) {
/* 101 */     write(value.toByteArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 109 */     return getSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int pipe(OutputStream paramOutputStream) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract List<ByteBuf> getByteBuffers();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void truncateToPosition(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/*     */     try {
/* 140 */       ByteArrayOutputStream bout = new ByteArrayOutputStream(size());
/* 141 */       pipe(bout);
/* 142 */       return bout.toByteArray();
/* 143 */     } catch (IOException ioe) {
/* 144 */       throw new RuntimeException("should be impossible", ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int value) {
/* 150 */     writeByte(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInt(int value) {
/* 160 */     writeInt32(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 165 */     return getClass().getName() + " size: " + size() + " pos: " + getPosition();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void write(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLong(long value) {
/* 183 */     writeInt64(value);
/*     */   }
/*     */   
/*     */   private int writeCharacters(String str, boolean checkForNullCharacters) {
/* 187 */     int len = str.length();
/* 188 */     int total = 0;
/*     */     int i;
/* 190 */     for (i = 0; i < len; ) {
/* 191 */       int c = Character.codePointAt(str, i);
/*     */       
/* 193 */       if (checkForNullCharacters && c == 0) {
/* 194 */         throw new BsonSerializationException(String.format("BSON cstring '%s' is not valid because it contains a null character at index %d", new Object[] { str, 
/* 195 */                 Integer.valueOf(i) }));
/*     */       }
/* 197 */       if (c < 128) {
/* 198 */         write((byte)c);
/* 199 */         total++;
/* 200 */       } else if (c < 2048) {
/* 201 */         write((byte)(192 + (c >> 6)));
/* 202 */         write((byte)(128 + (c & 0x3F)));
/* 203 */         total += 2;
/* 204 */       } else if (c < 65536) {
/* 205 */         write((byte)(224 + (c >> 12)));
/* 206 */         write((byte)(128 + (c >> 6 & 0x3F)));
/* 207 */         write((byte)(128 + (c & 0x3F)));
/* 208 */         total += 3;
/*     */       } else {
/* 210 */         write((byte)(240 + (c >> 18)));
/* 211 */         write((byte)(128 + (c >> 12 & 0x3F)));
/* 212 */         write((byte)(128 + (c >> 6 & 0x3F)));
/* 213 */         write((byte)(128 + (c & 0x3F)));
/* 214 */         total += 4;
/*     */       } 
/*     */       
/* 217 */       i += Character.charCount(c);
/*     */     } 
/*     */     
/* 220 */     write(0);
/* 221 */     total++;
/* 222 */     return total;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\io\OutputBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */