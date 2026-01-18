/*     */ package org.bson;
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
/*     */ class Bits
/*     */ {
/*     */   static void readFully(InputStream inputStream, byte[] buffer) throws IOException {
/*  38 */     readFully(inputStream, buffer, 0, buffer.length);
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
/*     */   static void readFully(InputStream inputStream, byte[] buffer, int offset, int length) throws IOException {
/*  53 */     if (buffer.length < length + offset) {
/*  54 */       throw new IllegalArgumentException("Buffer is too small");
/*     */     }
/*     */     
/*  57 */     int arrayOffset = offset;
/*  58 */     int bytesToRead = length;
/*  59 */     while (bytesToRead > 0) {
/*  60 */       int bytesRead = inputStream.read(buffer, arrayOffset, bytesToRead);
/*  61 */       if (bytesRead < 0) {
/*  62 */         throw new EOFException();
/*     */       }
/*  64 */       bytesToRead -= bytesRead;
/*  65 */       arrayOffset += bytesRead;
/*     */     } 
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
/*     */   static int readInt(InputStream inputStream, byte[] buffer) throws IOException {
/*  78 */     readFully(inputStream, buffer, 0, 4);
/*  79 */     return readInt(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int readInt(byte[] buffer) {
/*  90 */     return readInt(buffer, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int readInt(byte[] buffer, int offset) {
/* 101 */     int x = 0;
/* 102 */     x |= (0xFF & buffer[offset + 0]) << 0;
/* 103 */     x |= (0xFF & buffer[offset + 1]) << 8;
/* 104 */     x |= (0xFF & buffer[offset + 2]) << 16;
/* 105 */     x |= (0xFF & buffer[offset + 3]) << 24;
/* 106 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long readLong(InputStream inputStream) throws IOException {
/* 117 */     return readLong(inputStream, new byte[8]);
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
/*     */   static long readLong(InputStream inputStream, byte[] buffer) throws IOException {
/* 129 */     readFully(inputStream, buffer, 0, 8);
/* 130 */     return readLong(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long readLong(byte[] buffer) {
/* 141 */     return readLong(buffer, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long readLong(byte[] buffer, int offset) {
/* 152 */     long x = 0L;
/* 153 */     x |= (0xFFL & buffer[offset + 0]) << 0L;
/* 154 */     x |= (0xFFL & buffer[offset + 1]) << 8L;
/* 155 */     x |= (0xFFL & buffer[offset + 2]) << 16L;
/* 156 */     x |= (0xFFL & buffer[offset + 3]) << 24L;
/* 157 */     x |= (0xFFL & buffer[offset + 4]) << 32L;
/* 158 */     x |= (0xFFL & buffer[offset + 5]) << 40L;
/* 159 */     x |= (0xFFL & buffer[offset + 6]) << 48L;
/* 160 */     x |= (0xFFL & buffer[offset + 7]) << 56L;
/* 161 */     return x;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\Bits.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */